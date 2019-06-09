package com.shareyi.molicode.common.vo.code

import com.alibaba.fastjson.annotation.JSONField
import com.shareyi.molicode.common.filter.impl.PKFilter
import com.shareyi.molicode.common.utils.TableNameUtil
import org.apache.commons.collections.CollectionUtils

/**
 * 表定义信息
 * @author david
 * @since 2018/12/12
 */
class TableDefineVo {
    /**
     * id
     */
    private String id;
    /**
     * 分页大小
     */
    private String pageSize;
    /**
     * 表中文名称
     */
    private String cnname;
    /**
     * 是否需要分页
     */
    private Boolean isPaged;
    /**
     * 数据库表名称
     */
    private String dbTableName;
    /**
     * 列map，方便通过key获取
     */
    @JSONField(serialize = false)
    Map<String, ColumnVo> columnMap;
    /**
     * 所有列
     */
    private List<ColumnVo> columns;
    /**
     * 主键列
     */
    @JSONField(serialize = false)
    private List<ColumnVo> pkColumns;


    List<ColumnVo> getColumns() {
        return columns;
    }

    void setColumns(List<ColumnVo> columns) {
        this.columns = columns;
    }

    String getId() {
        return id;
    }

    void setId(String id) {
        this.id = id;
    }

    String getPageSize() {
        return pageSize;
    }

    void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    String getCnname() {
        return cnname;
    }

    void setCnname(String cnname) {
        this.cnname = cnname;
    }

    Boolean getIsPaged() {
        return isPaged;
    }

    void setIsPaged(Boolean isPaged) {
        this.isPaged = isPaged;
    }

    String getDbTableName() {
        return dbTableName;
    }

    void setDbTableName(String dbTableName) {
        this.dbTableName = dbTableName;
    }


    Map<String, ColumnVo> getColumnMap() {
        if (columnMap == null) {
            columnMap = new HashMap<String, ColumnVo>();
            for (ColumnVo column : columns) {
                columnMap.put(column.getColumnName(), column);
            }
        }
        return columnMap;
    }

    void setColumnMap(Map<String, ColumnVo> columnMap) {
        this.columnMap = columnMap;
    }

    /**
     * 通过列名获取列信息
     * @param columnName 列名
     * @return 列信息
     */
    ColumnVo getColumnByColumnName(String columnName) {
        return getColumnMap().get(columnName);
    }

    /**
     * 获取主键列表
     * @return
     */
    List<ColumnVo> getPkColumns() {
        if (pkColumns == null) {
            PKFilter pkFilter = new PKFilter();
            pkColumns = pkFilter.filterColumns(columns);
        }
        return pkColumns;
    }

    /**
     * 获取一个主键
     * @return
     */
    ColumnVo getPkColumn() {
        List<ColumnVo> list = getPkColumns();
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 获取变量名称
     * @return
     */
    String getVarDomainName() {
        return TableNameUtil.lowerCaseFirst(this.id);
    }
}
