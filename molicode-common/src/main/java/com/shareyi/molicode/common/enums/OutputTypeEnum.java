package com.shareyi.molicode.common.enums;

/**
 * 输出方式枚举
 *
 * @author david
 * @date 2019/6/16
 */
public enum OutputTypeEnum implements EnumCode<String> {

    LOCAL_DIR("1", "本地文件", true),
    ZIP_FILE("2", "zip文件", false),
    FRONT_CONSOLE("3", "前台", false),
    RESPONSE("4", "直接返回", false);

    String code, desc;
    /**
     * 是否需要输出路径
     */
    boolean needOutputDir;

    OutputTypeEnum(String code, String desc, boolean needOutputDir) {
        this.code = code;
        this.desc = desc;
        this.needOutputDir = needOutputDir;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public boolean isNeedOutputDir() {
        return needOutputDir;
    }

}
