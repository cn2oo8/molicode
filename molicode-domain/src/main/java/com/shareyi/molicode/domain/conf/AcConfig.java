/**
 * Copyright(c) 2004-2018 bianfeng
 */


package com.shareyi.molicode.domain.conf;

import com.shareyi.molicode.domain.BasicDomain;

import java.io.Serializable;

/**
 * @author david
 * @date 2018-08-22
 * 配置文件 Domain 类
 */
public class AcConfig extends BasicDomain implements Serializable {

    private static final long serialVersionUID = -6944585439125289530L;

    /**
     * 类型
     */
    private Integer type;
    /**
     * 项目key
     */
    private String projectKey;
    /**
     * 范围
     */
    private Integer scope;
    /**
     * 配置key
     */
    private String configKey;
    /**
     * 配置值
     */
    private String configValue;
    /**
     * 扩展1
     */
    private String ext1;
    /**
     * 扩展2
     */
    private String ext2;
    /**
     * 扩展3
     */
    private String ext3;


    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return this.type;
    }

    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }

    public String getProjectKey() {
        return this.projectKey;
    }

    public void setScope(Integer scope) {
        this.scope = scope;
    }

    public Integer getScope() {
        return this.scope;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getConfigKey() {
        return this.configKey;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public String getConfigValue() {
        return this.configValue;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt1() {
        return this.ext1;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public String getExt2() {
        return this.ext2;
    }

    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }

    public String getExt3() {
        return this.ext3;
    }

}
