package com.shareyi.molicode.configuaration;

import com.shareyi.molicode.common.utils.LogHelper;
import org.apache.commons.lang3.StringUtils;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.internal.util.jdbc.JdbcUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Objects;

/**
 * flayway 配置
 * <p>
 * 因为目前springboot1.x 版本的flyway版本太低，对H2 有兼容性问题
 * 故而升级到使用2.X版本的flyway，然后手动进行配置处理
 *
 * @author zhangshibin
 * @date 2018/11/4
 */
//@Configuration
public class FlywayConfiguration {

    @Value("${flyway.schemas}")
    private String flywaySchema;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Bean("no_use_str")
    public String flywayDatabase(DataSource dataSource) {
        Connection connection = null;
        if (makeSureSchemaExist(connection)) return "";
        //初始化flyway类
        Flyway flyway = new Flyway();
        //设置加载数据库的相关配置信息
        flyway.setDataSource(dataSource);
        //设置flyway扫描sql升级脚本、java升级脚本的目录路径或包路径，默认"db/migration"，可不写
        flyway.setLocations("classpath:/db");
        //设置sql脚本文件的编码，默认"UTF-8"，可不写
        flyway.setEncoding("UTF-8");
        flyway.setSchemas(flywaySchema);
        flyway.migrate();
        return "no_use_str";
    }

    private boolean makeSureSchemaExist(Connection connection) {
        try {
            //只处理H2数据库连接
            if (!Objects.equals(driverClassName, "org.h2.Driver") || StringUtils.isEmpty(flywaySchema)) {
                return true;
            }
            String urlLowercase = url.toLowerCase();
            String newUrl = url;
            if (urlLowercase.contains("schema=")) {
                int indexStart = urlLowercase.indexOf("schema=");
                int lastIndex = urlLowercase.indexOf(";", indexStart);
                if (lastIndex < 0) {
                    lastIndex = urlLowercase.length();
                }
                newUrl = url.substring(0, indexStart);
                if (lastIndex < url.length()) {
                    newUrl += url.substring(lastIndex + 1);
                }
            }
            connection = DriverManager.getConnection(newUrl, username, password);
            boolean findSchema = false;
            ResultSet resultSet = connection.getMetaData().getSchemas();
            while (resultSet.next()) {
                String schemaName = resultSet.getString("TABLE_SCHEM");
                if (StringUtils.equalsIgnoreCase(schemaName, flywaySchema)) {
                    findSchema = true;
                    break;
                }
            }
            JdbcUtils.closeResultSet(resultSet);

            //尝试自动创建schema
            if (!findSchema) {
                Statement statement = connection.createStatement();
                String sql = "CREATE SCHEMA " + flywaySchema;
                boolean success = statement.execute(sql);
                if (!success) {
                    LogHelper.DEFAULT.error("创建默认的数据库失败! sql={}", sql);
                    return true;
                }
                JdbcUtils.closeStatement(statement);
            }
        } catch (SQLException e) {
            LogHelper.EXCEPTION.error("获取数据库连接异常", e);
            if (connection != null) {
                JdbcUtils.closeConnection(connection);
            }
        }
        return false;
    }

}
