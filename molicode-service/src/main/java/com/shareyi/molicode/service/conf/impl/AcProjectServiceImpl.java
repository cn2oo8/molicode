/**
 * Copyright(c) 2004-2018 bianfeng
 */

package com.shareyi.molicode.service.conf.impl;

import javax.annotation.Resource;

import com.shareyi.molicode.builder.impl.AcConfigBuilder;
import com.shareyi.molicode.builder.impl.AcProjectBuilder;
import com.shareyi.molicode.common.utils.LogHelper;
import com.shareyi.molicode.common.web.CommonResult;
import com.shareyi.molicode.domain.conf.AcConfig;
import com.shareyi.molicode.domain.conf.CommonExtInfo;
import com.shareyi.molicode.manager.conf.AcConfigManager;
import com.shareyi.molicode.manager.conf.AcProjectManager;
import com.shareyi.molicode.service.conf.AcConfigService;
import com.shareyi.molicode.service.conf.AcProjectService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.shareyi.molicode.domain.conf.AcProject;
import com.shareyi.molicode.validate.provide.AcProjectValidator;
import com.shareyi.molicode.service.AbstractService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>项目 service实现</p>
 *
 * @author david
 * @date 2018-08-22
 */
@Service("acProjectService")
public class AcProjectServiceImpl extends
        AbstractService<AcProject> implements AcProjectService {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AcProjectServiceImpl.class);

    @Resource(name = "acProjectValidator")
    private AcProjectValidator acProjectValidator;

    @Resource(name = "acProjectManager")
    private AcProjectManager acProjectManager;

    @Resource(name = "acProjectBuilder")
    private AcProjectBuilder acProjectBuilder;

    @Resource(name="acConfigService")
    AcConfigService acConfigService;

    @Resource
    AcConfigManager acConfigManager;
    @Resource
    AcConfigBuilder acConfigBuilder;

    @Override
    public CommonResult getByProjectKey(AcProject acProject) {
        CommonResult<AcProject> result = CommonResult.create();
        String bizName = getBuilder().getBizName();
        try {
            acProjectValidator.queryValid(acProject);
            AcProject data = getManager().getByProjectKey(acProject.getProjectKey());
            if (data != null) {
                result.succeed().addDefaultModel(data);
            } else {
                result.failed("未查询到数据");
            }
        } catch (Exception e) {
            LogHelper.EXCEPTION.error("获取" + bizName + "失败，data={}", acProject, e);
            result.failed("获取" + bizName + "失败，原因是：" + e.getMessage());
        }
        return result;
    }


    /**
     * 复制项目
     *
     * @param acProject
     * @return
     */
    public CommonResult copyProject(AcProject acProject) {
        CommonResult<AcProject> result = CommonResult.create();
        String bizName = getBuilder().getBizName();
        try {
            String oldProjectKey = acProject.getProjectKey();
            acProjectValidator.copyValidate(acProject);
            AcProject data = getManager().getByProjectKey(acProject.getProjectKey());
            if (data == null) {
                result.failed("通过项目key查询项目信息失败，projectKey=" + acProject.getProjectKey());
            }
            //从新设置新的KEY
            acProject.setProjectKey(null);
            CommonResult<AcProject> addProjectResult = super.add(acProject);
            if(!addProjectResult.isSuccess()){
                return addProjectResult;
            }

            String newProjectKey = acProject.getProjectKey();
            List<AcConfig> configList = acConfigService.queryByProjectKey(oldProjectKey,null);
            List<AcConfig> newList = acConfigBuilder.buildForCopyList(configList, newProjectKey);
            if(CollectionUtils.isNotEmpty(newList)){
                acConfigManager.batchAdd(newList);
            }
            result.addDefaultModel(acProject);
            result.succeed();
        } catch (Exception e) {
            LogHelper.EXCEPTION.error("复制" + bizName + "失败，data={}", acProject, e);
            result.failed("复制" + bizName + "失败，原因是：" + e.getMessage());
        }
        return result;
    }

    /******* getter and setter ***/
    public AcProjectManager getManager() {
        return acProjectManager;
    }

    public AcProjectValidator getValidator() {
        return acProjectValidator;
    }

    @Override
    public AcProjectBuilder getBuilder() {
        return acProjectBuilder;
    }


}
