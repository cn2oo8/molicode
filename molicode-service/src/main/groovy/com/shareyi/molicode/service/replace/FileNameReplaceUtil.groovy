package com.shareyi.molicode.service.replace

import com.shareyi.molicode.common.utils.LogHelper
import org.apache.commons.collections4.MapUtils
import org.apache.commons.lang3.StringUtils

import java.util.Map.Entry

class FileNameReplaceUtil {

    /**
     * 表达式
     */
    public String replaceExp;
    /**
     * 转换后的替换信息
     */
    public HashMap<String, String> replaceMap = new HashMap<String, String>();

    FileNameReplaceUtil(String replaceExp) {
        this.replaceExp = replaceExp;
        this.init();
    }


    void init() {
        if (StringUtils.isEmpty(replaceExp)) {
            return;
        }
        try {
            String[] exps = replaceExp.split("[,，\\s\n]+");
            for (String exp : exps) {
                String[] replaces = exp.split("=");
                if (replaces.length > 1) {
                    replaceMap.put(replaces[0], replaces[1]);
                }
            }
        } catch (Exception e) {
            LogHelper.EXCEPTION.error("转换表达式异常,exp=" + replaceExp, e);
        }

    }


    String doReplace(String src) {
        String dest = src;
        if (StringUtils.isEmpty(dest) || MapUtils.isEmpty(replaceMap)) {
            return dest;
        }
        Set<Entry<String, String>> set = replaceMap.entrySet();
        for (Entry<String, String> entry : set) {
            if (StringUtils.isNotEmpty(entry.getKey()) && entry.getValue() != null) {
                dest = StringUtils.replaceAll(dest, entry.key, entry.value);
            }
        }
        return dest;
    }

}
