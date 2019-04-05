package com.shareyi.molicode.common.utils;

import com.shareyi.molicode.common.vo.code.ColumnVo;
import com.shareyi.molicode.common.vo.code.DictVo;
import com.shareyi.molicode.common.vo.code.OrderColumnVo;
import com.shareyi.molicode.common.vo.code.TableModelVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class TableNameUtil {


    public HashMap<String, String> dataTypeMap;
    public HashMap<String, String> jspTagMap;
    /**
     * 缓存map
     */
    private Map<String, Object> cacheMap = new HashMap<>();

    public TableNameUtil() {
        dataTypeMap = new HashMap();
        dataTypeMap.put("VARCHAR", "String");
        dataTypeMap.put("VARCHAR2", "String");
        dataTypeMap.put("CHAR", "String");
        dataTypeMap.put("TEXT", "String");
        dataTypeMap.put("BOOLEAN", "Boolean");

        dataTypeMap.put("FLOAT", "Float");
        dataTypeMap.put("DOUBLE", "Double");
        dataTypeMap.put("DECIMAL", "java.math.BigDecimal");

        dataTypeMap.put("TINYINT", "Integer");
        dataTypeMap.put("SMALLINT", "Integer");
        dataTypeMap.put("MEDIUMINT", "Integer");
        dataTypeMap.put("INT", "Integer");
        dataTypeMap.put("INTEGER", "Integer");
        dataTypeMap.put("BIGINT", "Long");


        dataTypeMap.put("DATE", "java.util.Date");
        dataTypeMap.put("DATETIME", "java.util.Date");
        dataTypeMap.put("TIMESTAMP", "java.sql.Timestamp");

        jspTagMap = new HashMap<String, String>();
        jspTagMap.put("DATE", "DATE");
        jspTagMap.put("DATETIME", "DATETIME");
        jspTagMap.put("TIMESTAMP", "DATETIME");
    }


    /**
     * 首字符大写，为空返回空
     * 示例： userName-&gt;UserName  常用于获取类名
     *
     * @param str 要转换的字符串
     * @return 首字符大写
     */
    public String upperFirst(String str) {
        if (str == null) {
            return "";
        }
        str = str.trim();
        if (str.length() == 0) {
            return "";
        }

        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }


    /**
     * 首字符小写，为空返回空
     * 示例： UserName  -&gt; userName 常用于类名转实例名
     *
     * @param str 要转换的字符串
     * @return 首字符小写
     */
    public static String lowerCaseFirst(String str) {
        if (str == null) {
            return "";
        }
        str = str.trim();
        if (str.length() == 0) {
            return "";
        }

        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }


    /**
     * 数据库字段名称转换为属性名称 MY_OLD-&gt; myOld
     *
     * @param dbNames 数据库字段名称
     * @return 属性名称
     */
    public String convertToBeanNames(String dbNames) {
        String[] sr = dbNames.split("[_]");
        StringBuilder builder = new StringBuilder();
        System.out.println(dbNames);
        for (int i = 0; i < sr.length; i++) {
            String str2 = sr[i];
            if (i != 0) {
                String c = str2.charAt(0) + "";
                builder.append(c.toUpperCase() + str2.substring(str2.length() > 1 ? 1 : 0).toLowerCase());
            } else
                builder.append(str2.toLowerCase());
        }
        return builder.toString();
    }


    /**
     * 转换为数据库字段名称 myOld --&gt; MY_OLD
     *
     * @param dbNames 属性名称
     * @return 数据库字段名称
     */
    public String convertToDbNames(String dbNames) {

        StringBuilder builder = new StringBuilder();
        List<String> list = splitByUppercase(dbNames);

        for (String string : list) {
            builder.append(string.toUpperCase()).append("_");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }


    /**
     * 将字符串按大写字母拆分为List元素
     * 如： userName -&gt; [user, name]
     *
     * @param src
     * @return
     */
    public List<String> splitByUppercase(String src) {
        LinkedList<String> list = new LinkedList<String>();
        if (src == null || src.length() == 0) {
            return list;
        }

        int lastInd = 0;
        for (int i = 1; i < src.length(); i++) {
            char c = src.charAt(i);
            if (Character.isUpperCase(c)) {
                list.add(src.substring(lastInd, i));
                lastInd = i;
            }
        }
        if (lastInd != src.length()) {
            list.add(src.substring(lastInd, src.length()));
        }
        return list;
    }


    /**
     * 根据数据库类型获取java数据类型
     * <p>
     * 如 VARCHAR -&gt; String
     *
     * @param dbType 数据库数据类型
     * @return 数据类型
     */
    public String getDataType(String dbType) {
        if (dbType == null) {
            dbType = "";
        }

        String dataType = dataTypeMap.get(dbType.toUpperCase());
        if (dataType == null) {
            dataType = "String";
        }
        return dataType;
    }

    /**
     * 根java数据类型获取数据库类型
     * <p>
     * 如 String -&gt; VARCHAR
     *
     * @param dataType 数据类型
     * @return 数据库类型
     */
    public String transToColumnType(String dataType) {
        if (dataType == null) {
            dataType = "String";
        }

        String cacheKey = "dataType_to_column_" + dataType;
        String dataType2ColumnType = (String) cacheMap.get(cacheKey);
        if (dataType2ColumnType != null) {
            return dataType2ColumnType;
        }

        if (dataTypeMap == null || dataTypeMap.isEmpty()) {
            return "VARCHAR";
        }

        String columnType = "VARCHAR";
        for (Map.Entry<String, String> entry : dataTypeMap.entrySet()) {
            String dataTypeName = entry.getValue();
            if (dataTypeName.contains(".") && !dataType.contains(".")) {
                dataTypeName = StringUtils.substring(dataTypeName, dataTypeName.lastIndexOf(".") + 1);
            }
            if (Objects.equals(dataTypeName, dataType)) {
                columnType = entry.getKey();
                break;
            }
        }
        cacheMap.put(cacheKey, columnType);
        return columnType;
    }


    /**
     * 根据数据库类型获取java数据类型 z全包名，  如java.lang.Integer
     *
     * @param dbType 数据库数据类型
     * @return 数据类型
     */
    public String getFullDataType(String dbType) {
        String dataType = this.getDataType(dbType);
        if (dataType.length() > 0 && dataType.indexOf(".") < 0) {
            dataType = "java.lang." + dataType;
        }
        return dataType;
    }


    /**
     * 根据数据库类型获取Tag
     *
     * @param dbType 数据库数据类型
     * @return 数据类型
     */
    public String getJspTag(String dbType) {
        if (dbType == null) {
            dbType = "";
        }

        String jspTag = jspTagMap.get(dbType.toUpperCase());
        if (jspTag == null)
            jspTag = "TEXT";
        return jspTag;
    }


    /**
     * 获取排序字段
     *
     * @param tableModel
     * @return
     */
    public String getOrderString(TableModelVo tableModel) {
        StringBuilder orderStr = new StringBuilder();
        if (tableModel == null)
            return "";

        List<OrderColumnVo> orderColumns = tableModel.getOrderColumns();
        if (orderColumns != null && !orderColumns.isEmpty()) {
            orderStr.append(" order by ");
            for (OrderColumnVo orderColumn : orderColumns) {
                orderStr.append(orderColumn.getColumnName());
                if ("asc".equalsIgnoreCase(orderColumn.getOrderType())) {
                    orderStr.append(" asc,");
                } else {
                    orderStr.append(" desc,");
                }

            }
            orderStr.deleteCharAt(orderStr.length() - 1);
        }

        return orderStr.toString();
    }


    /**
     * 获取列的测试数据
     *
     * @param column
     * @return
     */
    public String genTestDataQuote(ColumnVo column, Map<String, DictVo> dictMap) {
        if (column == null) {
            return "null";
        }

        String dataType = this.getDataType(column.getColumnType());
        if ("String".equals(dataType) || "java.lang.String".equals(dataType)) {
            return wrapWithQuote(genTestDataWithDict(column.getColumnName(), column, dictMap));
        }
        if ("java.util.Date".equals(dataType) || "java.sql.Timestamp".equals(dataType)) {
            return String.valueOf(System.currentTimeMillis());
        }
        if ("Float".equals(dataType) || "Double".equals(dataType) || "Integer".equals(dataType) || "Long".equals(dataType) ||
                "java.math.BigDecimal".equals(dataType)) {
            String value = genTestDataWithDict("1", column, dictMap);
            return value;
        }
        return "null";
    }


    /**
     * 通过字典项生成数据
     *
     * @param data
     * @param column
     * @param dictMap
     * @return
     */
    public static String genTestDataWithDict(String data, ColumnVo column, Map<String, DictVo> dictMap) {
        if (dictMap == null || StringUtils.isEmpty(column.getDictName())) {
            return data;
        }
        DictVo dict = dictMap.get(column.getDictName());
        if (dict != null && CollectionUtils.isNotEmpty(dict.getOptionList())) {
            data = dict.getOptionList().get(0).getValue();
        }
        return data;
    }


    public static String wrapWithQuote(String value) {
        if (value == null) {
            return "null";
        }
        return "\"" + value + "\"";
    }
}
