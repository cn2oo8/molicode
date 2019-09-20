package com.shareyi.molicode.handler.gencode.generate

import com.google.common.base.Function
import com.shareyi.molicode.common.chain.handler.SimpleHandler
import com.shareyi.molicode.common.chain.handler.awares.TemplateGenerateHandlerAware
import com.shareyi.molicode.common.constants.MoliCodeConstant
import com.shareyi.molicode.common.context.MoliCodeContext
import com.shareyi.molicode.common.enums.OutputTypeEnum
import com.shareyi.molicode.common.utils.*
import com.shareyi.molicode.common.vo.code.AutoCodeParams
import com.shareyi.molicode.common.vo.code.AutoMakeVo
import com.shareyi.molicode.common.vo.code.TemplateResultVo
import com.shareyi.molicode.common.vo.code.TemplateVo
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Service

/**
 * 输出到请求
 *
 * @author david
 * @since 2019/06/09
 */
@Service
class ContentResponseHandler extends SimpleHandler<MoliCodeContext> implements
        TemplateGenerateHandlerAware {
    @Override
    int getOrder() {
        return 5;
    }

    @Override
    boolean shouldHandle(MoliCodeContext context) {
        return Objects.equals(context.getAutoCodeParams().getOutputType(), OutputTypeEnum.RESPONSE.getCode());
    }

    @Override
    void doHandle(MoliCodeContext context) {
        final AutoMakeVo autoMakeVo = (AutoMakeVo) context.get(MoliCodeConstant.CTX_KEY_AUTO_MAKE);
        //获取模板列表对象，对每个模板生成模板结果
        AutoCodeParams autoCodeParams = context.getAutoCodeParams();
        List<TemplateVo> templates = autoMakeVo.getTemplates();
        Set<String> templateIdSet = autoCodeParams.getTemplateIdSet();

        File sourceFile = new File(autoMakeVo.getProjectOutputDir());
        List<TemplateResultVo> templateResultVos = MyLists.transformNotNull(templates, new Function<TemplateVo, TemplateResultVo>() {
            @Override
            TemplateResultVo apply(TemplateVo input) {
                if (!templateIdSet.contains(input.id) || StringUtils.isEmpty(input.renderedDestFilePath) ||
                        StringUtils.isEmpty(input.renderedContent)) {
                    return null;
                }
                TemplateResultVo templateResultVo = new TemplateResultVo();
                templateResultVo.setId(input.id);
                templateResultVo.name = input.name;
                templateResultVo.desc = input.desc;
                templateResultVo.relativePath = parseRelativePath(input, sourceFile);
                //系统&项目相关
                templateResultVo.outputDir = autoCodeParams.outputDir;
                templateResultVo.setRenderedContent(input.getRenderedContent());
                templateResultVo.setProjectKey(autoCodeParams.projectKey)
                return templateResultVo
            }
        });
        context.put(MoliCodeConstant.TEMPLATE_RESULT_LIST, templateResultVos);
    }

    /**
     * 转换为相对路径
     *
     * @param templateVo
     * @param parentFile
     * @return
     */
    private String parseRelativePath(TemplateVo templateVo, File parentFile) {
        File file = new File(parentFile.getAbsolutePath(), templateVo.renderedDestFilePath);
        String path = file.getAbsolutePath();
        String parentFilePath = parentFile.getAbsolutePath();

        if (StringUtils.startsWith(path, parentFilePath)) {
            path = path.substring(parentFilePath.length());
        }
        path = StringUtils.replaceAll(path, "\\\\", "/");
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        return path;
    }

}
