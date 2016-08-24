package com.softserve.osbb.tenant.impl;

import com.softserve.osbb.tenant.TenantDatasourceProperties;
import org.springframework.beans.factory.annotation.Value;

import java.util.Properties;

/**
 * Created by Anastasiia Fedorak on 8/24/16.
 */
public class TenantDatasourcePropertiesPostgresImpl implements TenantDatasourceProperties {
    @Value("${spring.datasource.username}")
    String defaultUsername;

    @Value("${spring.datasource.password}")
    String defaultPassword;

    @Value("${spring.datasource.url}")
    String defaultUrl;

    @Override
    public Properties getDefaultPropertiesByTenantName(String tenantName) {
        Properties properties = new Properties();
        properties.setProperty("name", tenantName);
        properties.setProperty("datasource.url", createDatabaseUrl(defaultUrl,tenantName));
        properties.setProperty("datasource.username", defaultUsername);
        properties.setProperty("datasource.password", defaultPassword);
        return properties;
    }

    private String createDatabaseUrl(String defaultUrl, String tenantName){
        int index=defaultUrl.lastIndexOf('/');
        return defaultUrl.substring(0,index + 1)  + tenantName;
    }

}
