package com.shareyi.molicode.common.utils;

import com.shareyi.fileutil.FileUtil;
import com.shareyi.joywindow.JoyWindowData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Properties;


public abstract class BindResourceUtil {

    private static HashMap<String, Properties> resourceMap = new HashMap<String, Properties>();

    /**
     * 获取配置资源
     *
     * @param bindId
     * @return
     */
    public static Properties getBindProperties(String bindId) {
        return getBindProperties(bindId, true);
    }

    /**
     * 获取配置资源
     *
     * @param bindId
     * @param cache  是否缓存
     * @return
     */
    public static Properties getBindProperties(String bindId, boolean cache) {
        Properties pro = resourceMap.get(bindId);
        if (pro == null || !cache) {
            String bindSrc = FileUtil.contactPath(FileUtil.getRuntimeFilePath("config/bindSource"), bindId + ".properties");
            File file = new File(bindSrc);
            if (file.exists() && file.isFile() && file.canRead()) {
                pro = new Properties();
                try {
                    pro.load(new FileInputStream(file));
                } catch (Exception e) {
                }
            }

            resourceMap.put(bindId, pro);
        }
        return pro;
    }


    /**
     * 存储配置文件
     *
     * @param bindId
     */
    public static boolean saveBindProperties(String bindId, Properties pro) {
        if (pro == null) {
            pro = resourceMap.get(bindId);
        } else {
            resourceMap.put(bindId, pro);
        }

        if (pro != null) {
            String bindSrc = FileUtil.contactPath(FileUtil.getRuntimeFilePath("config/bindSource"), bindId + ".properties");
            File file = new File(bindSrc);
            FileUtil.makeSureFileExsit(file);
            if (file.canWrite()) {
                try {
                    pro.store(new FileOutputStream(file), "update config file");
                } catch (Exception e) {
                    return false;
                }
            } else {
                return false;
            }

        } else return false;


        return true;
    }

    /**
     * 获取配置资源
     *
     * @param bindId
     * @return
     */
    public static Properties getProperties(String bindId) {
        Properties pro = null;
        String bindSrc = FileUtil.contactPath(FileUtil.getRunPath(), bindId + ".properties");
        File file = new File(bindSrc);
        if (file.exists() && file.isFile() && file.canRead()) {
            pro = new Properties();
            try {
                pro.load(new FileInputStream(file));
            } catch (Exception e) {
            }
        }
        return pro;
    }


}
