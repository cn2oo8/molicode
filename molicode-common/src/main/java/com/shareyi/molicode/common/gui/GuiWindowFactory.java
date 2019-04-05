package com.shareyi.molicode.common.gui;

import com.shareyi.molicode.common.enums.BrowserWindowEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 窗口构建器工厂
 *
 * @author david
 * @date 2018/9/23
 */
public class GuiWindowFactory {

    private static Logger LOGGER = LoggerFactory.getLogger(GuiWindowFactory.class);

    /**
     * 单例
     */
    private static GuiWindowFactory instance = new GuiWindowFactory();

    /**
     * window 窗口
     */
    BrowserWindow browserWindow;

    /**
     * 创建window
     *
     * @param windowName
     * @return
     */
    public GuiWindowFactory createWindow(String windowName) {
        if(browserWindow != null){
            return this;
        }

        BrowserWindowEnum windowEnum  = BrowserWindowEnum.getByCode(windowName);
        BrowserWindow browserWindow = null;
        switch (windowEnum){
            case JX_BROWSER:
                browserWindow = new JXBrowserSwingWindow();
            break;
            default:
              browserWindow = new SwingBrowserWindow();

        }
        getInstance().browserWindow = browserWindow;
        return this;
    }

    /**
     * 初始化并打开窗口
     *
     * @param url
     * @return
     */
    public GuiWindowFactory initAndOpen(String url) {
        try {
            browserWindow.init();
            browserWindow.openWindow();
            browserWindow.setUrl(url);
        } catch (Exception e) {
            LOGGER.error("初始化窗口失败, url={}", url, e);
            //TODO 跑出异常
        }
        return this;
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static GuiWindowFactory getInstance() {
        return instance;
    }

    public BrowserWindow getBrowserWindow() {
        return browserWindow;
    }


}
