package com.shareyi.molicode.common.enums;

/**
 * 响应码枚举
 * @author david
 * @date 2018-08-21
 */
public enum ResultCodeEnum implements EnumCode<Integer> {
    /**
     * 处理成功
     */
    SUCCESS(000, "处理成功"),
    /**
     * 处理失败
     */
    FAILURE(100, "处理失败"),
    /**
     * 系统异常
     */
    EXCEPTION(101,"系统异常"),
    /**
     * 系统错误
     */
    ERROR(102,"系统错误"), //较严重
    /**
     * 参数错误
     */
    PARAM_ERROR(400,"参数错误"),
    /**
     * 数据不存在
     */
    DATA_NOT_EXIST(401,"数据不存在"),
    /**
     * 查询的数据存在异常
     */
    DATA_ERROR(402,"查询的数据存在异常"),
    /**
     * 重复数据
     */
    REPEAT_DATA(403,"重复数据"),
    /**
     * 重复提交
     */
    DUPLICATE_SUBMIT(404,"重复提交"),
    /**
     * 数据锁定
     */
    DATA_LOCKED(405,"数据锁定"),
    /**
     * 越权操作
     */
    AUTH_REQUIRED(500,"越权操作");

    private Integer code;

    private String desc;

    ResultCodeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public Integer getCode() {
        return code;
    }


    public String codeString() {
        return String.valueOf(code);
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
