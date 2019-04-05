package com.shareyi.molicode.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志辅助工具类
 *
 * @author david
 * @date 2017/12/12
 */
public class LogHelper {

    public static final Logger DEFAULT = LoggerFactory.getLogger("defaultLog");
    public static final Logger EXCEPTION = LoggerFactory.getLogger("exceptionLog");
    public static final Logger BROWSER_CONSOLE = LoggerFactory.getLogger("browserConsole");
    public static final Logger FRONT_CONSOLE = LoggerFactory.getLogger("frontConsole");

}
