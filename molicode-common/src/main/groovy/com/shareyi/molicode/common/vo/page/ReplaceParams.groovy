package com.shareyi.molicode.common.vo.page

/**
 * 替换参数
 * @author david
 * @since 2018/9/2
 */
class ReplaceParams {

    /**
     * 原路径
     */
    String srcPath;
    /**
     * 目标路径
     */
    String destPath;
    /**
     * 不处理替换，但是会复制
     */
    String ignoreExp;
    /**
     * 丢弃的文件
     */
    String throwExp;
    /**
     * 替换类型
     */
    String replaceType;
    /**
     * 替换表达式
     */
    String replaceExp;
    /**
     * 文件目录转换表达式
     */
    String dirReplaceExp;
}
