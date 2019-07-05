package com.shareyi.molicode.common.constants;

/**
 * 通用常量
 *
 * @author david
 * @date 2018/9/1
 */
public class CommonConstant {
    /**
     * 默认的系统拥有者
     */
    public static final String DEFAULT_SYS_OWNER = "admin";

    /**
     * 标准YES_NO 是
     */
    public static final int STD_YN_YES = 1;
    /**
     * 标准YES_NO 否
     */
    public static final int STD_YN_NO = 2;

    /**
     * 标准YES_NO 是
     */
    public static final String STD_YN_YES_STR = "1";
    /**
     * 标准YES_NO 否
     */
    public static final String STD_YN_NO_STR = "2";

    public static final String LINE_STR = "\n";
    public static final String BROWSER_PASS_KEY = "browserPassKey";
    /**
     * 会话ID
     */
    public static final String SID = "sid";

    /**
     * 逗号分隔
     */
    public static final String COMMA = ",";
    public static final String LOCAL_HOST_IP = "127.0.0.1";

    public static final String MODEL_TYPE_JSON = "json";
    /**
     * 密码加盐
     */
    public static final String PWD_SALT = "_pwd_salt_mcode";
    /**
     * 登录key
     */
    public static final String MOLI_LOGIN_KEY = "moliLoginKey";

    /**
     * 一天
     */
    public static final int ONE_DAY_SEC = 1 * 24 * 3600;


    /**
     * 登录context
     */
    public class LoginContext {
        /**
         * acUser
         */
        public static final String AC_USER = "acUser";

    }

    /**
     * 角色等级
     */
    public class ROLE_LEVEL {
        /**
         * admin等级
         */
        public static final int ADMIN = 0;
        /**
         * 普通用户
         */
        public static final int NORMAL = 3;
        /**
         * guest
         */
        public static final int GUEST = 5;
    }
}
