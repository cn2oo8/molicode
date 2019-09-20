package com.shareyi.molicode.hander.gencode.process;

import com.alibaba.fastjson.JSON;
import com.shareyi.molicode.common.chain.handler.SimpleHandler;
import com.shareyi.molicode.common.chain.handler.awares.DataProcessHandlerAware;
import com.shareyi.molicode.common.constants.MoliCodeConstant;
import com.shareyi.molicode.common.context.MoliCodeContext;
import com.shareyi.molicode.common.enums.DataModelTypeEnum;
import com.shareyi.molicode.common.utils.XmlUtils;
import com.shareyi.molicode.common.vo.code.TableModelVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * tableModel 数据处理handler
 *
 * @author david
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
        String tableModelPath = context.getAutoCodeParams().getTableModelPath();
        TableModelVo tableModelVo = null;
        //如果为json需要调整解析器
        if (StringUtils.isNotEmpty(tableModelPath) && StringUtils.endsWith(tableModelPath, ".json")) {
            tableModelVo = JSON.parseObject(content, TableModelVo.class);
        } else {
            tableModelVo = XmlUtils.getTableModelByContent(content);
        }
        context.put(MoliCodeConstant.CTX_KEY_TABLE_MODEL, tableModelVo);
    }
}
