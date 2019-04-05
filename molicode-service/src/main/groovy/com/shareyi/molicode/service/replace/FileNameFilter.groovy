package com.shareyi.molicode.service.replace;


interface FileNameFilter {
		
	/**
	 * 列是否符合过滤器
	 * @param name
	 * @return
	 */
	boolean isMatch(String name);
}
