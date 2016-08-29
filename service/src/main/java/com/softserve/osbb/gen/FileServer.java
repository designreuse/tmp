package com.softserve.osbb.gen;

import com.dropbox.core.DbxException;

import java.io.IOException;

/**
 * Created by nazar.dovhyy on 09.08.2016.
 */
public interface FileServer {

    void authenticate() throws Exception;

    String uploadFile(String filePath, String type) throws IOException, DbxException;

    void downloadFile(String fileName) throws IOException, DbxException;

}
