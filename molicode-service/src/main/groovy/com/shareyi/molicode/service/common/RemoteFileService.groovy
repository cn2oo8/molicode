package com.shareyi.molicode.service.common;

import com.shareyi.molicode.common.web.CommonResult;

/**
 * 远程文件服务
 *
 * @author david
 * @date 2019/6/15
 */
interface RemoteFileService {
    /**
     * 获取远程文件内容
     *
     * @param url
     * @return
     */
    CommonResult<String> httpGet(String url);
}
