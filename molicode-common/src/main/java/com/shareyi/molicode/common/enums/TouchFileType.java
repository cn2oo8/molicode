package com.shareyi.molicode.common.enums;

/**
 * Created by david on 2016/1/3.
 */
public enum  TouchFileType {

    NONE(0,"不做处理"),
    EXTEND_DIR(1,"路径扩展"),
    SHORTEN_DIR(2,"路径缩短"),
    EXCHANGE_DIR(3,"路径交换");



    private int type;
    private String name;

    TouchFileType(int type,String name){
        this.type=type;
        this.name=name;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
