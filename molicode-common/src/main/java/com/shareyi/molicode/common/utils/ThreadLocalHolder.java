package com.shareyi.molicode.common.utils;

import com.shareyi.molicode.common.context.MoliCodeContext;

import java.util.jar.JarFile;

/**
 * 线程变量持有器
 *
 * @author zhangshibin
 * @date 2018/10/11
 */
public class ThreadLocalHolder {

    private static ThreadLocal<MoliCodeContext> moliCodeContextThreadLocal = new ThreadLocal();

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
}
