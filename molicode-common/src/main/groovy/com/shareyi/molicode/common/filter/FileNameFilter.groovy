package com.shareyi.molicode.common.filter;


interface FileNameFilter {
		
	/**
	 * 列是否符合过滤器
	 * @param name
	 * @return
	 */
	boolean isMatch(String name);
}
