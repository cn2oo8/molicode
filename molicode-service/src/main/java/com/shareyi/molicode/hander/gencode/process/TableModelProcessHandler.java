package com.shareyi.molicode.hander.gencode.process;

import com.shareyi.molicode.common.chain.handler.SimpleHandler;
import com.shareyi.molicode.common.chain.handler.awares.DataProcessHandlerAware;
import com.shareyi.molicode.common.constants.MoliCodeConstant;
import com.shareyi.molicode.common.enums.DataModelTypeEnum;
import com.shareyi.molicode.common.utils.XmlUtils;
import com.shareyi.molicode.common.vo.code.TableModelVo;
import com.shareyi.molicode.common.context.MoliCodeContext;
import org.springframework.stereotype.Service;

/**
 * tableModel 数据处理handler
 *
 * @author zhangshibin
 * @date 2018/10/3
 */
@Service
public class TableModelProcessHandler extends SimpleHandler<MoliCodeContext> implements DataProcessHandlerAware {
    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public boolean shouldHandle(MoliCodeContext context) {
        DataModelTypeEnum dataModelTypeEnum = context.getDataModelTypeEnum();
        return dataModelTypeEnum == DataModelTypeEnum.TABLE_MODEL;
    }

    @Override
    public void doHandle(MoliCodeContext context) {
        String content = context.getDataString(MoliCodeConstant.CTX_KEY_SRC_CONTENT);
        TableModelVo tableModelVo = XmlUtils.getTableModelByContent(content);
        context.put(MoliCodeConstant.CTX_KEY_TABLE_MODEL, tableModelVo);
    }
}
