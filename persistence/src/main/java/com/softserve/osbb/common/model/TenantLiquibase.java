package com.softserve.osbb.common.model;

import liquibase.integration.spring.SpringLiquibase;

import javax.sql.DataSource;

/**
 * Created by Anastasiia Fedorak on 8/22/16.
 */
//@EnableConfigurationProperties(LiquibaseProperties.class)
public class TenantLiquibase extends SpringLiquibase {
    private DataSource dataSource;
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(TenantLiquibase.class);
    public TenantLiquibase(TenantDataSource tenantDataSource) {
        this.dataSource = tenantDataSource.getDataSource();
        logger.info("creating tenant db");
        super.setDataSource(dataSource);
        super.setChangeLog("classpath:/db/changelog/db.changelog-master.json");
    }
}
