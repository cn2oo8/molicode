package com.shareyi.molicode.common.vo.code

/**
 * 模板输出结果对象
 *
 * @author david
 * @date 2019/7/5
 */
class TemplateResultVo {

    /**
     * id
     */
    String id;
    /**
     * 名称
     */
    String name;
    /**
     * 描述信息
     */
    String desc;

    /**
     * 渲染后的相对路径
     */
    String relativePath;

    /**
     * 渲染后的内容
     */
    String renderedContent;

    /**
     * 输出目录
     */
    String outputDir;

    /**
     * 项目key
     */
    String projectKey;
}
