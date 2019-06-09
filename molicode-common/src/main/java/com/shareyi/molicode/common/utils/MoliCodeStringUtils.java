package com.shareyi.molicode.common.utils;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * String 工具类
 *
 * @author zhangshibin
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


    public static void main(String[] args) {
        System.out.println(compareVersion("1.0.0", "1.0.1"));
        System.out.println(compareVersion("1.0.a", "1.0.1"));
        System.out.println(compareVersion("1.0.2", "1.0.1"));
        System.out.println(compareVersion("1.0.1", "1.0.1"));
        System.out.println(getTimeBasedStr());
    }
}
