package com.shareyi.molicode.common.utils;

import com.shareyi.molicode.common.enums.ResultCodeEnum;
import com.shareyi.molicode.common.exception.DefaultExceptionMaker;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 压缩解压缩辅助工具类
 *
 * @author david
 * @date 2019/6/10
 */
public class ZipHelper {

    /**
     * 执行文件压缩
     */
    public static void zipFile(File sourceFile, File destZipFile) {
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(new FileOutputStream(destZipFile));
            long start = System.currentTimeMillis();
            compress(zos, sourceFile, sourceFile.getName());
            long end = System.currentTimeMillis();
            long costTIme = end - start;
            LogHelper.DEFAULT.info("执行文件压缩完成，cost={}, srcPath={}, zipFile={}", costTIme, sourceFile.getAbsolutePath(), destZipFile.getAbsolutePath());
            zos.flush();
        } catch (Exception e) {
            LogHelper.DEFAULT.error("执行文件压缩失败，srcPath={}, zipFile={}", "执行文件压缩完成，srcPath={}, zipFile={}", sourceFile.getAbsolutePath(), destZipFile.getAbsolutePath(), e);
            throw DefaultExceptionMaker.buildException("压缩文件失败，原因是：" + e.getMessage(), ResultCodeEnum.EXCEPTION);
        } finally {
            IOUtils.closeQuietly(zos);
        }
    }

    /**
     * 压缩文件
     *
     * @param zos        zip文件输出流
     * @param sourceFile 源文件
     * @param base       文件名称
     * @throws Exception
     */
    public static void compress(ZipOutputStream zos, File sourceFile, String base) throws Exception {
        //如果路径为目录（文件夹）
        if (sourceFile.isDirectory()) {
            //取出文件夹中的文件（或子文件夹）
            File[] subFiles = sourceFile.listFiles();
//如果文件夹为空，则只需在目的地zip文件中写入一个目录进入点
            if (subFiles.length == 0) {
                zos.putNextEntry(new ZipEntry(base + "/"));
            } else { //如果文件夹不为空，则递归调用compress，文件夹中的每一个文件（或文件夹）进行压缩
                for (int i = 0; i < subFiles.length; i++) {
                    compress(zos, subFiles[i], base + "/" + subFiles[i].getName());
                }
            }
        } else { //如果不是目录（文件夹），即为文件，则先写入目录进入点，之后将文件写入zip文件中
            zos.putNextEntry(new ZipEntry(base));
            FileInputStream fos = new FileInputStream(sourceFile);
            BufferedInputStream bis = new BufferedInputStream(fos);

            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = bis.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }
            IOUtils.closeQuietly(bis);
            IOUtils.closeQuietly(fos);
        }
    }
}
