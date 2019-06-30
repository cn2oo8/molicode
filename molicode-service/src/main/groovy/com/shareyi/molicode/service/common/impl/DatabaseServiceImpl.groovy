package com.shareyi.molicode.service.common.impl

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.shareyi.molicode.common.constants.ConfigKeyConstant
import com.shareyi.molicode.common.utils.LogHelper
import com.shareyi.molicode.common.web.CommonResult
import com.shareyi.molicode.domain.conf.AcConfig
import com.shareyi.molicode.service.common.DatabaseService
import groovy.sql.Sql
import org.apache.commons.collections4.MapUtils
import org.springframework.stereotype.Service

/**
 * 描述
 *
 * @author zhangshibin
 * @date 2019/6/29
 */
@Service
class DatabaseServiceImpl implements DatabaseService {

    @Override
    CommonResult<String> testConnection(AcConfig acConfig) {
        CommonResult<String> result = CommonResult.create();
        def sql = null;
        try {
            JSONObject databaseConfigMap = JSON.parseObject(acConfig.getConfigValue());
            String driverName = MapUtils.getString(databaseConfigMap, ConfigKeyConstant.DatabaseConfig.DRIVER_CLASS);
            String url = MapUtils.getString(databaseConfigMap, ConfigKeyConstant.DatabaseConfig.URL);
            String username = MapUtils.getString(databaseConfigMap, ConfigKeyConstant.DatabaseConfig.USERNAME);
            String password = MapUtils.getString(databaseConfigMap, ConfigKeyConstant.DatabaseConfig.PASSWORD);
            sql = Sql.newInstance(url, username, password, driverName);
            def meta = sql.connection.metaData;
            result.succeed();
        } catch (Exception e) {
            LogHelper.EXCEPTION.error("链接失败", e);
            result.failed("链接失败，原因是:" + e.getMessage());
        } finally {
            if (sql != null) {
                try {
                    sql.close();
                } catch (Exception ee) {

                }
            }
        }
        return result;
    }

}