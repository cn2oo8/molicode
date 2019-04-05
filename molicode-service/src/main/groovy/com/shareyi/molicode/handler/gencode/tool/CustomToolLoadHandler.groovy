package com.shareyi.molicode.handler.gencode.tool

import com.shareyi.molicode.common.chain.handler.SimpleHandler
import com.shareyi.molicode.common.chain.handler.awares.ToolLoadHandlerAware
import com.shareyi.molicode.common.constants.AutoCodeConstant
import com.shareyi.molicode.common.constants.MoliCodeConstant
import com.shareyi.molicode.common.utils.*
import com.shareyi.molicode.common.vo.code.AutoMakeVo
import com.shareyi.molicode.common.context.MoliCodeContext
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Service

import javax.annotation.Resource

/**
 * 用户自定义的工具加载器
 *
 * @author zhangshibin
 * @since 2018/10/4
 */
@Service
class CustomToolLoadHandler extends SimpleHandler<MoliCodeContext> implements ToolLoadHandlerAware {

    @Resource
    TemplateUtil templateUtil;

    @Override
    public int getOrder() {
        return 2;
    }

    @Override
    public boolean shouldHandle(MoliCodeContext context) {
        return true;
    }

    @Override
    public void doHandle(MoliCodeContext context) {

        AutoMakeVo autoMakeVo = (AutoMakeVo) context.get(MoliCodeConstant.CTX_KEY_AUTO_MAKE);
        String templateContent = autoMakeVo.getMoliTemplate(AutoCodeConstant.MOLI_TEMPLATE_CUSTOM_TOOL);
        if (StringUtils.isEmpty(templateContent)) {
            return;
        }

        // 自定义工具放在 customTool Map 之中
        templateUtil.renderContent(templateContent, context.templateBinding);
    }
}
