package com.imooc.o2o.config.dao;

import com.imooc.o2o.util.DESUtil;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.beans.PropertyVetoException;

@Configuration
//配置mapper掃描路徑
@MapperScan("com.imooc.o2o.dao")
public class DataSourceConfiguration {

    @Value("${jdbc.driver}")
    private String jdbcDriver;
    @Value("${jdbc.url}")
    private String jdbcUrl;
    @Value("${jdbc.username}")
    private String jdbcUsername;
    @Value("${jdbc.password}")
    private String jdbcPassword;

    @Bean(name = "dataSource")
    public ComboPooledDataSource createDataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(jdbcDriver);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUser(DESUtil.getDecryptString(jdbcUsername));
        dataSource.setPassword(DESUtil.getDecryptString(jdbcPassword));
        //c3p0私有屬性
        dataSource.setMaxPoolSize(30);
        dataSource.setMinPoolSize(10);
        //關閉連結後不自動commit
        dataSource.setAutoCommitOnClose(false);
        //連接超時
        dataSource.setCheckoutTimeout(10000);
        //失敗重試次數
        dataSource.setAcquireRetryAttempts(2);
        return dataSource;
    }
}
