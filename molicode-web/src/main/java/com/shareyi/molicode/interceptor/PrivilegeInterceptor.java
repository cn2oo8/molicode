package com.shareyi.molicode.interceptor;

import com.shareyi.molicode.common.annotations.UserAuthPrivilege;
import com.shareyi.molicode.common.bean.LoginContext;
import com.shareyi.molicode.common.constants.CommonConstant;
import com.shareyi.molicode.common.enums.RoleCodeEnum;
import com.shareyi.molicode.helper.LoginHelper;
import com.shareyi.molicode.common.utils.Profiles;
import com.shareyi.molicode.domain.sys.AcUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 权限拦截器
 *
 * @author zhangshibin
 * @date 2019/7/5
 */
@Service
public class PrivilegeInterceptor extends BaseAbstractInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        LoginContext loginContext = LoginHelper.getLoginContext();
        if (loginContext == null) {
            return true;
        }
        Method method = handlerMethod.getMethod();
        Class handlerClass = method.getDeclaringClass();
        if (method == null || handlerClass == null) {
            return true;
        }
        UserAuthPrivilege annotation = method.getAnnotation(UserAuthPrivilege.class);
        if (annotation == null) {
            annotation = (UserAuthPrivilege) handlerClass.getAnnotation(UserAuthPrivilege.class);
        }
        if (annotation == null) {
            return true;
        }

        AcUser acUser = loginContext.getExtValue(CommonConstant.LoginContext.AC_USER, AcUser.class);
        RoleCodeEnum roleCodeEnum = RoleCodeEnum.Parser.parseToNullSafe(RoleCodeEnum.class, acUser.getRoleCode());
        if (roleCodeEnum == null) {
            //返回无权限
            this.responseNoAuth(request, response, false, StringUtils.EMPTY);
            return false;
        }

        //可切换掉整个系统的权限等级
        Integer safeLevel = Profiles.getInstance().getServerSafeLevel();
        int level = annotation.level();

        Integer userLevel = roleCodeEnum.getPrivilegeLevel();
        if (safeLevel != null && userLevel != CommonConstant.ROLE_LEVEL.ADMIN) {
            userLevel = Math.max(userLevel, safeLevel);
        }
        if (userLevel > level) {
            this.responseNoAuth(request, response, false, annotation.code());
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
