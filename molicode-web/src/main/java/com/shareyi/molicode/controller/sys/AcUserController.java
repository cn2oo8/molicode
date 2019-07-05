/**
 * Copyright(c) 2004-2018 bianfeng
 */


package com.shareyi.molicode.controller.sys;


import com.shareyi.molicode.common.web.CommonResult;
import com.shareyi.molicode.domain.sys.AcUser;
import com.shareyi.molicode.service.sys.AcUserService;
import com.shareyi.molicode.web.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/sys/acUser")
public class AcUserController extends BaseController {

    @Resource(name = "acUserService")
    private AcUserService acUserService;

    /**
     * 获取登录用户信息
     *
     * @return
     */
    @RequestMapping(value = "getLoginUser", method = RequestMethod.POST)
    @ResponseBody
    public Map getLoginUser() {
        CommonResult<AcUser> result = acUserService.getLoginUser();
        return result.getReturnMap();
    }

    /**
     * 获取登录用户信息
     *
     * @return
     */
    @RequestMapping(value = "changePassword", method = RequestMethod.POST)
    @ResponseBody
    public Map changePassword(@RequestParam("oldPass") String oldPass, @RequestParam("newPass") String newPass) {
        CommonResult<String> result = acUserService.changePassword(oldPass, newPass);
        return result.getReturnMap();
    }

    public AcUserService getService() {
        return acUserService;
    }
}
