package com.shareyi.molicode.common.utils;

import com.google.common.collect.Maps;
import com.shareyi.molicode.common.context.MoliCodeContext;

import java.util.Map;
import java.util.jar.JarFile;

/**
 * 线程变量持有器
 *
 * @author zhangshibin
 * @date 2018/10/11
 */
public class ThreadLocalHolder {

    private static ThreadLocal<MoliCodeContext> moliCodeContextThreadLocal = new ThreadLocal();

    /**
     * 前台请求
     */
    private static ThreadLocal<Map<String, Object>> requestThreadLocal = new ThreadLocal();


    public static MoliCodeContext getMoliCodeContext() {
        return moliCodeContextThreadLocal.get();
    }

    public static void setMoliCodeContext(MoliCodeContext context) {
        moliCodeContextThreadLocal.set(context);
    }

    public static void removeMoliCodeContext() {
        moliCodeContextThreadLocal.remove();
    }

    public static void setJarFileToCodeContext(JarFile jarFile) {
        MoliCodeContext context = moliCodeContextThreadLocal.get();
        if (context == null) {
            return;
        }
        context.setMavenJarFile(jarFile);
    }

    public static ThreadLocal<Map<String, Object>> getRequestThreadLocal() {
        return requestThreadLocal;
    }

    /**
     * 保存请求线程的线程信息
     *
     * @param key
     * @param value
     */
    public static void putRequestThreadInfo(String key, Object value) {
        Map<String, Object> context = requestThreadLocal.get();
        if (context == null) {
            context = Maps.newHashMap();
            requestThreadLocal.set(context);
        }
        context.put(key, value);
    }

    /**
     * 获取线程请求变量
     *
     * @param key
     */
    public static Object getRequestThreadInfo(String key) {
        Map<String, Object> context = requestThreadLocal.get();
        if (context == null) {
            return null;
        }
        return context.get(key);
    }

    /**
     * 清除请求变量
     */
    public static void clearRequestContext() {
        requestThreadLocal.remove();
    }
}
