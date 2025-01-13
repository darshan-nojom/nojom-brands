package com.nojom.client.api;

import com.nojom.client.model.CampListData;

import okhttp3.ResponseBody;

public interface DownloadListener {

    void successDownload(ResponseBody responseBody, String url, String message);

    void failureResponse(Throwable throwable, String url, String message);

}