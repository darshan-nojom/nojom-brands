package com.nojom.client.ui.clientprofile;

import static com.nojom.client.util.Constants.API_ADD_CRN;
import static com.nojom.client.util.Constants.API_ADD_JOB_POST;
import static com.nojom.client.util.Constants.API_ADD_VAT;
import static com.nojom.client.util.Constants.API_DELETE_CRN;
import static com.nojom.client.util.Constants.API_DELETE_VAT;
import static com.nojom.client.util.Constants.API_PROFILE_VERIFICATIONS;
import static com.nojom.client.util.Constants.API_SEND_EMAIL_VERIFICATION;
import static com.nojom.client.util.Constants.API_UPDATE_PROFILE;
import static com.nojom.client.util.Constants.API_UPDATE_PROFILE_PIC;
import static com.nojom.client.util.Constants.COUNTRY_CODE_VALUE;
import static com.nojom.client.util.Utils.WindowScreen.NAME;

import android.app.Application;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.nojom.client.R;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivityMyProfileBinding;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import java.io.File;
import java.util.HashMap;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MyProfileActivityVM extends AndroidViewModel implements RequestResponseListener {
    private ActivityMyProfileBinding binding;
    private BaseActivity activity;
    private MutableLiveData<Boolean> isShowProgress = new MutableLiveData<>();
    private MutableLiveData<Boolean> isDialogClose = new MutableLiveData<>();
    private MutableLiveData<Integer> isDeleteSuccess = new MutableLiveData<>();
    private MutableLiveData<Boolean> isDeleteSuccessVat = new MutableLiveData<>();
    private MutableLiveData<Boolean> isShowProgressMobile = new MutableLiveData<>();

    public MutableLiveData<Boolean> getIsShowProgress() {
        return isShowProgress;
    }

    public MutableLiveData<Boolean> getIsDialogClose() {
        return isDialogClose;
    }

    public MutableLiveData<Integer> getDeleteSuccess() {
        return isDeleteSuccess;
    }

    public MutableLiveData<Boolean> getDeleteSuccessVat() {
        return isDeleteSuccessVat;
    }

    public MutableLiveData<Boolean> getIsShowProgressMobile() {
        return isShowProgressMobile;
    }

    public MyProfileActivityVM(Application application, BaseActivity clientProfileActivity) {
        super(application);
        activity = clientProfileActivity;
        initData();
    }

    private void initData() {

    }

    public void updateProfile(String crNo, String selectedFilePath) {
        if (!activity.isNetworkConnected()) {
            activity.toastMessage(activity.getString(R.string.no_internet_connection));
            return;
        }
        getIsShowProgress().postValue(true);

        HashMap<String, String> map = new HashMap<>();
//        map.put("cr_number", crNo);
        RequestBody crNumber = RequestBody.create(crNo, MultipartBody.FORM);

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
            headers.addUnsafeNonAscii("Content-Disposition", "form-data; name=\"cr_file\"; filename=\"" + file.getName() + "\"");

            if (requestFile != null) {
                body = MultipartBody.Part.create(headers.build(), requestFile);
            }
        }

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiImageUploadRequestWithMap(this, activity, API_ADD_CRN, body, crNumber, false);
    }

    public void updateProfileVat(String crNo, String selectedFilePath) {
        if (!activity.isNetworkConnected()) {
            activity.toastMessage(activity.getString(R.string.no_internet_connection));
            return;
        }
        getIsShowProgress().postValue(true);

        HashMap<String, String> map = new HashMap<>();
//        map.put("cr_number", crNo);
        RequestBody crNumber = RequestBody.create(crNo, MultipartBody.FORM);

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
            headers.addUnsafeNonAscii("Content-Disposition", "form-data; name=\"vat_file\"; filename=\"" + file.getName() + "\"");

            if (requestFile != null) {
                body = MultipartBody.Part.create(headers.build(), requestFile);
            }
        }

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiImageUploadRequestWithMap(this, activity, API_ADD_VAT, body, crNumber, true);
    }

    public void updateProfile(Utils.WindowScreen screen, String companyName, String brandName, String fName, String lName,
                              String email, String phone, String mobilePrefix, int aboutId, String otherAbout, Integer isVerified, String uName) {
        if (!activity.isNetworkConnected()) {
            activity.toastMessage(activity.getString(R.string.no_internet_connection));
            return;
        }
        getIsShowProgress().postValue(true);

        HashMap<String, String> map = new HashMap<>();

        if (phone != null) {
            String[] split = phone.split("\\.");
            if (split.length == 2) {
                try {
                    if (split[0] != null) {
                        String code = split[0].replace(" ", "");
                        phone = split[1] != null ? split[1] : "";
                        mobilePrefix = code;
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        if (!TextUtils.isEmpty(phone) && !phone.equals("null")) {
            map.put("contactNo", phone);
        }
        if (!TextUtils.isEmpty(mobilePrefix) && !mobilePrefix.equals("null")) {
            map.put("mobile_prefix", mobilePrefix);
        }
        if (!TextUtils.isEmpty(companyName)) {
            map.put("company_name", companyName);
        }
        if (!TextUtils.isEmpty(brandName)) {
            map.put("brand_name", brandName);
        }
        if (!TextUtils.isEmpty(fName)) {
            map.put("first_name", fName);
        }
        if (!TextUtils.isEmpty(lName)) {
            map.put("last_name", lName);
        }
        if (!TextUtils.isEmpty(email)) {
            map.put("email", email);
        }
        if (!TextUtils.isEmpty(uName)) {
            map.put("username", uName);
        }
        if (aboutId != 0) {
            map.put("aboutus_id", "" + aboutId);
        }
        if (!TextUtils.isEmpty(otherAbout)) {
            map.put("other_aboutus", otherAbout);
        }
        map.put("is_verified", isVerified != null ? "" + isVerified : "0");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_UPDATE_PROFILE, true, map);
    }

    public void deleteCrn(int id) {
        if (!activity.isNetworkConnected()) {
            activity.toastMessage(activity.getString(R.string.no_internet_connection));
            return;
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("commercial_registration_id", id != 0 ? "" + id : "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_DELETE_CRN, true, map);
    }

    public void deleteVat(int id) {
        if (!activity.isNetworkConnected()) {
            activity.toastMessage(activity.getString(R.string.no_internet_connection));
            return;
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("vat_registration_id", id != 0 ? "" + id : "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_DELETE_VAT, true, map);
    }


    public void updateProfilePic(String selectedFilePath) {
        if (!activity.isNetworkConnected())
            return;


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
            headers.addUnsafeNonAscii("Content-Disposition", "form-data; name=\"profile\"; filename=\"" + file.getName() + "\"");

            if (requestFile != null) {
                body = MultipartBody.Part.create(headers.build(), requestFile);
            }
        }

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiImageUploadRequest(this, activity, API_UPDATE_PROFILE_PIC, body, null);
    }

    public void verifyEmail(String email) {
        if (!activity.isNetworkConnected())
            return;


        HashMap<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("platform", "4");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_SEND_EMAIL_VERIFICATION, true, map);
    }

    public void verifyPhoneNumber() {
        if (!activity.isNetworkConnected())
            return;

        getIsShowProgressMobile().postValue(true);

        activity.isClickableView = true;

        HashMap<String, String> map = new HashMap<>();
        map.put("type", "2");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_PROFILE_VERIFICATIONS, true, map);
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        switch (url) {
            case API_UPDATE_PROFILE:
            case API_ADD_CRN:
            case API_ADD_VAT:
            case API_UPDATE_PROFILE_PIC:
                activity.toastMessage(message);
                getIsShowProgress().postValue(false);

                activity.getProfile();

                getIsDialogClose().postValue(true);
                break;
            case API_SEND_EMAIL_VERIFICATION:
                activity.toastMessage(message);
                break;
            case API_DELETE_CRN:
                getDeleteSuccess().postValue(1);
                activity.getProfile();
                activity.toastMessage(message);
                break;
            case API_DELETE_VAT:
                getDeleteSuccessVat().postValue(true);
                activity.getProfile();
                activity.toastMessage(message);
                break;
            case API_PROFILE_VERIFICATIONS:
                activity.toastMessage(message);
                activity.getProfile();
                getIsShowProgressMobile().postValue(false);
                break;
        }
        activity.isClickableView = false;
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        switch (url) {
            case API_UPDATE_PROFILE:
            case API_UPDATE_PROFILE_PIC:
            case API_ADD_CRN:
            case API_ADD_VAT:
                activity.toastMessage(message);
                getIsShowProgress().postValue(false);
                break;
            case API_SEND_EMAIL_VERIFICATION:
            case API_DELETE_CRN:
            case API_DELETE_VAT:
                activity.toastMessage(message);
                break;
            case API_PROFILE_VERIFICATIONS:
                activity.toastMessage(message);
                getIsShowProgressMobile().postValue(false);
                break;
        }
        activity.isClickableView = false;
    }
}
