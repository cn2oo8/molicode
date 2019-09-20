package com.shareyi.molicode.service.gencode

import com.shareyi.molicode.common.vo.code.TableModelVo;

/**
 * sql解析服务
 *
 * @author david
 * @date 2019/7/4
 */
interface SqlParseService {

    /**
     * 解析createSql为tableModel列表
     *
     * @param projectKey
     * @param createSql
     * @param databaseName
     * @return
     */
    List<TableModelVo> parseCreateSql(String projectKey, String createSql, String databaseName)
}
