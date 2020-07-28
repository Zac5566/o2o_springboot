package com.imooc.o2o.config.dao;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class SessionFactoryConfiguration {
    //mybatis-config.xml
    private static String mybatisConfigFile;

    //靜態屬性沒辦法直接注入
    @Value("${mybatis_config_file}")
    public void setMybatisConfigFile(String mybatisConfigFile) {
        SessionFactoryConfiguration.mybatisConfigFile = mybatisConfigFile;
    }

    //mybatis mapper文件所在路徑
    private static String mapperPath;
    @Value("${mapper_path}")
    public void setMapperPath(String mapperPath) {
        SessionFactoryConfiguration.mapperPath = mapperPath;
    }

    //實體類的package
    @Value("${type_alias_package}")
    private String typeAliasPackage;

    @Autowired
    private DataSource dataSource;

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean createSqlSessionFactoryBean() throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        //設置mybatis configuration掃描路徑
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(mybatisConfigFile));
        //添加mapper掃描路徑
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX+mapperPath;
        sqlSessionFactoryBean.setMapperLocations(pathMatchingResourcePatternResolver.getResources(packageSearchPath));
        //設置dataSource
        sqlSessionFactoryBean.setDataSource(dataSource);
        //typeAlias掃描路徑
        sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasPackage);
        return sqlSessionFactoryBean;
    }
}
