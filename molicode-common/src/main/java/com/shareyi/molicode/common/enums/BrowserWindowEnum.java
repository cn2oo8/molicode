package com.shareyi.molicode.common.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * window 类型
 *
 * @author david
 * @date 2018/9/23
 */
public enum BrowserWindowEnum implements EnumCode<String>{

    SWING("swing","swing窗口"),
    JX_BROWSER("jxBrowser","JXBrowser");

    String code,desc;

    BrowserWindowEnum(String code, String desc) {
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

    public static BrowserWindowEnum getByCode(String windowName) {
        if(StringUtils.isEmpty(windowName)){
            return SWING;
        }
        for(BrowserWindowEnum windowEnum : values()){
            if(Objects.equals(windowEnum.getCode(), windowName)){
                return windowEnum;
            }
        }
        return SWING;
    }
}
