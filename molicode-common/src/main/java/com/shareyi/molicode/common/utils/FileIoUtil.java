package com.shareyi.molicode.common.utils;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * <p>文件读写工具类</p>
 *
 * @author david
 * @date 2018/8/5
 */
public class FileIoUtil {

    private static String runPath;


    /**
     * create directory dirPath,if it is exist,return false;
     *
     * @param dirPath
     * @return
     */
    public static boolean makeDir(String dirPath) {
        return makeDir(new File(dirPath));
    }

    /**
     * create directory dirPath,if it is exist,return false
     *
     * @param file
     * @return
     */
    public static boolean makeDir(File file) {
        if (!file.exists()) {
            return file.mkdirs();
        }
        return false;
    }


    /**
     * copy directory and sub directories and files in it to the Destination file path
     *
     * @param src
     * @param dest
     * @return
     */
    public static boolean copyDirFiles(File src, File dest) {
        if (src.exists() && src.isDirectory()) {
            makeDir(dest);
            File[] files = src.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    if (file.getName().equals(".svn")) {
                        continue;
                    }
                    copyDirFiles(file, new File(dest, file.getName()));
                } else {
                    File toFile = new File(dest, file.getName());
                    try {
                        IOUtils.copy(new FileInputStream(file), new FileOutputStream(toFile));
                    } catch (Exception e) {
                        LogHelper.EXCEPTION.error("复制文件出现异常, toFile=" + toFile.getAbsolutePath(), e);
                        return false;
                    }
                }
            }
            return true;
        }

        return false;
    }


    /**
     * copy file to the destination path
     *
     * @param src
     * @param dest
     * @return
     */
    public static boolean copyFile(String src, String dest) {
        return copyFile(new File(src), new File(dest));
    }


    /**
     * copy file
     *
     * @param src
     * @param dest
     * @return
     */
    public static boolean copyFile(File src, File dest) {

        if (src.exists()) {
            if (!src.isDirectory()) {
                File pPath = new File(dest.getParent());
                if (!pPath.exists())
                    makeDir(pPath);
                try {
                    IOUtils.copy(new FileInputStream(src), new FileOutputStream(dest));
                } catch (Exception e) {
                    LogHelper.EXCEPTION.error("复制文件出现异常, toFile=" + dest.getAbsolutePath(), e);
                    return false;
                }
                return true;
            } else {
                copyDirFiles(src, dest);
                return true;
            }
        }
        //非公有

        return false;
    }


    /**
     * delete file, it will delete the sub directory and files in it when the file is a directory
     *
     * @param packDir
     * @return
     */
    public static boolean deleteDirFiles(String packDir) {
        return deleteDirFiles(new File(packDir));
    }

    public static boolean deleteDirFiles(File src) {
        if (src.exists() && src.isDirectory()) {
            File[] files = src.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirFiles(file);
                } else {
                    file.delete();
                }
            }
            src.delete();
            return true;
        }
        return false;
    }


    /**
     * get the java application running path
     *
     * @return
     */
    public static String getRunPath() {
        if (runPath == null) {
            runPath = new File("").getAbsolutePath();
        }
        return runPath;
    }


    /**
     * contact the parent path and sub path to be an entire path
     *
     * @param parent
     * @param filePath
     * @return
     */
    public static String contactPath(String parent, String filePath) {
        if (!parent.endsWith("/") && !parent.endsWith("\\")) {
            parent = parent + "/";
        }

        if (filePath.startsWith("/") || filePath.startsWith("\\"))
            filePath = filePath.substring(1);
        return parent + filePath;
    }


    /**
     * contact the runPaht and sub file path
     *
     * @param filePath
     * @return
     */
    public static String getRuntimeFilePath(String filePath) {
        return contactPath(getRunPath(), filePath);
    }


    /**
     * 如果文件不存在，创建文件包含路径
     *
     * @param file 文件
     * @return
     */
    public static File makeSureFileExist(File file) {
        if (!file.exists()) {
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                return null;
            }
        }
        return file;
    }

}
