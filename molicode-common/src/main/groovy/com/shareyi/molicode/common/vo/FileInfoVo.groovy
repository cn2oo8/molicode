package com.shareyi.molicode.common.vo;
/**
 * 文件相关信息VO
 */
class FileInfoVo {

	/**
	 * 文件名称
	 */
	 String name;
	/**
	 * 父路径
	 */
	 String parentPath;
	/**
	 * 文件类型
	 */
	 Integer fileType; //1 文件 2文件夹
	/**
	 * 上次修改时间
	 */
	 long lastModified;
	/**
	 * 是否隐藏
	 */
	 boolean hidden;
	/**
	 * 是否可写入
	 */
	 boolean canWrite;
}
