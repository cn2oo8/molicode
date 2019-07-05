package com.shareyi.molicode.service.gencode.impl;


import com.shareyi.molicode.common.utils.LogHelper;
import com.shareyi.molicode.common.vo.code.ColumnVo;
import com.shareyi.molicode.common.vo.code.DictVo;
import com.shareyi.molicode.common.vo.code.OptionVo;
import com.shareyi.molicode.common.vo.code.TableModelVo;
import com.shareyi.molicode.service.gencode.ColumnProcessor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 列处理工具类
 */
@Service
public class DictColumnProcessor implements ColumnProcessor {

    public void process(TableModelVo tableModel, List<ColumnVo> columns) {
        if (tableModel.getDictMap() == null) {
            Map<String, DictVo> dictMap = new HashMap<String, DictVo>();
            tableModel.setDictMap(dictMap);
        }
        for (ColumnVo column : columns) {
            doProcessColumn(tableModel, column);

        }
    }


    /**
     * 1. 以  中文名@PUBLIC_DICT_NAME 类型的，解析出公共的dictName
     * 2. 中文名 :1.字典项1 2.字典项2 3.字典项3
     *
     * @param tableModel
     * @param column
     */
    private void doProcessColumn(TableModelVo tableModel, ColumnVo column) {
        String comment = column.getComment();
        if (StringUtils.isEmpty(comment)) {
            LogHelper.DEFAULT.warn("列的备注为空,columnInfo={}", column);
            return;
        }
        int atIndex = comment.indexOf("@");

        //以@指定公共的字典项名称
        if (atIndex != -1) {
            //中文名称去掉字典项属性
            String dictName = (atIndex == comment.length()) ? "" : comment.substring(atIndex + 1);
            if (StringUtils.isEmpty(dictName)) {
                column.setCnname(comment.substring(0, atIndex));
                column.setDictName(comment.substring(atIndex + 1).trim());
            }
            return;
        }


        //后期改为正则表达式来查找分割符

        Pattern pattern = Pattern.compile("[:(：（\\s]");
        Matcher matcher = pattern.matcher(comment);
        if (matcher.find()) {
            int startIdx = matcher.start();
            //干掉末尾括弧
            if (comment.endsWith(")") || comment.endsWith("）")) {
                comment = comment.substring(0, comment.length());
            }

            //如果字典项内容数据为空，直接返回
            if (startIdx == comment.length() - 1) {
                return;
            }
            //剪切出飞字典项注释数据作为字段中文名
            column.setCnname(comment.substring(0, startIdx));
            //截取出字典内容数据
            String dictContent = comment.substring(startIdx + 1).trim();
            processDictContent(tableModel, column, dictContent);
        }

    }


    /**
     * 解析字典项内容数据
     *
     * @param column
     * @param dictContent
     */
    private void processDictContent(TableModelVo tableModel, ColumnVo column, String dictContent) {
        Map<String, DictVo> dictMap = tableModel.getDictMap();
        String[] optionContents = dictContent.split("[,，\\s]+");

        String columnDictName = tableModel.getTableDefine().getVarDomainName() + "_" + column.getDataName() + "_DICT";
        DictVo dict = new DictVo();
        dict.setName(column.getColumnName() + "字段的字典项");
        dict.setId(columnDictName);
        for (String optionContent : optionContents) {
            Pattern pattern = Pattern.compile("[.:：。]");
            Matcher matcher = pattern.matcher(optionContent);
            if (matcher.find()) {
                OptionVo option = new OptionVo();
                int spiltIdx = matcher.start();


                String value = optionContent.substring(0, spiltIdx).trim();
                //如果没有内容
                if (spiltIdx == optionContent.length() - 1) {
                    option.setName("");
                } else {
                    String name = optionContent.substring(spiltIdx + 1);
                    option.setName(name);
                }
                option.setValue(value);
                dict.getOptionList().add(option);
            }
        }
        column.setJspTag("SELECT");
        column.setDictName(columnDictName);
        dictMap.put(columnDictName, dict);
    }


    public static void main(String[] args) {
        //后期改为正则表达式来查找分割符

        Pattern pattern = Pattern.compile("[:(：（\\s]+");
        Matcher matcher = pattern.matcher("这是中文  ");
        if (matcher.find()) {
            System.out.println(matcher.group(0) + matcher.start());
        }

        String[] str = "dsdsds,sdddddd，sddsds   dfsdse  ".split("[,，\\s]+");
        for (String string : str) {
            System.out.println(string);
        }


        Pattern digtalPattern = Pattern.compile("[\\d]");
        Matcher digtalMatcher = pattern.matcher("83443好的");
    }


}
