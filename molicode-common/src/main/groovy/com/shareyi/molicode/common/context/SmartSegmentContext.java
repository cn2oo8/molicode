package com.shareyi.molicode.common.context;

import com.shareyi.molicode.common.filter.FileNameFilter;
import com.shareyi.molicode.common.vo.page.SmartSegmentPageVo;

import java.util.Map;

/**
 * 智能片段上下文
 *
 * @author david
 * @date 2019/5/19
 */
public class SmartSegmentContext {

    /**
     * 页面入参
     */
    private SmartSegmentPageVo segmentPageVo;

    /**
     * 是否执行成功
     */
    private boolean success;
    /**
     * 脚本解析后的工具类，存储上下文
     */
    private Map<String, Object> scriptContext;
    /**
     * 白名单过滤器
     */
    private FileNameFilter whiteListFilter;
    /**
     * 忽略表达式
     */
    private FileNameFilter ignoreFilter;

    public SmartSegmentPageVo getSegmentPageVo() {
        return segmentPageVo;
    }

    public void setSegmentPageVo(SmartSegmentPageVo segmentPageVo) {
        this.segmentPageVo = segmentPageVo;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static SmartSegmentContext create(SmartSegmentPageVo segmentPageVo) {
        SmartSegmentContext context = new SmartSegmentContext();
        context.segmentPageVo = segmentPageVo;
        return context;
    }

    public void setScriptContext(Map<String, Object> scriptContext) {
        this.scriptContext = scriptContext;
    }

    public Map<String, Object> getScriptContext() {
        return scriptContext;
    }

    public void setWhiteListFilter(FileNameFilter fileNameFilter) {
        this.whiteListFilter = fileNameFilter;
    }

    public void setIgnoreFilter(FileNameFilter ignoreFilter) {
        this.ignoreFilter = ignoreFilter;
    }

    public FileNameFilter getWhiteListFilter() {
        return whiteListFilter;
    }

    public FileNameFilter getIgnoreFilter() {
        return ignoreFilter;
    }
}
