/**
 * Copyright(c) 2004-2018 bianfeng
 */

package com.shareyi.molicode.service.sys.impl;

import com.shareyi.molicode.builder.impl.AcUserBuilder;
import com.shareyi.molicode.common.bean.LoginContext;
import com.shareyi.molicode.common.constants.CommonConstant;
import com.shareyi.molicode.common.utils.LoginHelper;
import com.shareyi.molicode.common.utils.MoliCodeStringUtils;
import com.shareyi.molicode.common.web.CommonResult;
import com.shareyi.molicode.domain.sys.AcUser;
import com.shareyi.molicode.manager.sys.AcUserManager;
import com.shareyi.molicode.service.AbstractService;
import com.shareyi.molicode.service.common.CipherService;
import com.shareyi.molicode.service.sys.AcUserService;
import com.shareyi.molicode.validate.provide.AcUserValidator;
import com.shareyi.molicode.vo.user.LoginUserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * <p>用户信息 service实现</p>
 *
 * @author david
 * @date 2018-08-21
 */
@Service("acUserService")
public class AcUserServiceImpl extends
        AbstractService<AcUser> implements AcUserService {

    @Resource(name = "acUserValidator")
    private AcUserValidator acUserValidator;

    @Resource(name = "acUserManager")
    private AcUserManager acUserManager;

    @Resource(name = "acUserBuilder")
    private AcUserBuilder acUserBuilder;

    @Resource
    private CipherService cipherService;

    /**
     * 简易版登录，非安全模式，仅限内网使用，friendly use only
     *
     * @param loginUserVo
     * @param response
     * @return
     */
    @Override
    public CommonResult login(LoginUserVo loginUserVo, HttpServletResponse response) {
        CommonResult result = CommonResult.create();
        try {
            if (StringUtils.isEmpty(loginUserVo.getUserName()) || StringUtils.isEmpty(loginUserVo.getPassword())) {
                return result.failed("参数有误！");
            }
            AcUser acUser = acUserManager.getByUserName(loginUserVo.getUserName());
            if (acUser == null) {
                return result.failed("用户不存在！");
            }
            String passwordMd5 = MoliCodeStringUtils.md5(loginUserVo.getPassword() + CommonConstant.PWD_SALT);
            if (!Objects.equals(passwordMd5, acUser.getPasswordMd5())) {
                return result.failed("密码不一致！");
            }
            String encryptUserName = cipherService.encryptByRSA(LoginContext.joinLoginInfo(acUser.getUserName(), acUser.getDataVersion()));
            Cookie cookie = new Cookie(CommonConstant.MOLI_LOGIN_KEY, encryptUserName);
            //cookie.setHttpOnly(true);
            cookie.setMaxAge(1 * 24 * 3600); //1天
            cookie.setPath("/");
            response.addCookie(cookie);
            acUser.setPasswordMd5(null);
            result.addDefaultModel(acUser);
            result.succeed();
        } catch (Exception e) {
            result.failed("登录失败，原因是：" + e.getMessage());
        }
        return result;
    }

    /**
     * 获取登录用户信息
     *
     * @return
     */
    public CommonResult<AcUser> getLoginUser() {
        CommonResult result = CommonResult.create();
        try {
            LoginContext loginContext = LoginHelper.getLoginContext();
            if (loginContext == null) {
                return result.failed("尚未登录！");
            }
            AcUser acUser = acUserManager.getByUserName(loginContext.getUserName());
            if (acUser == null) {
                return result.failed("用户不存在！");
            }
            acUser.setPasswordMd5(null);
            result.addDefaultModel(acUser);
            result.succeed();
        } catch (Exception e) {
            result.failed("登录失败，原因是：" + e.getMessage());
        }
        return result;
    }

    @Override
    public CommonResult<String> changePassword(String oldPass, String newPass) {
        CommonResult result = CommonResult.create();
        try {
            LoginContext loginContext = LoginHelper.getLoginContext();
            if (loginContext == null) {
                return result.failed("尚未登录！");
            }
            AcUser acUser = acUserManager.getByUserName(loginContext.getUserName());
            if (acUser == null) {
                return result.failed("用户不存在！");
            }

            String oldPassMd5 = MoliCodeStringUtils.md5(oldPass + CommonConstant.PWD_SALT);
            if (!Objects.equals(oldPassMd5, acUser.getPasswordMd5())) {
                return result.failed("旧密码验证不通过！");
            }
            String newPassMd5 = MoliCodeStringUtils.md5(newPass + CommonConstant.PWD_SALT);


            AcUser updateInfo = new AcUser();
            updateInfo.setId(acUser.getId());
            updateInfo.setUserName(acUser.getUserName());
            updateInfo.setDataVersion(acUser.getDataVersion() + 1);
            updateInfo.setPasswordMd5(newPassMd5);
            int update = acUserManager.update(updateInfo);
            if (update > 0) {
                result.succeed();
            } else {
                result.failed("更新失败，原因未知！");
            }
        } catch (Exception e) {
            result.failed("修改密码失败，原因是：" + e.getMessage());
        }
        return result;
    }

    /******* getter and setter ***/
    public AcUserManager getManager() {
        return acUserManager;
    }

    public AcUserValidator getValidator() {
        return acUserValidator;
    }

    @Override
    public AcUserBuilder getBuilder() {
        return acUserBuilder;
    }


}
