package com.nojom.client.ui.settings;

import static com.nojom.client.util.Constants.API_INVOICES_REPORT;
import static com.nojom.client.util.Constants.API_MY_INVOICES;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.CampaignListener;
import com.nojom.client.api.DownloadListener;
import com.nojom.client.model.CampList;
import com.nojom.client.model.CampListData;
import com.nojom.client.model.Invoices;
import com.nojom.client.ui.BaseActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.ResponseBody;

public class MyInvoiceActivityVM extends AndroidViewModel implements CampaignListener, DownloadListener {
    private BaseActivity activity;
    public MutableLiveData<List<CampList>> listMutableLiveData = new MutableLiveData<>();

    MyInvoiceActivityVM(Application application, BaseActivity activity) {
        super(application);
        this.activity = activity;
    }

    public void getMyInvoices(int pageNo) {
        if (!activity.isNetworkConnected()) {
            return;
        }

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.getInvoices(this, activity, API_MY_INVOICES + pageNo);
    }

    String downloadUrl;

    public void getInvoiceReport(int invoiceId, int clientId) {
        if (!activity.isNetworkConnected()) {
            return;
        }
        downloadUrl = API_INVOICES_REPORT + clientId + "?invoice_id=" + invoiceId;
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.getInvoiceReport(this, activity, downloadUrl);
    }


    @Override
    public void successResponse(CampListData responseBody, String url, String message) {
        if (url.equals(downloadUrl)) {

        } else {
            if (responseBody != null && responseBody.campaigns != null && responseBody.campaigns.size() > 0) {
                listMutableLiveData.postValue(responseBody.campaigns);
            }
        }
    }

    @Override
    public void successDownload(ResponseBody responseBody, String url, String message) {
        boolean isSaved = saveFileToStorage(responseBody, System.currentTimeMillis() + "_brand.pdf");
        if (isSaved) {
            activity.toastMessage("File downloaded successfully!");
        } else {
            activity.toastMessage("Failed to save the file.");
        }
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        if (url.equals(downloadUrl)) {
            activity.toastMessage("Failed to download.");
        }
    }

    private boolean saveFileToStorage(ResponseBody body, String fileName) {
        try {
            // Directory to save the file
            File downloadDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);

            InputStream inputStream = null;
            FileOutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(downloadDir);

                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;

                    Log.d("FileDownload", "File Downloaded: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();
                return true;
            } catch (IOException e) {
                Log.e("FileSaveError", e.getMessage());
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            Log.e("FileSaveError", e.getMessage());
            return false;
        }
    }
}
