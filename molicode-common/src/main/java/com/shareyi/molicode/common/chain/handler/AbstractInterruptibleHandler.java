package com.shareyi.molicode.common.chain.handler;

import com.shareyi.molicode.common.chain.HandlerChain;

/**
 * 可中断的 handler抽象类
 * <p>
 * 如果 doHandle 返回false, 则中断整个责任链
 *
 * @author david
 * @since 2018/5/12
 */
public abstract class AbstractInterruptibleHandler<T> implements Handler<T> {

    @Override
    public void handle(T t, HandlerChain<T> handlerChain) throws Exception {
        boolean shouldContinue = true;
        if (this.shouldHandle(t)) {
            shouldContinue = this.doHandle(t);
        }
        if (handlerChain != null && shouldContinue) {
            handlerChain.handle(t);
        }
    }

    /**
     * 是否本handler 应该执行处理
     * 默认是需要，子类可更改逻辑，跳过本处理器
     *
     * @return 是否本handler 应该执行处理
     */
    protected boolean shouldHandle(T t) {
        return true;
    }

    /**
     * 执行处理
     *
     * @param t 参数
     * @return 是否需要继续处理下个handler
     * @throws Exception
     */
    protected abstract boolean doHandle(T t) throws Exception;
}
