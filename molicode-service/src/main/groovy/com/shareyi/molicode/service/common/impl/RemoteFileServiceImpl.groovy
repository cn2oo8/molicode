package com.shareyi.molicode.service.common.impl

import com.shareyi.molicode.common.utils.LogHelper
import com.shareyi.molicode.common.web.CommonResult
import com.shareyi.molicode.service.common.RemoteFileService
import org.springframework.stereotype.Service

/**
 * 远程文件服务
 *
 * @author david
 * @date 2019/6/15
 */
@Service
class RemoteFileServiceImpl implements RemoteFileService {

    @Override
    CommonResult<String> httpGet(String url) {
        CommonResult result = CommonResult.create();
        try {
            result.succeed();
        } catch (Exception e) {
            LogHelper.EXCEPTION.error("获取资源失败,url={}", url, e);
            result.failed("获取资源失败，url=" + url + ",原因是" + e.getMessage());
        }
        return result;
    }
}
