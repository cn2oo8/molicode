package com.shareyi.molicode.common.utils;

import com.shareyi.molicode.common.vo.FileInfoVo;
import com.shareyi.fileutil.FileUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 系统文件工具类
 *
 * @author zhangshibin
 * @date 2018/12/12
 */
public class SystemFileUtils {

    /**
     * 当前路径的前缀
     */
    private static final String CUR_DIR = "curDir:";

    static Logger logger = Logger.getLogger(SystemFileUtils.class);

    /**
     * 获取文件相关信息
     *
     * @param files
     * @return 文件描述信息List
     */
    public static List<FileInfoVo> getFileInfo(File[] files) {
        if (files != null && files.length > 0) {
            List<FileInfoVo> fileInfoVos = new ArrayList<FileInfoVo>(files.length);
            for (File file : files) {
                fileInfoVos.add(getFileInfo(file));
            }
            return fileInfoVos;
        } else {
            return Collections.EMPTY_LIST;
        }

    }

    /**
     * 文件描述信息转换
     *
     * @param file 文件
     * @return 文件描述信息
     */
    private static FileInfoVo getFileInfo(File file) {
        FileInfoVo fileInfo = new FileInfoVo();
        fileInfo.setName(file.getName());
        fileInfo.setParentPath(file.getParent());
        fileInfo.setLastModified(file.lastModified());
        fileInfo.setHidden(file.isHidden());
        fileInfo.setCanWrite(file.canWrite());
        int fileType = 1;
        if (file.isDirectory()) {
            fileType = 2;
        }
        fileInfo.setFileType(fileType);
        return fileInfo;

    }


    /**
     * 获取文件路径
     *
     * @param filePath 路径
     * @return 转换后的文件路径
     */
    public static String parseFilePath(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            return filePath;
        }
        String parsePath = filePath.trim();
        if (filePath.startsWith(CUR_DIR)) {
            parsePath = filePath.substring(filePath.indexOf(":") + 1).trim();
            parsePath = FileUtil.getRuntimeFilePath(parsePath);
        } else if (filePath.startsWith("projectOutputDir:")) {
            parsePath = filePath.substring(filePath.indexOf(":") + 1).trim();
            parsePath = FileUtil.contactPath(FileUtil.getRuntimeFilePath("bind"), parsePath);
        } else if (filePath.indexOf(":") == -1 && !filePath.startsWith("/")) { //既非linux绝对路径 也非Windows绝对路径 添加浅醉
            parsePath = FileUtil.getRuntimeFilePath(parsePath);
        }
        return parsePath;
    }


    /**
     * 更改当前路径
     *
     * @param filePath 文件路径
     * @return 改成当前运行环境的相对路径
     */
    public static String changeCurFilePath(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            return filePath;
        }
        String runPath = FileUtil.getRunPath() + File.separator;
        logger.error("runPath=" + runPath + ", filePath=" + filePath);
        if (filePath.startsWith(runPath)) {
            if (runPath.length() == filePath.length()) {
                return "";
            }
            return filePath.substring(runPath.length());
        }
        return filePath;
    }

}
