package wpy.personal.novel.base.enums;

public enum  ParamEnums {

    /**
     * 是否删除
     */
    IS_DELETE("0","未删除"),
    NOT_DELETE("1","删除");

    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    private ParamEnums(String code){
        this.code=code;
        this.desc="暂无描述";
    }

    public String getDesc(){
        return desc;
    }

    private ParamEnums(String key, String desc){
        this.code=key;
        this.desc=desc;
    }
}
