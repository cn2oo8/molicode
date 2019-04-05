package com.shareyi.molicode.common.vo.code;

/**
 * 配置相关信息
 * @author david
 * @since 2018-12-12
 */
class ConfigVo {
    /**
     *  基础包名，项目配置
     */
    String basePackage = "";
    /**
     * 子包名，项目配置
     */
    String category = "";
    /**
     *  作者，项目配置
     */
    String author = "";
    /**
     * 当前时间 yyyy-MM-dd 系统自动生成
     */
    String nowDate;
    /**
     * 当前年份 系统自动生成
     */
    String year;
    /**
     * 工程ID前缀 项目配置
     */
    String artifactId = "";
}
