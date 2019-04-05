package com.shareyi.molicode.common.chain;

import com.shareyi.molicode.common.chain.handler.Handler;

import java.util.List;

public class DefaultHandlerChain<T> implements HandlerChain<T> {
    private int index = 0;
    private final List<? extends Handler<T>> handlers;

    public DefaultHandlerChain(List<? extends Handler<T>> handlers) {
        this.handlers = handlers;
    }

    public void handle(T t) throws Exception {
        Handler<T> next = this.getNext();
        if (next != null) {
            next.handle(t, this);
        }

    }

    protected Handler<T> getNext() {
        return this.handlers != null && this.index < this.handlers.size() ? (Handler)this.handlers.get(this.index++) : null;
    }
}
