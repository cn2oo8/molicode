package com.shareyi.molicode.service.replace.impl

import com.shareyi.molicode.common.enums.TouchFileType
import com.shareyi.molicode.common.filter.FileNameExpressionFilter
import com.shareyi.molicode.common.filter.FileNameFilter
import com.shareyi.molicode.common.utils.LogHelper
import com.shareyi.molicode.common.vo.page.ReplaceParams
import com.shareyi.molicode.common.vo.replace.TouchFileDir
import com.shareyi.molicode.common.web.CommonResult
import com.shareyi.molicode.service.replace.CopyAndReplaceService
import com.shareyi.molicode.service.replace.FileNameReplaceUtil
import com.shareyi.molicode.service.replace.FileReplace
import com.shareyi.fileutil.FileIo
import com.shareyi.fileutil.FileUtil
import org.apache.commons.io.IOUtils
import org.apache.commons.lang.StringUtils
import org.springframework.stereotype.Service

/**
 * 文件复制到新路径，及文件内容和目录名称替换
 */
@Service
class CopyAndReplaceServiceImpl implements CopyAndReplaceService {

    /**
     * 执行文件复制和内容相关替换
     * @param srcPath 源路径
     * @param destPath 输出路径
     * @param ignoreExp 被忽略进行替换的表达式
     * @param throwExp 被丢弃的文件
     * @param replaceType 替换类型
     * @param replaceExp 替换表达式
     * @param dirReplaceExp 路径替换表达式
     * @return
     */
    CommonResult<String> doCopyAndReplace(ReplaceParams replaceParams) {

        CommonResult<String> result = new CommonResult<String>();
        try {

            if (StringUtils.isEmpty(replaceParams.srcPath) || StringUtils.isEmpty(replaceParams.destPath)) {
                return result.failed("参数有误！");
            }

            if (StringUtils.isEmpty(replaceParams.replaceType)) {
                replaceParams.replaceType = 1;
            }

            FileNameReplaceUtil fileNameReplaceUtil = new FileNameReplaceUtil(replaceParams.replaceExp);
            FileNameExpressionFilter ignoreFilter = new FileNameExpressionFilter(replaceParams.ignoreExp);
            FileNameExpressionFilter throwFilter = new FileNameExpressionFilter(replaceParams.throwExp);

            ignoreFilter.setEmptyMatch(false);
            throwFilter.setEmptyMatch(false);


            File srcFile = new File(replaceParams.srcPath);
            File destFile = new File(FileUtil.contactPath(replaceParams.destPath, srcFile.getName()));
            FileReplace fileReplace = new FileReplace();
            fileReplace.srcFile = srcFile;
            fileReplace.destFile = destFile;

            if (!fileReplace.srcFile.exists()) {
                result.setSuccess(false);
                result.setMessage("源文件不存在，请检查！");
                return result;
            }
            File destParentPath = new File(replaceParams.destPath);
            if (!destParentPath.exists()) {
                result.setSuccess(false);
                result.setMessage("输出目录不存在，请检查！");
                return result;
            }
            if (!destParentPath.isDirectory()) {
                result.setSuccess(false);
                result.setMessage("输出目录不为文件夹，请检查！");
                return result;
            }

            long start = System.currentTimeMillis();
            TouchFileHelper touchFileHelper = TouchFileHelper.getTouchFileHelper(replaceParams.dirReplaceExp);
            TouchFileDir rootFileDir = null;
            //对替换的文件目录做路径转换解析及处理，需要进行全路径匹配扫描，并解析为数据进行相关存储
            if (touchFileHelper != null) {
                rootFileDir = parseTouchFileResult(srcFile, touchFileHelper, null, ignoreFilter, throwFilter);
            }
            copyAndReplaceFile(fileReplace, rootFileDir, fileNameReplaceUtil, ignoreFilter, throwFilter, replaceParams.replaceType);
            long end = System.currentTimeMillis();
            String message = "共计执行：" + (end - start) / 1000 + "秒，文件保存在：" + destFile.getAbsolutePath();
            return result.succeed(message);
        } catch (Exception e) {
            LogHelper.EXCEPTION.error("执行文件替换失败，param={}", replaceParams, e)
            result.failed("执行文件替换失败，原因是" + e.getMessage())
        }

        return result;
    }

    static void copyAndReplaceFile(FileReplace fileReplace, TouchFileDir touchFileDir, FileNameReplaceUtil fileNameReplaceUtil,
                                   FileNameFilter ignoreFilter, FileNameFilter throwFilter,
                                   String replaceType) {

        if (fileReplace.isDirectory()) {
            FileUtil.makeDir(fileReplace.destFile);
            fileReplace.srcFile.eachFile { file ->
                String fileName = file.getName();
                if (throwFilter.isMatch(fileName)) {
                    return;
                }

                FileReplace subFileReplace = new FileReplace();
                subFileReplace.srcFile = file;
                String newFileName = file.getName();

                //是否已经被touchFile处理过，默认为否，如果被处理过，则改为true
                boolean isTouchFileProcess = false;
                TouchFileDir subTouchFileDir = null;
                if (file.isDirectory() && touchFileDir != null) {
                    subTouchFileDir = touchFileDir.getChildTouchFileDirByDirName(fileName);
                    if (subTouchFileDir != null && subTouchFileDir.getTouchFileResult() != null) {
                        newFileName = subTouchFileDir.getChangedDirName();
                        isTouchFileProcess = true;
                    }
                }


                if (isTouchFileProcess == false && !ignoreFilter.isMatch(newFileName)) {
                    if ("1".equals(replaceType) || "3".equals(replaceType)) {
                        newFileName = fileNameReplaceUtil.doReplace(newFileName);
                    }
                }

                subFileReplace.destFile = fileReplace.getDestSubFile(newFileName);
                CopyAndReplaceServiceImpl.copyAndReplaceFile(subFileReplace, subTouchFileDir, fileNameReplaceUtil,
                        ignoreFilter, throwFilter, replaceType);
            }
        } else {
            boolean onlyCopy = true;
            if ("1".equals(replaceType) || "2".equals(replaceType)) {
                onlyCopy = false;
            }

            if (onlyCopy || ignoreFilter.isMatch(fileReplace.srcFile.name) || !isTextFile(fileReplace.srcFile)) {
                FileUtil.copyFile(fileReplace.srcFile, fileReplace.destFile);
            } else {
                String content = FileIo.readFileAsString(fileReplace.srcFile, "UTF-8");
                content = fileNameReplaceUtil.doReplace(content);
                FileIo.writeToFile(fileReplace.destFile, content, "UTF-8");

            }
        }
    }

    /**
     * 对替换的文件目录做路径转换解析及处理，需要进行全路径匹配扫描，并解析为数据进行相关存储
     * @param parentDir
     * @param touchFileHelper
     * @param parentFileDir
     * @param ignoreFilter
     * @param throwFilter
     */
   static TouchFileDir parseTouchFileResult(File parentDir, TouchFileHelper touchFileHelper, TouchFileDir parentFileDir,
                                      FileNameFilter ignoreFilter, FileNameFilter throwFilter) {
        if (parentFileDir == null) {
            parentFileDir = new TouchFileDir();
            parentFileDir.name = parentDir.getName();
            parentFileDir.level = 0;
            parentFileDir.touchFileType = TouchFileType.NONE;
        }

        parentDir.eachFile { file ->
            //非文件夹直接丢弃
            if (!file.isDirectory()) {
                return;
            }

            String fileName = file.getName();
            //如果类似 .svn, .idea等系统丢弃文件，直接略过
            if (throwFilter.isMatch(fileName)) {
                return;
            }

            TouchFileDir curFileDir = touchFileHelper.doTouchFile(fileName, parentFileDir);
            parseTouchFileResult(file, touchFileHelper, curFileDir, ignoreFilter, throwFilter);
        }

        //子目录处理完成，清空本目录层级下的疑似被touch到的数据
        parentFileDir.clearSusTouchResultList();
        parentFileDir.clearNotTouchedSubDir();
        return parentFileDir;

    }


    static boolean isTextFile(File file) {
        def inputStream = file.newInputStream();
        byte[] bytes = new byte[1024];
        int read = inputStream.read(bytes);
        boolean isText = true;
        for (int i = 0; i < read; i++) {
            if (bytes[i] == 0XFF || bytes[i] == 0X00) {
                isText = false;
                break;
            }
        }
        IOUtils.closeQuietly(inputStream);
        return isText;
    }
}
