package com.shareyi.molicode.hander.gencode.main;

import com.shareyi.molicode.common.chain.HandlerChainFactoryImpl;
import com.shareyi.molicode.common.chain.handler.SimpleHandler;
import com.shareyi.molicode.common.chain.handler.awares.DataProcessHandlerAware;
import com.shareyi.molicode.common.chain.handler.awares.CodeGenMainHandlerAware;
import com.shareyi.molicode.common.context.MoliCodeContext;
import org.springframework.stereotype.Service;

/**
 * 数据处理器
 *
 * @author zhangshibin
 * @date 2018/10/3
 */
@Service
public class DataProcessHandler extends SimpleHandler<MoliCodeContext> implements CodeGenMainHandlerAware {

    @Override
    public boolean shouldHandle(MoliCodeContext context) {
        return true;
    }

    @Override
    public void doHandle(MoliCodeContext context) {
        //加载数据, 子责任链
        HandlerChainFactoryImpl.executeByHandlerAware(DataProcessHandlerAware.class, context);
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
