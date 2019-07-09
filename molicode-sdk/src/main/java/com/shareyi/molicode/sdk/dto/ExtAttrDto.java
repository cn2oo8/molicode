package com.shareyi.molicode.sdk.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 基础扩展信息dto
 *
 * @author david
 * @date 2018/1/10
 */
public class ExtAttrDto implements Serializable {

    /**
     * 扩展信息map
     */
    private Map<String, Object> extInfoMap;

    /**
     * 获取扩展属性map
     *
     * @return 扩展map
     */
    public Map<String, Object> getExtInfoMap() {
        return extInfoMap;
    }

    /**
     * 设置扩展属性Map
     *
     * @param extInfoMap 扩展map
     */
    public void setExtInfoMap(Map<String, Object> extInfoMap) {
        this.extInfoMap = extInfoMap;
    }

    /**
     * 设置扩展属性信息
     *
     * @param key   key
     * @param value 值
     * @return 本身
     */
    public ExtAttrDto putExtInfo(String key, Object value) {
        if (extInfoMap == null) {
            extInfoMap = new HashMap();
        }
        extInfoMap.put(key, value);
        return this;
    }

    /**
     * 获取object类型的扩展信息
     *
     * @param key key
     * @return 获取扩展object
     */
    public Object getExtObject(String key) {
        return getExtObject(key, null);
    }

    /**
     * 获取泛型类型的扩展信息
     *
     * @param key   key
     * @param clazz 数据类型
     * @return 获取扩展value
     */
    public <T> T getExtValue(String key, Class<T> clazz) {
        Object object = getExtObject(key, null);
        if (object != null) {
            return (T) object;
        }
        return null;
    }

    /**
     * 获取object类型的扩展信息
     *
     * @param key    key
     * @param defVal 默认值
     * @return 获取扩展object
     */
    public Object getExtObject(String key, Object defVal) {
        if (extInfoMap == null) {
            return null;
        }
        Object value = extInfoMap.get(key);
        return value == null ? defVal : value;
    }

    /**
     * 获取字符串类型的扩展信息
     *
     * @param key key
     * @return 获取扩展ext字符串
     */
    public String getExtStr(String key) {
        return getExtStr(key, null);
    }

    /**
     * 获取字符串类型的扩展信息
     *
     * @param key    key
     * @param defVal 默认参数
     * @return 扩展字符串
     */
    public String getExtStr(String key, String defVal) {
        Object obj = getExtObject(key, defVal);
        return obj == null ? defVal : obj.toString();
    }
}
