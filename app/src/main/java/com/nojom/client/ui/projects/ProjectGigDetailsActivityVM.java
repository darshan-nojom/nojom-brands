package com.nojom.client.ui.projects;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.AndroidViewModel;
import androidx.viewpager.widget.ViewPager;

import com.nojom.client.R;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivityProjectDetailsBinding;
import com.nojom.client.fragment.projects.ProjectGigDetailsFragment;
import com.nojom.client.fragment.projects.ProjectGigPaymentFragment;
import com.nojom.client.fragment.projects.ProjectGigRateFragment;
import com.nojom.client.fragment.projects.ProjectGigStatusFragment;
import com.nojom.client.fragment.projects.ProjectGigSubmitFragment;
import com.nojom.client.model.ProjectGigByID;
import com.nojom.client.segment.SegmentedButtonGroup;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.clientprofile.ClientGigReviewActivity;
import com.nojom.client.ui.clientprofile.PostJobActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;
import com.willy.ratingbar.ScaleRatingBar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;

import static android.app.Activity.RESULT_OK;
import static com.nojom.client.util.Constants.API_CLOSE_JOB_POST;
import static com.nojom.client.util.Constants.API_GET_CONTRACT_DETAILS;
import static com.nojom.client.util.Constants.API_GET_CUSTOM_CONTRACT_DETAILS;

class ProjectGigDetailsActivityVM extends AndroidViewModel implements View.OnClickListener, RequestResponseListener {
    public String gigType = "";
    boolean isNeedToRefresh;
    boolean isState;
    private BaseActivity activity;
    private ActivityProjectDetailsBinding binding;
    private ProjectGigByID projectData;
    private boolean isRefresh = false;
    private boolean isCancelledJob = false;
    private int RC_RATING = 10101;
    private ProjectGigRateFragment projectRateFragment;
    private Dialog closeProjectDialog;
    private CircularProgressBar progressBarCloseProject;
    private TextView tvCloseProject;

    ProjectGigDetailsActivityVM(Application application, ActivityProjectDetailsBinding detailsBinding, BaseActivity projectDetailsActivity) {
        super(application);
        binding = detailsBinding;
        activity = projectDetailsActivity;
        initData();
    }

    private void initData() {
        binding.imgBack.setOnClickListener(this);
        binding.tvCloseProject.setOnClickListener(this);
        if (activity.getIntent() != null) {
            projectData = (ProjectGigByID) activity.getIntent().getSerializableExtra(Constants.PROJECT_GIG);
            gigType = activity.getIntent().getStringExtra("gigType");
            isState = activity.getIntent().getBooleanExtra("state", false);
        }
        if (projectData != null) {
            setUpViews();
        }
        Utils.trackAppsFlayerEvent(activity, "Project_Gig_Detail_Screen");
    }

    void getProjectGigById(boolean isNeedToRefresh) {
        if (!activity.isNetworkConnected()) {
            return;
        }

        this.isNeedToRefresh = isNeedToRefresh;
        ApiRequest apiRequest = new ApiRequest();
        if (gigType.equalsIgnoreCase("1") || gigType.equalsIgnoreCase("3")) {
            apiRequest.apiRequest(this, activity, API_GET_CUSTOM_CONTRACT_DETAILS + "/" + projectData.id, false, null);
        } else {
            apiRequest.apiRequest(this, activity, API_GET_CONTRACT_DETAILS + "/" + projectData.id, false, null);
        }
    }

    public ProjectGigByID getProjectData() {
        return projectData;
    }

    public String getGigType() {
        return gigType;
    }

    public boolean isCancelledJob() {
        return isCancelledJob;
    }

    void onResumeMethod() {
        if (isRefresh) {
            isRefresh = false;
            getProjectGigById(false);
        } else {
            int orderId = Preferences.readInteger(activity, Constants.EDIT_PROJECT_ID, 0);
            if (orderId != 0) {
                Preferences.writeInteger(activity, Constants.EDIT_PROJECT_ID, 0);
                getProjectGigById(false);
            }
        }
    }

    private void setSegmentGroup(SegmentedButtonGroup... groups) {
        for (SegmentedButtonGroup group : groups) {
            group.setPosition(0, true);
            group.setOnPositionChangedListener(position -> binding.viewpager.setCurrentItem(position));
        }
    }

    private void setSegmentPosition(int position, SegmentedButtonGroup... groups) {
        for (SegmentedButtonGroup group : groups) {
            group.setPosition(position, true);
        }
    }

    private void setupPager() {
        setupViewPager(binding.viewpager);

        setSegmentGroup(binding.segmentGroupBidding, binding.segmentWaitingDeposit, binding.segmentGroupProgress, binding.segmentSubmitPayment, binding.segmentGroupComplete);

        binding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                setSegmentPosition(i, binding.segmentGroupBidding, binding.segmentWaitingDeposit, binding.segmentGroupProgress, binding.segmentSubmitPayment, binding.segmentGroupComplete);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(activity.getSupportFragmentManager());
        adapter.addFrag(new ProjectGigStatusFragment(), activity.getString(R.string.proposals));
        switch (projectData.gigStateID) {
            case Constants.SUBMIT_WAITING_FOR_PAYMENT:
                adapter.addFrag(new ProjectGigSubmitFragment(), activity.getString(R.string.submit));
                break;
            case Constants.COMPLETED:
                adapter.addFrag(projectRateFragment = new ProjectGigRateFragment(), activity.getString(R.string.rate));
                adapter.addFrag(new ProjectGigSubmitFragment(), activity.getString(R.string.submit));
                break;
        }
        adapter.addFrag(new ProjectGigDetailsFragment(), activity.getString(R.string.details));
        adapter.addFrag(new ProjectGigPaymentFragment(), activity.getString(R.string.pay));
        viewPager.setPageMargin(20);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                activity.onBackPressed();
                break;
            case R.id.tv_close_project:
                if (isCancelledJob) {
                    Preferences.writeBoolean(activity, Constants.DUPLICATE_PROJECT, true);

                    Intent i = new Intent(activity, PostJobActivity.class);
                    i.putExtra(Constants.DUPLICATE_PROJECT, true);
                    i.putExtra(Constants.PROJECT, projectData);
                    activity.startActivity(i);
                } else {
                    showCloseProjectDialog();
                }
                break;
        }
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        if (url.equalsIgnoreCase(API_GET_CONTRACT_DETAILS + "/" + projectData.id) || url.equalsIgnoreCase(API_GET_CUSTOM_CONTRACT_DETAILS + "/" + projectData.id)) {
            ProjectGigByID project = ProjectGigByID.getProjectGigById(responseBody);
            binding.segmentGroupBidding.setVisibility(View.GONE);
            binding.segmentWaitingDeposit.setVisibility(View.GONE);
            binding.segmentGroupProgress.setVisibility(View.GONE);
            binding.segmentSubmitPayment.setVisibility(View.GONE);
            binding.segmentGroupComplete.setVisibility(View.GONE);
            if (project != null) {
                projectData = project;
                setUpViews();
            } else {
                isRefresh = true;
                binding.viewpager.setVisibility(View.GONE);
                binding.tvNoData.setVisibility(View.VISIBLE);
            }
        } else if (url.equalsIgnoreCase(API_CLOSE_JOB_POST)) {
            activity.isClickableView = false;
            closeProjectDialog.dismiss();
            activity.gotoMainActivity(Constants.TAB_JOB_LIST);
        }
    }

    private void setUpViews() {
        binding.viewpager.setVisibility(View.VISIBLE);
        binding.tvNoData.setVisibility(View.GONE);

        switch (projectData.gigStateID) {
            case Constants.BIDDING:
                binding.segmentGroupBidding.setVisibility(View.VISIBLE);
                binding.tvCloseProject.setVisibility(View.GONE);
                break;
            case Constants.WAITING_FOR_AGENT_ACCEPTANCE:
                binding.segmentWaitingDeposit.setVisibility(View.VISIBLE);
                binding.tvCloseProject.setVisibility(View.GONE);
                break;
            case Constants.BANK_TRANSFER_REVIEW:
            case Constants.WAITING_FOR_DEPOSIT:
                binding.segmentWaitingDeposit.setVisibility(View.VISIBLE);
                break;
            case Constants.IN_PROGRESS:
                binding.segmentGroupProgress.setVisibility(View.VISIBLE);
                binding.tvCloseProject.setVisibility(View.GONE);
                binding.tvCloseProject.setText(activity.getString(R.string.complete_project));
                binding.tvCloseProject.setTextColor(ContextCompat.getColor(activity, R.color.lightgreen));
                break;
            case Constants.SUBMIT_WAITING_FOR_PAYMENT:
                binding.segmentSubmitPayment.setVisibility(View.VISIBLE);
                break;
            case Constants.COMPLETED:
                binding.segmentGroupComplete.setVisibility(View.VISIBLE);
                if (projectData.isAgentReview != null && projectData.isAgentReview == 0) {
                    giveRatingDialog();
                }
                break;
            case Constants.CANCELLED:
            case Constants.REFUNDED:
                binding.segmentGroupBidding.setVisibility(View.VISIBLE);
                binding.tvCloseProject.setVisibility(View.GONE);
                isCancelledJob = true;
                binding.tvCloseProject.setText(activity.getString(R.string.duplicate_job));
                binding.tvCloseProject.setTextColor(ContextCompat.getColor(activity, R.color.lightgreen));
                break;
            default:
                binding.segmentGroupBidding.setVisibility(View.VISIBLE);
                binding.tvCloseProject.setText(activity.getString(R.string.close_job));
                binding.tvCloseProject.setTextColor(ContextCompat.getColor(activity, R.color.red));
                break;
        }

        if (!isNeedToRefresh) {
            setupPager();
        }
        if (isNeedToRefresh) {//need to refresh rate fragment after give rating to client
            if (projectRateFragment != null) {
                projectRateFragment.refreshPage();
            }
        }
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        binding.viewpager.setVisibility(View.GONE);
        binding.tvNoData.setVisibility(View.VISIBLE);
        if (url.equalsIgnoreCase(API_CLOSE_JOB_POST)) {
            progressBarCloseProject.setVisibility(View.GONE);
            tvCloseProject.setVisibility(View.GONE);
        }
        activity.isClickableView = false;
    }

    void setPagerPosition(int position) {
        binding.viewpager.setCurrentItem(position);
    }


    private void showCloseProjectDialog() {
        final Dialog dialog = new Dialog(activity);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_close_project);
        dialog.setCancelable(true);
        closeProjectDialog = dialog;
        TextView tvTitle = dialog.findViewById(R.id.tv_title);
        TextView tvBudget = dialog.findViewById(R.id.tv_budget);
        TextView tvBidCount = dialog.findViewById(R.id.tv_bid_count);
        tvCloseProject = dialog.findViewById(R.id.tv_close_project);
        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        progressBarCloseProject = dialog.findViewById(R.id.progress_bar);

        tvBidCount.setVisibility(View.GONE);
        tvTitle.setText(projectData.gigTitle);

        tvCancel.setOnClickListener(v -> dialog.dismiss());

        tvCloseProject.setOnClickListener(v -> {
            progressBarCloseProject.setVisibility(View.VISIBLE);
            tvCloseProject.setVisibility(View.INVISIBLE);
            activity.isClickableView = true;
            closeProject();
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.TOP;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }

    @SuppressLint("StringFormatInvalid")
    private void giveRatingDialog() {
        final Dialog dialog = new Dialog(activity, R.style.Theme_AppCompat_Dialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_rate_me);
        dialog.setCancelable(true);

        TextView tvReleasedPayment = dialog.findViewById(R.id.tv_user_release_payment);
        TextView tvHowsUser = dialog.findViewById(R.id.tv_hows_user);
        ScaleRatingBar ratingBar = dialog.findViewById(R.id.ratingbar);
        TextView tvNo = dialog.findViewById(R.id.tv_no);

        String username;
        if (projectData != null && projectData.agentDetails != null && !projectData.agentDetails.firstName.isEmpty())
            username = projectData.agentDetails.firstName;
        else
            username = "";

        tvReleasedPayment.setText(activity.getString(R.string.user_done_with_job, username));
        String s = activity.getString(R.string.how_was_user, username);
        String[] words = {username};
        String[] fonts = {Constants.SFTEXT_BOLD};

        tvHowsUser.setText(Utils.getBoldString(activity, s, fonts, null, words));

        tvNo.setOnClickListener(v -> dialog.dismiss());

        ratingBar.setOnRatingChangeListener((baseRatingBar, v) -> {
            dialog.dismiss();
            Intent i = new Intent(activity, ClientGigReviewActivity.class);
            i.putExtra(Constants.USER_DATA, projectData);
            activity.startActivityForResult(i, RC_RATING);
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.gravity = Gravity.CENTER;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }

    private void closeProject() {
        if (!activity.isNetworkConnected())
            return;

        HashMap<String, String> map = new HashMap<>();
        map.put("job_post_id", projectData.id + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_CLOSE_JOB_POST, true, map);
    }

    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == RC_RATING) {
                getProjectGigById(true);
            }
        }
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {
        final List<String> mFragmentTitleList = new ArrayList<>();
        private final List<Fragment> mFragmentList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @NotNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }
}
