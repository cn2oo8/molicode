/**
 * Copyright(c) 2004-2018 bianfeng
 */

package com.shareyi.molicode.common.enums.columns;

/**
 * 配置文件 列名
 * @author david
 * @date 2018-08-22
 */
public enum AcConfigColumn implements Column {

    /**  类型  **/
    type("type","类型"),
    /**  项目key  **/
    projectKey("project_key","项目key"),
    /**  范围  **/
    scope("scope","范围"),
    /**  配置key  **/
    configKey("config_key","配置key"),
    /**  配置值  **/
    configValue("config_value","配置值"),
    /**  扩展1  **/
    ext1("ext1","扩展1"),
    /**  扩展2  **/
    ext2("ext2","扩展2"),
    /**  扩展3  **/
    ext3("ext3","扩展3"),
    /**  创建人  **/
    creator("creator","创建人"),
    ;

    private String column;
    private String desc;

    AcConfigColumn(String column, String desc) {
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
