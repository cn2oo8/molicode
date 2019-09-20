package com.shareyi.molicode.hander.gencode.process;

import com.google.common.collect.Lists;
import com.shareyi.molicode.common.chain.handler.SimpleHandler;
import com.shareyi.molicode.common.chain.handler.awares.DataProcessHandlerAware;
import com.shareyi.molicode.common.constants.CommonConstant;
import com.shareyi.molicode.common.constants.MoliCodeConstant;
import com.shareyi.molicode.common.enums.DataModelTypeEnum;
import com.shareyi.molicode.common.utils.MoliCodeStringUtils;
import com.shareyi.molicode.common.vo.code.AutoCodeParams;
import com.shareyi.molicode.common.context.MoliCodeContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * cell 单元格 数据处理handler
 *
 * @author david
 * @date 2018/10/3
 */
@Service
public class CellDataProcessHandler extends SimpleHandler<MoliCodeContext> implements DataProcessHandlerAware {
    @Override
    public int getOrder() {
        return 4;
    }

    @Override
    public boolean shouldHandle(MoliCodeContext context) {
        DataModelTypeEnum dataModelTypeEnum = context.getDataModelTypeEnum();
        return dataModelTypeEnum == DataModelTypeEnum.CELL_LIST;
    }

    @Override
    public void doHandle(MoliCodeContext context) {
        //需要和lineDataProcessHandler配合使用，在其处理之后，再拆分为小cell
        Object data = context.get(MoliCodeConstant.CTX_KEY_DEF_DATA);
        AutoCodeParams autoCodeParams = context.getAutoCodeParams();

        if (data == null) {
            return;
        }
        if (!(data instanceof List)) {
            return;
        }
        List<String> lineList = (List<String>) data;
        String trimType = autoCodeParams.getExtStr(MoliCodeConstant.PARAM_KEY_TRIM_TYPE, CommonConstant.STD_YN_YES_STR);

        List<List<String>> cellDataList = Lists.newArrayListWithCapacity(lineList.size());
        for (String lineStr : lineList) {
            String[] lineCellStr = lineStr.split("[ \t]");
            List<String> trimedCellList = MoliCodeStringUtils.removeEmptyString(Lists.newArrayList(lineCellStr), Objects.equals(trimType, CommonConstant.STD_YN_YES_STR));
            cellDataList.add(trimedCellList);
        }
        context.put(MoliCodeConstant.CTX_KEY_DEF_DATA, cellDataList);
    }


}
