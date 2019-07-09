/**
 * Copyright(c) 2004-2018 bianfeng
 */

package com.shareyi.molicode.service.sys.impl;

import com.google.common.base.Function;
import com.google.common.util.concurrent.RateLimiter;
import com.shareyi.molicode.builder.impl.AcUserBuilder;
import com.shareyi.molicode.common.bean.LoginContext;
import com.shareyi.molicode.common.constants.CacheKeyConstant;
import com.shareyi.molicode.common.constants.CommonConstant;
import com.shareyi.molicode.common.enums.RoleCodeEnum;
import com.shareyi.molicode.common.utils.*;
import com.shareyi.molicode.common.web.CommonResult;
import com.shareyi.molicode.common.web.PageQuery;
import com.shareyi.molicode.domain.sys.AcUser;
import com.shareyi.molicode.helper.LoginHelper;
import com.shareyi.molicode.manager.sys.AcUserManager;
import com.shareyi.molicode.service.AbstractService;
import com.shareyi.molicode.service.common.CacheService;
import com.shareyi.molicode.service.common.CipherService;
import com.shareyi.molicode.service.common.RateLimiterProvider;
import com.shareyi.molicode.service.sys.AcUserService;
import com.shareyi.molicode.validate.provide.AcUserValidator;
import com.shareyi.molicode.vo.user.LoginUserVo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
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

    @Resource
    private RateLimiterProvider rateLimiterProvider;
    @Resource(name = "guavaCacheService")
    private CacheService cacheService;

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

            String loginFailureKey = CacheKeyConstant.getLoginFailureKey(loginUserVo.getUserName());
            Integer failureCount = (Integer) cacheService.getShortTime(loginFailureKey);
            if (failureCount == null) {
                failureCount = 0;
            }
            if (failureCount > 6) {
                return result.failed("输错密码已经超过6次，请稍候再试！");
            }
            String passwordMd5 = MoliCodeStringUtils.md5PasswordWithSalt(loginUserVo.getPassword());
            if (!Objects.equals(passwordMd5, acUser.getPasswordMd5())) {
                failureCount++;
                cacheService.saveShortTime(loginFailureKey, failureCount);
                return result.failed("密码不一致！");
            }
            String encryptUserName = cipherService.encryptByRSA(LoginContext.joinLoginInfo(acUser.getUserName(), acUser.getDataVersion()));
            CookieUtils.setCookie(response, CommonConstant.MOLI_LOGIN_KEY, encryptUserName, CommonConstant.ONE_DAY_SEC);
            acUser.setPasswordMd5(null);
            result.addDefaultModel(acUser);
            result.succeed();
        } catch (Exception e) {
            LogHelper.EXCEPTION.error("登录失败,loginInfo={}", loginUserVo, e);
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
            LogHelper.EXCEPTION.error("获取用户信息失败", e);
            result.failed("获取用户信息失败，原因是：" + e.getMessage());
        }
        return result;
    }

    @Override
    public CommonResult<String> changePassword(String oldPass, String newPass, HttpServletResponse response) {
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

            String oldPassMd5 = MoliCodeStringUtils.md5PasswordWithSalt(oldPass);
            if (!Objects.equals(oldPassMd5, acUser.getPasswordMd5())) {
                return result.failed("旧密码验证不通过！");
            }
            String newPassMd5 = MoliCodeStringUtils.md5PasswordWithSalt(newPass);


            AcUser updateInfo = new AcUser();
            updateInfo.setId(acUser.getId());
            updateInfo.setUserName(acUser.getUserName());
            updateInfo.setDataVersion(acUser.getDataVersion() + 1);
            updateInfo.setPasswordMd5(newPassMd5);
            int update = acUserManager.update(updateInfo);
            if (update > 0) {
                String encryptUserName = cipherService.encryptByRSA(LoginContext.joinLoginInfo(updateInfo.getUserName(), updateInfo.getDataVersion()));
                CookieUtils.setCookie(response, CommonConstant.MOLI_LOGIN_KEY, encryptUserName, CommonConstant.ONE_DAY_SEC);
                cacheService.deleteShortTimeKey(CacheKeyConstant.getAcUserCacheKey(updateInfo.getUserName()));
                result.succeed();
            } else {
                result.failed("更新失败，原因未知！");
            }
        } catch (Exception e) {
            LogHelper.EXCEPTION.error("修改密码失败", e);
            result.failed("修改密码失败，原因是：" + e.getMessage());
        }
        return result;
    }

    @Override
    public CommonResult<AcUser> register(LoginUserVo loginUserVo) {
        CommonResult result = CommonResult.create();
        try {
            RateLimiter rateLimiter = rateLimiterProvider.getRegisterRateLimiter();
            boolean acquired = rateLimiter.tryAcquire();
            if (!acquired) {
                return result.failed("注册繁忙，超过最大注册数量，请稍候重试！");
            }
            LoginContext loginContext = LoginHelper.getLoginContext();
            if (loginContext != null) {
                return result.failed("您已经登录成功，请勿重复注册！");
            }
            acUserValidator.validateForRegister(loginUserVo);
            AcUser acUser = acUserManager.getByUserName(loginUserVo.getUserName());
            if (acUser != null) {
                return result.failed("用户已存在！");
            }

            loginUserVo.setRoleCode(Profiles.getInstance().getRegisterRoleCode());
            if (StringUtils.isEmpty(loginUserVo.getRoleCode())) {
                loginUserVo.setRoleCode(RoleCodeEnum.GUEST_USER.getCode());
            }
            AcUser acUserNew = acUserBuilder.buildForRegister(loginUserVo);
            CommonResult addResult = super.add(acUserNew);
            if (addResult.isSuccess()) {
                acUserNew.setPasswordMd5(null);
                result.addDefaultModel(acUserNew);
                result.succeed();
            } else {
                result.failed(addResult.getMessage());
            }
        } catch (Exception e) {
            LogHelper.EXCEPTION.error("注册用户异常,loginUserVo={}", loginUserVo, e);
            result.failed("注册用户异常，原因是：" + e.getMessage());
        }
        return result;
    }


    @Override
    public CommonResult<String> updateUserInfo(AcUser acUser) {
        CommonResult result = CommonResult.create();
        try {
            LoginContext loginContext = LoginHelper.getLoginContext();
            if (loginContext == null) {
                return result.failed("尚未登录！");
            }
            AcUser existUser = loginContext.getExtValue(CommonConstant.LoginContext.AC_USER, AcUser.class);
            if (existUser == null) {
                return result.failed("用户不存在！");
            }

            acUserValidator.updateValid(acUser);
            AcUser updateInfo = new AcUser();
            updateInfo.setId(existUser.getId());
            updateInfo.setUserName(existUser.getUserName());
            updateInfo.setRemark(acUser.getRemark());
            updateInfo.setGender(acUser.getGender());
            updateInfo.setBirthDay(acUser.getBirthDay());
            updateInfo.setNickName(acUser.getNickName());
            int update = acUserManager.update(updateInfo);
            if (update > 0) {
                cacheService.deleteShortTimeKey(CacheKeyConstant.getAcUserCacheKey(updateInfo.getUserName()));
                result.succeed();
            } else {
                result.failed("更新失败，原因未知！");
            }
        } catch (Exception e) {
            LogHelper.EXCEPTION.error("更新用户失败，acUser={}", acUser, e);
            result.failed("更新失败，原因是：" + e.getMessage());
        }
        return result;
    }

    @Override
    public CommonResult<String> logout(HttpServletRequest request, HttpServletResponse response) {
        CommonResult result = CommonResult.create();
        try {
            LoginContext loginContext = LoginHelper.getLoginContext();
            if (loginContext == null) {
                return result.failed("尚未登录！");
            }
            cacheService.deleteShortTimeKey(CacheKeyConstant.getAcUserCacheKey(loginContext.getUserName()));
            CookieUtils.removeCookie(request, response, CommonConstant.MOLI_LOGIN_KEY);
            result.succeed();
        } catch (Exception e) {
            LogHelper.EXCEPTION.error("登出失败", e);
            result.failed("登出失败，原因是：" + e.getMessage());
        }
        return result;
    }

    @Override
    public CommonResult<AcUser> addByAdmin(LoginUserVo loginUserVo) {
        CommonResult result = CommonResult.create();
        try {
            AcUser loginUser = LoginHelper.getLoginUser();
            if (loginUser == null) {
                return result.failed("您尚未登录，请先登录！");
            }
            acUserValidator.validateForRegister(loginUserVo);
            AcUser acUser = acUserManager.getByUserName(loginUserVo.getUserName());
            if (acUser != null) {
                return result.failed("用户已存在！");
            }
            if (StringUtils.isEmpty(loginUserVo.getRoleCode())) {
                loginUserVo.setRoleCode(RoleCodeEnum.GUEST_USER.getCode());
            }
            AcUser acUserNew = acUserBuilder.buildForRegister(loginUserVo);
            acUserNew.setCreator(loginUser.getUserName());
            CommonResult addResult = super.add(acUserNew);
            if (addResult.isSuccess()) {
                acUserNew.setPasswordMd5(null);
                result.addDefaultModel(acUserNew);
                result.succeed();
            } else {
                result.failed(addResult.getMessage());
            }
        } catch (Exception e) {
            LogHelper.EXCEPTION.error("注册用户异常,loginUserVo={}", loginUserVo, e);
            result.failed("注册用户异常，原因是：" + e.getMessage());
        }
        return result;
    }


    @Override
    public CommonResult<List<AcUser>> queryByPage(PageQuery pageQuery) {
        CommonResult<List<AcUser>> result = super.queryByPage(pageQuery);
        if (CollectionUtils.isNotEmpty(result.getDefaultModel())) {
            MyLists.transform(result.getDefaultModel(), new Function<AcUser, AcUser>() {
                @Override
                public AcUser apply(AcUser input) {
                    input.setPasswordMd5(null);
                    return input;
                }
            });
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
