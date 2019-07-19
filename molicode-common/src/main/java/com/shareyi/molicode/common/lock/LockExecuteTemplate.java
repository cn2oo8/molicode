package com.shareyi.molicode.common.lock;

/**
 * 锁定任务执行模板
 *
 * @author david
 * @date 2019/1/23
 */
public interface LockExecuteTemplate<T> {
    /**
     * 执行处理，并返回结果
     *
     * @return
     */
    T execute() throws Exception;

    /**
     * 获取锁定失败消息
     *
     * @return
     */
    String getLockFailedMsg();
}
