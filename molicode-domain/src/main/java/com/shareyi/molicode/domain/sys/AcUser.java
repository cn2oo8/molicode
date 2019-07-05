/**
 * Copyright(c) 2004-2018 bianfeng
 */


package com.shareyi.molicode.domain.sys;

import com.shareyi.molicode.domain.BasicDomain;

import java.io.Serializable;

/**
 * @author david
 * @date 2018-08-21
 * 用户信息 Domain 类
 */
public class AcUser extends BasicDomain implements Serializable {

    private static final long serialVersionUID = -2616012924896935797L;

    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 密码MD5
     */
    private String passwordMd5;
    /**
     * 出生日期
     */
    private java.util.Date birthDay;
    /**
     * 角色码
     */
    private String roleCode;
    /**
     * 用户标签
     */
    private String userMark;
    /**
     * 备注
     */
    private String remark;
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
    /**
     * 创建人
     */
    private String creator;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return this.nickName;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getGender() {
        return this.gender;
    }

    public void setPasswordMd5(String passwordMd5) {
        this.passwordMd5 = passwordMd5;
    }

    public String getPasswordMd5() {
        return this.passwordMd5;
    }

    public void setBirthDay(java.util.Date birthDay) {
        this.birthDay = birthDay;
    }

    public java.util.Date getBirthDay() {
        return this.birthDay;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public void setUserMark(String userMark) {
        this.userMark = userMark;
    }

    public String getUserMark() {
        return this.userMark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return this.remark;
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

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreator() {
        return this.creator;
    }


}
