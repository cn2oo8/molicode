package com.shareyi.molicode.common.vo.code

import com.shareyi.molicode.common.vo.maven.MavenResourceVo;

class AutoMakeVo {
    /**
     * 文件输出目录
     */
    String projectOutputDir;
    /**
     * 模板列表
     */
    List<TemplateVo> templates;
    /**
     * moliCodeVersion适配版本号
     */
    String moliCodeVersion;

    /**
     * maven相关资源信息
     */
    MavenResourceVo mavenResourceVo;

    /**
     * 配置文件
     */
    Map<String, String> props = new HashMap();

    Map<String, String> moliTemplateMap = new HashMap<>();


    public void addProp(String key, String value) {
        props.put(key, value);
    }

    public String getProp(String key) {
        return props.get(key);
    }

    String getMoliTemplate(String key) {
        return moliTemplateMap.get(key);
    }

    AutoMakeVo putMoliTemplate(String key, String content) {
        moliTemplateMap.put(key, content);
        return this;
    }
}
