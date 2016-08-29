package com.softserve.osbb.config;

import liquibase.integration.spring.SpringLiquibase;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = {"com.softserve.osbb.common" },
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager")
public class CommonPersistenceConfiguration {
    private static Logger logger = Logger.getLogger(CommonPersistenceConfiguration.class);
    
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        logger.info("configuring common emf...");
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.softserve.osbb.common");
        em.setPersistenceUnitName("common_persistence_unit");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());
        logger.info("end of configuring common emf");
        return em;
    }

    @Bean
    public DataSource dataSource() {
        logger.info("configuring persistence datasource...");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/common");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");
        logger.info("end of configuring persistance datasource");
        return dataSource;
    }

    @Bean
    public SpringLiquibase liquibase(){
        logger.info("running SpringLiquibase bean for common datasource");
        SpringLiquibase springLiquibase = new SpringLiquibase();
        springLiquibase.setDataSource(dataSource());
        springLiquibase.setChangeLog("classpath:/db/changelog/db.changelog-common.json");
        return springLiquibase;
    }

    @Bean
    public PlatformTransactionManager transactionManager(
            @Qualifier("entityManagerFactory") EntityManagerFactory emf) {
        logger.info("configuring persistance tmf...");
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        logger.info("end of configuring persistance tmf");
        return transactionManager;
    }

    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
        return properties;
    }
}
