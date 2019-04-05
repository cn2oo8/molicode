package com.shareyi.molicode.service.gencode

import com.shareyi.molicode.common.vo.code.SimpleTableInfoVo
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
}
