package com.nojom.client.ui.clientprofile;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.nojom.client.util.Constants.AGENT_PROFILE_DATA;
import static com.nojom.client.util.Constants.API_BLOCK_USER;
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

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.nojom.client.R;
import com.nojom.client.adapter.VerifiedAdapter;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivityFreelancerProfileBinding;
import com.nojom.client.fragment.profile.AboutProfileFragment;
import com.nojom.client.fragment.profile.PortfolioFragment;
import com.nojom.client.fragment.profile.ReviewsProfileFragment;
import com.nojom.client.fragment.profile.SkillProfileFragment;
import com.nojom.client.model.AgentProfile;
import com.nojom.client.model.ExpertDetail;
import com.nojom.client.model.ExpertLawyers;
import com.nojom.client.model.Profile;
import com.nojom.client.model.Proposals;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.chat.ChatMessagesActivity;
import com.nojom.client.ui.projects.HireDescribeActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

class FreelancerProfileActivityVM extends AndroidViewModel implements View.OnClickListener, RequestResponseListener, BaseActivity.ExpertInfoListener {
    private final ActivityFreelancerProfileBinding binding;
    @SuppressLint("StaticFieldLeak")
    private final BaseActivity activity;

    private AgentProfile agentData;
    private Profile clientData;
    private boolean isShowHire, isRehire;
    private Proposals.Data proposalData;
    private VerifiedAdapter mVerifiedAdapter;
    private boolean isFromChatScreen;

    FreelancerProfileActivityVM(Application application, ActivityFreelancerProfileBinding profileBinding, BaseActivity freelancerProfileActivity) {
        super(application);
        binding = profileBinding;
        activity = freelancerProfileActivity;
        initData();
    }

    private void initData() {
        binding.imgBack.setOnClickListener(this);
        binding.tvHire.setOnClickListener(this);
        binding.tvChat.setOnClickListener(this);
        binding.tvReportBlock.setOnClickListener(this);

        if (activity.getIntent() != null) {
            agentData = (AgentProfile) activity.getIntent().getSerializableExtra(AGENT_PROFILE_DATA);
            isShowHire = activity.getIntent().getBooleanExtra(Constants.SHOW_HIRE, false);
            isRehire = activity.getIntent().getBooleanExtra(Constants.REHIRE, false);
            isFromChatScreen = activity.getIntent().getBooleanExtra("from", false);
            proposalData = (Proposals.Data) activity.getIntent().getSerializableExtra(Constants.USER_DATA);
        }

        if (agentData != null) {
            setUi();
            setupPager();
        } else {
            activity.finish();
            return;
        }

        binding.rvVerified.setLayoutManager(new GridLayoutManager(activity, 2));

        clientData = Preferences.getProfileData(activity);

        if (isRehire) {
            binding.tvHire.setText(activity.getString(R.string.rehire_me));
        }

        Utils.trackAppsFlayerEvent(activity, "Freelancer_Profile_Screen");

    }

    private void setUi() {
        if (agentData != null) {
            if (agentData.username != null) {
                binding.tvUserName.setText(agentData.username);
            } else if (agentData.firstName != null && agentData.lastName != null) {
                binding.tvUserName.setText(String.format(Locale.US, "%s %s", agentData.firstName, agentData.lastName));
            } else if (agentData.firstName != null) {
                binding.tvUserName.setText(agentData.firstName);
            } else if (agentData.lastName != null) {
                binding.tvUserName.setText(agentData.lastName);
            }

            if (agentData.countRating != null) {
                binding.tvReviews.setText(String.format(Locale.US, "(%d " + activity.getString(R.string.reviews) + ")", agentData.countRating));
            }
            try {
                if (agentData.rate != null) {
                    String rate = activity.get1DecimalPlaces(agentData.rate);
                    binding.ratingbar.setRating(Float.parseFloat(rate));
                }
            } catch (Exception e) {
                binding.ratingbar.setRating(0);
            }

            activity.setImage(binding.imgProfile, TextUtils.isEmpty(agentData.profilePic) ? "" : agentData.path + agentData.profilePic, 0, 0);

            ArrayList<Profile.VerifiedWith> verifiedList = new ArrayList<>();
            if (agentData.trustRate != null) {
                verifiedList.add(new Profile.VerifiedWith(activity.getString(R.string.email_address), agentData.trustRate.email));
                verifiedList.add(new Profile.VerifiedWith(activity.getString(R.string.facebook), agentData.trustRate.facebook));
                verifiedList.add(new Profile.VerifiedWith(activity.getString(R.string.payment), agentData.trustRate.payment));
                verifiedList.add(new Profile.VerifiedWith(activity.getString(R.string.phonenumber), agentData.trustRate.phoneNumber));
                verifiedList.add(new Profile.VerifiedWith(activity.getString(R.string.government_id), agentData.trustRate.verifyId));
            }
            setVerifiedAdapter(verifiedList);

            updateBlockUnblockStatus();
        }
    }

    private void updateBlockUnblockStatus() {
        if (agentData.blockStatus == 0) {
            binding.tvReportBlock.setText(activity.getString(R.string.report_amp_block));
        } else {
            binding.tvReportBlock.setText(activity.getString(R.string.unblock));
        }
    }

    private void setVerifiedAdapter(ArrayList<Profile.VerifiedWith> verifiedList) {
        if (verifiedList != null && verifiedList.size() > 0) {
            binding.tvNoVerified.setVisibility(View.GONE);
            if (mVerifiedAdapter == null) {
                mVerifiedAdapter = new VerifiedAdapter();
            }
            mVerifiedAdapter.doRefresh(verifiedList);

            if (binding.rvVerified.getAdapter() == null) {
                binding.rvVerified.setAdapter(mVerifiedAdapter);
            }
        } else {
            binding.tvNoVerified.setVisibility(View.VISIBLE);
            if (mVerifiedAdapter != null) {
                mVerifiedAdapter.doRefresh(verifiedList);
            }
        }
    }

    AgentProfile getAgentData() {
        return agentData;
    }

    private void setupPager() {
        setupViewPager(binding.viewpager);

        binding.segmentGroup.setOnPositionChangedListener(position -> binding.viewpager.setCurrentItem(position));

        binding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (binding.segmentGroup.getPosition() != i) {
                    binding.segmentGroup.setPosition(i, false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(activity.getSupportFragmentManager());
        adapter.addFrag(new AboutProfileFragment(), activity.getString(R.string.about));
        adapter.addFrag(new SkillProfileFragment(), activity.getString(R.string.skills));
        adapter.addFrag(new PortfolioFragment(), activity.getString(R.string.portfolio));
        adapter.addFrag(new ReviewsProfileFragment(), activity.getString(R.string.reviews));
        viewPager.setPageMargin(20);
        viewPager.setAdapter(adapter);
    }

    public void onClick(View view) {
        switch (view.getId()) {
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
                            if (!TextUtils.isEmpty(agentData.lastName) && !agentData.lastName.equals("null")) {
                                chatMap.put(Constants.RECEIVER_NAME, agentData.firstName + " " + agentData.lastName);
                            } else {
                                chatMap.put(Constants.RECEIVER_NAME, agentData.firstName);
                            }

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
            case R.id.tv_hire:
                if (activity.isLogin()) {

                    Intent i = new Intent(activity, HireDescribeActivity.class);
                    i.putExtra(Constants.AGENT_PROFILE_DATA, agentData);
                    activity.startActivity(i);

//                    if (isRehire) {
//                        try {
//                            if (!TextUtils.isEmpty(String.valueOf(agentData.id))) {
//                                activity.setExpertInfoListener(this);
//                                activity.getExpert(agentData.id);
//                            } else
//                                activity.toastMessage(activity.getString(R.string.agent_has_not_been_hired_on_this_project));
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            activity.toastMessage(activity.getString(R.string.agent_has_not_been_hired_on_this_project));
//                        }
//                    } else {
//                        ArrayList<ExpertLawyers.Data> expertUsers = new ArrayList<>();
//                        expertUsers.add(new ExpertLawyers.Data(agentData.id, agentData.username));
//                        Preferences.setExpertUsers(activity, expertUsers);
//                        activity.gotoMainActivity(Constants.TAB_POST_JOB);
//                    }

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
        expertUsers.add(new ExpertLawyers.Data(expertDetail.profileId, expertDetail.firstName + " " + expertDetail.lastName));
        Preferences.setExpertUsers(activity, expertUsers);

        binding.tvHire.setVisibility(VISIBLE);
        binding.progressBar.setVisibility(GONE);
        activity.isClickableView = false;

        activity.gotoMainActivity(Constants.TAB_POST_JOB);
    }

    @Override
    public void onExpertFail() {
        binding.tvHire.setVisibility(VISIBLE);
        binding.progressBar.setVisibility(GONE);
        activity.isClickableView = false;
    }

    @Override
    public void onPreExpert() {
        binding.tvHire.setVisibility(View.INVISIBLE);
        binding.progressBar.setVisibility(VISIBLE);
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
        if (!activity.isNetworkConnected())
            return;

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
        if (!activity.isNetworkConnected())
            return;

        HashMap<String, String> map = new HashMap<>();
        map.put("reported_user", agentData.id + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_UNBLOCK_USER, true, map);
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        if (url.equalsIgnoreCase(API_GET_PROFILE_INFO)) {
            AgentProfile profile = AgentProfile.getProfileInfo(responseBody);
            if (profile != null) {
                agentData = profile;

                setUi();
                setupPager();
            }
        } else if (url.equalsIgnoreCase(API_UNBLOCK_USER)) {
            activity.failureError(message);
            agentData.blockStatus = 0;
            updateBlockUnblockStatus();
        } else if (url.equalsIgnoreCase(API_BLOCK_USER)) {
            activity.failureError(message);
            agentData.blockStatus = 1;
            updateBlockUnblockStatus();
        }
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        if (url.equalsIgnoreCase(API_GET_PROFILE_INFO)) {
            activity.finish();
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
