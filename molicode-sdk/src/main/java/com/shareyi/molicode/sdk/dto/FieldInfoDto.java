package com.shareyi.molicode.sdk.dto;

/**
 * 字段信息
 *
 * @author zhangshibin
 * @date 2018/10/28
 */
public class FieldInfoDto {

    /**
     * 字段名称
     */
    private String dataName;
    /**
     * 字段名称
     */
    private String columnName;
    /**
     * 备注
     */
    private String comment;
    /**
     * 字段类型
     */
    private String fieldClass;

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFieldClass() {
        return fieldClass;
    }

    public void setFieldClass(String fieldClass) {
        this.fieldClass = fieldClass;
    }
}
