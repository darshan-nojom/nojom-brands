package com.nojom.client.ui.clientprofile;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;

import com.nojom.client.R;
import com.nojom.client.databinding.ActivityPostJobBinding;
import com.nojom.client.fragment.postjob.ChooseDeveloperFragment;
import com.nojom.client.fragment.postjob.DeadlineFragment;
import com.nojom.client.fragment.postjob.DescribeFragment;
import com.nojom.client.fragment.postjob.PostJobFragment;
import com.nojom.client.fragment.postjob.PriceRateFragment;
import com.nojom.client.fragment.postjob.SelectServiceFragment;
import com.nojom.client.model.ProjectByID;
import com.nojom.client.model.ServicesModel;
import com.nojom.client.model.SocialPlatformModel;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;

import java.util.ArrayList;
import java.util.List;

class PostJobActivityVM extends AndroidViewModel {
    private ActivityPostJobBinding binding;
    @SuppressLint("StaticFieldLeak")
    private BaseActivity activity;
    private boolean isEditJob = false;
    private boolean isDuplicateJob = false;
    private int projectId;
    private boolean isRepostJob, isAllCategory;
    private boolean isFromHome;
    private String serviceId, lawyerService;
    private ProjectByID projectData = null;
    private String selectedTab;

    PostJobActivityVM(Application application, ActivityPostJobBinding postJobBinding, BaseActivity postJobActivity) {
        super(application);
        binding = postJobBinding;
        activity = postJobActivity;
        initData();
    }

    private void initData() {
        selectedTab = activity.getIntent().getStringExtra("tab");

        //platform list
        ((PostJobActivity) activity).platformList = new ArrayList<>();
        ((PostJobActivity) activity).platformList.add(new SocialPlatformModel.Data(activity.getString(R.string.all_platforms), 0));//add for all platform fist index
        ((PostJobActivity) activity).platformList.addAll(new ArrayList<>(Preferences.getSocialPlatforms(activity)));//social platforms
        //category list
        List<ServicesModel.Data> servicesSubList = new ArrayList<>(Preferences.getCategoryV2(activity));//sub category list
        ((PostJobActivity) activity).subServiceList = new ArrayList<>();
        ((PostJobActivity) activity).subServiceList.addAll(servicesSubList);
//        for (ServicesModel.Data serviceData : servicesSubList) {
//            if (serviceData.id == 4352) {
//                if (serviceData.services != null && ((PostJobActivity) activity).subServiceList != null) {
//                    ((PostJobActivity) activity).subServiceList.addAll(serviceData.services);
//                }
//                break;
//            }
//        }

        binding.toolBack.imgBack.setOnClickListener(view -> {
            activity.finish();
        });


        if (activity.getIntent() != null) {
            isEditJob = activity.getIntent().getBooleanExtra(Constants.IS_EDIT, false);
            isDuplicateJob = activity.getIntent().getBooleanExtra(Constants.DUPLICATE_PROJECT, false);
            isRepostJob = activity.getIntent().getBooleanExtra(Constants.REPOST_PROJECT, false);
            projectId = activity.getIntent().getIntExtra(Constants.PROJECT_ID, 0);

            projectData = (ProjectByID) activity.getIntent().getSerializableExtra(Constants.PROJECT);//in case of edit project

            isFromHome = activity.getIntent().getBooleanExtra(Constants.FROM_HOME, false);
            //this will use when user click on category from HOME SCREEN
            serviceId = activity.getIntent().getStringExtra(Constants.PLATFORM_ID);
            lawyerService = activity.getIntent().getStringExtra(Constants.PLATFORM_NAME);
            isAllCategory = activity.getIntent().getBooleanExtra("allcategory", false);//when user comes from HOME screen ['All','Post a job'] and Expertise screen [Post a job(its free)] at that time this flag is set
        }

        if (TextUtils.isEmpty(selectedTab)) {//
            if (isEditJob) {
                activity.addFragment(PostJobFragment.newInstance(true, false, false, projectId, projectData));
            } else if (isDuplicateJob) {
                activity.addFragment(PostJobFragment.newInstance(false, true, false, projectId, projectData));
            } else if (isRepostJob) {
                activity.addFragment(PostJobFragment.newInstance(false, false, true, projectId, projectData));
            } else if (isFromHome) {

                activity.replaceFragment(new SelectServiceFragment());

                Fragment fragmentA = new ChooseDeveloperFragment();
                Bundle bundle = new Bundle();
                fragmentA.setArguments(bundle);
                activity.replaceFragment(fragmentA);
            } else if (isAllCategory) {//comes from home screen and Find Expert screen
                activity.replaceFragment(new SelectServiceFragment());
            } else {//May when click on Rehire,Edit, Hire case etc.
                activity.replaceFragment(new SelectServiceFragment());
            }
        } else {//redirect to specific fragment
            Fragment fragmentA;
            Bundle bundle;

            String skillIdDe = activity.getIntent().getStringExtra(Constants.SKILL_IDS);
            String skillNameDe = activity.getIntent().getStringExtra(Constants.SKILL_NAMES);
            String payTypeDe = activity.getIntent().getStringExtra(Constants.PAY_TYPE);
            String crDe = activity.getIntent().getStringExtra(Constants.CLIENT_RATE);
            int crIdDe = activity.getIntent().getIntExtra(Constants.CLIENT_RATE_ID, 0);
            String budgetDe = activity.getIntent().getStringExtra(Constants.BUDGET);
            String deadl = activity.getIntent().getStringExtra("deadline");
            String desc = activity.getIntent().getStringExtra(Constants.DESCRIBE);

            switch (selectedTab) {
                case "serv":
                    fragmentA = new ChooseDeveloperFragment();
                    bundle = new Bundle();
                    bundle.putString(Constants.PLATFORM_ID, serviceId + "");
                    bundle.putString(Constants.SKILL_IDS, skillIdDe);
                    bundle.putString(Constants.SKILL_NAMES, skillNameDe);
                    bundle.putString(Constants.PAY_TYPE, payTypeDe);
                    bundle.putString(Constants.CLIENT_RATE, crDe);
                    bundle.putInt(Constants.CLIENT_RATE_ID, crIdDe);
                    bundle.putString(Constants.BUDGET, budgetDe);
                    bundle.putString("deadline", deadl);
                    bundle.putString(Constants.PLATFORM_NAME, lawyerService + "");
                    bundle.putString(Constants.DESCRIBE, desc);
                    fragmentA.setArguments(bundle);
                    activity.replaceFragment(fragmentA);
                    break;
                case "rate":
//                    String skillId = activity.getIntent().getStringExtra(Constants.SKILL_IDS);
//                    String skillName = activity.getIntent().getStringExtra(Constants.SKILL_NAMES);

                    Preferences.writeBoolean(activity, Constants.IS_FIXED_PRICE, true);
                    fragmentA = new PriceRateFragment();

                    bundle = new Bundle();
                    bundle.putString(Constants.PLATFORM_ID, serviceId + "");
                    bundle.putString(Constants.SKILL_IDS, skillIdDe);
                    bundle.putString(Constants.SKILL_NAMES, skillNameDe);
                    bundle.putString(Constants.PAY_TYPE, payTypeDe);
                    bundle.putString(Constants.CLIENT_RATE, crDe);
                    bundle.putInt(Constants.CLIENT_RATE_ID, crIdDe);
                    bundle.putString(Constants.BUDGET, budgetDe);
                    bundle.putString("deadline", deadl);
                    bundle.putString(Constants.PLATFORM_NAME, lawyerService + "");
                    bundle.putString(Constants.DESCRIBE, desc);

                    fragmentA.setArguments(bundle);
                    activity.replaceFragment(fragmentA);
                    break;
                case "deadline":
                   /* String skillIdD = activity.getIntent().getStringExtra(Constants.SKILL_IDS);
                    String skillNameD = activity.getIntent().getStringExtra(Constants.SKILL_NAMES);
                    String payType = activity.getIntent().getStringExtra(Constants.PAY_TYPE);
                    String cr = activity.getIntent().getStringExtra(Constants.CLIENT_RATE);
                    int crId = activity.getIntent().getIntExtra(Constants.CLIENT_RATE_ID, 0);
                    String budget = activity.getIntent().getStringExtra(Constants.BUDGET);
                    String dead = activity.getIntent().getStringExtra("deadline");
                    String desc1 = activity.getIntent().getStringExtra(Constants.DESCRIBE);*/

                    fragmentA = new DeadlineFragment();
                    bundle = new Bundle();
                    bundle.putString(Constants.PLATFORM_ID, serviceId + "");
                    bundle.putString(Constants.PLATFORM_NAME, lawyerService + "");
                    bundle.putString(Constants.SKILL_IDS, skillIdDe);
                    bundle.putString(Constants.SKILL_NAMES, skillNameDe);
                    bundle.putString(Constants.PAY_TYPE, payTypeDe);
                    bundle.putString(Constants.CLIENT_RATE, crDe);
                    bundle.putInt(Constants.CLIENT_RATE_ID, crIdDe);
                    bundle.putString(Constants.BUDGET, budgetDe);
                    bundle.putString("deadline", deadl);
                    bundle.putString(Constants.DESCRIBE, desc);
                    fragmentA.setArguments(bundle);
                    activity.replaceFragment(fragmentA);
                    break;
                case "desc":
                    fragmentA = new DescribeFragment();
                    bundle = new Bundle();
                    bundle.putString(Constants.PLATFORM_ID, serviceId + "");
                    bundle.putString(Constants.SKILL_IDS, skillIdDe);
                    bundle.putString(Constants.SKILL_NAMES, skillNameDe);
                    bundle.putString(Constants.PAY_TYPE, payTypeDe);
                    bundle.putString(Constants.CLIENT_RATE, crDe);
                    bundle.putInt(Constants.CLIENT_RATE_ID, crIdDe);
                    bundle.putString(Constants.BUDGET, budgetDe);
                    bundle.putString("deadline", deadl);
                    bundle.putString(Constants.PLATFORM_NAME, lawyerService + "");
                    bundle.putString(Constants.DESCRIBE, desc);
                    fragmentA.setArguments(bundle);
                    activity.replaceFragment(fragmentA);
                    break;
            }

        }
        if (isEditJob || isDuplicateJob) {
            binding.toolBack.tvNext.setVisibility(View.GONE);
        }
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

    public void onBackPressed() {
        int backStackEntryCount = activity.getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount == 0) {
            if (!isEditJob && !isDuplicateJob && !isRepostJob) {
                activity.onBackPressedEvent();
            } else {
                activity.finish();
            }
        } else {
            activity.getSupportFragmentManager().popBackStack();
        }
    }
}
