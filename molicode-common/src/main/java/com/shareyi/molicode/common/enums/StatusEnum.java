package com.shareyi.molicode.common.enums;

/**
 * <p> 数据有效性；1：有效，-1：无效 </p>
 * `status` tinyint(4) DEFAULT '1' COMMENT '数据有效性；1：有效，-1：无效',
 * @author david
 * @date 2018-08-12
 */
public enum StatusEnum implements EnumCode<Integer> {

    /**
     * 有效数据
     */
    YES(1, "有效数据")
    /**
     * 无效数据
     */
    ,NO(-1, "无效数据");

    private final Integer code;
    private final String desc;

    StatusEnum(Integer code, String desc) {
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
