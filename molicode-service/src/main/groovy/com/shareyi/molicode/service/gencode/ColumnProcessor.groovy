package com.shareyi.molicode.service.gencode

import com.shareyi.molicode.common.vo.code.ColumnVo;
import com.shareyi.molicode.common.vo.code.TableModelVo;

/**
 * 列处理工具类
 * @author david
 *
 */
interface ColumnProcessor {

	
	/**
	 * 执行处理
	 * @param columns
	 */
	void process(TableModelVo tableModel, List<ColumnVo> columns);
}
