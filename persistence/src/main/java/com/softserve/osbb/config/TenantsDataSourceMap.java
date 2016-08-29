package com.softserve.osbb.config;


import com.softserve.osbb.common.model.Osbbs;
import com.softserve.osbb.common.repository.CommonOsbbsRepository;
import liquibase.integration.spring.SpringLiquibase;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;

@Component
public class TenantsDataSourceMap extends HashMap<Object, Object> {
    private static Logger logger = Logger.getLogger(TenantsDataSourceMap.class);
    
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public Object get(Object key) {
        logger.info("LocationDataSourceMap: get key");
        Object value;
        if (key == null) {
            logger.info("key is null");
            BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) applicationContext.getAutowireCapableBeanFactory();

            DataSource dataSource = DataSourceBuilder.create().driverClassName("org.postgresql.Driver")
                    .username("postgres").password("postgres")
                    .url("jdbc:postgresql://localhost:5432/initial").build();
            BeanDefinition liquibaseDefinition = BeanDefinitionBuilder
                    .genericBeanDefinition(SpringLiquibase.class)
                    .addPropertyValue("dataSource", dataSource)
                    .addPropertyValue("changeLog","classpath:/db/changelog/db.changelog-master.json")
                    .setScope(BeanDefinition.SCOPE_SINGLETON)
                    .getBeanDefinition();
            beanFactory.registerBeanDefinition("testLiquibaseBean", liquibaseDefinition);
            key = 1;
            value = dataSource;
            logger.info("now key=1");
        } else {
            value = super.get(key);
        }
        logger.info("value is: " + value);
        if (value == null) {
            logger.info("app ctx is " + applicationContext);
            CommonOsbbsRepository repo = applicationContext.getBean(CommonOsbbsRepository.class);
            Osbbs tenantOsbb =repo.findOne((Integer) key);
            logger.info("tenant osbb is " + tenantOsbb);
            if (tenantOsbb != null) {
                logger.info("building datasource...");
                DataSource dataSource =  DataSourceBuilder.create()
                        .driverClassName("org.postgresql.Driver")
                        .username(tenantOsbb.getUsername()).password(tenantOsbb.getPassword())
                        .url(tenantOsbb.getUrl()).build();
                logger.info("success: " + dataSource);
                value = dataSource;
                super.put(key, value);
            }
        }
        return value;
    }
}
