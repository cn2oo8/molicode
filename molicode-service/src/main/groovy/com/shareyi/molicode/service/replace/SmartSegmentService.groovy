package com.shareyi.molicode.service.replace

import com.shareyi.molicode.common.vo.page.SmartSegmentPageVo
import com.shareyi.molicode.common.web.CommonResult

/**
 * 智能片段处理器
 * @author david
 * @date 2019-05-20
 */
interface SmartSegmentService {

    /**
     * 执行智能片段处理器
     * @param smartSegmentPageVo 转换参数
     * @return
     */
    CommonResult<String> execute(SmartSegmentPageVo smartSegmentPageVo)

}
