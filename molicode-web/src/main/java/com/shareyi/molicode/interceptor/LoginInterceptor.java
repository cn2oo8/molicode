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
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Resource
    private CipherService cipherService;
    private List<String> excludeUrlList = Lists.newArrayList("/dist/", "/loginfree/", "/error");
    @Resource(name = "guavaCacheService")
    private CacheService cacheService;
    @Resource
    private AcUserManager acUserManager;

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
            AcUser acUser = this.loadAcUserByUserName(loginContext.getUserName());
            if (acUser == null || !Objects.equals(acUser.getDataVersion(), loginContext.getDataVersion())) {
                responseNoAuth(httpServletRequest, httpServletResponse, true, StringUtils.EMPTY);
                return false;
            }
            loginContext.putExtInfo(CommonConstant.LoginContext.AC_USER, acUser);
            ThreadLocalHolder.putRequestThreadInfo(CommonConstant.MOLI_LOGIN_KEY, loginContext);
        } catch (Exception e) {
            responseNoAuth(httpServletRequest, httpServletResponse, true, StringUtils.EMPTY);
            return false;
        }
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
