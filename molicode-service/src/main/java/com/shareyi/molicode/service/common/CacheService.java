package com.shareyi.molicode.service.common;

/**
 * 缓存服务
 *
 * @author david
 * @date 2019/7/5
 */
public interface CacheService {

    /**
     * 保存短时间
     *
     * @param key
     * @param value
     */
    void saveShortTime(String key, Object value);

    /**
     * 从短时间缓存获取数据
     *
     * @param key
     */
    Object getShortTime(String key);

    /**
     * 清理缓存
     * @param key
     */
    void deleteShortTimeKey(String key);
}
