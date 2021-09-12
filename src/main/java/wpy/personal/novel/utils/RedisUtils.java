package wpy.personal.novel.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import wpy.personal.novel.base.enums.UtilsEnums;
import wpy.personal.novel.base.exception.UtilsException;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 13940
 */

@Component
@Slf4j
public class RedisUtils {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 静态的RedisTemplate
     */
    private static RedisTemplate redisTemplateStatic;

    @PostConstruct
    public void init(){
        redisTemplateStatic = redisTemplate;
    }

    /**
     * 给一个指定的 key 值附加过期时间
     *
     * @param key
     * @param time
     * @return
     */
    public static boolean expire(String key, long time) {
        try {
            return redisTemplateStatic.expire(key, time, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw UtilsException.fail(UtilsEnums.REDIS_ERROR,e);
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key
     * @return
     */
    public static long getTime(String key) {
        try {
            return redisTemplateStatic.getExpire(key, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw UtilsException.fail(UtilsEnums.REDIS_ERROR,e);
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key
     * @return
     */
    public static boolean hasKey(String key) {
        try {
            return redisTemplateStatic.hasKey(key);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw UtilsException.fail(UtilsEnums.REDIS_ERROR,e);
        }
    }

    /**
     * 移除指定key 的过期时间
     *
     * @param key
     * @return
     */
    public static boolean persist(String key) {
        try {
            return redisTemplateStatic.boundValueOps(key).persist();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw UtilsException.fail(UtilsEnums.REDIS_ERROR,e);
        }
    }

    /**
     * 删除指定key
     * @param key
     */
    public static void delete(String key){
        try {
            redisTemplateStatic.delete(key);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw UtilsException.fail(UtilsEnums.REDIS_ERROR,e);
        }
    }

    /**
     * 设定对象
     * @param key
     * @param object
     */
    public static void setObject(String key, Object object){
        try {
            redisTemplateStatic.opsForValue().set(key,object);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw UtilsException.fail(UtilsEnums.REDIS_ERROR,e);
        }
    }

    /**
     * 设定对象带时间
     * @param key
     * @param object
     * @param time 秒数
     */
    public static void setObject(String key, Object object,long time){
        try {
            redisTemplateStatic.opsForValue().set(key,object,time,TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw UtilsException.fail(UtilsEnums.REDIS_ERROR,e);
        }
    }

    /**
     * 获取key
     * @param key
     */
    public static Object getObject(String key){
        try {
            return redisTemplateStatic.opsForValue().get(key);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw UtilsException.fail(UtilsEnums.REDIS_ERROR,e);
        }
    }

    /**
     * 获取原来的值并且重新赋值
     * @param key
     * @param object
     */
    public static void setAndSetObject(String key, Object object){
        try {
            redisTemplateStatic.opsForValue().getAndSet(key,object);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw UtilsException.fail(UtilsEnums.REDIS_ERROR,e);
        }
    }

    /**
     * 不存在则新建，存在不改变原有的值
     * @param key
     * @param object
     */
    public static void setSetIfAbsent(String key, Object object){
        try {
            redisTemplateStatic.opsForValue().setIfAbsent(key,object);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw UtilsException.fail(UtilsEnums.REDIS_ERROR,e);
        }
    }

    /**
     * 设置map
     * @param map
     */
    public static void setMap(Map<Object, Object> map){
        try {
            redisTemplateStatic.opsForValue().multiSet(map);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw UtilsException.fail(UtilsEnums.REDIS_ERROR,e);
        }
    }

    /**
     * 获得参数
     * @param key
     * @return
     */
    public static Object getMap(String key){
        try {
            return redisTemplateStatic.opsForValue().get(key);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw UtilsException.fail(UtilsEnums.REDIS_ERROR,e);
        }
    }

    /**
     * 设定带key的map
     * @param key
     * @param map
     */
    public static void setMap(String key, Map<Object, Object> map){
        try {
            redisTemplateStatic.opsForHash().putAll(key,map);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw UtilsException.fail(UtilsEnums.REDIS_ERROR,e);
        }
    }

    /**
     * 设置带key的map中的某一个key的值
     * @param key
     * @param mapKey
     * @param value
     */
    public static void setMapByKey(String key, Object mapKey, Object value){
        try {
            redisTemplateStatic.opsForHash().put(key,mapKey,value);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw UtilsException.fail(UtilsEnums.REDIS_ERROR,e);
        }
    }

    /**
     * 获取指定key下面的Map
     * @param key
     */
    public static Map<Object, Object> getAllMap(String key){
        try {
            return redisTemplateStatic.opsForHash().entries(key);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw UtilsException.fail(UtilsEnums.REDIS_ERROR,e);
        }
    }

    /**
     * 获取指定key下面的map的指定key的value
     * @param keyName
     * @param mapKey
     */
    public static Object getMapByKey(String keyName, String mapKey){
        try {
            return redisTemplateStatic.opsForHash().get(keyName, mapKey);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw UtilsException.fail(UtilsEnums.REDIS_ERROR,e);
        }
    }


}
