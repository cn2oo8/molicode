package com.shareyi.molicode.controller.common;

import com.shareyi.molicode.common.annotations.UserAuthPrivilege;
import com.shareyi.molicode.common.constants.CommonConstant;
import com.shareyi.molicode.service.common.RemoteFileService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 远程资源请求服务
 *
 * @author david
 * @date 2019/6/15
 */
@Controller
@RequestMapping("/common/remoteFile")
public class RemoteFileController {

    @Resource
    private RemoteFileService remoteFileService;

    @ResponseBody
    @RequestMapping(value = "httpGet", method = {RequestMethod.GET, RequestMethod.POST})
    @UserAuthPrivilege
    public Map httpGet(String url) {
        return remoteFileService.httpGet(url).getReturnMap();
    }

}
