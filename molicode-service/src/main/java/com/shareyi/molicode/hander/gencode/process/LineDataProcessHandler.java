package com.shareyi.molicode.hander.gencode.process;

import com.shareyi.molicode.common.chain.handler.SimpleHandler;
import com.shareyi.molicode.common.chain.handler.awares.DataProcessHandlerAware;
import com.shareyi.molicode.common.constants.CommonConstant;
import com.shareyi.molicode.common.constants.MoliCodeConstant;
import com.shareyi.molicode.common.enums.DataModelTypeEnum;
import com.shareyi.molicode.common.utils.MoliCodeStringUtils;
import com.shareyi.molicode.common.vo.code.AutoCodeParams;
import com.shareyi.molicode.common.context.MoliCodeContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 单行文本 数据处理handler
 *
 * @author zhangshibin
 * @date 2018/10/3
 */
@Service
public class LineDataProcessHandler extends SimpleHandler<MoliCodeContext> implements DataProcessHandlerAware {
    @Override
    public int getOrder() {
        return 3;
    }

    @Override
    public boolean shouldHandle(MoliCodeContext context) {
         DataModelTypeEnum dataModelTypeEnum  = context.getDataModelTypeEnum();
         if(dataModelTypeEnum == DataModelTypeEnum.LINE_LIST || dataModelTypeEnum == DataModelTypeEnum.CELL_LIST){
             return true;
         }
        return false;
    }

    @Override
    public void doHandle(MoliCodeContext context) {
        String content = context.getDataString(MoliCodeConstant.CTX_KEY_SRC_CONTENT);
        AutoCodeParams autoCodeParams = context.getAutoCodeParams();
        if (StringUtils.isEmpty(content)) {
            return;
        }
        String trimType = autoCodeParams.getExtStr(MoliCodeConstant.PARAM_KEY_TRIM_TYPE, CommonConstant.STD_YN_YES_STR);
        List<String> lineStrList = MoliCodeStringUtils.splitToList(content, CommonConstant.LINE_STR);
        List<String> trimedList = MoliCodeStringUtils.removeEmptyString(lineStrList, Objects.equals(trimType, CommonConstant.STD_YN_YES_STR));
        context.put(MoliCodeConstant.CTX_KEY_DEF_DATA, trimedList);
    }
}
