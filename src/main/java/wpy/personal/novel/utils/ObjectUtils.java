package wpy.personal.novel.utils;

import lombok.extern.slf4j.Slf4j;
import wpy.personal.novel.base.enums.SqlEnums;
import wpy.personal.novel.base.enums.UtilsEnums;
import wpy.personal.novel.base.exception.UtilsException;

import java.lang.reflect.Field;
import java.util.Date;

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
            isDelete.set(t, SqlEnums.NOT_DELETE.getCode());
            Field createTime = tClass.getDeclaredField("createTime");
            createTime.set(t, new Date());
            Field createBy = tClass.getDeclaredField("createBy");
            createBy.set(t, operationUserId);
            Field updateTime = tClass.getDeclaredField("updateTime");
            updateTime.set(t, new Date());
            Field updateBy = tClass.getDeclaredField("updateBy");
            updateBy.set(t, operationUserId);
            return t;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw UtilsException.fail(UtilsEnums.REFLEX_FAIL,e);
        }
    }
 }
