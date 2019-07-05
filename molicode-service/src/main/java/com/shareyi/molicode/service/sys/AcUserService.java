/**
 * Copyright(c) 2004-2018 bianfeng
 */

package com.shareyi.molicode.service.sys;

import com.shareyi.molicode.common.web.CommonResult;
import com.shareyi.molicode.domain.sys.AcUser;
import com.shareyi.molicode.service.BaseService;
import com.shareyi.molicode.vo.user.LoginUserVo;

import javax.servlet.http.HttpServletResponse;

/**
 * 用户信息 service 接口
 *
 * @author david
 * @date 2018-08-21
 */
public interface AcUserService extends BaseService<AcUser> {

    /**
     * 登录
     *
     * @param loginUserVo
     * @param response
     * @return
     */
    CommonResult login(LoginUserVo loginUserVo, HttpServletResponse response);

    /**
     * 获取登录用户信息
     *
     * @return
     */
    CommonResult<AcUser> getLoginUser();

    /**
     * 修改密码
     *
     * @param oldPass
     * @param newPass
     * @param response
     * @return
     */
    CommonResult<String> changePassword(String oldPass, String newPass, HttpServletResponse response);

    /**
     * 注册用户
     *
     * @param loginUserVo
     * @return
     */
    CommonResult<AcUser> register(LoginUserVo loginUserVo);


    /**
     * 变更用户，只能修改部分字段
     *
     * @param acUser
     * @return
     */
    CommonResult<String> updateUserInfo(AcUser acUser);
}
