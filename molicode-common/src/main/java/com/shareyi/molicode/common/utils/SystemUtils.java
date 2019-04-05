package com.shareyi.molicode.common.utils;

/**
 * 系统工具类
 *
 * @author zhangshibin
 * @date 2018/11/2
 */
public class SystemUtils {

    public static String getOsName(){
        String osName = System.getProperties().getProperty("os.name");
        return osName;
    }

    public static boolean isWindows() {
        String osName = getOsName();
        if(osName!=null){
            return osName.toUpperCase().indexOf("WINDOWS") != -1;
        }
        return false;
    }
}
