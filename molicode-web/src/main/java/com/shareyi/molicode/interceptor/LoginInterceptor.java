package com.shareyi.molicode.interceptor;

import com.google.common.collect.Lists;
import com.shareyi.molicode.common.bean.LoginContext;
import com.shareyi.molicode.common.constants.CacheKeyConstant;
import com.shareyi.molicode.common.constants.CommonConstant;
import com.shareyi.molicode.common.utils.CookieUtils;
import com.shareyi.molicode.common.utils.ThreadLocalHolder;
import com.shareyi.molicode.domain.sys.AcUser;
import com.shareyi.molicode.manager.sys.AcUserManager;
import com.shareyi.molicode.service.common.CacheService;
import com.shareyi.molicode.service.common.CipherService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * 登录拦截
 *
 * @author zhangshibin
 * @date 2019/7/3
 */
@Service
public class LoginInterceptor extends BaseAbstractInterceptor implements HandlerInterceptor {

    /**
     * 请求token
     */
    public static final String TOKEN_KEY = "token";
    @Resource
    private CipherService cipherService;
    /**
     * 忽略的url前缀
     */
    private List<String> excludeUrlList = Lists.newArrayList("/dist/", "/loginfree/", "/error");
    @Resource(name = "guavaCacheService")
    private CacheService cacheService;
    @Resource
    private AcUserManager acUserManager;
    /**
     * 启用token模式
     */
    @Value("${request.token.value:}")
    private String tokenValue;
    /**
     * token模式的映射用户
     */
    @Value("${request.token.userName:admin}")
    private String tokenUserName;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String url = httpServletRequest.getRequestURI();
        for (String excludeUrl : excludeUrlList) {
            if (url.startsWith(excludeUrl)) {
                return true;
            }
        }
        String reqToken = httpServletRequest.getParameter(TOKEN_KEY);
        if (StringUtils.isNotBlank(tokenValue) && StringUtils.isNotBlank(reqToken)) {
            if (Objects.equals(tokenValue, reqToken)) {
                LoginContext loginContext = LoginContext.buildByUserName(tokenUserName);
                if (loadUserInfo(httpServletRequest, httpServletResponse, loginContext)) {
                    return true;
                }
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
            if (!loadUserInfo(httpServletRequest, httpServletResponse, loginContext)) {
                return false;
            }
        } catch (Exception e) {
            responseNoAuth(httpServletRequest, httpServletResponse, true, StringUtils.EMPTY);
            return false;
        }
        return true;
    }

    private boolean loadUserInfo(HttpServletRequest request, HttpServletResponse response, LoginContext loginContext) throws IOException {
        AcUser acUser = this.loadAcUserByUserName(loginContext.getUserName());
        if (acUser == null || !loginContext.checkDataVersion(acUser.getDataVersion())) {
            responseNoAuth(request, response, true, StringUtils.EMPTY);
            return false;
        }
        loginContext.putExtInfo(CommonConstant.LoginContext.AC_USER, acUser);
        ThreadLocalHolder.putRequestThreadInfo(CommonConstant.MOLI_LOGIN_KEY, loginContext);
        return true;
    }

    /**
     * 通过用户名称查询用户信息
     *
     * @param userName
     * @return
     */
    private AcUser loadAcUserByUserName(String userName) {
        String cacheKey = CacheKeyConstant.getAcUserCacheKey(userName);
        AcUser acUser = (AcUser) cacheService.getShortTime(cacheKey);
        if (acUser != null) {
            return acUser;
        }
        acUser = acUserManager.getByUserName(userName);
        if (acUser == null) {
            return acUser;
        }
        cacheService.saveShortTime(cacheKey, acUser);
        return acUser;
    }


    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
