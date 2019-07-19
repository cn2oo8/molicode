package com.shareyi.molicode.common.exception;

import com.shareyi.molicode.common.enums.ResultCodeEnum;

/**
 * 异常构建器
 *
 * @author david
 * @date 2018/8/21
 */
public class DefaultExceptionMaker implements MolicodeExceptionMaker {

    /**
     * 构建异常
     *
     * @param resultCodeEnum
     * @return
     */
    public static AutoCodeException buildException(ResultCodeEnum resultCodeEnum) {
        return new AutoCodeException(resultCodeEnum);
    }

    /**
     * 构建异常
     *
     * @param message
     * @param resultCodeEnum
     * @return
     */
    public static AutoCodeException buildException(String message, ResultCodeEnum resultCodeEnum) {
        return new AutoCodeException(message, resultCodeEnum.codeString());
    }

    @Override
    public void throwLockException(String msg) {
        throw makeLockException(msg);
    }

    @Override
    public RuntimeException makeLockException(String message) {
        return buildException(message, ResultCodeEnum.DUPLICATE_SUBMIT);
    }

}
