package com.shareyi.molicode.service.common.impl;

import com.google.common.util.concurrent.RateLimiter;
import com.shareyi.molicode.service.common.RateLimiterProvider;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * 限流服务
 *
 * @author zhangshibin
 * @date 2019/7/5
 */
@Service
public class RateLimiterProviderImpl implements RateLimiterProvider, InitializingBean {

    /**
     * 注册限流服务
     */
    private RateLimiter registerRateLimiter;

    @Override
    public RateLimiter getRegisterRateLimiter() {
        return registerRateLimiter;
    }

    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //每分钟最多30次
        registerRateLimiter = RateLimiter.create(0.4);
    }
}
