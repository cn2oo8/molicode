package com.shareyi.molicode.common.enums;

/**
 * 来源名称
 *
 * @author zhangshibin
 * @date 2019/7/4
 */
public enum TableSourceNameEnum implements EnumCode<String> {

    DATABASE("database", "数据库"),
    SQL("sql", "sql");

    String code, desc;

    TableSourceNameEnum(String code, String desc) {
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

