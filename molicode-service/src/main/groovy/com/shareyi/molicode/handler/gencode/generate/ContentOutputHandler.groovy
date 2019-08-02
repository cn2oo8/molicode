package com.shareyi.molicode.handler.gencode.generate

import com.shareyi.molicode.common.chain.handler.SimpleHandler
import com.shareyi.molicode.common.chain.handler.awares.TemplateGenerateHandlerAware
import com.shareyi.molicode.common.constants.MoliCodeConstant
import com.shareyi.molicode.common.context.MoliCodeContext
import com.shareyi.molicode.common.enums.EngineType
import com.shareyi.molicode.common.enums.EnumCode
import com.shareyi.molicode.common.enums.OutputTypeEnum
import com.shareyi.molicode.common.utils.FileIoUtil
import com.shareyi.molicode.common.utils.LogHelper
import com.shareyi.molicode.common.vo.code.AutoCodeParams
import com.shareyi.molicode.common.vo.code.AutoMakeVo
import com.shareyi.molicode.common.vo.code.TemplateVo
import org.apache.commons.io.IOUtils
import org.apache.commons.lang3.StringUtils
import org.apache.poi.ss.usermodel.Workbook
import org.springframework.stereotype.Service

/**
 * 输出处理器
 *
 * @author zhangshibin* @since 2018/10/3
 */
@Service
class ContentOutputHandler extends SimpleHandler<MoliCodeContext> implements
        TemplateGenerateHandlerAware {
    @Override
    int getOrder() {
        return 3;
    }

    @Override
    boolean shouldHandle(MoliCodeContext context) {
        return true;
    }

    @Override
    void doHandle(MoliCodeContext context) {
        AutoMakeVo autoMake = (AutoMakeVo) context.get(MoliCodeConstant.CTX_KEY_AUTO_MAKE);
        AutoCodeParams autoCodeParams = context.autoCodeParams;
        //获取模板列表对象，对每个模板生成模板结果
        List<TemplateVo> templates = autoMake.getTemplates();

        //输出类型枚举
        OutputTypeEnum outputTypeEnum = EnumCode.Parser.parseToNullSafe(OutputTypeEnum.class, autoCodeParams.outputType, OutputTypeEnum.FRONT_CONSOLE);

        Set<String> templateIdSet = autoCodeParams.getTemplateIdSet();
        for (TemplateVo template : templates) {
            try {
                //如果不包含，continue
                if (!templateIdSet.contains(template.getId())) {
                    continue
                }
                switch (outputTypeEnum) {
                    case OutputTypeEnum.FRONT_CONSOLE:
                        //不能输出2进制文件
                        if (!Objects.equals(template.engine, EngineType.JXLS.type)) {
                            this.outputFront(context, template);
                        }
                        break;
                    case OutputTypeEnum.LOCAL_DIR:
                    case OutputTypeEnum.ZIP_FILE:
                        this.handleFileOut(template, context, autoCodeParams);
                        break;
                }
            } catch (Exception e) {
                LogHelper.FRONT_CONSOLE.error("输出文件失败， template={}", template, e)
            }
        }
    }

    /**
     * 处理文件流输出
     * @param templateVo
     * @param moliCodeContext
     */
    private void handleFileOut(TemplateVo template, MoliCodeContext context, AutoCodeParams autoCodeParams) {
        //如果输出路径为空，跳出
        if (StringUtils.isEmpty(template.renderedDestFilePath)) {
            return
        }
        //如果为输出不为Excel文件，且内容为空，跳出
        if (!Objects.equals(template.engine, EngineType.JXLS.type) && StringUtils.isEmpty(template.renderedContent)) {
            return;
        }
        AutoMakeVo autoMake = (AutoMakeVo) context.get(MoliCodeConstant.CTX_KEY_AUTO_MAKE);

        //适配旧版本的可输出到前台
        if (Objects.equals(template.getDestFile(), "moli_code_front.out")) {
            this.outputFront(context, template);
            return;
        }

        File file = new File(autoMake.getProjectOutputDir(), template.renderedDestFilePath);
        if (file.exists()) {
            //如果不允许覆盖，直接略过
            if (!autoCodeParams.overrideFlag) {
                LogHelper.FRONT_CONSOLE.warn "文件已存在，不允许覆盖，略过:" + file.absolutePath;
                return;
            }
        }

        File f = FileIoUtil.makeSureFileExist(file);
        if (f != null) {
            if (Objects.equals(template.engine, EngineType.JXLS.type)) {
                Workbook workbook = template.getExtObject("workbook");
                if (workbook == null) {
                    LogHelper.FRONT_CONSOLE.warn "[" + template.desc + "]生成的poi workbook为空；\n";
                    return
                }
                def outputStream = new FileOutputStream(f)
                try {
                    workbook.write(outputStream)
                } finally {
                    IOUtils.closeQuietly(outputStream)
                }
            } else {
                f.withWriter("UTF-8") { writer -> writer.write template.renderedContent }
            }
            LogHelper.FRONT_CONSOLE.info "[" + template.desc + "]模板执行成功，生成文件在：" + f.absolutePath;
        } else {
            LogHelper.FRONT_CONSOLE.error "[" + template.desc + "]模板执行失败，创建文件失败，路径：" + file.absolutePath;
        }
    }

/**
 * 输出到前台
 * @param moliCodeContext
 * @param template
 */
    private void outputFront(MoliCodeContext moliCodeContext, TemplateVo template) {
        StringBuilder stringBuilder = new StringBuilder("\n [" + template.desc + "]模板执行成功，输出到前台，应输出路径:" + template.getRenderedDestFilePath());
        stringBuilder.append("\n").append("==============模板输出开始 =================")
        stringBuilder.append("\n").append(template.getRenderedContent())
        stringBuilder.append("\n").append("==============模板输出结束 =================")
        LogHelper.FRONT_CONSOLE.info(stringBuilder.toString())
    }

}
