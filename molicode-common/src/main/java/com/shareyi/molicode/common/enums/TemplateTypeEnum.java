package com.shareyi.molicode.common.enums;

/**
 * 模板类型
 *
 * @author david
 * @date 2018/9/1
 */
public enum TemplateTypeEnum implements EnumCode<String> {

    LOCAL("local", "本地"),
    MAVEN("maven", "maven");

    private final String code;
    private final String desc;

    TemplateTypeEnum(String code, String desc) {
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
