package com.shareyi.molicode.handler.gencode.generate

import com.shareyi.molicode.common.chain.handler.SimpleHandler
import com.shareyi.molicode.common.chain.handler.awares.TemplateGenerateHandlerAware
import com.shareyi.molicode.common.constants.MoliCodeConstant
import com.shareyi.molicode.common.enums.EngineType
import com.shareyi.molicode.common.utils.LogHelper
import com.shareyi.molicode.common.utils.TemplateUtil
import com.shareyi.molicode.common.vo.code.AutoCodeParams
import com.shareyi.molicode.common.vo.code.AutoMakeVo
import com.shareyi.molicode.common.vo.code.TemplateVo
import com.shareyi.molicode.common.context.MoliCodeContext
import org.springframework.stereotype.Service

import javax.annotation.Resource

/**
 * 模板生成处理器
 *
 * @author david
 * @since 2018/10/3
 */
@Service
class ContentGenerateHandler extends SimpleHandler<MoliCodeContext> implements
        TemplateGenerateHandlerAware {

    @Resource
    TemplateUtil templateUtil;

    @Override
    int getOrder() {
        return 1;
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
        for (TemplateVo template : templates) {
            try {
                //如果页面上没有选择本template，自动跳过
                if (!autoCodeParams.containsTemplateIds(template.id)) {
                    LogHelper.DEFAULT.info("未选择本模板，略过:[" + template.desc + "],templateId=" + template.id);
                    continue;
                }

                if(!template.canHandleDataModel(context.getAutoCodeParams().getDataModelType())){
                    LogHelper.FRONT_CONSOLE.info("数据模型不匹配，[{}], templateId={}, 支持的数据模型为:{}", template.desc, template.id, template.acceptDataModelList);
                    continue;
                }

                String destFile = template.destFile;
                String renderPath = templateUtil.renderContent(destFile, context.templateBinding);
                renderPath = renderPath.replaceAll("//", "/");
                template.renderedDestFilePath = renderPath;


                String content = templateUtil.renderTemplate(template, context.templateBinding);
                if (Objects.equals(template.engine, EngineType.JXLS.type) || content != null) {
                    template.renderedContent = content;
                    LogHelper.DEFAULT.info("[{}]模板执行成功;", template.desc);
                } else {
                    LogHelper.FRONT_CONSOLE.warn "模板不存在或为空，略过:[" + template.desc + "],templateId=" + template.id + "\n";
                    continue;
                }
            } catch (Exception e) {
                LogHelper.FRONT_CONSOLE.error "[" + template.desc + "]模板执行失败，原因是：" + e.message, e;
            }
        }
    }
}
