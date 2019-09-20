package com.shareyi.molicode.handler.smartsegment

import com.shareyi.molicode.common.bo.SegmentInfoBo
import com.shareyi.molicode.common.chain.handler.AbstractInterruptibleHandler
import com.shareyi.molicode.common.chain.handler.awares.SmartSegmentUnitHandlerAware
import com.shareyi.molicode.common.context.SmartSegmentUnitContext
import com.shareyi.molicode.common.utils.LogHelper
import org.apache.commons.collections.CollectionUtils
import org.springframework.stereotype.Service

/**
 * 片段内容处理器
 *
 * @author david
 * @date 2019/5/20
 */
@Service
class SegmentProcessorHandler extends AbstractInterruptibleHandler<SmartSegmentUnitContext>
        implements SmartSegmentUnitHandlerAware {
    @Override
    int getOrder() {
        return 1;
    }

    @Override
    boolean shouldHandle(SmartSegmentUnitContext smartSegmentUnitContext) {
        return true;
    }

    @Override
    boolean doHandle(SmartSegmentUnitContext smartSegmentUnitContext) {
        final Map<String, Object> scriptContext = smartSegmentUnitContext.smartSegmentContext.scriptContext;

        //如果没有查找到片段直接返回
        if (CollectionUtils.isEmpty(smartSegmentUnitContext.segmentInfoBoList)) {
            return false;
        }

        //step 2: 进行segment查找
        List<String> lineContentList = smartSegmentUnitContext.lineContentList;
        Map<Integer, SegmentInfoBo> segmentInfoBoMap = smartSegmentUnitContext.initForProcess().startLineIndexSegmentMap;

        StringBuilder processedContent = new StringBuilder()
        //文档是否发生了变化
        boolean contentChanged = false;
        for (int lineIndex = 0; lineIndex < lineContentList.size();) {
            String line = lineContentList.get(lineIndex);
            SegmentInfoBo segmentInfoBo = segmentInfoBoMap.get(lineIndex);
            if (segmentInfoBo != null) {
                String renderContent = scriptContext['segmentProcess'](segmentInfoBo);
                String originSegmentContent = segmentInfoBo.getOriginSegmentContent();
                if (!Objects.equals(renderContent, originSegmentContent)) {
                    contentChanged = true;
                }
                appendContent(lineIndex, processedContent, renderContent);
                lineIndex = segmentInfoBo.endLineIndex + 1;
            } else {
                appendContent(lineIndex, processedContent, line)
                lineIndex++;
            }
        }
        smartSegmentUnitContext.setContentChanged(contentChanged);
        smartSegmentUnitContext.setProcessedContent(processedContent.toString());
      //  LogHelper.FRONT_CONSOLE.info("文件被修改了：{}", processedContent)
        return true;
    }

    /**
     * 追加内容
     * @param lineIndex
     * @param processedContent
     * @param line
     */
    private void appendContent(int lineIndex, StringBuilder processedContent, String content) {
        if (lineIndex != 0) {
            processedContent.append("\n");
        }
        processedContent.append(content)
    }

}
