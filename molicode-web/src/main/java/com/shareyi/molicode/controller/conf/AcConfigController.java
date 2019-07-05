/**
 * Copyright(c) 2004-2018 bianfeng
 */


package com.shareyi.molicode.controller.conf;


import com.shareyi.molicode.common.annotations.UserAuthPrivilege;
import com.shareyi.molicode.common.constants.CommonConstant;
import com.shareyi.molicode.controller.AbstractController;
import com.shareyi.molicode.domain.conf.AcConfig;
import com.shareyi.molicode.service.conf.AcConfigService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/conf/acConfig")
public class AcConfigController extends AbstractController<AcConfig> {

    @Resource(name = "acConfigService")
    private AcConfigService acConfigService;


    @RequestMapping(value = "save", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    @UserAuthPrivilege(level = CommonConstant.ROLE_LEVEL.NORMAL)
    public Map save(AcConfig acConfig) {
        return getService().save(acConfig).getReturnMap();
    }

    public AcConfigService getService() {
        return acConfigService;
    }
}
