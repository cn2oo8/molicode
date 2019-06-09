package com.shareyi.molicode.common.vo.page

/**
 * 表模型页面入参
 * @author david
 * @since 2018/8/26
 */
class TableModelPageVo {

    /**
     * 项目key
     */
    String projectKey
    /**
     * 表模型存放目录
     */
    String tableModelDir
    /**
     * 表名
     */
    String tableName
    /**
     * 中文名称
     */
    String cnname
    /**
     * 智能标识
     */
    int smartFlag = 1
    /**
     * 模型类型
     */
    String modelType


    @Override
    String toString() {
        return "TableModelPageVo{" +
                "projectKey='" + projectKey + '\'' +
                ", tableModelDir='" + tableModelDir + '\'' +
                ", tableName='" + tableName + '\'' +
                ", cnname='" + cnname + '\'' +
                ", smartFlag=" + smartFlag +
                '}';
    }
}
