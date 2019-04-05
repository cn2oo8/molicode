package com.shareyi.molicode.common.enums;

/**
 * 数据类型
 *
 * @author david
 * @date 2018/8/26
 */
public enum  DataTypeEnum implements EnumCode<Integer>{

    /**
     * 文本
     */
    TEXT(1, "文本")
    /**
     * JSON
     */
    ,JSON(2, "JSON"),
    /**
     * XML
     */
    XML(3, "XML")
    /**
     * Properties
     */
    ,PROPERTIES(4, "Properties"),
    /**
     * 开关
     */
    SWITCH(5, "开关");

    private final Integer code;
    private final String desc;

    DataTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
