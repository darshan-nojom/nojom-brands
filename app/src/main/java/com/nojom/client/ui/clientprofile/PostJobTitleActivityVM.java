package com.nojom.client.ui.clientprofile;

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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nojom.client.R;
import com.nojom.client.adapter.JobTitleAdapter;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivityJobPostTitleBinding;
import com.nojom.client.model.Attachment;
import com.nojom.client.model.ExpertLawyers;
import com.nojom.client.model.ServicesModel;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.CompressFile;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PostJobTitleActivityVM extends AndroidViewModel implements JobTitleAdapter.OnClickTitle, RequestResponseListener {
    private ActivityJobPostTitleBinding binding;
    private BaseActivity activity;
    private String serviceId;
    private List<ServicesModel.JobTitle> jobTitleList;
    private String moSkilIDs, moSkillNames, moBudget, moClientRate, selectedDeadline, payType, lawyerService, description, attachLocalFile;
    private int moClientRateId;
    private List<ExpertLawyers.Data> expertUserList;
    private ArrayList<Attachment> fileList;

    public PostJobTitleActivityVM(Application application, ActivityJobPostTitleBinding postJobBinding, BaseActivity postJobActivity) {
        super(application);
        this.activity = postJobActivity;
        this.binding = postJobBinding;
    }

    public boolean isValid() {
        boolean isValid = true;
        if (TextUtils.isEmpty(binding.etTitle.getText().toString().trim())) {
            activity.toastMessage(activity.getString(R.string.please_enter_valid_title));
            isValid = false;
            return isValid;
        }
        return isValid;
    }

    public void initData() {
        binding.tvTitle.setText(Utils.getColorString(activity, activity.getString(R.string.job_post_title),
                activity.getString(R.string._title), R.color.colorPrimary));

        serviceId = activity.getIntent().getStringExtra(Constants.PLATFORM_ID);
        moSkilIDs = activity.getIntent().getStringExtra(Constants.SKILL_IDS);
        moSkillNames = activity.getIntent().getStringExtra(Constants.SKILL_NAMES);
        moClientRateId = activity.getIntent().getIntExtra(Constants.CLIENT_RATE_ID, 0);
        moClientRate = activity.getIntent().getStringExtra(Constants.CLIENT_RATE);
        moBudget = activity.getIntent().getStringExtra(Constants.BUDGET);
        selectedDeadline = activity.getIntent().getStringExtra("deadline");
        description = activity.getIntent().getStringExtra(Constants.DESCRIBE);
        attachLocalFile = activity.getIntent().getStringExtra(Constants.ATTACH_LOCAL_FILE);

        payType = activity.getIntent().getStringExtra(Constants.PAY_TYPE);
        lawyerService = activity.getIntent().getStringExtra(Constants.PLATFORM_NAME);

        List<ServicesModel.Data> servicesList = Preferences.getTopServices(activity);
        if (servicesList != null && servicesList.size() > 0) {
            for (ServicesModel.Data data : servicesList) {
                if (data.id == 4352/*Integer.parseInt(serviceId)*/) {
                    jobTitleList = data.suggestedJobTitles;
                    break;
                }
            }
        }

//        jobTitleList = new ArrayList<>();
//        jobTitleList.add(new ServicesModel.JobTitle(activity.getString(R.string.promote_my_restaurant)));
//        jobTitleList.add(new ServicesModel.JobTitle(activity.getString(R.string.i_would_like_to_meet_you)));
//        jobTitleList.add(new ServicesModel.JobTitle(activity.getString(R.string.post_ads_for_my_company)));
//        jobTitleList.add(new ServicesModel.JobTitle(activity.getString(R.string.i_have_partnership_proposal)));

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        JobTitleAdapter titleAdapter = new JobTitleAdapter(activity, jobTitleList, this);
        binding.recyclerView.setAdapter(titleAdapter);

        expertUserList = Preferences.getExpertUsers(activity);

        fileList = new ArrayList<>();
        if (!TextUtils.isEmpty(attachLocalFile)) {
            String[] filesSplit = attachLocalFile.split(",");
            for (String aFilesSplit : filesSplit) {
                if (aFilesSplit.contains("png") || aFilesSplit.contains("jpg") || aFilesSplit.contains("jpeg")) {
                    fileList.add(new Attachment(aFilesSplit, "", "", true));
                } else {
                    fileList.add(new Attachment(aFilesSplit, "", "", false));
                }
            }
        }
    }

    @Override
    public void onClickTitle(ServicesModel.JobTitle title) {
        binding.etTitle.setText(title.getTitle(activity.getLanguage()));
    }

    public void postJobAPI() {
        try {
            if (!activity.isNetworkConnected())
                return;

            activity.isClickableView = true;
            binding.btnPostJob.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);

            MultipartBody.Part[] body = null;
            if (fileList != null && fileList.size() > 0) {
                body = new MultipartBody.Part[fileList.size()];
                for (int i = 0; i < fileList.size(); i++) {
                    File file;
                    if (!activity.isEmpty(fileList.get(i).filepath)) {
//                        if (fileList.get(i).filepath.contains(".png") || fileList.get(i).filepath.contains(".jpg") || fileList.get(i).filepath.contains(".jpeg")) {
//                            file = CompressFile.getCompressedImageFile(new File(fileList.get(i).filepath), activity);
//                        } else {
                        file = new File(fileList.get(i).filepath);
//                        }
                        Uri selectedUri = Uri.fromFile(file); // not orking in samsung device
                        String fileExtension = MimeTypeMap.getFileExtensionFromUrl(selectedUri.toString());
                        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase());

                        RequestBody requestFile = null;
                        if (mimeType != null) {
                            requestFile = RequestBody.create(file, MediaType.parse(mimeType));
                            Headers.Builder headers = new Headers.Builder();
                            headers.addUnsafeNonAscii("Content-Disposition", "form-data; name=\"file\"; filename=\"" + file.getName() + "\"");
                            body[i] = MultipartBody.Part.create(headers.build(), requestFile);
                        }
                    }
                }
            }

            RequestBody descriptionBody = RequestBody.create(description, MultipartBody.FORM);
            RequestBody titleBody = RequestBody.create(binding.etTitle.getText().toString(), MultipartBody.FORM);
            RequestBody payTypeIdBody = null;
            if (!TextUtils.isEmpty(payType)) {
                payTypeIdBody = RequestBody.create(payType, MultipartBody.FORM);
            }

            if (moClientRateId == -1) {//if select FREE then pass clientRate=-1 and budget=0
                payTypeIdBody = RequestBody.create("5", MultipartBody.FORM);
            } else {
                payTypeIdBody = RequestBody.create("1", MultipartBody.FORM);
            }

            RequestBody rateIdBody = RequestBody.create(String.valueOf(moClientRateId), MultipartBody.FORM);
            if (moSkilIDs.equals("0")) {
                moSkilIDs = "4352";
            }
            RequestBody skillidBody = RequestBody.create(moSkilIDs, MultipartBody.FORM);
            RequestBody platFormId = RequestBody.create(serviceId, MultipartBody.FORM);

            if (moClientRateId == 0) {
                if (TextUtils.isEmpty(moBudget) || moBudget.equalsIgnoreCase("null")) {
                    activity.toastMessage(activity.getString(R.string.please_select_budget));
                    activity.isClickableView = false;
                    binding.btnPostJob.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.GONE);
                    return;
                }
            }

            StringBuilder expertIds = new StringBuilder();
            if (expertUserList != null && expertUserList.size() > 0) {
                for (int i = 0; i < expertUserList.size(); i++) {
                    if (i == 0) {
                        expertIds = new StringBuilder(expertUserList.get(i).id + "");
                    } else {
                        expertIds.insert(0, expertUserList.get(i).id + ", ");
                    }
                }
            }
            RequestBody offeredBody = RequestBody.create("1", MultipartBody.FORM);
            RequestBody expertsBody = RequestBody.create(expertIds.toString(), MultipartBody.FORM);
            RequestBody sysIdBody = RequestBody.create("6" /*SYS_ID*/, MultipartBody.FORM);


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
            if (expertUserList != null && expertUserList.size() > 0) {
                map.put("offered", offeredBody);
                map.put("experts", expertsBody);
            }
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

            apiRequest.apiImageUploadRequestBody(this, activity, API_ADD_JOB_POST, body, map);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        activity.isClickableView = false;
        binding.btnPostJob.setVisibility(View.VISIBLE);
        binding.progressBar.setVisibility(View.GONE);
        Preferences.setExpertUsers(activity, null);
        postDoneDialog();
        Preferences.writeString(activity, Constants.ATTACH_LOCAL_FILE, "");
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        activity.isClickableView = false;
        binding.btnPostJob.setVisibility(View.VISIBLE);
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
