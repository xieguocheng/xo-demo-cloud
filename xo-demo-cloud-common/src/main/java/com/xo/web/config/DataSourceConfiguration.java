package com.xo.web.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 数据库连接池配置
 * @author xo
 */
@ConditionalOnClass(DruidDataSource.class)
@Configuration
public class DataSourceConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource DataSource(){
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return dataSource;
    }

   /* @Bean
    @ConfigurationProperties(prefix = "spring.ju.datasource")
    public DataSource promoteDataSource(){
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return dataSource;
    }*/
}
