package com.shareyi.molicode.common.enums;

/**
 * 输出方式枚举
 *
 * @author zhangshibin
 * @date 2019/6/16
 */
public enum OutputTypeEnum implements EnumCode<String> {

    LOCAL_DIR("1", "本地文件"),
    ZIP_FILE("2", "zip文件"),
    FRONT_CONSOLE("3", "前台");

    String code, desc;

    OutputTypeEnum(String code, String desc) {
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
