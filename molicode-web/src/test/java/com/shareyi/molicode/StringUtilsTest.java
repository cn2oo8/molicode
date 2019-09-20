package com.shareyi.molicode;

import com.shareyi.molicode.common.utils.MoliCodeStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * 字符串测试
 *
 * @author david
 * @date 2018/12/12
 */
public class StringUtilsTest {


    @Test
    public void test() {
        System.out.println(StringUtils.replace("\\test aa test bb", "\\test", "new_"));
    }


    @Test
    public void test2() {
        System.out.println(StringUtils.contains("hello....java", ".."));
        System.out.println(StringUtils.contains(".java.dsjdf", ".."));

        System.out.println(StringUtils.contains("../aa.java", ".."));

        System.out.println(StringUtils.contains(".java", ".."));


        System.out.println(MoliCodeStringUtils.replaceAllSimple("ksdjkewjkewiodmsdbnusejdsdhjshjdshjdshds", "kew", "pop"));
    }


    @Test
    public void testStringUtil() {
        String str = "ksdjkewjkewiodmsdbnusejdsdhjshjdshjdshds";

        int count = 10000;
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            String content = MoliCodeStringUtils.replaceAllSimple(str, "kew", "pop");
        }
        long end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            String content = str.replaceAll("kew", "pop");
        }
        end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start));

    }

}
