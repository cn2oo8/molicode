package com.shareyi.molicode.common.enums;

/**
 * 源文件来源类型
 *
 * @author zhangshibin
 * @date 2018/10/19
 */
public enum ResourceTypeEnum implements EnumCode<String> {
    FILE("file", "文件"),
    FRONT("front", "前台"),
    HTTP("http", "http"),
    DATABASE("database", "数据库");

    private String code;
    private String desc;


    ResourceTypeEnum(String code, String desc) {
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
