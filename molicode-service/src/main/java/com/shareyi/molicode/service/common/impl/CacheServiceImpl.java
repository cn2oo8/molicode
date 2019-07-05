package com.shareyi.molicode.service.common.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.shareyi.molicode.service.common.CacheService;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 缓存服务实现
 *
 * @author zhangshibin
 * @date 2019/7/5
 */
@Service("guavaCacheService")
public class CacheServiceImpl implements CacheService {

    /**
     * 缓存短时间，10分钟
     */
    private Cache<String, Object> shortTimeCache = CacheBuilder.newBuilder()
            .maximumSize(2000).expireAfterWrite(10, TimeUnit.MINUTES)
            .build();


    @Override
    public void saveShortTime(String key, Object value) {
        shortTimeCache.put(key, value);
    }

    @Override
    public Object getShortTime(String key) {
        return shortTimeCache.getIfPresent(key);
    }

    @Override
    public void deleteShortTimeKey(String key) {
        shortTimeCache.invalidate(key);
    }
}
