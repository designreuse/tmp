package com.softserve.osbb.controller;

import com.softserve.osbb.config.multitenancy.MyDataSource;
import com.softserve.osbb.config.multitenancy.MyLiquibase;
import com.softserve.osbb.config.multitenancy.TenantBeenFactoryPostProcessor;
import com.softserve.osbb.config.multitenancy.TenantContext;
import com.softserve.osbb.dto.ProviderPageDTO;
import com.softserve.osbb.model.Contract;
import com.softserve.osbb.model.Tenant;
import com.softserve.osbb.service.ContractService;
import com.softserve.osbb.util.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by Anastasiia Fedorak on 8/18/16.
 */
@RestController
@CrossOrigin
@RequestMapping("/restful/tenant/contract")
public class TestMultitenancyContractController {
    private static Logger logger = LoggerFactory.getLogger(TestMultitenancyContractController.class);

    @Autowired
    ContractService contractService;

    @RequestMapping(value = "/{tenantId}", method = RequestMethod.POST)
    public Contract putContract(@RequestBody Contract contract, @PathVariable String tenantId) {
        TenantContext.setCurrentTenant(tenantId);
        logger.info("current tenant is: " + TenantContext.getCurrentTenant());
        logger.info("Saving contract, sending to service");
        if (contract.getPriceCurrency() == null) contract.setPriceCurrency(Contract.DEFAULT_CURRENCY);
        return contractService.save(contract);
    }
}

