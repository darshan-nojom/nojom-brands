package com.nojom.client.ui.projects;

import static com.nojom.client.util.Constants.AGENT_PROFILE_DATA;
import static com.nojom.client.util.Constants.API_ADD_JOB_POST;
import static com.nojom.client.util.Constants.SYS_ID;

import android.app.Application;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.TextView;

import androidx.lifecycle.AndroidViewModel;

import com.nojom.client.R;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivityOfferSummaryBinding;
import com.nojom.client.databinding.ActivityOfferTitleBinding;
import com.nojom.client.model.AgentProfile;
import com.nojom.client.model.Attachment;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.CompressFile;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class HireOfferSummaryActivityVM extends AndroidViewModel implements RequestResponseListener {
    private ActivityOfferSummaryBinding binding;
    private BaseActivity activity;
    private String moBudget, selectedDeadline, attachLocalFile, title, moClientRate;
    private int moClientRateId;
    //    private List<ExpertLawyers.Data> expertUserList;
    private ArrayList<Attachment> fileList;
    private String describe;
    private AgentProfile agentData;

    public HireOfferSummaryActivityVM(Application application, ActivityOfferSummaryBinding postJobBinding, BaseActivity postJobActivity) {
        super(application);
        this.activity = postJobActivity;
        this.binding = postJobBinding;
        initData();
    }

    private void initData() {
        if (activity.getIntent() != null) {
            describe = activity.getIntent().getStringExtra(Constants.DESCRIBE);
            attachLocalFile = activity.getIntent().getStringExtra(Constants.ATTACH_LOCAL_FILE);
            moClientRateId = activity.getIntent().getIntExtra(Constants.CLIENT_RATE_ID, 0);
            moClientRate = activity.getIntent().getStringExtra(Constants.CLIENT_RATE);
            moBudget = activity.getIntent().getStringExtra(Constants.BUDGET);
            selectedDeadline = activity.getIntent().getStringExtra("deadline");
            title = activity.getIntent().getStringExtra("title");
            agentData = (AgentProfile) activity.getIntent().getSerializableExtra(AGENT_PROFILE_DATA);
        }

        if (agentData != null) {
            activity.setImage(binding.imgProfile, TextUtils.isEmpty(agentData.profilePic) ? "" : agentData.path + agentData.profilePic, 0, 0);
            binding.txtName.setText(agentData.username);
        }
        binding.txtDeadline.setText(selectedDeadline);
        binding.txtDescribe.setText(describe);
        if (TextUtils.isEmpty(moClientRate)) {
            if (TextUtils.isEmpty(moBudget) || moBudget == null || moBudget.equals("null")) {
                binding.txtBudget.setText(activity.getString(R.string.free).toUpperCase());
            } else {
                binding.txtBudget.setText(activity.getCurrency().equals("SAR") ? moBudget + " " + activity.getString(R.string.sar) : activity.getString(R.string.dollar) + moBudget);
            }
        } else {
            binding.txtBudget.setText(moClientRate);
        }
        binding.txtBudget.setTextColor(activity.getResources().getColor(R.color.black));
        binding.txtTitle.setText(title);
    }

    public void postJobAPI() {
        try {
            if (!activity.isNetworkConnected())
                return;

            activity.isClickableView = true;
            binding.btnLastStep.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);

            MultipartBody.Part[] body = null;
            if (fileList != null && fileList.size() > 0) {
                body = new MultipartBody.Part[fileList.size()];
                for (int i = 0; i < fileList.size(); i++) {
                    File file;
                    if (!activity.isEmpty(fileList.get(i).filepath)) {
                        if (fileList.get(i).filepath.contains(".png") || fileList.get(i).filepath.contains(".jpg") || fileList.get(i).filepath.contains(".jpeg")) {
                            file = CompressFile.getCompressedImageFile(new File(fileList.get(i).filepath), activity);
                        } else {
                            file = new File(fileList.get(i).filepath);
                        }
                        Uri selectedUri = Uri.fromFile(file); // not orking in samsung device
                        String fileExtension = MimeTypeMap.getFileExtensionFromUrl(selectedUri.toString());
                        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase());

                        RequestBody requestFile = null;
                        if (mimeType != null && file != null) {
                            requestFile = RequestBody.create(file, MediaType.parse(mimeType));
                            Headers.Builder headers = new Headers.Builder();
                            headers.addUnsafeNonAscii("Content-Disposition", "form-data; name=\"file\"; filename=\"" + file.getName() + "\"");
                            body[i] = MultipartBody.Part.create(headers.build(), requestFile);
                        }
                    }
                }
            }

            RequestBody descriptionBody = RequestBody.create(describe, MultipartBody.FORM);
            RequestBody titleBody = RequestBody.create(title, MultipartBody.FORM);
            RequestBody payTypeIdBody = null;
//            if (!TextUtils.isEmpty(payType)) {
//                payTypeIdBody = RequestBody.create(payType, MultipartBody.FORM);
//            }

            if (moClientRateId == -1) {//if select FREE then pass clientRate=-1 and budget=0
                payTypeIdBody = RequestBody.create("5", MultipartBody.FORM);
            } else {
                payTypeIdBody = RequestBody.create("1", MultipartBody.FORM);
            }

            RequestBody rateIdBody = RequestBody.create(String.valueOf(moClientRateId), MultipartBody.FORM);
            RequestBody skillidBody = RequestBody.create("4352", MultipartBody.FORM);
            RequestBody platFormId = RequestBody.create("0", MultipartBody.FORM);

            if (moClientRateId == 0) {
                if (TextUtils.isEmpty(moBudget) || moBudget.equalsIgnoreCase("null")) {
                    activity.toastMessage(activity.getString(R.string.please_select_budget));
                    activity.isClickableView = false;
                    binding.btnLastStep.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.GONE);
                    return;
                }
            }

//            StringBuilder expertIds = new StringBuilder();
//            if (expertUserList != null && expertUserList.size() > 0) {
//                for (int i = 0; i < expertUserList.size(); i++) {
//                    if (i == 0) {
//                        expertIds = new StringBuilder(expertUserList.get(i).id + "");
//                    } else {
//                        expertIds.insert(0, expertUserList.get(i).id + ", ");
//                    }
//                }
//            }
            RequestBody offeredBody = RequestBody.create("1", MultipartBody.FORM);
            RequestBody expertsBody = RequestBody.create(agentData.id.toString(), MultipartBody.FORM);
            RequestBody sysIdBody = RequestBody.create("6", MultipartBody.FORM);


            RequestBody deadLineBody = null;
            String formattedDate = "";
            if (selectedDeadline != null && !TextUtils.isEmpty(selectedDeadline.trim())) {
                String outputFormat = "yyyy-MM-dd HH:mm:ss";
                String inputFormat = "yyyy-MM-dd HH:mm";
                try {
                    formattedDate = Utils.TimeStampConverterEnglish(inputFormat, selectedDeadline, outputFormat);
                    deadLineBody = RequestBody.create(formattedDate, MultipartBody.FORM);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return;
                }
            }


            HashMap<String, RequestBody> map = new HashMap<>();
            map.put("offered", offeredBody);
            map.put("experts", expertsBody);
            map.put("sys_id", sysIdBody);
            map.put("title", titleBody);
            map.put("description", descriptionBody);
            if (payTypeIdBody != null) {
                map.put("pay_type_id", payTypeIdBody);
            }
            RequestBody budgetBody;
            if (moClientRateId == 0) {
                if (!TextUtils.isEmpty(moBudget) && !moBudget.equalsIgnoreCase("null")) {
                    budgetBody = RequestBody.create(moBudget, MultipartBody.FORM);
                    map.put("budget", budgetBody);
                }
            }
            map.put("client_rate_id", rateIdBody);
            map.put("service_id", skillidBody);
            map.put("socialPlatformID", platFormId);

            map.put("pages", RequestBody.create("0", MultipartBody.FORM));
            if (deadLineBody != null)
                map.put("deadline", deadLineBody);

            ApiRequest apiRequest = new ApiRequest();
//            if (moClientRateId == 0) {
//                apiRequest.apiImageUploadRequestBody(this, activity, API_ADD_JOB_POST, body, map,Double.parseDouble(moBudget));
//            } else {
            apiRequest.apiImageUploadRequestBody(this, activity, API_ADD_JOB_POST, body, map);
//            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        activity.isClickableView = false;
        binding.btnLastStep.setVisibility(View.VISIBLE);
        binding.progressBar.setVisibility(View.GONE);
        Preferences.setExpertUsers(activity, null);
        postDoneDialog();
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        activity.isClickableView = false;
        binding.btnLastStep.setVisibility(View.VISIBLE);
        binding.progressBar.setVisibility(View.GONE);
        activity.toastMessage(message);
    }

    private void postDoneDialog() {
        final Dialog dialog = new Dialog(activity);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_posting_done);
        dialog.setCancelable(true);

        TextView tvInfo1 = dialog.findViewById(R.id.tv_info_1);
        TextView tvInfo2 = dialog.findViewById(R.id.tv_info_2);
        TextView tvInfo3 = dialog.findViewById(R.id.tv_info_3);
        TextView tvViewProposals = dialog.findViewById(R.id.tv_view_proposals);

        int[] colorList = {R.color.black};
        String[] fonts = {Constants.SFTEXT_BOLD};

        String[] words1 = {activity.getString(R.string.free_bids)};
        String[] words2 = {activity.getString(R.string.hire_the_best_fit)};
        String[] words3 = {activity.getString(R.string.satisfied_100)};

        tvInfo1.setText(Utils.getBoldString(activity, activity.getString(R.string.job_post_done_1), fonts, colorList, words1));
        tvInfo2.setText(Utils.getBoldString(activity, activity.getString(R.string.job_post_done_2), fonts, colorList, words2));
        tvInfo3.setText(Utils.getBoldString(activity, activity.getString(R.string.job_post_done_3), fonts, colorList, words3));

        tvViewProposals.setOnClickListener(v -> dialog.dismiss());

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        lp.gravity = Gravity.CENTER;
        lp.width = (int) (displaymetrics.widthPixels * 0.9);
        lp.height = (int) (displaymetrics.heightPixels * 0.7);
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
        dialog.setOnDismissListener(dialog1 -> {
            activity.isClickableView = false;
            activity.gotoMainActivity(Constants.TAB_JOB_LIST);
        });
    }
}
