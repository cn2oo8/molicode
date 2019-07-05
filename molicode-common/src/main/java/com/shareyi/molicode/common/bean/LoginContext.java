package com.shareyi.molicode.common.bean;

import com.shareyi.molicode.common.enums.ResultCodeEnum;
import com.shareyi.molicode.common.exception.ExceptionMaker;
import org.apache.commons.lang3.StringUtils;

/**
 * 登录上下文
 *
 * @author zhangshibin
 * @date 2019/7/3
 */
public class LoginContext {

    /**
     * 链接字符串
     */
    public static final String CONTACT_CHAR = ":";
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户版本信息
     */
    private Integer dataVersion;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(Integer dataVersion) {
        this.dataVersion = dataVersion;
    }

    /**
     * 构建loginInfo字符串
     *
     * @param userName
     * @param dataVersion
     * @return
     */
    public static String joinLoginInfo(String userName, Integer dataVersion) {
        return userName + CONTACT_CHAR + dataVersion;
    }

    /**
     * 通过登录信息构建登录context
     *
     * @param loginInfo
     * @return
     */
    public static LoginContext buildByLoginInfo(String loginInfo) {
        if (StringUtils.isEmpty(loginInfo)) {
            throw ExceptionMaker.buildException("登录信息有误", ResultCodeEnum.AUTH_REQUIRED);
        }
        int idx = loginInfo.lastIndexOf(CONTACT_CHAR);
        if (idx < 0) {
            throw ExceptionMaker.buildException("登录信息有误", ResultCodeEnum.AUTH_REQUIRED);
        }
        String userName = loginInfo.substring(0, idx);
        String dataVersion = loginInfo.substring(idx + 1);
        if (!StringUtils.isNumeric(dataVersion)) {
            throw ExceptionMaker.buildException("登录信息有误", ResultCodeEnum.AUTH_REQUIRED);
        }
        LoginContext loginContext = new LoginContext();
        loginContext.userName = userName;
        loginContext.dataVersion = Integer.valueOf(dataVersion);
        return loginContext;
    }
}
