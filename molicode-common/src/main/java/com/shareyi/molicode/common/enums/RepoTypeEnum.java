package com.shareyi.molicode.common.enums;

/**
 * 仓库类型
 *
 * @author zhangshibin
 * @date 2019/6/10
 */
public enum RepoTypeEnum implements EnumCode<String> {

    SAMPLE_PROJECT("sampleProject", "示例工程"),
    TEMPLATE("template", "模板仓库");

    private String code;
    private String desc;

    RepoTypeEnum(String code, String desc) {
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
