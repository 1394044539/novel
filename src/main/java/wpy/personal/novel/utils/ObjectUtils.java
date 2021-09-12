package wpy.personal.novel.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import wpy.personal.novel.base.enums.SqlEnums;
import wpy.personal.novel.base.enums.UtilsEnums;
import wpy.personal.novel.base.exception.UtilsException;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 对象工具类
 */
@Slf4j
public class ObjectUtils {

    /**
     * 初始化对象
     * @param operationUserId
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T newInstance(String operationUserId,Class<T> tClass){
        try {
            T t = tClass.newInstance();
            Field isDelete = tClass.getDeclaredField("isDelete");
            isDelete.setAccessible(true);
            isDelete.set(t, SqlEnums.NOT_DELETE.getCode());
            Field createTime = tClass.getDeclaredField("createTime");
            createTime.setAccessible(true);
            createTime.set(t, new Date());
            Field createBy = tClass.getDeclaredField("createBy");
            createBy.setAccessible(true);
            createBy.set(t, operationUserId);
            Field updateTime = tClass.getDeclaredField("updateTime");
            updateTime.setAccessible(true);
            updateTime.set(t, new Date());
            Field updateBy = tClass.getDeclaredField("updateBy");
            updateBy.setAccessible(true);
            updateBy.set(t, operationUserId);
            return t;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw UtilsException.fail(UtilsEnums.REFLEX_FAIL,e);
        }
    }

    /**
     * 需要忽视的属性
     * @param src
     * @param target
     */
    public static void copyProperties(Object src,Object target){
        String[] properties=new String[]{"isDelete","createTime","createBy","updateTime","updateBy"};
        BeanUtils.copyProperties(src,target,properties);
    }

    /**
     * 获取对象中为null字段的集合
     * @param source
     * @return
     */
    public static String[] getNullPropertyNames (Object source) {
        BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * 复制不为空的属性,对外提供的方法
     * @param src
     * @param target
     */
    public static void copyPropertiesIgnoreNull(Object src, Object target){
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }
 }
