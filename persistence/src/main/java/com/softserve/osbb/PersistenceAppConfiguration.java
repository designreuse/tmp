package com.softserve.osbb;

import com.softserve.osbb.tenant.DatabaseRuntimeCreator;
import com.softserve.osbb.tenant.TenantDatasourceProperties;
import com.softserve.osbb.tenant.impl.DatabaseRuntimeCreatorPostgresImpl;
import com.softserve.osbb.tenant.impl.TenantDatasourcePropertiesPostgresImpl;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {
		DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class,
		HibernateJpaAutoConfiguration.class,
		JpaRepositoriesAutoConfiguration.class,
		LiquibaseAutoConfiguration.class})
@ComponentScan(basePackages = "com.softserve.osbb")
public class PersistenceAppConfiguration {

	public static void main(String[] args)
	{
		SpringApplication.run(PersistenceAppConfiguration.class, args);

	}
	@Bean
	public DatabaseRuntimeCreator databaseRuntimeCreator(){
		return new DatabaseRuntimeCreatorPostgresImpl();
	}

	@Bean
	public TenantDatasourceProperties tenantDatasourceProperties(){
		return new TenantDatasourcePropertiesPostgresImpl();
	}

	@Bean
	public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
		PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
		ppc.setLocations(//new ClassPathResource("config.properties"),
				//new FileSystemResource("/home/nataliia/myosbb1/deployment/external.properties"),
				new FileSystemResource("/home/aska/project/tmp/myosbb/deployment/external.properties"));        //external properties
		ppc.setIgnoreUnresolvablePlaceholders(true);
		return ppc;
	}

}
