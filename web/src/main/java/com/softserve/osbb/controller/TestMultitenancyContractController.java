package com.softserve.osbb.controller;

import com.softserve.osbb.config.multitenancy.TenantContext;
import com.softserve.osbb.model.Contract;
import com.softserve.osbb.service.ContractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

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

