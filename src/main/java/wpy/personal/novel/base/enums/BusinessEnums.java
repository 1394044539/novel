package wpy.personal.novel.base.enums;

/**
 * 业务枚举
 */
public enum BusinessEnums {

    /**
     * 是否删除
     */
    TXT("txt","TXT文件"),
    EPUB("epub","EPUB文件"),

    /**
     * 发送短信
     */
    SEND_MESSAGE("1","发送短信"),
    NOT_SEND_MESSAGE("0","不发送短信"),

    /**
     * 移动或复制
     */
    COPY("copy","复制"),
    MOVE("move","移动")
    ;

    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    private BusinessEnums(String code){
        this.code=code;
        this.desc="暂无描述";
    }

    public String getDesc(){
        return desc;
    }

    private BusinessEnums(String key, String desc){
        this.code=key;
        this.desc=desc;
    }
}
