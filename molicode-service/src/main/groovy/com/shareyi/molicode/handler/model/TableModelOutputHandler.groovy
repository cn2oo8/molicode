package com.shareyi.molicode.handler.model

import com.shareyi.fileutil.FileUtil
import com.shareyi.molicode.common.chain.handler.SimpleHandler
import com.shareyi.molicode.common.chain.handler.awares.TableModelHandlerAware
import com.shareyi.molicode.common.constants.CommonConstant
import com.shareyi.molicode.common.filter.ColumnFilter
import com.shareyi.molicode.common.filter.impl.PKFilter
import com.shareyi.molicode.common.vo.code.OptionVo
import com.shareyi.molicode.common.vo.code.TableDefineVo
import com.shareyi.molicode.common.vo.code.TableModelVo
import com.shareyi.molicode.common.vo.page.TableModelPageVo
import com.shareyi.molicode.context.TableModelContext
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Service

/**
 * tableModel 数据库处理器
 *
 * @author zhangshibin
 * @since 2018/10/7
 */
@Service
class TableModelOutputHandler extends SimpleHandler<TableModelContext> implements TableModelHandlerAware {
    @Override
    int getOrder() {
        return 4;
    }

    @Override
    boolean shouldHandle(TableModelContext tableModelContext) {
        return !Objects.equals(tableModelContext.tableModelPageVo.modelType, CommonConstant.MODEL_TYPE_JSON);
    }

    @Override
    void doHandle(TableModelContext tableModelContext) {
        TableModelVo tableModelVo = tableModelContext.tableModelVo;
        TableDefineVo tableDefineVo = tableModelVo.tableDefine;
        TableModelPageVo tableModelPageVo = tableModelContext.tableModelPageVo;
        ColumnFilter pKColumnFilter = new PKFilter();

        def sw = new StringWriter()
        sw.append("""<?xml version="1.0" encoding="UTF-8" ?> \n""");
        def xml = new groovy.xml.MarkupBuilder(sw)
        xml.tableModel {
            tableDefine(id: tableDefineVo.id, cnname: tableDefineVo.cnname, dbTableName: tableDefineVo.dbTableName) {
                tableDefineVo.columns.each {
                    return column(dataName: it.dataName, columnName: it.columnName,
                            cnname: it.cnname, columnType: it.columnType, canBeNull: it.canBeNull,
                            readonly: it.readonly, isPK: it.isPK, length: it.length, jspTag: it.jspTag,
                            dictName: it.dictName, comment: it.comment)
                }
            }
            orderColumns {
                pKColumnFilter.filterColumns(tableDefineVo.columns).each { pkColumn ->
                    return orderColumn(orderType: "desc", pkColumn.columnName)
                }
            }
            bizFieldsMap {
                tableModelVo.bizFieldsMap.each { entry ->
                    return bizFields(key: entry.key, entry.value)
                }
            }
            dicts {
                tableModelVo.dictMap.each { entry ->
                    def dictDomain = entry.value;
                    return dict(id: dictDomain.id, name: dictDomain.name) {
                        dictDomain.optionList.each {
                            OptionVo op = it;
                            option(value: op.value, cssClass: op.cssClass, op.name)
                        }
                    }

                }
            }
        }
        if (StringUtils.isBlank(tableModelPageVo.tableModelDir)) {
            tableModelPageVo.setTableModelDir(FileUtil.getRuntimeFilePath("tableModel/project_" + tableModelPageVo.projectKey))
        }
        File f = new File(FileUtil.contactPath(tableModelPageVo.tableModelDir, tableDefineVo.id?.trim() + ".xml"));
        FileUtil.makeSureFileExsit(f);
        //换行符号替换为Windows的换行符号
        f.withWriter("utf-8")
                { writer ->
                    writer.write sw.toString().replaceAll("\n", "\r\n");
                }

        print "table " + tableModelPageVo.tableName + "的XML文件成功生成，在：" + f.absolutePath;
        tableModelContext.setOutputPath(f.absolutePath)
    }
}
