package com.shareyi.molicode.common.utils;

import com.shareyi.molicode.common.bean.LoginContext;
import com.shareyi.molicode.common.constants.CommonConstant;

/**
 * 描述
 *
 * @author zhangshibin
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
}
