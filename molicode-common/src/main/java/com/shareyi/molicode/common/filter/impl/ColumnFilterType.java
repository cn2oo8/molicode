package com.shareyi.molicode.common.filter.impl;

import java.util.ArrayList;
import java.util.List;

public class ColumnFilterType {
 
	String searchText;
	int type; //0 全匹配，1.后匹配   2.前匹配    3任意匹配 
	String originText;
	boolean not;  //是否取非，即满足条件的去掉
	
	public ColumnFilterType(String originText){
		this.originText=originText;
		
		if(originText.charAt(0)=='^'){	
			originText=originText.substring(1);
			not=true;
		}
		Object o=null;

		int idx=originText.indexOf("*");
		if(idx==-1){
			this.type=0;
			searchText=originText;
		}else{
			int lastIdx=originText.lastIndexOf("*");
			if(idx==0 && lastIdx==idx){
				this.type=1;
				searchText=originText.substring(1);	
			}else if(idx!=0 && lastIdx==idx){
				this.type=2;
				searchText=originText.substring(0,lastIdx);	
			}else if(idx==0 && lastIdx==originText.length()-1){
				this.type=3;
				searchText=originText.substring(1);	
			}else{
				this.type=-1; //非法格式 忽略
			}
		}
		
	}
	
	
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getOriginText() {
		return originText;
	}
	public void setOriginText(String originText) {
		this.originText = originText;
	}
	
	
	public static List<ColumnFilterType> getColumnFilterType(String[] filters){
		ArrayList<ColumnFilterType> list=new ArrayList<ColumnFilterType>();
		for (String filter : filters) {
			if(filter.length()!=0){
					ColumnFilterType columnFilterType=new ColumnFilterType(filter);
					if(columnFilterType.type!=-1){
						list.add(columnFilterType);
					}
			}
		}
		return list;
	} 
	
}
