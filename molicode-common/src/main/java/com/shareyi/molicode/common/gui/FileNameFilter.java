package com.shareyi.molicode.common.gui;

import org.apache.commons.lang.StringUtils;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件名称过滤器
 *
 * @author david
 * @date 2016/6/10
 */
public class FileNameFilter extends FileFilter {

    /**
     * 文件后缀
     */
    String ends;
    /**
     * 文件描述文字
     */
    String description;
    /**
     * 文件后缀转换的list
     */
    private List<String> fileNameEnds;

    /**
     * 构造函数
     *
     * @param ends
     */
    public FileNameFilter(String ends) {
        this.ends = ends; // 设置文件后缀
        this.description = ends; // 设置文件描述文字
        this.fileNameEnds = new ArrayList<String>();

        if (StringUtils.isNotEmpty(ends) && !"*.*".equals(ends)) {
            String[] endArray = ends.split(";");
            for (String end : endArray) {
                String endName = end.startsWith("*.") ? end.substring(2) : end;
                fileNameEnds.add(endName.toUpperCase());
            }

        }
    }

    /**
     * 只显示符合扩展名的文件，目录全部显示
     *
     * @param file
     * @return
     */
    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return true;
        }
        if (fileNameEnds == null || fileNameEnds.isEmpty()) {
            return true;
        }
        String fileName = file.getName();
        String fileEnd = fileName;
        int idx = fileName.lastIndexOf(".");
        if (idx > -1) {
            fileEnd = fileName.substring(idx + 1);
        }
        return fileNameEnds.contains(fileEnd.toUpperCase());
    }

    /**
     * 返回这个扩展名过滤器的描述
     *
     * @return
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * 返回这个扩展名过滤器的扩展名
     *
     * @return
     */
    public String getEnds() {
        return this.ends;
    }

}
