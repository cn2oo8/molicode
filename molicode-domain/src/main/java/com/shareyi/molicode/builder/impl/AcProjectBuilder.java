/**
 * Copyright(c) 2004-2018 bianfeng
 */

package com.shareyi.molicode.builder.impl;

import com.shareyi.molicode.builder.AbstractBuilder;
import com.shareyi.molicode.common.utils.PubUtils;
import com.shareyi.molicode.domain.conf.AcProject;
import org.springframework.stereotype.Service;

/**
 * 项目 Builder 类
 *
 * @author david
 * @date 2018-08-22
 */
@Service("acProjectBuilder")
public class AcProjectBuilder extends AbstractBuilder<AcProject> {


    @Override
    public AcProject supplyAddInfo(AcProject dto) {
        dto.setProjectKey(PubUtils.getUUID());
        return super.supplyAddInfo(dto);
    }

    @Override
    public String getBizName() {
        return "项目";
    }

    @Override
    public Class getBizClass() {
        return AcProject.class;
    }
}