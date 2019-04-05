package com.shareyi.molicode.common.gui;

import javax.swing.*;

/**
 * 浏览器窗口
 *
 * @author david
 * @date 2018/9/23
 */
public interface BrowserWindow {

    /**
     * 初始化
     * @throws Exception
     */
    void init() throws Exception;

    /**
     * 打开窗口
     */
    void openWindow();

    /**
     * 关闭窗口
     */
    void closeWindow();

    /**
     * 提示信息
     * @param message
     */
    void alertMessage(String message);

    /**
     * 设置url
     * @param url
     */
    void setUrl(String url);

    /**
     * 获取顶层的jFrame
     * @return
     */
    JFrame getTopJFrame();
}
