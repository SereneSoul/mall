package com.mmall.user.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Bean(name = "dataSource")
    public DataSource createDataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(driverClassName);
        druidDataSource.setUrl(url);
        druidDataSource.setName(username);
        druidDataSource.setPassword(password);
        druidDataSource.setMaxActive(20);
        druidDataSource.setMinIdle(5);
        return druidDataSource;
    }
}
