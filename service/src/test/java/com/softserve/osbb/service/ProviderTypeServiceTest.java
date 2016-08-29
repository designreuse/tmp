package com.softserve.osbb.service;

import com.softserve.osbb.ServiceAppConfiguration;
import com.softserve.osbb.tenants.model.ProviderType;
import com.softserve.osbb.tenants.repository.ProviderTypeRepository;
import com.softserve.osbb.tenants.service.impl.ProviderTypeServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.SpringApplicationConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anastasiia Fedorak on 7/21/16.
 */

@RunWith(MockitoJUnitRunner.class)
@SpringApplicationConfiguration(classes = ServiceAppConfiguration.class)
public class ProviderTypeServiceTest {

    @Mock
    private ProviderType type;

    @Mock
    private ProviderTypeRepository providerTypeRepository;

    @InjectMocks
    ProviderTypeServiceImpl providerTypeService;

    @InjectMocks
    List<ProviderType> list = new ArrayList<>();

    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);
        list.add(type);
        System.out.println(list.size());
        Mockito.when(providerTypeRepository.findProviderTypesByName(Matchers.anyString()))
                .thenReturn(list);
    }

    @Test
    public void testFindProviderTypesByName() throws Exception {
        List<ProviderType> list = providerTypeService.findProviderTypesByName("inet");
        Assert.assertEquals(list.size(),1);
    }
}
