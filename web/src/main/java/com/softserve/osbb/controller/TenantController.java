package com.softserve.osbb.controller;

import com.softserve.osbb.model.common.Osbbs;
import com.softserve.osbb.model.common.Tenant;
import com.softserve.osbb.service.common.CommonOsbbsService;
import com.softserve.osbb.tenant.DatabaseRuntimeCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by Anastasiia Fedorak on 8/22/16.
 */
@RestController
@CrossOrigin
@RequestMapping("/restful/tenant")
public class TenantController {
    private static Logger logger = LoggerFactory.getLogger(TenantController.class);

    @Autowired
    DatabaseRuntimeCreator databaseRuntimeCreator;

    @Autowired
    CommonOsbbsService osbbsService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Tenant> addTenant(@RequestBody Tenant tenant) {
        logger.info("creating new tenant");
        try {
            osbbsService.saveOsbb(new Osbbs(tenant.getTenantName()));
        } catch (Exception e) {
            logger.error("could not save osbb");
        }

        return ResponseEntity.ok(tenant);
    }
}
