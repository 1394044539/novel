package wpy.personal.novel.utils;

import com.alibaba.fastjson.JSON;

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class ListUtils {

    /**
     * 用于对象去重
     * @param keyExtractor 需要去重的属性
     * @param <T>
     * @return
             */
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        //记录已有对象或者属性
        ConcurrentSkipListMap<Object,Boolean> skipListMap = new ConcurrentSkipListMap<>();
        //获取对象的属性值,且使用putIfAbsent判断存在则不添加到map而且返回数值不存在则添加返回null,value恒定为true
        //JSONObject.toJSONString(keyExtractor.apply(t)) 是为了解决null参数和对象比较的问题
        //在Stream distinct()中使用了支持null为key的hashSet来进行处理 java/util/stream/DistinctOps.java:90  但是没有解决对象比较的问题
        //所以虽然序列化消耗性能但是也没有更好的办法
        Predicate<T> predicate = t -> skipListMap.putIfAbsent(JSON.toJSONString(keyExtractor.apply(t)), Boolean.TRUE) == null;
        return predicate;
    }
}
