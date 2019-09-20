package com.shareyi.molicode.controller.common;

import com.shareyi.molicode.common.annotations.UserAuthPrivilege;
import com.shareyi.molicode.common.constants.CommonConstant;
import com.shareyi.molicode.domain.conf.AcConfig;
import com.shareyi.molicode.service.common.DatabaseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 数据库服务
 *
 * @author david
 * @date 2019/6/29
 */
@Controller
@RequestMapping("/common/database")
public class DatabaseController {
    @Resource
    private DatabaseService databaseService;

    @RequestMapping(value = "testConnection", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    @UserAuthPrivilege(level = CommonConstant.ROLE_LEVEL.NORMAL)
    public Map testConnection(AcConfig acConfig) {
        return databaseService.testConnection(acConfig).getReturnMap();
    }
}
