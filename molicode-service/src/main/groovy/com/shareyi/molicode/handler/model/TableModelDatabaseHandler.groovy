package com.shareyi.molicode.handler.model

import com.shareyi.molicode.common.chain.handler.SimpleHandler
import com.shareyi.molicode.common.chain.handler.awares.TableModelHandlerAware
import com.shareyi.molicode.common.constants.ConfigKeyConstant
import com.shareyi.molicode.common.enums.ResultCodeEnum
import com.shareyi.molicode.common.exception.AutoCodeException
import com.shareyi.molicode.common.exception.ExceptionMaker
import com.shareyi.molicode.common.utils.LogHelper
import com.shareyi.molicode.common.utils.TableNameUtil
import com.shareyi.molicode.common.vo.code.ColumnVo
import com.shareyi.molicode.common.vo.code.TableDefineVo
import com.shareyi.molicode.common.vo.code.TableModelVo
import com.shareyi.molicode.common.vo.page.TableModelPageVo
import com.shareyi.molicode.context.TableModelContext
import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import org.apache.commons.collections4.MapUtils
import org.apache.commons.lang.StringUtils
import org.springframework.stereotype.Service

import java.sql.DatabaseMetaData

/**
 * tableModel 数据库处理器
 *
 * @author zhangshibin
 * @since 2018/10/7
 */
@Service
class TableModelDatabaseHandler extends SimpleHandler<TableModelContext> implements TableModelHandlerAware {

    @Override
    int getOrder() {
        return 2;
    }

    @Override
    boolean shouldHandle(TableModelContext tableModelContext) {
        //如果外部传入了tableModel，就不需要查库了，一般是通过SQL解析得来的
        return tableModelContext.tableModelVo == null &&
                !tableModelContext.isReadonly();
    }

    @Override
    void doHandle(TableModelContext tableModelContext) {
        TableModelPageVo tableModelPageVo = tableModelContext.tableModelPageVo;
        Map<String, Map<String, String>> configMap = tableModelContext.getProjectConfigMap();
        Map<String, String> databaseConfigMap = configMap.get(ConfigKeyConstant.DatabaseConfig.CONFIG_KEY);
        String driverName = MapUtils.getString(databaseConfigMap, ConfigKeyConstant.DatabaseConfig.DRIVER_CLASS);
        String url = MapUtils.getString(databaseConfigMap, ConfigKeyConstant.DatabaseConfig.URL);
        String username = MapUtils.getString(databaseConfigMap, ConfigKeyConstant.DatabaseConfig.USERNAME);
        String password = MapUtils.getString(databaseConfigMap, ConfigKeyConstant.DatabaseConfig.PASSWORD);


        def sql = Sql.newInstance(url, username, password, driverName);
        try {
            TableNameUtil tableNameUtil = new TableNameUtil();

            TableModelVo tableModel = new TableModelVo();
            TableDefineVo tableDefine = new TableDefineVo();
            def columns = [];
            tableDefine.columns = columns;
            tableModel.tableDefine = tableDefine;

            def meta = sql.connection.metaData
            String[] schemaTypes = new String[1];
            schemaTypes[0] = "TABLE";
            def tableInfo = meta.getTables(null, null, tableModelPageVo.getTableName(), schemaTypes);
            if (tableInfo.next()) {
                String tableName = tableInfo.getString("TABLE_NAME");
                tableDefine.id = tableNameUtil.upperFirst(tableNameUtil.convertToBeanNames(tableName));
                tableDefine.dbTableName = tableName;

                if (StringUtils.isNotBlank(tableModelPageVo.getCnname())) {
                    tableDefine.cnname = tableModelPageVo.getCnname();
                } else {
                    tableDefine.cnname = tableInfo.getString("REMARKS");
                    if (tableDefine.cnname == null || tableDefine.cnname == '') {
                        tableDefine.cnname = tableDefine.id;
                    }
                }
            } else {
                throw new AutoCodeException(tableModelPageVo.tableName + "表不存在！执行失败！");
            }

            def cols = meta.getColumns(null, null, tableModelPageVo.getTableName(), null);
            while (cols.next()) {
                ColumnVo column = new ColumnVo();
                column.columnName = cols.getString('COLUMN_NAME');
                column.dataName = tableNameUtil.convertToBeanNames(column.columnName);
                column.cnname = cols.getString('REMARKS');
                column.columnType = cols.getString('TYPE_NAME');
                column.length = cols.getInt('COLUMN_SIZE');
                column.isPK = cols.getString('IS_AUTOINCREMENT') == "YES";
                column.canBeNull = cols.getString('IS_NULLABLE') == "YES";
                column.jspTag = tableNameUtil.getJspTag(column.columnType);

                if (column.cnname == null || column.cnname == '') {
                    column.cnname = column.columnName;
                }

                //进行一次trim操作
                column.comment = column.cnname.trim();
                column.cnname = column.comment;
                columns.add(column);
            }

            getTableComment(sql, meta, tableModelPageVo, tableDefine);

            //将解析的数据设置回context
            tableModelContext.setTableModelVo(tableModel)
        } catch (Exception e) {
            LogHelper.EXCEPTION.error("获取数据库信息失败", e);
            throw ExceptionMaker.buildException("获取数据库信息失败，原因是" + e.getMessage(), ResultCodeEnum.EXCEPTION);
        } finally {
            sql.close();
        }
    }

    private GroovyRowResult getTableComment(Sql sql, DatabaseMetaData meta, TableModelPageVo tableModelPageVo, TableDefineVo tableDefine) {
        try {
            GroovyRowResult firstRow = sql.firstRow("Select table_name,table_comment from INFORMATION_SCHEMA.TABLES Where table_schema = ? AND table_name =? ", [meta['database'], tableModelPageVo.getTableName()])
            if (firstRow != null) {
                def comment = firstRow.getAt(1);
                String commentStr = comment == null ? null : comment.toString();
                if (StringUtils.isNotEmpty(commentStr)) {
                    tableDefine.setCnname(commentStr);
                }
            }
        } catch (Exception e) {
            LogHelper.EXCEPTION.error("获取表注释失败, tableName={}", tableModelPageVo.tableName, e);
        }
    }
}
