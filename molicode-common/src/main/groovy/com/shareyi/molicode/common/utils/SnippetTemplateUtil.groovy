package com.shareyi.molicode.common.utils

import com.shareyi.molicode.common.vo.code.ColumnVo
import com.shareyi.molicode.common.vo.code.DictVo
import com.shareyi.molicode.common.vo.code.SnippetTemplateVo

/**
 * 代码片段工具类
 *
 */
class SnippetTemplateUtil {

    Map<String, SnippetTemplateVo> snippetTemplateMap;
    Map<String, DictVo> dictMap;
    Map<String, Object> binding;
    TemplateUtil templateUtil;

    /**
     * 获取字典项内容
     *
     * 通过column对象中的dictName 查找字典数据
     * 通过字典数据，列定义，模板来生成字典项内容
     * 其中
     * @param column 列定义
     * @param templateId 字典项模板id
     * @return
     */
    String getDictTemplate(ColumnVo column, String templateId) {
        DictVo dict = dictMap.get(column.dictName);
        if (dict == null) {
            return "";
        }

        SnippetTemplateVo snippetTemplate = snippetTemplateMap.get(templateId);
        if (snippetTemplate == null) {
            return "";
        }

        def templateBinding = ["dict": dict, "optionList": dict.optionList, "column": column, "templateId": templateId];
        templateBinding.putAll(binding);

        String result = "";
        try {
            result = getTemplateUtil().renderContent(snippetTemplate.template, templateBinding);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 用 列数据+模板片段 生成内容
     * 如果通过模板id未找到模板，则返回空串
     * @param column 列定义
     * @param templateId 模板id
     * @return 生成的内容
     */
    String getTemplate(ColumnVo column, String templateId) {
        SnippetTemplateVo snippetTemplate = snippetTemplateMap.get(templateId);
        if (snippetTemplate == null) {
            return "";
        }

        def templateBinding = ["column": column, "templateId": templateId];
        templateBinding.putAll(binding);
        String result = "";
        try {
            result = getTemplateUtil().renderContent(snippetTemplate.template, templateBinding);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 用 模板片段 生成内容
     * 如果通过模板id未找到模板，则返回空串
     * @param templateId 模板id
     * @return 生成的内容
     */
    String getTemplate(String templateId) {
        SnippetTemplateVo snippetTemplate = snippetTemplateMap.get(templateId);
        if (snippetTemplate == null) {
            return "";
        }

        def templateBinding = ["templateId": templateId];
        templateBinding.putAll(binding);
        String result = "";
        try {
            result = getTemplateUtil().renderContent(snippetTemplate.template, templateBinding);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 用 模板片段 生成内容
     * 如果通过模板id未找到模板，则返回空串
     * @param dataMap
     * @param templateId 模板id
     * @return 生成的内容
     */
    String getTemplateByMap(Map dataMap, String templateId) {
        SnippetTemplateVo snippetTemplate = snippetTemplateMap.get(templateId);
        if (snippetTemplate == null) {
            return "";
        }

        if (dataMap == null) {
            dataMap = [:];
        }
        dataMap.put("templateId", templateId);
        dataMap.putAll(binding);
        String result = "";
        try {
            result = getTemplateUtil().renderContent(snippetTemplate.template, dataMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 用 模板片段 生成内容
     * 如果通过模板id未找到模板，则返回空串
     * @param dataMap
     * @param templateId 模板id
     * @return 生成的内容
     */
    String getTemplateByData(Object data, String templateId) {
        SnippetTemplateVo snippetTemplate = snippetTemplateMap.get(templateId);
        if (snippetTemplate == null) {
            return "";
        }

        def templateBinding = ["templateId": templateId, "data": data];
        templateBinding.putAll(binding);
        String result = "";
        try {
            result = getTemplateUtil().renderContent(snippetTemplate.template, templateBinding);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    TemplateUtil getTemplateUtil() {
        if (templateUtil == null) {
            templateUtil = new TemplateUtil();
        }
        return templateUtil;
    }
}
