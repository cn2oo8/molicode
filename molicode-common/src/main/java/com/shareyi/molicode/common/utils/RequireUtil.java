package com.shareyi.molicode.common.utils;


import com.shareyi.molicode.common.vo.code.ColumnVo;
import com.shareyi.molicode.common.vo.code.RequireConfigVo;
import com.shareyi.molicode.common.vo.code.RequireInfoVo;
import com.shareyi.molicode.common.vo.code.TableModelVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import java.util.List;
import java.util.Map;

/**
 * @author david
 * @date 2016/4/24
 */
public class RequireUtil {

    /**
     * require配置信息map
     */
    public Map<String, RequireConfigVo> requireConfigMap;


    /**
     * 获取requireJs的依赖信息
     *
     * @param tableModel
     * @param listName
     * @return
     */
    public RequireInfoVo getRequireInfo(TableModelVo tableModel, String listName) {

        RequireInfoVo requireInfo = new RequireInfoVo();
        List<ColumnVo> columnList = tableModel.getTableDefine().getColumns();
        List<String> columnNameList = tableModel.getColumnNameList(listName);

        if (CollectionUtils.isEmpty(columnList) || CollectionUtils.isEmpty(columnNameList) || MapUtils.isEmpty(requireConfigMap)) {
            return requireInfo;
        }

        for (ColumnVo column : columnList) {
            if (columnNameList.contains(column.getColumnName())) {
                checkColumnNeedRequire(requireInfo, column);
            }
        }

        requireInfo.sortModule();
        return requireInfo;

    }


    /**
     * 获取requireJs的依赖信息
     *
     * @param tableModel
     * @param columns
     * @return
     */
    public RequireInfoVo getRequireInfoByColumns(TableModelVo tableModel, String columns) {

        RequireInfoVo requireInfo = new RequireInfoVo();
        List<ColumnVo> columnList = tableModel.getTableDefine().getColumns();
        List<String> columnNameList = PubUtils.stringToList(columns);
        if (CollectionUtils.isEmpty(columnList) || CollectionUtils.isEmpty(columnNameList) || MapUtils.isEmpty(requireConfigMap)) {
            return requireInfo;
        }

        for (ColumnVo column : columnList) {
            if (columnNameList.contains(column.getColumnName())) {
                checkColumnNeedRequire(requireInfo, column);
            }
        }
        requireInfo.sortModule();
        return requireInfo;

    }

    /**
     * 验证列是否需要require js支持
     *
     * @param requireInfo
     * @param column
     */
    private void checkColumnNeedRequire(RequireInfoVo requireInfo, ColumnVo column) {

        RequireConfigVo requireConfig = requireConfigMap.get(column.getJspTag());
        if (requireConfig == null) {
            requireConfig = requireConfigMap.get(column.getJspTag().toUpperCase());
        }
        if (requireConfig == null) {
            requireConfig = requireConfigMap.get(column.getJspTag().toLowerCase());
        }


        if (requireConfig != null) {
            requireInfo.addColumnToModule(column, requireConfig);
        }
    }


    public Map<String, RequireConfigVo> getRequireConfigVoMap() {
        return requireConfigMap;
    }

    public void setRequireConfigMap(Map<String, RequireConfigVo> requireConfigMap) {
        this.requireConfigMap = requireConfigMap;
    }
}
