package com.shareyi.molicode.common.vo.code

import com.shareyi.molicode.common.constants.MoliCodeConstant
import org.apache.commons.collections.CollectionUtils
import org.apache.commons.lang3.StringUtils

/**
 * 表模型结构
 * @author david
 * @since 2018/12/12
 */
class TableModelVo {

    /**
     * table的表定义信息
     */
    TableDefineVo tableDefine;
    /**
     * 排序字段
     */
    List<OrderColumnVo> orderColumns;
    /**
     * 业务字段列表，以逗号分隔
     */
    Map<String, String> bizFieldsMap = new LinkedHashMap<String, String>();

    /**
     * 字段List, 转换为List，方便获取
     */
    Map<String, List<String>> fieldListMap = new HashMap<String, List<String>>();

    /**
     * 字典项map
     */
    Map<String, DictVo> dictMap;

    /**
     * 是否不在List中
     * @param listName
     * @param columnName
     * @return
     */
    boolean isNotInList(String listName, String columnName) {
        return !isInList(listName, columnName);
    }

    /**
     * 是否在List中
     * @param listName
     * @param columnName
     * @return
     */
    boolean isInList(String listName, String columnName) {
        List<String> columnNameList = this.getColumnNameList(listName);
        //如果为空，默认为TRUE
        if (CollectionUtils.isEmpty(columnNameList)) {
            return true;
        }
        return columnNameList.contains(columnName);
    }

    /**
     * 根据listName获取list列表
     * @param listName
     * @return
     */
    List<String> getColumnNameList(String listName) {
        if (fieldListMap.containsKey(listName)) {
            return fieldListMap.get(listName);
        } else {
            return this.parseListStrAndPutIntoMap(listName);
        }
    }

    /**
     * 将字符串转换为List<String>便于之后contains校验，也可以转为Set
     * @param listName
     */
    private List<String> parseListStrAndPutIntoMap(String listName) {
        String listStr = bizFieldsMap.get(listName);
        if (StringUtils.isEmpty(listStr)) {
            listStr = bizFieldsMap.get(MoliCodeConstant.BIZ_FIELDS_KEY_ALLCOLUMN)
        }
        if (StringUtils.isEmpty(listStr)) {
            return null;
        }
        String[] columnNameArr = listStr.split(",");
        List<String> columnNameList = Arrays.asList(columnNameArr);
        fieldListMap.put(listName, columnNameList);
        return columnNameList;
    }

    /**
     * 将字符串转换为List<String>便于之后contains校验，也可以转为Set
     * @param listName
     */
    int listSize(String listName) {
        List<String> columnNameList = this.getColumnNameList(listName)
        //如果为空，默认为全部
        if (CollectionUtils.isEmpty(columnNameList)) {
            return 0;
        }
        return columnNameList.size();
    }

    /**
     * 所有列表中是否包含tag
     * @param tagName
     * @return
     */
    boolean listContainsTag(String tagName) {
        for (ColumnVo column : tableDefine.getColumns()) {
            if (column.getJspTag().equalsIgnoreCase(tagName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 列表中是否包含tag
     * @param listName
     * @param tagName
     * @return
     */
    boolean listContainsTag(String listName, String tagName) {
        List<String> list = getColumnNameList(listName);
        if (CollectionUtils.isEmpty(list)) {
            return false;
        }

        for (ColumnVo column : tableDefine.getColumns()) {
            if (list.contains(column.getColumnName()) && column.getJspTag().equalsIgnoreCase(tagName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 取消，直接通过key获取
     * @return
     */
    @Deprecated
    List<String> getSearchKeyList() {
        return getColumnNameList(MoliCodeConstant.BIZ_FIELDS_KEY_SEARCHKEY);
    }

    /**
     * 设置bizFields
     * @param key
     * @param bizFields
     */
    void putBizFields(String key, String bizFields) {
        bizFieldsMap.put(key, bizFields);
    }

    /**
     * 通过key 获取 bizFields
     * @param key
     */
    String getBizFields(String key) {
        return bizFieldsMap.get(key);
    }

    /**
     * 获取创建时间字段
     */
    String getCreateTime() {
        return bizFieldsMap.get(MoliCodeConstant.BIZ_FIELDS_KEY_CREATETIME)
    }
    /**
     * 获取更新时间字段
     */
    String getUpdateTime() {
        return bizFieldsMap.get(MoliCodeConstant.BIZ_FIELDS_KEY_UPDATETIME)
    }
}
