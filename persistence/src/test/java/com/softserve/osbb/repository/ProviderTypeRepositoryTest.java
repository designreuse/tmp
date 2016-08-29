package com.softserve.osbb.repository;

import com.softserve.osbb.PersistenceAppConfiguration;
import com.softserve.osbb.tenants.model.ProviderType;
import com.softserve.osbb.tenants.repository.ProviderTypeRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Anastasiia Fedorak on 7/20/16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PersistenceAppConfiguration.class)
@Rollback
@Transactional
public class ProviderTypeRepositoryTest {
    private ProviderType providerType;

    @Before
    public void init() {
        providerType = new ProviderType("test");
    }

    @Autowired
    ProviderTypeRepository providerTypeRepository;


    @Test
    public void testSave() {
        providerTypeRepository.save(providerType);
        Assert.assertNotNull(providerTypeRepository.findOne(providerType.getProviderTypeId()));
    }

    @Test
    public void testFindProviderTypeByName(){
        testSave();
        List<ProviderType> providerTypeList = providerTypeRepository.findProviderTypesByName("Internet");
        Assert.assertNotNull(providerTypeList);
    }
}