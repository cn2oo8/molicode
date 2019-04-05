package com.shareyi.molicode.service.gencode.impl

import com.shareyi.molicode.common.chain.HandlerChainFactoryImpl
import com.shareyi.molicode.common.chain.handler.awares.TableModelHandlerAware
import com.shareyi.molicode.common.constants.AutoCodeConstant
import com.shareyi.molicode.common.constants.ConfigKeyConstant
import com.shareyi.molicode.common.enums.DataTypeEnum
import com.shareyi.molicode.common.filter.ColumnFilter
import com.shareyi.molicode.common.filter.impl.NameExpressionFilter
import com.shareyi.molicode.common.filter.impl.PKFilter
import com.shareyi.molicode.common.utils.BindResourceUtil
import com.shareyi.molicode.common.utils.LogHelper
import com.shareyi.molicode.common.utils.PubUtils
import com.shareyi.molicode.common.utils.TableNameUtil
import com.shareyi.molicode.common.valid.Validate
import com.shareyi.molicode.common.vo.code.*
import com.shareyi.molicode.common.vo.page.TableModelPageVo
import com.shareyi.molicode.common.web.CommonResult
import com.shareyi.molicode.context.TableModelContext
import com.shareyi.molicode.service.conf.AcConfigService
import com.shareyi.molicode.service.gencode.ColumnProcessor
import com.shareyi.molicode.service.gencode.DatabaseTableService
import com.shareyi.fileutil.FileUtil
import groovy.sql.Sql
import org.apache.commons.collections4.MapUtils
import org.apache.commons.lang.StringUtils
import org.springframework.stereotype.Service

import javax.annotation.Resource

@Service("databaseTableService")
class DatabaseTableServiceImpl implements DatabaseTableService {


    @Resource
    AcConfigService acConfigService;


    CommonResult generateTableModel(TableModelPageVo tableModelPageVo) {
        CommonResult<String> result = CommonResult.create();
        try {
            def context = TableModelContext.create(tableModelPageVo)
            HandlerChainFactoryImpl.executeByHandlerAware(TableModelHandlerAware.class, context);
            result.succeed(context.outputPath);
        } catch (Exception e) {
            LogHelper.EXCEPTION.error("生成表模型失败，tableModelPageVo={}" , tableModelPageVo ,e);
            result.failed("生成表模型失败, 表:"+ tableModelPageVo.tableName +", 原因是:"+e.getMessage());
        }
        return result;
    }

    /**
     * 生成数据库表名的tableModel xml文件
     * @param tName 表名
     */
    CommonResult<List<SimpleTableInfoVo>> getTableList(String projectKey) {
        CommonResult result = CommonResult.create();
        try {
            Validate.notEmpty(projectKey, "projectKey不能为空")
            Map<String, Map<String, String>> configMap = acConfigService.getConfigMapByProjectKey(projectKey, DataTypeEnum.JSON);
            Map<String, String> databaseConfigMap = configMap.get(ConfigKeyConstant.DatabaseConfig.CONFIG_KEY);
            String driverName = MapUtils.getString(databaseConfigMap, ConfigKeyConstant.DatabaseConfig.DRIVER_CLASS);
            String url = MapUtils.getString(databaseConfigMap, ConfigKeyConstant.DatabaseConfig.URL);
            String username = MapUtils.getString(databaseConfigMap, ConfigKeyConstant.DatabaseConfig.USERNAME);
            String password = MapUtils.getString(databaseConfigMap, ConfigKeyConstant.DatabaseConfig.PASSWORD);
            Validate.notEmpty(driverName, "数据库驱动不能为空")
            Validate.notEmpty(url, "数据库url不能为空")
            Validate.notEmpty(username, "数据库userName不能为空")
            Validate.notEmpty(password, "数据库密码不能为空")

            def sql = Sql.newInstance(url, username, password, driverName);
            TableNameUtil tableNameUtil = new TableNameUtil()
            def meta = sql.connection.metaData
            String[] s = new String[1];
            s[0] = "TABLE";

            def list = [];
            def tableInfo = meta.getTables(null, null, null, s);
            while (tableInfo.next()) {
                SimpleTableInfoVo tInfo = new SimpleTableInfoVo();
                String tableName = tableInfo.getString("TABLE_NAME");
                tInfo.tableName = tableName;
                tInfo.id = tableNameUtil.upperFirst(tableNameUtil.convertToBeanNames(tableName));
                tInfo.cnname = tableInfo.getString("REMARKS");
                if (tInfo.cnname == null || tInfo.cnname == '') {
                    tInfo.cnname = tInfo.id;
                }
                list.add(tInfo)
            }
            result.addDefaultModel(list)
            result.succeed()
        } catch (Exception e) {
            LogHelper.DEFAULT.error("获取数据库表列表异常", e)
            result.failed("获取数据库表列表异常， 原因是：" + e.getMessage())
        }
        return result;

    }
}
