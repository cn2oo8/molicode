/**
 * Copyright(c) 2004-2018 bianfeng
 */

package com.shareyi.molicode.manager.conf.impl;

import com.shareyi.molicode.common.enums.StatusEnum;
import com.shareyi.molicode.common.enums.columns.AcConfigColumn;
import com.shareyi.molicode.common.enums.columns.BasicColumn;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import com.shareyi.molicode.domain.conf.AcConfig;
import com.shareyi.molicode.dao.conf.AcConfigDao;
import com.shareyi.molicode.manager.conf.AcConfigManager;
import com.shareyi.molicode.manager.AbstractManager;
import com.shareyi.molicode.builder.impl.AcConfigBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 配置文件Manager实现类
 *
 * @author david
 * @date 2018-08-22
 */
@Component("acConfigManager")
public class AcConfigManagerImpl extends AbstractManager<AcConfig> implements AcConfigManager {

    @Resource
    private AcConfigDao acConfigDao;
    @Resource(name = "acConfigBuilder")
    AcConfigBuilder acConfigBuilder;


    @Override
    public AcConfig getByProjectAndKey(String projectKey, String configKey) {
        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put(AcConfigColumn.projectKey.name(), projectKey);
        queryParam.put(AcConfigColumn.configKey.name(), configKey);
        queryParam.put(BasicColumn.status.name(), StatusEnum.YES.getCode());
        List<AcConfig> list = acConfigDao.getListByExample(queryParam);
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    /******* getter and setter ***/
    public AcConfigBuilder getBuilder() {
        return acConfigBuilder;
    }

    public AcConfigDao getDao() {
        return acConfigDao;
    }

}
