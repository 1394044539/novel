package wpy.personal.novel.utils;

public class NumberUtils {

    /**
     * 加法
     * @param numbers
     * @return
     */
    public static Integer add(Integer ...numbers){
        int sum = 0;
        for (Integer number : numbers) {
            if(number==null){
                continue;
            }
            sum+=number;
        }
        return sum;
    }

    public static Long add(Long ...numbers){
        long sum = 0;
        for (Long number : numbers) {
            if(number==null){
                continue;
            }
            sum+=number;
        }
        return sum;
    }
}
