package com.shareyi.molicode.common.lock;

/**
 * 锁定任务执行模板适配器
 *
 * @author david
 * @date 2019/1/23
 */
public abstract class LockExecuteAdapter<T> implements LockExecuteTemplate<T> {

    /**
     * 获取锁定失败消息
     *
     * @return
     */
    @Override
    public String getLockFailedMsg() {
        StringBuilder sb = new StringBuilder("操作正在处理，请稍后提交！");
        String bizMsg = this.getBizMsg();
        if (bizMsg != null) {
            sb.append(bizMsg);
        }
        return sb.toString();
    }

    /**
     * 返回业务相关消息，在锁定失败的时候拼接
     *
     * @return
     */
    public String getBizMsg() {
        return null;
    }
}
