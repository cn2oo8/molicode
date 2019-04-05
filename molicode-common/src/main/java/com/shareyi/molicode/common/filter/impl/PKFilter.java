package com.shareyi.molicode.common.filter.impl;


import com.shareyi.molicode.common.filter.ColumnFilter;
import com.shareyi.molicode.common.vo.code.ColumnVo;

import java.util.ArrayList;
import java.util.List;

public class PKFilter implements ColumnFilter {

	
	public List<ColumnVo> filterColumns(List<ColumnVo> columns) {
		List<ColumnVo> matchColumns=new ArrayList<ColumnVo>();
		for (ColumnVo column : columns) {
			if(isMatch(column)){
				matchColumns.add(column);
			}
		}
		return matchColumns;
	}

	public boolean isMatch(ColumnVo column) {
		return column.getIsPK() || "id".equals(column.getDataName());
	}

	

}
