/**
 * Copyright(c) 2004-2018 bianfeng
 */


package com.shareyi.molicode.domain.conf;

import com.shareyi.molicode.domain.BasicDomain;

import java.io.Serializable;

/**
 * @author david
 * @date 2018-08-25
 * 通用扩展信息 Domain 类
 */
public class CommonExtInfo extends BasicDomain implements Serializable {

    private static final long serialVersionUID = 6166486470137424904L;

    /**
     * 归属类型
     */
    private Integer ownerType;
    /**
     * 归属码
     */
    private String ownerCode;
    /**
     * 扩展key
     */
    private String extKey;
    /**
     * 扩展值
     */
    private String extValue;

    /**
     * 数据类型
     * {@link com.shareyi.molicode.common.enums.DataTypeEnum}
     */
    private Integer type;

    public void setOwnerType(Integer ownerType) {
        this.ownerType = ownerType;
    }

    public Integer getOwnerType() {
        return this.ownerType;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getOwnerCode() {
        return this.ownerCode;
    }

    public void setExtKey(String extKey) {
        this.extKey = extKey;
    }

    public String getExtKey() {
        return this.extKey;
    }

    public void setExtValue(String extValue) {
        this.extValue = extValue;
    }

    public String getExtValue() {
        return this.extValue;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
