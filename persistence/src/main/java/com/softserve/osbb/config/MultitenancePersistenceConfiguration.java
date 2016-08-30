  package com.softserve.osbb.config;

  import org.apache.log4j.Logger;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.beans.factory.annotation.Qualifier;
  import org.springframework.beans.factory.annotation.Value;
  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.Configuration;
  import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
  import org.springframework.orm.jpa.JpaTransactionManager;
  import org.springframework.orm.jpa.JpaVendorAdapter;
  import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
  import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
  import org.springframework.transaction.PlatformTransactionManager;

  import javax.persistence.EntityManagerFactory;
  import javax.sql.DataSource;
  import java.util.Properties;

  @Configuration
  @EnableJpaRepositories(basePackages={"com.softserve.osbb.tenants"},
      entityManagerFactoryRef="tenantEntityManagerFactory",
      transactionManagerRef = "tenantTransactionManager")
  public class MultitenancePersistenceConfiguration {
    private static Logger logger = Logger.getLogger(MultitenancePersistenceConfiguration.class);
      @Autowired
      private TenantsDataSourceMap tenantsDataSourceMap;

      @Value("${spring.jpa.properties.hibernate.dialect}")
      private String hibernateDialect;

      @Bean
      public LocalContainerEntityManagerFactoryBean tenantEntityManagerFactory() {
          logger.info("configuring tenant emf...");
          LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
          em.setDataSource(tenantDataSource());
          em.setPackagesToScan("com.softserve.osbb.tenants");
          em.setPersistenceUnitName("tenants_persistence_unit");

          JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
          em.setJpaVendorAdapter(vendorAdapter);
          em.setJpaProperties(additionalProperties());
          logger.info("end of configuring tenant emf");
          return em;
      }

      @Bean
      public DataSource tenantDataSource() {
          logger.info("configuring tenant datasource...");
          MultitenanceDatasource ds = new MultitenanceDatasource();
          ds.setTargetDataSources(tenantsDataSourceMap);
          logger.info("end of configuring tenant datasource");
          return ds;
      }


      @Bean
      public PlatformTransactionManager tenantTransactionManager(@Qualifier("tenantEntityManagerFactory") EntityManagerFactory emf) {
          logger.info("configuring tenant tmf...");
          JpaTransactionManager transactionManager = new JpaTransactionManager();
          transactionManager.setEntityManagerFactory(emf);
          logger.info("end of configuring tenant tmf...");
          return transactionManager;
      }

      private Properties additionalProperties() {
          Properties properties = new Properties();
          logger.info("set hibernate dialect " + hibernateDialect);
          properties.setProperty("hibernate.dialect", hibernateDialect);
          return properties;
      }

  }
