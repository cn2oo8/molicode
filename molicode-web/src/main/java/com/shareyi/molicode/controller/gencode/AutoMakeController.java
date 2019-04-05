package com.shareyi.molicode.controller.gencode;

import com.shareyi.molicode.common.vo.code.AutoCodeParams;
import com.shareyi.molicode.service.gencode.AutoCodeService;
import com.shareyi.molicode.web.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 执行代码生成的Action
 */
@Controller
@RequestMapping("/autoCode/coder")
public class AutoMakeController extends BaseController {

    @Resource
    AutoCodeService autoCodeService;

    /**
     * 根据模板生成页面
     */
    @RequestMapping("/execute")
    @ResponseBody
    public Map doAutoMakeNew(AutoCodeParams autoMakeParams) {
        return autoCodeService.generateCode(autoMakeParams).getReturnMap();
    }

    @RequestMapping("/getAutoMake")
    @ResponseBody
    public Map getAutoMake(AutoCodeParams autoMakeParams) {
        return autoCodeService.getAutoMake(autoMakeParams).getReturnMap();
    }

}
