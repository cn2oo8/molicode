package com.shareyi.molicode.common.filter.impl;

import com.shareyi.molicode.common.filter.ColumnFilter;
import com.shareyi.molicode.common.vo.code.ColumnVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class NameExpressionFilter implements ColumnFilter {

	String nameExpression;
	private List<ColumnFilterType> filterTypes;
	boolean initialed=false;
	private boolean not=false;
	
	

	public NameExpressionFilter(){}
	
	public NameExpressionFilter(String nameExpression){
		this.nameExpression=nameExpression;
	}

	
	
	
	
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
		String dataName=column.getDataName().toLowerCase();
		initNameFilter();
		boolean isMatch=false;
		//为空 ，默认全过
		if(CollectionUtils.isEmpty(filterTypes)){
			return true;
		}
		
		
		for(ColumnFilterType columnFilterType : filterTypes){
				if(columnFilterType.getType()==0){
					isMatch=dataName.equals(columnFilterType.getSearchText());
					if(isMatch){
						break;
					}
				}
				
				if(columnFilterType.getType()==1){
					isMatch=dataName.endsWith(columnFilterType.getSearchText());
					if(isMatch){
						break;
					}
				}
				
				if(columnFilterType.getType()==2){
					isMatch=dataName.startsWith(columnFilterType.getSearchText());
					if(isMatch){
						break;
					}
				}
				
				if(columnFilterType.getType()==3){
					isMatch=dataName.contains(columnFilterType.getSearchText());
					if(isMatch){
						break;
					}
				}
			}
		
		if(not){//如果取非 只要有1个匹配上，认为失败！
			return !isMatch;
		}else{
			return isMatch;
		}
	}

	
	
	public void initNameFilter(){
		if(StringUtils.isEmpty(nameExpression) || initialed){
			return;
		}
		
	
		if(nameExpression.charAt(0)=='^'){	
			nameExpression=nameExpression.substring(1).trim();
			if(nameExpression.charAt(0)=='('||nameExpression.charAt(0)=='（'){
				nameExpression=nameExpression.substring(1).trim();
			}
			if(nameExpression.endsWith(")")||nameExpression.endsWith("）")){
				nameExpression=nameExpression.substring(0,nameExpression.length()-1).trim();
			}
 			not=true;
		}
		String[] filters=nameExpression.toLowerCase().split("[,，]"); 
		filterTypes=ColumnFilterType.getColumnFilterType(filters);
		initialed=true;
	}
	
	
	
	
	public String getNameFilter() {
		return nameExpression;
	}

	
	
	public void setNameFilter(String nameFilter) {
		this.nameExpression = nameFilter;
	}
	
	

}
