package com.shareyi.molicode.common.enums;

/**
 * Created by david on 2016/6/6.
 */
public enum EngineType {

    GROOVY("groovy", "groovy模板"),
    VELOCITY("velocity", "velocity引擎"),
    JXLS("jxls", "jxls引擎");


    EngineType(String type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    /**
     * 获取engineType
     *
     * @param type
     * @return
     */
    public static EngineType getEngineType(String type) {
        for (EngineType engineType : values()) {
            if (engineType.type.equalsIgnoreCase(type)) {
                return engineType;
            }
        }
        return null;
    }

    private String type;
    private String typeName;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
