package com.softserve.osbb.tenant;

import org.springframework.context.annotation.Bean;

import java.io.IOException;

/**
 * Created by Anastasiia Fedorak on 8/23/16.
 */
public interface DatabaseRuntimeCreator {
    Process create(Runtime runtime, String dbName) throws IOException;
}
