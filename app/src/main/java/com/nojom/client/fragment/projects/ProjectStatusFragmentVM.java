package com.nojom.client.fragment.projects;

import static com.nojom.client.util.Constants.API_CANCEL_CONTRACT;
import static com.nojom.client.util.Constants.API_GET_PROFILE_INFO;
import static com.nojom.client.util.Constants.API_JOB_POST_BIDLIST;

import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.adapter.ProposalsAdapter;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.FragmentProjectStatusBinding;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.model.AgentProfile;
import com.nojom.client.model.ExpertDetail;
import com.nojom.client.model.ExpertLawyers;
import com.nojom.client.model.Profile;
import com.nojom.client.model.ProjectByID;
import com.nojom.client.model.Proposals;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.chat.ChatMessagesActivity;
import com.nojom.client.ui.clientprofile.MilestoneActivity;
import com.nojom.client.ui.projects.InfluencerProfileActivityCopy;
import com.nojom.client.ui.projects.ProjectDetailsActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.EndlessRecyclerViewScrollListener;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;

public class ProjectStatusFragmentVM extends AndroidViewModel implements View.OnClickListener, RequestResponseListener,
        ProposalsAdapter.OnClickProfileListener, BaseActivity.ExpertInfoListener {
    private FragmentProjectStatusBinding binding;
    private BaseFragment fragment;
    private ProposalsAdapter proposalsAdapter;
    private List<Proposals.Data> proposalList;
    private boolean isHired = false, viewProfile;
    private ProjectByID projectData;
    private Profile profileData;
    private EndlessRecyclerViewScrollListener scrollListener;
    private long tempSec;
    private Timer t;
    private CircularProgressBar pbCancelFreelancer;
    private TextView tvCancelFreelancer;
    private Dialog cancelFreelancerDialog;
    private int selectedPos;
    private Proposals.Data userData;


    ProjectStatusFragmentVM(Application application, FragmentProjectStatusBinding fragmentProjectStatusBinding, BaseFragment projectStatusFragment) {
        super(application);
        binding = fragmentProjectStatusBinding;
        fragment = projectStatusFragment;
        initData();
    }

    private void initData() {
        binding.tvCancelFreelancer.setOnClickListener(this);
        binding.rvHire.setOnClickListener(this);
        binding.tvChat1.setOnClickListener(this);
        binding.proposal.tvChat.setOnClickListener(this);
        binding.rehire.tvChat3.setOnClickListener(this);
        binding.proposal.rlProfile.setOnClickListener(this);
        binding.rehire.rlProfile1.setOnClickListener(this);
        binding.rehire.tvHireUser.setOnClickListener(this);
        binding.proposal.tvHire.setOnClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(fragment.activity);
        binding.rvHire.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.rvHire.getContext(),
                linearLayoutManager.getOrientation());
        binding.rvHire.addItemDecoration(dividerItemDecoration);

        if (fragment.activity != null) {
            projectData = ((ProjectDetailsActivity) fragment.activity).getProjectData();
            profileData = Preferences.getProfileData(fragment.activity);
        }

        if (projectData != null) {
            int PAID = 4;
            int IN_PROGRESS = 3;
            switch (projectData.jpstateId) {
                case Constants.BIDDING:
                    binding.llHire.setVisibility(View.VISIBLE);
                    binding.llUserRehire.setVisibility(View.GONE);
                    binding.noData.tvNoTitle.setText(fragment.getString(R.string.no_proposals));
                    binding.noData.tvNoDescription.setText(fragment.getString(R.string.no_proposals_desc));

                    binding.tvTitle.setText(fragment.getString(R.string.hire_freelancer));

                    fragment.activity.runOnUiThread(() -> getProposals(1));

                    break;
                case Constants.WAITING_FOR_AGENT_ACCEPTANCE:
                    binding.llUserRehire.setVisibility(View.GONE);
                    binding.tvTitle.setText(fragment.getString(R.string.my_freelancer));
                    binding.llWaitingForDeposit.setVisibility(View.VISIBLE);

                    binding.proposal.tvChat.setTextColor(ContextCompat.getColor(fragment.activity, R.color.colorPrimary));
                    binding.proposal.tvHire.setBackground(ContextCompat.getDrawable(fragment.activity, R.drawable.blue_rounded_corner_10));
                    binding.proposal.tvChat.setBackground(ContextCompat.getDrawable(fragment.activity, R.drawable.chat_blue_border));
                    binding.proposal.tvHire.setText(fragment.getString(R.string.waiting_for_agent_acceptance));
                    binding.tvCancelFreelancer.setText(Utils.getColorString(fragment.activity, fragment.getString(R.string.cancel_freelancer),
                            fragment.getString(R.string.cancel), R.color.red_dark));
                    break;
                case Constants.WAITING_FOR_DEPOSIT:
                    binding.llUserRehire.setVisibility(View.GONE);
                    isHired = true;

                    binding.tvTitle.setText(fragment.getString(R.string.my_freelancer));
                    binding.llWaitingForDeposit.setVisibility(View.VISIBLE);

                    binding.proposal.tvChat.setTextColor(ContextCompat.getColor(fragment.activity, R.color.colorPrimary));
                    binding.proposal.tvHire.setBackground(ContextCompat.getDrawable(fragment.activity, R.drawable.blue_rounded_corner_10));
                    binding.proposal.tvChat.setBackground(ContextCompat.getDrawable(fragment.activity, R.drawable.chat_blue_border));
                    binding.proposal.tvHire.setText(fragment.getString(R.string.deposit_to_get_started));
                    binding.tvCancelFreelancer.setText(Utils.getColorString(fragment.activity, fragment.getString(R.string.cancel_freelancer),
                            fragment.getString(R.string.cancel), R.color.red_dark));
                    break;
                case Constants.BANK_TRANSFER_REVIEW:
                    binding.llUserRehire.setVisibility(View.GONE);
                    isHired = false;

                    binding.tvTitle.setText(fragment.getString(R.string.my_freelancer));
                    binding.llWaitingForDeposit.setVisibility(View.VISIBLE);

                    binding.proposal.tvChat.setTextColor(ContextCompat.getColor(fragment.activity, R.color.colorPrimary));
                    binding.proposal.tvHire.setBackground(ContextCompat.getDrawable(fragment.activity, R.drawable.blue_rounded_corner_10));
                    binding.proposal.tvChat.setBackground(ContextCompat.getDrawable(fragment.activity, R.drawable.chat_blue_border));
                    binding.proposal.tvHire.setText(fragment.getString(R.string.under_review_by_admin));
                    binding.tvCancelFreelancer.setText(Utils.getColorString(fragment.activity, fragment.getString(R.string.cancel_freelancer),
                            fragment.getString(R.string.cancel), R.color.red_dark));
                    break;
                case Constants.IN_PROGRESS:
                    binding.llUserRehire.setVisibility(View.GONE);
                    binding.tvTitle.setText(fragment.getString(R.string.job_status));
                    binding.proposal.rlProfile.setVisibility(View.VISIBLE);
                    binding.llInprogress.setVisibility(View.VISIBLE);
                    binding.llJobStatus.setVisibility(View.VISIBLE);
                    binding.rlProfileProgress.setVisibility(View.VISIBLE);
                    setJobProgress(IN_PROGRESS, false);
                    setTimerTextForIncrement();

                    binding.tvJobStatusInfo.setText(fragment.getString(R.string.check_job_release_payment));
                    break;
                case Constants.SUBMIT_WAITING_FOR_PAYMENT:
                    binding.llUserRehire.setVisibility(View.GONE);
                    binding.llJobStatus.setVisibility(View.VISIBLE);
                    binding.tvTitle.setText(fragment.getString(R.string.job_status));
                    binding.proposal.rlProfile.setVisibility(View.VISIBLE);
                    binding.llInprogress.setVisibility(View.VISIBLE);
                    binding.rlProfileProgress.setVisibility(View.VISIBLE);
                    setJobProgress(PAID, false);
                    setTimerTextForIncrement();

                    binding.tvJobStatusInfo.setText(fragment.getString(R.string.submit_waiting_payment_status));

                    ClickableSpan balanceClick = new ClickableSpan() {

                        @Override
                        public void onClick(@NotNull View view) {
                            ((ProjectDetailsActivity) fragment.activity).setPagerPosition(3);
                        }

                        @Override
                        public void updateDrawState(@NonNull TextPaint ds) {
                            super.updateDrawState(ds);
                            ds.setUnderlineText(true);
                            ds.setColor(Color.BLUE);
                        }
                    };

                    Utils.makeLinks(binding.tvJobStatusInfo, new String[]{"payment"}, new ClickableSpan[]{balanceClick});

                    binding.tvLiveSupport.setVisibility(View.VISIBLE);
                    break;
                case Constants.COMPLETED:
                    binding.llUserRehire.setVisibility(View.VISIBLE);
                    binding.llJobStatus.setVisibility(View.VISIBLE);
                    binding.tvTitle.setVisibility(View.GONE);
                    setJobProgress(PAID, true);

                    int[] colorList = {R.color.black};
                    String[] words = {fragment.activity.getString(R.string.job_is_closed_now)};
                    String[] fonts = {Constants.SFTEXT_BOLD};

                    binding.tvJobStatusInfo.setText(Utils.getBoldString(fragment.activity, fragment.getString(R.string.job_is_closed), fonts, colorList, words));
                    break;
                case Constants.CANCELLED:
                    binding.llUserRehire.setVisibility(View.GONE);
                    binding.tvTitle.setText(fragment.getString(R.string.job_status));
                    binding.tvProjectStatus.setText(fragment.getString(R.string.this_project_has_been_closed));
                    binding.llCloseProject.setVisibility(View.VISIBLE);
                    break;
                case Constants.REFUNDED:
                    binding.llUserRehire.setVisibility(View.GONE);
                    binding.tvTitle.setText(fragment.getString(R.string.job_status));
                    binding.tvProjectStatus.setText(fragment.getString(R.string.this_project_has_been_refunded));
                    binding.llCloseProject.setVisibility(View.VISIBLE);
                    break;
            }

            if (projectData.agentDetails != null) {
                if (!TextUtils.isEmpty(projectData.agentDetails.lastName) && !projectData.agentDetails.lastName.equals("null")) {
                    binding.proposal.tvName.setText(projectData.agentDetails.firstName + " " + projectData.agentDetails.lastName);
                    binding.rehire.tvName3.setText(projectData.agentDetails.firstName + " " + projectData.agentDetails.lastName);
                    binding.tvName1.setText(projectData.agentDetails.firstName + " " + projectData.agentDetails.lastName);
                } else {
                    binding.proposal.tvName.setText(projectData.agentDetails.firstName);
                    binding.rehire.tvName3.setText(projectData.agentDetails.firstName);
                    binding.tvName1.setText(projectData.agentDetails.firstName);
                }


                fragment.activity.setImage(binding.proposal.imgUser, TextUtils.isEmpty(projectData.agentDetails.photo) ? "" : fragment.activity.getUserData().filePath.pathProfilePicAgent + projectData.agentDetails.photo, 0, 0);
                fragment.activity.setImage(binding.rehire.imgUser3, TextUtils.isEmpty(projectData.agentDetails.photo) ? "" : fragment.activity.getUserData().filePath.pathProfilePicAgent + projectData.agentDetails.photo, 0, 0);
                fragment.activity.setImage(binding.imgUser1, TextUtils.isEmpty(projectData.agentDetails.photo) ? "" : fragment.activity.getUserData().filePath.pathProfilePicAgent + projectData.agentDetails.photo, 0, 0);

                binding.proposal.tvPlace.setText(projectData.agentDetails.address.getCountry(fragment.activity.getLanguage()));
                binding.rehire.tvPlace3.setText(projectData.agentDetails.address.getCountry(fragment.activity.getLanguage()));
                binding.tvPlace1.setText(String.format(Locale.US, fragment.activity.getCurrency().equals("SAR") ? fragment.getString(R.string.s_sar) : "$%s", projectData.fixedPrice != null ? Utils.decimalFormat(String.valueOf(projectData.fixedPrice)) : Utils.decimalFormat(String.valueOf(projectData.amount))));
                binding.proposal.tvBidPrice.setText(String.format(Locale.US, fragment.activity.getCurrency().equals("SAR") ? fragment.getString(R.string.s_sar) : "$%s", projectData.fixedPrice != null ? Utils.decimalFormat(String.valueOf(projectData.fixedPrice)) : Utils.decimalFormat(String.valueOf(projectData.amount))));

                if (projectData.jobPayType != null) {
                    if (projectData.jobPayType.id == 1 || projectData.jobPayType.id == 5) {
                        binding.proposal.tvPriceType.setText(fragment.activity.getString(R.string.project));
                    } else {
                        binding.proposal.tvPriceType.setText(fragment.activity.getString(R.string.hr));
                    }
                } else {
                    binding.proposal.tvPriceType.setText(fragment.getString(R.string.Free));
                }
                binding.proposal.tvProposal.setText(projectData.message);
            }
        }

        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            scrollListener.resetState();
            getProposals(1);
        });

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (totalItemsCount > 9) {
                    getProposals(page);
                }
            }
        };
    }

    void onDestroyMethod() {
        try {
            if (t != null)
                t.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void onResumeMethod() {
        if (scrollListener != null)
            binding.rvHire.addOnScrollListener(scrollListener);
    }

    private void setTimerTextForIncrement() {
        try {
            if (projectData.timer == null) {
                return;
            }
            long day = projectData.timer.days;
            long hour = projectData.timer.hours;
            long minute = projectData.timer.minutes;
            long second = projectData.timer.seconds;
            tempSec = (day * (24 * 60 * 60)) + (hour * 60 * 60) + (minute * 60) + second;

            tempSec = tempSec * 1000;

            t = new Timer();
            TimerTask tt = new TimerTask() {
                @Override
                public void run() {
                    if (projectData.timer.isdue) {
                        tempSec = tempSec + 1000;
                    } else {
                        tempSec = tempSec - 1000;
                    }

                    long days = TimeUnit.MILLISECONDS.toDays(tempSec);
                    long hours = TimeUnit.MILLISECONDS.toHours(tempSec) - TimeUnit.DAYS.toHours(days);
                    long minutes = TimeUnit.MILLISECONDS.toMinutes(tempSec) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(tempSec));
                    long seconds = TimeUnit.MILLISECONDS.toSeconds(tempSec) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(tempSec));

                    if (fragment.activity != null)
                        fragment.activity.runOnUiThread(() -> setTimerUi((int) days, (int) hours, (int) minutes, (int) seconds));
                }
            };
            t.scheduleAtFixedRate(tt, new Date(), 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTimerUi(int day, int hour, int minute, int second) {
        try {
            if (projectData.timer != null && projectData.timer.isdue) {
                binding.tvDays.setText(fragment.getString(R.string._00));
                binding.tvMinutes.setText(fragment.getString(R.string._00));
                binding.tvSecond.setText(fragment.getString(R.string._00));
                binding.tvHours.setText(fragment.getString(R.string._00));

                binding.tvDays.setTextColor(Color.RED);
                binding.tvMinutes.setTextColor(Color.RED);
                binding.tvSecond.setTextColor(Color.RED);
                binding.tvHours.setTextColor(Color.RED);
            } else {
                binding.tvDays.setText(Utils.doubleDigit(day));
                binding.tvMinutes.setText(Utils.doubleDigit(minute));
                binding.tvSecond.setText(Utils.doubleDigit(second));
                binding.tvHours.setText(Utils.doubleDigit(hour));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getProposals(int pageNo) {
        if (!fragment.activity.isNetworkConnected()) {
            binding.swipeRefreshLayout.setRefreshing(false);
            return;
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("job_id", projectData.id + "");
        map.put("page_no", pageNo + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, fragment.activity, API_JOB_POST_BIDLIST, true, map);
    }

    private void setProposalAdapter() {
        if (proposalList != null && proposalList.size() > 0) {
            binding.noData.llNoData.setVisibility(View.GONE);
            if (proposalsAdapter == null) {
                proposalsAdapter = new ProposalsAdapter(fragment.activity, projectData, this);
            }
            proposalsAdapter.doRefresh(proposalList);
            if (binding.rvHire.getAdapter() == null) {
                binding.rvHire.setAdapter(proposalsAdapter);
            }
        } else {
            binding.noData.llNoData.setVisibility(View.VISIBLE);
            if (proposalsAdapter != null) {
                proposalsAdapter.doRefresh(proposalList);
            }
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.rl_profile:
            case R.id.rl_profile1:
                viewProfile = true;
                getAgentProfile(projectData.agentProfileId);
                break;
            case R.id.tv_cancel_freelancer:
                cancelFreelancerDialog();
                break;
            case R.id.tv_hire:
                if (isHired) {
                    Intent i = new Intent(fragment.activity, MilestoneActivity.class);
                    i.putExtra(Constants.USER_DATA, projectData);
                    fragment.startActivity(i);
                }
                break;
            case R.id.tv_hire_user:
                try {
                    if (!TextUtils.isEmpty(String.valueOf(projectData.agentProfileId))) {
                        fragment.activity.setExpertInfoListener(this);
                        fragment.activity.getExpert(projectData.agentProfileId);
                    } else {
                        fragment.activity.toastMessage(fragment.activity.getString(R.string.agent_has_not_been_hired_on_this_project));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    fragment.activity.toastMessage(fragment.activity.getString(R.string.agent_has_not_been_hired_on_this_project));
                }
                break;
            case R.id.tv_chat:
            case R.id.tv_chat1:
            case R.id.tv_chat3:
                if (profileData != null && projectData.agentDetails != null) {
                    HashMap<String, String> chatMap = new HashMap<>();
                    chatMap.put(Constants.RECEIVER_ID, projectData.agentProfileId + "");
                    if (!TextUtils.isEmpty(projectData.agentDetails.lastName) && !projectData.agentDetails.lastName.equals("null")) {
                        chatMap.put(Constants.RECEIVER_NAME, projectData.agentDetails.firstName + " " + projectData.agentDetails.lastName);
                    } else {
                        chatMap.put(Constants.RECEIVER_NAME, projectData.agentDetails.firstName);
                    }

                    chatMap.put(Constants.RECEIVER_PIC, fragment.activity.getUserData().filePath.pathProfilePicAgent + projectData.agentDetails.photo);
                    chatMap.put(Constants.SENDER_ID, profileData.id + "");
                    chatMap.put(Constants.SENDER_NAME, profileData.username);
                    chatMap.put(Constants.SENDER_PIC, profileData.filePath.pathProfilePicClient + profileData.profilePic);
                    chatMap.put(Constants.PROJECT_ID, String.valueOf(projectData.id));

                    Intent i = new Intent(fragment.activity, ChatMessagesActivity.class);
                    i.putExtra(Constants.CHAT_ID, profileData.id + "-" + projectData.agentProfileId);  // ClientId - AgentId
                    i.putExtra(Constants.CHAT_DATA, chatMap);
                    if (fragment.activity.getIsVerified() == 1) {
                        fragment.startActivity(i);
                    } else {
                        fragment.activity.toastMessage(fragment.getString(R.string.verification_is_pending_please_complete_the_verification_first_before_chatting_with_them));
                    }
                }
                break;
        }
    }

    private void cancelFreelancerDialog() {
        final Dialog dialog = new Dialog(fragment.activity);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_cancel_freelancer);
        dialog.setCancelable(true);
        cancelFreelancerDialog = dialog;
        TextView tvCancelFree = dialog.findViewById(R.id.tv_free);
        tvCancelFreelancer = dialog.findViewById(R.id.tv_cancel_freelancer);
        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        pbCancelFreelancer = dialog.findViewById(R.id.progress_bar);

        String s = fragment.getString(R.string.cancel_freelancer_is_free);
        int[] colorList = {R.color.lightgreen};
        String[] words = {"FREE"};
        String[] fonts = {Constants.SFTEXT_BOLD};
        tvCancelFree.setText(Utils.getBoldString(fragment.activity, s, fonts, colorList, words));

        tvCancelFreelancer.setOnClickListener(v -> {
            cancelFreelancer();
        });

        tvCancel.setOnClickListener(v -> dialog.dismiss());

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.TOP;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }

    private void cancelFreelancer() {
        if (!fragment.activity.isNetworkConnected()) {
            return;
        }

        fragment.activity.isClickableView = true;
        pbCancelFreelancer.setVisibility(View.VISIBLE);
        tvCancelFreelancer.setVisibility(View.INVISIBLE);

        HashMap<String, String> map = new HashMap<>();
        map.put("job_post_id", projectData.id + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, fragment.activity, API_CANCEL_CONTRACT, true, map);
    }

    private void setJobProgress(int progress, boolean isComplete) {
        for (int i = 1; i <= progress; i++) {
            int resTv = fragment.getResources().getIdentifier("tv_" + i, "id", fragment.activity.getPackageName());
            int resTvLbl = fragment.getResources().getIdentifier("txt_lbl_" + i, "id", fragment.activity.getPackageName());
            TextView tv = binding.getRoot().findViewById(resTv);
            TextView tvlbl = binding.getRoot().findViewById(resTvLbl);
            try {
                View viewLeft = null;
                View viewRight = null;

                if (i != 1) {
                    int resLeft = fragment.getResources().getIdentifier("view" + i + "_left", "id", fragment.activity.getPackageName());
                    viewLeft = binding.getRoot().findViewById(resLeft);
                }

                if (i != progress) {
                    int resRight = fragment.getResources().getIdentifier("view" + i + "_right", "id", fragment.activity.getPackageName());
                    viewRight = binding.getRoot().findViewById(resRight);
                }

                if (i < progress) {
                    setTextColor(tv, R.color.white);
                    setTextColor(tvlbl, R.color.black);
                    setTextBackground(tv, R.drawable.job_status_complete);
                    if (viewLeft != null) {
                        setTextBackground(viewLeft, R.color.black);
                    }
                    if (viewRight != null) {
                        setTextBackground(viewRight, R.color.black);
                    }
                } else {
                    setTextColor(tv, isComplete ? R.color.white : R.color.black);
                    setTextColor(tvlbl, R.color.black);
                    setTextBackground(tv, isComplete ? R.drawable.job_status_complete : R.drawable.job_status_current);
                    if (viewLeft != null) {
                        setTextBackground(viewLeft, R.color.black);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setTextColor(TextView tv, int color) {
        tv.setTextColor(ContextCompat.getColor(fragment.activity, color));
    }

    private void setTextBackground(View view, int drawable) {
        view.setBackground(ContextCompat.getDrawable(fragment.activity, drawable));
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        if (url.equalsIgnoreCase(API_JOB_POST_BIDLIST)) {
            Proposals proposals = Proposals.getBidList(responseBody);
            proposalList = new ArrayList<>();
            if (proposals != null && proposals.data != null) {
                proposalList = proposals.data;
            }
            setProposalAdapter();
            binding.swipeRefreshLayout.setRefreshing(false);
        } else if (url.equalsIgnoreCase(API_CANCEL_CONTRACT)) {
            fragment.activity.isClickableView = false;
            cancelFreelancerDialog.dismiss();
            pbCancelFreelancer.setVisibility(View.GONE);
            tvCancelFreelancer.setVisibility(View.VISIBLE);
            fragment.activity.gotoMainActivity(Constants.TAB_JOB_LIST);
        } else if (url.equalsIgnoreCase(API_GET_PROFILE_INFO)) {
            AgentProfile profile = AgentProfile.getProfileInfo(responseBody);
            if (profile != null) {
//                Intent i = new Intent(fragment.activity, FreelancerProfileActivity.class);
                Intent i = new Intent(fragment.activity, InfluencerProfileActivityCopy.class);
                i.putExtra(Constants.SHOW_HIRE, true);
                if (viewProfile) {
                    if (projectData.jpstateId == (Constants.CANCELLED) ||
                            projectData.jpstateId == (Constants.COMPLETED) ||
                            projectData.jpstateId == (Constants.REFUNDED)) {
                        i.putExtra(Constants.REHIRE, true);
                    }
                } else {
                    i.putExtra(Constants.USER_DATA, userData);
                }
                i.putExtra(Constants.AGENT_PROFILE_DATA, profile);

                fragment.startActivity(i);
            }
            if (!viewProfile) {
                notifyProfileProgress();
                viewProfile = false;
            }
        }
        fragment.activity.isClickableView = false;
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        binding.swipeRefreshLayout.setRefreshing(false);
        if (url.equalsIgnoreCase(API_GET_PROFILE_INFO)) {
            if (!viewProfile) {
                notifyProfileProgress();
            }
            viewProfile = false;
        } else if (url.equalsIgnoreCase(API_CANCEL_CONTRACT)) {
            pbCancelFreelancer.setVisibility(View.GONE);
            tvCancelFreelancer.setVisibility(View.VISIBLE);
        } else if (url.equalsIgnoreCase(API_JOB_POST_BIDLIST)) {
            proposalList = new ArrayList<>();
            setProposalAdapter();
        }
        fragment.activity.isClickableView = false;
    }

    @Override
    public void onClickProfile(int agentId, Proposals.Data userData, int selPos) {
        selectedPos = selPos;
        this.userData = userData;
        getAgentProfile(agentId);
    }

    private void getAgentProfile(int agentProfileId) {
        if (!fragment.activity.isNetworkConnected())
            return;
        fragment.activity.isClickableView = true;

        HashMap<String, String> map = new HashMap<>();
        map.put("agent_profile_id", agentProfileId + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, fragment.activity, API_GET_PROFILE_INFO, true, map);
    }

    private void notifyProfileProgress() {
        proposalsAdapter.getData().get(selectedPos).isShowProfileProgress = false;
        proposalsAdapter.notifyItemChanged(selectedPos);
    }

    @Override
    public void onExpertSuccess(ExpertDetail expertDetail) {
        Preferences.writeString(fragment.activity, Constants.PLATFORM_ID, expertDetail.serviceId + "");
        Preferences.writeString(fragment.activity, Constants.PLATFORM_NAME, expertDetail.serviceName + "");
        ArrayList<ExpertLawyers.Data> expertUsers = new ArrayList<>();
        expertUsers.add(new ExpertLawyers.Data(expertDetail.profileId, expertDetail.firstName + " " + expertDetail.lastName));
        Preferences.setExpertUsers(fragment.activity, expertUsers);

        binding.rehire.tvHireUser.setVisibility(View.VISIBLE);
        binding.rehire.progressBar.setVisibility(View.GONE);
        fragment.activity.isClickableView = false;

        fragment.activity.gotoMainActivity(Constants.TAB_POST_JOB);
    }

    @Override
    public void onExpertFail() {
        binding.rehire.tvHireUser.setVisibility(View.VISIBLE);
        binding.rehire.progressBar.setVisibility(View.GONE);
        fragment.activity.isClickableView = false;
    }

    @Override
    public void onPreExpert() {
        binding.rehire.tvHireUser.setVisibility(View.INVISIBLE);
        binding.rehire.progressBar.setVisibility(View.VISIBLE);
        fragment.activity.isClickableView = true;
    }
}
