package com.shareyi.molicode.service.replace

import com.shareyi.molicode.common.vo.page.ReplaceParams
import com.shareyi.molicode.common.web.CommonResult

/**
 * 文件复制到新路径，及文件内容和目录名称替换
 */
interface CopyAndReplaceService {

    /**
     * 执行文件复制和内容相关替换
     * @param replaceParams 转换参数
     * @return
     */
    CommonResult<String> doCopyAndReplace(ReplaceParams replaceParams)

}
