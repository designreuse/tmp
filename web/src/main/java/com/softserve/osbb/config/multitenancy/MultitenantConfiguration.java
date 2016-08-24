package com.softserve.osbb.config.multitenancy;

import com.softserve.osbb.model.common.TenantDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.*;
/**
 * Created by Anastasiia Fedorak on 8/22/16.
 */
@Configuration
public class MultitenantConfiguration {
    private static Logger logger = org.apache.log4j.Logger.getLogger(MultitenantConfiguration.class);

    @Autowired
    private DataSourceProperties properties;

    @Autowired
    ApplicationContext applicationContext;

    private Map<Object, Object> resolvedDataSources = new HashMap<>();

    @Bean
    @ConfigurationProperties(
            prefix = "spring.datasource"
    )
    public DataSource dataSource() {
        logger.info("configuring datasource bean");
        resolvedDataSources = new HashMap<>();
        HashMap<String, TenantDataSource> tenantSataSourceHashMap = null;
        try {
            tenantSataSourceHashMap = (HashMap<String, TenantDataSource>) applicationContext.getBeansOfType(TenantDataSource.class);
        } catch (Exception e) {
            logger.error("cannot resolve datasources");
        }

        for (Map.Entry<String, TenantDataSource> tenantSataSource : tenantSataSourceHashMap.entrySet()){
            tenantSataSource.getValue().getDataSource();
            logger.info("successfully resolve datasource for tenant " + tenantSataSource.getValue().getTenantIdentifier());
            resolvedDataSources.put(tenantSataSource.getValue().getTenantIdentifier(), tenantSataSource.getValue().getDataSource());
        }

        MultitenantDataSource dataSource = new MultitenantDataSource();
        dataSource.setDefaultTargetDataSource(defaultDataSource());
        dataSource.setTargetDataSources(resolvedDataSources);
        dataSource.afterPropertiesSet();
        logger.info("successfully resolve datasource for all tenants");
        return dataSource;
    }

    private DataSource defaultDataSource() {
        logger.info("build default data source");
        DataSourceBuilder dataSourceBuilder = new DataSourceBuilder(this.getClass().getClassLoader())
                .driverClassName(properties.getDriverClassName())
                .url(properties.getUrl())
                .username(properties.getUsername())
                .password(properties.getPassword());
        if (properties.getType() != null) {
            dataSourceBuilder.type(properties.getType());
        }
        return dataSourceBuilder.build();
    }
}


