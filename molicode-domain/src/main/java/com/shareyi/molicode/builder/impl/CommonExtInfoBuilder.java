/**
 * Copyright(c) 2004-2018 bianfeng
 */

package com.shareyi.molicode.builder.impl;

import com.alibaba.fastjson.JSON;
import com.shareyi.molicode.builder.AbstractBuilder;
import com.shareyi.molicode.domain.conf.AcConfig;
import com.shareyi.molicode.domain.conf.CommonExtInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用扩展信息 Builder 类
 * @author david
 * @date 2018-08-25
 */
@Service("commonExtInfoBuilder")
public class CommonExtInfoBuilder extends AbstractBuilder<CommonExtInfo>{

    @Override
    public String getBizName() {
        return "通用扩展信息";
    }
     @Override
    public Class getBizClass() {
        return CommonExtInfo.class;
    }

    public Map<String,Map<String,String>> buildConfigMap(List<CommonExtInfo> list) {
        Map<String, Map<String,String>> dataMap =new HashMap<>();
        if(CollectionUtils.isEmpty(list)){
            return dataMap;
        }

        for(CommonExtInfo commonExtInfo : list){
            dataMap.put(commonExtInfo.getExtKey(), JSON.parseObject(commonExtInfo.getExtValue(), Map.class));
        }
        return dataMap;
    }
}