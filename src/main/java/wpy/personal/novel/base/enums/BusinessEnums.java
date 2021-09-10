package wpy.personal.novel.base.enums;

/**
 * 业务枚举
 */
public enum BusinessEnums {

    /**
     * 是否删除
     */
    IS_DELETE("0","否"),
    NOT_DELETE("1","是");

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
