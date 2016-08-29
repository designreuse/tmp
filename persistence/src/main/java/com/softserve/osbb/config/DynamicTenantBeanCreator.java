package com.softserve.osbb.config;

import com.softserve.osbb.tenant.TenantDatasourceProperties;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by Anastasiia Fedorak on 8/24/16.
 */
public class DynamicTenantBeanCreator {
    private static Logger logger = Logger.getLogger(DynamicTenantBeanCreator.class);

    public static void create(String tenantName, ApplicationContext applicationContext, TenantDatasourceProperties tenantDatasourceProperties){

        BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) applicationContext.getAutowireCapableBeanFactory();

        Properties properties = tenantDatasourceProperties.getDefaultPropertiesByTenantName(tenantName);

        DataSource dataSource=
        DataSourceBuilder.create().url(properties.getProperty("datasource.url"))
                .username(properties.getProperty("datasource.username"))
                .password(properties.getProperty("datasource.password")).build();

        BeanDefinition liquibaseDefinition = BeanDefinitionBuilder
                .genericBeanDefinition(SpringLiquibase.class)
                .setScope(BeanDefinition.SCOPE_SINGLETON)
                .addPropertyValue("dataSource", dataSource)
                .addPropertyValue("changeLog", "classpath:/db/changelog/db.changelog-master.json")
                .getBeanDefinition();

        beanFactory.registerBeanDefinition(tenantName + "Liquibase", liquibaseDefinition);
        logger.info("successfully create liquibase bean for " + tenantName + " tenant");
        try {
            applicationContext.getBean(tenantName+"Liquibase", SpringLiquibase.class).afterPropertiesSet();
            logger.info("successfully update database " + tenantName);
        } catch (LiquibaseException e) {
            logger.error("could not update database " + tenantName + " with liquibase schema");
            logger.error(e.getMessage());
        }
    }
}
