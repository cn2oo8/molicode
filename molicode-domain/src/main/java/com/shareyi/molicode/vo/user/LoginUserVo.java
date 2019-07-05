package com.shareyi.molicode.vo.user;

/**
 * 用户登录信息万
 *
 * @author zhangshibin
 * @date 2019/7/3
 */
public class LoginUserVo {
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
