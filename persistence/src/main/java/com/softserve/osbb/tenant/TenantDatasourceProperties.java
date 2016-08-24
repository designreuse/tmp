package com.softserve.osbb.tenant;

import java.util.Properties;

/**
 * Created by Anastasiia Fedorak on 8/24/16.
 */
public interface TenantDatasourceProperties {
        Properties getDefaultPropertiesByTenantName(String tenantName);
}
