package com.shareyi.molicode.controller.common;


import com.alibaba.fastjson.JSON;
import com.shareyi.molicode.common.utils.BindResourceUtil;
import com.shareyi.molicode.common.utils.LogHelper;
import com.shareyi.molicode.common.valid.Validate;
import com.shareyi.molicode.common.web.CommonResult;
import com.shareyi.molicode.web.base.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 保存配置文件服务
 */
@Controller
@RequestMapping("/common/bindResource")
public class BindResourceController extends BaseController {


    @RequestMapping("saveBindResource")
    @ResponseBody
    public Map saveBindResource(String bindId, String configMapJson, HttpServletRequest request) {
        CommonResult result = CommonResult.create();
        Properties pro = null;
        try {
            Validate.notEmpty(bindId, "bindId不能为空");
            Validate.notEmpty(configMapJson, "configMapJson不能为空");

            pro = BindResourceUtil.getBindProperties(bindId);
            if (pro == null) {
                pro = new Properties();
            }
            Map configMapParsed = JSON.parseObject(configMapJson, Map.class);
            pro.putAll(configMapParsed);
            boolean b = BindResourceUtil.saveBindProperties(bindId, pro);
            result.addDefaultModel(new HashMap(pro));
            if (b) {
                result.succeed("save success!");
            } else {
                result.failed("save failed!");
            }
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
        Properties pro = null;
        try {
            Validate.notEmpty(bindId, "bindId不能为空");
            pro = BindResourceUtil.getBindProperties(bindId);
            if (pro == null) {
                pro = new Properties();
            }
            result.addDefaultModel(new HashMap(pro));
            result.succeed();
        } catch (Exception e) {
            LogHelper.EXCEPTION.error("获取配置信息失败，bindId={}", bindId, e);
            result.failed(e.getMessage());
        }
        return result.getReturnMap();
    }


}
