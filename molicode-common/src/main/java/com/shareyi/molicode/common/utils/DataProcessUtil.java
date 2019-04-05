package com.shareyi.molicode.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

/**
 * 数据处理工具类
 *
 * @author zhangshibin
 * @date 2018/10/7
 */
public class DataProcessUtil {

    /**
     * 转换为JSONObject
     * @param json
     * @return
     */
    public static JSONObject parseJSONObject(String json){
        if(StringUtils.isEmpty(json)){
            return null;
        }
        return JSON.parseObject(json);
    }

    /**
     * 转换为JSONArray
     * @param json
     * @return
     */
    public static JSONArray parseJSONArray(String json){
        if(StringUtils.isEmpty(json)){
            return null;
        }
        return JSON.parseArray(json);
    }


}
