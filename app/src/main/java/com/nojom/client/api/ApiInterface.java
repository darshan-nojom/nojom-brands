package com.nojom.client.api;


import com.nojom.client.model.AgentServiceResponse;
import com.nojom.client.model.CampAttachResponse;
import com.nojom.client.model.CampListResponse;
import com.nojom.client.model.Campaign;
import com.nojom.client.model.CampaignPay;
import com.nojom.client.model.CampaignRelease;
import com.nojom.client.model.CampaignType;
import com.nojom.client.model.ChargeAmount;
import com.nojom.client.model.ChatList;
import com.nojom.client.model.CommonResponse;
import com.nojom.client.model.ContactCheck;
import com.nojom.client.model.InvoiceListResponse;
import com.nojom.client.model.PriceRange;
import com.nojom.client.model.PriceRangeSel;
import com.nojom.client.model.SendCode;
import com.nojom.client.model.ServicesData;
import com.nojom.client.model.SimpleResponse;
import com.nojom.client.model.UpdateNoti;
import com.nojom.client.model.VerifyCode;
import com.nojom.client.model.WalletResponse;
import com.nojom.client.model.WalletTxnResponse;
import com.nojom.client.util.Constants;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Url;

public interface ApiInterface {
    @FormUrlEncoded
    @POST
    Call<CommonResponse> requestAPIPOST(@Url String method, @FieldMap HashMap<String, String> defaultData, @Header("Authorization") String token, @Header("sys_id") String sysId);

    @FormUrlEncoded
    @POST
    Call<CommonResponse> requestAPIPOST(@Url String method, @FieldMap HashMap<String, String> defaultData, @Header("Authorization") String token, @Header("sys_id") String sysId, @Field("price_range") JSONArray items
            , @Field("followers_range") JSONArray follow, @Field("age_range") JSONArray age);

    @GET
    Call<CommonResponse> requestAPIGET(@Url String method, @Header("Authorization") String token, @Header("sys_id") String sysId);

    @GET
    Call<SimpleResponse> simpleAPIGET(@Url String method, @Header("Authorization") String token, @Header("sys_id") String sysId);

    @Multipart
    @POST
    Call<CommonResponse> uploadFile(@Url String method, @Part MultipartBody.Part file, @Header("Authorization") String token, @Header("sys_id") String sysId);

    @Multipart
    @POST
    Call<CommonResponse> uploadFileWithMap(@Url String method, @Part MultipartBody.Part file, @Header("Authorization") String token, @Header("sys_id") String sysId, @Part("cr_number") RequestBody defaultData);

    @Multipart
    @POST
    Call<CampAttachResponse> uploadCampAttach(@Url String method, @Part MultipartBody.Part file, @Header("Authorization") String token, @Header("sys_id") String sysId);

    @POST
    Call<CampAttachResponse> postCampaign(@Url String method, @Header("Authorization") String token, @Body Campaign campaign, @Header("sys_id") String sysId);

    @PATCH
    Call<CampAttachResponse> updateCampaign(@Url String method, @Header("Authorization") String token, @Body Campaign campaign, @Header("sys_id") String sysId);

    @POST
    Call<CampAttachResponse> campaignPayment(@Url String method, @Header("Authorization") String token, @Body CampaignPay campaign, @Header("sys_id") String sysId);

    @Multipart
    @POST
    Call<CommonResponse> uploadFileWithMapVat(@Url String method, @Part MultipartBody.Part file, @Header("Authorization") String token, @Header("sys_id") String sysId, @Part("vat_number") RequestBody defaultData);

    @Multipart
    @POST
    Call<CommonResponse> uploadFile(@Url String method, @Part MultipartBody.Part[] file, @PartMap HashMap<String, String> defaultData, @Header("Authorization") String token, @Header("sys_id") String sysId);

    @Multipart
    @POST
    Call<CommonResponse> bankTransfer(@Url String method, @Part MultipartBody.Part file, @Header("Authorization") String token, @Header("sys_id") Integer sysId,
                                      @Part("job_post_id") Integer jid, @Part("job_post_bid_id") Integer jpbid, @Part("redeem") Double reedim, @Part("amount") Double amount,
                                      @Part("deposit_charges") Double dCharge, @Part("payment_type_id") Integer pTypeId,
                                      @Part("discount_code") String dCode, @Part("payable_amount") Double payAmnt,
                                      @Part("description") String desc, @Part("payment_platform_id") Integer ptId
            , @Part("sender_name") String sendName, @Part("bank_id") Integer bId, @Part("account_number") String accNo, @Part("transaction_date") String txnDate
            , @Part("reference_number") String refNo, @Part("notes") String not, @Part("sys_id") Integer syId);

    @Multipart
    @POST
    Call<CommonResponse> bankTransferGig(@Url String method, @Part MultipartBody.Part file, @Header("Authorization") String token, @Header("sys_id") Integer sysId, @Part("sys_id") Integer sId,
                                         @Part("gigID") Integer gigId, @Part("agentProfileID") Integer agentId, @Part("deadlineID") Integer deadline, @Part("paymentTypeID") Integer payTypeId,
                                         @Part("paymentPlatformID") Integer payPlatformId, @Part("description") String desc,
                                         @PartMap Map<String, RequestBody> items, @Part("discountCode") String disCode,
                                         @Part("payableAmount") Double payAmount, @Part("totalPrice") Double totPrice
            , @Part("sender_name") String sendName, @Part("bank_id") Integer bId, @Part("account_number") String accNo, @Part("transaction_date") String txnDate
            , @Part("reference_number") String refNo, @Part("notes") String not);

    @Multipart
    @POST
    Call<CommonResponse> uploadFileBody(@Url String method, @Part MultipartBody.Part[] file, @PartMap HashMap<String, RequestBody> defaultData, @Header("Authorization") String token, @Header("sys_id") String sysId);


    @Multipart
    @POST
    Call<CommonResponse> uploadFileBody(@Url String method, @Part MultipartBody.Part[] file, @PartMap HashMap<String, RequestBody> defaultData, @Header("Authorization") String token, @Header("sys_id") String sysId,
                                        @Part("budget") Double budget);


    @FormUrlEncoded
    @POST
    Call<ChatList> getUser(@Url String method, @Field("profileId") String profileID, @Field("profile_type_id") String profileTypeID, @Header("sys_id") String sysId
            , @Header("Authorization") String token);

    @Multipart
    @POST
    Call<CommonResponse> acceptOrRejectOffer(@Url String method, @Header("Authorization") String token, @PartMap HashMap<String, RequestBody> defaultData, @Header("sys_id") String sysId);

    @Multipart
    @POST
    Call<CommonResponse> getSocialInfluenceProfiles(@Header("Authorization") String token, @Url String method, @PartMap HashMap<String, RequestBody> defaultData, @Header("sys_id") String sysId);

    @GET
    Call<ServicesData> getServices(@Url String method, @Header("Authorization") String token, @Header("sys_id") String sysId);

    @POST
    Call<CampListResponse> campaignList(@Url String method, @Header("Authorization") String token,
                                        @Body CampaignType campaignType, @Header("sys_id") String sysId);

    @GET
    Call<InvoiceListResponse> getInvoices(@Url String method, @Header("Authorization") String token, @Header("sys_id") String sysId);

    @POST
    Call<CampAttachResponse> checkContactUnique(@Url String method, @Header("Authorization") String token, @Body ContactCheck campaign, @Header("sys_id") String sysId);

    @POST
    Call<CampAttachResponse> sendCode(@Url String method, @Header("Authorization") String token, @Body SendCode campaign, @Header("sys_id") String sysId);

    @POST
    Call<CampAttachResponse> verifyCode(@Url String method, @Header("Authorization") String token, @Body VerifyCode campaign, @Header("sys_id") String sysId);

    @GET
    Call<ResponseBody> getInvoiceReport(@Url String method, @Header("Authorization") String token, @Header("sys_id") String sysId);

    @GET
    Call<AgentServiceResponse> getAgentService(@Url String method, @Header("Authorization") String token, @Header("sys_id") String sysId);

    @POST
    Call<CampListResponse> paymentRelease(@Url String method, @Header("Authorization") String token, @Body CampaignRelease campaign, @Header("sys_id") String sysId);

    @GET
    Call<AgentServiceResponse> getCampaignById(@Url String method, @Header("Authorization") String token, @Header("sys_id") String sysId);

    @GET
    Call<WalletTxnResponse> getWallet(@Url String method, @Header("Authorization") String token, @Header("sys_id") String sysId);

    @GET
    Call<WalletResponse> getWalletBalance(@Url String method, @Header("Authorization") String token, @Header("sys_id") String sysId);

    @POST
    Call<WalletResponse> walletCharge(@Url String method, @Header("Authorization") String token, @Body ChargeAmount campaign, @Header("sys_id") String sysId);

    @PATCH
    Call<WalletResponse> updateNotification(@Url String method, @Header("Authorization") String token, @Body UpdateNoti campaign, @Header("sys_id") String sysId);

}


