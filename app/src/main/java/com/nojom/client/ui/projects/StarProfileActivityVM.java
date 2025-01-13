package com.nojom.client.ui.projects;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.nojom.client.util.Constants.AGENT_PROFILE_DATA;
import static com.nojom.client.util.Constants.API_GET_AGENT_COMPANIES;
import static com.nojom.client.util.Constants.API_GET_AGENT_PARTNERS;
import static com.nojom.client.util.Constants.API_GET_AGENT_STORES;
import static com.nojom.client.util.Constants.API_GET_AGENT_YOUTUBE;
import static com.nojom.client.util.Constants.API_GET_PORTFOLIO;
import static com.nojom.client.util.Constants.API_GET_SOCIAL_PLATFORM_LIST;
import static java.lang.Math.abs;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.nojom.client.R;
import com.nojom.client.adapter.CustomAdapter;
import com.nojom.client.adapter.MyStoreAdapter;
import com.nojom.client.adapter.PartnerAdapter;
import com.nojom.client.adapter.ProfileYoutubeAdapter;
import com.nojom.client.adapter.SelectedServiceAdapter;
import com.nojom.client.adapter.SkillsListAdapter;
import com.nojom.client.adapter.SocialMediaAdapterProfile;
import com.nojom.client.adapter.WorkWithAdapter;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivityProfileStarsBinding;
import com.nojom.client.databinding.ViewAgencyBinding;
import com.nojom.client.databinding.ViewMyStoreBinding;
import com.nojom.client.databinding.ViewOverviewBinding;
import com.nojom.client.databinding.ViewPartnerBinding;
import com.nojom.client.databinding.ViewPortfolioBinding;
import com.nojom.client.databinding.ViewServicesBinding;
import com.nojom.client.databinding.ViewSocialMediaBinding;
import com.nojom.client.databinding.ViewWorkwithBinding;
import com.nojom.client.databinding.ViewYoutubeBinding;
import com.nojom.client.model.AgentProfile;
import com.nojom.client.model.ClientReviews;
import com.nojom.client.model.GetAgentPartners;
import com.nojom.client.model.GetCompanies;
import com.nojom.client.model.GetStores;
import com.nojom.client.model.GetYoutube;
import com.nojom.client.model.InfServices;
import com.nojom.client.model.Portfolios;
import com.nojom.client.model.Profile;
import com.nojom.client.model.ProfileMenu;
import com.nojom.client.model.Proposals;
import com.nojom.client.model.Serv;
import com.nojom.client.model.SocialPlatformList;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

class StarProfileActivityVM extends AndroidViewModel implements View.OnClickListener, RequestResponseListener, BaseActivity.OnProfileLoadListener, SelectedServiceAdapter.OnClickServiceListener {
    private final ActivityProfileStarsBinding binding;
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
    List<ProfileMenu> profileMenuListOrigin;

    private MutableLiveData<List<SocialPlatformList.Data>> socialMediaListMutableData = new MutableLiveData<>();
    private MutableLiveData<Portfolios> portfolioListMutableData = new MutableLiveData<>();
    private MutableLiveData<GetCompanies> workWithMutableData = new MutableLiveData<>();
    private MutableLiveData<GetStores> storeMutableData = new MutableLiveData<>();
    private MutableLiveData<GetAgentPartners> partnerMutableData = new MutableLiveData<>();
    private MutableLiveData<GetYoutube> youtubeMutableData = new MutableLiveData<>();

    public void setServiceList(List<SocialPlatformList.Data> socialPlatformList) {
        this.socialPlatformList = socialPlatformList;
    }

    private GetServiceActivityVM serviceActivityVM;

    StarProfileActivityVM(Application application, ActivityProfileStarsBinding profileBinding, BaseActivity freelancerProfileActivity) {
        super(application);
        binding = profileBinding;
        activity = freelancerProfileActivity;
        reviewsList = new ArrayList<>();
        reviewsListAll = new ArrayList<>();
        socialPlatformList = new ArrayList<>();
        socialListPage = new ArrayList<>();
        storeList = new ArrayList<>();
        activity.setOnProfileLoadListener(this);
        initData();
    }

    private void initData() {
        binding.imgBack.setOnClickListener(this);
        binding.imgShare.setOnClickListener(this);
        binding.btnContinuePrice.setOnClickListener(this);
        binding.imgShare.setVisibility(View.INVISIBLE);

        serviceActivityVM = ViewModelProviders.of(activity).get(GetServiceActivityVM.class);
        serviceActivityVM.init(activity);

        if (activity.getIntent() != null) {
            agentData = (AgentProfile) activity.getIntent().getSerializableExtra(AGENT_PROFILE_DATA);
            isShowHire = activity.getIntent().getBooleanExtra(Constants.SHOW_HIRE, false);
            isRehire = activity.getIntent().getBooleanExtra(Constants.REHIRE, false);
            isFromChatScreen = activity.getIntent().getBooleanExtra("from", false);
            proposalData = (Proposals.Data) activity.getIntent().getSerializableExtra(Constants.USER_DATA);
        }

        clientData = Preferences.getProfileData(activity);

        setUi();
    }

    private void setUi() {

        profileMenuListOrigin = new ArrayList<>();
        profileMenuListOrigin.add(new ProfileMenu(activity.getString(R.string.social_media), 1));
        profileMenuListOrigin.add(new ProfileMenu(activity.getString(R.string.overview), 2));
        profileMenuListOrigin.add(new ProfileMenu(activity.getString(R.string.portfolio), 3));
        profileMenuListOrigin.add(new ProfileMenu(activity.getString(R.string.work_with_1), 4));
        profileMenuListOrigin.add(new ProfileMenu(activity.getString(R.string.my_stores), 5));
        profileMenuListOrigin.add(new ProfileMenu(activity.getString(R.string.youtube), 6));
        profileMenuListOrigin.add(new ProfileMenu(activity.getString(R.string.partners), 7));
        profileMenuListOrigin.add(new ProfileMenu(activity.getString(R.string.agency), 8));


        if (agentData != null) {
            setPreview();
            StringBuilder stringBuilder = new StringBuilder();
            if (agentData.firstName != null) {
//                binding.tvName.setTextColor(getColor(R.color.black));
                stringBuilder.append(agentData.firstName);

            }
            if (agentData.lastName != null) {
//                binding.tvName.setTextColor(getColor(R.color.black));
                stringBuilder.append(" ");
                stringBuilder.append(agentData.lastName);
            }
            binding.tvName.setText(stringBuilder.toString());
            binding.toolbarTitle.setText(stringBuilder.toString());

            if (agentData.username != null) {
//                binding.tvUserName.setTextColor(getColor(R.color.black));
                binding.tvUserName.setText(String.format(activity.getString(R.string.nojom_com_s), agentData.username));
            }
            if (agentData.websites != null) {
                binding.tvLink.setTextColor(activity.getResources().getColor(R.color.black));
                binding.tvLink.setText(agentData.websites);
            }

            activity.setImage(binding.imgProfile, TextUtils.isEmpty(agentData.profilePic) ? "" : agentData.path + agentData.profilePic, 0, 0);
            activity.setImage(binding.imgProfileToolbar, TextUtils.isEmpty(agentData.profilePic) ? "" : agentData.path + agentData.profilePic, 0, 0);

            binding.linearCustom.removeAllViews();

            if (agentData.settings_order != null) {
                List<String> list = new ArrayList<>(Arrays.asList(agentData.settings_order.split(",")));
                for (String item : list) {

                    switch (item) {
                        case "1":
                            addSocialMediaLayout();
                            break;
                        case "2":
                            addOverviewLayout();
                            break;
                        case "3":
                            addPortfolioLayout();
                            break;
                        case "4":
                            addWorkWithLayout();
                            break;
                        case "5":
                            addMyStoreLayout();
                            break;
                        case "6":
                            addYoutubeLayout();
                            break;
                        case "7":
                            addPartnerLayout();
                            break;
                        case "8":
                            addAgencyLayout();
                            break;
                        case "9":
                            addInfluencerServiceLayout();
                            break;
                    }

                }
            }
        }


        binding.appbar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            Log.e("lll", "" + (abs(verticalOffset) - appBarLayout.getTotalScrollRange()));
            if (abs(verticalOffset) - appBarLayout.getTotalScrollRange() == 0) {
                //  Collapsed
                binding.toolbarTitle.setVisibility(VISIBLE);
                binding.imgProfileToolbar.setVisibility(VISIBLE);
//                binding.tvSendOffer1.setVisibility(VISIBLE);
            } else {
                //Expanded
                binding.toolbarTitle.setVisibility(View.INVISIBLE);
                binding.imgProfileToolbar.setVisibility(View.INVISIBLE);
//                binding.tvSendOffer1.setVisibility(GONE);
            }
        });

    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        if (url.equalsIgnoreCase(API_GET_PORTFOLIO)) {
            Portfolios portfolios = Portfolios.getPortfolios(responseBody);
            if (portfolios != null && portfolios.data != null && portfolios.data.size() > 0) {
                portfolioListMutableData.postValue(portfolios);
            }
        } else if (url.equalsIgnoreCase(API_GET_AGENT_COMPANIES)) {
            GetCompanies companies = GetCompanies.getCompanies(responseBody);
            if (companies != null && companies.data != null && companies.data.size() > 0) {
//                setWorkWithAdapter(companies.data, companies.path);
                workWithMutableData.postValue(companies);
            }
        } else if (url.equalsIgnoreCase(API_GET_AGENT_STORES)) {
            GetStores stores = GetStores.getStores(responseBody);
            if (stores != null && stores.data != null && stores.data.size() > 0) {
//                setStoreAdapter(stores.data, stores.path);
                storeMutableData.postValue(stores);
            }
        } else if (url.equalsIgnoreCase(API_GET_AGENT_PARTNERS)) {
            GetAgentPartners stores = GetAgentPartners.getStores(responseBody);
            if (stores != null && stores.data != null && stores.data.size() > 0) {
                partnerMutableData.postValue(stores);
            }
        } else if (url.equalsIgnoreCase(API_GET_AGENT_YOUTUBE)) {
            GetYoutube stores = GetYoutube.getYoutubeList(responseBody);
            if (stores != null && stores.data != null && stores.data.size() > 0) {
                youtubeMutableData.postValue(stores);
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
            socialMediaListMutableData.postValue(socialPlatformList);
        }
        activity.isClickableView = false;
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        activity.isClickableView = false;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.img_back) {
            activity.onBackPressed();
        } else if (view.getId() == R.id.img_share) {

        } else if (view.getId() == R.id.btn_continue_price) {
            Intent intent = new Intent(activity, InfluencerServActivity.class);
            intent.putExtra("data", influencerServices);
            intent.putExtra(AGENT_PROFILE_DATA, agentData);
            intent.putExtra("social", connectedMediaList);
            activity.startActivity(intent);
        }
    }

    @Override
    public void onProfileLoad(Profile data) {

    }

    private void setPreview() {
        if (getEmailStatus() == 3 && getWhatsappStatus() == 3 && getAcceptOfferStatus() == 3) {
            msg();
        } else if (getWhatsappStatus() != 3 && getAcceptOfferStatus() != 3 && getEmailStatus() != 3) {
            businessEmailWhatsapp(false);
        } else if (getEmailStatus() != 3 && getAcceptOfferStatus() != 3) {
            msgEmailOffer();
        } else if (getEmailStatus() == 3 && getAcceptOfferStatus() != 3 && getWhatsappStatus() != 3) {
            msgWhatsappOffer(true);
        } else if (getEmailStatus() != 3 && getAcceptOfferStatus() == 3 && getWhatsappStatus() != 3) {
            businessEmailWhatsapp(true);
        } else if (getEmailStatus() == 3 && getAcceptOfferStatus() == 3 && getWhatsappStatus() != 3) {
            msgWhatsapp(true);
        } else if (getWhatsappStatus() != 3 && getAcceptOfferStatus() != 3) {
            msgWhatsappOffer(false);
        } else if (getAcceptOfferStatus() != 3 && getEmailStatus() == 3 && getWhatsappStatus() == 3) {
            msgOffer();
        }
    }

    private void businessEmailWhatsapp(boolean offerGone) {
        binding.linPreview.removeAllViews();
        binding.txtOffer.setVisibility(offerGone ? View.GONE : View.VISIBLE);
        for (int i = 0; i < 3; i++) {
            View view = LayoutInflater.from(activity).inflate(R.layout.item_textview, binding.linPreview, false);
            TextView txtView = view.findViewById(R.id.txt_view1);
            switch (i) {
                case 0:
                    txtView.setText(activity.getString(R.string.whatsapp));
                    break;
                case 1:
                    txtView.setText(activity.getString(R.string.email));
                    break;
                case 2:
                    txtView.setText(activity.getString(R.string.message));
                    break;
            }
            txtView.setBackground(activity.getResources().getDrawable(R.drawable.gray_button_bg));
            txtView.setTextColor(activity.getResources().getColor(R.color.C_020814));
            binding.linPreview.addView(view);
        }
    }

    private void msgOffer() {
        binding.linPreview.removeAllViews();
        binding.txtOffer.setVisibility(View.GONE);
        for (int i = 0; i < 2; i++) {
            View view = LayoutInflater.from(activity).inflate(R.layout.item_textview, binding.linPreview, false);
            TextView txtView = view.findViewById(R.id.txt_view1);
            switch (i) {
                case 0:
                    txtView.setText(activity.getString(R.string.message));
                    txtView.setBackground(activity.getResources().getDrawable(R.drawable.gray_button_bg));
                    txtView.setTextColor(activity.getResources().getColor(R.color.C_020814));
                    break;
                case 1:
                    txtView.setText(activity.getString(R.string.send_offer));
                    txtView.setBackground(activity.getResources().getDrawable(R.drawable.light_black_bg_7));
                    DrawableCompat.setTint(txtView.getBackground(), ContextCompat.getColor(activity, R.color.black));
                    txtView.setTextColor(activity.getResources().getColor(R.color.white));
                    break;
            }

            binding.linPreview.addView(view);
        }
    }

    private void msg() {
        binding.linPreview.removeAllViews();
        binding.txtOffer.setVisibility(View.GONE);

        View view = LayoutInflater.from(activity).inflate(R.layout.item_textview, binding.linPreview, false);
        TextView txtView = view.findViewById(R.id.txt_view1);
        txtView.setText(activity.getString(R.string.message));
        txtView.setBackground(activity.getResources().getDrawable(R.drawable.gray_button_bg));
        txtView.setTextColor(activity.getResources().getColor(R.color.C_020814));
        binding.linPreview.addView(view);
    }


    private void msgWhatsappOffer(boolean offerGone) {
        binding.linPreview.removeAllViews();
        binding.txtOffer.setVisibility(offerGone ? View.GONE : View.VISIBLE);
        for (int i = 0; i < 3; i++) {
            View view = LayoutInflater.from(activity).inflate(R.layout.item_textview, binding.linPreview, false);
            TextView txtView = view.findViewById(R.id.txt_view1);
            switch (i) {
                case 0:
                    txtView.setText(activity.getString(R.string.message));
                    txtView.setBackground(activity.getResources().getDrawable(R.drawable.gray_button_bg));
                    txtView.setTextColor(activity.getResources().getColor(R.color.C_020814));
                    break;
                case 1:
                    txtView.setText(activity.getString(R.string.whatsapp));
                    txtView.setBackground(activity.getResources().getDrawable(R.drawable.gray_button_bg));
                    txtView.setTextColor(activity.getResources().getColor(R.color.C_020814));
                    break;
                case 2:
                    txtView.setText(activity.getString(R.string.send_offer));
                    txtView.setBackground(activity.getResources().getDrawable(R.drawable.light_black_bg_7));
                    DrawableCompat.setTint(txtView.getBackground(), ContextCompat.getColor(activity, R.color.black));
                    txtView.setTextColor(activity.getResources().getColor(R.color.white));
                    break;
            }

            binding.linPreview.addView(view);
        }
    }

    private void msgEmailOffer() {
        binding.linPreview.removeAllViews();
        binding.txtOffer.setVisibility(View.GONE);
        for (int i = 0; i < 3; i++) {
            View view = LayoutInflater.from(activity).inflate(R.layout.item_textview, binding.linPreview, false);
            TextView txtView = view.findViewById(R.id.txt_view1);
            switch (i) {
                case 0:
                    txtView.setText(activity.getString(R.string.message));
                    txtView.setBackground(activity.getResources().getDrawable(R.drawable.gray_button_bg));
                    txtView.setTextColor(activity.getResources().getColor(R.color.C_020814));
                    break;
                case 1:
                    txtView.setText(activity.getString(R.string.email));
                    txtView.setBackground(activity.getResources().getDrawable(R.drawable.gray_button_bg));
                    txtView.setTextColor(activity.getResources().getColor(R.color.C_020814));
                    break;
                case 2:
                    txtView.setText(activity.getString(R.string.send_offer));
                    txtView.setBackground(activity.getResources().getDrawable(R.drawable.light_black_bg_7));
                    DrawableCompat.setTint(txtView.getBackground(), ContextCompat.getColor(activity, R.color.black));
                    txtView.setTextColor(activity.getResources().getColor(R.color.white));
                    break;
            }

            binding.linPreview.addView(view);
        }
    }

    private void msgWhatsapp(boolean offerGone) {
        binding.linPreview.removeAllViews();
        binding.txtOffer.setVisibility(offerGone ? View.GONE : View.VISIBLE);
        for (int i = 0; i < 2; i++) {
            View view = LayoutInflater.from(activity).inflate(R.layout.item_textview, binding.linPreview, false);
            TextView txtView = view.findViewById(R.id.txt_view1);
            switch (i) {
                case 0:
                    txtView.setText(activity.getString(R.string.message));
                    txtView.setBackground(activity.getResources().getDrawable(R.drawable.gray_button_bg));
                    txtView.setTextColor(activity.getResources().getColor(R.color.C_020814));
                    break;
                case 1:
                    txtView.setText(activity.getString(R.string.whatsapp));
                    txtView.setBackground(activity.getResources().getDrawable(R.drawable.gray_button_bg));
                    txtView.setTextColor(activity.getResources().getColor(R.color.C_020814));
                    break;
            }

            binding.linPreview.addView(view);
        }
    }

    private int getAcceptOfferStatus() {
        return agentData.show_send_offer_button;
    }

    private int getEmailStatus() {
        return agentData.show_email;
    }

    private int getWhatsappStatus() {
        return agentData.show_whatsapp;
    }

    ArrayList<SocialPlatformList.Data> connectedMediaList;

    private void addSocialMediaLayout() {
        ViewSocialMediaBinding socialMediaBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.view_social_media, null, false);
        socialMediaBinding.txtName.setText(activity.getString(R.string.social_media));

        getSocialPlatforms(agentData.id);

        socialMediaListMutableData.observe(activity, data -> {
            List<SocialPlatformList.Data> filteredList;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                filteredList = data.stream().filter(obj -> (obj.public_status != 3)).collect(Collectors.toList());
            } else {
                filteredList = data;
            }
            connectedMediaList = (ArrayList<SocialPlatformList.Data>) filteredList;

            SocialMediaAdapterProfile adapter = new SocialMediaAdapterProfile();
            adapter.doRefresh(filteredList, activity);
            socialMediaBinding.rvSocial.setAdapter(adapter);
        });

        binding.linearCustom.addView(socialMediaBinding.getRoot());
    }


    public void getSocialPlatforms(int profileId) {
        activity.isClickableView = true;


        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_SOCIAL_PLATFORM_LIST +
                /*"456696"*/profileId, false, null);

    }

    private void addOverviewLayout() {
        ViewOverviewBinding overviewBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.view_overview, null, false);

        overviewBinding.titleAbout.setText(activity.getString(R.string.about_me));
        overviewBinding.txtCatTitle.setText(activity.getString(R.string.category));
        overviewBinding.txtPriceTitle.setText(activity.getString(R.string.price_range));
        overviewBinding.txtMawTitle.setText(activity.getString(R.string.mawthooq));
        overviewBinding.txtGenTitle.setText(activity.getString(R.string.gender));
        overviewBinding.txtAgeTitle.setText(activity.getString(R.string.age));

        if (agentData != null) {

            overviewBinding.tvAboutme.setText(agentData.about_me);
            overviewBinding.tvMawId.setText("-");
            if (agentData.gender == 1) {
                overviewBinding.tvGender.setText(activity.getString(R.string.male));
            } else if (agentData.gender == 2) {
                overviewBinding.tvGender.setText(activity.getString(R.string.female));
            } else if (agentData.gender == 3) {
                overviewBinding.tvGender.setText(activity.getString(R.string.others));
            }
            if (agentData.min_price != null && agentData.max_price != null) {
                overviewBinding.tvPriceRange.setText(String.format("%s - %s %s", agentData.min_price, agentData.max_price, activity.getCurrency().equals("ar") ? activity.getString(R.string.sar) : activity.getString(R.string.dollar)));
            }

            if (agentData.show_age == 1) {
                overviewBinding.linAge.setVisibility(VISIBLE);
                if (!TextUtils.isEmpty(agentData.birth_date)) {
                    int age = Utils.calculateAge(agentData.birth_date.split("T")[0]);
                    overviewBinding.tvAge.setText("" + age);
                } else {
                    overviewBinding.tvAge.setText("-");
                }
            } else {
                overviewBinding.linAge.setVisibility(GONE);
            }

            if (agentData.agent_categories != null && agentData.agent_categories.size() > 0) {
                FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(activity);
                layoutManager.setFlexDirection(FlexDirection.ROW);
                layoutManager.setJustifyContent(JustifyContent.FLEX_START);
                layoutManager.setFlexWrap(FlexWrap.WRAP);
                overviewBinding.chipView.setLayoutManager(layoutManager);
                SkillsListAdapter skillsListAdapter = new SkillsListAdapter(activity, agentData.agent_categories);
                overviewBinding.chipView.setAdapter(skillsListAdapter);
            }

            if (agentData.agent_tags != null && agentData.agent_tags.size() > 0) {
                FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(activity);
                layoutManager.setFlexDirection(FlexDirection.ROW);
                layoutManager.setJustifyContent(JustifyContent.FLEX_START);
                layoutManager.setFlexWrap(FlexWrap.WRAP);
                overviewBinding.chipViewTags.setLayoutManager(layoutManager);
                SkillsListAdapter skillsListAdapter = new SkillsListAdapter(activity, agentData.agent_tags);
                skillsListAdapter.setTag(true);
                overviewBinding.chipViewTags.setAdapter(skillsListAdapter);
            }
        }

        binding.linearCustom.addView(overviewBinding.getRoot());
    }

    String filePath, companyPath;

    private void addPortfolioLayout() {
        ViewPortfolioBinding portfolioBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.view_portfolio, null, false);
        getMyPortfolios();

        portfolioListMutableData.observe(activity, data -> {
            if (data != null && data.data != null) {
                companyPath = data.company_path;
                filePath = data.path;
                List<Portfolios.Data> updatedList = new ArrayList<>();
                for (int i = 0; i < data.data.size(); i++) {
                    try {
                        if (i % 3 == 0) {
                            updatedList.add(data.data.get(i));
                        } else {
                            int n = i;
                            List<Portfolios.Data> twoList = new ArrayList<>();
                            twoList.add(data.data.get(i));
                            i++;
                            if (i <= data.data.size() - 1) {
                                twoList.add(data.data.get(i));
                            }
                            data.data.get(n).data = twoList;
                            updatedList.add(data.data.get(n));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                CustomAdapter adapter = new CustomAdapter(activity, updatedList, filePath, companyPath);
                portfolioBinding.rvPortfolio.setAdapter(adapter);

            }
        });


        binding.linearCustom.addView(portfolioBinding.getRoot());
    }

    public void getMyPortfolios() {
        if (!activity.isNetworkConnected()) return;

        HashMap<String, String> map = new HashMap<>();
        map.put("agent_profile_id", agentData.id + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_PORTFOLIO, true, map);
    }

    private void addWorkWithLayout() {
        ViewWorkwithBinding workwithBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.view_workwith, null, false);
        getAgentCompanies();

        AtomicReference<GridLayoutManager> gridLayoutManager = new AtomicReference<>(new GridLayoutManager(activity, 2, RecyclerView.HORIZONTAL, false));

        workWithMutableData.observe(activity, getAgentCompanies -> {
            if (getAgentCompanies.data != null && getAgentCompanies.data.size() > 0) {

                if (getAgentCompanies.data.size() > 2) {
                    gridLayoutManager.set(new GridLayoutManager(activity, 2, RecyclerView.HORIZONTAL, false));
                    workwithBinding.rvPortfolio.setLayoutManager(gridLayoutManager.get());
                } else {
                    gridLayoutManager.set(new GridLayoutManager(activity, 1, RecyclerView.HORIZONTAL, false));
                    workwithBinding.rvPortfolio.setLayoutManager(gridLayoutManager.get());
                }

                WorkWithAdapter adapter = new WorkWithAdapter(getAgentCompanies.path);
                adapter.doRefresh(getAgentCompanies.data, activity);
                adapter.setPath(getAgentCompanies.path);
                workwithBinding.rvPortfolio.setAdapter(adapter);
            }
        });

        binding.linearCustom.addView(workwithBinding.getRoot());
    }

    public void getAgentCompanies() {
        if (!activity.isNetworkConnected()) return;

        HashMap<String, String> map = new HashMap<>();
        map.put("agent_profile_id", agentData.id + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_AGENT_COMPANIES, true, map);

    }

    private void addMyStoreLayout() {
        ViewMyStoreBinding myStoreBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.view_my_store, null, false);
        myStoreBinding.txtStores.setText(activity.getString(R.string.my_stores));
        myStoreBinding.txtProduct.setText(activity.getString(R.string.my_product));
        getAgentStores();

        storeMutableData.observe(activity, getCompanies -> {
            if (getCompanies != null && getCompanies.data != null && getCompanies.data.size() > 0) {
//                storeList = getCompanies;
                myStoreBinding.txtStores.setVisibility(VISIBLE);

                MyStoreAdapter storeAdapter = new MyStoreAdapter();
                storeAdapter.doRefresh(getCompanies.data, activity, getCompanies.path);
                myStoreBinding.rvPortfolio.setAdapter(storeAdapter);
            } else {
                myStoreBinding.txtStores.setVisibility(GONE);
            }
        });

        /*myStoreActivityVM.getProductDataList().observe(this, getCompanies -> {
            if (getCompanies != null && getCompanies.data != null && getCompanies.data.size() > 0) {
                productList = getCompanies;

                List<GetProduct.Data> filteredList;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    filteredList = getCompanies.data.stream().filter(obj -> isSelectPublic ? (obj.public_status == 1) : (obj.public_status != 3)).collect(Collectors.toList());
                } else {
                    filteredList = getCompanies.data;
                }
                if (filteredList.size() > 0) {
                    myStoreBinding.txtProduct.setVisibility(VISIBLE);
                } else {
                    myStoreBinding.txtProduct.setVisibility(GONE);
                }

                ProfileProductsAdapter productAdapter = new ProfileProductsAdapter(this);
                productAdapter.doRefresh(productList.path);
                productAdapter.doRefresh(filteredList);
                myStoreBinding.rvProduct.setAdapter(productAdapter);
            } else {
                myStoreBinding.txtProduct.setVisibility(GONE);
            }
        });*/
        binding.linearCustom.addView(myStoreBinding.getRoot());
    }

    public void getAgentStores() {
        if (!activity.isNetworkConnected()) return;

        HashMap<String, String> map = new HashMap<>();
        map.put("agent_profile_id", agentData.id + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_AGENT_STORES, true, map);

    }

    private void addPartnerLayout() {
        ViewPartnerBinding partnerBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.view_partner, null, false);
        getAgentPartners();
        partnerMutableData.observe(activity, getAgentCompanies -> {
            if (getAgentCompanies.data != null && getAgentCompanies.data.size() > 0) {

                PartnerAdapter partnerAdapter = new PartnerAdapter();
                partnerAdapter.doRefresh(getAgentCompanies.data, activity, getAgentCompanies.path);
                partnerBinding.rvPortfolio.setAdapter(partnerAdapter);
            }
        });
        binding.linearCustom.addView(partnerBinding.getRoot());
    }

    public void getAgentPartners() {
        if (!activity.isNetworkConnected()) return;

        HashMap<String, String> map = new HashMap<>();
        map.put("agent_profile_id", agentData.id + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_AGENT_PARTNERS, true, map);

    }

    private void addAgencyLayout() {
        ViewAgencyBinding agencyBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.view_agency, null, false);

        if (agentData != null && agentData.agent_agency != null && agentData.agent_agency.size() > 0) {

            if (!TextUtils.isEmpty(agentData.agent_agency.get(0).name)) {
                agencyBinding.txtAgencyName.setText(agentData.agent_agency.get(0).name);
            }

//            if (!TextUtils.isEmpty(profileData.profile_agencies.about)) {
//                agencyBinding.txtAg.setText(profileData.profile_agencies.about);
//            }

            if (!TextUtils.isEmpty(agentData.agent_agency.get(0).phone)) {
                agencyBinding.txtAgencyNo.setText(agentData.agent_agency.get(0).phone);
            }

//            if (!TextUtils.isEmpty(profileData.profile_agencies.email)) {
//                binding.tvEmail.setText(profileData.profile_agencies.email);
//            }

            if (!TextUtils.isEmpty(agentData.agent_agency.get(0).address)) {
                agencyBinding.txtAgencyLocation.setText(String.format("%s", agentData.agent_agency.get(0).address));
            }

            if (!TextUtils.isEmpty(agentData.agent_agency.get(0).website)) {
                agencyBinding.txtAgencyWbsite.setText(agentData.agent_agency.get(0).website);
            }

//            Glide.with(activity).load(agencyBinding.filePaths.agency + agentData.agent_agency.get(0).).error(R.mipmap.ic_launcher).into(agencyBinding.imgProfile);

        }
        binding.linearCustom.addView(agencyBinding.getRoot());
    }

    private void addYoutubeLayout() {
        ViewYoutubeBinding youtubeBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.view_youtube, null, false);
        getAgentYouTube();

        youtubeMutableData.observe(activity, youtubeData -> {
            if (youtubeData.data != null && youtubeData.data.size() > 0) {

                ProfileYoutubeAdapter adapter = new ProfileYoutubeAdapter(activity);
                adapter.doRefresh(youtubeData.data);
                youtubeBinding.rvPortfolio.setAdapter(adapter);

            }
        });
        binding.linearCustom.addView(youtubeBinding.getRoot());
    }

    public void getAgentYouTube() {
        if (!activity.isNetworkConnected()) return;

        HashMap<String, String> map = new HashMap<>();
        map.put("agent_profile_id", agentData.id + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_AGENT_YOUTUBE, true, map);

    }

    private InfServices influencerServices;
    private SelectedServiceAdapter serviceAdapter;

    private void addInfluencerServiceLayout() {
        ViewServicesBinding socialMediaBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.view_services, null, false);
        if (activity.getLanguage().equals("ar")) {
//            setArFont(socialMediaBinding.txtName, Constants.FONT_AR_MEDIUM);
//            setArFont(socialMediaBinding.txtShow, Constants.FONT_AR_MEDIUM);
//            setArFont(socialMediaBinding.txtAdd, Constants.FONT_AR_MEDIUM);
        }
        serviceActivityVM.getServices(agentData.id);

        socialMediaBinding.txtName.setText(activity.getString(R.string.influencer_services));

        serviceActivityVM.serviceMutableLiveData.observe(activity, data -> {
            influencerServices = data;
            if (data != null && data.services != null && data.services.size() > 0) {
                ArrayList<Serv> servicesData = new ArrayList<>(data.services);

//                if (data.all_platforms_price != null && data.all_platforms_price > 0) {
//                    servicesData.add(new Serv(activity.getString(R.string.all_social_media), data.all_platforms_price));
//                }
                Collections.reverse(servicesData);
                serviceAdapter = new SelectedServiceAdapter(servicesData, activity);
                serviceAdapter.setLimit(true);
                serviceAdapter.setOnclickListener(this);
                socialMediaBinding.rvService.setAdapter(serviceAdapter);
                socialMediaBinding.rvService.setVisibility(VISIBLE);

                if (data.services.size() > 3) {
                    socialMediaBinding.txtServiceAll.setVisibility(VISIBLE);
                }
            } else {
                socialMediaBinding.rvService.setVisibility(GONE);
            }
        });

        socialMediaBinding.txtServiceAll.setOnClickListener(view -> {
            Intent intent = new Intent(activity, InfluencerServActivity.class);
            intent.putExtra("data", influencerServices);
            intent.putExtra(AGENT_PROFILE_DATA, agentData);
            intent.putExtra("social", connectedMediaList);
            activity.startActivity(intent);
        });

        binding.linearCustom.addView(socialMediaBinding.getRoot());
    }

    @Override
    public void onClickService(int pos, Serv serv) {
        //move to next screen
        Intent intent = new Intent(activity, InfluencerServActivity.class);
        intent.putExtra("data", influencerServices);
        intent.putExtra(AGENT_PROFILE_DATA, agentData);
        intent.putExtra("social", connectedMediaList);
        activity.startActivity(intent);
    }

    @Override
    public void onClickServiceChecked() {
        if (serviceAdapter != null) {
            if (serviceAdapter.calculatePrice() == 0) {
                binding.relContinue.setVisibility(GONE);
            } else {
                binding.relContinue.setVisibility(VISIBLE);
                String formattedNumber = Utils.getDecimalFormat(Utils.formatValue(serviceAdapter.calculatePrice()));
                binding.btnContinuePrice.setText(activity.getString(R.string.continue_) + " (" + formattedNumber + " " + activity.getString(R.string.sar) + ")");
            }
        }
    }
}
