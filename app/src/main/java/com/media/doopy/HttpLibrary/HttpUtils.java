/*
 * Copyright Lyo(c) 2017
 *  Autorized Luis Fernando Hernández Méndez
 * signature
 * b56a75f4cd38f40f3a0f4ded26e3902fe8f172594c9d9fafe889a2f4f8f5145a
 */

package com.media.doopy.HttpLibrary;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class HttpUtils {
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(url, params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(url, params, responseHandler);
    }
}
