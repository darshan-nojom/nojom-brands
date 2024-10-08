package com.nojom.client.ui.projects;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
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
import com.nojom.client.fragment.projects.ProjectDetailsFragment;
import com.nojom.client.fragment.projects.ProjectPaymentFragment;
import com.nojom.client.fragment.projects.ProjectRateFragment;
import com.nojom.client.fragment.projects.ProjectStatusFragment;
import com.nojom.client.fragment.projects.ProjectSubmitFragment;
import com.nojom.client.model.ProjectByID;
import com.nojom.client.segment.SegmentedButtonGroup;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.clientprofile.ClientReviewActivity;
import com.nojom.client.ui.clientprofile.PostJobActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;
import com.willy.ratingbar.ScaleRatingBar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;

import static android.app.Activity.RESULT_OK;
import static com.nojom.client.util.Constants.API_CLOSE_JOB_POST;
import static com.nojom.client.util.Constants.API_JOB_DETAILS;

class ProjectDetailsActivityVM extends AndroidViewModel implements View.OnClickListener, RequestResponseListener {
    boolean isNeedToRefresh;
    boolean isState;
    private BaseActivity activity;
    private ActivityProjectDetailsBinding binding;
    private ProjectByID projectData;
    private boolean isRefresh = false;
    private boolean isCancelledJob = false;
    private int RC_RATING = 10101;
    private ProjectRateFragment projectRateFragment;
    private Dialog closeProjectDialog;
    private CircularProgressBar progressBarCloseProject;
    private TextView tvCloseProject;

    ProjectDetailsActivityVM(Application application, ActivityProjectDetailsBinding detailsBinding, BaseActivity projectGigDetailsActivity) {
        super(application);
        binding = detailsBinding;
        activity = projectGigDetailsActivity;
        initData();
    }

    private void initData() {
        binding.imgBack.setOnClickListener(this);
        binding.tvCloseProject.setOnClickListener(this);
        if (activity.getIntent() != null) {
            projectData = (ProjectByID) activity.getIntent().getSerializableExtra(Constants.PROJECT);
            isState = activity.getIntent().getBooleanExtra("state", false);
        }
        if (projectData != null) {
            setUpViews();
        }

        Utils.trackAppsFlayerEvent(activity, "Project_Detail_Screen");
    }

    void getProjectById(boolean isNeedToRefresh) {
        if (!activity.isNetworkConnected()) {
            return;
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("job_id", projectData.id + "");
        this.isNeedToRefresh = isNeedToRefresh;
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_JOB_DETAILS, true, map);
    }

    public ProjectByID getProjectData() {
        return projectData;
    }

    public boolean isCancelledJob() {
        return isCancelledJob;
    }

    void onResumeMethod() {
        if (isRefresh) {
            isRefresh = false;
            getProjectById(false);
        } else {
            int orderId = Preferences.readInteger(activity, Constants.EDIT_PROJECT_ID, 0);
            if (orderId != 0) {
                Preferences.writeInteger(activity, Constants.EDIT_PROJECT_ID, 0);
                getProjectById(false);
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
        adapter.addFrag(new ProjectStatusFragment(), activity.getString(R.string.proposals));
        switch (projectData.jpstateId) {
            case Constants.SUBMIT_WAITING_FOR_PAYMENT:
                adapter.addFrag(new ProjectSubmitFragment(), activity.getString(R.string.submit));
                break;
            case Constants.COMPLETED:
                adapter.addFrag(projectRateFragment = new ProjectRateFragment(), activity.getString(R.string.rate));
                adapter.addFrag(new ProjectSubmitFragment(), activity.getString(R.string.submit));
                break;
        }
        adapter.addFrag(new ProjectDetailsFragment(), activity.getString(R.string.details));
        adapter.addFrag(new ProjectPaymentFragment(), activity.getString(R.string.pay));
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
        if (url.equalsIgnoreCase(API_JOB_DETAILS)) {
            ProjectByID project = ProjectByID.getProjectById(responseBody);
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

        switch (projectData.jpstateId) {
            case Constants.BIDDING:
                binding.segmentGroupBidding.setVisibility(View.VISIBLE);
                binding.tvCloseProject.setVisibility(View.VISIBLE);
                break;
            case Constants.WAITING_FOR_AGENT_ACCEPTANCE:
                binding.segmentWaitingDeposit.setVisibility(View.VISIBLE);
                binding.tvCloseProject.setVisibility(View.VISIBLE);
                break;
            case Constants.BANK_TRANSFER_REVIEW:
            case Constants.WAITING_FOR_DEPOSIT:
                binding.segmentGroupProgress.setVisibility(View.VISIBLE);
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
                binding.tvCloseProject.setVisibility(View.VISIBLE);
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
            tvCloseProject.setVisibility(View.VISIBLE);
        }
        activity.isClickableView = false;
    }

    void setPagerPosition(int position) {
        binding.viewpager.setCurrentItem(position);
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

        //String username = getProperName(projectData.jobPostBids.firstName, projectData.jobPostBids.lastName, projectData.jobPostBids.username);
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
            Intent i = new Intent(activity, ClientReviewActivity.class);
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

        tvBidCount.setText(projectData.bidsCount > 1 ? projectData.bidsCount + " " + activity.getString(R.string.bids) : projectData.bidsCount + " " + activity.getString(R.string.bid));
        tvTitle.setText(projectData.title);
        if (projectData.clientRateId != null && projectData.clientRateId == 0 && projectData.jobPostBudget != null) {
            tvBudget.setText(String.format(Locale.US, activity.getCurrency().equals("SAR") ? activity.getString(R.string.s_sar) : activity.getString(R.string.dollar)+"%s", projectData.jobPostBudget.budget));
        } else {
            if (!TextUtils.isEmpty(projectData.rangeTo)) {
                tvBudget.setText(String.format(Locale.US, activity.getCurrency().equals("SAR") ? activity.getString(R.string.s_sar_s_sar) : "$%s - $%s", projectData.rangeFrom, projectData.rangeTo));
            } else if (!TextUtils.isEmpty(projectData.rangeFrom)) {
                tvBudget.setText(String.format(Locale.US, activity.getCurrency().equals("SAR") ? activity.getString(R.string.s_sar) : activity.getString(R.string.dollar)+"%s", projectData.rangeFrom));
            } else if (projectData.jobPostBudget != null && projectData.jobPostBudget.budget != null) {
                tvBudget.setText(String.format(Locale.US, activity.getCurrency().equals("SAR") ? activity.getString(R.string.s_sar) : activity.getString(R.string.dollar)+"%s", projectData.jobPostBudget.budget));
            } else {
                tvBudget.setText(activity.getString(R.string.Free));
            }
        }

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
                getProjectById(true);
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
