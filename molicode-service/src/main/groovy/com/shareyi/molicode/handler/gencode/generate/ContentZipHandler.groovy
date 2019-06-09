package com.shareyi.molicode.handler.gencode.generate

import com.shareyi.fileutil.FileUtil
import com.shareyi.molicode.common.chain.handler.SimpleHandler
import com.shareyi.molicode.common.chain.handler.awares.TemplateGenerateHandlerAware
import com.shareyi.molicode.common.constants.MoliCodeConstant
import com.shareyi.molicode.common.context.MoliCodeContext
import com.shareyi.molicode.common.utils.LogHelper
import com.shareyi.molicode.common.utils.Profiles
import com.shareyi.molicode.common.utils.SystemFileUtils
import com.shareyi.molicode.common.vo.code.AutoMakeVo
import org.apache.commons.io.IOUtils
import org.springframework.stereotype.Service

import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

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
        return Profiles.instance.isHeadLess();
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
        ZipOutputStream out = null;
        BufferedOutputStream bos = null;
        try {
            out = new ZipOutputStream(new FileOutputStream(zipFilePath));
            bos = new BufferedOutputStream(out);
            compress(out, bos, sourceFile, sourceFile.getName());
            LogHelper.DEFAULT.error("执行文件压缩完成，src={}, zipFile={}", autoMakeVo.getProjectOutputDir(), zipFilePath);
            context.put(MoliCodeConstant.CTX_KEY_ZIP_FILE_NAME, zipFileName);
        } catch (Exception e) {
            LogHelper.DEFAULT.error("执行文件压缩失败，path={}", autoMakeVo.getProjectOutputDir(), e);
        } finally {
            IOUtils.closeQuietly(bos);
            IOUtils.closeQuietly(out);
        }
    }

    void compress(ZipOutputStream out, BufferedOutputStream bos, File sourceFile, String base) throws Exception {
        //如果路径为目录（文件夹）
        if (sourceFile.isDirectory()) {

            //取出文件夹中的文件（或子文件夹）
            File[] subFiles = sourceFile.listFiles();
//如果文件夹为空，则只需在目的地zip文件中写入一个目录进入点
            if (subFiles.length == 0) {
                out.putNextEntry(new ZipEntry(base + "/"));
            } else { //如果文件夹不为空，则递归调用compress，文件夹中的每一个文件（或文件夹）进行压缩
                for (int i = 0; i < subFiles.length; i++) {
                    compress(out, bos, subFiles[i], base + "/" + subFiles[i].getName());
                }
            }
        } else { //如果不是目录（文件夹），即为文件，则先写入目录进入点，之后将文件写入zip文件中
            out.putNextEntry(new ZipEntry(base));
            FileInputStream fos = new FileInputStream(sourceFile);
            BufferedInputStream bis = new BufferedInputStream(fos);
            int tag;
            //将源文件写入到zip文件中
            while ((tag = bis.read()) != -1) {
                bos.write(tag);
            }
            bis.close();
            fos.close();

        }
    }
}
