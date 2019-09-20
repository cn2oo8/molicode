package com.shareyi.molicode.common.chain;


import com.shareyi.molicode.common.chain.handler.Handler;
import com.shareyi.molicode.common.enums.ResultCodeEnum;
import com.shareyi.molicode.common.exception.AutoCodeException;
import com.shareyi.molicode.common.utils.LogHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;


/**
 * 过滤执行器
 *
 * @author david
 * @date 2017/9/28
 */
public class HandlerChainExecutor {

    /**
     * 执行过滤器，外部传入filterList
     *
     * @param t
     * @param handlerList
     */
    public static  <T> void execute(T t, List<Handler<T>> handlerList) throws Exception {
        if (CollectionUtils.isEmpty(handlerList)) {
            LogHelper.DEFAULT.warn("filterList为空，data=[{}]",t);
            return;
        }
        HandlerChain<T> handlerChain = new DefaultHandlerChain<T>(handlerList);
        handlerChain.handle(t);
    }

    /**
     * 执行过滤器，外部传入filterList
     *
     * @param t
     * @param handlerList
     */
    public static  <T> void executeSilent(T t, List<Handler<T>> handlerList) throws AutoCodeException {
        try {
            execute(t,handlerList);
        }catch (AutoCodeException ee){
            throw ee;
        }catch (Exception e) {
            LogHelper.EXCEPTION.error("执行处理器异常",e);
            throw new AutoCodeException("异常,原因是："+e.getMessage(), ResultCodeEnum.EXCEPTION.codeString());
        }
    }

    /**
     * 执行过滤器，handlerChain
     *
     * @param t
     * @param handlerChain
     */
    public static  <T> void executeSilent(T t, HandlerChain<T> handlerChain) throws AutoCodeException {
        try {
            handlerChain.handle(t);
        }catch (AutoCodeException ee){
            throw ee;
        }catch (Exception e) {
            LogHelper.EXCEPTION.error("执行处理器异常",e);
            throw new AutoCodeException("异常,原因是："+e.getMessage(), ResultCodeEnum.EXCEPTION.codeString());
        }
    }

}
