package com.shareyi.molicode.handler.gencode.tool;

import com.shareyi.molicode.common.chain.handler.SimpleHandler
import com.shareyi.molicode.common.chain.handler.awares.ToolLoadHandlerAware;
import com.shareyi.molicode.common.constants.AutoCodeConstant
import com.shareyi.molicode.common.constants.MoliCodeConstant
import com.shareyi.molicode.common.utils.TemplateUtil
import com.shareyi.molicode.common.vo.code.AutoMakeVo;
import com.shareyi.molicode.common.context.MoliCodeContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource

/**
 * 用户自定义数据处理器
 *
 * @author david
 * @since 2018/10/3
 */
@Service
class CustomDataProcessHandler extends SimpleHandler<MoliCodeContext> implements ToolLoadHandlerAware {

    @Resource
    TemplateUtil templateUtil;

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
        AutoMakeVo autoMakeVo = (AutoMakeVo) context.get(MoliCodeConstant.CTX_KEY_AUTO_MAKE);
        String customDataProcessContent = autoMakeVo.getMoliTemplate(AutoCodeConstant.MOLI_TEMPLATE_CUSTOM_DATA_PROCESS);
        if (StringUtils.isEmpty(customDataProcessContent)) {
            return;
        }
        templateUtil.renderContent(customDataProcessContent, context.templateBinding);

    }
}
