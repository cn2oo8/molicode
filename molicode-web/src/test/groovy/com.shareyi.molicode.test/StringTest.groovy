package com.shareyi.molicode.test

import org.apache.commons.io.IOUtils
import org.assertj.core.util.Lists
import org.junit.Test

/**
 * 文件读取测试
 * @author zhangshibin
 * @date 2019/5/20
 */
class StringTest {


    @Test
    void testStrLine() throws Exception {
        File file = new File("/Users/zhangshibin/github/molicode/molicode-web/src/test/java/com/shareyi/molicode/MyTest.java");
        FileInputStream inputStream = new FileInputStream(file);
        String info = IOUtils.toString(inputStream, "UTF-8");
        List<String> lineList = Lists.newArrayList();
        info.eachLine { it ->
            lineList.add(it);
        }
        IOUtils.closeQuietly(inputStream)
        println lineList;
    }

    @Test
    void testStrLine2() throws Exception {
        File file = new File("/Users/zhangshibin/github/molicode/molicode-web/src/test/java/com/shareyi/molicode/MyTest.java");
        FileInputStream inputStream = new FileInputStream(file);
        String info = IOUtils.toString(inputStream, "UTF-8");
        List<String> lineList = Lists.newArrayList();
        info.eachLine { it ->
            lineList.add(it);
        }
        IOUtils.closeQuietly(inputStream)

        println lineList.get(lineList.size()-1).charAt(0);
        char ch=lineList.get(lineList.size()-1).charAt(0);
        println ch;
    }
}
