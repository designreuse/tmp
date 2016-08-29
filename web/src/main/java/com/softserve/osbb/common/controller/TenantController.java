package com.softserve.osbb.common.controller;

import com.softserve.osbb.common.model.Osbbs;
import com.softserve.osbb.common.service.CommonOsbbsService;
import com.softserve.osbb.tenant.DatabaseRuntimeCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Osbbs> addTenant(@RequestBody Osbbs tenantOsbb) {
        logger.info("creating new tenant");
        try {
            osbbsService.saveOsbb(new Osbbs(tenantOsbb.getName()));
        } catch (Exception e) {
            logger.error("could not save osbb");
        }
        return ResponseEntity.ok(tenantOsbb);
    }
}
