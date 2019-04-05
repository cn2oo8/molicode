package com.shareyi.molicode.common.vo.code;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import java.util.*;

/**
 * requireJs依赖信息
 * Created by david on 2016/4/24.
 */
class RequireInfoVo {

    Set<String> columnTagSet;
    List<RequireConfigVo> requireConfigList;
    Map<String, List<ColumnVo>> columnListMap;
    String listName;


     RequireInfoVo() {
        columnTagSet = new HashSet<String>();
        requireConfigList = new ArrayList<RequireConfigVo>();
        columnListMap = new HashMap<String, List<ColumnVo>>();
    }

    /**
     * 是否含有依赖模块
     * @return
     */
    boolean hasModule() {
        return CollectionUtils.isNotEmpty(requireConfigList);
    }

    /**
     * 是否含有依赖模块
     * @param moduleName
     * @return
     */
    boolean containsModule(String moduleName) {
        if (MapUtils.isNotEmpty(columnListMap)) {
            return columnListMap.containsKey(moduleName);
        }
        return false;
    }

    /**
     * 是否含有依赖模块
     * @param columnTag
     * @return
     */
    boolean containsTag(String columnTag) {
        if (CollectionUtils.isNotEmpty(columnTagSet)) {
            return columnTagSet.contains(columnTag);
        }
        return false;
    }

    /**
     * 聚合依赖模块  'jquery','xheditor'
     * @return
     */
    String getModuleNames(String wrap) {
        if (CollectionUtils.isEmpty(requireConfigList)) {
            return "";
        }

        StringBuilder moduleInfo = new StringBuilder();
        Set<String> moduleSet = new HashSet<String>();
        for (int i = 0; i < requireConfigList.size(); i++) {
            RequireConfigVo requireConfig = requireConfigList.get(i);
            if (moduleSet.contains(requireConfig.requireModule)) {
                continue;
            } else {
                moduleSet.add(requireConfig.getRequireModule());
            }
            if (i > 0) {
                moduleInfo.append(",");
            }
            moduleInfo.append(wrap).append(requireConfig.getRequireModule()).append(wrap);
        }
        return moduleInfo.toString();
    }

    /**
     * 聚合依赖模块  'jquery','xheditor'
     * @return
     */
    String getRequireText() {
        if (CollectionUtils.isEmpty(requireConfigList)) {
            return "";
        }

        StringBuilder moduleInfo = new StringBuilder();
        Set<String> moduleSet = new HashSet<String>();
        for (int i = 0; i < requireConfigList.size(); i++) {
            RequireConfigVo requireConfig = requireConfigList.get(i);
            if (moduleSet.contains(requireConfig.requireModule)) {
                continue;
            } else {
                moduleSet.add(requireConfig.getRequireModule());
            }
            moduleInfo.append(requireConfig.getRequireText()).append("\n");
        }
        return moduleInfo.toString();
    }


    List<ColumnVo> getColumnListByModule(String module) {
        return columnListMap.get(module);
    }

    List<RequireConfigVo> getRequireConfigList() {
        return requireConfigList;
    }

    void setRequireConfigList(List<RequireConfigVo> requireConfigList) {
        this.requireConfigList = requireConfigList;
    }

    Map<String, List<ColumnVo>> getColumnListMap() {
        return columnListMap;
    }

    void setColumnListMap(Map<String, List<ColumnVo>> columnListMap) {
        this.columnListMap = columnListMap;
    }

    String getListName() {
        return listName;
    }

    void setListName(String listName) {
        this.listName = listName;
    }

    /**
     * 添加列到模块中
     * @param column
     * @param requireConfig
     */
    void addColumnToModule(ColumnVo column, RequireConfigVo requireConfig) {
        List<ColumnVo> columnList = null;
        if (columnListMap.containsKey(requireConfig.getRequireModule())) {
            columnList = columnListMap.get(requireConfig.getRequireModule());
        } else {
            requireConfigList.add(requireConfig);
            columnList = new ArrayList<ColumnVo>();
            columnListMap.put(requireConfig.getRequireModule(), columnList);
        }
        columnTagSet.add(requireConfig.getColumnTag());
        columnList.add(column);
    }

    /**
     * 排序
     */
    void sortModule() {
        if (CollectionUtils.isNotEmpty(requireConfigList)) {
            Collections.sort(requireConfigList);
        }
    }

}
