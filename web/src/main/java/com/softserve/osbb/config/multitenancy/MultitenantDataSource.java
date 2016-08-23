package com.softserve.osbb.config.multitenancy;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
/**
 * Created by Anastasiia Fedorak on 8/22/16.
 */
public class MultitenantDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {

        return TenantContext.getCurrentTenant();
    }
}
