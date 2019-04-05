package com.shareyi.molicode.common.chain.handler;

import com.shareyi.molicode.common.chain.HandlerChain;

/**
 * handler 处理器
 * @param <T>
 */
public interface Handler<T> {
    void handle(T context, HandlerChain<T> handlerChain) throws Exception;
}
