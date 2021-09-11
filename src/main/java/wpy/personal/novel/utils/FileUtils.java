package wpy.personal.novel.utils;

import lombok.extern.slf4j.Slf4j;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.epub.EpubReader;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;
import wpy.personal.novel.base.constant.CharConstant;
import wpy.personal.novel.base.constant.StrConstant;
import wpy.personal.novel.base.enums.UtilsEnums;
import wpy.personal.novel.base.exception.UtilsException;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class FileUtils {

    /**
     * 上传文件
     * @param multipartFile
     * @param path
     * @param fileName
     */
    public static void upload(MultipartFile multipartFile,String path,String fileName){
        //没有才会选择去存一份并且上传
        String savePath=path+CharConstant.FILE_SEPARATOR+(StringUtils.isEmpty(fileName)?multipartFile.getOriginalFilename():fileName);
        File file=new File(savePath);
        if(!file.exists()){
            boolean mkdirs = file.getParentFile().mkdirs();
            if(mkdirs){
                log.info("文件夹创建成功");
            }else {
                log.info("文件夹已存在，无需创建爱你");
            }
        }
        try {
            multipartFile.transferTo(file);
        }catch (Exception e){
            log.error(e.getMessage());
            throw UtilsException.fail(UtilsEnums.FILE_UPLOAD_FAIL,e);
        }
    }

    /**
     * 上传文件
     * @param bytes
     * @param path
     * @param fileName
     */
    public static void upload(byte[] bytes,String path,String fileName){
        File file = new File(path + CharConstant.FILE_SEPARATOR + fileName);
        if (!file.exists()){
            boolean mkdirs = file.getParentFile().mkdirs();
            if(mkdirs){
                log.info("文件夹创建成功");
            }else {
                log.info("文件夹已存在，无需创建爱你");
            }
        }
        try (FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fos))
        {
            bos.write(bytes);
        }catch (Exception e){
            log.error(e.getMessage());
            throw UtilsException.fail(UtilsEnums.FILE_UPLOAD_FAIL);
        }

    }

    /**
     * 上传图片
     * @param rootPath
     * @param childPath
     * @param uniqueId
     * @param imgFile
     */
    public static String uploadImg(String rootPath ,String childPath,String uniqueId, MultipartFile imgFile){
        if(imgFile==null){
            return null;
        }
        //1、设置上传的路径和名字,已novel的id作为路径文件夹名，防止上传同一名字出现问题
        String prePath=childPath+ CharConstant.FILE_SEPARATOR+uniqueId;
        //存到数据库的路径和磁盘路径不一样
        String uploadFilePath = rootPath + CharConstant.FILE_SEPARATOR+prePath;
        FileUtils.upload(imgFile,uploadFilePath,imgFile.getOriginalFilename());
        return prePath+ CharConstant.FILE_SEPARATOR+imgFile.getOriginalFilename();
    }

    /**
     * 上传图片
     * @param rootPath
     * @param childPath
     * @param uniqueId
     * @param bytes
     */
    public static String uploadImg(String rootPath ,String childPath,String uniqueId,String fileName, byte[] bytes){
        if(bytes==null){
            return null;
        }
        //1、设置上传的路径和名字,已novel的id作为路径文件夹名，防止上传同一名字出现问题
        String prePath=childPath+ CharConstant.FILE_SEPARATOR+uniqueId;
        //存到数据库的路径和磁盘路径不一样
        String uploadFilePath = rootPath + CharConstant.FILE_SEPARATOR+prePath;
        FileUtils.upload(bytes,uploadFilePath,fileName);
        return prePath+ CharConstant.FILE_SEPARATOR+fileName;
    }



    /**
     * 获取上传文件的md5
     * @param file
     * @return
     * @throws
     */
    public static String getMd5(MultipartFile file) {
        try {
            //获取文件的byte信息
            byte[] uploadBytes = file.getBytes();
            // 拿到一个MD5转换器
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(uploadBytes);
            //转换为16进制
            return new BigInteger(1, digest).toString(16);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * 吧输入流转为byte
     * @author pywang6
     * @date 2021/3/11 10:24
     *
     * @param inputStream
     * @return byte[]
     */
    public static byte[] inputStreamToByte(InputStream inputStream){
        byte[] buf = null;
        try {
            if(inputStream!=null){
                buf = IOUtils.toByteArray(inputStream);//ins为InputStream流
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return buf;
    }

    /**
     * 获取文件类型
     * @param fileName
     * @return
     */
    public static String getFileType(String fileName){
        if(StringUtils.isNotEmpty(fileName)){
            int index = fileName.lastIndexOf(CharConstant.SEPARATOR);
            if(index != -1){
                return fileName.substring(index+1);
            }
        }
        return "";
    }

    /**
     * 获取文件类型带.
     * @param fileName
     * @return
     */
    public static String getFileTypeHasSep(String fileName){
        if(StringUtils.isNotEmpty(fileName)){
            int index = fileName.lastIndexOf(CharConstant.SEPARATOR);
            if(index != -1){
                return fileName.substring(index);
            }
        }
        return "";
    }

    /**
     * 获取文件类型
     * @param fileName
     * @return
     */
    public static String getFileName(String fileName){
        if(StringUtils.isNotEmpty(fileName)){
            int index = fileName.lastIndexOf(CharConstant.SEPARATOR);
            if(index != -1){
                return fileName.substring(0,index);
            }
            return fileName;
        }
        return "";
    }

    /**
     * 增加前缀‘/’
     * @return
     */
    public static String preFileName(String fileName){
        if(!fileName.startsWith(CharConstant.FILE_SEPARATOR)){
            return fileName+ CharConstant.FILE_SEPARATOR;
        }
        return fileName;
    }

    /**
     * 删除文件
     * @param oldPath
     * @return
     */
    public static boolean deleteFile(String oldPath) {
        try {
            File file = new File(oldPath);
            //判断文件是否存在
            if (!file.exists()) {
                //不存在则默认已经删除
                return true;
            }
            //判断是否是文件夹
            if (!file.isDirectory()) {
                //单文件
                return file.delete();
            }else {
                //文件夹
                return delFolder(oldPath);
            }
        }catch (Exception e){
            log.error(e.getMessage());
            throw UtilsException.fail(UtilsEnums.FILE_DELETE_FAIL,e);
        }
    }

    /**
     * 删除文件夹
     * @param folderPath
     * @return
     */
    public static boolean delFolder(String folderPath) {
        try {
            //1、删除文件夹里面的内容（需要递归）
            File rootFile = new File(folderPath);
            String[] tempList = rootFile.list();
            if(tempList==null||tempList.length==0){
                return rootFile.delete();
            }
            for (String path : tempList) {
                File file = new File(folderPath+preFileName(path));
                //是文件夹需要先清空文件夹里面的东西
                if (file.isDirectory()) {
                    delFolder(folderPath + preFileName(path));//先删除文件夹里面的文件
                }
                //文件或空文件夹删掉
                file.delete();
            }
            return rootFile.delete();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw UtilsException.fail(UtilsEnums.FILE_DELETE_FAIL,e);
        }
    }

    /**
     * 获取txt的编码格式
     * @param inputStream
     * @return
     */
    public static String getTxtFileCode(InputStream inputStream) {
            //GBK
            String code = "gb2312";
            try {
                byte[] head = new byte[3];
                inputStream.read(head);
                if (head[0] == -1 && head[1] == -2 ) {
                    code = "UTF-16";
                }else if (head[0] == -2 && head[1] == -1 ) {
                    code = "Unicode";
                }else if(head[0]==-17 && head[1]==-69 && head[2] ==-65) {
                    code = "UTF-8";
                } else if(head[0]==13 && head[1]==10 && head[2] ==45){
                    //这个格式是自己打断点打出来的，网上搜不到
                    code = "";
                }
            }catch (Exception e){
                log.error(e.getMessage());
            }
            return code;
    }

    /**
     * 根据文件路径获取文件总行数
     * @param inputStream
     * @return
     */
    public static Long getTotalLine(InputStream inputStream){
        try(Stream<String> lines= IOUtils.readLines(inputStream, StrConstant.DEFAULT_CHARSET).stream()){
            return lines.count();
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return 0L;
    }

    /**
     * 根据文件路径获取文件数据集合
     * @param inputStream
     * @return
     */
    public static List<String> getAllList(InputStream inputStream){
        try(Stream<String> lines= IOUtils.readLines(inputStream, StrConstant.DEFAULT_CHARSET).stream()){
            return lines.collect(Collectors.toList());
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return Collections.emptyList();
    }

    /**
     * 读取文件制定行数
     * @param inputStream
     * @param before 从哪一行开始
     * @param limit 跳过的行数
     * @return
     */
    public static List<String> getLineByLimit(InputStream inputStream,Long before,Long limit){
        try(Stream<String> lines= IOUtils.readLines(inputStream, StrConstant.DEFAULT_CHARSET).stream()){
            return lines.skip(before).limit(limit).collect(Collectors.toList());
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return Collections.emptyList();
    }

    /**
     * 读取文件制定行数
     * @param inputStream
     * @param start 从哪一行开始
     * @param end 到哪一行结束
     * @return
     */
    public static List<String> getLineByEnd(InputStream inputStream,int start,int end){
        try(Stream<String> lines= IOUtils.readLines(inputStream, StrConstant.DEFAULT_CHARSET).stream()){
            return lines.skip(start).limit(end-start).collect(Collectors.toList());
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return Collections.emptyList();
    }

    /**
     * 获取eppb文件
     * @param inputStream
     * @return
     */
    public static Book getEpubBook(InputStream inputStream) {
        try {
            EpubReader reader = new EpubReader();
            return reader.readEpub(inputStream);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw UtilsException.fail(UtilsEnums.READ_EPUB_FAIL,e);
        }
    }
}
