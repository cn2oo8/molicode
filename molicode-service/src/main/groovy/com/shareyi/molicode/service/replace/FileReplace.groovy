package com.shareyi.molicode.service.replace

import org.apache.commons.lang.StringUtils

class FileReplace {

	File srcFile;
	File destFile;
	
	public boolean isFile(){
		return srcFile.isFile();
	}
	
	public boolean isDirectory(){
		return srcFile.isDirectory();
	}
	
	public File getDestSubFile(String subFileName){
		//如果子目录为空，就返回原来的父目录
		if(StringUtils.isNotEmpty(subFileName)){
			return new File(destFile,subFileName);
		}else{
			return destFile;
		}

	}
}
