/**
 * Copyright(C) 2004-2017 shareyi.com All Right Reserved
 */
package com.shareyi.molicode.common.enums.columns;

/**
 * <p>基本的列名 </p>
 *
 * @author david
 * @date 2017-09-26 13:44
 */
public enum BasicColumn implements Column {

    /**
     * 主键id
     */
    id("id", "主键id"),
    /**
     * 分库字段
     */
    router("router", "分库字段"),
    /**
     * 平台：1-大客，2-分销，3-新通路，4-医药城
     */
    platform("platform", "平台：1-大客，2-分销，3-新通路，4-医药城"),
    /**
     * 原始企业id
     */
    originalCompanyId("original_company_id", "原始企业id"),
    /**
     * 创建人
     */
    creator("creator", "创建人"),
    /**
     * 修改人
     */
    operator("operator", "修改人"),
    /**
     * 数据有效性；1
     */
    status("status", "数据有效性；1"),
    /**
     * 创建时间
     */
    created("created", "创建时间"),
    /**
     * 最后修改时间
     */
    modified("modified", "最后修改时间"),
    /**
     * 并发版本号，从1开始
     */
    concurrentVersion("concurrent_version", "并发版本号，从1开始"),
    /**
     * 数据版本号
     */
    dataVersion("data_version", "数据版本号");

    private String column;
    private String desc;

    BasicColumn(String column, String desc) {
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
