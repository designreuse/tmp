package com.softserve.osbb.tenant.impl;

import com.softserve.osbb.tenant.DatabaseRuntimeCreator;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

/**
 * Created by Anastasiia Fedorak on 8/23/16.
 */
public class DatabaseRuntimeCreatorPostgresImpl implements DatabaseRuntimeCreator {

    @Value("${postgres.bin.createdb}")
    String createdb;

    @Value("${spring.datasource.username}")
    String owner;

    @Override
    public Process create(Runtime runtime, String databaseName) throws IOException {
        return runtime.exec(createdb + " " + databaseName + " --owner " + owner);
    }
}
