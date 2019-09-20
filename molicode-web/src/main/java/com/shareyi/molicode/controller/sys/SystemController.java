package com.shareyi.molicode.controller.sys;

import com.shareyi.molicode.common.utils.Profiles;
import com.shareyi.molicode.common.web.CommonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 系统controller
 *
 * @author david
 * @date 2018/11/1
 */
@Controller
@RequestMapping("/sys/system")
public class SystemController {

    /**
     * 获取系统级缓存
     *
     * @param cacheKey
     * @return
     */
    @ResponseBody
    @RequestMapping("getProfileCache")
    public Map getProfileCache(String cacheKey) {
        CommonResult result = CommonResult.create();
        result.addDefaultModel(Profiles.getInstance().getCache(cacheKey));
        return result.succeed().getReturnMap();
    }


    /**
     * 获取系统级缓存
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getProfileInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public Map getProfileInfo() {
        CommonResult result = CommonResult.create();
        result.addDefaultModel(Profiles.getInstance().getProfileInfo());
        return result.succeed().getReturnMap();
    }

}
