/**
 * Copyright(c) 2004-2018 bianfeng
 */

package com.shareyi.molicode.common.enums.columns;

/**
 * 用户信息 列名
 * @author david
 * @date 2018-08-21
 */
public enum AcUserColumn implements Column {

    /**  用户名称  **/
    userName("user_name","用户名称"),
    /**  用户昵称  **/
    nickName("nick_name","用户昵称"),
    /**  性别  **/
    gender("gender","性别"),
    /**  密码MD5  **/
    passwordMd5("password_md5","密码MD5"),
    /**  出生日期  **/
    birthDay("birth_day","出生日期"),
    /**  用户标签  **/
    userMark("user_mark","用户标签"),
    /**  备注  **/
    remark("remark","备注"),
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

    AcUserColumn(String column, String desc) {
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
