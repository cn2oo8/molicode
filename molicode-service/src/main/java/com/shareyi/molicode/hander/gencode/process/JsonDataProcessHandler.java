package com.shareyi.molicode.hander.gencode.process;

import com.alibaba.fastjson.JSON;
import com.shareyi.molicode.common.chain.handler.SimpleHandler;
import com.shareyi.molicode.common.chain.handler.awares.DataProcessHandlerAware;
import com.shareyi.molicode.common.constants.MoliCodeConstant;
import com.shareyi.molicode.common.enums.DataModelTypeEnum;
import com.shareyi.molicode.common.enums.ResultCodeEnum;
import com.shareyi.molicode.common.exception.AutoCodeException;
import com.shareyi.molicode.common.utils.LogHelper;
import com.shareyi.molicode.common.context.MoliCodeContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * json 数据处理handler
 *
 * @author david
 * @date 2018/10/3
 */
@Service
public class JsonDataProcessHandler extends SimpleHandler<MoliCodeContext> implements DataProcessHandlerAware {
    @Override
    public int getOrder() {
        return 2;
    }

    @Override
    public boolean shouldHandle(MoliCodeContext context) {
        DataModelTypeEnum dataModelTypeEnum = context.getDataModelTypeEnum();
        return dataModelTypeEnum == DataModelTypeEnum.JSON;
    }

    @Override
    public void doHandle(MoliCodeContext context) {
        String content = context.getDataString(MoliCodeConstant.CTX_KEY_SRC_CONTENT);
        content = StringUtils.trim(content);
        if (StringUtils.isEmpty(content)) {
            LogHelper.DEFAULT.warn("输入数据为空！ 数据处理失败");
            return;
        }

        if (content.startsWith("[")) {
            context.put(MoliCodeConstant.CTX_KEY_DEF_DATA, JSON.parseArray(content));
        } else if (content.startsWith("{")) {
            context.put(MoliCodeConstant.CTX_KEY_DEF_DATA, JSON.parseObject(content));
        } else {
            throw new AutoCodeException("非合法的JSON数据格式，content=" + content, ResultCodeEnum.PARAM_ERROR);
        }
    }
}
