package com.nojom.client.api;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.nojom.client.R;
import com.nojom.client.model.CommonResponse;
import com.nojom.client.model.PriceRange;
import com.nojom.client.model.PriceRangeSel;
import com.nojom.client.model.SimpleResponse;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

    public void apiRequest(RequestResponseListener requestResponseListener, BaseActivity activity, String url, boolean isPostMethod, Map<String, String> map, JSONArray mPrice
            , JSONArray arrFollow, JSONArray arrAge) {

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

    public void apiImageUploadRequestWithMap(RequestResponseListener requestResponseListener, BaseActivity activity, String url, MultipartBody.Part fileBody,
                                             RequestBody map, boolean isVat) {
        String jwtToken = null;
        if (activity.getJWT() != null && !TextUtils.isEmpty(activity.getJWT())) {
            jwtToken = activity.getJWT();
        }

        Call<CommonResponse> orderCall = isVat ? activity.getService().uploadFileWithMapVat(url, fileBody, jwtToken, "6", map)
                : activity.getService().uploadFileWithMap(url, fileBody, jwtToken, "6", map);
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

    public void apiImageUploadRequest(RequestResponseListener requestResponseListener, BaseActivity activity, String url, MultipartBody.Part fileBody,
                                      HashMap<String, String> map) {
        String jwtToken = null;
        if (activity.getJWT() != null && !TextUtils.isEmpty(activity.getJWT())) {
            jwtToken = activity.getJWT();
        }
        Call<CommonResponse> orderCall = activity.getService().uploadFile(url, fileBody, jwtToken, "6");
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


    public void apiImageUploadRequestBody(RequestResponseListener requestResponseListener, BaseActivity activity, String url, MultipartBody.Part[] fileBody,
                                          HashMap<String, RequestBody> map) {
        String jwtToken = null;
        if (activity.getJWT() != null && !TextUtils.isEmpty(activity.getJWT())) {
            jwtToken = activity.getJWT();
        }
        Call<CommonResponse> orderCall = activity.getService().uploadFileBody(url, fileBody, map, jwtToken, "6");
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

    public void apiImageUploadRequestBody(RequestResponseListener requestResponseListener, BaseActivity activity, String url, MultipartBody.Part[] fileBody,
                                          HashMap<String, RequestBody> map, Double buget) {
        String jwtToken = null;
        if (activity.getJWT() != null && !TextUtils.isEmpty(activity.getJWT())) {
            jwtToken = activity.getJWT();
        }
        Call<CommonResponse> orderCall = activity.getService().uploadFileBody(url, fileBody, map, jwtToken, "6", buget);
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


    public void apiImageUploadRequest(RequestResponseListener requestResponseListener, BaseActivity activity, String url, MultipartBody.Part[] fileBody,
                                      HashMap<String, String> map) {
        String jwtToken = null;
        if (activity.getJWT() != null && !TextUtils.isEmpty(activity.getJWT())) {
            jwtToken = activity.getJWT();
        }
        Call<CommonResponse> orderCall = activity.getService().uploadFile(url, fileBody, map, jwtToken, "6");
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

    public void apiBankTransfer(RequestResponseListener requestResponseListener, BaseActivity activity, String url, MultipartBody.Part fileBody,
                                Integer jId, Integer jpbid, Double redim, Double amnt, Double depFee, String pCode, Double payAmnt, String desc,
                                Integer thr, Integer six,
                                String sendName, Integer bId, String cardNo, String txnDate,
                                String refNo, String not) {
        String jwtToken = null;
        if (activity.getJWT() != null && !TextUtils.isEmpty(activity.getJWT())) {
            jwtToken = activity.getJWT();
        }
        Call<CommonResponse> orderCall = activity.getService().bankTransfer(url, fileBody, jwtToken, six, jId, jpbid, redim, amnt, depFee, 11,
                pCode
                , payAmnt, desc, thr, sendName, bId, cardNo, txnDate, refNo, not, 6);
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

    public void apiBankTransferGig(RequestResponseListener requestResponseListener, BaseActivity activity, String url, MultipartBody.Part fileBody,
                                   Integer gigId, Integer agentId, Integer deadline, Integer payTypeId, Integer payPlatId, String desc, String req, String disCode,
                                   Double payAmount, Double totPrice,
                                   String sendName, Integer bId, String cardNo, String txnDate,
                                   String refNo, String not, Integer offerId, String pk, String sk) {
        String jwtToken = null;
        if (activity.getJWT() != null && !TextUtils.isEmpty(activity.getJWT())) {
            jwtToken = activity.getJWT();
        }

//        Type listType = new TypeToken<List<MyRequestItem>>(){}.getType();
//        ArrayList<MyRequestItem> requestItems = new Gson().fromJson(req, listType);

        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        RequestBody requestBody = RequestBody.create(req, MediaType.parse("text/plain"));
        requestBodyMap.put("requirments", requestBody);

        Call<CommonResponse> orderCall = activity.getService().bankTransferGig(url, fileBody, jwtToken, 6, 6, gigId, agentId, deadline, payTypeId, payPlatId, desc,
                requestBodyMap
                , disCode, payAmount, totPrice, sendName, bId, cardNo, txnDate, refNo, not);
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
                            String jsonArray
                                    = objectMapper.writeValueAsString(commonResponse.data);
                            System.out.println(jsonArray);
                            requestResponseListener.successResponse(jsonArray, url, commonResponse.getMessage(activity),"");
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
}
