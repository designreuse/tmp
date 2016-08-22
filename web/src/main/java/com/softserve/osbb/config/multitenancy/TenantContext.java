package com.softserve.osbb.config.multitenancy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Created by Anastasiia Fedorak on 8/22/16.
 */
public class TenantContext {
    private static ThreadLocal<Object> currentTenant = new ThreadLocal<>();
    private static Logger logger = LoggerFactory.getLogger(TenantContext.class);

    public static void setCurrentTenant(Object tenant) {
        logger.info("set tenant " + tenant);
        currentTenant.set(tenant);
    }

    public static Object getCurrentTenant() {
        return currentTenant.get();
    }
}
