package com.shareyi.molicode.hander.gencode.process;

import com.shareyi.molicode.common.chain.handler.SimpleHandler;
import com.shareyi.molicode.common.chain.handler.awares.DataProcessHandlerAware;
import com.shareyi.molicode.common.constants.CommonConstant;
import com.shareyi.molicode.common.constants.MoliCodeConstant;
import com.shareyi.molicode.common.context.MoliCodeContext;
import com.shareyi.molicode.common.enums.DataModelTypeEnum;
import com.shareyi.molicode.common.utils.MoliCodeStringUtils;
import com.shareyi.molicode.common.vo.code.AutoCodeParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 原始内容 数据处理handler
 *
 * @author david
 * @date 2018/10/3
 */
@Service
public class RawContentProcessHandler extends SimpleHandler<MoliCodeContext> implements DataProcessHandlerAware {
    @Override
    public int getOrder() {
        return 6;
    }

    @Override
    public boolean shouldHandle(MoliCodeContext context) {
        DataModelTypeEnum dataModelTypeEnum = context.getDataModelTypeEnum();
        if (dataModelTypeEnum == DataModelTypeEnum.RAW_CONTENT) {
            return true;
        }
        return false;
    }

    @Override
    public void doHandle(MoliCodeContext context) {
        String content = context.getDataString(MoliCodeConstant.CTX_KEY_SRC_CONTENT);
        context.put(MoliCodeConstant.CTX_KEY_DEF_DATA, content);
    }
}
