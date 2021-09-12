package wpy.personal.novel.base.enums;

/**
 * 数据库字段枚举
 */
public enum SqlEnums {

    /**
     * 是否删除
     */
    NOT_DELETE("0","否"),
    IS_DELETE("1","是"),

    /**
     * 日志类型
     */
    CURRENCY_LOG("0","通用日志"),
    OTHER_LOG("1","其他日志"),

    /**
     * 字典选项
     */
    DICT_STRING("0","字符串"),
    DICT_BOOL("1","开关"),
    DICT_LIST("2","集合");


    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    private SqlEnums(String code){
        this.code=code;
        this.desc="暂无描述";
    }

    public String getDesc(){
        return desc;
    }

    private SqlEnums(String key, String desc){
        this.code=key;
        this.desc=desc;
    }
}
