package com.shareyi.molicode.common.utils;

import com.google.common.collect.Lists;
import com.shareyi.molicode.common.constants.CommonConstant;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * String 工具类
 *
 * @author david
 * @date 2018/10/11
 */
public class MoliCodeStringUtils {

    /**
     * 将字符串split为List
     *
     * @param str       字符串
     * @param separator 裁剪码
     * @return 字符串list
     */
    public static List<String> splitToList(String str, String separator) {
        if (StringUtils.isEmpty(str)) {
            return Lists.newArrayListWithCapacity(0);
        }
        String[] strs = StringUtils.split(str, separator);
        return Lists.newArrayList(strs);
    }

    /**
     * 去掉空的字符串
     *
     * @param lineStrList
     * @param trim
     * @return
     */
    public static List<String> removeEmptyString(List<String> lineStrList, boolean trim) {
        if (CollectionUtils.isEmpty(lineStrList)) {
            return lineStrList;
        }
        List<String> trimedList = Lists.newArrayListWithCapacity(lineStrList.size());
        for (String line : lineStrList) {
            if (trim) {
                line = StringUtils.trim(line);
            }
            if (StringUtils.isEmpty(line)) {
                continue;
            }
            trimedList.add(line);
        }
        return trimedList;
    }


    public static int compareVersion(String version1, String version2) {
        if (version1 == null || version2 == null) {
            return 0;
        }
        if (Objects.equals(version1, version2)) {
            return 0;
        }

        List<String> versionInfoOne = splitToList(version1, "\\.");
        List<String> versionInfoTwo = splitToList(version2, "\\.");

        for (int index = 0; index < versionInfoOne.size(); index++) {
            String segmentOne = versionInfoOne.get(index);
            String segmentTwo = index < versionInfoTwo.size() ? versionInfoTwo.get(index) : null;
            if (StringUtils.isNumeric(segmentOne)) {
                if (StringUtils.isNumeric(segmentTwo)) {
                    int segResult = Integer.valueOf(segmentOne).compareTo(Integer.valueOf(segmentTwo));
                    if (segResult == 0) {
                        continue;
                    }
                    return segResult;
                }
                return 1;
            } else {
                if (StringUtils.isNumeric(segmentTwo)) {
                    return -1;
                }
                return 0;
            }
        }
        return 0;
    }


    /**
     * 获取基于时间的字符串
     *
     * @return
     */
    public static String getTimeBasedStr() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
        Date now = new Date();
        return dateFormat.format(now);
    }


    /**
     * 执行md5
     *
     * @param str
     * @return
     */
    public static String md5(String str) {
        return DigestUtils.md5Hex(str);
    }

    /**
     * 执行md5
     *
     * @param password
     * @return
     */
    public static String md5PasswordWithSalt(String password) {
        return DigestUtils.md5Hex(password + CommonConstant.PWD_SALT);
    }

    /**
     * 去除字符串的引号，单引号 & 双引号, 以及mysql转义引号
     *
     * @param str
     * @return
     */
    public static String removeQuot(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch == '\'' || ch == '"' || ch == '`') {
                continue;
            }
            sb.append(ch);
        }
        return sb.toString();
    }


    /**
     * 进行字符串简单替换
     *
     * @param str
     * @param searchChars
     * @param replaceChars
     * @return
     */
    public static String replaceAllSimple(final String str, final String searchChars, String replaceChars) {
        if (StringUtils.isEmpty(str) || StringUtils.isEmpty(searchChars)) {
            return str;
        }
        if (replaceChars == null) {
            replaceChars = StringUtils.EMPTY;
        }
        boolean modified = false;
        final int strLength = str.length();
        final int searchLength = searchChars.length();
        final StringBuilder buf = new StringBuilder(strLength);

        for (int i = 0; i < strLength; ) {
            if (i + searchLength > strLength) {
                buf.append(str.charAt(i));
                i++;
                continue;
            }
            boolean allMatch = true;
            for (int searchIndex = 0; searchIndex < searchLength; searchIndex++) {
                final char originChar = str.charAt(i + searchIndex);
                final char searchChar = searchChars.charAt(searchIndex);
                if (originChar != searchChar) {
                    allMatch = false;
                    break;
                }
            }

            if (allMatch) {
                modified = true;
                buf.append(replaceChars);
                i = i + searchLength;
            } else {
                buf.append(str.charAt(i));
                i++;
            }
        }
        if (modified) {
            return buf.toString();
        }
        return str;
    }


    public static void main(String[] args) {
        System.out.println(compareVersion("1.0.0", "1.0.1"));
        System.out.println(compareVersion("1.0.a", "1.0.1"));
        System.out.println(compareVersion("1.0.2", "1.0.1"));
        System.out.println(compareVersion("1.0.1", "1.0.1"));
        System.out.println(getTimeBasedStr());

        System.out.println(md5("hahaha"));


        System.out.println(md5("molicodepwd" + CommonConstant.PWD_SALT));

        System.out.println(removeQuot("\"dsdsds`"));
    }

}
