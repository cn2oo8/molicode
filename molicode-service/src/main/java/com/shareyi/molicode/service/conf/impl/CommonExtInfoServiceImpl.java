/**
 * Copyright(c) 2004-2018 bianfeng
 */

package com.shareyi.molicode.service.conf.impl;

import com.shareyi.molicode.builder.impl.CommonExtInfoBuilder;
import com.shareyi.molicode.common.enums.DataTypeEnum;
import com.shareyi.molicode.common.enums.OwnerTypeEnum;
import com.shareyi.molicode.common.utils.LogHelper;
import com.shareyi.molicode.common.utils.LoginHelper;
import com.shareyi.molicode.common.web.CommonResult;
import com.shareyi.molicode.domain.conf.CommonExtInfo;
import com.shareyi.molicode.manager.conf.CommonExtInfoManager;
import com.shareyi.molicode.service.AbstractService;
import com.shareyi.molicode.service.conf.CommonExtInfoService;
import com.shareyi.molicode.validate.provide.CommonExtInfoValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>通用扩展信息 service实现</p>
 *
 * @author david
 * @date 2018-08-25
 */
@Service("commonExtInfoService")
public class CommonExtInfoServiceImpl extends
        AbstractService<CommonExtInfo> implements CommonExtInfoService {

    protected static final Logger LOGGER = LoggerFactory.getLogger(CommonExtInfoServiceImpl.class);

    @Resource(name = "commonExtInfoValidator")
    private CommonExtInfoValidator commonExtInfoValidator;

    @Resource(name = "commonExtInfoManager")
    private CommonExtInfoManager commonExtInfoManager;

    @Resource(name = "commonExtInfoBuilder")
    private CommonExtInfoBuilder commonExtInfoBuilder;

    /**
     * 保存数据
     *
     * @param commonExtInfo
     * @return
     */
    public CommonResult save(CommonExtInfo commonExtInfo) {
        CommonResult<CommonExtInfo> result = CommonResult.create();
        String bizName = getBuilder().getBizName();
        try {
            //如果为用户信息，则直接设置死ownerCode信息
            if (Objects.equals(commonExtInfo.getOwnerType(), OwnerTypeEnum.USER.getCode())) {
                commonExtInfo.setOwnerCode(LoginHelper.getLoginContext().getUserName());
            }
            commonExtInfoValidator.addValid(commonExtInfo);
            CommonExtInfo existData = commonExtInfoManager.getByOwnerAndKey(commonExtInfo);
            if (existData != null) {
                commonExtInfo.setId(existData.getId());
                result = this.update(commonExtInfo);
            } else {
                result = this.add(commonExtInfo);
            }
        } catch (Exception e) {
            LogHelper.EXCEPTION.error("保存" + bizName + "失败，data={}", commonExtInfo, e);
            result.failed("保存" + bizName + "失败，原因是：" + e.getMessage());
        }
        return result;
    }

    /**
     * 通过owner 和 key进行查询
     *
     * @param commonExtInfo
     * @return
     */
    public CommonResult getByOwnerAndKey(CommonExtInfo commonExtInfo) {
        CommonResult<CommonExtInfo> result = CommonResult.create();
        String bizName = getBuilder().getBizName();
        try {
            //如果为用户信息，则直接设置死ownerCode信息
            if (Objects.equals(commonExtInfo.getOwnerType(), OwnerTypeEnum.USER.getCode())) {
                commonExtInfo.setOwnerCode(LoginHelper.getLoginContext().getUserName());
            }
            commonExtInfoValidator.queryValid(commonExtInfo);
            CommonExtInfo data = getManager().getByOwnerAndKey(commonExtInfo);
            if (data != null) {
                result.succeed().addDefaultModel(data);
            } else {
                result.succeed("未查询到数据");
            }
        } catch (Exception e) {
            LogHelper.EXCEPTION.error("获取" + bizName + "失败，data={}", commonExtInfo, e);
            result.failed("获取" + bizName + "失败，原因是：" + e.getMessage());
        }
        return result;

    }


    /**
     * 通过拥有者获取数据
     *
     * @param ownerType
     * @param ownerCode
     * @param dataTypeEnum
     * @return
     */
    public Map<String, Map<String, String>> getConfigMapByOwner(Integer ownerType, String ownerCode, DataTypeEnum dataTypeEnum) {
        //如果为用户信息，则直接设置死ownerCode信息
        if (Objects.equals(ownerType, OwnerTypeEnum.USER.getCode())) {
            ownerCode = LoginHelper.getLoginContext().getUserName();
        }
        CommonExtInfo queryVo = new CommonExtInfo();
        queryVo.setOwnerType(ownerType);
        queryVo.setOwnerCode(ownerCode);
        if (dataTypeEnum != null) {
            queryVo.setType(dataTypeEnum.getCode());
        }
        List<CommonExtInfo> list = commonExtInfoManager.getListByExample(queryVo);
        return commonExtInfoBuilder.buildConfigMap(list);
    }


    /******* getter and setter ***/
    public CommonExtInfoManager getManager() {
        return commonExtInfoManager;
    }

    public CommonExtInfoValidator getValidator() {
        return commonExtInfoValidator;
    }

    @Override
    public CommonExtInfoBuilder getBuilder() {
        return commonExtInfoBuilder;
    }

}
