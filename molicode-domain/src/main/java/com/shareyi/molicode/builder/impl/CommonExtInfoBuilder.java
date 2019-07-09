/**
 * Copyright(c) 2004-2018 bianfeng
 */

package com.shareyi.molicode.builder.impl;

import com.alibaba.fastjson.JSON;
import com.shareyi.molicode.builder.AbstractBuilder;
import com.shareyi.molicode.common.enums.DataTypeEnum;
import com.shareyi.molicode.common.enums.OwnerTypeEnum;
import com.shareyi.molicode.common.enums.StatusEnum;
import com.shareyi.molicode.domain.conf.CommonExtInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用扩展信息 Builder 类
 *
 * @author david
 * @date 2018-08-25
 */
@Service("commonExtInfoBuilder")
public class CommonExtInfoBuilder extends AbstractBuilder<CommonExtInfo> {

    @Override
    public String getBizName() {
        return "通用扩展信息";
    }

    @Override
    public Class getBizClass() {
        return CommonExtInfo.class;
    }


    @Override
    public CommonExtInfo supplyAddInfo(CommonExtInfo dto) {
        super.supplyAddInfo(dto);
        //默认设置为text
        if (dto.getType() == null) {
            dto.setType(DataTypeEnum.TEXT.getCode());
        }
        return dto;
    }

    public Map<String, Map<String, String>> buildConfigMap(List<CommonExtInfo> list) {
        Map<String, Map<String, String>> dataMap = new HashMap<>();
        if (CollectionUtils.isEmpty(list)) {
            return dataMap;
        }

        for (CommonExtInfo commonExtInfo : list) {
            dataMap.put(commonExtInfo.getExtKey(), JSON.parseObject(commonExtInfo.getExtValue(), Map.class));
        }
        return dataMap;
    }

    /**
     * 构建系统bindInfo对象
     *
     * @param bindId
     * @param configMapJson
     * @param ownerCode
     * @return
     */
    public CommonExtInfo buildByBindInfo(String bindId, String configMapJson, String ownerCode) {
        CommonExtInfo commonExtInfo = new CommonExtInfo();
        commonExtInfo.setOwnerType(OwnerTypeEnum.USER.getCode());
        commonExtInfo.setOwnerCode(ownerCode);
        commonExtInfo.setExtKey(bindId);
        commonExtInfo.setExtValue(configMapJson);
        commonExtInfo.setStatus(StatusEnum.YES.getCode());
        commonExtInfo.setType(DataTypeEnum.JSON.getCode());
        commonExtInfo.setCreator(ownerCode);
        return commonExtInfo;
    }
}