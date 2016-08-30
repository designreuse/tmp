package com.softserve.osbb.tenants.controller;

import com.softserve.osbb.common.repository.CommonOsbbsRepository;
import com.softserve.osbb.common.service.CommonOsbbsService;
import com.softserve.osbb.config.TenantContext;
import com.softserve.osbb.tenants.model.Contract;
import com.softserve.osbb.tenants.service.ContractService;
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

    @Autowired
    CommonOsbbsService osbbsService;

    @RequestMapping(value = "/{tenantId}", method = RequestMethod.POST)
    public Contract putContract(@RequestBody Contract contract, @PathVariable String tenantId) {
        TenantContext.setCurrentTenant(osbbsService.findOsbbByName(tenantId).get(0).getOsbbId());
        logger.info("current tenant is: " + TenantContext.getCurrentTenant());
        logger.info("Saving contract, sending to service");
        if (contract.getPriceCurrency() == null) contract.setPriceCurrency(Contract.DEFAULT_CURRENCY);
        logger.info("saving contract " + contract);
        return contractService.save(contract);
    }
}

