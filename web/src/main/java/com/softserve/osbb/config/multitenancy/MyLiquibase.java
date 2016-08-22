package com.softserve.osbb.config.multitenancy;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.sql.DataSource;

/**
 * Created by Anastasiia Fedorak on 8/22/16.
 */
@EnableConfigurationProperties(LiquibaseProperties.class)
public class MyLiquibase extends SpringLiquibase {
    private DataSource dataSource;
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(MyLiquibase.class);
    public MyLiquibase(MyDataSource myDataSource) {
        this.dataSource = myDataSource.getDataSource();
        logger.info("creating tenant db");
        super.setDataSource(dataSource);
        super.setChangeLog("classpath:/db/changelog/db.changelog-master.json");
    }
}
