package com.shareyi.molicode;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * 日志测试
 *
 * @author david
 * @date 2019/5/20
 */
public class LogEnableTest {

    private static Logger LOGGER = LoggerFactory.getLogger(LogEnableTest.class);

    public void test01() {
        LOGGER.info("保持原样");
    }

    public void test02() {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("保持原样,data={}", JSON.toJSONString(new HashMap<>()));
        }
    }

    public void test03() {
        if(LOGGER.isInfoEnabled()) {
	        LOGGER.info("进行替换 ,data={}", JSON.toJSONString(new HashMap<>()));
        }
        
    }
}