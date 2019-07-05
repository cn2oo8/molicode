package com.shareyi.molicode.common.enums;

import com.alibaba.druid.util.JdbcConstants;

/**
 * 数据库类型
 *
 * @author zhangshibin
 * @date 2019/7/4
 */
public enum DatabaseNameEnum implements EnumCode<String> {

    MYSQL(JdbcConstants.MYSQL, "mysql"),
    SQL_SERVER(JdbcConstants.SQL_SERVER, "sqlServer"),
    SYBASE(JdbcConstants.SYBASE, "sybase"),
    POSTGRESQL(JdbcConstants.POSTGRESQL, "postgresql"),
    ORACLE(JdbcConstants.ORACLE, "oracle");

    String code;
    String desc;


    DatabaseNameEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
