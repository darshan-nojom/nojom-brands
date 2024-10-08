package com.nojom.client.ui.projects;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.nojom.client.util.Constants.AGENT_PROFILE_DATA;
import static com.nojom.client.util.Constants.API_BLOCK_USER;
import static com.nojom.client.util.Constants.API_GET_AGENT_REVIEW;
import static com.nojom.client.util.Constants.API_GET_CUSTOM_GIG_DETAILS;
import static com.nojom.client.util.Constants.API_GET_PROFILE_INFO;
import static com.nojom.client.util.Constants.API_UNBLOCK_USER;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.lifecycle.AndroidViewModel;

import com.nojom.client.R;
import com.nojom.client.adapter.InfluencerServiceAdapter;
import com.nojom.client.adapter.PortfolioListAdapter;
import com.nojom.client.adapter.ProfilePlatformAdapter;
import com.nojom.client.adapter.ReviewsAdapter;
import com.nojom.client.adapter.StoreAdapter;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivityInflProfileAllBinding;
import com.nojom.client.databinding.ActivityInfluencerProfileBinding;
import com.nojom.client.model.AgentProfile;
import com.nojom.client.model.ClientReviews;
import com.nojom.client.model.ExpertDetail;
import com.nojom.client.model.ExpertGigDetail;
import com.nojom.client.model.ExpertLawyers;
import com.nojom.client.model.Portfolios;
import com.nojom.client.model.Profile;
import com.nojom.client.model.Proposals;
import com.nojom.client.model.SocialPlatformList;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.chat.ChatMessagesActivity;
import com.nojom.client.ui.home.GigDetailActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

class InfluencerProfileAllActivityVM extends AndroidViewModel implements View.OnClickListener, RequestResponseListener, BaseActivity.ExpertInfoListener, InfluencerServiceAdapter.OnClickService {
    private final ActivityInflProfileAllBinding binding;
    @SuppressLint("StaticFieldLeak")
    private final BaseActivity activity;

    private AgentProfile agentData;
    Portfolios portfolios;
    private Profile clientData;
    private Proposals.Data proposalData;
    private boolean isFromChatScreen;
    public List<SocialPlatformList.Data> socialPlatformList;
    private List<ClientReviews.Data> reviewsList;
    private String fromScreen;
    private int gigId = 0, selectedPos;

    InfluencerProfileAllActivityVM(Application application, ActivityInflProfileAllBinding profileBinding, BaseActivity freelancerProfileActivity) {
        super(application);
        binding = profileBinding;
        activity = freelancerProfileActivity;
        initData();
    }

    private void initData() {
        reviewsList = new ArrayList<>();
        binding.imgBack.setOnClickListener(this);
        binding.rlPortfolioView.setOnClickListener(this);
//        binding.tvHire.setOnClickListener(this);
        binding.tvChat.setOnClickListener(this);
//        binding.tvReportBlock.setOnClickListener(this);

        if (activity.getIntent() != null) {
            agentData = (AgentProfile) activity.getIntent().getSerializableExtra(AGENT_PROFILE_DATA);
            portfolios = (Portfolios) activity.getIntent().getSerializableExtra("portfolios");
            socialPlatformList = (List<SocialPlatformList.Data>) activity.getIntent().getSerializableExtra("platform");
            isFromChatScreen = activity.getIntent().getBooleanExtra("from", false);
            fromScreen = activity.getIntent().getStringExtra("screen");
            proposalData = (Proposals.Data) activity.getIntent().getSerializableExtra(Constants.USER_DATA);


            if (agentData.social_platform != null && agentData.social_platform.size() > 0) {
                setPlatformAdapter(agentData.social_platform);
            }
        }

        binding.imgShare.setOnClickListener(v -> {
            if (agentData.firebaseLink != null) {
                activity.shareApp(agentData.firebaseLink);
            }
        });

        if (agentData != null) {

            switch (fromScreen) {
                case "portfolio": //View all from portfolio screen
                    //set portfolio list adapter
                    if (portfolios != null && portfolios.data != null && portfolios.data.size() > 0) {
                        setPortfolioAdapter(portfolios.data, portfolios.path);
                    }
                    binding.txtTitle.setText(activity.getString(R.string.portfolio));
                    break;
                case "services": //View all from portfolio screen
                    setServicesAdapter();
                    binding.txtTitle.setText(activity.getString(R.string.services));
                    break;
                case "review": //View all from portfolio screen
                    binding.txtTitle.setText(activity.getString(R.string.reviews));
                    getReviews(1, agentData.id);
                    break;
                case "store": //View all from portfolio screen
                    binding.txtTitle.setText(activity.getString(R.string.store));
                    setStoreAdapter(agentData.store);
                    break;
                case "agency": //View all from portfolio screen
                    binding.linPortfolio.setVisibility(GONE);
                    binding.linAgency.setVisibility(VISIBLE);

                    activity.runOnUiThread(() -> {
                        if (agentData.agent_agency != null && agentData.agent_agency.size() > 0) {
                            binding.tvAgencyName.setText(agentData.agent_agency.get(0).name);
                            binding.tvAgencyContact.setText(agentData.agent_agency.get(0).phone);
                            binding.tvAgencyWebsite.setText(agentData.agent_agency.get(0).website);
                            binding.tvAgencyEmail.setText(agentData.agent_agency.get(0).email);
                            binding.tvAgencyAdd.setText(agentData.agent_agency.get(0).address);
                            binding.tvAgencyNote.setText(agentData.agent_agency.get(0).note);
                            binding.tvAgencyAbout.setText(agentData.agent_agency.get(0).about);
                        }
                    });
                    break;
            }

            setUi();
        } else {
            activity.finish();
            return;
        }

        clientData = Preferences.getProfileData(activity);

        Utils.trackAppsFlayerEvent(activity, "Freelancer_Profile_Screen");
    }

    public void setStoreAdapter(List<AgentProfile.StoreList> profilePlatformArrayList) {
        StoreAdapter adapter = new StoreAdapter();
        adapter.doRefresh(profilePlatformArrayList, activity);
        binding.rvPortfolio.setAdapter(adapter);
    }

    private void setReviewAdapter() {
        activity.runOnUiThread(() -> {
            if (reviewsList != null && reviewsList.size() > 0) {
                ReviewsAdapter mAdapter = new ReviewsAdapter(activity);
                mAdapter.doRefresh(reviewsList);
                binding.rvPortfolio.setAdapter(mAdapter);
            }
        });
    }

    InfluencerServiceAdapter influencerServiceAdapter;

    private void setServicesAdapter() {
        if (socialPlatformList != null && socialPlatformList.size() > 0) {
            influencerServiceAdapter = new InfluencerServiceAdapter(activity, socialPlatformList, InfluencerProfileAllActivityVM.this);
            binding.rvPortfolio.setAdapter(influencerServiceAdapter);
        }
    }

    private void setPortfolioAdapter(List<Portfolios.Data> data, String filePath) {
        PortfolioListAdapter portfolioFileAdapter = new PortfolioListAdapter(activity, data, filePath);
        binding.rvPortfolio.setAdapter(portfolioFileAdapter);
    }

    private void setUi() {
        if (agentData != null) {
            if (agentData.firstName != null && agentData.lastName != null) {
                binding.tvName.setText(String.format("%s %s", agentData.firstName, agentData.lastName));
            }

            binding.tvUserName.setText("@" + agentData.username);

            if (agentData.saved == 1) {
                binding.imgSave.setImageResource(R.drawable.ic_fav_fill);
            } else {
                binding.imgSave.setImageResource(R.drawable.ic_fav);
            }

            if (agentData.trustRateStatus.verifyId == 1) {
                binding.imgVerified.setVisibility(VISIBLE);
            }

            if (!TextUtils.isEmpty(agentData.websites)) {
                binding.tvLink.setText(String.format("%s", agentData.websites));
            }

            activity.setImage(binding.imgProfile, TextUtils.isEmpty(agentData.profilePic) ? "" : agentData.path + agentData.profilePic, 0, 0);

            updateBlockUnblockStatus();
        }
    }

    public void setPlatformAdapter(List<AgentProfile.ConnectedPlatform> profilePlatformArrayList) {
        ProfilePlatformAdapter adapter = new ProfilePlatformAdapter();
        adapter.doRefresh(profilePlatformArrayList, activity);
        binding.rvPlatform.setAdapter(adapter);
    }

    private void updateBlockUnblockStatus() {
//        if (agentData.blockStatus == 0) {
//            binding.tvReportBlock.setText(activity.getString(R.string.report_amp_block));
//        } else {
//            binding.tvReportBlock.setText(activity.getString(R.string.unblock));
//        }
    }

    AgentProfile getAgentData() {
        return agentData;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_portfolio_view:
            case R.id.img_back:
                activity.onBackPressed();
                break;
            case R.id.tv_chat:
                if (activity.isLogin()) {
                    if (clientData != null) {
                        if (isFromChatScreen) {
                            activity.finish();
                        } else {
                            HashMap<String, String> chatMap = new HashMap<>();
                            chatMap.put(Constants.RECEIVER_ID, agentData.id + "");
                            chatMap.put(Constants.RECEIVER_NAME, agentData.firstName+" "+agentData.lastName);
                            chatMap.put(Constants.RECEIVER_PIC, agentData.path + agentData.profilePic);
                            chatMap.put(Constants.SENDER_ID, clientData.id + "");
                            chatMap.put(Constants.SENDER_NAME, clientData.username);
                            chatMap.put(Constants.SENDER_PIC, clientData.filePath.pathProfilePicClient + clientData.profilePic);
                            if (proposalData != null && proposalData.jobPostId != 0) {
                                chatMap.put(Constants.PROJECT_ID, String.valueOf(proposalData.jobPostId));
                            }

                            Intent i = new Intent(activity, ChatMessagesActivity.class);
                            i.putExtra(Constants.CHAT_ID, clientData.id + "-" + agentData.id);  // ClientId - AgentId
                            i.putExtra(Constants.CHAT_DATA, chatMap);
                            if (activity.getIsVerified() == 1) {
                                activity.startActivity(i);
                            } else {
                                activity.toastMessage(activity.getString(R.string.verification_is_pending_please_complete_the_verification_first_before_chatting_with_them));
                            }
                        }
                    }
                } else {
                    Preferences.writeString(activity, "influencerName", agentData.id + "");
                    activity.openLoginDialog();
                }
                break;
            case R.id.tv_report_block:
                if (activity.isLogin()) {
                    if (agentData.blockStatus == 0) {
                        refundPaymentReasonDialog();
                    } else {
                        showUnblockDialog();
                    }
                } else {
                    activity.openLoginDialog();
                }
                break;
        }
    }

    @Override
    public void onExpertSuccess(ExpertDetail expertDetail) {
        Preferences.writeString(activity, Constants.PLATFORM_ID, expertDetail.serviceId + "");
        Preferences.writeString(activity, Constants.PLATFORM_NAME, expertDetail.serviceName + "");
        ArrayList<ExpertLawyers.Data> expertUsers = new ArrayList<>();
        expertUsers.add(new ExpertLawyers.Data(expertDetail.profileId, expertDetail.firstName+" "+expertDetail.lastName));
        Preferences.setExpertUsers(activity, expertUsers);

//        binding.tvHire.setVisibility(VISIBLE);
//        binding.progressBar.setVisibility(GONE);
        activity.isClickableView = false;

        activity.gotoMainActivity(Constants.TAB_POST_JOB);
    }

    @Override
    public void onExpertFail() {
//        binding.tvHire.setVisibility(VISIBLE);
//        binding.progressBar.setVisibility(GONE);
        activity.isClickableView = false;
    }

    @Override
    public void onPreExpert() {
//        binding.tvHire.setVisibility(View.INVISIBLE);
//        binding.progressBar.setVisibility(VISIBLE);
        activity.isClickableView = true;
    }

    private void refundPaymentReasonDialog() {
        final Dialog dialog = new Dialog(activity);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_refund_user);
        dialog.setCancelable(true);

        TextView tvConfirm = dialog.findViewById(R.id.tv_submit);
        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        TextView etReason = dialog.findViewById(R.id.edit_reason);
        TextView tvTitle = dialog.findViewById(R.id.tv_title);
        TextView txt1 = dialog.findViewById(R.id.txt1);
        RadioGroup radioGroup = dialog.findViewById(R.id.radioGroup);
        RadioButton rb1 = dialog.findViewById(R.id.rb_inappropriate);
        RadioButton rb2 = dialog.findViewById(R.id.rb_irrelevant);
        RadioButton rb4 = dialog.findViewById(R.id.rb_other);
        RadioButton rb3 = dialog.findViewById(R.id.rb_scam);
        radioGroup.clearCheck();

        tvTitle.setText(activity.getString(R.string.whats_wrong_with_this_profile));
        txt1.setText(activity.getString(R.string.help_us_know_why_you_are_reporting_this));
        rb1.setText(activity.getString(R.string.scammer));
        rb2.setText(activity.getString(R.string.share_personal_contact_and_payment));
        rb3.setText(activity.getString(R.string.blackmailing_and_harasment));
        rb4.setText(activity.getString(R.string.others));

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton rb = group.findViewById(checkedId);
            if (null != rb) {
                if (rb.getText().equals(activity.getString(R.string.others))) {
                    etReason.setVisibility(VISIBLE);
                } else {
                    etReason.setVisibility(GONE);
                }
            }
        });

        tvConfirm.setOnClickListener(v -> {
            RadioButton rb = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
            String selectedReason = "";
            boolean isOtherSelect = false;
            if (rb != null && !TextUtils.isEmpty(rb.getText())) {
                if (rb.getText().equals(activity.getString(R.string.other))) {
                    selectedReason = etReason.getText().toString();
                    isOtherSelect = true;
                } else {
                    selectedReason = rb.getText().toString();
                    isOtherSelect = false;
                }
            }
            if (TextUtils.isEmpty(selectedReason)) {
                activity.toastMessage(activity.getString(R.string.please_select_reason));
                return;
            }
            dialog.dismiss();
            reportUser(selectedReason, isOtherSelect);
        });

        tvCancel.setOnClickListener(v -> {
            radioGroup.clearCheck();
            dialog.dismiss();
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.TOP;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }

    private void reportUser(String reason, boolean isOtherSelect) {
        if (!activity.isNetworkConnected()) return;

        HashMap<String, String> map = new HashMap<>();
        map.put("reported_user", agentData.id + "");
        map.put("reason", reason);
        map.put("other", isOtherSelect ? "1" : "0");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_BLOCK_USER, true, map);
    }

    private void showUnblockDialog() {
        final Dialog dialog = new Dialog(activity, R.style.Theme_AppCompat_Dialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_delete_project);
        dialog.setCancelable(true);

        TextView tvMessage = dialog.findViewById(R.id.tv_message);
        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        TextView tvChatnow = dialog.findViewById(R.id.tv_chat_now);

        tvMessage.setText(activity.getString(R.string.are_you_sure_wants_to_unblock_user));

        tvCancel.setText(activity.getString(R.string.no));
        tvChatnow.setText(activity.getString(R.string.yes));
        tvCancel.setOnClickListener(v -> dialog.dismiss());

        tvChatnow.setOnClickListener(v -> {
            dialog.dismiss();
            unBlockUser();
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.gravity = Gravity.CENTER;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }

    private void unBlockUser() {
        if (!activity.isNetworkConnected()) return;

        HashMap<String, String> map = new HashMap<>();
        map.put("reported_user", agentData.id + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_UNBLOCK_USER, true, map);
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        activity.isClickableView = false;
        if (url.equalsIgnoreCase(API_GET_PROFILE_INFO)) {
            AgentProfile profile = AgentProfile.getProfileInfo(responseBody);
            if (profile != null) {
                agentData = profile;

                setUi();
            }
        } else if (url.equalsIgnoreCase(API_UNBLOCK_USER)) {
            activity.failureError(message);
            agentData.blockStatus = 0;
            updateBlockUnblockStatus();
        } else if (url.equalsIgnoreCase(API_BLOCK_USER)) {
            activity.failureError(message);
            agentData.blockStatus = 1;
            updateBlockUnblockStatus();
        } else if (url.equalsIgnoreCase(API_GET_CUSTOM_GIG_DETAILS + "/" + gigId)) {
            ExpertGigDetail expertGigDetail = ExpertGigDetail.getGigDetail(responseBody);
            Preferences.writeString(activity, "gigID", null);

            if (expertGigDetail != null) {

                activity.runOnUiThread(() -> {
                    if (influencerServiceAdapter != null) {
                        influencerServiceAdapter.getData().get(selectedPos).isShowProgress = false;
                        influencerServiceAdapter.notifyItemChanged(selectedPos);
                    }
                });

                Intent intent = new Intent(activity, GigDetailActivity.class);
                intent.putExtra(Constants.PROJECT_DETAIL, expertGigDetail);
//                intent.putExtra("gigID", gigId);
                activity.startActivity(intent);
            }
            gigId = 0;
            selectedPos = 0;
        } else if (url.equalsIgnoreCase(API_GET_AGENT_REVIEW)) {
            ClientReviews agentReviews = ClientReviews.getClientReviews(responseBody);
            if (agentReviews != null && agentReviews.data != null) {
                reviewsList.addAll(agentReviews.data);
            }
            setReviewAdapter();
        }
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        activity.isClickableView = false;
        if (url.equalsIgnoreCase(API_GET_PROFILE_INFO)) {
            activity.finish();
        } else if (url.equalsIgnoreCase(API_GET_CUSTOM_GIG_DETAILS + "/" + gigId)) {
            activity.runOnUiThread(() -> {
                if (influencerServiceAdapter != null) {
                    influencerServiceAdapter.getData().get(selectedPos).isShowProgress = false;
                    influencerServiceAdapter.notifyItemChanged(selectedPos);
                }
            });
            gigId = 0;
            selectedPos = 0;
        }
    }


    @Override
    public void onClickService(SocialPlatformList.Data data, int pos) {
        gigId = data.id;
        selectedPos = pos;
        getGigDetails();
    }

    private void getGigDetails() {
        if (!activity.isNetworkConnected()) {
            return;
        }

        ApiRequest apiRequest = new ApiRequest();

        apiRequest.apiRequest(this, activity, API_GET_CUSTOM_GIG_DETAILS + "/" + gigId, false, null);
    }

    public void getReviews(int pageNo, int profileId) {
        activity.isClickableView = true;

        HashMap<String, String> map = new HashMap<>();
        map.put("agent_profile_id", profileId + "");
        map.put("page_no", pageNo + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_AGENT_REVIEW, true, map);
    }
}
