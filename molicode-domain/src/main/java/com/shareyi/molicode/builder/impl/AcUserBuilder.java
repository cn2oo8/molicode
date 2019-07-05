/**
 * Copyright(c) 2004-2018 bianfeng
 */

package com.shareyi.molicode.builder.impl;

import com.shareyi.molicode.builder.AbstractBuilder;
import com.shareyi.molicode.common.enums.StatusEnum;
import com.shareyi.molicode.common.utils.MoliCodeStringUtils;
import com.shareyi.molicode.domain.sys.AcUser;
import com.shareyi.molicode.vo.user.LoginUserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 用户信息 Builder 类
 *
 * @author david
 * @date 2018-08-21
 */
@Service("acUserBuilder")
public class AcUserBuilder extends AbstractBuilder<AcUser> {

    @Override
    public String getBizName() {
        return "用户信息";
    }

    @Override
    public Class getBizClass() {
        return AcUser.class;
    }


    /**
     * 构建注册对象
     *
     * @param loginUserVo
     * @return
     */
    public AcUser buildForRegister(LoginUserVo loginUserVo) {
        AcUser acUserNew = new AcUser();
        acUserNew.setUserName(loginUserVo.getUserName());
        acUserNew.setPasswordMd5(MoliCodeStringUtils.md5PasswordWithSalt(loginUserVo.getPassword()));
        acUserNew.setNickName(loginUserVo.getNickName());
        acUserNew.setStatus(StatusEnum.YES.getCode());
        acUserNew.setRoleCode(loginUserVo.getRoleCode());
        acUserNew.setBirthDay(loginUserVo.getBirthDay());
        acUserNew.setRemark(loginUserVo.getRemark());
        acUserNew.setCreator(loginUserVo.getUserName());
        acUserNew.setGender(loginUserVo.getGender());
        if(StringUtils.isEmpty(acUserNew.getNickName())){
            acUserNew.setNickName(acUserNew.getUserName());
        }
        return acUserNew;
    }
}