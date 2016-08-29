package com.softserve.osbb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(PersistenceAppConfiguration.class)
@ComponentScan(basePackages = {"com.softserve.osbb"})
public class ServiceAppConfiguration {
    public static void main(String[] args) {
        SpringApplication.run(ServiceAppConfiguration.class, args);
    }

}