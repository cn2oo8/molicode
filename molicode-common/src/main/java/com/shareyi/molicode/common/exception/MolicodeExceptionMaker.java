package com.shareyi.molicode.common.exception;

/**
 * 用户平台异常构建器
 *
 * @author zhangshibin
 * @date 2019/4/3
 */
public interface MolicodeExceptionMaker {

    /**
     * 抛出用户平台锁定异常
     */
    void throwLockException(String msg);

    /**
     * 构建加锁异常
     *
     * @param message
     * @return
     */
    RuntimeException makeLockException(String message);
}
