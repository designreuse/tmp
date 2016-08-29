package com.softserve.osbb.common.model;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by Anastasiia Fedorak on 8/22/16.
 */
public class TenantDataSource {
    public static final String DRIVER = "org.postgresql.Driver";
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(TenantDataSource.class);
    private String tenantIdentifier;
    private String driver;
    private String url;
    private String username;
    private String pass;

    public TenantDataSource(Properties properties) {
        this.driver = DRIVER;
        this.tenantIdentifier = properties.getProperty("name");
        this.url = properties.getProperty("datasource.url");
        this.username = properties.getProperty("datasource.username");
        this.pass = properties.getProperty("datasource.password");
        logger.info("get properties");
    }

    public DataSource getDataSource(){
        logger.info("build data source for tenant=" + tenantIdentifier);
        return DataSourceBuilder.create()
                .driverClassName(driver)
                .url(url)
                .username(username)
                .password(pass)
                .build();
    }

    public String getTenantIdentifier(){
        return tenantIdentifier;
    }
}
