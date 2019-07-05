/**
 * Copyright(c) 2004-2018 bianfeng
 */

package com.shareyi.molicode.validate.provide;

import com.shareyi.molicode.common.utils.ValidateUtils;
import com.shareyi.molicode.common.valid.Validate;
import com.shareyi.molicode.domain.sys.AcUser;
import com.shareyi.molicode.validate.AbstractValidator;
import com.shareyi.molicode.vo.user.LoginUserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import static com.shareyi.molicode.common.valid.Validate.notNull;


/**
 * 用户信息 SDK 验证 类
 *
 * @author david
 * @date 2018-08-21
 */
@Service("acUserValidator")
public class AcUserValidator extends AbstractValidator<AcUser> {
    @Override
    public void addValid(AcUser obj) {
        notNull(obj, "入参对象不能为空.");
    }

    @Override
    public void updateValid(AcUser acUser) {
        notNull(acUser, "入参对象不能为空.");
        Validate.notEmpty(acUser.getUserName(), "userName不能为空.");

        Validate.range(StringUtils.length(acUser.getRemark()), 0, 200, "备注请保持在0~200字符");
        Validate.range(StringUtils.length(acUser.getNickName()), 0, 30, "昵称请保持在0~30字符");
        Validate.checkChineseAndLetterNum(acUser.getNickName(), "nickName不能包含非法字符");
    }

    /**
     * 注册信息验证
     *
     * @param loginUserVo
     */
    public void validateForRegister(LoginUserVo loginUserVo) {
        ValidateUtils.notEmptyField(loginUserVo, "userName");
        ValidateUtils.notEmptyField(loginUserVo, "password");
        ValidateUtils.notEmptyField(loginUserVo, "gender");

        Validate.range(loginUserVo.getUserName().length(), 4, 30, "用户名请保持在4~30字符");
        Validate.range(loginUserVo.getPassword().length(), 6, 30, "密码请保持在4~30字符");
        Validate.range(StringUtils.length(loginUserVo.getRemark()), 0, 200, "备注请保持在0~200字符");
        Validate.range(StringUtils.length(loginUserVo.getNickName()), 0, 30, "昵称请保持在0~30字符");
        Validate.range(loginUserVo.getUserName().length(), 4, 30, "用户名请保持在4~30字符");
        Validate.checkChineseAndLetterNum(loginUserVo.getUserName(), "用户名称不能包含非法字符");
        Validate.checkChineseAndLetterNum(loginUserVo.getNickName(), "nickName不能包含非法字符");
    }
}
