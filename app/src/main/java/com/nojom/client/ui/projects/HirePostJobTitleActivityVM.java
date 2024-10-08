package com.nojom.client.ui.projects;

import static com.nojom.client.util.Constants.AGENT_PROFILE_DATA;

import android.app.Application;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nojom.client.R;
import com.nojom.client.adapter.JobTitleAdapter;
import com.nojom.client.databinding.ActivityJobPostTitleBinding;
import com.nojom.client.databinding.ActivityOfferTitleBinding;
import com.nojom.client.model.AgentProfile;
import com.nojom.client.model.Attachment;
import com.nojom.client.model.ExpertLawyers;
import com.nojom.client.model.ServicesModel;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class HirePostJobTitleActivityVM extends AndroidViewModel implements JobTitleAdapter.OnClickTitle {
    private ActivityOfferTitleBinding binding;
    private BaseActivity activity;
    private String serviceId;
    private List<ServicesModel.JobTitle> jobTitleList;
    private String moSkilIDs, moSkillNames, moBudget, moClientRate, selectedDeadline, payType, lawyerService, description, attachLocalFile;
    private int moClientRateId;
    private List<ExpertLawyers.Data> expertUserList;
    private ArrayList<Attachment> fileList;
    private String describe;
    private AgentProfile agentData;

    public HirePostJobTitleActivityVM(Application application, ActivityOfferTitleBinding postJobBinding, BaseActivity postJobActivity) {
        super(application);
        this.activity = postJobActivity;
        this.binding = postJobBinding;
    }

    public boolean isValid() {
        if (TextUtils.isEmpty(binding.etTitle.getText().toString().trim())) {
            activity.toastMessage(activity.getString(R.string.please_enter_valid_title));
            return false;
        }
        return true;
    }

    public void initData() {
        binding.llProgress.setVisibility(View.VISIBLE);

        if (activity.getIntent() != null) {
            describe = activity.getIntent().getStringExtra(Constants.DESCRIBE);
            attachLocalFile = activity.getIntent().getStringExtra(Constants.ATTACH_LOCAL_FILE);
            moClientRateId = activity.getIntent().getIntExtra(Constants.CLIENT_RATE_ID, 0);
            moClientRate = activity.getIntent().getStringExtra(Constants.CLIENT_RATE);
            moBudget = activity.getIntent().getStringExtra(Constants.BUDGET);
            selectedDeadline = activity.getIntent().getStringExtra("deadline");
            agentData = (AgentProfile) activity.getIntent().getSerializableExtra(AGENT_PROFILE_DATA);
        }

        binding.tvTitle.setText(Utils.getColorString(activity, activity.getString(R.string.job_post_title),
                activity.getString(R.string._title), R.color.colorPrimary));

        binding.btnLastStep.setOnClickListener(v -> {
            Intent intent = new Intent(activity, HireOfferSummaryActivity.class);
            intent.putExtra(Constants.DESCRIBE, describe);
            intent.putExtra(Constants.ATTACH_LOCAL_FILE, attachLocalFile);
            intent.putExtra("deadline", selectedDeadline);
            intent.putExtra(Constants.BUDGET, moBudget);
            intent.putExtra(Constants.CLIENT_RATE_ID, moClientRateId);
            intent.putExtra(Constants.CLIENT_RATE, moClientRate);
            intent.putExtra("title", binding.etTitle.getText().toString());
            intent.putExtra(Constants.AGENT_PROFILE_DATA, agentData);
            activity.startActivity(intent);
        });

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

    void onResumeMethod() {
        setProgressView(0.99f);
    }

    void setProgressView(float progress) {
        binding.llProgress.setVisibility(View.VISIBLE);

        float remainProgress = 1.0f - progress;
        LinearLayout.LayoutParams lParams = (LinearLayout.LayoutParams) binding.progressView.getLayoutParams();
        lParams.weight = progress;
        LinearLayout.LayoutParams rParams = (LinearLayout.LayoutParams) binding.blankView.getLayoutParams();
        rParams.weight = remainProgress;

        binding.progressView.setLayoutParams(lParams);
        binding.blankView.setLayoutParams(rParams);
    }

    void hideProgressView() {
        binding.llProgress.setVisibility(View.GONE);
    }


    @Override
    public void onClickTitle(ServicesModel.JobTitle title) {
        binding.etTitle.setText(title.getTitle(activity.getLanguage()));
    }

}
