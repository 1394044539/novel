package wpy.personal.novel.pojo.bo;

import lombok.Data;

@Data
public class NovelFileBo {

    /**
     * 小说id
     */
    private String novelId;

    /**
     * 小说名
     */
    private String novelName;

    /**
     * 小说id
     */
    private String fileId;

    /**
     * 路径
     */
    private String filePath;

    /**
     * md5
     */
    private String fileMd5;

    /**
     * 类型
     */
    private String fileType;

    /**
     * 文件大小
     */
    private Long fileSize;
}
