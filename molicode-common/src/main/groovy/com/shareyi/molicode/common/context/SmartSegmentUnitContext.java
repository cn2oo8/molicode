package com.shareyi.molicode.common.context;

import com.google.common.collect.Lists;
import com.shareyi.molicode.common.bo.SegmentInfoBo;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 智能片段(单元文件)上下文
 *
 * @author zhangshibin
 * @date 2019/5/19
 */
public class SmartSegmentUnitContext {

    /**
     * 待处理文件
     */
    private File file;
    /**
     * 页面入参
     */
    private SmartSegmentContext smartSegmentContext;

    /**
     * 文件内容读取
     */
    private List<String> lineContentList;

    /**
     * 查询到的片段列表
     */
    private List<SegmentInfoBo> segmentInfoBoList = Lists.newArrayList();

    /**
     * 起始行号的片段map
     */
    private transient Map<Integer, SegmentInfoBo> startLineIndexSegmentMap;
    /**
     * 内容是否被修改
     */
    private boolean contentChanged;

    /**
     * 处理之后的文件内容
     */
    private String processedContent;

    public static SmartSegmentUnitContext create(File file, SmartSegmentContext smartSegmentContext) {
        SmartSegmentUnitContext unitContext = new SmartSegmentUnitContext();
        unitContext.file = file;
        unitContext.smartSegmentContext = smartSegmentContext;
        return unitContext;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public SmartSegmentContext getSmartSegmentContext() {
        return smartSegmentContext;
    }

    public void setSmartSegmentContext(SmartSegmentContext smartSegmentContext) {
        this.smartSegmentContext = smartSegmentContext;
    }

    public List<String> getLineContentList() {
        return lineContentList;
    }

    public void setLineContentList(List<String> lineContentList) {
        this.lineContentList = lineContentList;
    }

    public List<SegmentInfoBo> getSegmentInfoBoList() {
        return segmentInfoBoList;
    }

    public void setSegmentInfoBoList(List<SegmentInfoBo> segmentInfoBoList) {
        this.segmentInfoBoList = segmentInfoBoList;
    }

    public Map<Integer, SegmentInfoBo> getStartLineIndexSegmentMap() {
        return startLineIndexSegmentMap;
    }

    public void setStartLineIndexSegmentMap(Map<Integer, SegmentInfoBo> startLineIndexSegmentMap) {
        this.startLineIndexSegmentMap = startLineIndexSegmentMap;
    }

    public boolean isContentChanged() {
        return contentChanged;
    }

    public void setContentChanged(boolean contentChanged) {
        this.contentChanged = contentChanged;
    }

    public String getProcessedContent() {
        return processedContent;
    }

    public void setProcessedContent(String processedContent) {
        this.processedContent = processedContent;
    }

    public SmartSegmentUnitContext addSegment(SegmentInfoBo segmentInfoBo) {
        segmentInfoBoList.add(segmentInfoBo);
        return this;
    }

    /**
     * 为待处理初始化数据
     *
     * @return
     */
    public SmartSegmentUnitContext initForProcess() {
        startLineIndexSegmentMap = new HashMap<>();
        for (SegmentInfoBo segmentInfoBo : segmentInfoBoList) {
            startLineIndexSegmentMap.put(segmentInfoBo.getStartLineIndex(), segmentInfoBo);
        }
        return this;
    }
}
