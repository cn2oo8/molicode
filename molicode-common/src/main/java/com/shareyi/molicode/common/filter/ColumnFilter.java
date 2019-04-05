package com.shareyi.molicode.common.filter;

import com.shareyi.molicode.common.vo.code.ColumnVo;

import java.util.List;

public interface ColumnFilter {
		
	/**
	 * 获取符合规则的列
	 * @param columns
	 * @return
	 */
	List<ColumnVo> filterColumns(List<ColumnVo> columns);
	

	/**
	 * 列是否符合过滤器
	 * @param column
	 * @return
	 */
	boolean isMatch(ColumnVo column);
}
