package com.shareyi.molicode.common.chain.handler;


import com.shareyi.molicode.common.chain.HandlerChain;

/**
 * 简单的处理器
 *
 * @author david
 * @date 2018/2/2
 */
public abstract class SimpleHandler<T> implements Handler<T> {


    @Override
    public void handle(T t, HandlerChain<T> handlerChain) throws Exception {
        if(shouldHandle(t)){
            this.doHandle(t);
        }
        if(handlerChain != null){
            handlerChain.handle(t);
        }
    }

    /**
     * 是否应该执行处理
     * @param t 数据
     * @return 是否应该执行
     */
    public abstract boolean shouldHandle(T t);


    /**
     * 执行handler操作
     * @param t 数据
     */
    public abstract void doHandle(T t);
}
