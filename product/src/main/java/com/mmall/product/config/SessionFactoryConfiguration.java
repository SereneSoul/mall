package com.mmall.product.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;
@Configuration
public class SessionFactoryConfiguration {

    @Value("${mybatis.config.file}")
    private String mybatisConfigFilePath;

    @Value("${mapper.path}")
    private String mapperPath;

    @Value("${entity.package}")
    private String eneityPackage;

    @Autowired
    private DataSource dataSource;

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean createSqlSessionFactoryBean() throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        //mybatis文件位置
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(mybatisConfigFilePath));
        //mapper包
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        String packageSearchPath = PathMatchingResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + mapperPath;
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(packageSearchPath));
        //dataSource
        sqlSessionFactoryBean.setDataSource(dataSource);
        //entity包
        sqlSessionFactoryBean.setTypeAliasesPackage(eneityPackage);
        return sqlSessionFactoryBean;
    }
}
