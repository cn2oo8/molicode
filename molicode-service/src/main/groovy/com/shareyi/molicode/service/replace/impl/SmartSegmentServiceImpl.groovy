package com.shareyi.molicode.service.replace.impl

import com.shareyi.molicode.common.chain.HandlerChainFactoryImpl
import com.shareyi.molicode.common.chain.handler.awares.SmartSegmentHandlerAware
import com.shareyi.molicode.common.context.SmartSegmentContext
import com.shareyi.molicode.common.utils.LogHelper
import com.shareyi.molicode.common.vo.page.SmartSegmentPageVo
import com.shareyi.molicode.common.web.CommonResult
import com.shareyi.molicode.service.replace.SmartSegmentService
import org.springframework.stereotype.Service

/**
 * 智能处理表达式
 *
 * @author zhangshibin
 * @date 2019/5/19
 */
@Service
class SmartSegmentServiceImpl implements SmartSegmentService {


    @Override
    CommonResult<String> execute(SmartSegmentPageVo smartSegmentPageVo) {
        CommonResult<String> result = new CommonResult<String>();
        try {
            SmartSegmentContext context = SmartSegmentContext.create(smartSegmentPageVo);
            HandlerChainFactoryImpl.executeByHandlerAware(SmartSegmentHandlerAware.class, context)
            result.succeed();
        } catch (Exception e) {
            LogHelper.EXCEPTION.error("执行失败，param={}", smartSegmentPageVo, e)
            result.failed("执行失败，原因是" + e.getMessage())
        }
        return result;
    }
}
