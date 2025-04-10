package com.nojom.client.ui.projects;

import static com.nojom.client.util.Constants.API_CAMP_ATTACH;
import static com.nojom.client.util.Constants.API_CREATE_CAMP;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.nojom.client.R;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.model.Campaign;
import com.nojom.client.model.CampaignPay;
import com.nojom.client.ui.BaseActivity;

import java.io.File;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CampaignDataActivityVM extends AndroidViewModel implements RequestResponseListener {
    private BaseActivity activity;
    public MutableLiveData<String> mutableUploadedFileUrl = new MutableLiveData<>();
    public MutableLiveData<Integer> mutableCampId = new MutableLiveData<>();
    public MutableLiveData<Boolean> mutableProgress = new MutableLiveData<>();
    public MutableLiveData<Integer> mutableWalletSuccess = new MutableLiveData<>();
    public MutableLiveData<String> mutableIntentId = new MutableLiveData<>();

    private boolean isWallet;

    public void setWallet(boolean wallet) {
        isWallet = wallet;
    }

    public CampaignDataActivityVM(Application application, BaseActivity postJobActivity) {
        super(application);
        this.activity = postJobActivity;
        initData();
    }

    private void initData() {

    }

    public void uploadAttachment(String selectedFilePath) {
        try {
            if (!activity.isNetworkConnected()) return;
            mutableProgress.postValue(true);
            activity.isClickableView = true;

            MultipartBody.Part body = null;
            if (selectedFilePath != null) {
                File file = new File(selectedFilePath);
                Uri selectedUri = Uri.fromFile(file);
                String fileExtension = MimeTypeMap.getFileExtensionFromUrl(selectedUri.toString());
                String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase());

                RequestBody requestFile = null;
                if (mimeType != null) {
                    requestFile = RequestBody.create(file, MediaType.parse(mimeType));
                }

                Headers.Builder headers = new Headers.Builder();
                headers.addUnsafeNonAscii("Content-Disposition", "form-data; name=\"attachment\"; filename=\"" + file.getName() + "\"");

                if (requestFile != null) {
                    body = MultipartBody.Part.create(headers.build(), requestFile);
                }
            }

            ApiRequest apiRequest = new ApiRequest();
            apiRequest.uploadCampaignAttach(this, activity, API_CAMP_ATTACH, body);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createCampaign(Campaign campaign) {
        if (!activity.isNetworkConnected()) return;
        mutableProgress.postValue(true);
        activity.isClickableView = true;

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.postCampaign(this, activity, API_CREATE_CAMP, campaign);

    }

    String updateUrl;

    public void updateCampaign(Campaign campaign, int id) {
        if (!activity.isNetworkConnected()) return;
        mutableProgress.postValue(true);
        activity.isClickableView = true;
        updateUrl = API_CREATE_CAMP + "/" + id;
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.updateCampaign(this, activity, updateUrl, campaign);

    }

    String campaignId;

    public void createCampaignPayment(CampaignPay campaign, int campId) {
        if (!activity.isNetworkConnected()) return;
        mutableProgress.postValue(true);
        activity.isClickableView = true;
        campaignId = campId + "";
        ApiRequest apiRequest = new ApiRequest();
        String url = "campaign/" + campaignId + "/payment";
        apiRequest.campaignPayment(this, activity, url, campaign);

    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        activity.isClickableView = false;
        if (url.equalsIgnoreCase(API_CAMP_ATTACH)) {
            if (!TextUtils.isEmpty(responseBody)) {
                mutableUploadedFileUrl.postValue(responseBody);
            }
        } else if (url.equalsIgnoreCase(API_CREATE_CAMP)) {
//            mutableCampId.postValue(Integer.valueOf(responseBody));
            if (!isWallet) {
                String[] res = responseBody.split("-");//(intent-campaign_id)
//                Intent intent = new Intent(activity, WebViewActivity.class);
//                intent.putExtra("url", data);
//                intent.putExtra("intent", res[0]);
//                intent.putExtra("campId", res[1]);
//                activity.startActivity(intent);
                mutableIntentId.postValue(res[0]);
            } else {
                mutableWalletSuccess.postValue(1);
            }
            mutableProgress.postValue(false);
        } else if (url.equalsIgnoreCase(updateUrl)) {
//            mutableCampId.postValue(Integer.valueOf(responseBody));
            mutableProgress.postValue(false);
            activity.toastMessage(activity.getString(R.string.updated_successfully));
            activity.finish();
            activity.finishToRight();
        } else {
//            activity.toastMessage(message);
            //start webview activity
            Intent intent = new Intent(activity, WebViewActivity.class);
            intent.putExtra("url", data);
            intent.putExtra("intent", responseBody);
            intent.putExtra("campId", campaignId);
            activity.startActivity(intent);
            mutableProgress.postValue(false);
        }

    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        activity.isClickableView = false;
        activity.toastMessage(message);
        mutableProgress.postValue(false);
    }
}
