package com.shareyi.molicode.common.enums;

/**
 * Created by davidon 2016/1/5.
 */
public enum DirTransType {

    NONE(0,"不做处理"), //定位符
    EXTEND_DIR(1,"路径扩展"),
    SHORTEN_DIR(2,"路径缩短"),
    NAME_CHANGE(3,"名称替换");



    private int type;
    private String name;

    DirTransType(int type,String name){
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


    @Override
    public String toString() {
        return this.name;
    }
}
