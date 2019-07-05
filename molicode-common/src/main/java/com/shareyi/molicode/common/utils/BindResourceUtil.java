package com.shareyi.molicode.common.utils;

import com.shareyi.fileutil.FileUtil;
import org.apache.commons.io.IOUtils;

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
                FileInputStream inStream = null;
                try {
                    inStream = new FileInputStream(file);
                    pro.load(inStream);
                } catch (Exception e) {
                } finally {
                    IOUtils.closeQuietly(inStream);
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
        if (pro == null) {
            return false;
        }
        String bindSrc = FileUtil.contactPath(FileUtil.getRuntimeFilePath("config/bindSource"), bindId + ".properties");
        File file = new File(bindSrc);
        FileUtil.makeSureFileExsit(file);
        FileOutputStream outputStream = null;
        if (!file.canWrite()) {
            return false;
        }

        try {
            outputStream = new FileOutputStream(file);
            pro.store(outputStream, "update config file");
        } catch (Exception e) {
            return false;
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
        return true;
    }


}
