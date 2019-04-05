package com.shareyi.molicode.common.utils;

import com.google.common.collect.Maps;
import com.shareyi.molicode.common.enums.BrowserWindowEnum;
import com.shareyi.molicode.common.gui.BrowserCallbackCenter;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 单列模式
 *
 * @author zhangshibin
 * @date 2018/11/1
 */
public class Profiles {

    /**
     * 单例
     */
    final static Profiles instance = new Profiles();

    /**
     * 文件编码类型
     */
    @Value("${default.fileEncoding}")
    private String fileEncoding;
    /**
     * 版本号
     */
    @Value("${moliCode.version}")
    private String moliCodeVersion;
    /**
     * 浏览器窗口名称
     *
     * @see com.shareyi.molicode.common.enums.BrowserWindowEnum
     */
    @Value("${browser.windowName}")
    private String browserWindowName;
    /**
     * 浏览器回调中心
     */
    @Resource
    private BrowserCallbackCenter browserCallbackCenter;

    /**
     * 缓存map
     */
    private Map<String, Object> cacheMap = new ConcurrentHashMap<>();


    public String getFileEncoding() {
        return fileEncoding;
    }

    public void setFileEncoding(String fileEncoding) {
        this.fileEncoding = fileEncoding;
    }

    public String getMoliCodeVersion() {
        return moliCodeVersion;
    }

    public void setMoliCodeVersion(String moliCodeVersion) {
        this.moliCodeVersion = moliCodeVersion;
    }

    public String getBrowserWindowName() {
        return browserWindowName;
    }

    public void setBrowserWindowName(String browserWindowName) {
        this.browserWindowName = browserWindowName;
    }

    public BrowserCallbackCenter getBrowserCallbackCenter() {
        return browserCallbackCenter;
    }

    public void setBrowserCallbackCenter(BrowserCallbackCenter browserCallbackCenter) {
        this.browserCallbackCenter = browserCallbackCenter;
    }

    /**
     * 放置缓存信息
     *
     * @param key
     * @param data
     * @return
     */
    public Profiles putCache(String key, String data) {
        cacheMap.put(key, data);
        return this;
    }

    /**
     * 获取缓存信息
     *
     * @param key
     * @return
     */
    public Object getCache(String key) {
        return cacheMap.get(key);
    }

    public static Profiles getInstance() {
        return instance;
    }

    /**
     * 以MAP形式将profile相关信息返回到前台
     *
     * @return
     */
    public Map<String, Object> getProfileInfo() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("moliCodeVersion", moliCodeVersion);
        map.put("fileEncoding", fileEncoding);
        map.put("browserWindowName", browserWindowName);
        return map;
    }
}
