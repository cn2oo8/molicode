package com.shareyi.molicode.handler.model

import com.alibaba.fastjson.JSON
import com.shareyi.fileutil.FileUtil
import com.shareyi.molicode.common.chain.handler.SimpleHandler
import com.shareyi.molicode.common.chain.handler.awares.TableModelHandlerAware
import com.shareyi.molicode.common.enums.DataModelTypeEnum
import com.shareyi.molicode.common.enums.ResultCodeEnum
import com.shareyi.molicode.common.exception.ExceptionMaker
import com.shareyi.molicode.common.utils.Profiles
import com.shareyi.molicode.common.vo.code.TableModelVo
import com.shareyi.molicode.common.vo.page.TableModelPageVo
import com.shareyi.molicode.context.TableModelContext
import org.apache.commons.io.FileUtils
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Service

/**
 * 表模型sql模式加载器
 *
 * @author zhangshibin
 * @date 2019/7/4
 */
@Service
class TableModelSqlLoadHandler extends SimpleHandler<TableModelContext> implements TableModelHandlerAware {

    @Override
    int getOrder() {
        return 3;
    }

    @Override
    boolean shouldHandle(TableModelContext tableModelContext) {
        return Objects.equals(tableModelContext.tableModelPageVo.modelType, DataModelTypeEnum.JSON.getCode()) && tableModelContext.isReadonly();
    }

    @Override
    void doHandle(TableModelContext tableModelContext) {
        TableModelPageVo tableModelPageVo = tableModelContext.tableModelPageVo;
        File f = new File(FileUtil.contactPath(tableModelPageVo.tableModelDir, tableModelPageVo.tableName?.trim() + ".json"));
        String tableModelJson = FileUtils.readFileToString(f, Profiles.instance.fileEncoding)
        if (StringUtils.isEmpty(tableModelJson)) {
            throw ExceptionMaker.buildException("表模型还不存在，table=" + tableModelPageVo.tableName, ResultCodeEnum.ERROR);
        }
        tableModelContext.tableModelVo = JSON.parseObject(tableModelJson, TableModelVo.class);
    }
}
