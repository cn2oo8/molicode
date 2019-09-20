package com.shareyi.molicode.common.gui;

import com.shareyi.molicode.common.web.CommonResult;

/**
 * 浏览器回调监听器
 *
 * @author david
 * @date 2018/11/1
 */
public interface BrowserCallbackListener {

    /**
     * 处理数据
     * @param payload
     * @return
     */
    CommonResult<Object> handle(String payload);


    /**
     * 获取action名称
     * @return
     */
    String getAction();
}
