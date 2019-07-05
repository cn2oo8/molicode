package com.shareyi.molicode.service.common;

import com.google.common.util.concurrent.RateLimiter;

/**
 * 限流供应器
 *
 * @author zhangshibin
 * @date 2019/7/5
 */
public interface RateLimiterProvider {

    /**
     * 获取登录使用的限流器
     *
     * @return
     */
    RateLimiter getRegisterRateLimiter();
}
