package com.softserve.osbb.config.multitenancy;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Created by Anastasiia Fedorak on 8/21/16.
 */
public class TenantBeenFactoryPostProcessor implements BeanFactoryPostProcessor {
private Logger logger = Logger.getLogger(TenantBeenFactoryPostProcessor.class);
        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory bf)
                throws BeansException {
            DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) bf;

                    File[] files = Paths.get("tenants").toFile().listFiles();
                    for(File propertyFile : files) {
                        logger.info("get file "+ propertyFile.getName());
                        Properties properties = new Properties();
                        try {
                            properties.load(new FileInputStream(propertyFile));
                            BeanDefinition dataSourceDefinition = BeanDefinitionBuilder
                                    .genericBeanDefinition(MyDataSource.class)
                                    .setScope(BeanDefinition.SCOPE_SINGLETON)
                                    .addConstructorArgValue(properties)
                                    .getBeanDefinition();
                            String tenantName = properties.getProperty("name");
                            beanFactory.registerBeanDefinition(tenantName, dataSourceDefinition);
                            logger.info("successfully create datasource bean for " + tenantName + " tenant");

                            BeanDefinition liquibaseDefinition = BeanDefinitionBuilder
                                    .genericBeanDefinition(MyLiquibase.class)
                                    .addConstructorArgReference(tenantName)
                                    .setScope(BeanDefinition.SCOPE_SINGLETON)
                                    .getBeanDefinition();
                            beanFactory.registerBeanDefinition(tenantName + "LiquibaseBean", liquibaseDefinition);
                            logger.info("successfully create liquibase bean for " + tenantName + " tenant");
                        } catch (IOException e) {
                            logger.error("could not load file and create beans");
                            logger.error(e.getMessage());
                        }
                    }
        };

}
