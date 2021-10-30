package wpy.personal.novel.utils.dateUtils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Slf4j
public class DateUtils {


    public static String strFormaterDate1(Date date){
        if(date == null){
            return "";
        }
        return DateFormatUtils.format(date,DateFormatEnum.YMD_DATE_ONE.getFormat());
    }

    public static String strFormaterDate2(Date date){
        if(date == null){
            return "";
        }
        return DateFormatUtils.format(date,DateFormatEnum.YMDHMS_DATE_ONE.getFormat());
    }

    public static String strFormaterDate3(Date date){
        if(date == null){
            return "";
        }
        return DateFormatUtils.format(date,DateFormatEnum.YMD_DATE_TWO.getFormat());
    }

    public static String strFormaterDate4(Date date){
        if(date == null){
            return "";
        }
        return DateFormatUtils.format(date,DateFormatEnum.YMDHMS_DATE_TWO.getFormat());
    }

    public static String strFormaterDateCustom(Date date, DateFormatEnum formatEnum){
        if(date == null){
            return "";
        }
        return DateFormatUtils.format(date,formatEnum.getFormat());
    }

    public static Date dateFormaterStr1(String str){
        SimpleDateFormat sdf=new SimpleDateFormat(DateFormatEnum.YMD_DATE_ONE.getFormat());
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            log.error(e.getMessage(),e);
        }
        return null;
    }

    public static Date dateFormaterStr2(String str){
        SimpleDateFormat sdf=new SimpleDateFormat(DateFormatEnum.YMDHMS_DATE_ONE.getFormat());
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            log.error(e.getMessage(),e);
        }
        return null;
    }

    public static Date dateFormaterStr3(String str){
        SimpleDateFormat sdf=new SimpleDateFormat(DateFormatEnum.YMD_DATE_TWO.getFormat());
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            log.error(e.getMessage(),e);
        }
        return null;
    }

    public static Date dateFormaterStr4(String str){
        SimpleDateFormat sdf=new SimpleDateFormat(DateFormatEnum.YMDHMS_DATE_TWO.getFormat());
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            log.error(e.getMessage(),e);
        }
        return null;
    }

    public static Date dateFormaterStrCustom(String str, DateFormatEnum formatEnum){
        SimpleDateFormat sdf=new SimpleDateFormat(formatEnum.getFormat());
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            log.error(e.getMessage(),e);
        }
        return null;
    }

    public static int getYear(){
        return DateTime.now().getYear();
    }

    public static int getMoth(){
        return DateTime.now().getMonthOfYear();
    }

    public static int getDay(){
        return DateTime.now().getDayOfYear();
    }

    public static Date getNowDate(){
        return DateTime.now().toDate();
    }

    public static Date getPlusDateTime(int d){
        return DateTime.now().plusDays(d).toDate();
    }

    public static Date getYesterDateTime(int d){
        return DateTime.now().minusDays(d).toDate();
    }

    public static Date getDateBefore(Date date, int day){
        Calendar now= Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.DATE,now.get(Calendar.DATE)-day);
        return now.getTime();
    }

    public static Date getDateAfter(Date date, int day){
        Calendar now= Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.DATE,now.get(Calendar.DATE)+day);
        return now.getTime();
    }

    public static Date getAllFormatDate(String value) {
        Date date=DateUtils.dateFormaterStr1(value);
        if(date==null){
            date=DateUtils.dateFormaterStr2(value);
        }
        if(date==null){
            date=DateUtils.dateFormaterStr3(value);
        }
        if(date==null){
            date=DateUtils.dateFormaterStr4(value);
        }
        return date;
    }

    /**
     * 增加一天
     * @param date
     * @return
     */
    public static Date addDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

    /**
     * 比较传入时间是否大于当前时间 a.after(b),a>b时返回true， 当a>=b , 则 !a<b
     * @param date
     * @return
     */
    public static boolean afterDateNow(Date date,boolean equals){
        return equals? !date.before(new Date()): date.after(new Date());
    }

    /**
     * 比较传入时间是否小于当前时间 a.before(b),a<b的时候返回true,当a<=b, 则 !a>b
     * @param date
     * @param equals
     * @return
     */
    public static boolean beforeDateNow(Date date,boolean equals){
        return equals? !date.after(new Date()):date.before(new Date());
    }
}
