package com.shareyi.molicode.handler.gencode.generate

import com.shareyi.fileutil.FileUtil
import com.shareyi.molicode.common.chain.handler.SimpleHandler
import com.shareyi.molicode.common.chain.handler.awares.TemplateGenerateHandlerAware
import com.shareyi.molicode.common.constants.MoliCodeConstant
import com.shareyi.molicode.common.context.MoliCodeContext
import com.shareyi.molicode.common.enums.OutputTypeEnum
import com.shareyi.molicode.common.utils.LogHelper
import com.shareyi.molicode.common.utils.SystemFileUtils
import com.shareyi.molicode.common.utils.ZipHelper
import com.shareyi.molicode.common.vo.code.AutoMakeVo
import org.apache.commons.io.FileUtils
import org.springframework.stereotype.Service

/**
 * 对输出文件进行压缩
 *
 * @author zhangshibin
 * @since 2019/06/09
 */
@Service
class ContentZipHandler extends SimpleHandler<MoliCodeContext> implements
        TemplateGenerateHandlerAware {
    @Override
    int getOrder() {
        return 4;
    }

    @Override
    boolean shouldHandle(MoliCodeContext context) {
        return Objects.equals(context.getAutoCodeParams().getOutputType(), OutputTypeEnum.ZIP_FILE.getCode());
    }

    @Override
    void doHandle(MoliCodeContext context) {
        AutoMakeVo autoMakeVo = (AutoMakeVo) context.get(MoliCodeConstant.CTX_KEY_AUTO_MAKE);
        File sourceFile = new File(autoMakeVo.getProjectOutputDir());
        if (!sourceFile.exists() || !sourceFile.isDirectory() || sourceFile.listFiles().length < 1) {
            LogHelper.FRONT_CONSOLE.error("项目输出目录为空，可能未生成输出代码！path={}", autoMakeVo.getProjectOutputDir())
            return;
        }
        LogHelper.DEFAULT.info("开始进行文件夹压缩，path={}", autoMakeVo.getProjectOutputDir())
        String zipParentDirPath = SystemFileUtils.buildZipOutputDir(context.autoCodeParams.getProjectKey());
        FileUtil.makeDir(zipParentDirPath);
        def zipFileName = sourceFile.getName() + ".zip"
        String zipFilePath = FileUtil.contactPath(zipParentDirPath, zipFileName);
        File zipFile = new File(zipFilePath);
        //执行压缩
        ZipHelper.zipFile(sourceFile, zipFile);
        context.put(MoliCodeConstant.CTX_KEY_ZIP_FILE_NAME, zipFileName);
        //压缩完毕后，可以删除源文件夹
        if (sourceFile.getAbsolutePath().startsWith(FileUtil.getRunPath())) {
            FileUtils.deleteDirectory(sourceFile);
        }
    }


}
