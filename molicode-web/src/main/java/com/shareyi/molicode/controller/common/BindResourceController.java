package com.shareyi.molicode.controller.common;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.shareyi.molicode.builder.impl.CommonExtInfoBuilder;
import com.shareyi.molicode.common.bean.LoginContext;
import com.shareyi.molicode.common.constants.CommonConstant;
import com.shareyi.molicode.common.enums.OwnerTypeEnum;
import com.shareyi.molicode.common.utils.LogHelper;
import com.shareyi.molicode.common.utils.LoginHelper;
import com.shareyi.molicode.common.valid.Validate;
import com.shareyi.molicode.common.web.CommonResult;
import com.shareyi.molicode.domain.conf.CommonExtInfo;
import com.shareyi.molicode.service.conf.CommonExtInfoService;
import com.shareyi.molicode.web.base.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 保存配置文件服务
 */
@Controller
@RequestMapping("/common/bindResource")
public class BindResourceController extends BaseController {

    @Resource(name = "commonExtInfoService")
    private CommonExtInfoService commonExtInfoService;
    @Resource
    private CommonExtInfoBuilder commonExtInfoBuilder;

    @RequestMapping("saveBindResource")
    @ResponseBody
    public Map saveBindResource(String bindId, String configMapJson) {
        CommonResult result = CommonResult.create();
        try {
            Validate.notEmpty(bindId, "bindId不能为空");
            Validate.notEmpty(configMapJson, "configMapJson不能为空");
            LoginContext loginContext = LoginHelper.getLoginContext();
            CommonExtInfo commonExtInfo = commonExtInfoBuilder.buildByBindInfo(bindId, configMapJson, loginContext.getUserName());
            CommonResult newResult = commonExtInfoService.save(commonExtInfo);
            if (!newResult.isSuccess()) {
                return newResult.getReturnMap();
            }
            Map configMapParsed = JSON.parseObject(configMapJson, Map.class);
            result.addDefaultModel(configMapParsed);
            result.succeed("save success!");
        } catch (Exception e) {
            LogHelper.EXCEPTION.error("保存配置信息失败，bindId={}", bindId, e);
            result.failed(e.getMessage());
        }
        return result.getReturnMap();
    }

    @RequestMapping("getBindResource")
    @ResponseBody
    public Map getBindResource(String bindId) {
        CommonResult result = CommonResult.create();
        try {
            Validate.notEmpty(bindId, "bindId不能为空");
            CommonExtInfo queryInfo = new CommonExtInfo();
            queryInfo.setOwnerType(OwnerTypeEnum.USER.getCode());
            LoginContext loginContext = LoginHelper.getLoginContext();
            queryInfo.setOwnerCode(loginContext.getUserName());
            queryInfo.setExtKey(bindId);
            CommonResult<CommonExtInfo> commonResult = commonExtInfoService.getByOwnerAndKey(queryInfo);
            if (!commonResult.isSuccess()) {
                return commonResult.getReturnMap();
            }
            CommonExtInfo extInfo = commonResult.getDefaultModel();
            Map proInfoMap = Maps.newHashMap();
            if (extInfo != null && StringUtils.isNotEmpty(extInfo.getExtValue())) {
                proInfoMap = JSON.parseObject(extInfo.getExtValue());
            }
            result.addDefaultModel(proInfoMap);
            result.succeed();
        } catch (Exception e) {
            LogHelper.EXCEPTION.error("获取配置信息失败，bindId={}", bindId, e);
            result.failed(e.getMessage());
        }
        return result.getReturnMap();
    }


}
