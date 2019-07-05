package com.shareyi.molicode.common.constants;

import org.apache.commons.lang3.StringUtils;

/**
 * 缓存key常量
 *
 * @author zhangshibin
 * @date 2019/7/5
 */
public class CacheKeyConstant {

    /**
     * 获取缓存的用户信息
     *
     * @param userName
     * @return
     */
    public static String getAcUserCacheKey(String userName) {
        return "ac_user_" + StringUtils.lowerCase(userName.trim());
    }

    /**
     * 失败次数key
     *
     * @param userName
     * @return
     */
    public static String getLoginFailureKey(String userName) {
        return "login_fail_" + StringUtils.lowerCase(userName.trim());
    }
}
