package com.softserve.osbb;

import com.softserve.osbb.config.JwtFilter;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.Filter;

/**
 * Created by nazar.dovhyy on 08.07.2016.
 */

@PropertySource("classpath:/config.properties")
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        JpaRepositoriesAutoConfiguration.class,
        LiquibaseAutoConfiguration.class})
@Import({ServiceAppConfiguration.class/*,SecurityConfiguration.class*/})
@ComponentScan(basePackages = {"com.softserve.osbb"})
@EnableScheduling
public class WebAppConfiguration extends WebMvcConfigurerAdapter {
private static Logger logger = Logger.getLogger(WebAppConfiguration.class);
    public static void main(String[] args) {
//        SpringApplication.run(new Object[]{WebAppConfiguration.class}, args);
        SpringApplication.run(WebAppConfiguration.class, args);
    }

    private static final String[] STATIC_RESOURCE_LOCATIONS = {"classpath:/META-INF/resources/",
            "classpath:/resources/",
            "classpath:/static/",
            "classpath:/public/"};

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String MAPPING_PATTERN = "/**";
        if (!registry.hasMappingForPattern(MAPPING_PATTERN))
            registry.addResourceHandler(MAPPING_PATTERN)
                    .addResourceLocations(STATIC_RESOURCE_LOCATIONS);
        super.addResourceHandlers(registry);
    }

//    @Bean
//    public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
//        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
//        ppc.setLocations(new ClassPathResource("config.properties"),
//                //new FileSystemResource("/home/nataliia/myosbb1/deployment/external.properties"),
//                new FileSystemResource("/home/aska/project/tmp/myosbb/deployment/external.properties"));        //external properties
//        ppc.setIgnoreUnresolvablePlaceholders(true);
//        return ppc;
//    }

    @Bean
    public FilterRegistrationBean jwtFilter(){
        final FilterRegistrationBean registrationBean=new FilterRegistrationBean();
        registrationBean.setFilter((Filter) new JwtFilter());
        registrationBean.addUrlPatterns("/restful/*");
        return registrationBean;
    }

}
