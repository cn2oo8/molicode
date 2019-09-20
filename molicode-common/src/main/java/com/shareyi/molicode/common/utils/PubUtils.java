package com.shareyi.molicode.common.utils;

import com.shareyi.molicode.common.vo.code.ColumnVo;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 通用工具类，主要是一些字符串，UUID，时间等功能生成
 *
 * @author david
 * @date 2018/12/12
 */
public class PubUtils {

    /**
     * 获取随机的Long
     *
     * @return 随机Long
     */
    public static Long getRandomLong() {
        Random random = new Random();
        return random.nextLong();
    }

    /**
     * 生成UUID
     *
     * @return 无-的，且大写32位的UUID
     */
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "").toUpperCase();
    }

    /**
     * 获取当前年月日
     *
     * @return yyyy-MM-dd
     */
    public static String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(new Date());
    }

    /**
     * 获取年月日和时间
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date());
    }


    /**
     * 获取当前的时间戳
     *
     * @return 时间戳
     */
    public static long getTime() {
        return System.currentTimeMillis();
    }

    /**
     * List转换为String,用逗号间隔
     * 如： col1,col2,col3
     *
     * @param list 字符串list
     * @return 逗号分隔的字符串
     */
    public static String listToStr(List<String> list) {
        if (list == null || list.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String str : list) {
            sb.append(str).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * String转换为List,用逗号间隔
     * 将 col1,col2,col3 以逗号分隔为list的元素
     *
     * @param str 原始字符串
     * @return 拆分后的List
     */
    public static List<String> stringToList(String str) {
        List<String> list = new ArrayList<String>();
        if (str != null && str.trim().length() != 0) {
            String[] strs = str.split(",");
            for (String string : strs) {
                list.add(string);
            }
        }
        return list;
    }

    /**
     * 包名转路径
     * 如：com.jd.xxx 转换为： com/jd/xxx
     *
     * @param str 包名
     * @return 路径地址
     */
    public static String packageToPath(String str) {
        return str.replace('.', '/');
    }


    /**
     * 路径转换为包名
     * 如： com/jd/xxx 转换为：com.jd.xxx
     *
     * @param str 路径地址
     * @return 包名路径
     */
    public static String pathToPackage(String str) {
        str = str.replace('/', '.');
        return str.replace('\\', '.');
    }


    /**
     * 如果字符串不为空，将字符串拼接到分隔符后面
     * 否则返回空串
     * 如：("path","/") --&gt;  /path
     *
     * @param str 原始串
     * @param sep 分隔符
     * @return 转换后的字符串
     */
    public static String addStrBeforeSeparator(String str, String sep) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }

        return str + sep;
    }


    /**
     * 如果字符串不为空，将字符串拼接到分隔符后面
     * 否则返回空串
     * 如：("path","/") --&gt;  /path
     *
     * @param str 原始串
     * @param sep 分隔符
     * @return 转换后的字符串
     */
    public static String addStrAfterSeparator(String str, String sep) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }

        return sep + str;
    }


    /**
     * 将列名，按id,name,total_money格式拼接在一起，主要用户SQL拼接
     *
     * @param columns 列List
     * @return 类似 id,name,total_money
     */
    public static String joinColumnNames(List<ColumnVo> columns) {
        StringBuilder sb = new StringBuilder();
        for (ColumnVo column : columns) {
            sb.append(column.getColumnName()).append(",");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

}
