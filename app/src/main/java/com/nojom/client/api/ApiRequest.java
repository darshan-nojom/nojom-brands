package com.nojom.client.api;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.annotations.SerializedName;
import com.nojom.client.R;
import com.nojom.client.model.AgentServiceResponse;
import com.nojom.client.model.CampAttachResponse;
import com.nojom.client.model.CampListResponse;
import com.nojom.client.model.Campaign;
import com.nojom.client.model.CampaignPay;
import com.nojom.client.model.CampaignRelease;
import com.nojom.client.model.CampaignType;
import com.nojom.client.model.ChargeAmount;
import com.nojom.client.model.CommonResponse;
import com.nojom.client.model.ContactCheck;
import com.nojom.client.model.InvoiceListResponse;
import com.nojom.client.model.SendCode;
import com.nojom.client.model.ServicesData;
import com.nojom.client.model.SimpleResponse;
import com.nojom.client.model.UpdateNoti;
import com.nojom.client.model.VerifyCode;
import com.nojom.client.model.WalletResponse;
import com.nojom.client.model.WalletTxnResponse;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiRequest implements Constants {

    public void apiRequest(RequestResponseListener requestResponseListener, BaseActivity activity, String url, boolean isPostMethod, Map<String, String> map) {

        Call<CommonResponse> orderCall;
        String jwtToken = null;
        if (activity.getJWT() != null && !TextUtils.isEmpty(activity.getJWT())) {
            jwtToken = activity.getJWT();
        }

        if (isPostMethod) {
            orderCall = activity.getService().requestAPIPOST(url, (HashMap<String, String>) map, jwtToken, "6");
        } else {
            orderCall = activity.getService().requestAPIGET(url, jwtToken, "6");
        }

        orderCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                if (response.code() == 401) {
                    activity.onLogout(activity);
                } else {
                    onResponseAPI(activity, url, response, requestResponseListener);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                onFailureAPI(activity, requestResponseListener, t, url);
            }
        });
    }

    public void apiRequest(RequestResponseListener requestResponseListener, BaseActivity activity, String url, boolean isPostMethod, Map<String, String> map, JSONArray mPrice, JSONArray arrFollow, JSONArray arrAge) {

        Call<CommonResponse> orderCall;
        String jwtToken = null;
        if (activity.getJWT() != null && !TextUtils.isEmpty(activity.getJWT())) {
            jwtToken = activity.getJWT();
        }

        if (isPostMethod) {
            orderCall = activity.getService().requestAPIPOST(url, (HashMap<String, String>) map, jwtToken, "6", mPrice, arrFollow, arrAge);
        } else {
            orderCall = activity.getService().requestAPIGET(url, jwtToken, "6");
        }

        orderCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                if (response.code() == 401) {
                    activity.onLogout(activity);
                } else {
                    onResponseAPI(activity, url, response, requestResponseListener);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                onFailureAPI(activity, requestResponseListener, t, url);
            }
        });
    }

    public void apiImageUploadRequestWithMap(RequestResponseListener requestResponseListener, BaseActivity activity, String url, MultipartBody.Part fileBody, RequestBody map, boolean isVat) {
        String jwtToken = null;
        if (activity.getJWT() != null && !TextUtils.isEmpty(activity.getJWT())) {
            jwtToken = activity.getJWT();
        }

        Call<CommonResponse> orderCall = isVat ? activity.getService().uploadFileWithMapVat(url, fileBody, jwtToken, "6", map) : activity.getService().uploadFileWithMap(url, fileBody, jwtToken, "6", map);
        orderCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                if (response.code() == 401) {
                    activity.onLogout(activity);
                } else {
                    onResponseAPI(activity, url, response, requestResponseListener);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                onFailureAPI(activity, requestResponseListener, t, url);
            }
        });
    }

    public void apiImageUploadRequest(RequestResponseListener requestResponseListener, BaseActivity activity, String url, MultipartBody.Part fileBody, HashMap<String, String> map) {
        String jwtToken = null;
        if (activity.getJWT() != null && !TextUtils.isEmpty(activity.getJWT())) {
            jwtToken = activity.getJWT();
        }
        Call<CommonResponse> orderCall = activity.getService().uploadFile(url, fileBody, jwtToken, "6");
        orderCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                if (response.code() == 401) {
                    activity.onLogout(activity);
                } else {
                    onResponseAPI(activity, url, response, requestResponseListener);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                onFailureAPI(activity, requestResponseListener, t, url);
            }
        });
    }

    public void apiRequestSocialInfluenceFilter(RequestResponseListener requestResponseListener, BaseActivity activity, String url, Map<String, RequestBody> map) {
        Call<CommonResponse> orderCall;

        String jwtToken = null;
        if (activity.getJWT() != null && !TextUtils.isEmpty(activity.getJWT())) {
            jwtToken = activity.getJWT();
        }

        orderCall = activity.getService().getSocialInfluenceProfiles(jwtToken, url, (HashMap<String, RequestBody>) map, "6");

        orderCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                onResponseAPI(activity, url, response, requestResponseListener);
            }

            @Override
            public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                onFailureAPI(activity, requestResponseListener, t, url);
            }
        });
    }


    public void apiImageUploadRequestBody(RequestResponseListener requestResponseListener, BaseActivity activity, String url, MultipartBody.Part[] fileBody, HashMap<String, RequestBody> map) {
        String jwtToken = null;
        if (activity.getJWT() != null && !TextUtils.isEmpty(activity.getJWT())) {
            jwtToken = activity.getJWT();
        }
        Call<CommonResponse> orderCall = activity.getService().uploadFileBody(url, fileBody, map, jwtToken, "6");
        orderCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                if (response.code() == 401) {
                    activity.onLogout(activity);
                } else {
                    onResponseAPI(activity, url, response, requestResponseListener);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                onFailureAPI(activity, requestResponseListener, t, url);
            }
        });
    }

    public void apiImageUploadRequestBody(RequestResponseListener requestResponseListener, BaseActivity activity, String url, MultipartBody.Part[] fileBody, HashMap<String, RequestBody> map, Double buget) {
        String jwtToken = null;
        if (activity.getJWT() != null && !TextUtils.isEmpty(activity.getJWT())) {
            jwtToken = activity.getJWT();
        }
        Call<CommonResponse> orderCall = activity.getService().uploadFileBody(url, fileBody, map, jwtToken, "6", buget);
        orderCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                if (response.code() == 401) {
                    activity.onLogout(activity);
                } else {
                    onResponseAPI(activity, url, response, requestResponseListener);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                onFailureAPI(activity, requestResponseListener, t, url);
            }
        });
    }


    public void apiImageUploadRequest(RequestResponseListener requestResponseListener, BaseActivity activity, String url, MultipartBody.Part[] fileBody, HashMap<String, String> map) {
        String jwtToken = null;
        if (activity.getJWT() != null && !TextUtils.isEmpty(activity.getJWT())) {
            jwtToken = activity.getJWT();
        }
        Call<CommonResponse> orderCall = activity.getService().uploadFile(url, fileBody, map, jwtToken, "6");
        orderCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                if (response.code() == 401) {
                    activity.onLogout(activity);
                } else {
                    onResponseAPI(activity, url, response, requestResponseListener);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                onFailureAPI(activity, requestResponseListener, t, url);
            }
        });
    }

    public void apiBankTransfer(RequestResponseListener requestResponseListener, BaseActivity activity, String url, MultipartBody.Part fileBody, Integer jId, Integer jpbid, Double redim, Double amnt, Double depFee, String pCode, Double payAmnt, String desc, Integer thr, Integer six, String sendName, Integer bId, String cardNo, String txnDate, String refNo, String not) {
        String jwtToken = null;
        if (activity.getJWT() != null && !TextUtils.isEmpty(activity.getJWT())) {
            jwtToken = activity.getJWT();
        }
        Call<CommonResponse> orderCall = activity.getService().bankTransfer(url, fileBody, jwtToken, six, jId, jpbid, redim, amnt, depFee, 11, pCode, payAmnt, desc, thr, sendName, bId, cardNo, txnDate, refNo, not, 6);
        orderCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                if (response.code() == 401) {
                    activity.onLogout(activity);
                } else {
                    onResponseAPI(activity, url, response, requestResponseListener);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                onFailureAPI(activity, requestResponseListener, t, url);
            }
        });
    }

    public class MyRequestItem {
        @SerializedName("gig_requirment_id")
        private int gigRequirementId;
        @SerializedName("customPackageID")
        private int customPackageId;

        // Constructor, getters, and setters
    }

    public void apiBankTransferGig(RequestResponseListener requestResponseListener, BaseActivity activity, String url, MultipartBody.Part fileBody, Integer gigId, Integer agentId, Integer deadline, Integer payTypeId, Integer payPlatId, String desc, String req, String disCode, Double payAmount, Double totPrice, String sendName, Integer bId, String cardNo, String txnDate, String refNo, String not, Integer offerId, String pk, String sk) {
        String jwtToken = null;
        if (activity.getJWT() != null && !TextUtils.isEmpty(activity.getJWT())) {
            jwtToken = activity.getJWT();
        }

//        Type listType = new TypeToken<List<MyRequestItem>>(){}.getType();
//        ArrayList<MyRequestItem> requestItems = new Gson().fromJson(req, listType);

        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        RequestBody requestBody = RequestBody.create(req, MediaType.parse("text/plain"));
        requestBodyMap.put("requirments", requestBody);

        Call<CommonResponse> orderCall = activity.getService().bankTransferGig(url, fileBody, jwtToken, 6, 6, gigId, agentId, deadline, payTypeId, payPlatId, desc, requestBodyMap, disCode, payAmount, totPrice, sendName, bId, cardNo, txnDate, refNo, not);
        orderCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                if (response.code() == 401) {
                    activity.onLogout(activity);
                } else {
                    onResponseAPI(activity, url, response, requestResponseListener);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                onFailureAPI(activity, requestResponseListener, t, url);
            }
        });
    }

    private void onResponseAPI(BaseActivity activity, String url, Response<CommonResponse> response, RequestResponseListener requestResponseListener) {
        try {
            if (response.body() != null) {
                CommonResponse commonResponse = response.body();
                if (commonResponse.status) {
                    if (!TextUtils.isEmpty(commonResponse.data)) {
                        if (url.equalsIgnoreCase(API_LOGIN) || url.equalsIgnoreCase(API_REGISTER))
                            Preferences.writeString(activity, Constants.JWT, commonResponse.data);

                        requestResponseListener.successResponse(Utils.decode(commonResponse.data), url, commonResponse.getMessage(activity), commonResponse.data);
                    } else {
                        requestResponseListener.successResponse("", url, commonResponse.getMessage(activity), "");
                    }
                } else {
                    try {
                        activity.runOnUiThread(() -> {
                            switch (url) {
                                case "getJobPosts":
                                case "getGigListsV2":
                                case "getJobPostsV2":
                                case "getPortfolio":
                                case "getProfileByCategory":
                                    break;
                                case "checkPromocode":
                                    if (!commonResponse.getMessage(activity).contains("Empty")) {
                                        activity.toastMessage(commonResponse.getMessage(activity));
                                    }
                                    break;
                            }
                        });
                        requestResponseListener.failureResponse(null, url, commonResponse.getMessage(activity));
                    } catch (Exception e) {
                        requestResponseListener.failureResponse(null, url, commonResponse.getMessage(activity));
                    }

                }
            } else {
                activity.failureError(activity.getString(R.string.something_went_wrong));
            }
        } catch (Exception e) {
            requestResponseListener.failureResponse(null, url, "");
        }
    }

    private void onFailureAPI(BaseActivity activity, RequestResponseListener requestResponseListener, Throwable t, String url) {
        activity.failureError(activity.getString(R.string.something_went_wrong));
        requestResponseListener.failureResponse(t, url, "");
    }


    public void apiRequest(RequestResponseListener requestResponseListener, BaseActivity activity, String url) {

        Call<SimpleResponse> orderCall;
        String jwtToken = null;
        if (activity.getJWT() != null && !TextUtils.isEmpty(activity.getJWT())) {
            jwtToken = activity.getJWT();
        }

        orderCall = activity.getService().simpleAPIGET(url, jwtToken, "6");

        orderCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<SimpleResponse> call, @NonNull Response<SimpleResponse> response) {
                if (response.code() == 401) {
                    activity.onLogout(activity);
                } else {
                    onSimpleResponseAPI(activity, url, response, requestResponseListener);
                }
            }

            @Override
            public void onFailure(@NonNull Call<SimpleResponse> call, @NonNull Throwable t) {
                onFailureAPI(activity, requestResponseListener, t, url);
            }
        });
    }

    private void onSimpleResponseAPI(BaseActivity activity, String url, Response<SimpleResponse> response, RequestResponseListener requestResponseListener) {
        try {
            if (response.body() != null) {
                SimpleResponse commonResponse = response.body();
                if (commonResponse.status) {
                    if (commonResponse.data != null) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        try {
                            String jsonArray = objectMapper.writeValueAsString(commonResponse.data);
                            System.out.println(jsonArray);
                            requestResponseListener.successResponse(jsonArray, url, commonResponse.getMessage(activity), "");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        requestResponseListener.successResponse("", url, commonResponse.getMessage(activity), "");
                    }
                } else {
                    try {
                        activity.runOnUiThread(() -> {
                            switch (url) {
                                case "getJobPosts":
                                case "getGigListsV2":
                                case "getJobPostsV2":
                                case "getPortfolio":
                                case "getProfileByCategory":
                                    break;
                                case "checkPromocode":
                                    if (!commonResponse.getMessage(activity).contains("Empty")) {
                                        activity.toastMessage(commonResponse.getMessage(activity));
                                    }
                                    break;
                            }
                        });
                        requestResponseListener.failureResponse(null, url, commonResponse.getMessage(activity));
                    } catch (Exception e) {
                        requestResponseListener.failureResponse(null, url, commonResponse.getMessage(activity));
                    }

                }
            } else {
                activity.failureError(activity.getString(R.string.something_went_wrong));
            }
        } catch (Exception e) {
            requestResponseListener.failureResponse(null, url, "");
        }
    }

    public void getServices(BaseActivity activity, String url, RequestResponseListener apiRequestListener) {

        String jwtToken = activity.getJWT();

        Call<ServicesData> call;
        call = activity.getService().getServices(url, jwtToken, "6");


        call.enqueue(new Callback<ServicesData>() {
            @Override
            public void onResponse(@NonNull Call<ServicesData> call, @NonNull Response<ServicesData> response) {

                if (response.isSuccessful()) {
                    ServicesData apiResponse = response.body();
                    if (apiResponse != null) {
                        if (apiResponse.status) {
                            if (apiRequestListener != null) {
                                ObjectMapper objectMapper = new ObjectMapper();
                                try {
                                    String jsonArray = objectMapper.writeValueAsString(apiResponse.data);
                                    System.out.println(jsonArray);
                                    apiRequestListener.successResponse(jsonArray, url, apiResponse.getMessage(activity), "");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            if (apiRequestListener != null) {
                                apiRequestListener.failureResponse(null, url, apiResponse.getMessage(activity));

                            }
                        }
                    }
                } else if (response.code() == 401) {
                    activity.onLogout(activity);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServicesData> call, @NonNull Throwable t) {
                if (apiRequestListener != null) {
                    apiRequestListener.failureResponse(t, url, "");
                }
            }
        });
    }

    public void uploadCampaignAttach(RequestResponseListener requestResponseListener, BaseActivity activity, String url, MultipartBody.Part fileBody) {
        String jwtToken = null;
        if (activity.getJWT() != null && !TextUtils.isEmpty(activity.getJWT())) {
            jwtToken = activity.getJWT();
        }

        Call<CampAttachResponse> orderCall = activity.getService().uploadCampAttach(url, fileBody, jwtToken, "6");
        orderCall.enqueue(new Callback<CampAttachResponse>() {
            @Override
            public void onResponse(@NonNull Call<CampAttachResponse> call, @NonNull Response<CampAttachResponse> response) {
                if (response.code() == 401) {
                    activity.onLogout(activity);
                } else {
                    onCampResponseAPI(activity, url, response, requestResponseListener);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CampAttachResponse> call, @NonNull Throwable t) {
                onFailureAPI(activity, requestResponseListener, t, url);
            }
        });
    }

    public void postCampaign(RequestResponseListener requestResponseListener, BaseActivity activity, String url, Campaign body) {
        String jwtToken = null;
        if (activity.getJWT() != null && !TextUtils.isEmpty(activity.getJWT())) {
            jwtToken = activity.getJWT();
        }

        Call<CampAttachResponse> orderCall = activity.getService().postCampaign(url, jwtToken, body, "6");
        orderCall.enqueue(new Callback<CampAttachResponse>() {
            @Override
            public void onResponse(@NonNull Call<CampAttachResponse> call, @NonNull Response<CampAttachResponse> response) {
                if (response.code() == 401) {
                    activity.onLogout(activity);
                } else if (response.code() == 402) {
//                    requestResponseListener.successResponse("", url, "", "");

                    try {
                        String errorBodyString = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorBodyString);
                        String message = jsonObject.getString("message");
                        if (!TextUtils.isEmpty(message)) {
                            requestResponseListener.failureResponse(null, url, message);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    onCampResponseAPI(activity, url, response, requestResponseListener);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CampAttachResponse> call, @NonNull Throwable t) {
                onFailureAPI(activity, requestResponseListener, t, url);
            }
        });
    }

    public void campaignPayment(RequestResponseListener requestResponseListener, BaseActivity activity, String url, CampaignPay body) {
        String jwtToken = null;
        if (activity.getJWT() != null && !TextUtils.isEmpty(activity.getJWT())) {
            jwtToken = activity.getJWT();
        }

        Call<CampAttachResponse> orderCall = activity.getService().campaignPayment(url, jwtToken, body, "6");
        orderCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<CampAttachResponse> call, @NonNull Response<CampAttachResponse> response) {
                if (response.code() == 401) {
                    activity.onLogout(activity);
                } else {
                    onCampResponseAPI(activity, url, response, requestResponseListener);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CampAttachResponse> call, @NonNull Throwable t) {
                onFailureAPI(activity, requestResponseListener, t, url);
            }
        });
    }

    private void onCampResponseAPI(BaseActivity activity, String url, Response<CampAttachResponse> response, RequestResponseListener requestResponseListener) {
        try {
            if (response.body() != null) {
                CampAttachResponse commonResponse = response.body();
                if (commonResponse.status) {
                    if (commonResponse.data != null) {
                        if (commonResponse.data.url != null && !TextUtils.isEmpty(commonResponse.data.url)) {
                            requestResponseListener.successResponse(commonResponse.data.url, url, commonResponse.getMessage(activity), null);
                        }/* else if (commonResponse.data.embed_url != null) {
                            requestResponseListener.successResponse(commonResponse.data.embed_url + "", url, commonResponse.getMessage(activity), null);
                        }*/ else if (!TextUtils.isEmpty(commonResponse.data.intent_id) && !TextUtils.isEmpty(commonResponse.data.embed_url)) {
                            requestResponseListener.successResponse(commonResponse.data.intent_id + "-" + commonResponse.data.campaign_id, url, commonResponse.getMessage(activity), commonResponse.data.embed_url);
                        } else {
                            requestResponseListener.successResponse("", url, "", "");
                        }

                    } else {
                        requestResponseListener.successResponse("", url, commonResponse.getMessage(activity), "");
                    }
                } else {
                    try {
                        requestResponseListener.failureResponse(null, url, commonResponse.getMessage(activity));
                    } catch (Exception e) {
                        requestResponseListener.failureResponse(null, url, "");
                    }

                }
            } else {
                activity.failureError(activity.getString(R.string.something_went_wrong));
            }
        } catch (Exception e) {
            requestResponseListener.failureResponse(null, url, "");
        }
    }


    public void fetchCampaign(CampaignListener requestResponseListener, BaseActivity activity, String url, CampaignType body) {
        String jwtToken = null;
        if (activity.getJWT() != null && !TextUtils.isEmpty(activity.getJWT())) {
            jwtToken = activity.getJWT();
        }

        Call<CampListResponse> orderCall = activity.getService().campaignList(url, jwtToken, body, "6");
        orderCall.enqueue(new Callback<CampListResponse>() {
            @Override
            public void onResponse(@NonNull Call<CampListResponse> call, @NonNull Response<CampListResponse> response) {
                if (response.code() == 401) {
                    activity.onLogout(activity);
                } else {
                    try {
                        if (response.body() != null) {
                            CampListResponse commonResponse = response.body();
                            if (commonResponse.status) {
                                if (commonResponse.data != null) {
                                    requestResponseListener.successResponse(commonResponse.data, url, commonResponse.getMessage(activity));
                                } else {
                                    requestResponseListener.successResponse(null, url, commonResponse.getMessage(activity));
                                }
                            } else {
                                try {
                                    activity.runOnUiThread(() -> {
                                        switch (url) {
                                            case "getJobPosts":
                                            case "getGigListsV2":
                                            case "getJobPostsV2":
                                            case "getPortfolio":
                                            case "getProfileByCategory":
                                                break;
                                            case "checkPromocode":
                                                if (!commonResponse.getMessage(activity).contains("Empty")) {
                                                    activity.toastMessage(commonResponse.getMessage(activity));
                                                }
                                                break;
                                        }
                                    });
                                    requestResponseListener.failureResponse(null, url, commonResponse.getMessage(activity));
                                } catch (Exception e) {
                                    requestResponseListener.failureResponse(null, url, commonResponse.getMessage(activity));
                                }

                            }
                        } else {
                            activity.failureError(activity.getString(R.string.something_went_wrong));
                        }
                    } catch (Exception e) {
                        requestResponseListener.failureResponse(null, url, "");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CampListResponse> call, @NonNull Throwable t) {
                activity.failureError(activity.getString(R.string.something_went_wrong));
                requestResponseListener.failureResponse(t, url, "");
            }
        });
    }

    public void getInvoices(CampaignListener requestResponseListener, BaseActivity activity, String url) {
        String jwtToken = null;
        if (activity.getJWT() != null && !TextUtils.isEmpty(activity.getJWT())) {
            jwtToken = activity.getJWT();
        }

        Call<InvoiceListResponse> orderCall = activity.getService().getInvoices(url, jwtToken, "6");
        orderCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<InvoiceListResponse> call, @NonNull Response<InvoiceListResponse> response) {
                if (response.code() == 401) {
                    activity.onLogout(activity);
                } else {
                    try {
                        if (response.body() != null) {
                            InvoiceListResponse commonResponse = response.body();
                            if (commonResponse.status) {
                                if (commonResponse.data != null) {
                                    requestResponseListener.successResponse(commonResponse.data, url, commonResponse.getMessage(activity));
                                } else {
                                    requestResponseListener.successResponse(null, url, commonResponse.getMessage(activity));
                                }
                            } else {
                                try {
                                    activity.runOnUiThread(() -> {
                                        switch (url) {
                                            case "getJobPosts":
                                            case "getGigListsV2":
                                            case "getJobPostsV2":
                                            case "getPortfolio":
                                            case "getProfileByCategory":
                                                break;
                                            case "checkPromocode":
                                                if (!commonResponse.getMessage(activity).contains("Empty")) {
                                                    activity.toastMessage(commonResponse.getMessage(activity));
                                                }
                                                break;
                                        }
                                    });
                                    requestResponseListener.failureResponse(null, url, commonResponse.getMessage(activity));
                                } catch (Exception e) {
                                    requestResponseListener.failureResponse(null, url, commonResponse.getMessage(activity));
                                }

                            }
                        } else {
                            activity.failureError(activity.getString(R.string.something_went_wrong));
                        }
                    } catch (Exception e) {
                        requestResponseListener.failureResponse(null, url, "");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<InvoiceListResponse> call, @NonNull Throwable t) {
                activity.failureError(activity.getString(R.string.something_went_wrong));
                requestResponseListener.failureResponse(t, url, "");
            }
        });
    }

    public void checkContactUniqueness(RequestResponseListener requestResponseListener, BaseActivity activity, String url, ContactCheck body) {
        String jwtToken = null;
        if (activity.getJWT() != null && !TextUtils.isEmpty(activity.getJWT())) {
            jwtToken = activity.getJWT();
        }

        Call<CampAttachResponse> orderCall = activity.getService().checkContactUnique(url, jwtToken, body, "6");
        orderCall.enqueue(new Callback<CampAttachResponse>() {
            @Override
            public void onResponse(@NonNull Call<CampAttachResponse> call, @NonNull Response<CampAttachResponse> response) {
                if (response.code() == 401) {
                    activity.onLogout(activity);
                } else {
                    try {
                        if (response.body() != null) {
                            CampAttachResponse commonResponse = response.body();
                            if (commonResponse.status) {
                                requestResponseListener.successResponse("", url, commonResponse.getMessage(activity), "");
                            } else {
                                try {
                                    requestResponseListener.failureResponse(null, url, commonResponse.getMessage(activity));
                                } catch (Exception e) {
                                    requestResponseListener.failureResponse(null, url, commonResponse.getMessage(activity));
                                }

                            }
                        } else {
                            activity.failureError(activity.getString(R.string.something_went_wrong));
                        }
                    } catch (Exception e) {
                        requestResponseListener.failureResponse(null, url, "");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CampAttachResponse> call, @NonNull Throwable t) {
                onFailureAPI(activity, requestResponseListener, t, url);
            }
        });
    }

    public void sendCode(RequestResponseListener requestResponseListener, BaseActivity activity, String url, SendCode body) {
        String jwtToken = null;
        if (activity.getJWT() != null && !TextUtils.isEmpty(activity.getJWT())) {
            jwtToken = activity.getJWT();
        }

        Call<CampAttachResponse> orderCall = activity.getService().sendCode(url, jwtToken, body, "6");
        orderCall.enqueue(new Callback<CampAttachResponse>() {
            @Override
            public void onResponse(@NonNull Call<CampAttachResponse> call, @NonNull Response<CampAttachResponse> response) {
                if (response.code() == 401) {
                    activity.onLogout(activity);
                } else {
                    try {
                        if (response.body() != null) {
                            CampAttachResponse commonResponse = response.body();
                            if (commonResponse.status) {
                                requestResponseListener.successResponse("", url, commonResponse.getMessage(activity), "");
                            } else {
                                try {
                                    requestResponseListener.failureResponse(null, url, commonResponse.getMessage(activity));
                                } catch (Exception e) {
                                    requestResponseListener.failureResponse(null, url, commonResponse.getMessage(activity));
                                }

                            }
                        } else {
                            activity.failureError(activity.getString(R.string.something_went_wrong));
                        }
                    } catch (Exception e) {
                        requestResponseListener.failureResponse(null, url, "");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CampAttachResponse> call, @NonNull Throwable t) {
                onFailureAPI(activity, requestResponseListener, t, url);
            }
        });
    }

    public void verifyCode(RequestResponseListener requestResponseListener, BaseActivity activity, String url, VerifyCode body) {
        String jwtToken = null;
        if (activity.getJWT() != null && !TextUtils.isEmpty(activity.getJWT())) {
            jwtToken = activity.getJWT();
        }

        Call<CampAttachResponse> orderCall = activity.getService().verifyCode(url, jwtToken, body, "6");
        orderCall.enqueue(new Callback<CampAttachResponse>() {
            @Override
            public void onResponse(@NonNull Call<CampAttachResponse> call, @NonNull Response<CampAttachResponse> response) {
                if (response.code() == 401) {
                    activity.onLogout(activity);
                } else {
                    try {
                        if (response.body() != null) {
                            CampAttachResponse commonResponse = response.body();
                            if (commonResponse.status) {
                                requestResponseListener.successResponse("", url, commonResponse.getMessage(activity), "");
                            } else {
                                try {
                                    requestResponseListener.failureResponse(null, url, commonResponse.getMessage(activity));
                                } catch (Exception e) {
                                    requestResponseListener.failureResponse(null, url, commonResponse.getMessage(activity));
                                }

                            }
                        } else {
                            activity.failureError(activity.getString(R.string.something_went_wrong));
                        }
                    } catch (Exception e) {
                        requestResponseListener.failureResponse(null, url, "");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CampAttachResponse> call, @NonNull Throwable t) {
                onFailureAPI(activity, requestResponseListener, t, url);
            }
        });
    }

    public void getInvoiceReport(DownloadListener downloadListener, BaseActivity activity, String url) {
        String jwtToken = null;
        if (activity.getJWT() != null && !TextUtils.isEmpty(activity.getJWT())) {
            jwtToken = activity.getJWT();
        }

        Call<ResponseBody> orderCall = activity.getService().getInvoiceReport(url, jwtToken, "6");
        orderCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.code() == 401) {
                    activity.onLogout(activity);
                } else {
                    try {
                        if (response.body() != null) {
                            downloadListener.successDownload(response.body(), url, response.body().toString());
                        } else {
                            activity.failureError(activity.getString(R.string.invoice_not_found));
                        }
                    } catch (Exception e) {
                        downloadListener.failureResponse(null, url, "Failed to download");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                activity.failureError(activity.getString(R.string.something_went_wrong));
                downloadListener.failureResponse(t, url, "Failed to download");
            }
        });
    }

    public void getAgentService(CampaignListener requestResponseListener, BaseActivity activity, String url) {
        String jwtToken = null;
        if (activity.getJWT() != null && !TextUtils.isEmpty(activity.getJWT())) {
            jwtToken = activity.getJWT();
        }

        Call<AgentServiceResponse> orderCall = activity.getService().getAgentService(url, jwtToken, "6");
        orderCall.enqueue(new Callback<AgentServiceResponse>() {
            @Override
            public void onResponse(@NonNull Call<AgentServiceResponse> call, @NonNull Response<AgentServiceResponse> response) {
                if (response.code() == 401) {
                    activity.onLogout(activity);
                } else {
                    try {
                        if (response.body() != null) {
                            AgentServiceResponse commonResponse = response.body();
                            if (commonResponse.status) {
                                if (commonResponse.data != null) {
                                    requestResponseListener.successResponse(commonResponse.data, url, commonResponse.getMessage(activity));
                                } else {
                                    requestResponseListener.successResponse(null, url, commonResponse.getMessage(activity));
                                }
                            } else {
                                requestResponseListener.failureResponse(null, url, commonResponse.getMessage(activity));
                            }
                        } else {
                            activity.failureError(activity.getString(R.string.something_went_wrong));
                        }
                    } catch (Exception e) {
                        requestResponseListener.failureResponse(null, url, "");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AgentServiceResponse> call, @NonNull Throwable t) {
                activity.failureError(activity.getString(R.string.something_went_wrong));
                requestResponseListener.failureResponse(t, url, "");
            }
        });
    }


    public void paymentRelease(CampaignListener requestResponseListener, BaseActivity activity, String url, CampaignRelease body) {
        String jwtToken = null;
        if (activity.getJWT() != null && !TextUtils.isEmpty(activity.getJWT())) {
            jwtToken = activity.getJWT();
        }

        Call<CampListResponse> orderCall = activity.getService().paymentRelease(url, jwtToken,body, "6");
        orderCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<CampListResponse> call, @NonNull Response<CampListResponse> response) {
                if (response.code() == 401) {
                    activity.onLogout(activity);
                } else {
                    try {
                        if (response.body() != null) {
                            CampListResponse commonResponse = response.body();
                            if (commonResponse.status) {
                                if (commonResponse.data != null) {
                                    requestResponseListener.successResponse(commonResponse.data, url, commonResponse.getMessage(activity));
                                } else {
                                    requestResponseListener.successResponse(null, url, commonResponse.getMessage(activity));
                                }
                            } else {
                                try {
                                    activity.runOnUiThread(() -> {
                                        switch (url) {
                                            case "getJobPosts":
                                            case "getGigListsV2":
                                            case "getJobPostsV2":
                                            case "getPortfolio":
                                            case "getProfileByCategory":
                                                break;
                                            case "checkPromocode":
                                                if (!commonResponse.getMessage(activity).contains("Empty")) {
                                                    activity.toastMessage(commonResponse.getMessage(activity));
                                                }
                                                break;
                                        }
                                    });
                                    requestResponseListener.failureResponse(null, url, commonResponse.getMessage(activity));
                                } catch (Exception e) {
                                    requestResponseListener.failureResponse(null, url, commonResponse.getMessage(activity));
                                }

                            }
                        } else {
                            activity.failureError(activity.getString(R.string.something_went_wrong));
                            requestResponseListener.failureResponse(null, url, "");
                        }
                    } catch (Exception e) {
                        requestResponseListener.failureResponse(null, url, "");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CampListResponse> call, @NonNull Throwable t) {
                activity.failureError(activity.getString(R.string.something_went_wrong));
                requestResponseListener.failureResponse(t, url, "");
            }
        });
    }

    public void getCampaignById(CampaignListener requestResponseListener, BaseActivity activity, String url) {
        String jwtToken = null;
        if (activity.getJWT() != null && !TextUtils.isEmpty(activity.getJWT())) {
            jwtToken = activity.getJWT();
        }

        Call<AgentServiceResponse> orderCall = activity.getService().getCampaignById(url, jwtToken, "6");
        orderCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<AgentServiceResponse> call, @NonNull Response<AgentServiceResponse> response) {
                if (response.code() == 401) {
                    activity.onLogout(activity);
                } else {
                    try {
                        if (response.body() != null) {
                            AgentServiceResponse commonResponse = response.body();
                            if (commonResponse.status) {
                                if (commonResponse.data != null) {
                                    requestResponseListener.successResponse(commonResponse.data, url, commonResponse.getMessage(activity));
                                } else {
                                    requestResponseListener.successResponse(null, url, commonResponse.getMessage(activity));
                                }
                            } else {
                                requestResponseListener.failureResponse(null, url, commonResponse.getMessage(activity));
                            }
                        } else {
                            activity.failureError(activity.getString(R.string.something_went_wrong));
                        }
                    } catch (Exception e) {
                        requestResponseListener.failureResponse(null, url, "");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AgentServiceResponse> call, @NonNull Throwable t) {
                activity.failureError(activity.getString(R.string.something_went_wrong));
                requestResponseListener.failureResponse(t, url, "");
            }
        });
    }

    public void getWalletTxn(WalletListener requestResponseListener, BaseActivity activity, String url) {
        String jwtToken = null;
        if (activity.getJWT() != null && !TextUtils.isEmpty(activity.getJWT())) {
            jwtToken = activity.getJWT();
        }

        Call<WalletTxnResponse> orderCall = activity.getService().getWallet(url, jwtToken, "6");
        orderCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<WalletTxnResponse> call, @NonNull Response<WalletTxnResponse> response) {
                if (response.code() == 401) {
                    //activity.onLogout(activity);
                } else {
                    try {
                        if (response.body() != null) {
                            WalletTxnResponse commonResponse = response.body();
                            if (commonResponse.status) {
                                if (commonResponse.data != null) {
                                    requestResponseListener.successTxnResponse(commonResponse.data, url, commonResponse.getMessage(activity));
                                } else {
                                    requestResponseListener.successResponse(null, url, commonResponse.getMessage(activity));
                                }
                            } else {
                                requestResponseListener.failureResponse(null, url, commonResponse.getMessage(activity));
                            }
                        } else {
                            activity.failureError(activity.getString(R.string.something_went_wrong));
                        }
                    } catch (Exception e) {
                        requestResponseListener.failureResponse(null, url, "");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<WalletTxnResponse> call, @NonNull Throwable t) {
                activity.failureError(activity.getString(R.string.something_went_wrong));
                requestResponseListener.failureResponse(t, url, "");
            }
        });
    }

    public void getWalletBalance(WalletListener requestResponseListener, BaseActivity activity, String url) {
        String jwtToken = null;
        if (activity.getJWT() != null && !TextUtils.isEmpty(activity.getJWT())) {
            jwtToken = activity.getJWT();
        }

        Call<WalletResponse> orderCall = activity.getService().getWalletBalance(url, jwtToken, "6");
        orderCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<WalletResponse> call, @NonNull Response<WalletResponse> response) {
                if (response.code() == 401) {
                    activity.onLogout(activity);
                } else {
                    try {
                        if (response.body() != null) {
                            WalletResponse commonResponse = response.body();
                            if (commonResponse.status) {
                                if (commonResponse.data != null) {
                                    requestResponseListener.successResponse(commonResponse.data, url, commonResponse.getMessage(activity));
                                } else {
                                    requestResponseListener.successResponse(null, url, commonResponse.getMessage(activity));
                                }
                            } else {
                                requestResponseListener.failureResponse(null, url, commonResponse.getMessage(activity));
                            }
                        } else {
                            activity.failureError(activity.getString(R.string.something_went_wrong));
                        }
                    } catch (Exception e) {
                        requestResponseListener.failureResponse(null, url, "");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<WalletResponse> call, @NonNull Throwable t) {
                activity.failureError(activity.getString(R.string.something_went_wrong));
                requestResponseListener.failureResponse(t, url, "");
            }
        });
    }


    public void chargeWallet(WalletListener requestResponseListener, BaseActivity activity, String url, ChargeAmount body) {
        String jwtToken = null;
        if (activity.getJWT() != null && !TextUtils.isEmpty(activity.getJWT())) {
            jwtToken = activity.getJWT();
        }

        Call<WalletResponse> orderCall = activity.getService().walletCharge(url, jwtToken, body, "6");
        orderCall.enqueue(new Callback<WalletResponse>() {
            @Override
            public void onResponse(@NonNull Call<WalletResponse> call, @NonNull Response<WalletResponse> response) {
                if (response.code() == 401) {
                    activity.onLogout(activity);
                } else {
                    try {
                        if (response.body() != null) {
                            WalletResponse commonResponse = response.body();
                            if (commonResponse.status) {
                                requestResponseListener.successResponse(commonResponse.data, url, commonResponse.getMessage(activity));
                            } else {
                                try {
                                    requestResponseListener.failureResponse(null, url, commonResponse.getMessage(activity));
                                } catch (Exception e) {
                                    requestResponseListener.failureResponse(null, url, commonResponse.getMessage(activity));
                                }

                            }
                        } else {
                            activity.failureError(activity.getString(R.string.something_went_wrong));
                        }
                    } catch (Exception e) {
                        requestResponseListener.failureResponse(null, url, "");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<WalletResponse> call, @NonNull Throwable t) {
//                onFailureAPI(activity, requestResponseListener, t, url);
                requestResponseListener.failureResponse(null, url, "");
            }
        });
    }

    public void updateCampaign(RequestResponseListener requestResponseListener, BaseActivity activity, String url, Campaign body) {
        String jwtToken = null;
        if (activity.getJWT() != null && !TextUtils.isEmpty(activity.getJWT())) {
            jwtToken = activity.getJWT();
        }

        Call<CampAttachResponse> orderCall = activity.getService().updateCampaign(url, jwtToken, body, "6");
        orderCall.enqueue(new Callback<CampAttachResponse>() {
            @Override
            public void onResponse(@NonNull Call<CampAttachResponse> call, @NonNull Response<CampAttachResponse> response) {
                if (response.code() == 401) {
                    activity.onLogout(activity);
                } else {
                    onCampResponseAPI(activity, url, response, requestResponseListener);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CampAttachResponse> call, @NonNull Throwable t) {
                onFailureAPI(activity, requestResponseListener, t, url);
            }
        });
    }

    public void getAgentList(WalletListener requestResponseListener, BaseActivity activity, String url) {
        String jwtToken = null;
        if (activity.getJWT() != null && !TextUtils.isEmpty(activity.getJWT())) {
            jwtToken = activity.getJWT();
        }

        Call<WalletResponse> orderCall = activity.getService().getWalletBalance(url, jwtToken, "6");
        orderCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<WalletResponse> call, @NonNull Response<WalletResponse> response) {
                if (response.code() == 401) {
                    activity.onLogout(activity);
                } else {
                    try {
                        if (response.body() != null) {
                            WalletResponse commonResponse = response.body();
                            if (commonResponse.status) {
                                if (commonResponse.data != null) {
                                    requestResponseListener.successResponse(commonResponse.data, url, commonResponse.getMessage(activity));
                                } else {
                                    requestResponseListener.successResponse(null, url, commonResponse.getMessage(activity));
                                }
                            } else {
                                requestResponseListener.failureResponse(null, url, commonResponse.getMessage(activity));
                            }
                        } else {
                            activity.failureError(activity.getString(R.string.something_went_wrong));
                        }
                    } catch (Exception e) {
                        requestResponseListener.failureResponse(null, url, "");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<WalletResponse> call, @NonNull Throwable t) {
                activity.failureError(activity.getString(R.string.something_went_wrong));
                requestResponseListener.failureResponse(t, url, "");
            }
        });
    }

   public void getOrderList(WalletListener requestResponseListener, BaseActivity activity, String url) {
        String jwtToken = null;
        if (activity.getJWT() != null && !TextUtils.isEmpty(activity.getJWT())) {
            jwtToken = activity.getJWT();
        }

        Call<WalletResponse> orderCall = activity.getService().getWalletBalance(url, jwtToken, "6");
        orderCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<WalletResponse> call, @NonNull Response<WalletResponse> response) {
                if (response.code() == 401) {
                    activity.onLogout(activity);
                } else {
                    try {
                        if (response.body() != null) {
                            WalletResponse commonResponse = response.body();
                            if (commonResponse.status) {
                                if (commonResponse.data != null) {
                                    requestResponseListener.successResponse(commonResponse.data, url, commonResponse.getMessage(activity));
                                } else {
                                    requestResponseListener.successResponse(null, url, commonResponse.getMessage(activity));
                                }
                            } else {
                                requestResponseListener.failureResponse(null, url, commonResponse.getMessage(activity));
                            }
                        } else {
                            activity.failureError(activity.getString(R.string.something_went_wrong));
                        }
                    } catch (Exception e) {
                        requestResponseListener.failureResponse(null, url, "");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<WalletResponse> call, @NonNull Throwable t) {
                activity.failureError(activity.getString(R.string.something_went_wrong));
                requestResponseListener.failureResponse(t, url, "");
            }
        });
    }

    public void updateNoti(WalletListener requestResponseListener, BaseActivity activity, String url, UpdateNoti body) {
        String jwtToken = null;
        if (activity.getJWT() != null && !TextUtils.isEmpty(activity.getJWT())) {
            jwtToken = activity.getJWT();
        }

        Call<WalletResponse> orderCall = activity.getService().updateNotification(url, jwtToken, body, "6");
        orderCall.enqueue(new Callback<WalletResponse>() {
            @Override
            public void onResponse(@NonNull Call<WalletResponse> call, @NonNull Response<WalletResponse> response) {
                if (response.code() == 401) {
                    activity.onLogout(activity);
                } else {
                    try {
                        if (response.body() != null) {
                            WalletResponse commonResponse = response.body();
                            if (commonResponse.status) {
                                requestResponseListener.successResponse(commonResponse.data, url, commonResponse.getMessage(activity));
                            } else {
                                try {
                                    requestResponseListener.failureResponse(null, url, commonResponse.getMessage(activity));
                                } catch (Exception e) {
                                    requestResponseListener.failureResponse(null, url, commonResponse.getMessage(activity));
                                }

                            }
                        } else {
                            activity.failureError(activity.getString(R.string.something_went_wrong));
                        }
                    } catch (Exception e) {
                        requestResponseListener.failureResponse(null, url, "");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<WalletResponse> call, @NonNull Throwable t) {
//                onFailureAPI(activity, requestResponseListener, t, url);
                requestResponseListener.failureResponse(null, url, "");
            }
        });
    }
}
