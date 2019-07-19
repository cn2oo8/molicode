package com.shareyi.molicode.common.lock;

import com.google.common.base.Objects;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.shareyi.molicode.common.exception.DefaultExceptionMaker;
import com.shareyi.molicode.common.exception.MolicodeExceptionMaker;
import com.shareyi.molicode.common.utils.PubUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * redisLock执行器
 *
 * @author zhangshibin
 * @date 2019/1/23
 */
@Service
public class RedisLockExecutor {

    /**
     * 缓存短时间，10分钟
     */
    private Cache<String, Object> lockCache = CacheBuilder.newBuilder()
            .maximumSize(2000).expireAfterWrite(10, TimeUnit.MINUTES)
            .build();

    /**
     * 异常构建器,
     */
    private MolicodeExceptionMaker exceptionMaker = new DefaultExceptionMaker();

    /**
     * 执行操作
     *
     * @param lockKey
     * @param timeoutMSec         超时时间，毫秒
     * @param lockExecuteTemplate
     * @param <T>
     * @return
     */
    public <T> T execute(String lockKey, long timeoutMSec, LockExecuteTemplate<T> lockExecuteTemplate) throws Exception {
        String uuid = PubUtils.getUUID();
        boolean locked = false;

        try {
            locked = this.tryLock(lockKey, uuid, timeoutMSec);
            if (!locked) {
                String msg = lockExecuteTemplate.getLockFailedMsg();
                msg = StringUtils.isEmpty(msg) ? "操作正在处理，请稍后提交!" : msg;
                exceptionMaker.throwLockException(msg);
            }
            return lockExecuteTemplate.execute();
        } finally {
            if (locked) {
                this.unlock(lockKey, uuid);
            }
        }
    }

    /**
     * 解锁
     *
     * @param lockKey
     * @param uuid
     */
    private synchronized void unlock(String lockKey, String uuid) {
        Object value = lockCache.getIfPresent(lockKey);
        if (Objects.equal(value, uuid)) {
            lockCache.invalidate(lockKey);
        }
        //如果不等，就不解锁
    }

    /**
     * 执行任务锁定
     *
     * @param lockKey
     * @param uuid
     * @param timeoutMSec
     * @return
     */
    private synchronized boolean tryLock(String lockKey, String uuid, long timeoutMSec) {
        Object value = lockCache.getIfPresent(lockKey);
        if (value != null) {
            return false;
        }
        lockCache.put(lockKey, uuid);
        return true;
    }


}
