/**
 * Copyright(c) 2004-2018 bianfeng
 */


package com.shareyi.molicode.controller.loginfree;


import com.shareyi.molicode.common.web.CommonResult;
import com.shareyi.molicode.service.sys.AcUserService;
import com.shareyi.molicode.vo.user.LoginUserVo;
import com.shareyi.molicode.web.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/loginfree/login")
public class LoginController extends BaseController {

    @Resource(name = "acUserService")
    private AcUserService acUserService;

    /**
     * 登录入口
     *
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public Map login(LoginUserVo loginUserVo, HttpServletResponse response) {
        CommonResult result = acUserService.login(loginUserVo, response);
        return result.getReturnMap();
    }
}
