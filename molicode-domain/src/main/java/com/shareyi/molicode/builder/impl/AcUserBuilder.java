/**
 * Copyright(c) 2004-2018 bianfeng
 */

package com.shareyi.molicode.builder.impl;

import com.shareyi.molicode.builder.AbstractBuilder;
import com.shareyi.molicode.domain.sys.AcUser;
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
}