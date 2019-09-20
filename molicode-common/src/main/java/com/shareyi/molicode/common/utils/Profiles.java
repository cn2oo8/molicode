package com.shareyi.molicode.common.utils;

import com.google.common.collect.Maps;
import com.shareyi.molicode.common.enums.BrowserWindowEnum;
import com.shareyi.molicode.common.gui.BrowserCallbackCenter;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 单列模式
 *
 * @author david
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
     * 服务器安全等级
     * 1 初级，权限较大；
     * 2.中级，权限中度；
     * 3.严格，权限极小；
     */
    @Value("${server.safe.level}")
    private Integer serverSafeLevel;
    /**
     * 浏览器回调中心
     */
    @Resource
    private BrowserCallbackCenter browserCallbackCenter;

    /**
     * 缓存map
     */
    private Map<String, Object> cacheMap = new ConcurrentHashMap<>();

    /**
     * 基础工程仓库
     */
    @Value("${repo.url.baseProjectRepos}")
    private String baseProjectReposUrl;
    /**
     * 模板仓库
     */
    @Value("${repo.url.templateRepos}")
    private String templateReposUrl;

    @Value("${register.default.roleCode}")
    private String registerRoleCode;


    @Value("${profile.simpleReplace:true}")
    private boolean simpleReplace;

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

    public String getBaseProjectReposUrl() {
        return baseProjectReposUrl;
    }

    public void setBaseProjectReposUrl(String baseProjectReposUrl) {
        this.baseProjectReposUrl = baseProjectReposUrl;
    }

    public String getTemplateReposUrl() {
        return templateReposUrl;
    }

    public void setTemplateReposUrl(String templateReposUrl) {
        this.templateReposUrl = templateReposUrl;
    }


    public Integer getServerSafeLevel() {
        return serverSafeLevel;
    }

    public void setServerSafeLevel(Integer serverSafeLevel) {
        this.serverSafeLevel = serverSafeLevel;
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
        map.put("baseProjectReposUrl", baseProjectReposUrl);
        map.put("templateReposUrl", templateReposUrl);
        return map;
    }

    /**
     * 是否为headless状态
     *
     * @return
     */
    public boolean isHeadLess() {
        return Objects.equals(browserWindowName, BrowserWindowEnum.HEADLESS.getCode());
    }

    public String getRegisterRoleCode() {
        return registerRoleCode;
    }

    public void setRegisterRoleCode(String registerRoleCode) {
        this.registerRoleCode = registerRoleCode;
    }

    public boolean isSimpleReplace() {
        return simpleReplace;
    }

    public void setSimpleReplace(boolean simpleReplace) {
        this.simpleReplace = simpleReplace;
    }
}
