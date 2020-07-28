package com.imooc.o2o.config.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

@Configuration
//使用註解@EnableTransactionManagement後，
//在Serice層中直接添加@Transactional即可
@EnableTransactionManagement
public class TransactionManagementConfiguration implements TransactionManagementConfigurer {

    @Autowired
    private DataSource dataSource;

    @Override
    public TransactionManager annotationDrivenTransactionManager() {

        return new DataSourceTransactionManager(dataSource);
    }
}
