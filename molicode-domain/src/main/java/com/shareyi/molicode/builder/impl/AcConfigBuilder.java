/**
 * Copyright(c) 2004-2018 bianfeng
 */

package com.shareyi.molicode.builder.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.shareyi.molicode.builder.AbstractBuilder;
import com.shareyi.molicode.common.enums.DataTypeEnum;
import com.shareyi.molicode.common.utils.MyLists;
import com.shareyi.molicode.domain.conf.AcConfig;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 配置文件 Builder 类
 *
 * @author david
 * @date 2018-08-22
 */
@Service("acConfigBuilder")
public class AcConfigBuilder extends AbstractBuilder<AcConfig> {

    @Override
    public String getBizName() {
        return "配置文件";
    }

    @Override
    public Class getBizClass() {
        return AcConfig.class;
    }

    /**
     * 构建Map
     * @param configList
     * @param dataTypeEnum
     * @return
     */
    public Map<String, Map<String, String>> buildConfigMap(List<AcConfig> configList, DataTypeEnum dataTypeEnum) {
        Map<String, Map<String,String>> dataMap =new HashMap<>();
        if(CollectionUtils.isEmpty(configList)){
            return dataMap;
        }

        for(AcConfig acConfig : configList){
          dataMap.put(acConfig.getConfigKey(), JSON.parseObject(acConfig.getConfigValue(), Map.class));
        }
        return dataMap;
    }

    public List<AcConfig> buildForCopyList(List<AcConfig> configList, final String newProjectKey) {
        return MyLists.transform(configList, new Function<AcConfig, AcConfig>() {

            @Override
            public AcConfig apply(AcConfig acConfig) {
                AcConfig newConfig = new AcConfig();
                newConfig.setProjectKey(newProjectKey);
                newConfig.setType(acConfig.getType());
                newConfig.setStatus(acConfig.getStatus());
                newConfig.setConfigKey(acConfig.getConfigKey());
                newConfig.setConfigValue(acConfig.getConfigValue());
                newConfig.setScope(acConfig.getScope());
                return newConfig;
            }
        });
    }
}