/**
 * Copyright(c) 2004-2018 bianfeng
 */

package com.shareyi.molicode.common.enums.columns;

/**
 * 项目 列名
 * @author david
 * @date 2018-08-22
 */
public enum AcProjectColumn implements Column {

    /**  名称  **/
    name("name","名称"),
    /**  备注  **/
    remark("remark","备注"),
    /**  项目key  **/
    projectKey("project_key","项目key"),
    /**  类型  **/
    type("type","类型"),
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

    AcProjectColumn(String column, String desc) {
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
