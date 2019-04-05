package com.shareyi.molicode.common.chain.handler;

/**
 * 责任链识别
 *
 * @author zhangshibin
 * @date 2018/8/7
 */
public interface HandlerAware {

    /**
     * 获取顺序，以此来进行排序
     *
     * @return
     */
    int getOrder();

}
