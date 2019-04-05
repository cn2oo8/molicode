package com.shareyi.molicode.common.vo

/**
 * <p>文件相关参数页面传递</p>
 * @author david
 * @since 2018-08-05
 */
class FileVo {

    /**
     * 文件窗口打开模式： save,open,directory
     */
    String dialogType;
    /**
     * 文件格式
     * img, audio, vedio, doc, all, other
     */
    String fileType;
    /**
     * 文件扩展类型
     */
    String fileExt;
    /**
     * 修改文件的全路径
     */
    String editFilePath;
    /**
     * 文件内容
     */
    String fileContent;
    /**
     * 父路径地址
     */
    String parentPath;
    /**
     * 是否改成相对路径，去掉当前路径前置
     */
    String changeCurPath;


    @Override
    public String toString() {
        return "FileVo{" +
                "dialogType='" + dialogType + '\'' +
                ", fileType='" + fileType + '\'' +
                ", fileExt='" + fileExt + '\'' +
                ", editFilePath='" + editFilePath + '\'' +
                ", fileContent='" + fileContent + '\'' +
                ", parentPath='" + parentPath + '\'' +
                ", changeCurPath='" + changeCurPath + '\'' +
                '}';
    }
}
