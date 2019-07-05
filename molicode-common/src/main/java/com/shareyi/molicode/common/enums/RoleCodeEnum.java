package com.shareyi.molicode.common.enums;

import com.shareyi.molicode.common.constants.CommonConstant;

/**
 * 描述
 *
 * @author zhangshibin
 * @date 2019/7/5
 */
public enum RoleCodeEnum implements EnumCode<String> {

    /**
     * 权限较大，请勿随意授予
     */
    SYS_ADMIN("sys_admin", "系统超级管理员", CommonConstant.ROLE_LEVEL.ADMIN),
    /**
     * 普通用户
     */
    NORMAL_USER("normal_user", "普通用户", CommonConstant.ROLE_LEVEL.NORMAL),
    /**
     * 前台注册体验用户，禁止本地文件读取访问相关操作
     */
    GUEST_USER("guest_user", "前台用户", CommonConstant.ROLE_LEVEL.GUEST);


    String code, desc;
    /**
     * 权限等级
     */
    Integer privilegeLevel;

    RoleCodeEnum(String code, String desc, Integer privilegeLevel) {
        this.code = code;
        this.desc = desc;
        this.privilegeLevel = privilegeLevel;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public Integer getPrivilegeLevel() {
        return privilegeLevel;
    }
}
