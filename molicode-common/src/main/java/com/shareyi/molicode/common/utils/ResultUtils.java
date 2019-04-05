package com.shareyi.molicode.common.utils;

import com.shareyi.molicode.common.web.CommonResult;

/**
 * 结果工具类
 *
 * @author david
 * @date 2018/9/1
 */
public class ResultUtils {

    /**
     * 传递结果集
     *
     * @param local  本地result
     * @param remote 远程result
     * @return 转换后的result
     */
    public static CommonResult passResult(CommonResult local, CommonResult remote) {
        if (local == null) {
            local = new CommonResult();
        }
        return local.setResultInfo(remote.isSuccess(), remote.getMessage(), remote.getReturnCode());
    }
}
