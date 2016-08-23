package com.softserve.osbb.config.multitenancy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

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
