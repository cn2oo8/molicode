package com.shareyi.molicode.service.gencode

import com.shareyi.molicode.common.vo.code.SimpleTableInfoVo
import com.shareyi.molicode.common.vo.code.TableModelVo
import com.shareyi.molicode.common.vo.page.TableModelPageVo
import com.shareyi.molicode.common.web.CommonResult

/**
 * 数据库表模型服务
 */
interface DatabaseTableService {

    /**
     * 生成数据库表名的tableModel xml文件
     * @param tName 表名
     */
    CommonResult<List<SimpleTableInfoVo>> getTableList(String projectKey);

    /**
     * 生成数据库表名的tableModel.xml文件
     * @param tableModelPageVo
     * @return
     */
    CommonResult generateTableModel(TableModelPageVo tableModelPageVo);

    /**
     * 通过表名获取表结构
     * @param projectKey
     * @param tableName
     * @return
     */
    CommonResult<TableModelVo> getTableInfo(String projectKey, String tableName)

    /**
     * 保存表模型
     * @param projectKey
     * @param tableModelJson
     * @return
     */
    CommonResult<Object> saveTableModel(String projectKey, String tableModelJson)
}
