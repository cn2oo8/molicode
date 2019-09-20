package com.shareyi.molicode.helper;

import com.shareyi.molicode.common.bean.LoginContext;
import com.shareyi.molicode.common.constants.CommonConstant;
import com.shareyi.molicode.common.utils.ThreadLocalHolder;
import com.shareyi.molicode.domain.sys.AcUser;

/**
 * 描述
 *
 * @author david
 * @date 2019/7/3
 */
public class LoginHelper {

    /**
     * 获取登录信息
     *
     * @return
     */
    public static LoginContext getLoginContext() {
        return (LoginContext) ThreadLocalHolder.getRequestThreadInfo(CommonConstant.MOLI_LOGIN_KEY);
    }


    /**
     * 获取登录用户信息
     *
     * @return
     */
    public static AcUser getLoginUser() {
        return getLoginUser(getLoginContext());
    }

    /**
     * 获取登录用户信息
     *
     * @param loginContext
     * @return
     */
    public static AcUser getLoginUser(LoginContext loginContext) {
        return loginContext.getExtValue(CommonConstant.LoginContext.AC_USER, AcUser.class);
    }
}
