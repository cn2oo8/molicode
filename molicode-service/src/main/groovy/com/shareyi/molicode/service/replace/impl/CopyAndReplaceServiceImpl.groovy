package com.shareyi.molicode.service.replace.impl

import com.shareyi.fileutil.FileIo
import com.shareyi.fileutil.FileUtil
import com.shareyi.molicode.common.constants.MoliCodeConstant
import com.shareyi.molicode.common.enums.EnumCode
import com.shareyi.molicode.common.enums.TemplateTypeEnum
import com.shareyi.molicode.common.enums.TouchFileType
import com.shareyi.molicode.common.filter.FileNameExpressionFilter
import com.shareyi.molicode.common.filter.FileNameFilter
import com.shareyi.molicode.common.utils.*
import com.shareyi.molicode.common.valid.Validate
import com.shareyi.molicode.common.vo.page.ReplaceParams
import com.shareyi.molicode.common.vo.replace.TouchFileDir
import com.shareyi.molicode.common.web.CommonResult
import com.shareyi.molicode.service.replace.CopyAndReplaceService
import com.shareyi.molicode.service.replace.FileNameReplaceUtil
import com.shareyi.molicode.service.replace.FileReplace
import org.apache.commons.io.IOUtils
import org.apache.commons.lang.StringUtils
import org.springframework.stereotype.Service

import java.util.concurrent.TimeUnit

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
            TemplateTypeEnum templateTypeEnum = EnumCode.Parser.parseTo(TemplateTypeEnum.class, replaceParams.templateType);
            if (templateTypeEnum == TemplateTypeEnum.GIT) {
                ValidateUtils.notEmptyField(replaceParams, "gitUrl");
                ValidateUtils.notEmptyField(replaceParams, "branchName");
                String repoDirPath = SystemFileUtils.buildGitRepoDir(replaceParams.gitUrl, replaceParams.branchName);
                File file = new File(repoDirPath);

                Validate.assertTrue(file.exists() && file.listFiles().length > 0, "仓库尚未拉取或者不存在，请检查！gitUrl=" + replaceParams.gitUrl);
                replaceParams.srcPath = repoDirPath;
            }

            if (StringUtils.isEmpty(replaceParams.srcPath)) {
                return result.failed("输入路径参数有误！");
            }
            //如果为headless模式，输出目录不能指定, 手动设置
            replaceParams.destPath = FileUtil.contactPath(SystemFileUtils.getSampleProjectOutDir(), MoliCodeStringUtils.getTimeBasedStr());

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
                return result.failed("源文件不存在，请检查！");
            }
            File destParentPath = new File(replaceParams.destPath);
            FileUtil.makeDir(destParentPath)
            CostWatch costWatch = CostWatch.createStarted();

            TouchFileHelper touchFileHelper = TouchFileHelper.getTouchFileHelper(replaceParams.dirReplaceExp);
            TouchFileDir rootFileDir = null;
            //对替换的文件目录做路径转换解析及处理，需要进行全路径匹配扫描，并解析为数据进行相关存储
            if (touchFileHelper != null) {
                LogHelper.FRONT_CONSOLE.info("文件路径转换计算开始,srcPath={}", srcFile.getAbsolutePath());
                rootFileDir = parseTouchFileResult(srcFile, touchFileHelper, null, ignoreFilter, throwFilter);
                LogHelper.FRONT_CONSOLE.info("文件路径转换计算结束,srcPath={}", srcFile.getAbsolutePath());
            }
            LogHelper.FRONT_CONSOLE.info("执行文件转换开始");
            copyAndReplaceFile(fileReplace, rootFileDir, fileNameReplaceUtil, ignoreFilter, throwFilter, replaceParams.replaceType);
            LogHelper.FRONT_CONSOLE.info("执行文件转换结束");

            //执行压缩处理
            LogHelper.FRONT_CONSOLE.info("执行工程文件压缩开始");
            String zipOutputRootDir = SystemFileUtils.buildZipOutputDir("sampleProject");
            FileUtil.makeDir(zipOutputRootDir);
            File destZipFile = new File(FileUtil.contactPath(zipOutputRootDir, destParentPath.getName() + ".zip"));
            ZipHelper.zipFile(destParentPath, destZipFile);
            LogHelper.FRONT_CONSOLE.info("执行工程文件压缩结束");

            costWatch.stop();
            LogHelper.FRONT_CONSOLE.info("共计执行：{}秒，文件保存在：{}", costWatch.getCost(TimeUnit.SECONDS), destFile.getAbsolutePath());
            result.addModel(MoliCodeConstant.CTX_KEY_ZIP_FILE_NAME, destZipFile.getName());
            result.addModel(MoliCodeConstant.ResultInfo.START_TIME_KEY, costWatch.getStartTime());
            result.addModel(MoliCodeConstant.ResultInfo.COST_TIME_KEY, costWatch.getCost(TimeUnit.MILLISECONDS));
            return result.succeed();
        } catch (Exception e) {
            LogHelper.FRONT_CONSOLE.error("执行文件替换失败，param={}", replaceParams, e)
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
