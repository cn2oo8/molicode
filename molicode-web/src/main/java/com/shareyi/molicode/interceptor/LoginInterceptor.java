package com.shareyi.molicode.interceptor;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.shareyi.molicode.common.bean.LoginContext;
import com.shareyi.molicode.common.constants.CommonConstant;
import com.shareyi.molicode.common.enums.ResultCodeEnum;
import com.shareyi.molicode.common.utils.CookieUtils;
import com.shareyi.molicode.common.utils.ThreadLocalHolder;
import com.shareyi.molicode.common.web.CommonResult;
import com.shareyi.molicode.service.common.CipherService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 登录拦截
 *
 * @author zhangshibin
 * @date 2019/7/3
 */
@Service
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private CipherService cipherService;

    private List<String> excludeUrlList = Lists.newArrayList("/dist/", "/loginfree/", "/error");

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String url = httpServletRequest.getRequestURI();
        for (String excludeUrl : excludeUrlList) {
            if (url.startsWith(excludeUrl)) {
                return true;
            }
        }
        String loginStr = CookieUtils.getCookieValue(httpServletRequest, CommonConstant.MOLI_LOGIN_KEY);
        if (StringUtils.isEmpty(loginStr)) {
            responseNoAuth(httpServletRequest, httpServletResponse, true, StringUtils.EMPTY);
            return false;
        }
        try {
            String decryptStr = cipherService.decryptByRSA(loginStr);
            LoginContext loginContext = LoginContext.buildByLoginInfo(decryptStr);
            ThreadLocalHolder.putRequestThreadInfo(CommonConstant.MOLI_LOGIN_KEY, loginContext);
        } catch (Exception e) {
            responseNoAuth(httpServletRequest, httpServletResponse, true, StringUtils.EMPTY);
            return false;
        }
        return true;
    }


    /**
     * 响应无权限结果
     *
     * @param request
     * @param response
     * @param notLogin
     * @param noAuthCode
     * @throws IOException
     */
    private void responseNoAuth(HttpServletRequest request, HttpServletResponse response, boolean notLogin, String noAuthCode) throws IOException {
        CommonResult result = CommonResult.create();

        if (notLogin) {
            String message = "尚未登录";
            result.setReturnCode(ResultCodeEnum.LOGIN.codeString());
            result.failed(message);
        } else {
            String message = "用户没有权限, resource:" + noAuthCode;
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
    private static boolean isJsonRequest(HttpServletRequest request) {
        String contentType = request.getHeader("accept");
        return contentType != null && contentType.startsWith("application/json");
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
