package com.shareyi.molicode.handler;

import com.shareyi.molicode.common.enums.ResultCodeEnum;
import com.shareyi.molicode.common.utils.LogHelper;
import com.shareyi.molicode.common.web.CommonResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 异常处理器
 *
 * @author zhangshibin
 * @date 2019-08-16
 */
@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Map handle(Exception exception) {
        LogHelper.EXCEPTION.error("controller系统异常", exception);
        return CommonResult.create().failed("系统异常, message:" + exception.getMessage(), ResultCodeEnum.EXCEPTION).getReturnMap();

    }
}
