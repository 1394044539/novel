package wpy.personal.novel.novel.novel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import nl.siegmann.epublib.domain.*;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import wpy.personal.novel.base.constant.CharConstant;
import wpy.personal.novel.base.constant.NumConstant;
import wpy.personal.novel.base.constant.StrConstant;
import wpy.personal.novel.base.enums.BusinessEnums;
import wpy.personal.novel.base.enums.ErrorCode;
import wpy.personal.novel.base.exception.BusinessException;
import wpy.personal.novel.novel.novel.mapper.NovelChapterMapper;
import wpy.personal.novel.novel.novel.service.NovelChapterService;
import wpy.personal.novel.pojo.bo.ChapterBo;
import wpy.personal.novel.pojo.bo.VolumeChapterBo;
import wpy.personal.novel.pojo.entity.NovelChapter;
import wpy.personal.novel.pojo.entity.NovelVolume;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.utils.FileUtils;
import wpy.personal.novel.utils.ObjectUtils;
import wpy.personal.novel.utils.StringUtils;
import wpy.personal.novel.utils.dateUtils.DateUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * <p>
 * 小说章节表 服务实现类
 * </p>
 *
 * @author wangpanyin
 * @since 2021-09-07
 */
@Service
@Transactional
public class NovelChapterServiceImpl extends ServiceImpl<NovelChapterMapper, NovelChapter> implements NovelChapterService {

    @Autowired
    private NovelChapterMapper novelChapterMapper;

    @Value("${novel.filePath.rootPath}")
    private String rootPath;
    @Value("${novel.filePath.volumeImg}")
    private String volumeImgPath;


    /**
     * 第一种正则规则：空格或没有空格开头，匹配到章节，中间可能有空格，可能没有，然后匹配到章节名称
     */
    private static final Pattern txtChapterPattern = Pattern.compile("[\\u3000|\\u0020|\\u00A0]*第[0-9零一二三四五六七八九十百千万亿]+[章节卷集部篇回][\\u3000|\\u0020|\\u00A0]*\\S*");


    @Override
    public VolumeChapterBo analysisTxt(byte[] bytes, NovelVolume novelVolume, MultipartFile volumeFile, String userId) {
        try {
            VolumeChapterBo volumeChapterBo = new VolumeChapterBo();
            //bytes转化为流，获取全部数据
            List<String> allList = new ArrayList<>(IOUtils.readLines(new ByteArrayInputStream(bytes), StrConstant.DEFAULT_CHARSET));
            //获取返回的参数
            volumeChapterBo.setTotalLine(allList.size());
            Long reduce = allList.stream().reduce(0L, (result, item) -> result + (long) item.length(), Long::sum);
            volumeChapterBo.setTotalWord(reduce);
            List<NovelChapter> chapterList = Lists.newArrayList();
            int chapterOrder = 0;
            //第一次循环，正则取到章节基本信息
            for (int i = 0; i < allList.size(); i++) {
                NovelChapter novelChapter = ObjectUtils.newInstance(userId, NovelChapter.class);
                novelChapter.setChapterId(StringUtils.getUuid32());
                // 小说id
                novelChapter.setNovelId(novelVolume.getNovelId());
                //分卷id
                novelChapter.setVolumeId(novelVolume.getVolumeId());
                //设置排序
                novelChapter.setChapterOrder(chapterOrder);
                chapterOrder++;
                //如果正则匹配上，则说明这是章节名称
                String chapterText = allList.get(i);
                if (txtChapterPattern.matcher(chapterText).matches()) {
                    //设置章节名
                    novelChapter.setChapterName(chapterText);
                    //设置章节行数
                    novelChapter.setStartLine(i + 1);
                } else if (i == NumConstant.ZERO) {
                    //如果是第一次，且没有匹配到数据，默认为序章
                    novelChapter.setChapterName("序章");
                    //设置章节行数
                    novelChapter.setStartLine(0);
                } else {
                    //既不是章节标题也不是序章，将章节号减回来然后进行下一次循环
                    chapterOrder--;
                    continue;
                }
                chapterList.add(novelChapter);
            }
            if (CollectionUtils.isEmpty(chapterList)) {
                //说明未解析到任何章节名数据，则整本为一个章节，章节名为小说名
                NovelChapter novelChapter = ObjectUtils.newInstance(userId, NovelChapter.class);
                novelChapter.setNovelId(novelVolume.getNovelId());
                novelChapter.setVolumeId(novelVolume.getVolumeId());
                novelChapter.setChapterOrder(NumConstant.ZERO);
                novelChapter.setTotalLine(allList.size());
                novelChapter.setStartLine(NumConstant.ZERO);
                novelChapter.setEndLine(allList.size());
                novelChapter.setChapterName(novelVolume.getVolumeName());
                chapterList.add(novelChapter);
            } else {
                //再循环一次，把每一章的行数的记录下来
                for (int i = 0; i < chapterList.size(); i++) {
                    NovelChapter novelChapter = chapterList.get(i);
                    if (i + 1 >= chapterList.size()) {
                        //如果i=1大于等于总长度，说明是最后一章，结束行就是总行，总长度只需要全部行减去开始行
                        novelChapter.setEndLine(allList.size());
                        novelChapter.setTotalLine(allList.size() - novelChapter.getStartLine());
                    } else {
                        //普通情况比较下一个的开始行即可
                        NovelChapter nextNovelChapter = chapterList.get(i + 1);
                        novelChapter.setEndLine(nextNovelChapter.getStartLine() - 1);
                        novelChapter.setTotalLine(novelChapter.getTotalLine() == null ? nextNovelChapter.getStartLine() : nextNovelChapter.getStartLine() - novelChapter.getTotalLine());
                    }
                }
            }
            //批量插入进数据库
            if (!CollectionUtils.isEmpty(chapterList)) {
                this.saveBatch(chapterList);
            }
            return volumeChapterBo;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw BusinessException.fail(ErrorCode.ANALYSIS_TXT_ERROR, e);
        }
    }

    @Override
    public VolumeChapterBo analysisEpub(byte[] bytes, NovelVolume novelVolume, MultipartFile volumeFile, String userId) {
        //获取epub文件
        Book epubBook = FileUtils.getEpubBook(new ByteArrayInputStream(bytes));
        //解析epub会有这样的情况，前端啥都没有写，完全需要从文件中解析，所以这里每一个都需要判断一下
        VolumeChapterBo volumeChapterBo = this.checkVolumeParam(epubBook, novelVolume);
        //开始解析章节信息
        TableOfContents tableOfContents = epubBook.getTableOfContents();
        List<TOCReference> tocReferences = tableOfContents.getTocReferences();
        List<NovelChapter> list=Lists.newArrayList();
        long wordNum=0;
        int novelLine=0;
        for(int i=0;i<tocReferences.size();i++){
            TOCReference tocReference = tocReferences.get(i);
            Resource resource = tocReference.getResource();
            if(StringUtils.isBlank(resource)){
                continue;
            }
            NovelChapter novelChapter = ObjectUtils.newInstance(userId, NovelChapter.class);
            novelChapter.setNovelId(novelVolume.getNovelId());
            novelChapter.setVolumeId(novelVolume.getVolumeId());
            novelChapter.setEpubPath(resource.getHref());
            novelChapter.setChapterName(StringUtils.isNotEmpty(resource.getTitle())?resource.getTitle():tocReference.getTitle());
            novelChapter.setChapterOrder(i);
            //从这里开始要解析行
            byte[] data=null;
            try {
                data = resource.getData();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
            int chapterLine=0;
            if(data!=null){
                //去掉html
                String context = StringUtils.removeHtml(new String(data));
                //查看有多少行
                while (context.contains("\n")){
                    context=StringUtils.replaceFirst(context, "\n", "");
                    chapterLine++;
                    novelLine++;
                }
                context=StringUtils.replaceAll(context, "\r", "");
                wordNum += context.length();
            }
            novelChapter.setTotalLine(chapterLine);
            list.add(novelChapter);
        }
        //保存章节信息
        if(!CollectionUtils.isEmpty(list)){
            this.saveBatch(list);
        }
        volumeChapterBo.setTotalWord(wordNum);
        volumeChapterBo.setTotalLine(novelLine);
        return volumeChapterBo;
    }

    @Override
    public ChapterBo getChapterContent(String chapterId, SysUser sysUser) {
        ChapterBo chapterBo = new ChapterBo();
        //1、拿到基本参数和文件类型
        NovelChapter novelChapter = novelChapterMapper.getChapterFileInfo(chapterId);
        chapterBo.setChapterName(novelChapter.getChapterName());
        //2、拿到上一章和下一章
        QueryWrapper<NovelChapter> qw = new QueryWrapper<>();
        qw.eq("volume_id", novelChapter.getVolumeId());
        qw.and(wrapper -> wrapper.eq("chapter_order", novelChapter.getChapterOrder() - 1)
                .or().eq("chapter_order", novelChapter.getChapterOrder() + 1));
        List<NovelChapter> novelChapterList = this.novelChapterMapper.selectList(qw);
        for (NovelChapter chapter : novelChapterList) {
            if(novelChapter.getChapterOrder().equals(chapter.getChapterOrder() - 1)){
                chapterBo.setLastChapterId(chapter.getChapterId());
                chapterBo.setLastChapterName(chapter.getChapterName());
            }
            if(novelChapter.getChapterOrder().equals(chapter.getChapterOrder() + 1)){
                chapterBo.setNextChapterId(chapter.getChapterId());
                chapterBo.setNextChapterName(chapter.getChapterName());
            }
        }
        //3、取出内容
        if(BusinessEnums.TXT.getCode().equalsIgnoreCase(novelChapter.getFileType())){
            //txt
            chapterBo.setContext(this.getTxtContent(novelChapter));
        }else if(BusinessEnums.EPUB.getCode().equalsIgnoreCase(novelChapter.getFileType())){
            //epub
            chapterBo.setContext(this.getEpubContent(novelChapter));
        }else {
            throw BusinessException.fail(ErrorCode.FILE_TYPE_ERROR);
        }
        return chapterBo;
    }

    @Override
    public List<NovelChapter> getChapterList(String volumeId, SysUser sysUser) {
        return this.novelChapterMapper.selectList(new QueryWrapper<NovelChapter>().eq("volume_id", volumeId)
            .orderByAsc("volume_order"));
    }

    /**
     * 获取epub的某一条内容
     * @param novelChapter
     * @return
     */
    private List<String> getEpubContent(NovelChapter novelChapter) {
        List<String> list = Lists.newArrayList();
        Book epubBook = FileUtils.getEpubBook(novelChapter.getFilePath()+ CharConstant.FILE_SEPARATOR+novelChapter.getFileMd5());
        Resource resource = epubBook.getResources().getByHref(novelChapter.getEpubPath());
        byte[] data = null;
        try {
            data = resource.getData();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        //html解析
        if (data != null) {
            String html = new String(data);
            Document parse = Jsoup.parse(html);
            Elements p = parse.getElementsByTag("p");
            for (Element element : p) {
                list.add(element.text());
            }
        }
        return list;
    }

    /**
     * 获取txt的某一张内容
     * @param novelChapter
     * @return
     */
    private List<String> getTxtContent(NovelChapter novelChapter) {
        List<String> list=Lists.newArrayList();
        File file = new File(novelChapter.getFilePath()+ CharConstant.FILE_SEPARATOR+novelChapter.getFileMd5());
        try (InputStream is = new FileInputStream(file)){
            //获取的时候编码也需要解析
            String code=FileUtils.getTxtFileCode(is);
            if(StringUtils.isEmpty(code)){
                list=FileUtils.getLineByEnd(is,novelChapter.getStartLine(),novelChapter.getEndLine());
            }else {
                InputStreamReader isr = new InputStreamReader(is, code);
                byte[] fileBytes = IOUtils.toByteArray(isr);
                list = FileUtils.getLineByLimit(new ByteArrayInputStream(fileBytes),(long)novelChapter.getStartLine(),(long)novelChapter.getTotalLine());
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        //去空格处理
        list.forEach(StringUtils::removeBlank);
        return list;
    }

    /**
     * 检查epub的参数是否需要从文件中查找
     *
     * @param epubBook
     * @param novelVolume
     * @return
     */
    private VolumeChapterBo checkVolumeParam(Book epubBook, NovelVolume novelVolume) {
        VolumeChapterBo volumeChapterBo = new VolumeChapterBo();
        Metadata metadata = epubBook.getMetadata();

        //获得作者信息
        String authorStr="";
        List<Author> authors = metadata.getAuthors();
        for (Author author : authors) {
            String a=author.getFirstname();
            if(StringUtils.isNotEmpty(a)){
                a+="·" + author.getLastname();
            }else{
                a=author.getLastname();
            }
            if(StringUtils.isEmpty(authorStr)){
                authorStr=a;
            }else{
                authorStr+=";"+a;
            }
        }
        volumeChapterBo.setVolumeAuthor(authorStr);
        //判断是否有发布时间
        if (novelVolume.getPublicTime() == null) {
            List<nl.siegmann.epublib.domain.Date> dates = metadata.getDates();
            if (!dates.isEmpty()) {
                nl.siegmann.epublib.domain.Date date = dates.get(0);
                if (date != null) {
                    volumeChapterBo.setPublicTime(DateUtils.getAllFormatDate(date.getValue()));
                }
            }
        }
        //获得描述信息
        if (StringUtils.isEmpty(novelVolume.getVolumeDesc())) {
            StringBuilder stringBuilder = new StringBuilder();
            List<String> descriptions = metadata.getDescriptions();
            for (String description : descriptions) {
                description = StringUtils.removeHtml(description);
                if (StringUtils.isEmpty(stringBuilder.toString())) {
                    stringBuilder.append(description);
                } else {
                    stringBuilder.append(";").append(description);
                }
            }
            //去掉各种奇怪的空格
            String introduce = stringBuilder.toString();
            introduce = introduce.replace("\n", "");
            introduce = introduce.replace("\t", "");
            introduce = introduce.replace("\r", "");
            introduce = introduce.replace(" ", "");
            volumeChapterBo.setVolumeDesc(introduce);
        }
        //获得标题信息
        if (StringUtils.isEmpty(novelVolume.getVolumeName())) {
            if (StringUtils.isNotEmpty(metadata.getFirstTitle())) {
                volumeChapterBo.setVolumeName(metadata.getFirstTitle());
            } else if (!metadata.getTitles().isEmpty()) {
                volumeChapterBo.setVolumeName(metadata.getTitles().get(0));
            }
        }

        //判断是否有封面
        if (StringUtils.isEmpty(novelVolume.getVolumeImg())) {
            Resource coverImage = epubBook.getCoverImage();
            if (coverImage != null) {
                try {
                    byte[] data = coverImage.getData();
                    //文件名加后缀
                    String fileName;
                    if (StringUtils.isNotEmpty(novelVolume.getVolumeName())) {
                        fileName = novelVolume.getVolumeName();
                    } else if (StringUtils.isNotEmpty(volumeChapterBo.getVolumeName())) {
                        fileName = volumeChapterBo.getVolumeName();
                    } else {
                        fileName = novelVolume.getNovelId();
                    }
                    fileName += coverImage.getMediaType().getDefaultExtension();
                    volumeChapterBo.setVolumeImg(FileUtils.uploadImg(rootPath, volumeImgPath, novelVolume.getVolumeId(), fileName, data));
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return volumeChapterBo;
    }
}
