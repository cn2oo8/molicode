package com.shareyi.molicode.handler.model

import com.shareyi.molicode.common.chain.handler.SimpleHandler
import com.shareyi.molicode.common.chain.handler.awares.TableModelHandlerAware
import com.shareyi.molicode.common.constants.AutoCodeConstant
import com.shareyi.molicode.common.constants.MoliCodeConstant
import com.shareyi.molicode.common.filter.ColumnFilter
import com.shareyi.molicode.common.filter.impl.NameExpressionFilter
import com.shareyi.molicode.common.utils.BindResourceUtil
import com.shareyi.molicode.common.utils.PubUtils
import com.shareyi.molicode.common.vo.code.ColumnVo
import com.shareyi.molicode.common.vo.code.TableDefineVo
import com.shareyi.molicode.common.vo.code.TableModelVo
import com.shareyi.molicode.common.vo.page.TableModelPageVo
import com.shareyi.molicode.context.TableModelContext
import com.shareyi.molicode.service.gencode.impl.DictColumnProcessor
import org.springframework.stereotype.Service

import javax.annotation.Resource

/**
 * tableModel 数据库处理器
 *
 * @author zhangshibin
 * @since 2018/10/7
 */
@Service
class TableModelSmartHandler extends SimpleHandler<TableModelContext> implements TableModelHandlerAware {

    @Resource
    DictColumnProcessor dictColumnProcessor;

    @Override
    int getOrder() {
        return 4;
    }

    @Override
    boolean shouldHandle(TableModelContext tableModelContext) {
        return !tableModelContext.isReadonly();
    }

    @Override
    void doHandle(TableModelContext tableModelContext) {
        TableModelVo tableModelVo = tableModelContext.tableModelVo;
        TableDefineVo tableDefineVo = tableModelVo.tableDefine;
        TableModelPageVo tableModelPageVo = tableModelContext.tableModelPageVo;
        Map<String, String> bizFieldMap = this.getBizFieldExpMap();

        //对字典项字段进行预处理，分解出字典项数据
        dictColumnProcessor.process(tableModelVo, tableDefineVo.columns);

        //不启用
        if (tableModelPageVo.smartFlag == null || tableModelPageVo.smartFlag == 2) {
            def columnNames = PubUtils.joinColumnNames(tableDefineVo.columns)
            tableModelVo.putBizFields(MoliCodeConstant.BIZ_FIELDS_KEY_QUERYLIST, columnNames);
            tableModelVo.putBizFields(MoliCodeConstant.BIZ_FIELDS_KEY_ADDLIST, columnNames);
            tableModelVo.putBizFields(MoliCodeConstant.BIZ_FIELDS_KEY_UPDATELIST, columnNames);
            tableModelVo.putBizFields(MoliCodeConstant.BIZ_FIELDS_KEY_VIEWLIST, columnNames);
            tableModelVo.putBizFields(MoliCodeConstant.BIZ_FIELDS_KEY_ALLCOLUMN, columnNames);
            tableModelVo.putBizFields(MoliCodeConstant.BIZ_FIELDS_KEY_SEARCHKEY, "");
            tableModelVo.putBizFields(MoliCodeConstant.BIZ_FIELDS_KEY_UPDATETIME, "");
            tableModelVo.putBizFields(MoliCodeConstant.BIZ_FIELDS_KEY_CREATETIME, "");

        } else {
            bizFieldMap.each { entry ->
                ColumnFilter filter = new NameExpressionFilter(entry.value);
                List<ColumnVo> bizColumnList = filter.filterColumns(tableDefineVo.columns)
                tableModelVo.putBizFields(entry.key, PubUtils.joinColumnNames(bizColumnList));
            }
        }
    }

    /**
     * 获取字段配置的表达式map
     * @return
     */
    Map<String, String> getBizFieldExpMap() {
        Properties dbTableUtilPro = BindResourceUtil.getBindProperties(AutoCodeConstant.BIND_DBTABLEUTIL, false);
        //使用默认值设置map
        Map<String, String> bizFieldExpMap = new LinkedHashMap(MoliCodeConstant.BIZ_FIELDS_DEF_EXP_MAP);
        if (dbTableUtilPro == null) {
            return bizFieldExpMap;
        }

        dbTableUtilPro.each { entry ->
            String key = entry.key;
            if (!key.endsWith("Exp") && key.length() > 3) {
                return;
            }
            bizFieldExpMap.put(key.substring(0, key.length() - 3), entry.value);
        }
        return bizFieldExpMap;
    }
}
