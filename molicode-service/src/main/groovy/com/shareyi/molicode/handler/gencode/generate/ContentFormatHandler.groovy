package com.shareyi.molicode.handler.gencode.generate

import com.google.googlejavaformat.java.Formatter
import com.shareyi.molicode.common.chain.handler.SimpleHandler
import com.shareyi.molicode.common.chain.handler.awares.TemplateGenerateHandlerAware
import com.shareyi.molicode.common.constants.MoliCodeConstant
import com.shareyi.molicode.common.utils.LogHelper
import com.shareyi.molicode.common.vo.code.AutoMakeVo
import com.shareyi.molicode.common.vo.code.TemplateVo
import com.shareyi.molicode.common.context.MoliCodeContext
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Service

/**
 * 格式化处理器
 *
 * @author david
 * @since 2018/10/3
 */
@Service
class ContentFormatHandler extends SimpleHandler<MoliCodeContext> implements
        TemplateGenerateHandlerAware {

    Formatter formatter = new Formatter();

    @Override
    int getOrder() {
        return 2;
    }

    @Override
    boolean shouldHandle(MoliCodeContext context) {
        return true;
    }

    @Override
    void doHandle(MoliCodeContext context) {
        AutoMakeVo autoMake = (AutoMakeVo) context.get(MoliCodeConstant.CTX_KEY_AUTO_MAKE);
        //获取模板列表对象，对每个模板生成模板结果
        List<TemplateVo> templates = autoMake.getTemplates();
        for (TemplateVo template : templates) {
            try {
                if (StringUtils.isEmpty(template.renderedContent) ||
                        !isJavaFile(template)) {
                    continue;
                }
                String formattedSource = formatter.formatSource(template.renderedContent);
                template.renderedContent = formattedSource
            } catch (Exception e) {
                LogHelper.EXCEPTION.error("format file failed, templateId={} " + template.getId(), e);
            }
        }
    }

    boolean isJavaFile(TemplateVo templateVo) {
        return StringUtils.endsWith(templateVo.renderedDestFilePath, ".java");
    }
}
