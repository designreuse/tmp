package com.softserve.osbb.exe;

/**
 * Created by nazar.dovhyy on 13.07.2016.
 */

public class NotFoundEntityException extends Exception {
    public NotFoundEntityException(String errMsg) {
        super(errMsg);
    }
}
