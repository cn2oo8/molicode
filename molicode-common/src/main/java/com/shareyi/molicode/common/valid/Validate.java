/**
 * Copyright(C) 2004-2017 shareyi.com All Right Reserved
 */
package com.shareyi.molicode.common.valid;

import com.shareyi.molicode.common.enums.ResultCodeEnum;
import com.shareyi.molicode.common.exception.ExceptionMaker;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * <p>
 * 验证工具
 * <p>
 * </p>
 *
 * @author david
 * @date 2017-09-26 12:04
 */
public class Validate {

    /**
     * 中文 & 英文 & 上下划线
     */
    public static final String CN_AND_LETTER_PTN = "[A-Za-z0-9\\-_\\u4e00-\\u9fa5]+";


    public static void range(int val, Integer min, Integer max, String message) {
        if (min == null && max == null) {
            return;
        }
        if (min != null && val < min) {
            throw ExceptionMaker.buildException(message, ResultCodeEnum.PARAM_ERROR);
        }
        if (max != null && val > max) {
            throw ExceptionMaker.buildException(message, ResultCodeEnum.PARAM_ERROR);
        }
    }

    public static boolean isNull(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof Collection) {
            if (((Collection) obj).size() == 0) {
                return true;
            }
        } else if (obj instanceof Map) {
            if (((Map) obj).size() == 0) {
                return true;
            }
        } else if (obj.getClass().isArray()) {
            if (((Object[]) obj).length == 0) {
                return true;
            }
        }
        return false;
    }

    public static void notNull(Object obj, String message) {
        if (isNull(obj)) {
            throw ExceptionMaker.buildException(message, ResultCodeEnum.PARAM_ERROR);
        }
    }

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String && StringUtils.isBlank((String) obj)) {
            return true;
        }
        return false;
    }

    public static void notEmpty(Object obj, String message) {
        if (isEmpty(obj)) {
            throw ExceptionMaker.buildException(message, ResultCodeEnum.PARAM_ERROR);
        }
    }


    public static void assertTrue(boolean check, String message) {
        if (!check) {
            throw ExceptionMaker.buildException(message, ResultCodeEnum.PARAM_ERROR);
        }
    }

    /**
     * 是否中文，字母，数字，和 -_
     *
     * @param str
     * @return
     */
    public static void checkChineseAndLetterNum(String str, String message) {
        if (StringUtils.isEmpty(str)) {
            throw ExceptionMaker.buildException(message, ResultCodeEnum.PARAM_ERROR);
        }
        assertTrue(patternCheck(str, CN_AND_LETTER_PTN), message);
    }


    /**
     * 验证字符串是否满足正则表达式
     *
     * @param val
     * @param ptn
     * @return
     */
    public static boolean patternCheck(String val, String ptn) {
        boolean isMatch = Pattern.matches(ptn, val);
        return isMatch;
    }
}
