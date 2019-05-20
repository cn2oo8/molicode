package com.shareyi.molicode.common.vo.page

/**
 * 智能片段前台参数
 *
 * @author david
 * @since 2019/5/20
 */
class SmartSegmentPageVo {

    /**
     * 原路径
     */
    String srcPath;
    /**
     * 脚本路径
     */
    String segmentScriptPath;

    /**
     * 待处理白名单表达式
     */
    String whiteListExp;

    /**
     * 不处理替换，但是会复制
     */
    String ignoreExp;
    /**
     * 丢弃的文件
     */
    String throwExp;

    /**
     * json配置信息
     */
    String jsonConfig;


}
