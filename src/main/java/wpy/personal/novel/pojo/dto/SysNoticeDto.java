package wpy.personal.novel.pojo.dto;

import lombok.Data;

@Data
public class SysNoticeDto extends BasePageDto{

    /**
     * 主键id
     */
    private String noticeId;

    /**
     * 公告标题
     */
    private String noticeTitle;

    /**
     * 首页展示(0:不展示,1:展示)
     */
    private String mainShow;

    /**
     * 是否发布(0:否,1:是)
     */
    private String isPublic;
}
