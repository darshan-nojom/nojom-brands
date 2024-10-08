package com.nojom.client.ui.home;

import static android.app.Activity.RESULT_OK;
import static com.nojom.client.util.Constants.API_GET_CUSTOM_GIG_DETAILS;
import static com.nojom.client.util.Constants.API_GET_PROFILE_INFO;
import static com.nojom.client.util.Constants.API_REMOVE_AGENT;
import static com.nojom.client.util.Constants.API_SAVE_AGENT;
import static com.nojom.client.util.Constants.API_SEARCH_INFLU;
import static com.nojom.client.util.Constants.API_SERVICE_CATEGORIES_V2;

import android.app.Application;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nojom.client.R;
import com.nojom.client.adapter.InfAdapter;
import com.nojom.client.adapter.ViewAllCategoryAdapter;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivityViewAllGigBinding;
import com.nojom.client.model.AgentProfile;
import com.nojom.client.model.ExpertGigDetail;
import com.nojom.client.model.HomeServiceCatg;
import com.nojom.client.model.InfluencerList;
import com.nojom.client.model.PriceRange;
import com.nojom.client.model.SavedInfluencer;
import com.nojom.client.model.ServicesModel;
import com.nojom.client.model.SkillTags;
import com.nojom.client.model.SocialInfluence;
import com.nojom.client.model.SortByFilterModel;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.InfluencerFilterActivity;
import com.nojom.client.ui.ServiceSellersSearchActivity;
import com.nojom.client.ui.projects.InfluencerProfileActivityCopy;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.SaveRemoveGigClickListener;
import com.nojom.client.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ViewAllGigActivityVM extends AndroidViewModel implements View.OnClickListener, RequestResponseListener, ViewAllCategoryAdapter.OnClickCategoryListener, InfAdapter.OnClickListener, SaveRemoveGigClickListener {
    private static final int REQ_FILTER = 101;
    private final ActivityViewAllGigBinding binding;
    private final BaseActivity activity;
    public int serviceCatId = 0, tempSelCatId = 0;
    private int selectedLanguage = -1;
    private int selectedLocation = 194, filterCount, selectedCityId = -1;
    private String filePath;
    private String catName = "", influencersName;
    private String filterByGig = "BestSellers";
    private List<InfluencerList.AllData> arrGigList = new ArrayList<>();
    private int pageGigNo = 1, selectedGigPos, gigId;
    private ViewAllCategoryAdapter viewAllCategoryAdapter;
    private int isFirstOrder;
    private SocialInfluence.CouponData couponData;
    private InfAdapter serviceSellersGigViewAdapter;
    private boolean isFavChecked = false, isProfile, isFilter;
    private String selectedSeller = "ALL";
    private String selectedMawthooq = "0";
    private int selectedPlatform;
    private ArrayList<ServicesModel.Data> skillsArray = new ArrayList<>();
    private ArrayList<SkillTags.Data> skillTagsArray = new ArrayList<>();
    private ArrayList<PriceRange> selectedPriceList;
    private SortByFilterModel selectedSortBy;
    private String selectedPlatformName = "";
    private String selectedSkills = "";
    private String selectedTags = "";
    private ArrayList<PriceRange> selectedFollowersList;
    private ArrayList<PriceRange> selectedAgeList;
    private int selectedGender = -1;

    public ViewAllGigActivityVM(@NonNull Application application, ActivityViewAllGigBinding activityFindGigBinding, ViewAllGigActivity findGigActivity) {
        super(application);
        binding = activityFindGigBinding;
        activity = findGigActivity;
        initData();
    }

    private void initData() {
        binding.imgBack.setOnClickListener(this);
        binding.imgSearch.setOnClickListener(this);
        binding.txtCancel.setOnClickListener(this);
        binding.imgCatFilter.setOnClickListener(this);

        if (activity.getIntent() != null) {
            tempSelCatId = activity.getIntent().getIntExtra("catID", 0);
            selectedPlatform = activity.getIntent().getIntExtra("socialPlatformID", 0);
            isProfile = activity.getIntent().getBooleanExtra("isProfile", false);
            isFilter = activity.getIntent().getBooleanExtra("isFilter", false);

//            if (isProfile) {//best influencers
//                selectedSortBy = "BestInfluencers";
//            } else {//influencer service
//                selectedSortBy = "BestServices";
//            }

//            if (selectedPlatform == 7) {//saved influencer case
//                selectedPlatform = 0;
//                selectedSortBy = "SaveInfluencers";
//            }

            serviceCatId = tempSelCatId;
            if (activity.getIntent().getStringExtra("catName") != null) {
                catName = activity.getIntent().getStringExtra("catName");
                if (catName != null && !catName.isEmpty()) {
                    binding.etSearch.setText(catName);
                }
                binding.etSearch.setSelection(binding.etSearch.getText().length());
                Utils.hideSoftKeyboard(activity);
            }
        }

        binding.rvSkills.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));

//        arrFilterList = new ArrayList<>();
//        SortByFilterModel delivered = new SortByFilterModel();
//        delivered.filterName = "Best Stars";
//        delivered.filterID = "BestSellers";
//        arrFilterList.add(delivered);
//
//        SortByFilterModel cancelled = new SortByFilterModel();
//        cancelled.filterName = "Top Rated";
//        cancelled.filterID = "TopRated";
//        arrFilterList.add(cancelled);
//
//        SortByFilterModel transit = new SortByFilterModel();
//        transit.filterName = "New Arrivals";
//        transit.filterID = "NewArrivals";
//        arrFilterList.add(transit);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(activity);
        binding.rvAll1.setLayoutManager(linearLayoutManager1);


        binding.txtTitle1.setVisibility(View.GONE);

        binding.loutViewMore1.setOnClickListener(view -> {
            pageGigNo++;
            binding.progressBar1.setVisibility(View.VISIBLE);
            binding.txtViewMore1.setVisibility(View.INVISIBLE);
            getGigAllList(pageGigNo, binding.etSearch.getText().toString());
        });

        setServiceAdapter();
        refreshData();

        binding.etSearch.setOnClickListener(view -> {
            Intent intent = new Intent(activity, ServiceSellersSearchActivity.class);
            intent.putExtra("selectedCategoryId", serviceCatId);
            intent.putExtra("isSimpleBack", true);
            activity.startActivityForResult(intent, 4545);
        });

        Glide.with(activity).asGif().load(R.drawable.ic_loader).placeholder(R.drawable.ic_loader).into(binding.imgLoader);

        if (isFilter) {
            onClick(binding.imgCatFilter);
        }
    }

    public void refreshData() {
        arrGigList = new ArrayList<>();
        this.pageGigNo = 1;
        serviceSellersGigViewAdapter = null;
        getGigAllList(pageGigNo, binding.etSearch.getText().toString());
    }

    private void setServiceAdapter() {
        try {
            List<ServicesModel.Data> servicesList = new ArrayList<>(Preferences.getCategoryV2(activity));

            if (servicesList.size() > 0) {
                binding.txtPh.setVisibility(View.GONE);
                //get all sub category from parent skills
//                List<ServicesModel.Data> subCatList = new ArrayList<>();

//                for (ServicesModel.Data serviceData : servicesList) {
//                    if (serviceData.id == 4352) {//get only influencer category skills
//                        subCatList.addAll(serviceData.services);
//                        break;
//                    }
//                }
                List<HomeServiceCatg> categoryList = new ArrayList<>();
//                categoryList.add(new HomeServiceCatg("#F3D231", activity.getResources().getString(R.string.influencer), activity.getString(R.string.all), "0", 0));

                for (int i = 0; i < servicesList.size(); i++) {
                    categoryList.add(new HomeServiceCatg(!TextUtils.isEmpty(servicesList.get(i).colourHex) ? "#" + servicesList.get(i).colourHex : "#434343", servicesList.get(i).getServNameByLang(activity.getLanguage()),
                            servicesList.get(i).getServNameByLang(activity.getLanguage()),
                            servicesList.get(i).isNew, servicesList.get(i).id));
                }

                if (viewAllCategoryAdapter == null) {
                    viewAllCategoryAdapter = new ViewAllCategoryAdapter(activity, categoryList, serviceCatId, this);
                    binding.rvSkills.setAdapter(viewAllCategoryAdapter);
                }
            } else {
                getTopServiceList();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getTopServiceList() {
        if (!activity.isNetworkConnected()) return;

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_SERVICE_CATEGORIES_V2);
    }

//    private void getGigAllList(int pageNo, String filterByGig) {
//        if (!activity.isNetworkConnected()) {
//            return;
//        }
//
//        if (pageNo == 1) {
//            binding.loutMain.setVisibility(View.GONE);
//            binding.imgLoader.setVisibility(View.VISIBLE);
//        }
//        HashMap<String, RequestBody> map = new HashMap<>();
//        RequestBody pageNO = RequestBody.create(String.valueOf(pageNo), MultipartBody.FORM);
//        RequestBody limit = RequestBody.create("10", MultipartBody.FORM);
//        RequestBody tab = RequestBody.create(selectedSeller, MultipartBody.FORM);
//        map.put("page_no", pageNO);
//        map.put("limit", limit);
//        map.put("tab", tab);
//
//        if (skillsArray != null && skillsArray.size() > 0) {
//            StringBuilder stringBuilder = new StringBuilder();
//            for (ServicesModel.Data data : skillsArray) {
//                stringBuilder.append(data.id);
//                stringBuilder.append(",");
//            }
//            selectedSkills = stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();
//        }
//
//        String selectedSkillsList = selectedSkills != null ? serviceCatId + "," + selectedSkills : serviceCatId != 0 ? serviceCatId + "" : "";
//
//        Set<String> elements = new LinkedHashSet<>(Arrays.asList(selectedSkillsList.split(",")));
//
//        Iterator<String> it = elements.iterator();
//
//        StringBuilder sb = new StringBuilder(it.hasNext() ? it.next() : "");
//        while (it.hasNext()) {
//            sb.append(',').append(it.next());
//        }
//
//        RequestBody skillID = RequestBody.create(sb.toString(), MultipartBody.FORM);
//
//        map.put("skill_id", skillID);
//
//        if (selectedLanguage != -1) {
//            RequestBody languageID = RequestBody.create(String.valueOf(selectedLanguage), MultipartBody.FORM);
//            map.put("language_id", languageID);
//        }
//
//        RequestBody filterBy = RequestBody.create(selectedSortBy, MultipartBody.FORM);
//        RequestBody price_From = RequestBody.create(priceFrom, MultipartBody.FORM);
//        RequestBody price_To = RequestBody.create(priceTo, MultipartBody.FORM);
//
//        map.put("filterBy", filterBy);
//        map.put("price_from", price_From);
//        map.put("price_to", price_To);
//        // map.put("gender", selectedGender + "");
//        if (selectedLocation != -1) {
//            RequestBody countryID = RequestBody.create(String.valueOf(selectedLocation), MultipartBody.FORM);
//            map.put("countryID", countryID);
//        }
//
//        RequestBody socialPlatformID = RequestBody.create(String.valueOf(selectedPlatform), MultipartBody.FORM);
//        RequestBody followersCount = RequestBody.create(String.valueOf(selectedFollowers), MultipartBody.FORM);
//        map.put("socialPlatformID", socialPlatformID);
//        map.put("followersCount", followersCount);
//
//        ApiRequest apiRequest = new ApiRequest();
//        apiRequest.apiRequestSocialInfluenceFilter(this, activity, API_GET_SOCIAL_INFLUENCE_PROFILES, map);
//    }


    public void getGigAllList(int page, String search) {
        if (!activity.isNetworkConnected()) return;


        if (page == 1) {
            binding.loutMain.setVisibility(View.GONE);
            binding.imgLoader.setVisibility(View.VISIBLE);
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("page_no", page + "");
        map.put("search", search + "");
        if (selectedGender != -1) {
            map.put("gender", selectedGender + "");
        }
        map.put("tab", selectedSeller);
        map.put("mawthooq_status", selectedMawthooq);

        if (selectedLocation != -1) {
            map.put("countryID", String.valueOf(selectedLocation));
        }
        if (selectedCityId != -1) {
            map.put("cityID", String.valueOf(selectedCityId));
        }

        StringBuilder stringBuilder = new StringBuilder();
        if (skillsArray != null && skillsArray.size() > 0) {
            for (ServicesModel.Data data : skillsArray) {
                stringBuilder.append(data.id);
                stringBuilder.append(",");
            }
            selectedSkills = stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();
        }
        StringBuilder stringBuilderTag = new StringBuilder();
        if (skillTagsArray != null && skillTagsArray.size() > 0) {
//            if (!TextUtils.isEmpty(selectedSkills)) {
//                stringBuilder.append(",");
//            }
            for (SkillTags.Data data : skillTagsArray) {
                stringBuilderTag.append(data.id);
                stringBuilderTag.append(",");
            }
            selectedTags = stringBuilderTag.deleteCharAt(stringBuilderTag.length() - 1).toString();
        }
        String selectedSkillsList = selectedSkills != null ? serviceCatId + "," + selectedSkills : serviceCatId != 0 ? serviceCatId + "" : "";
        Set<String> elements = new LinkedHashSet<>(Arrays.asList(selectedSkillsList.split(",")));

        Iterator<String> it = elements.iterator();

        StringBuilder sb = new StringBuilder(it.hasNext() ? it.next() : "");
        while (it.hasNext()) {
            sb.append(',').append(it.next());
        }
        map.put("skill_id", sb.toString());

        String selectedTagList = selectedTags;
        Set<String> elementsTags = new LinkedHashSet<>(Arrays.asList(selectedTagList.split(",")));

        Iterator<String> itTag = elementsTags.iterator();

        StringBuilder sbTag = new StringBuilder(itTag.hasNext() ? itTag.next() : "");
        while (itTag.hasNext()) {
            sbTag.append(',').append(itTag.next());
        }
        map.put("tag_id", sbTag.toString());


        if (selectedSortBy != null) {
            switch (selectedSortBy.filterID) {
                case "1": //high to low price
                    map.put("sort_by", "PRICEHIGHTOLOW");
                    break;
                case "2": //low to high price
                    map.put("sort_by", "PRICELOWTOHIGH");
                    break;
                case "3": //high to low follower
                    map.put("sort_by", "FOLLOWERHIGHTOLOW");
                    break;
                case "4": //low to high follower
                    map.put("sort_by", "FOLLOWERLOWTOHIGH");
                    break;
                case "5": //Newest
                    map.put("sort_by", "NEWEST");
                    break;
            }
        }
        /*if (selectedPrice != null) {
            if (selectedPrice.minPrice == -1 && selectedPrice.maxPrice == -1) {//custom price case
                map.put("price_range_start", priceFrom);
                map.put("price_range_end", priceTo);
            } else {//range case
                map.put("price_range_start", selectedPrice.minPrice + "");
                map.put("price_range_end", selectedPrice.maxPrice + "");
            }
        }*/
//        HashMap<String, List<PriceRangeSel>> mapPrice = new HashMap<>();
//        List<PriceRangeSel> finalRange = new ArrayList<>();
        JSONArray array = new JSONArray();
        if (selectedPriceList != null && selectedPriceList.size() > 0) {
            for (PriceRange pr : selectedPriceList) {
                JSONObject object = new JSONObject();
                try {
                    object.put("price_range_start", "" + pr.minPrice);
                    object.put("price_range_end", "" + pr.maxPrice);
                    array.put(object);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }
//        mapPrice.put("price_range", finalRange);
        /*if (selectedFollowers != null) {
            if (selectedFollowers.minPrice == -1 && selectedFollowers.maxPrice == -1) {//custom follower case
                map.put("followers_range_start", minFoll);
                map.put("followers_range_end", maxFoll);
            } else {//range case
                map.put("followers_range_start", selectedFollowers.minPrice + "");
                map.put("followers_range_end", selectedFollowers.maxPrice + "");
            }
        }*/

        JSONArray arrayFollow = new JSONArray();
        if (selectedFollowersList != null && selectedFollowersList.size() > 0) {
            for (PriceRange pr : selectedFollowersList) {
                JSONObject object = new JSONObject();
                try {
                    object.put("followers_range_start", "" + pr.minPrice);
                    object.put("followers_range_end", "" + pr.maxPrice);
                    arrayFollow.put(object);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        /*if (selectedAge != null) {
            if (selectedAge.minPrice == -1 && selectedAge.maxPrice == -1) {//custom age case
                map.put("age_range_start", minAge);
                map.put("age_range_end", maxAge);
            } else {//range case
                map.put("age_range_start", selectedAge.minPrice + "");
                map.put("age_range_end", selectedAge.maxPrice + "");
            }
        }*/

        JSONArray arrayAge = new JSONArray();
        if (selectedAgeList != null && selectedAgeList.size() > 0) {
            for (PriceRange pr : selectedAgeList) {
                JSONObject object = new JSONObject();
                try {
                    object.put("age_range_start", "" + pr.minPrice);
                    object.put("age_range_end", "" + pr.maxPrice);
                    arrayAge.put(object);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_SEARCH_INFLU, true, map, array, arrayFollow, arrayAge);
    }

    private void getGigDetails() {
        if (!activity.isNetworkConnected()) {
            return;
        }

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_CUSTOM_GIG_DETAILS + "/" + gigId, false, null);
    }

    private void getAgentByUsername(Integer agentProfileId) {
        if (!activity.isNetworkConnected()) return;
        activity.isClickableView = true;

        HashMap<String, String> map = new HashMap<>();
        map.put("agent_profile_id", agentProfileId + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_PROFILE_INFO, true, map);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                activity.onBackPressed();
                break;
            case R.id.img_cat_filter:
                activity.setEnableDisableView(binding.imgCatFilter);
                Intent intent = new Intent(activity, InfluencerFilterActivity.class);
                intent.putExtra(Constants.SERVICE_CATEGORY_ID, serviceCatId);
                intent.putExtra("sortBy", selectedSortBy);
                intent.putExtra("seller", selectedSeller);
                intent.putExtra("mawthooq_status", selectedMawthooq);
                intent.putExtra("platform", selectedPlatform);
                intent.putExtra("platformName", selectedPlatformName);
                intent.putExtra("skill", skillsArray);
                intent.putExtra("skilltags", skillTagsArray);
//                intent.putExtra("priceRange", selectedPrice);
                intent.putExtra("priceRange", selectedPriceList);
                intent.putExtra("followers", selectedFollowersList);
                intent.putExtra("age", selectedAgeList);
                intent.putExtra("gender", selectedGender);
                intent.putExtra("language", selectedLanguage);
                intent.putExtra("location", selectedLocation);
                intent.putExtra("cityid", selectedCityId);
                activity.startActivityForResult(intent, REQ_FILTER);
                break;
            case R.id.img_search:
                binding.etSearch.setVisibility(View.VISIBLE);
                binding.imgSearch.setVisibility(View.GONE);
                binding.txtCancel.setVisibility(View.VISIBLE);
                binding.imgCatFilter.setVisibility(View.GONE);
                binding.etSearch.setFocusable(true);
                binding.etSearch.requestFocus();
                binding.etSearch.requestFocusFromTouch();
                binding.loutFilter.setVisibility(View.GONE);
                break;
            case R.id.txt_cancel:
                Utils.hideSoftKeyboard(activity);
                binding.etSearch.setText("");
                resetFilter();

                binding.etSearch.setVisibility(View.INVISIBLE);
                binding.imgSearch.setVisibility(View.VISIBLE);
                binding.txtCancel.setVisibility(View.GONE);
                binding.imgCatFilter.setVisibility(View.VISIBLE);
                binding.loutFilter.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void resetFilter() {
        serviceCatId = tempSelCatId;
        selectedSeller = "ALL";
        selectedMawthooq = "0";
        selectedPlatform = 0;
        selectedPlatformName = "";
        selectedSkills = "";
        skillsArray = new ArrayList<>();
        skillTagsArray = new ArrayList<>();
        selectedFollowersList = new ArrayList<>();
        selectedAgeList = new ArrayList<>();
        selectedGender = -1;
        selectedLanguage = -1;
        selectedLocation = 194;
        selectedCityId = -1;
        selectedPriceList = new ArrayList<>();
        selectedSortBy = null;
        filterCount = 3;
        binding.txtFilterCount.setText("" + filterCount);
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        try {
            if (url.equalsIgnoreCase(API_SERVICE_CATEGORIES_V2)) {
                List<ServicesModel.Data> servicesModel = ServicesModel.getServiceDataCat(responseBody);
                if (servicesModel != null && servicesModel.size() > 0) {
                    Preferences.saveCategoryV2(getApplication().getApplicationContext(), servicesModel);
                    setServiceAdapter();
                }
            } else if (url.equalsIgnoreCase(API_SEARCH_INFLU)) {
                binding.imgLoader.setVisibility(View.GONE);
//                SocialInfluence socialInfluence = SocialInfluence.getSocialInfluence(responseBody);
                InfluencerList socialInfluence = InfluencerList.getAllInfluencers(responseBody);
                binding.progressBar1.setVisibility(View.GONE);
                binding.txtViewMore1.setVisibility(View.VISIBLE);

                onSuccessGigList(socialInfluence);
            } else if (url.equalsIgnoreCase(API_GET_CUSTOM_GIG_DETAILS + "/" + gigId)) {
                ExpertGigDetail expertGigDetail = ExpertGigDetail.getGigDetail(responseBody);

                if (expertGigDetail != null) {
                    activity.runOnUiThread(() -> {
                        serviceSellersGigViewAdapter.getData().get(selectedGigPos).isShowProgress = false;
                        serviceSellersGigViewAdapter.notifyItemChanged(selectedGigPos);
                    });

                    Intent intent = new Intent(activity, GigDetailActivity.class);
                    intent.putExtra(Constants.PROJECT_DETAIL, expertGigDetail);
                    intent.putExtra("gigID", gigId);
                    activity.startActivity(intent);
                }
            } else if (url.equalsIgnoreCase(API_GET_PROFILE_INFO)) {
                Preferences.writeString(activity, "influencerName", null);
                influencersName = "";
                AgentProfile profile = AgentProfile.getProfileInfo(responseBody);
                activity.runOnUiThread(() -> {
                    if (serviceSellersGigViewAdapter != null) {
                        serviceSellersGigViewAdapter.getData().get(selectedGigPos).isShowProgress = false;
                        serviceSellersGigViewAdapter.notifyItemChanged(selectedGigPos);
                    }
                });
                if (profile != null) {
                    Intent i = new Intent(activity, InfluencerProfileActivityCopy.class);
                    i.putExtra(Constants.AGENT_PROFILE_DATA, profile);
                    activity.startActivity(i);
                }
            } else if (url.equalsIgnoreCase(API_REMOVE_AGENT) || url.equalsIgnoreCase(API_SAVE_AGENT)) {
                activity.toastMessage(message);
                activity.runOnUiThread(() -> {
                    SavedInfluencer savedInf = SavedInfluencer.getData(responseBody);
                    if (serviceSellersGigViewAdapter != null) {//best
                        serviceSellersGigViewAdapter.getData().get(selectedGigPos).isShowFavProgress = false;
                        serviceSellersGigViewAdapter.getData().get(selectedGigPos).saved = savedInf.saved;
                        serviceSellersGigViewAdapter.notifyItemChanged(selectedGigPos);
                    }
                });
            }
            activity.isClickableView = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        if (url.equalsIgnoreCase(API_SEARCH_INFLU)) {
            binding.imgLoader.setVisibility(View.GONE);
            binding.loutMain.setVisibility(View.VISIBLE);
            binding.progressBar1.setVisibility(View.GONE);
            binding.txtViewMore1.setVisibility(View.VISIBLE);
            binding.loutViewMore1.setVisibility(View.GONE);

            if (arrGigList.size() == 0) {
                binding.lout1.setVisibility(View.GONE);
            }
            if (pageGigNo == 1) {
                binding.txtPh.setVisibility(View.VISIBLE);
            }
        } else if (url.equalsIgnoreCase(API_GET_PROFILE_INFO)) {
            activity.runOnUiThread(() -> {
                if (serviceSellersGigViewAdapter != null) {
                    serviceSellersGigViewAdapter.getData().get(selectedGigPos).isShowProgress = false;
                    serviceSellersGigViewAdapter.notifyItemChanged(selectedGigPos);
                }
            });
            influencersName = "";
        } else if (url.equalsIgnoreCase(API_REMOVE_AGENT) || url.equalsIgnoreCase(API_SAVE_AGENT)) {
            activity.toastMessage(message);
            activity.runOnUiThread(() -> {
                if (serviceSellersGigViewAdapter != null) {//best
                    serviceSellersGigViewAdapter.notifyItemChanged(selectedGigPos);
                }
            });
        }
        activity.isClickableView = false;
    }

    private void onSuccessGigList(InfluencerList socialInfluence) {

        if (socialInfluence != null) {
            binding.txtTitle1.setVisibility(View.GONE);
            if (socialInfluence.influencer != null) {
                filePath = socialInfluence.path;
//            isFirstOrder = socialInfluence.isFirstOrder;
//            couponData = socialInfluence.couponData;
                if (socialInfluence.influencer.size() > 0) {
                    binding.txtPh.setVisibility(View.GONE);
                    arrGigList.addAll(socialInfluence.influencer);
                    binding.loutViewMore1.setVisibility(View.VISIBLE);
                    if (pageGigNo == 1) {
                        if (arrGigList.size() < 4) {
                            binding.loutViewMore1.setVisibility(View.GONE);
                        }
                    }
                    setGigAdapter();
                }
            }
        }
    }

    private void setGigAdapter() {
        if (arrGigList != null && arrGigList.size() > 0) {
            binding.loutMain.setVisibility(View.VISIBLE);
            binding.lout1.setVisibility(View.VISIBLE);
            if (serviceSellersGigViewAdapter == null) {
                serviceSellersGigViewAdapter = new InfAdapter(activity, this, arrGigList, filePath);
                binding.rvAll1.setAdapter(serviceSellersGigViewAdapter);
            } else {
                serviceSellersGigViewAdapter.doRefresh(arrGigList);
            }

            binding.rvAll1.setVisibility(View.VISIBLE);
        } else {
            binding.lout1.setVisibility(View.GONE);
        }
    }

    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQ_FILTER) {
            if (data != null) {
                resetFilter();
                selectedSeller = data.getStringExtra("seller");
                selectedMawthooq = data.getStringExtra("mawthooq_status");
                selectedPlatform = data.getIntExtra("platform", 0);
                selectedPlatformName = data.getStringExtra("platformName");
                skillsArray = (ArrayList<ServicesModel.Data>) data.getSerializableExtra("skill");
                skillTagsArray = (ArrayList<SkillTags.Data>) data.getSerializableExtra("skilltags");
//                selectedPrice = (PriceRange) data.getSerializableExtra("selectedPrice");
                selectedPriceList = (ArrayList<PriceRange>) data.getSerializableExtra("selectedPrice");
                selectedFollowersList = (ArrayList<PriceRange>) data.getSerializableExtra("followers");
                selectedAgeList = (ArrayList<PriceRange>) data.getSerializableExtra("age");
                selectedSortBy = (SortByFilterModel) data.getSerializableExtra("sortBy");
                selectedGender = data.getIntExtra("gender", -1);
                selectedLanguage = data.getIntExtra("language", 0);
                selectedLocation = data.getIntExtra("location", 194);
                selectedCityId = data.getIntExtra("cityid", -1);
                filterCount = data.getIntExtra("filterCount", 3);

                binding.txtFilterCount.setText("" + filterCount);

                refreshData();
            }
        } else if (resultCode == RESULT_OK && requestCode == 4545) {
            if (data != null) {
                resetFilter();
                serviceCatId = data.getIntExtra(Constants.SERVICE_CATEGORY_ID, 0);
                String serviceCatName = data.getStringExtra(Constants.SERVICE_CATEGORY_NAME);
                binding.etSearch.setText(serviceCatName);
                refreshData();
                if (viewAllCategoryAdapter != null) {
                    viewAllCategoryAdapter.doRefresh(serviceCatId);
                }
            }
        }
    }

    @Override
    public void onClickCategory(HomeServiceCatg category) {
        serviceCatId = category.id;
        binding.etSearch.setText("");
        // resetFilter();
        refreshData();
    }


    @Override
    public void savedGigSuccess(String gigId) {
        notifyFavProgressGig(1);
        activity.isClickableView = false;
    }

    @Override
    public void removeGigSuccess(String gigId) {
        notifyFavProgressGig(0);
    }

    private void notifyFavProgressGig(int state) {
        try {
            serviceSellersGigViewAdapter.getData().get(selectedGigPos).isShowFavProgress = false;
            serviceSellersGigViewAdapter.getData().get(selectedGigPos).saved = state;
            serviceSellersGigViewAdapter.notifyItemChanged(selectedGigPos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveRemoveInfluencer(int id, boolean isRemoved) {
        if (!activity.isNetworkConnected()) return;

        HashMap<String, String> map = new HashMap<>();
        map.put("agent_profile_id", id + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, isRemoved ? API_REMOVE_AGENT : API_SAVE_AGENT, true, map);
    }

    @Override
    public void onClickFavouriteInf(InfluencerList.AllData data, int pos) {
        this.selectedGigPos = pos;
        if (data.is_profile == 0) {//services
            activity.saveRemoveGig(data.gigId, this, data.saved != 0);
        } else if (data.is_profile == 1 && data.id != null) { //profile
            saveRemoveInfluencer(data.id, data.saved == 1);
        }
    }

    @Override
    public void onClickViewInfluencer(InfluencerList.AllData data, int pos) {
        this.selectedGigPos = pos;
        influencersName = data.username;
//        if (data.is_profile != null && data.is_profile == 0) {
//            getGigDetails();
//        } else {
        getAgentByUsername(data.id);
    }
}
