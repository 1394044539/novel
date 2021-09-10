package wpy.personal.novel.utils;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
public class JsonUtils {

    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * 将jsonString转化为map<String,Object>
     */
    public static Map<String,Object> jsonStrToMap(String jsonStr){
        Map<String,Object> map= Maps.newHashMap();
        try {
            map=mapper.readValue(jsonStr, new TypeReference<Map<String, Object>>() {});
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(),e);
        }
        return map;
    }

    /**
     * 将jsonString转化为List<Map<String,Object>>
     * @param jsonStr
     * @return
     */
    public static List<Map<String,Object>> jsonToListMap(String jsonStr){
        try {
            return mapper.readValue(jsonStr, new TypeReference<List<Map<String, Object>>>() {});
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(),e);
        }
        return Collections.emptyList();
    }
}
