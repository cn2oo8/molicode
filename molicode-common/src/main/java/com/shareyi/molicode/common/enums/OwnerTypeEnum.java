package com.shareyi.molicode.common.enums;

/**
 * 归属信息type
 *
 * @author david
 * @date 2018/8/25
 */
public enum OwnerTypeEnum implements EnumCode<Integer> {

    /**
     * 系统
     */
    SYSTEM(1, "系统"),
    /**
     * 项目
     */
    PROJECT(2, "项目"),
    /**
     * 系统
     */
    USER(3, "用户");

    private final Integer code;
    private final String desc;

    OwnerTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
