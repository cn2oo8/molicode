package com.shareyi.molicode.hander.gencode.main;

import com.shareyi.molicode.common.chain.HandlerChainFactoryImpl;
import com.shareyi.molicode.common.chain.handler.SimpleHandler;
import com.shareyi.molicode.common.chain.handler.awares.CodeGenMainHandlerAware;
import com.shareyi.molicode.common.chain.handler.awares.TemplateGenerateHandlerAware;
import com.shareyi.molicode.common.constants.MoliCodeConstant;
import com.shareyi.molicode.common.context.MoliCodeContext;
import com.shareyi.molicode.common.utils.LogHelper;
import com.shareyi.molicode.common.utils.MoliCodeStringUtils;
import com.shareyi.molicode.common.utils.Profiles;
import com.shareyi.molicode.common.vo.code.AutoMakeVo;
import org.springframework.stereotype.Service;

/**
 * 代码生成处理器
 *
 * @author david
 * @date 2018/10/3
 */
@Service
public class TemplateGenerateHandler extends SimpleHandler<MoliCodeContext> implements CodeGenMainHandlerAware {

    @Override
    public int getOrder() {
        return 4;
    }

    @Override
    public boolean shouldHandle(MoliCodeContext context) {
        return true;
    }

    @Override
    public void doHandle(MoliCodeContext context) {
        AutoMakeVo autoMake = (AutoMakeVo) context.get(MoliCodeConstant.CTX_KEY_AUTO_MAKE);
        if (MoliCodeStringUtils.compareVersion(autoMake.getMoliCodeVersion(), Profiles.getInstance().getMoliCodeVersion()) > 0) {
            LogHelper.FRONT_CONSOLE.warn("代码工具版本低于模板版本，可能运行异常，请注意! templateVersion={},sdkVersion={}", autoMake.getMoliCodeVersion(), Profiles.getInstance().getMoliCodeVersion());
        }
        HandlerChainFactoryImpl.executeByHandlerAware(TemplateGenerateHandlerAware.class, context);
    }
}
