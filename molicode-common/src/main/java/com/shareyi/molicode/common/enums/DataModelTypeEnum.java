package com.shareyi.molicode.common.enums;

/**
 * 模型处理器类型
 *
 * @author david
 * @date 2018/9/1
 */
public enum DataModelTypeEnum implements EnumCode<String> {

    TABLE_MODEL("tableModel", "表模型"),
    JSON("json", "json格式"),
    LINE_LIST("lineList", "单行文本"),
    CELL_LIST("cellList", "文本单元格"),
    JAVA_SOURCE("javaSource", "Java源码"),
    RAW_CONTENT("rawContent", "原始内容");

    private final String code;
    private final String desc;

    DataModelTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

}
