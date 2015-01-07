package com.google.gwt.core.client.impl;

import com.google.gwt.core.client.impl.AsyncFragmentLoader.HttpDownloadFailure;

public class AsyncDownloadHelper {

    public static int getDownloadStatusCode(Throwable caught) {
        if (caught instanceof HttpDownloadFailure) {
            HttpDownloadFailure f = (HttpDownloadFailure) caught;
            return f.getStatusCode();
        }
        return -1;
    }
}
