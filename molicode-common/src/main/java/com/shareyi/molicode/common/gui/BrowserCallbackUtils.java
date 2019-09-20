package com.shareyi.molicode.common.gui;

import com.shareyi.molicode.common.utils.LogHelper;

/**
 * 回调
 *
 * @author david
 * @date 2018/10/29
 */
public class BrowserCallbackUtils {

    /**
     * 单例
     */
    private static BrowserCallbackUtils instance = new BrowserCallbackUtils();

    /**
     * 回调
     * @param action
     * @param payload
     */
    public void callback(String action, String payload) {
        LogHelper.DEFAULT.info("action={}, payload={}", action, payload);
    }

    public static BrowserCallbackUtils getInstance() {
        return instance;
    }
}
