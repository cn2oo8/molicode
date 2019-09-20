package com.shareyi.molicode.handler.smartsegment

import com.shareyi.molicode.common.chain.handler.AbstractContinueHandler
import com.shareyi.molicode.common.chain.handler.awares.SmartSegmentUnitHandlerAware
import com.shareyi.molicode.common.context.SmartSegmentUnitContext
import com.shareyi.molicode.common.utils.LogHelper
import com.shareyi.molicode.common.utils.Profiles
import org.springframework.stereotype.Service

/**
 * 片段后置处理器，文档覆盖
 *
 * @author david
 * @date 2019/5/20
 */
@Service
class SegmentPostProcessHandler extends AbstractContinueHandler<SmartSegmentUnitContext>
        implements SmartSegmentUnitHandlerAware {
    @Override
    int getOrder() {
        return 3;
    }

    @Override
    protected void continueHandle(SmartSegmentUnitContext smartSegmentUnitContext) throws Exception {
        //如果内容未修改，直接返回
        if (!smartSegmentUnitContext.contentChanged) {
            return;
        }
        File file = smartSegmentUnitContext.getFile();
        file.withWriter(Profiles.instance.fileEncoding) { writer -> writer.write smartSegmentUnitContext.processedContent; }
        LogHelper.FRONT_CONSOLE.info("文件进行了处理，path={}, size={}", file.getAbsolutePath(), smartSegmentUnitContext.getSegmentInfoBoList().size())
    }
}
