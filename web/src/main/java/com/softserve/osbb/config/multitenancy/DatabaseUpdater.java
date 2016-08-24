package com.softserve.osbb.config.multitenancy;

import com.softserve.osbb.model.common.Osbbs;
import com.softserve.osbb.service.common.CommonOsbbsService;
import com.softserve.osbb.tenant.DynamicTenantBeanCreator;
import com.softserve.osbb.tenant.TenantDatasourceProperties;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Created by Anastasiia Fedorak on 8/24/16.
 */
@Configuration
public class DatabaseUpdater implements InitializingBean {

    private static Logger logger = Logger.getLogger(DatabaseUpdater.class);

    @Autowired
    CommonOsbbsService commonOsbbsService;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    TenantDatasourceProperties tenantDatasorceProperties;

    @Override
    public void afterPropertiesSet() throws Exception {
        List<Osbbs> osbbs = commonOsbbsService.findAllOsbbs();
        for (Osbbs osbb : osbbs) {
            DynamicTenantBeanCreator.getInstance().create(osbb.getName(), applicationContext, tenantDatasorceProperties);
        }
        logger.info("successfully update from changelog");
    }
}
