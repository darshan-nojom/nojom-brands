package com.nojom.client.ui.home;

import static android.app.Activity.RESULT_OK;
import static com.nojom.client.util.Constants.API_GET_AGENTS;
import static com.nojom.client.util.Constants.API_GET_AGENT_BY_USER_NAME;
import static com.nojom.client.util.Constants.API_GET_CUSTOM_GIG_DETAILS;
import static com.nojom.client.util.Constants.API_GET_PROFILE_INFO;
import static com.nojom.client.util.Constants.API_GET_RATES;
import static com.nojom.client.util.Constants.API_REMOVE_AGENT;
import static com.nojom.client.util.Constants.API_SAVE_AGENT;
import static com.nojom.client.util.Constants.API_SOCIAL_PLATFORMS;
import static com.nojom.client.util.Constants.PLATFORM_NAME;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.adapter.ServiceAdapter;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.CampaignListener;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.api.WalletListener;
import com.nojom.client.databinding.ActivityServiceBinding;
import com.nojom.client.model.AgentProfile;
import com.nojom.client.model.CampListData;
import com.nojom.client.model.ExpertGigDetail;
import com.nojom.client.model.Profile;
import com.nojom.client.model.SavedInfluencer;
import com.nojom.client.model.ServiceAgents;
import com.nojom.client.model.SocialPlatformModel;
import com.nojom.client.model.WalletData;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.MainActivity;
import com.nojom.client.ui.projects.InfluencerProfileActivityCopy;
import com.nojom.client.ui.workprofile.UpdateLocationActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.EndlessRecyclerViewScrollListener;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

class ServiceActivityVM extends AndroidViewModel implements View.OnClickListener, ServiceAdapter.OnClickListener, WalletListener, CampaignListener, RequestResponseListener {
    private final ActivityServiceBinding binding;
    @SuppressLint("StaticFieldLeak")
    private final BaseActivity activity;
    private ServiceAdapter serviceAdapter;
    private int selectedCategoryId = 0;
    private String selectedCategoryAppName = "", selectedCategoryColor = "", selectedCategoryAppMenuName = "";
    private int selectedPos, gigId = 0, selectedPlatform = 0;
    private String influencersName = "";
    private EndlessRecyclerViewScrollListener scrollListener;
    private int pageNo = 1;

    ServiceActivityVM(Application application, ActivityServiceBinding homeNewBinding, BaseActivity clientHomeActivity) {
        super(application);
        binding = homeNewBinding;
        activity = clientHomeActivity;
//        socialGigListVM.getSocialGigs();
        getRates();
        initData();
//        Log.e("TTT", "---- " + activity.getToken());
    }

    private void initData() {
        LinearLayoutManager linearLayoutManagerDesigner = null;
        try {

            linearLayoutManagerDesigner = new LinearLayoutManager(activity);
            binding.rvBestInf.setLayoutManager(linearLayoutManagerDesigner);

        } catch (Exception e) {
            e.printStackTrace();
        }

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManagerDesigner) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (totalItemsCount > 9) {
                    pageNo = page;
                    if (pageNo == 1) {
                        serviceAdapter = null;
                        savedInfluencers = new ArrayList<>();
                    }
                    getAgents(pageNo, binding.loutSearch.getText().toString());
                }
            }
        };

        binding.loutSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                pageNo = 1;
//                serviceAdapter = null;
//                savedInfluencers = new ArrayList<>();
                if (TextUtils.isEmpty(charSequence)) {
                    getAgents(pageNo, "");
                } else {
                    getAgents(pageNo, charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    List<ServiceAgents> savedInfluencers = new ArrayList<>();

    private void setAllAdapter(WalletData allSocialGigs) {


        if (allSocialGigs.agents != null && allSocialGigs.agents.size() > 0) {
            savedInfluencers.addAll(allSocialGigs.agents);
        }
        setBestInfAdapter(savedInfluencers);

    }


    public void onResumeMethod() {
        try {
            if (scrollListener != null) binding.rvBestInf.addOnScrollListener(scrollListener);

            if (activity.isLogin()) {
//                binding.txtHireLawyerLbl.setVisibility(View.GONE);
                Profile profileData = Preferences.getProfileData(activity);
                if (profileData != null) {
                    if (TextUtils.isEmpty(profileData.getCountryName(activity.getLanguage())) || TextUtils.isEmpty(profileData.region) || TextUtils.isEmpty(profileData.city)) {
                        SharedPreferences sharedPrefs = activity.getSharedPreferences("locationUpdate", Context.MODE_PRIVATE);

                        if (!sharedPrefs.getBoolean("cancel", false)) {
                            SharedPreferences.Editor prefsEditor = sharedPrefs.edit();
                            //YOUR CODE TO SHOW DIALOG
                            long time = sharedPrefs.getLong("displayedTime", 0);
                            if (time < System.currentTimeMillis() - 259200000) {
                                showLocationSkillDialog();
                                prefsEditor.putLong("displayedTime", System.currentTimeMillis()).commit();
                            }
                            prefsEditor.apply();
                        }
                    }

                }
            }

            if (!TextUtils.isEmpty(selectedCategoryAppMenuName) && !TextUtils.isEmpty(selectedCategoryAppName)) {
                updateUiBasedOnCatId(selectedCategoryId, selectedCategoryAppName, selectedCategoryColor, selectedCategoryAppMenuName, false);
                updateGigUiBasedOnCatId(selectedCategoryId, selectedCategoryAppName, false);
            }
            pageNo = 1;
            savedInfluencers = new ArrayList<>();
            serviceAdapter = null;
            getAgents(pageNo, "");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("Recycle")
    public ArrayList<HomePagerModel> getList(String type) {
        ArrayList<HomePagerModel> arrayList = new ArrayList<>();
        TypedArray imgs = null;
        String[] stringArray = new String[0];
        String[] serviceArray = new String[0];
        switch (type) {
            case Constants.HIRE:
                imgs = activity.getResources().obtainTypedArray(R.array.service_images);
                stringArray = activity.getResources().getStringArray(R.array.services);
                serviceArray = activity.getResources().getStringArray(R.array.service_name);
                break;
            case Constants.WHY_US:
                imgs = activity.getResources().obtainTypedArray(R.array.why_us_images);
                stringArray = activity.getResources().getStringArray(R.array.why_us);
                break;
            case Constants.HOW_IT_WORKS:
                imgs = activity.getResources().obtainTypedArray(R.array.how_it_works_images);
                stringArray = activity.getResources().getStringArray(R.array.how_it_works);
                break;
        }

        for (int i = 0; i < stringArray.length; i++) {
            HomePagerModel model = new HomePagerModel();
            model.title = stringArray[i];
            model.name = serviceArray[i];
            model.icon = imgs.getResourceId(i, -1);
            arrayList.add(model);
        }
        return arrayList;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_best_view:
                viewAllInfluencers(0);
                break;
            case R.id.img_profile:
                ((MainActivity) activity.getParent()).setSettingTab();
                break;
        }

    }

    private void viewAllInfluencers(int influID) {
        if (activity.isLogin()) {
//            activity.setEnableDisableView(binding.tvGigViewAll);
            Intent intent = new Intent(activity, ViewAllGigActivity.class);
            intent.putExtra("socialPlatformID", influID);
            intent.putExtra("isProfile", true);
            activity.startActivity(intent);
        } else {
            activity.openLoginDialog();
        }
    }

//    private void setServiceAdapter() {
//        try {
//            List<SocialPlatformModel.Data> servicesList = new ArrayList<>(Preferences.getSocialPlatforms(activity));//social platforms
//            List<ServicesModel.Data> servicesSubList = new ArrayList<>(Preferences.getTopServices(activity));//sub category list
//
//            if (servicesList.size() > 0) {
//                List<HomeServiceCatg> categoryList = new ArrayList<>();
//                List<HomeIsTopService> serviceTopList = new ArrayList<>();
//                List<ServicesModel.Data> subCatList = new ArrayList<>();
//                categoryList.add(new HomeServiceCatg("#F3D231", activity.getResources().getString(R.string.influencers), activity.getString(R.string.all), "0", 0));
//
//                //get all sub category from parent skills
//                for (ServicesModel.Data serviceData : servicesSubList) {
//                    if (serviceData.id == 4352) {
//                        subCatList.addAll(serviceData.services);
//                        break;
//                    }
//                }
//
//                //get main category or platform
//                for (SocialPlatformModel.Data serviceData : servicesList) {
//                    categoryList.add(new HomeServiceCatg(!TextUtils.isEmpty(serviceData.colorCode) ? "" + serviceData.colorCode : "#434343",
//                            serviceData.getServNameByLang(activity.getLanguage()), serviceData.getServNameByLang(activity.getLanguage()), "0", serviceData.id));
//                }
//
//                //retrive sub-category from all list (where isTop=1)
//                for (ServicesModel.Data serviceData : subCatList) {
//                    serviceTopList.add(new HomeIsTopService(serviceData.getServNameByLang(activity.getLanguage()), serviceData.id));
//                }
//
//                //main category
//                if (servicesAdapter == null) {
//                    servicesAdapter = new HomeServicesAdapter(activity, categoryList, 0, this);
//                    binding.rvSkills.setAdapter(servicesAdapter);
//                }
//
//                //sub category list
//                if (homeIsTopService == null) {
//                    homeIsTopService = new HomeIsTopAdapter(activity, serviceTopList, 0, this);
//                    binding.rvTopSkills.setAdapter(homeIsTopService);
//                }
//            } else {
//                getSocialPlatforms();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void openJob(int servId, String selectedService) {

        Intent i = new Intent(activity, MainActivity.class);
        i.putExtra(Constants.SCREEN_NAME, Constants.TAB_POST_JOB);
        if (servId != 0) {
            i.putExtra(Constants.PLATFORM_ID, String.valueOf(servId));
            i.putExtra(PLATFORM_NAME, selectedService);
            i.putExtra(Constants.LAWYER_CASE, true);
            if (!selectedService.contains(Utils.laywer)) {
                i.putExtra(Constants.FROM_HOME, true);
            }
        } else {
            i.putExtra("allcategory", true);
        }
        activity.startActivity(i);
        activity.finish();
    }

//    public void getSocialPlatforms() {
//        if (!activity.isNetworkConnected()) return;
//
//        ApiRequest apiRequest = new ApiRequest();
//        apiRequest.apiRequest(this, activity, API_SOCIAL_PLATFORMS, false, null);
//    }

//    private void setGigAdapter(List<AllSocialGigs.AllData> data, String imagePath, int isFirstOrder, ExpertGig.CouponData couponData) {
//        if (homeGigAdapter == null) {
//            int isGigFirstOrder;
//            if (activity.isLogin()) {
//                isGigFirstOrder = isFirstOrder;
//            } else {
//                isGigFirstOrder = 1;
//            }
//            homeGigAdapter = new HomeGigAdapter(activity, this, data, imagePath, isGigFirstOrder, couponData, 0);
//            binding.rvGig.setAdapter(homeGigAdapter);
//        } else {
//            homeGigAdapter.doRefresh(data);
//        }
//        binding.rvGig.setVisibility(View.VISIBLE);
//    }

    private void setBestInfAdapter(List<ServiceAgents> data) {
        if (serviceAdapter == null) {
            serviceAdapter = new ServiceAdapter(activity, this, data);
            binding.rvBestInf.setAdapter(serviceAdapter);
        } else {
            serviceAdapter.doRefresh(data);
        }
        binding.rvBestInf.setVisibility(View.VISIBLE);
    }

//    private void notifyFavProgress(int state) {
//        try {
//            serviceAdapter.getData().get(selectedPos).isShowFavProgress = false;
//            serviceAdapter.getData().get(selectedPos).saved = state;
//            serviceAdapter.notifyItemChanged(selectedPos);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    @Override
//    public void savedAgentSuccess(int agentId) {
//        notifyFavProgress(1);
//        activity.isClickableView = false;
//    }

//    @Override
//    public void removeAgentSuccess(int agentId) {
//        notifyFavProgress(0);
//        activity.isClickableView = false;
//    }

//    @Override
//    public void savedGigSuccess(String gigId) {
//        activity.isClickableView = false;
//    }
//
//    @Override
//    public void removeGigSuccess(String gigId) {
//        activity.isClickableView = false;
//    }

//    @Override
//    public void onClickCategory(HomeServiceCatg category) {
//        Intent intent = new Intent(activity, ViewAllGigActivity.class);
//        intent.putExtra("socialPlatformID", category.id);
//        intent.putExtra("catName", category.categoryNameApp.replace(activity.getString(R.string.influencers), activity.getString(R.string.all)));
//        activity.startActivity(intent);
//        //updateUiBasedOnCatId(category.id, category.categoryNameApp, category.categoryColor, category.categoryAppMenuName, true);
//        // updateGigUiBasedOnCatId(category.id, category.categoryNameApp, true);
//    }

//    @Override
//    public void onClickCategory(HomeIsTopService category) {
//        Intent intent = new Intent(activity, ViewAllGigActivity.class);
//        intent.putExtra("catID", category.id);
//        intent.putExtra("catName", category.categoryNameApp);
//        activity.startActivity(intent);
//    }

    private void updateUiBasedOnCatId(int catId, String categoryAppName, String categoryColor, String categoryAppMenuName, boolean isCallAPI) {
//        setPagerCategoryBasedOnSelection(catId, categoryAppName, categoryColor);

        selectedCategoryId = catId;
        selectedCategoryAppName = categoryAppName;
        selectedCategoryAppMenuName = categoryAppMenuName;
        selectedCategoryColor = categoryColor;
        if (isCallAPI) {
            pageNo = 1;
            savedInfluencers = new ArrayList<>();
            serviceAdapter = null;
            getAgents(pageNo, "");
        }
    }

    private void updateGigUiBasedOnCatId(int catId, String categoryAppName, boolean isCallAPI) {
        if (!Task24Application.getInstance().isGigShow) {
            selectedCategoryId = catId;
//            if (selectedCategoryId == 0) {
//                binding.tvGigTitle.setText(activity.getString(R.string.influencers_service));
//            } else {
//                binding.tvGigTitle.setText(categoryAppName + " " + activity.getString(R.string.service));
//            }

            if (isCallAPI) {
                pageNo = 1;
                savedInfluencers = new ArrayList<>();
                serviceAdapter = null;
                getAgents(pageNo, "");
            }
        }
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        if (url.equalsIgnoreCase(API_GET_PROFILE_INFO) || url.equalsIgnoreCase(API_GET_AGENT_BY_USER_NAME + "/" + influencersName)) {
            Preferences.writeString(activity, "influencerName", null);
            influencersName = "";
            AgentProfile profile = AgentProfile.getProfileInfo(responseBody);
            activity.runOnUiThread(() -> {
                if (serviceAdapter != null) {
                    serviceAdapter.getData().get(selectedPos).isShowProgress = false;
                    serviceAdapter.notifyItemChanged(selectedPos);
                }
            });
            if (profile != null) {
                Intent i = new Intent(activity, InfluencerProfileActivityCopy.class);
                i.putExtra(Constants.AGENT_PROFILE_DATA, profile);
                activity.startActivity(i);
            }
        } else if (url.equalsIgnoreCase(API_SOCIAL_PLATFORMS)) {
            SocialPlatformModel platformModel = SocialPlatformModel.getSocialPlatform(responseBody);
            if (platformModel != null && platformModel.data != null) {
                Preferences.saveSocialPlatform(getApplication().getApplicationContext(), platformModel.data);
//                setServiceAdapter();
            }
        } else if (url.equalsIgnoreCase(API_GET_CUSTOM_GIG_DETAILS + "/" + gigId)) {
            ExpertGigDetail expertGigDetail = ExpertGigDetail.getGigDetail(responseBody);
            Preferences.writeString(activity, "gigID", null);
            gigId = 0;
            if (expertGigDetail != null) {

//                activity.runOnUiThread(() -> {
//                    homeGigAdapter.getData().get(selectedPos).isShowProgress = false;
//                    homeGigAdapter.notifyItemChanged(selectedPos);
//                });

                Intent intent = new Intent(activity, GigDetailActivity.class);
                intent.putExtra(Constants.PROJECT_DETAIL, expertGigDetail);
                intent.putExtra("gigID", gigId);
                activity.startActivity(intent);
            }
        } else if (url.equalsIgnoreCase(API_REMOVE_AGENT) || url.equalsIgnoreCase(API_SAVE_AGENT)) {
            activity.toastMessage(message);
            activity.runOnUiThread(() -> {
                SavedInfluencer savedInf = SavedInfluencer.getData(responseBody);
                if (serviceAdapter != null) {//best
                    serviceAdapter.getData().get(selectedPos).isShowProgress = false;
//                    serviceAdapter.getData().get(selectedPos).saved = savedInf.saved;
                    serviceAdapter.notifyItemChanged(selectedPos);
                }/* else if (selectedPlatform == 7 && savedInfAdapter != null) {//saved
                    savedInfAdapter.getData().remove(selectedPos);
                    savedInfAdapter.notifyItemRemoved(selectedPos);
                }*/

                //onResumeMethod();
            });
        }
        activity.isClickableView = false;
    }

    @Override
    public void successResponse(WalletData responseBody, String url, String message) {
        if (url.equalsIgnoreCase(agentUrl)) {
            if (responseBody != null && responseBody.agents != null) {
                if (pageNo == 1) {
                    serviceAdapter = null;
                    savedInfluencers = new ArrayList<>();
                }
                setAllAdapter(responseBody);
            }
        }
    }

    @Override
    public void successTxnResponse(List<WalletData> responseBody, String url, String message) {
        Preferences.saveRates(activity, responseBody);
    }

    public MutableLiveData<CampListData> campListData = new MutableLiveData<>();

    @Override
    public void successResponse(CampListData responseBody, String url, String message) {
        campListData.postValue(responseBody);
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        activity.isClickableView = false;
        if (url.equalsIgnoreCase(API_GET_CUSTOM_GIG_DETAILS + "/" + gigId)) {
//            activity.runOnUiThread(() -> {
//                homeGigAdapter.getData().get(selectedPos).isShowProgress = false;
//                homeGigAdapter.notifyItemChanged(selectedPos);
//            });
            activity.toastMessage(message);
        } else if (url.equalsIgnoreCase(API_GET_PROFILE_INFO)) {
            activity.runOnUiThread(() -> {
                if (serviceAdapter != null) {
                    serviceAdapter.getData().get(selectedPos).isShowProgress = false;
                    serviceAdapter.notifyItemChanged(selectedPos);
                }
            });
            influencersName = "";
            activity.toastMessage(message);
        } else if (url.equalsIgnoreCase(API_REMOVE_AGENT) || url.equalsIgnoreCase(API_SAVE_AGENT)) {
            activity.toastMessage(message);
            activity.runOnUiThread(() -> {
                if (selectedPlatform == 6 && serviceAdapter != null) {//best
                    serviceAdapter.notifyItemChanged(selectedPos);
                } /*else if (selectedPlatform == 7 && savedInfAdapter != null) {//saved
                    savedInfAdapter.notifyItemChanged(selectedPos);
                }*/
            });
        }
    }


//    private void getAgentByUsername() {
//        if (!activity.isNetworkConnected()) return;
//        activity.isClickableView = true;
//
//        ApiRequest apiRequest = new ApiRequest();
//        apiRequest.apiRequest(this, activity, API_GET_AGENT_BY_USER_NAME + "/" + influencersName, false, null);
//
//    }

    private void getAgentProfile(int agentProfileId) {
        if (!activity.isNetworkConnected()) return;
        activity.isClickableView = true;

        HashMap<String, String> map = new HashMap<>();
        map.put("agent_profile_id", agentProfileId + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_PROFILE_INFO, true, map);
    }

    String agentUrl;

    public void getAgents(int page, String search) {
        if (!activity.isNetworkConnectedDialog()) {
            return;
        }

        if (TextUtils.isEmpty(search)) {
            agentUrl = API_GET_AGENTS + page;
            ApiRequest apiRequest = new ApiRequest();
            apiRequest.getAgentList(this, activity, agentUrl);
        } else {
            agentUrl = API_GET_AGENTS + page + "&agentName=" + search;
            ApiRequest apiRequest = new ApiRequest();
            apiRequest.getAgentList(this, activity, agentUrl);
        }

    }

//    private void getGigDetails() {
//        if (!activity.isNetworkConnected()) {
//            return;
//        }
//
//        ApiRequest apiRequest = new ApiRequest();
//
//        apiRequest.apiRequest(this, activity, API_GET_CUSTOM_GIG_DETAILS + "/" + gigId, false, null);
//    }

//    @Override
//    public void onClickFavourite(AllSocialGigs.AllData data, int selectedPos, int platform) {
//        if (activity.isLogin()) {
//            this.selectedPos = selectedPos;
//            selectedPlatform = platform;
//            activity.saveRemoveGig(data.gigID, this, data.saved != 0);
//        } else {
//            activity.openLoginDialog();
//        }
//    }

//    @Override
//    public void onClickViewDetail(AllSocialGigs.AllData data, int selectedPos, int platform) {
//        if (activity.isLogin()) {
//            this.selectedPos = selectedPos;
//            selectedPlatform = platform;
//            gigId = data.gigID;
//            getGigDetails();
//        } else {
//            activity.openLoginDialog();
//        }
//    }

    void showLocationSkillDialog() {
        final Dialog dialog = new Dialog(activity, R.style.Theme_AppCompat_Dialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_location_skill);
        dialog.setCancelable(false);

        TextView tvTitle = dialog.findViewById(R.id.tv_title);
        TextView tvMessage = dialog.findViewById(R.id.tv_message);
        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        TextView tvUpdate = dialog.findViewById(R.id.tv_update);

        tvTitle.setText(activity.getString(R.string.please_update_your_location));
        tvMessage.setText(activity.getString(R.string.this_makes_some_features_work_helps_client_find_influencers_based_on_your_location));

        tvCancel.setOnClickListener(v -> {
            dialog.dismiss();
            SharedPreferences prefs = activity.getSharedPreferences("locationUpdate", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("cancel", true);
            editor.apply();
        });

        tvUpdate.setOnClickListener(v -> {
            dialog.dismiss();
            Intent intent = new Intent(activity, UpdateLocationActivity.class);
            intent.putExtra("flag", true);
            activity.startActivity(intent);
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.gravity = Gravity.CENTER;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }

    //    @Override
//    public void onClickFavouriteInf(AllSocialGigs.AllData data, int pos, int platform) {
//        if (activity.isLogin()) {
//            this.selectedPos = pos;
//            selectedPlatform = platform;
//            saveRemoveInfluencer(data.profile_id != null ? data.profile_id : data.id, data.saved == 1);
//        } else {
//            activity.openLoginDialog();
//        }
//    }
//
    @Override
    public void onClickViewInfluencer(ServiceAgents data, int pos, int platform) {
        if (activity.isLogin()) {
            this.selectedPos = pos;
//            influencersName = data.username;
//            getAgentByUsername();
            getAgentProfile(data.id);
        } else {
            activity.openLoginDialog();
        }
    }

//    public void saveRemoveInfluencer(int id, boolean isRemoved) {
//        if (!activity.isNetworkConnected()) return;
//
//        HashMap<String, String> map = new HashMap<>();
//        map.put("agent_profile_id", id + "");
//
//        ApiRequest apiRequest = new ApiRequest();
//        apiRequest.apiRequest(this, activity, isRemoved ? API_REMOVE_AGENT : API_SAVE_AGENT, true, map);
//    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 124) {

        }
    }

    public void onPause() {
        if (scrollListener != null) binding.rvBestInf.removeOnScrollListener(scrollListener);
    }

    public void getRates() {
        if (!activity.isNetworkConnected()) return;
//        mutableProgress.postValue(true);
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.getWalletTxn(this, activity, API_GET_RATES);

    }

    @Override
    public void onClickFavouriteInf(ServiceAgents data, int pos, int platform) {

    }
}
