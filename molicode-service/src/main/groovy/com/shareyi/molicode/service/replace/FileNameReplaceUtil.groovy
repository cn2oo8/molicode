package com.shareyi.molicode.service.replace

import org.apache.commons.lang3.StringUtils

import java.util.Map.Entry

public class FileNameReplaceUtil {

    public String replaceExp;
    public HashMap<String, String> replaceMap = new HashMap<String, String>();

    public FileNameReplaceUtil(String replaceExp) {
        this.replaceExp = replaceExp;
        this.init();
    }


    void init() {
        if (StringUtils.isEmpty(replaceExp)) {
            return;
        }

        String[] exps = replaceExp.split("[,，\\s\n]+");
        for (String exp : exps) {
            String[] replaces = exp.split("=");
            if (replaces.length > 1) {
                replaceMap.put(replaces[0], replaces[1]);
            }
        }
    }


    public String doReplace(String src) {
        String dest = src;
        Set<Entry<String, String>> set = replaceMap.entrySet();
        for (Entry<String, String> entry : set) {
            dest = StringUtils.replace(dest, entry.key, entry.value);
        }
        return dest;
    }

}
