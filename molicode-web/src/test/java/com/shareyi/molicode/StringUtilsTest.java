package com.shareyi.molicode;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * 字符串测试
 *
 * @author zhangshibin
 * @date 2018/12/12
 */
public class StringUtilsTest {


    @Test
    public void test() {
        System.out.println(StringUtils.replace("\\test aa test bb","\\test","new_"));
    }

}
