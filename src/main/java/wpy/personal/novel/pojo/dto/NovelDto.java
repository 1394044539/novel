package wpy.personal.novel.pojo.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 小说dto
 */
@Data
public class NovelDto extends BasePageDto implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    private String novelId;

    /**
     * 作者
     */
    private String novelAuthor;

    /**
     * 发布日期
     */
    private Date publicTime;

    /**
     * 小说名
     */
    private String novelName;

    /**
     * 总行数
     */
    private Integer totalLine;

    /**
     * 总字数
     */
    private Long totalWord;

    /**
     * 封面
     */
    private String novelImg;

    /**
     * 小说描述
     */
    private String novelDesc;

    /**
     * 小说介绍
     */
    private String novelIntroduce;

    /**
     * 小说热度
     */
    private Long novelHot;

    /**
     * 小说点赞次数
     */
    private Long novelUp;

    /**
     * 小说点击次数
     */
    private Long novelClick;

    /**
     * 小说评论次数
     */
    private Long novelComment;

    /**
     * 小说分享次数
     */
    private Long novelShare;

    /**
     * 小说收藏次数
     */
    private Long novelCollect;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 0：否；1：是
     */
    private String isDelete;

    /**
     * 小说类型
     */
    private List<String> typeCodeList;

    /**
     * 小说封面
     */
    private MultipartFile imgFile;
}
