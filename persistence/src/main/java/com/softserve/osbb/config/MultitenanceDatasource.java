package com.softserve.osbb.config;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.AbstractDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class MultitenanceDatasource extends AbstractDataSource {

    private static Logger logger = Logger.getLogger(MultitenanceDatasource.class);
    private Map<Object, Object> targetDataSources;

    @Override
    public Connection getConnection() throws SQLException {
        DataSource ds = determineTargetDataSource();
        logger.info("Datasource: " + ds);
        logger.info("getting connection...");
        return ds.getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        DataSource ds = determineTargetDataSource();
        logger.info("Datasource: " + ds);
        logger.info("getting connection...");
        return ds.getConnection(username, password);
    }

    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        logger.info("set target datasources");
        this.targetDataSources = targetDataSources;
    }

    private DataSource determineTargetDataSource() {
        logger.info("determine target data source");
        Object lookupKey = TenantContext.getCurrentTenant();
        DataSource dataSource = (DataSource) this.targetDataSources.get(lookupKey);
        if (dataSource == null) {
            throw new IllegalStateException("Cannot determine target DataSource for lookup key [" + lookupKey + "]");
        }
        return dataSource;
    }

}
