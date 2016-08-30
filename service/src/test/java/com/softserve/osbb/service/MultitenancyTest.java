package com.softserve.osbb.service;

import com.softserve.osbb.ServiceAppConfiguration;
import com.softserve.osbb.common.model.Osbbs;
import com.softserve.osbb.common.service.CommonOsbbsService;
import com.softserve.osbb.config.TenantContext;
import com.softserve.osbb.tenants.model.Contract;
import com.softserve.osbb.tenants.service.ContractService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Anastasiia Fedorak on 8/29/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ServiceAppConfiguration.class)
@Rollback(value = true)
public class MultitenancyTest {

    private static Logger logger = LoggerFactory.getLogger(MultitenancyTest.class);

    @Autowired
    CommonOsbbsService osbbsService;

    @Autowired
    ContractService contractService;

    @Test
    @Transactional
    public void checkOutInitialMtState() {
        logger.info("----------------------------------------");
        logger.info(" BEFORE CREATING TEST DATABASE");
        logger.info("----------------------------------------");
        printInfo();
        Assert.assertNotNull(osbbsService.findOneOsbbById(1));
        Assert.assertEquals(osbbsService.findOneOsbbById(1).getName(), "initial");
    }


    @Test
    @Transactional
    public void testSavingNewTenant() throws Exception {
        checkOutInitialMtState();
        int countBeforeSaving = osbbsService.findAllOsbbs().size();
        logger.info("----------------------------------------");
        logger.info(" AFTER CREATING TEST DATABASE");
        logger.info("----------------------------------------");
        Osbbs osbb = new Osbbs("test", "jdbc:postgresql://localhost:5432/test", "postgres", "postgres");
        osbbsService.saveOsbb(osbb);
        printInfo();
        int countAfterSaving = osbbsService.findAllOsbbs().size();
        Assert.assertTrue(countAfterSaving-countBeforeSaving == 1);
    }

    @Test
    @Transactional
    public void testRouting() throws Exception {
        testSavingNewTenant();
        for (Osbbs osbbs : osbbsService.findAllOsbbs()) {
            logger.info("Database: " + osbbs.getUrl());
            TenantContext.setCurrentTenant(osbbs.getOsbbId());
            if (osbbs.getOsbbId()%2==0){
                Contract contract = new Contract();
                contract.setText("test");
                contractService.save(contract);
                Assert.assertTrue(contractService.count() > 0);
            }
            Assert.assertEquals(0, contractService.count());
        }
    }


    private void printInfo(){
        for (Osbbs osbbs : osbbsService.findAllOsbbs()) {
            logger.info("Database: " + osbbs.getUrl());
            TenantContext.setCurrentTenant(osbbs.getOsbbId());
            for (Contract contract : contractService.findAll()) {
                logger.info("" + contract);
            }
        }
    }


}
