package com.shareyi.molicode.interceptor;

import com.alibaba.fastjson.JSON;
import com.shareyi.molicode.common.enums.ResultCodeEnum;
import com.shareyi.molicode.common.web.CommonResult;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 基础的interceptor
 *
 * @author zhangshibin
 * @date 2019/7/5
 */
public abstract class BaseAbstractInterceptor {

    /**
     * 响应无权限结果
     *
     * @param request
     * @param response
     * @param notLogin
     * @param noAuthCode
     * @throws IOException
     */
    public void responseNoAuth(HttpServletRequest request, HttpServletResponse response, boolean notLogin, String noAuthCode) throws IOException {
        CommonResult result = CommonResult.create();

        if (notLogin) {
            String message = "尚未登录";
            result.setReturnCode(ResultCodeEnum.LOGIN.codeString());
            result.failed(message);
        } else {
            String message = "用户没有权限;";
            if (StringUtils.isNotEmpty(noAuthCode)) {
                message += "resourceCode:" + noAuthCode;
            }
            result.setReturnCode(ResultCodeEnum.AUTH_REQUIRED.codeString());
            result.failed(message);
        }
        if (isJsonRequest(request)) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=utf-8");
        } else {
            response.setContentType("text/html;charset=utf-8");
        }
        response.getWriter().write(JSON.toJSONString(result.getReturnMap()));
    }

    /**
     * 判断请求类型(json)
     *
     * @param request
     * @return
     */
    public static boolean isJsonRequest(HttpServletRequest request) {
        String contentType = request.getHeader("accept");
        return contentType != null && contentType.startsWith("application/json");
    }

}
