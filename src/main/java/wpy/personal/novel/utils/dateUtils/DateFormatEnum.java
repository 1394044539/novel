package wpy.personal.novel.utils.dateUtils;

public enum DateFormatEnum {

    YMD_DATE_ONE("yyyy-MM-dd"),
    YMDHMS_DATE_ONE("yyyy-MM-dd HH:mm:ss"),
    YMD_DATE_TWO("yyyy/MM/dd"),
    YMDHMS_DATE_TWO("yyyy/MM/dd HH:mm:ss");

    private String format;

    DateFormatEnum(){

    }

    DateFormatEnum(String format){
        this.format = format;
    }

    public String getFormat(){
        return this.format;
    }
}
