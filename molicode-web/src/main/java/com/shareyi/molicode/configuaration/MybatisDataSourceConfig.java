package com.shareyi.molicode.configuaration;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * MyBatis 配置，需要的时候再放开
 *
 * @author david
 * @date 2018/8/21
 */

//@Configuration
//@MapperScan(basePackages = "mybatis.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
public class MybatisDataSourceConfig {

    //此处使用自动装配的H2数据源,可自定义数据源
    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        //sessionFactory.setTypeAliasesPackage("mybatis.model");
        sessionFactory.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml"));
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return sessionFactory.getObject();
    }

}
