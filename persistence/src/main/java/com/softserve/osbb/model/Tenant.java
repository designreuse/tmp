package com.softserve.osbb.model;

import org.apache.log4j.Logger;

/**
 * Created by Anastasiia Fedorak on 8/22/16.
 */
public class Tenant{
    private static Logger logger = Logger.getLogger(Tenant.class);
    private String tenantName;
    private String username;
    private String password;
    private String url;

    public Tenant() {
    }
    public Tenant(String tenantName) {
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
