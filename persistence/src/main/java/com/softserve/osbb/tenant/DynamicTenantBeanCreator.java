package com.softserve.osbb.tenant;

import com.softserve.osbb.model.common.TenantDataSource;
import com.softserve.osbb.model.common.TenantLiquibase;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;

import java.util.Properties;

/**
 * Created by Anastasiia Fedorak on 8/24/16.
 */
public class DynamicTenantBeanCreator {
    private static Logger logger = Logger.getLogger(DynamicTenantBeanCreator.class);
    private static DynamicTenantBeanCreator instance = new DynamicTenantBeanCreator();

    private DynamicTenantBeanCreator() {
    }

    public static DynamicTenantBeanCreator getInstance() {
        return instance;
    }


    public void create(String tenantName, ApplicationContext applicationContext, TenantDatasourceProperties tenantDatasourceProperties){

        BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) applicationContext.getAutowireCapableBeanFactory();

        Properties properties = tenantDatasourceProperties.getDefaultPropertiesByTenantName(tenantName);
        BeanDefinition dataSourceDefinition = BeanDefinitionBuilder
                .genericBeanDefinition(TenantDataSource.class)
                .setScope(BeanDefinition.SCOPE_SINGLETON)
                .addConstructorArgValue(properties)
                .getBeanDefinition();

        beanFactory.registerBeanDefinition(tenantName, dataSourceDefinition);
        logger.info("successfully create datasource bean for " + tenantName + " tenant");

        BeanDefinition liquibaseDefinition = BeanDefinitionBuilder
                .genericBeanDefinition(TenantLiquibase.class)
                .addConstructorArgReference(tenantName)
                .setScope(BeanDefinition.SCOPE_SINGLETON)
                .getBeanDefinition();
        beanFactory.registerBeanDefinition(tenantName + "LiquibaseBean", liquibaseDefinition);
        logger.info("successfully create liquibase bean for " + tenantName + " tenant");

        logger.info("register datasource and liquibase beans");

        applicationContext.getBean(tenantName);
        applicationContext.getBean(tenantName+"LiquibaseBean");
        logger.info("successfully update database " + tenantName);
    }
}
