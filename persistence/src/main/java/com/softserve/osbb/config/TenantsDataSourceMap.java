package com.softserve.osbb.config;

import com.softserve.osbb.common.model.Osbbs;
import com.softserve.osbb.common.repository.CommonOsbbsRepository;
import com.softserve.osbb.tenant.TenantDatasourceProperties;
import liquibase.integration.spring.SpringLiquibase;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Properties;

@Component
public class TenantsDataSourceMap extends HashMap<Object, Object> {
    private static Logger logger = Logger.getLogger(TenantsDataSourceMap.class);

    public static final String INITIAL_DATABASE_NAME = "initial";

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    TenantDatasourceProperties defaultProperties;

    @Value("${spring.datasource.driver-class-name}")
    String driverClassName;

    @Override
    public Object get(Object key) {
        logger.info("TenantsDataSourceMap: get key");
        Object value;
        if (key == null) {
            logger.info("initializing map");
            value = setUpInitialDatasource();
        } else {
            value = super.get(key);
            logger.info("value: " + value);
        }
        if (value == null) {
            logger.info("adding new tenant to the map");
            CommonOsbbsRepository repo = applicationContext.getBean(CommonOsbbsRepository.class);
            Osbbs tenantOsbb =repo.findOne((Integer) key);
            logger.info("tenant osbb is " + tenantOsbb);
            if (tenantOsbb != null) {
                logger.info("building datasource...");
                DataSource dataSource =  DataSourceBuilder.create()
                            .driverClassName(driverClassName)
                            .username(tenantOsbb.getUsername()).password(tenantOsbb.getPassword())
                            .url(tenantOsbb.getUrl()).build();
                logger.info("success: " + dataSource);
                value = dataSource;
            }
            super.put(key, value);
        }
        return value;
    }

    private DataSource setUpInitialDatasource(){
        BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) applicationContext.getAutowireCapableBeanFactory();
        Properties properties = defaultProperties.getDefaultPropertiesByTenantName(INITIAL_DATABASE_NAME);

        DataSource dataSource = DataSourceBuilder.create().driverClassName(properties.getProperty("datasource.driver"))
                .username(properties.getProperty("datasource.username")).
                 password(properties.getProperty("datasource.password"))
                .url(properties.getProperty("datasource.url")).build();
        BeanDefinition liquibaseDefinition = BeanDefinitionBuilder
                .genericBeanDefinition(SpringLiquibase.class)
                .addPropertyValue("dataSource", dataSource)
                .addPropertyValue("changeLog","classpath:/db/changelog/db.changelog-master.json")
                .setScope(BeanDefinition.SCOPE_SINGLETON)
                .getBeanDefinition();
        beanFactory.registerBeanDefinition("initialDbLiquibaseBean", liquibaseDefinition);
        return dataSource;
    }
}
