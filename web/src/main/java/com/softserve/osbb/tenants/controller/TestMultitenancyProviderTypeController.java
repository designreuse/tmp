package com.softserve.osbb.tenants.controller;

import com.softserve.osbb.common.service.CommonOsbbsService;
import com.softserve.osbb.config.TenantContext;
import com.softserve.osbb.tenants.model.ProviderType;
import com.softserve.osbb.tenants.service.ProviderTypeService;
import com.softserve.osbb.util.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by Anastasiia Fedorak on 7/21/16.
 */
@RestController
@RequestMapping(value = "/restful/{tenantId}/providertype/")
@CrossOrigin
public class TestMultitenancyProviderTypeController {

    private static Logger logger = LoggerFactory.getLogger(TestMultitenancyProviderTypeController.class);
    @Autowired
    CommonOsbbsService osbbsService;

    @Autowired
    ProviderTypeService providerTypeService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Resource<ProviderType>> getProviderTypeById(@PathVariable("id") Integer providerTypeId, @PathVariable String tenantId) {
        TenantContext.setCurrentTenant(osbbsService.findOsbbByName(tenantId).get(0).getOsbbId());

        logger.info("fetching providerType by id: " + providerTypeId);
        ProviderType providerType = null;
        try {
            providerType = providerTypeService.findOneProviderTypeById(providerTypeId);
        } catch (Exception e) {
            logger.warn("Provider types not found");
        }
        Resource<ProviderType> providerTypeResource = null;
        try {
            providerTypeResource = addResourceLinkToProviderType(providerType, tenantId);
        } catch (EntityNotFoundException e) {
            logger.error(e.getMessage());
        }
        return new ResponseEntity<>(providerTypeResource, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Resource<ProviderType>> createProviderType(@RequestBody ProviderType providerType, @PathVariable String tenantId) {
        TenantContext.setCurrentTenant(osbbsService.findOsbbByName(tenantId).get(0).getOsbbId());
        logger.info("current tenant is: " + TenantContext.getCurrentTenant());
        Resource<ProviderType> providerTypeResource =null;
        try {
            logger.info("saving providerType object " + providerType);
            providerTypeService.saveProviderType(providerType);
            providerTypeResource = addResourceLinkToProviderType(providerType, tenantId);
        } catch (EntityNotFoundException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("Cannot save provider type " + providerType.getProviderTypeName());
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(providerTypeResource, HttpStatus.OK);
    }


    private Resource<ProviderType> addResourceLinkToProviderType(ProviderType providerType, String tenantId) throws EntityNotFoundException {
        if (providerType == null) throw new EntityNotFoundException();
        Resource<ProviderType> providerTypeResource = new Resource<>(providerType);
        providerTypeResource.add(
                linkTo(methodOn(TestMultitenancyProviderTypeController.class).getProviderTypeById(providerType.getProviderTypeId(), tenantId))
                .withSelfRel());
        return providerTypeResource;
    }

}
