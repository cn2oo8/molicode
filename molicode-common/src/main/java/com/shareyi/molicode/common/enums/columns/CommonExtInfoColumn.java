/**
 * Copyright(c) 2004-2018 bianfeng
 */

package com.shareyi.molicode.common.enums.columns;

/**
 * 通用扩展信息 列名
 *
 * @author david
 * @date 2018-08-25
 */
public enum CommonExtInfoColumn implements Column {

    /**
     * 归属类型
     **/
    ownerType("owner_type", "归属类型"),
    /**
     * 归属码
     **/
    ownerCode("owner_code", "归属码"),
    /**
     * 扩展key
     **/
    extKey("ext_key", "扩展key"),
    /**
     * 扩展值
     **/
    extValue("ext_value", "扩展值"),
    /**
     * 数据类型
     */
    type("type", "数据类型");

    private String column;
    private String desc;

    CommonExtInfoColumn(String column, String desc) {
        this.column = column;
        this.desc = desc;
    }

    @Override
    public String getColumn() {
        return column;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
