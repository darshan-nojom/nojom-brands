package com.nojom.client.ui;

import static com.nojom.client.util.Constants.API_GET_PROFILE_INFO;
import static com.nojom.client.util.Constants.API_SEARCH_INFLU;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.nojom.client.adapter.InfAdapter;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivityServiceSellersSearchBinding;
import com.nojom.client.model.AgentProfile;
import com.nojom.client.model.InfluencerList;
import com.nojom.client.model.ServicesModel;
import com.nojom.client.ui.projects.InfluencerProfileActivityCopy;
import com.nojom.client.util.Constants;
import com.nojom.client.util.EndlessRecyclerViewScrollListener;
import com.nojom.client.util.Preferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class ServiceSellersSearchActivityVM extends AndroidViewModel implements RequestResponseListener, InfAdapter.OnClickListener {
    private final ActivityServiceSellersSearchBinding binding;
    private final BaseActivity activity;
    private List<InfluencerList.AllData> influencerList;
    private boolean isSimpleBack;
    private EndlessRecyclerViewScrollListener scrollListener;
    private int pageNo = 1;

    ServiceSellersSearchActivityVM(Application application, ActivityServiceSellersSearchBinding serviceSellersSearchBinding, BaseActivity baseActivity) {
        super(application);
        binding = serviceSellersSearchBinding;
        activity = baseActivity;
        influencerList = new ArrayList<>();
        initData();
    }

    private void initData() {
        try {
            isSimpleBack = activity.getIntent().getBooleanExtra("isSimpleBack", false);
            LinearLayoutManager linearLayoutTopManager = new LinearLayoutManager(activity);
            binding.rvServicesTopSellers.setLayoutManager(linearLayoutTopManager);
            binding.rvServicesTopSellers.setNestedScrollingEnabled(false);

            searchInfluencers("", pageNo);

            binding.txtCancel.setOnClickListener(view -> activity.finish());

//            List<ServicesModel.Data> skillList = new ArrayList<>();
//            List<ServicesModel.Data> servicesSubList = new ArrayList<>(Preferences.getCategoryV2(activity));

//            for (ServicesModel.Data serviceData : servicesSubList) {
//                if (serviceData.id == 4352) {
//                    skillList.addAll(serviceData.services);
//                }
//            }

//            ArrayList<ServiceSellersTopCatg> arrSellerSearchTopList = new ArrayList<ServiceSellersTopCatg>();
//            for (ServicesModel.Data serviceData : skillList) {
//                arrSellerSearchTopList.add(new ServiceSellersTopCatg(serviceData.getServNameByLang(activity.getLanguage()), serviceData.id));
//            }


            binding.etSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try {
                        if (TextUtils.isEmpty(s)) {
                            pageNo = 1;
                            searchInfluencers(binding.etSearch.getText().toString(), pageNo);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            binding.etSearch.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    pageNo = 1;
                    searchInfluencers(binding.etSearch.getText().toString(), pageNo);
                    //hide keyboard
                    InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(binding.etSearch.getWindowToken(), 0);

                    return true;
                }
                return false;
            });

            binding.loutTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

            scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutTopManager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    if (totalItemsCount > 9) {
                        pageNo = page;
                        searchInfluencers(binding.etSearch.getText().toString(), pageNo);
                    }
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    InfAdapter serviceSellersTopAdapter;

    private void setAdapter() {
        if (influencerList != null && influencerList.size() > 0) {
            binding.rvServicesTopSellers.setVisibility(View.VISIBLE);
            binding.txtPh.setVisibility(View.GONE);
            if (serviceSellersTopAdapter == null) {
                serviceSellersTopAdapter = new InfAdapter(activity, this, influencerList, imgPath);
                binding.rvServicesTopSellers.setAdapter(serviceSellersTopAdapter);
            } else {
                //do refresh
                serviceSellersTopAdapter.doRefresh(influencerList);
            }
        } else {
            binding.rvServicesTopSellers.setVisibility(View.GONE);
            binding.txtPh.setVisibility(View.VISIBLE);
        }
    }

//    private void moveToNext(int selectedCategoryId, String categoryName) {
//        if (isSimpleBack) {
//            Intent intent = new Intent();
//            intent.putExtra(Constants.SERVICE_CATEGORY_ID, selectedCategoryId);
//            intent.putExtra(Constants.SERVICE_CATEGORY_NAME, categoryName);
//            activity.setResult(Activity.RESULT_OK, intent);
//            activity.finish();
//        } else {
//            Intent intent;
//            intent = new Intent(activity, ViewAllGigActivity.class);
//            intent.putExtra("catID", selectedCategoryId);
//            intent.putExtra("catName", categoryName);
//            activity.startActivity(intent);
//        }
//    }

    public void searchInfluencers(String search, int page) {
        if (!activity.isNetworkConnected())
            return;

        if (page == 1) {
            influencerList = new ArrayList<>();
            serviceSellersTopAdapter = null;
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("page_no", page + "");
        map.put("search", search + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_SEARCH_INFLU, true, map);
    }

    private void getAgentByUsername(Integer agentProfileId) {
        if (!activity.isNetworkConnected())
            return;
        activity.isClickableView = true;

        HashMap<String, String> map = new HashMap<>();
        map.put("agent_profile_id", agentProfileId + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_PROFILE_INFO, true, map);

    }

    String imgPath;

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        activity.isClickableView = false;
        if (url.equals(API_SEARCH_INFLU)) {
            InfluencerList profile = InfluencerList.getAllInfluencers(responseBody);
            if (profile != null && profile.influencer != null && profile.influencer.size() > 0) {
                imgPath = profile.path;
                influencerList.addAll(profile.influencer);
            }
            setAdapter();
        } else {
            Preferences.writeString(activity, "influencerName", null);
            influencersName = "";
            AgentProfile profile = AgentProfile.getProfileInfo(responseBody);
            activity.runOnUiThread(() -> {
                if (serviceSellersTopAdapter != null) {
                    serviceSellersTopAdapter.getData().get(selectedPos).isShowProgress = false;
                    serviceSellersTopAdapter.notifyItemChanged(selectedPos);
                }
            });
            if (profile != null) {
                Intent i = new Intent(activity, InfluencerProfileActivityCopy.class);
                i.putExtra(Constants.AGENT_PROFILE_DATA, profile);
                activity.startActivity(i);
            }
        }
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        activity.isClickableView = false;
        if (url.equals(API_SEARCH_INFLU)) {
            setAdapter();
        } else {
            influencersName = "";
            activity.runOnUiThread(() -> {
                if (serviceSellersTopAdapter != null) {
                    serviceSellersTopAdapter.getData().get(selectedPos).isShowProgress = false;
                    serviceSellersTopAdapter.notifyItemChanged(selectedPos);
                }
            });
        }
    }

    public void onResume() {
        if (scrollListener != null)
            binding.rvServicesTopSellers.addOnScrollListener(scrollListener);
    }

    public void onPause() {
        if (scrollListener != null)
            binding.rvServicesTopSellers.removeOnScrollListener(scrollListener);
    }

    @Override
    public void onClickFavouriteInf(InfluencerList.AllData data, int pos) {

    }

    String influencersName;
    int selectedPos;

    @Override
    public void onClickViewInfluencer(InfluencerList.AllData data, int pos) {
        influencersName = data.username;
        selectedPos = pos;
        getAgentByUsername(data.id);
    }
}
