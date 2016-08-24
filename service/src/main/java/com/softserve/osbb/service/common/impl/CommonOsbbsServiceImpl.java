package com.softserve.osbb.service.common.impl;

import com.softserve.osbb.model.common.Osbbs;
import com.softserve.osbb.repository.common.CommonOsbbsRepository;
import com.softserve.osbb.service.common.CommonOsbbsService;
import com.softserve.osbb.tenant.DatabaseRuntimeCreator;
import com.softserve.osbb.tenant.DynamicTenantBeanCreator;
import com.softserve.osbb.tenant.TenantDatasourceProperties;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

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

        DynamicTenantBeanCreator.getInstance().create(osbb.getName(), applicationContext, tenantDatasourceProperties);
        commonOsbbsRepository.save(osbb);

    }
}
