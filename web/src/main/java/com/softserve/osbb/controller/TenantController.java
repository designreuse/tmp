package com.softserve.osbb.controller;

import com.softserve.osbb.config.multitenancy.DatabaseRuntimeCreator;
import com.softserve.osbb.config.multitenancy.DatabaseRuntimeCreatorPostgresImpl;
import com.softserve.osbb.config.multitenancy.MyDataSource;
import com.softserve.osbb.config.multitenancy.MyLiquibase;
import com.softserve.osbb.model.Tenant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by Anastasiia Fedorak on 8/22/16.
 */
@RestController
@CrossOrigin
@RequestMapping("/restful/tenant")
public class TenantController {
    private static Logger logger = LoggerFactory.getLogger(TenantController.class);

    @Value("${spring.datasource.username}")
    String defaultUsername;

    @Value("${spring.datasource.password}")
    String defaultPassword;

    @Value("${spring.datasource.url}")
    String defaultUrl;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    DatabaseRuntimeCreator databaseRuntimeCreator;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Tenant> addTenant(@RequestBody Tenant tenant) {
        logger.info("creating new tenant");
        if (tenant.getUsername() == null) {
            tenant.setUsername(defaultUsername);
        }
        if (tenant.getPassword() == null) {
            tenant.setPassword(defaultPassword);
        }
        if (tenant.getUrl() == null) {
            tenant.setUrl(createDatabaseUrl(defaultUrl, tenant.getTenantName()));
        }
        logger.info("Database: " + tenant.getTenantName() + " | " + tenant.getUrl());
        logger.info("User: " + tenant.getUsername());
        logger.info("Password: " + tenant.getPassword());

        try {
            Process p = databaseRuntimeCreator.create(Runtime.getRuntime(), tenant.getTenantName());
            p.waitFor();
            logger.info("Successfully create new database " + tenant.getTenantName());
        } catch (IOException e) {
            logger.error("Cannot create new database ");
            logger.error(e.getMessage());
        } catch (InterruptedException e) {
            logger.info(e.getMessage());
        }
        try {
            writePropertyFile(tenant.getTenantName(), tenant.getUrl(), tenant.getUsername(), tenant.getPassword());
            logger.info("Successfully write property file ");
        } catch (IOException e) {
            logger.error("Cannot create property file ");
            logger.error(e.getMessage());
        }
        updateDatabase(tenant);

        return ResponseEntity.ok(tenant);
    }

    private void writePropertyFile(String tenantName, String url, String username, String password) throws IOException {
        List<String> lines = Arrays.asList("name = " + tenantName,
                "datasource.url = " + url,
                "datasource.username = " + username,
                "datasource.password = " + password);
        Path file = Paths.get("tenants", tenantName + ".properties");
        Files.write(file, lines, Charset.forName("UTF-8"));
    }

    private void updateDatabase(Tenant tenant){
        BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) applicationContext.getAutowireCapableBeanFactory();
        Properties properties = new Properties();
        properties.setProperty("name", tenant.getTenantName());
        properties.setProperty("datasource.url", tenant.getUrl());
        properties.setProperty("datasource.username", tenant.getUsername());
        properties.setProperty("datasource.password", tenant.getPassword());
        BeanDefinition dataSourceDefinition = BeanDefinitionBuilder
                .genericBeanDefinition(MyDataSource.class)
                .setScope(BeanDefinition.SCOPE_SINGLETON)
                .addConstructorArgValue(properties)
                .getBeanDefinition();

        beanFactory.registerBeanDefinition(tenant.getTenantName(), dataSourceDefinition);
        logger.info("successfully create datasource bean for " + tenant.getTenantName() + " tenant");

        BeanDefinition liquibaseDefinition = BeanDefinitionBuilder
                .genericBeanDefinition(MyLiquibase.class)
                .addConstructorArgReference(tenant.getTenantName())
                .setScope(BeanDefinition.SCOPE_SINGLETON)
                .getBeanDefinition();
        beanFactory.registerBeanDefinition(tenant.getTenantName() + "LiquibaseBean", liquibaseDefinition);
        logger.info("successfully create liquibase bean for " + tenant.getTenantName() + " tenant");

        logger.info("register datasource and liquibase beans");

        applicationContext.getBean(tenant.getTenantName());
        applicationContext.getBean(tenant.getTenantName()+"LiquibaseBean");
        logger.info("wow! such a good database");
    }

    private String createDatabaseUrl(String defaultUrl, String tenantName){
        int index=defaultUrl.lastIndexOf('/');
        String newUrl = defaultUrl.substring(0,index + 1)  + tenantName;
        logger.info("create database url " + newUrl);
        return newUrl;
    }
}
