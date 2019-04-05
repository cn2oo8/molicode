package com.shareyi.molicode.interceptor;

import com.shareyi.molicode.common.constants.CommonConstant;
import org.apache.commons.lang.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 日志相关拦截器
 *
 * @author zhangshibin
 * @date 2018/11/4
 */
@Component
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse, Object o) throws Exception {
        System.out.println("当前线程：" + Thread.currentThread().getName());
        String sid = httpServletRequest.getParameter(CommonConstant.SID);
        if (StringUtils.isEmpty(sid)) {
            MDC.put(CommonConstant.SID, sid);
        }

        //判断是否为本地ip访问，否则抛异常，避免出现严重问题
        String remoteHost = httpServletRequest.getRemoteHost();
        if (!Objects.equals(remoteHost, CommonConstant.LOCAL_HOST_IP)) {
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
        MDC.remove(CommonConstant.SID);
    }
}
