package com.shareyi.molicode.common.gui;

/**
 * 浏览器回调注册中心
 *
 * @author zhangshibin
 * @date 2018/10/29
 */
public interface BrowserCallbackCenter {

    /**
     * 回调
     *
     * @param action
     * @param payload
     */
    String callback(String action, String payload);


    /**
     * 新增回调监听器
     *
     * @param listener
     */
    void addListener(BrowserCallbackListener listener);
}
