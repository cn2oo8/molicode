/**
 * Copyright(C) 2004-2017 shareyi.com All Right Reserved
 */
package com.shareyi.molicode.common.exception;


import com.shareyi.molicode.common.enums.ResultCodeEnum;

/**
 * <p> 异常信息  </p>
 *
 * @author david
 * @date  2018-08-21
 */
public class AutoCodeException extends RuntimeException {

    private static final long serialVersionUID = -4034417775987612505L;
    /***
     * 异常编码
     */
    private String resultCode;
    /***
     * 链路跟踪ID
     */
    private String traceId;

    public AutoCodeException() {
    }

    /***
     *
     * @param message 个性的异常提示信息
     * @param resultCode 异常编码
     */
    public AutoCodeException(String message, String resultCode) {
        super(message);
        this.resultCode = resultCode;
    }

    /***
     *
     * @param resultCodeEnum 异常枚举
     */
    public AutoCodeException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getDesc());
        this.resultCode = resultCodeEnum.codeString();
    }

    /***
     * @param message 信息
     * @param resultCodeEnum 异常枚举
     */
    public AutoCodeException(String message, ResultCodeEnum resultCodeEnum) {
        super(message);
        this.resultCode = resultCodeEnum.codeString();
    }

    /**
     *
     * @param cause
     * @param resultCode
     */
    public AutoCodeException(Throwable cause, String resultCode) {
        super(resultCode , cause);
        this.resultCode = resultCode;
    }


}