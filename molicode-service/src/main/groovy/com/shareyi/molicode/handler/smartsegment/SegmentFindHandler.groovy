package com.shareyi.molicode.handler.smartsegment

import com.google.common.collect.Lists
import com.shareyi.molicode.common.bo.SegmentInfoBo
import com.shareyi.molicode.common.chain.handler.AbstractInterruptibleHandler
import com.shareyi.molicode.common.chain.handler.awares.SmartSegmentUnitHandlerAware
import com.shareyi.molicode.common.context.SmartSegmentUnitContext
import com.shareyi.molicode.common.utils.LogHelper
import com.shareyi.molicode.common.utils.Profiles
import org.apache.commons.collections.CollectionUtils
import org.apache.commons.io.IOUtils
import org.springframework.stereotype.Service

/**
 * 片段查找器
 *
 * @author david
 * @date 2019/5/20
 */
@Service
class SegmentFindHandler extends AbstractInterruptibleHandler<SmartSegmentUnitContext>
        implements SmartSegmentUnitHandlerAware {
    @Override
    int getOrder() {
        return 0;
    }

    @Override
    boolean shouldHandle(SmartSegmentUnitContext smartSegmentUnitContext) {
        return true;
    }

    @Override
    boolean doHandle(SmartSegmentUnitContext smartSegmentUnitContext) {
        File file = smartSegmentUnitContext.getFile();
        final Map<String, Object> scriptContext = smartSegmentUnitContext.smartSegmentContext.scriptContext;
        if (!checkFile(file)) {
            return false;
        }

        //step 1: 读取文件内容
        final List<String> lineContentList = Lists.newArrayList();

        FileInputStream fileInputStream = new FileInputStream(file);
        String content = null;
        try {
            content = IOUtils.toString(fileInputStream, Profiles.instance.fileEncoding);
        } finally {
            IOUtils.closeQuietly(fileInputStream);
        }
        if (content == null) {
            return false;
        }

        content.eachLine { line ->
            lineContentList.add(line);
        }
        if (content.endsWith("\n")) {
            lineContentList.add("");
        }
        smartSegmentUnitContext.setLineContentList(lineContentList);

        //step 2: 进行segment查找
        SegmentInfoBo segmentInfoBo = null;
        for (int lineIndex = 0; lineIndex < lineContentList.size(); lineIndex++) {
            String line = lineContentList.get(lineIndex);
            if (scriptContext['segmentStartFinder'](line, lineContentList, lineIndex)) {
                segmentInfoBo = SegmentInfoBo.create(lineContentList, lineIndex);
            }
            if (segmentInfoBo != null) {
                segmentInfoBo.segmentLineStack.add(line);
                if (scriptContext['segmentEndFinder'](line, lineContentList, lineIndex)) {
                    if (scriptContext['segmentFilter'] == null || scriptContext['segmentFilter'](segmentInfoBo)) {
                        segmentInfoBo.endLineIndex = lineIndex
                        smartSegmentUnitContext.addSegment(segmentInfoBo);
                    }
                    segmentInfoBo = null;
                }
            }
        }
       
        if (CollectionUtils.isNotEmpty(smartSegmentUnitContext.segmentInfoBoList)) {
            LogHelper.FRONT_CONSOLE.info("文件下一共找到{}个片段， path={}", smartSegmentUnitContext.segmentInfoBoList.size(), file.getAbsolutePath());
        }

        return true;
    }

    /**
     * 验证文件是否可以处理
     *
     * @param file
     * @return 是否可处理
     */
    private boolean checkFile(File file) {
        if (!file.exists()) {
            LogHelper.FRONT_CONSOLE.warn("文件不存在， filePath={}", file.getAbsolutePath());
            return false;
        }
        if (!file.canRead()) {
            LogHelper.FRONT_CONSOLE.warn("文件不可读， filePath={}", file.getAbsolutePath());
            return false;
        }
        if (!file.canRead()) {
            LogHelper.FRONT_CONSOLE.warn("文件不可写， filePath={}", file.getAbsolutePath());
            return false;
        }
        return true;
    }
}
