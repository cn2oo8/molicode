package com.shareyi.molicode.common.chain;

/**
 * handlerChain
 * @param <T>
 */
public interface HandlerChain<T> {
    void handle(T context) throws Exception;
}
