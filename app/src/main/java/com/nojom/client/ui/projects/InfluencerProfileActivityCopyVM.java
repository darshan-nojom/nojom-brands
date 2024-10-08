package com.nojom.client.ui.projects;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.nojom.client.util.Constants.AGENT_PROFILE_DATA;
import static com.nojom.client.util.Constants.API_BLOCK_USER;
import static com.nojom.client.util.Constants.API_GET_AGENCY;
import static com.nojom.client.util.Constants.API_GET_AGENT_COMPANIES;
import static com.nojom.client.util.Constants.API_GET_AGENT_PARTNERS;
import static com.nojom.client.util.Constants.API_GET_AGENT_PROFILE_SKILLS;
import static com.nojom.client.util.Constants.API_GET_AGENT_REVIEW;
import static com.nojom.client.util.Constants.API_GET_AGENT_STORES;
import static com.nojom.client.util.Constants.API_GET_CUSTOM_GIG_DETAILS;
import static com.nojom.client.util.Constants.API_GET_PORTFOLIO;
import static com.nojom.client.util.Constants.API_GET_PROFILE_INFO;
import static com.nojom.client.util.Constants.API_GET_SOCIAL_PLATFORM_LIST;
import static com.nojom.client.util.Constants.API_REMOVE_AGENT;
import static com.nojom.client.util.Constants.API_SAVE_AGENT;
import static com.nojom.client.util.Constants.API_UNBLOCK_USER;
import static java.lang.Math.abs;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.AndroidViewModel;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.nojom.client.R;
import com.nojom.client.adapter.CustomAdapter;
import com.nojom.client.adapter.InfluPortfolioAdapter;
import com.nojom.client.adapter.InfluencerServiceAdapter;
import com.nojom.client.adapter.MyStoreAdapter;
import com.nojom.client.adapter.PartnerAdapter;
import com.nojom.client.adapter.ProfilePlatformAdapter;
import com.nojom.client.adapter.ReviewsAdapter;
import com.nojom.client.adapter.SkillsListAdapter;
import com.nojom.client.adapter.StoreAdapter;
import com.nojom.client.adapter.WorkWithAdapter;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivityInfluencerProfileCopyBinding;
import com.nojom.client.model.AgencyList;
import com.nojom.client.model.AgentProfile;
import com.nojom.client.model.ClientReviews;
import com.nojom.client.model.ExpertDetail;
import com.nojom.client.model.ExpertGigDetail;
import com.nojom.client.model.ExpertLawyers;
import com.nojom.client.model.GetAgentPartners;
import com.nojom.client.model.GetCompanies;
import com.nojom.client.model.GetStores;
import com.nojom.client.model.Portfolios;
import com.nojom.client.model.Profile;
import com.nojom.client.model.ProfileSkills;
import com.nojom.client.model.Proposals;
import com.nojom.client.model.SavedInfluencer;
import com.nojom.client.model.SocialPlatformList;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.chat.ChatMessagesActivity;
import com.nojom.client.ui.home.GigDetailActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

class InfluencerProfileActivityCopyVM extends AndroidViewModel implements View.OnClickListener, RequestResponseListener, BaseActivity.ExpertInfoListener, InfluencerServiceAdapter.OnClickService {
    private final ActivityInfluencerProfileCopyBinding binding;
    @SuppressLint("StaticFieldLeak")
    private final BaseActivity activity;

    private AgentProfile agentData;
    private Profile clientData;
    private boolean isShowHire, isRehire, viewMoreService = true, viewMoreStore = true, viewMoreAgency = true, viewMoreReview = true;
    private Proposals.Data proposalData;
    private boolean isFromChatScreen;
    public List<SocialPlatformList.Data> socialPlatformList;

    private List<ClientReviews.Data> reviewsList, reviewsListAll;
    private List<SocialPlatformList.Data> socialListPage;
    private List<AgentProfile.StoreList> storeList;
    private int gigId = 0, selectedPos, page = 1;

    public void setServiceList(List<SocialPlatformList.Data> socialPlatformList) {
        this.socialPlatformList = socialPlatformList;
    }

    InfluencerProfileActivityCopyVM(Application application, ActivityInfluencerProfileCopyBinding profileBinding, BaseActivity freelancerProfileActivity) {
        super(application);
        binding = profileBinding;
        activity = freelancerProfileActivity;
        reviewsList = new ArrayList<>();
        reviewsListAll = new ArrayList<>();
        socialPlatformList = new ArrayList<>();
        socialListPage = new ArrayList<>();
        storeList = new ArrayList<>();
        initData();
    }

    private void initData() {
        binding.imgBack.setOnClickListener(this);
        binding.imgShare.setOnClickListener(this);
//        binding.tvHire.setOnClickListener(this);
        binding.tvChat.setOnClickListener(this);
        binding.tvSendOffer.setOnClickListener(this);
        binding.tvSendOffer1.setOnClickListener(this);
//        binding.rlPortfolioView.setOnClickListener(this);
//        binding.relServicesAll.setOnClickListener(this);
        binding.rlAgencyView.setOnClickListener(this);
        binding.relReviewsAll.setOnClickListener(this);
//        binding.rlStoreView.setOnClickListener(this);
        binding.imgSave.setOnClickListener(this);
        binding.tvMawId.setOnClickListener(this);
        binding.tvEmail.setOnClickListener(this);
        binding.tvWhatsapp.setOnClickListener(this);

        if (activity.getIntent() != null) {
            agentData = (AgentProfile) activity.getIntent().getSerializableExtra(AGENT_PROFILE_DATA);
            isShowHire = activity.getIntent().getBooleanExtra(Constants.SHOW_HIRE, false);
            isRehire = activity.getIntent().getBooleanExtra(Constants.REHIRE, false);
            isFromChatScreen = activity.getIntent().getBooleanExtra("from", false);
            proposalData = (Proposals.Data) activity.getIntent().getSerializableExtra(Constants.USER_DATA);
        }

        if (agentData != null) {

//            getReviews(page, agentData.id);
            getSocialPlatforms(agentData.id);
            getAgency(agentData.id);
            getAgentSkills(agentData.id);
            getMyPortfolios();
            getAgentStores();
            getAgentPartners();
            getAgentCompanies();
            setUi();
        } else {
            activity.finish();
            return;
        }

        clientData = Preferences.getProfileData(activity);

//        if (isRehire) {
//            binding.tvHire.setText(activity.getString(R.string.rehire_me));
//        }

        Utils.trackAppsFlayerEvent(activity, "Freelancer_Profile_Screen");

        binding.appbar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            Log.e("lll", "" + (abs(verticalOffset) - appBarLayout.getTotalScrollRange()));
            if (abs(verticalOffset) - appBarLayout.getTotalScrollRange() == 0) {
                //  Collapsed
                binding.toolbarTitle.setVisibility(VISIBLE);
                binding.imgProfileToolbar.setVisibility(VISIBLE);
//                binding.tvSendOffer1.setVisibility(VISIBLE);
                if (agentData.show_send_offer_button == 1) {
                    toggle(true);
                }
            } else {
                //Expanded
                binding.toolbarTitle.setVisibility(View.INVISIBLE);
                binding.imgProfileToolbar.setVisibility(View.INVISIBLE);
//                binding.tvSendOffer1.setVisibility(GONE);
                if (agentData.show_send_offer_button == 1) {
                    toggle(false);
                }
            }
        });

        binding.scroll.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            // on scroll change we are checking when users scroll as bottom.
            if (viewMoreReview) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    page++;
                    getReviews(page, agentData.id);
                }
            }
        });
    }

    private void setUi() {

        if (agentData != null) {
            StringBuilder sbName = new StringBuilder();
            if (activity.getLanguage().equals("ar")) {
                if (agentData.lastName != null) {
                    sbName.append(agentData.lastName);
                }
            } else {
                if (agentData.firstName != null) {
                    sbName.append(agentData.firstName);
                }
            }
            binding.tvName.setText(sbName.toString());
            binding.toolbarTitle.setText(sbName.toString());

            if (!TextUtils.isEmpty(agentData.username)) {
                binding.tvUserName.setText("@" + agentData.username);
            }

            if (!TextUtils.isEmpty(agentData.websites)) {
                binding.tvLink.setText(String.format("%s", agentData.websites));
            }

            if (!TextUtils.isEmpty(agentData.about_me)) {
                binding.tvAboutme.setVisibility(VISIBLE);
                binding.titleAbout.setVisibility(VISIBLE);
                binding.viewAbout.setVisibility(VISIBLE);
                binding.tvAboutme.setText(String.format("%s", agentData.about_me));
            } else {
                binding.tvAboutme.setVisibility(GONE);
                binding.titleAbout.setVisibility(GONE);
                binding.viewAbout.setVisibility(GONE);
                binding.tvAboutme.setText("-");
            }
            if (agentData.is_mawthooq_number != null) {
                binding.tvMawId.setVisibility(VISIBLE);
                binding.txtMawTitle.setVisibility(VISIBLE);
//                binding.veiwMaw.setVisibility(VISIBLE);
                binding.tvMawId.setText(String.format("%s", agentData.mawthooq_id));
                binding.tvMawId.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.show_password, 0);
            } else {
                binding.tvMawId.setVisibility(GONE);
                binding.txtMawTitle.setVisibility(GONE);
//                binding.veiwMaw.setVisibility(GONE);
                binding.tvMawId.setText("-");
            }

            if (agentData.show_email == 1) {
                binding.tvEmail.setVisibility(VISIBLE);
            } else {
                binding.tvEmail.setVisibility(GONE);
            }
            if (agentData.show_whatsapp == 1) {
                binding.tvWhatsapp.setVisibility(VISIBLE);
            } else {
                binding.tvWhatsapp.setVisibility(GONE);
            }
            if (agentData.show_message_button == 1) {
                binding.tvChat.setVisibility(VISIBLE);
            } else {
                binding.tvChat.setVisibility(GONE);
            }
            if (agentData.show_send_offer_button == 1) {
                binding.tvSendOffer1.setVisibility(VISIBLE);
                binding.tvSendOffer.setVisibility(VISIBLE);
            } else {
                binding.tvSendOffer1.setVisibility(GONE);
                binding.tvSendOffer.setVisibility(GONE);
            }

            binding.tvGender.setText(agentData.gender == 1 ? activity.getString(R.string.female) : activity.getString(R.string.male));
            if (agentData.show_age == 1) {
                binding.linAge.setVisibility(VISIBLE);
                if (!TextUtils.isEmpty(agentData.birth_date)) {
                    int age = Utils.calculateAge(agentData.birth_date.split("T")[0]);
                    binding.tvAge.setText("" + age);
                } else {
                    binding.tvAge.setText("-");
                }
            } else {
                binding.linAge.setVisibility(GONE);
            }

            if (agentData.saved == 1) {
                binding.imgSave.setImageResource(R.drawable.ic_fav_fill);
            } else {
                binding.imgSave.setImageResource(R.drawable.ic_fav);
            }

            if (agentData.trustRateStatus != null && agentData.trustRateStatus.verifyId != null && agentData.trustRateStatus.verifyId == 1) {
                binding.imgVerified.setVisibility(VISIBLE);
            }
            if (agentData.min_price != 0 && agentData.max_price != 0) {
                binding.tvPriceRange.setText(agentData.min_price + " - " + agentData.max_price + " " + activity.getString(R.string.sar));
            }

//            binding.imgSave.setImageResource(R.drawable.ic_fav_fill);

            activity.setImage(binding.imgProfile, TextUtils.isEmpty(agentData.profilePic) ? "" : agentData.path + agentData.profilePic, 0, 0);
            activity.setImage(binding.imgProfileToolbar, TextUtils.isEmpty(agentData.profilePic) ? "" : agentData.path + agentData.profilePic, 0, 0);

//            setServicesAdapter(socialListPage);
            updateBlockUnblockStatus();

            if (agentData.store != null && agentData.store.size() > 0) {
                storeList.add(agentData.store.get(0));
            }
            if (agentData.store != null && agentData.store.size() > 1) {
                storeList.add(agentData.store.get(1));
            }
//            setStoreAdapter(storeList);

            if (agentData.social_platform != null && agentData.social_platform.size() > 0) {
                binding.linSocialMedia.setVisibility(VISIBLE);
                setPlatformAdapter(agentData.social_platform);
            } else {
                binding.linSocialMedia.setVisibility(GONE);
            }

            if (agentData.show_email == 1) {//public
                binding.tvEmail.setVisibility(VISIBLE);
            } else {
                binding.tvEmail.setVisibility(GONE);
            }

            if (TextUtils.isEmpty(agentData.contactNo)) {
                binding.tvWhatsapp.setVisibility(GONE);
            } else {
                binding.tvWhatsapp.setVisibility(VISIBLE);
            }

        }
    }

    public void setPlatformAdapter(List<AgentProfile.ConnectedPlatform> profilePlatformArrayList) {
        ProfilePlatformAdapter adapter = new ProfilePlatformAdapter();
        adapter.doRefresh(profilePlatformArrayList, activity);
        binding.rvPlatform.setAdapter(adapter);
    }

    public void setWorkWithAdapter(List<GetCompanies.Data> profilePlatformArrayList, String path) {
        WorkWithAdapter adapter = new WorkWithAdapter(path);
        adapter.doRefresh(profilePlatformArrayList, activity);
        binding.rvServices.setAdapter(adapter);
    }

    MyStoreAdapter storeAdapter;

    public void setStoreAdapter(List<GetStores.Data> profilePlatformArrayList, String path) {
        if (profilePlatformArrayList != null && profilePlatformArrayList.size() > 0) {
            storeAdapter = new MyStoreAdapter();
            storeAdapter.doRefresh(profilePlatformArrayList, activity, path);
            binding.rvStore.setAdapter(storeAdapter);
            binding.linStore.setVisibility(VISIBLE);
        } else {
            binding.linStore.setVisibility(GONE);
        }
    }

    PartnerAdapter partnerAdapter;

    public void setPartnerAdapter(List<GetAgentPartners.Data> profilePlatformArrayList, String path) {
        if (profilePlatformArrayList != null && profilePlatformArrayList.size() > 0) {
            partnerAdapter = new PartnerAdapter();
            partnerAdapter.doRefresh(profilePlatformArrayList, activity, path);
            binding.rvPartners.setAdapter(partnerAdapter);
            binding.linPartners.setVisibility(VISIBLE);
        } else {
            binding.linPartners.setVisibility(GONE);
        }
    }

    private void updateBlockUnblockStatus() {
//        if (agentData.blockStatus == 0) {
//            binding.tvReportBlock.setText(activity.getString(R.string.report_amp_block));
//        } else {
//            binding.tvReportBlock.setText(activity.getString(R.string.unblock));
//        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                activity.onBackPressed();
                break;
            case R.id.img_save:
                saveRemoveInfluencer(agentData.saved == 1);
                break;
            case R.id.tv_whatsapp:
                if (!TextUtils.isEmpty(agentData.contactNo)) {
                    activity.openWhatsappChat(agentData.contactNo.replaceAll("\\+", "").replaceAll(".", ""));
                }
                break;
            case R.id.tv_email:
                try {
                    /*Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setData(Uri.parse("mailto:")); // only email apps should handle this
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{agentData.email}); // Single recipient

                    String nojomLink1 = "nojom.com/" + agentData.username;
                    String fLink1 = agentData.firebaseLink.replaceAll("https://", "");

                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Profile link");
                    // Adding HTML body
                    emailIntent.putExtra(Intent.EXTRA_TEXT, fLink1 + "\n\n" + nojomLink1);
                    emailIntent.setType("text/plain");
//                    activity.startActivity(emailIntent);
                    activity.startActivity(Intent.createChooser(emailIntent, "Choose Email Client..."));*/


                    String nojomLink1 = "nojom.com/" + agentData.username;
                    String fLink1 = agentData.firebaseLink.replaceAll("https://", "");

                    Intent intent = new Intent(Intent.ACTION_SEND);
                    String[] recipients = {agentData.bussiness_email};
                    intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                    intent.putExtra(Intent.EXTRA_SUBJECT, "");
                    intent.putExtra(Intent.EXTRA_TEXT, fLink1 + "\n\n" + nojomLink1);
                    intent.setType("text/html");
                    intent.setPackage("com.google.android.gm");
                    activity.startActivity(Intent.createChooser(intent, "Send mail"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.img_share:
                if (agentData.firebaseLink != null) {
                    String nojomLink = "nojom.com/" + agentData.username;
                    String fLink = agentData.firebaseLink.replaceAll("https://", "");
                    activity.shareApp(fLink + "\n\n" + nojomLink);
                }
                break;
            case R.id.tv_mawId:
                if (agentData != null) {
                    String url;
                    if (agentData.is_mawthooq_number.equals("1")) {
                        url = "https://elaam.gamr.gov.sa/gcam-licenses/gcam-celebrity-check/" + agentData.mawthooq_id;
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        activity.startActivity(browserIntent);
                    } else {
                        url = agentData.mawthooq_path + agentData.mawthooq_id;
                        activity.viewFile(url);
                    }
                }

                break;
//            case R.id.rl_portfolio_view:
//                Intent iP = new Intent(activity, InfluencerProfileAllActivity.class);
//                iP.putExtra(Constants.AGENT_PROFILE_DATA, agentData);
//                iP.putExtra("platform", (Serializable) socialPlatformList);
//                iP.putExtra("portfolios", portfolios);
//                iP.putExtra("screen", "portfolio");
//                activity.startActivity(iP);
//                break;
//            case R.id.rel_services_all:
//
//                if (viewMoreService) {//view more case
////                    setServicesAdapter(socialPlatformList);
//                    binding.txtServiceAll.setText(activity.getString(R.string.view_less));
//                    viewMoreService = false;
//                } else {//view less
//                    binding.txtServiceAll.setText(activity.getString(R.string.view_all));
////                    setServicesAdapter(socialListPage);
//                    viewMoreService = true;
//                }
//
////                Intent iS = new Intent(activity, InfluencerProfileAllActivity.class);
////                iS.putExtra(Constants.AGENT_PROFILE_DATA, agentData);
////                iS.putExtra("platform", (Serializable) socialPlatformList);
////                iS.putExtra("screen", "services");
////                activity.startActivity(iS);
//                break;
            case R.id.rl_agency_view:

                /*if (viewMoreAgency) {
                    binding.txtAgencyAll.setText(activity.getString(R.string.view_less));
                    binding.txtAbout.setVisibility(VISIBLE);
                    binding.tvAgencyAbout.setVisibility(VISIBLE);
                    binding.txtEmail.setVisibility(VISIBLE);
                    binding.tvAgencyEmail.setVisibility(VISIBLE);
                    binding.txtAddress.setVisibility(VISIBLE);
                    binding.tvAgencyAdd.setVisibility(VISIBLE);
                    binding.txtNote.setVisibility(VISIBLE);
                    binding.tvAgencyNote.setVisibility(VISIBLE);
                    viewMoreAgency = false;
                } else {
                    binding.txtAgencyAll.setText(activity.getString(R.string.view_all));
                    binding.txtAbout.setVisibility(GONE);
                    binding.tvAgencyAbout.setVisibility(GONE);
                    binding.txtEmail.setVisibility(GONE);
                    binding.tvAgencyEmail.setVisibility(GONE);
                    binding.txtAddress.setVisibility(GONE);
                    binding.tvAgencyAdd.setVisibility(GONE);
                    binding.txtNote.setVisibility(GONE);
                    binding.tvAgencyNote.setVisibility(GONE);
                    viewMoreAgency = true;
                }*/


//                Intent iA = new Intent(activity, InfluencerProfileAllActivity.class);
//                iA.putExtra(Constants.AGENT_PROFILE_DATA, agentData);
//                iA.putExtra("platform", (Serializable) socialPlatformList);
//                iA.putExtra("screen", "agency");
//                activity.startActivity(iA);
                break;
            case R.id.rel_reviews_all:

                if (viewMoreReview) {//view more case
                    setReviewAdapter(reviewsListAll);
                    binding.txtReviewAll.setText(activity.getString(R.string.view_less));
                    viewMoreReview = false;
                } else {//view less
                    page = 1;
                    reviewsListAll = new ArrayList<>();
                    binding.txtReviewAll.setText(activity.getString(R.string.view_all));
                    setReviewAdapter(reviewsList);
                    viewMoreReview = true;
                }


//                Intent iR = new Intent(activity, InfluencerProfileAllActivity.class);
//                iR.putExtra(Constants.AGENT_PROFILE_DATA, agentData);
//                iR.putExtra("platform", (Serializable) socialPlatformList);
//                iR.putExtra("screen", "review");
//                activity.startActivity(iR);
                break;
//            case R.id.rl_store_view:
//
//                if (viewMoreStore) {//view more case
////                    setStoreAdapter(agentData.store);
//                    binding.txtStoreAll.setText(activity.getString(R.string.view_less));
//                    viewMoreStore = false;
//                } else {//view less
//                    binding.txtStoreAll.setText(activity.getString(R.string.view_all));
////                    setStoreAdapter(storeList);
//                    viewMoreStore = true;
//                }
//
////                Intent iSt = new Intent(activity, InfluencerProfileAllActivity.class);
////                iSt.putExtra(Constants.AGENT_PROFILE_DATA, agentData);
////                iSt.putExtra("platform", (Serializable) socialPlatformList);
////                iSt.putExtra("screen", "store");
////                activity.startActivity(iSt);
//                break;
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
            case R.id.tv_sendOffer:
            case R.id.tv_sendOffer1:
                Intent i = new Intent(activity, HireDescribeActivity.class);
                i.putExtra(Constants.AGENT_PROFILE_DATA, agentData);
                activity.startActivity(i);
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

    public void getReviews(int pageNo, int profileId) {
        activity.isClickableView = true;

        HashMap<String, String> map = new HashMap<>();
        map.put("agent_profile_id", profileId + "");
        map.put("page_no", pageNo + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_AGENT_REVIEW, true, map);
    }

    public void getAgentSkills(int profileId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("agent_profile_id", profileId + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_AGENT_PROFILE_SKILLS, true, map);
    }

    public void getSocialPlatforms(int profileId) {
        activity.isClickableView = true;


        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_SOCIAL_PLATFORM_LIST +
                /*"456696"*/profileId, false, null);

    }

    public void getAgency(int profileId) {
        activity.isClickableView = true;


        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_AGENCY +
                /*"456696"*/profileId, false, null);

    }

    private void getGigDetails() {
        if (!activity.isNetworkConnected()) {
            return;
        }

        ApiRequest apiRequest = new ApiRequest();

        apiRequest.apiRequest(this, activity, API_GET_CUSTOM_GIG_DETAILS + "/" + gigId, false, null);
    }

    public void getMyPortfolios() {
        if (!activity.isNetworkConnected()) return;

        HashMap<String, String> map = new HashMap<>();
        map.put("agent_profile_id", agentData.id + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_PORTFOLIO, true, map);
    }

    public void getAgentStores() {
        if (!activity.isNetworkConnected()) return;

        HashMap<String, String> map = new HashMap<>();
        map.put("agent_profile_id", agentData.id + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_AGENT_STORES, true, map);

    }

    public void getAgentPartners() {
        if (!activity.isNetworkConnected()) return;

        HashMap<String, String> map = new HashMap<>();
        map.put("agent_profile_id", agentData.id + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_AGENT_PARTNERS, true, map);

    }

    public void getAgentCompanies() {
        if (!activity.isNetworkConnected()) return;

        HashMap<String, String> map = new HashMap<>();
        map.put("agent_profile_id", agentData.id + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_AGENT_COMPANIES, true, map);

    }


    public void saveRemoveInfluencer(boolean isRemoved) {
        if (!activity.isNetworkConnected()) return;

        HashMap<String, String> map = new HashMap<>();
        map.put("agent_profile_id", agentData.id + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, isRemoved ? API_REMOVE_AGENT : API_SAVE_AGENT, true, map);
    }

    ReviewsAdapter reviewsAdapter;

    private void setReviewAdapter(List<ClientReviews.Data> reviewsList) {
        if (reviewsList != null && reviewsList.size() > 0) {
            if (reviewsAdapter == null) {
                reviewsAdapter = new ReviewsAdapter(activity);
                reviewsAdapter.doRefresh(reviewsList);
                binding.rvLinkedin.setAdapter(reviewsAdapter);
            } else {
                reviewsAdapter.doRefresh(reviewsList);
            }

            binding.rvLinkedin.setVisibility(View.VISIBLE);
            binding.linReviews.setVisibility(VISIBLE);
        } else {
            binding.linReviews.setVisibility(View.GONE);
        }
    }

    InfluencerServiceAdapter influencerServiceAdapter;

    private void setServicesAdapter(List<SocialPlatformList.Data> serviceList) {
        if (serviceList != null && serviceList.size() > 0) {
            influencerServiceAdapter = new InfluencerServiceAdapter(activity, serviceList, InfluencerProfileActivityCopyVM.this);
            binding.rvServices.setAdapter(influencerServiceAdapter);
            binding.rvServices.setVisibility(View.VISIBLE);
            binding.linServices.setVisibility(VISIBLE);
        } else {
            binding.linServices.setVisibility(GONE);
        }
    }

    Portfolios portfolios;

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        if (url.equalsIgnoreCase(API_GET_AGENT_REVIEW)) {
            ClientReviews agentReviews = ClientReviews.getClientReviews(responseBody);
            if (agentReviews != null && agentReviews.data != null) {
                if (page == 1) {
                    reviewsList.add(agentReviews.data.get(0));
                    reviewsList.add(agentReviews.data.get(1));
                }
                reviewsListAll.addAll(agentReviews.data);
            }
//            binding.shimmerLayoutReview.setVisibility(View.GONE);
//            binding.shimmerLayoutReview.stopShimmer();
            setReviewAdapter(page == 1 ? reviewsList : reviewsListAll);
        } else if (url.equalsIgnoreCase(API_GET_AGENCY + /*"456696"*/agentData.id)) {

            AgencyList socialList = AgencyList.getAgencyList(responseBody);
            agentData.agent_agency = socialList.data;

            activity.runOnUiThread(() -> {
                if (agentData.agent_agency != null && agentData.agent_agency.size() > 0) {
                    binding.linAgency.setVisibility(VISIBLE);
                    binding.txtAgencyName.setText(agentData.agent_agency.get(0).name);
                    binding.txtAgencyNo.setText(agentData.agent_agency.get(0).phone);
                    binding.txtAgencyWbsite.setText(agentData.agent_agency.get(0).website);
//                    binding.tvAgencyEmail.setText(agentData.agent_agency.get(0).email);
                    binding.txtAgencyLocation.setText(agentData.agent_agency.get(0).address);
//                    binding.tvAgencyNote.setText(agentData.agent_agency.get(0).note);
//                    binding.tvAgencyAbout.setText(agentData.agent_agency.get(0).about);
//                    activity.setImage(binding.imgProfile, TextUtils.isEmpty(agentData.profilePic) ? "" : agentData.path + agentData.profilePic, 0, 0);

                }
            });

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
        } else if (url.equalsIgnoreCase(API_GET_PROFILE_INFO)) {
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
        } else if (url.equalsIgnoreCase(API_GET_PORTFOLIO)) {
            portfolios = Portfolios.getPortfolios(responseBody);
            if (portfolios != null && portfolios.data != null && portfolios.data.size() > 0) {
                binding.linPortfolio.setVisibility(VISIBLE);
                setPortfolioAdapter(portfolios.data, portfolios.path, portfolios.company_path);
            } else {
                binding.linPortfolio.setVisibility(GONE);
            }
        } else if (url.equalsIgnoreCase(API_REMOVE_AGENT) || url.equalsIgnoreCase(API_SAVE_AGENT)) {
            activity.toastMessage(message);
            SavedInfluencer savedInf = SavedInfluencer.getData(responseBody);
            agentData.saved = savedInf.saved;
            if (savedInf.saved == 1) {
                binding.imgSave.setImageResource(R.drawable.ic_fav_fill);
            } else {
                binding.imgSave.setImageResource(R.drawable.ic_fav);
            }
        } else if (url.equalsIgnoreCase(API_GET_AGENT_PROFILE_SKILLS)) {
            try {
                ProfileSkills profileSkills = ProfileSkills.getProfileSkills(responseBody);

                if (profileSkills != null && profileSkills.data != null && profileSkills.data.size() > 0) {
                    binding.chipView.setVisibility(VISIBLE);
                    binding.txtCatTitle.setVisibility(VISIBLE);
//                    binding.veiwCat.setVisibility(VISIBLE);
//                    setTagsAdapter(profileSkills.data);
                } else {
                    binding.chipView.setVisibility(GONE);
                    binding.txtCatTitle.setVisibility(GONE);
//                    binding.veiwCat.setVisibility(GONE);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (url.equalsIgnoreCase(API_GET_AGENT_STORES)) {
            GetStores stores = GetStores.getStores(responseBody);
            if (stores != null && stores.data != null && stores.data.size() > 0) {
                binding.linStore.setVisibility(VISIBLE);
                setStoreAdapter(stores.data, stores.path);
            } else {
                binding.linStore.setVisibility(GONE);
            }
        } else if (url.equalsIgnoreCase(API_GET_AGENT_PARTNERS)) {
            GetAgentPartners stores = GetAgentPartners.getStores(responseBody);
            if (stores != null && stores.data != null && stores.data.size() > 0) {
                binding.linStore.setVisibility(VISIBLE);
                setPartnerAdapter(stores.data, stores.path);
            } else {
                binding.linStore.setVisibility(GONE);
            }
        } else if (url.equalsIgnoreCase(API_GET_AGENT_COMPANIES)) {
            GetCompanies companies = GetCompanies.getCompanies(responseBody);
            if (companies != null && companies.data != null && companies.data.size() > 0) {
                binding.linServices.setVisibility(VISIBLE);
                setWorkWithAdapter(companies.data, companies.path);
            } else {
                binding.linServices.setVisibility(GONE);
            }
        } else {
            SocialPlatformList socialList = SocialPlatformList.getSocialPlatforms(responseBody);
            socialPlatformList = socialList.data;
            //setServiceList(socialPlatformList);
            if (socialList.data.size() > 0) {
                socialListPage.add(socialList.data.get(0));
            }
            if (socialList.data.size() > 1) {
                socialListPage.add(socialList.data.get(1));
            }
//            setPlatformAdapter(socialPlatformList);
//            setServicesAdapter(socialListPage);
        }
        activity.isClickableView = false;
    }

    /*private void setTagsAdapter(List<ProfileSkills.Skill> data) {
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(activity);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        binding.chipView.setLayoutManager(layoutManager);
        SkillsListAdapter skillsListAdapter = new SkillsListAdapter(activity, data);
        binding.chipView.setAdapter(skillsListAdapter);
    }*/

    private void setPortfolioAdapter(List<Portfolios.Data> data, String filePath, String companyPath) {
//        InfluPortfolioAdapter portfolioFileAdapter = new InfluPortfolioAdapter(activity, data, filePath);
//        binding.rvPortfolio.setAdapter(portfolioFileAdapter);

        List<Portfolios.Data> updatedList = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            try {
                if (i % 3 == 0) {
                    updatedList.add(data.get(i));
                } else {
                    int n = i;
                    List<Portfolios.Data> twoList = new ArrayList<>();
                    twoList.add(data.get(i));
                    i++;
                    if (i <= data.size() - 1) {
                        twoList.add(data.get(i));
                    }
                    data.get(n).data = twoList;
                    updatedList.add(data.get(n));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        CustomAdapter adapter = new CustomAdapter(activity, updatedList, filePath, companyPath);
        binding.rvPortfolio.setAdapter(adapter);
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        activity.isClickableView = false;
        if (url.equalsIgnoreCase(API_GET_CUSTOM_GIG_DETAILS + "/" + gigId)) {
            activity.runOnUiThread(() -> {
                if (influencerServiceAdapter != null) {
                    influencerServiceAdapter.getData().get(selectedPos).isShowProgress = false;
                    influencerServiceAdapter.notifyItemChanged(selectedPos);
                }
            });
            gigId = 0;
            selectedPos = 0;
        } else if (url.equalsIgnoreCase(API_GET_PROFILE_INFO)) {
            activity.finish();
        } else if (url.equalsIgnoreCase(API_GET_AGENCY + /*"456696"*/agentData.id)) {
            binding.linAgency.setVisibility(GONE);
        } else if (url.equalsIgnoreCase(API_GET_PORTFOLIO)) {
            binding.linPortfolio.setVisibility(GONE);
        } else if (url.equalsIgnoreCase(API_REMOVE_AGENT) || url.equalsIgnoreCase(API_SAVE_AGENT)) {
            activity.toastMessage(message);
        } else if (url.equals(API_GET_AGENT_REVIEW)) {
            setReviewAdapter(reviewsList);
        } else if (url.equalsIgnoreCase(API_GET_AGENT_PROFILE_SKILLS)) {
            binding.chipView.setVisibility(GONE);
            binding.txtCatTitle.setVisibility(GONE);
//            binding.veiwCat.setVisibility(GONE);
        } else {
//            setServicesAdapter(socialListPage);
        }
//        binding.shimmerLayoutReview.setVisibility(View.GONE);
//        binding.shimmerLayoutReview.stopShimmer();


    }

    @Override
    public void onClickService(SocialPlatformList.Data data, int pos) {
        gigId = data.id;
        selectedPos = pos;
        getGigDetails();
    }

    private void toggle(boolean show) {
        Transition transition = new Slide(Gravity.BOTTOM);
        transition.setDuration(400/*show ? 400 : 600*/);
        transition.addTarget(binding.tvSendOffer1);

        TransitionManager.beginDelayedTransition(binding.relParent, transition);
        binding.tvSendOffer1.setVisibility(show ? View.VISIBLE : View.GONE);
    }

}
