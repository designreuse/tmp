package com.softserve.osbb.common.service.impl;

import com.softserve.osbb.common.service.CommonOsbbsService;
import com.softserve.osbb.common.model.Osbbs;
import com.softserve.osbb.common.repository.CommonOsbbsRepository;
import com.softserve.osbb.tenant.DatabaseRuntimeCreator;
import com.softserve.osbb.config.DynamicTenantBeanCreator;
import com.softserve.osbb.tenant.TenantDatasourceProperties;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Created by Anastasiia Fedorak on 8/24/16.
 */
@Service
public class CommonOsbbsServiceImpl implements CommonOsbbsService {
    Logger logger = Logger.getLogger(CommonOsbbsServiceImpl.class);

    @Autowired
    CommonOsbbsRepository commonOsbbsRepository;

    @Autowired
    DatabaseRuntimeCreator databaseRuntimeCreator;

    @Autowired
    TenantDatasourceProperties tenantDatasourceProperties;

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public Osbbs findOneOsbbById(Integer id) {
        return commonOsbbsRepository.getOne(id);
    }

    @Override
    public List<Osbbs> findAllOsbbs() {
        return commonOsbbsRepository.findAll();
    }

    @Override
    public List<Osbbs> findOsbbByName(String name) {
        return commonOsbbsRepository.findByName(name);
    }

    @Override
    public void saveOsbb(Osbbs osbb) throws Exception {
        try {
            Process p = databaseRuntimeCreator.create(Runtime.getRuntime(), osbb.getName());
            p.waitFor();
            logger.info("Successfully create new database " +  osbb.getName());
        } catch (IOException e) {
            logger.error("Cannot create new database ");
            logger.error(e.getMessage());
        } catch (InterruptedException e) {
            logger.info(e.getMessage());
        }

        Properties prop = tenantDatasourceProperties.getDefaultPropertiesByTenantName(osbb.getName());
        osbb.setUrl(prop.getProperty("datasource.url"));
        osbb.setUsername(prop.getProperty("datasource.username"));
        osbb.setPassword(prop.getProperty("datasource.password"));

        DynamicTenantBeanCreator.create(osbb.getName(), applicationContext, tenantDatasourceProperties);
        commonOsbbsRepository.save(osbb);

    }
}
