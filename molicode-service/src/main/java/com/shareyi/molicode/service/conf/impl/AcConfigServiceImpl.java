/**
 * Copyright(c) 2004-2018 bianfeng
 */

package com.shareyi.molicode.service.conf.impl;

import javax.annotation.Resource;
import com.shareyi.molicode.builder.impl.AcConfigBuilder;
import com.shareyi.molicode.common.enums.DataTypeEnum;
import com.shareyi.molicode.common.enums.StatusEnum;
import com.shareyi.molicode.common.utils.LogHelper;
import com.shareyi.molicode.common.web.CommonResult;
import com.shareyi.molicode.manager.conf.AcConfigManager;
import com.shareyi.molicode.service.conf.AcConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.shareyi.molicode.domain.conf.AcConfig;
import com.shareyi.molicode.validate.provide.AcConfigValidator;
import com.shareyi.molicode.service.AbstractService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *  <p>配置文件 service实现</p>
 *
 * @author david
 * @date 2018-08-22
 *
 */
@Service("acConfigService")
public class AcConfigServiceImpl extends
    AbstractService<AcConfig> implements AcConfigService {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AcConfigServiceImpl.class);

    @Resource(name="acConfigValidator")
    private AcConfigValidator acConfigValidator;

    @Resource(name="acConfigManager")
    private AcConfigManager acConfigManager;

    @Resource(name="acConfigBuilder")
    private AcConfigBuilder acConfigBuilder;


    /**
     * 保存配置信息到项目上
     *
     * @param acConfig
     * @return
     */
    public CommonResult save(AcConfig acConfig) {
        CommonResult<AcConfig> result = CommonResult.create();
        String bizName = getBuilder().getBizName();
        try {
           acConfigValidator.addValid(acConfig);
           AcConfig existConfig = acConfigManager.getByProjectAndKey(acConfig.getProjectKey(), acConfig.getConfigKey());
            if(existConfig!= null){
                acConfig.setId(existConfig.getId());
                result = this.update(acConfig);
            }else{
                result = this.add(acConfig);
            }
        } catch (Exception e) {
            LogHelper.EXCEPTION.error("保存" + bizName + "失败，data={}", acConfig, e);
            result.failed("保存" + bizName + "失败，原因是：" + e.getMessage());
        }
        return result;
    }

    /**
     * 查询project 下的所有配置信息，并转化为map
     *
     * @param projectKey
     * @return
     */
    public Map<String, Map<String, String>> getConfigMapByProjectKey(String projectKey, DataTypeEnum dataTypeEnum) {
        AcConfig queryBean = new AcConfig();
        queryBean.setProjectKey(projectKey);
        if(dataTypeEnum!=null){
            queryBean.setType(dataTypeEnum.getCode());
        }
        List<AcConfig> configList = acConfigManager.getListByExample(queryBean);
        return acConfigBuilder.buildConfigMap(configList, dataTypeEnum);
    }


    /**
     * 查询project 下的所有配置信息，列表
     *
     * @param projectKey
     * @return
     */
    @Override
    public List<AcConfig> queryByProjectKey(String projectKey, DataTypeEnum dataTypeEnum) {
        AcConfig queryBean = new AcConfig();
        queryBean.setProjectKey(projectKey);
        queryBean.setStatus(StatusEnum.YES.getCode());
        if(dataTypeEnum!=null){
            queryBean.setType(dataTypeEnum.getCode());
        }
        List<AcConfig> configList = acConfigManager.getListByExample(queryBean);
        return configList;
    }



    /******* getter and setter ***/
    public AcConfigManager getManager() {
        return acConfigManager;
    }

    public AcConfigValidator getValidator() {
        return acConfigValidator;
    }

    @Override
    public AcConfigBuilder getBuilder() {
        return acConfigBuilder;
    }

}
