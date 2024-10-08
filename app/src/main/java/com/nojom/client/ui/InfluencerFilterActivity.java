package com.nojom.client.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.adapter.AgeAdapter;
import com.nojom.client.adapter.FollowerRangeAdapter;
import com.nojom.client.adapter.FollowersListAdapter;
import com.nojom.client.adapter.PlatformAdapter;
import com.nojom.client.adapter.PriceRangeAdapter;
import com.nojom.client.adapter.SelectCityListAdapter;
import com.nojom.client.adapter.SelectCountryListAdapter;
import com.nojom.client.adapter.SelectItemAdapter;
import com.nojom.client.adapter.SkillAdapter;
import com.nojom.client.adapter.SocialServiceAdapter;
import com.nojom.client.adapter.SortByAdapter;
import com.nojom.client.databinding.ActivityInfluencerFilterBinding;
import com.nojom.client.model.CityModel;
import com.nojom.client.model.CountryModel;
import com.nojom.client.model.FollowersListModel;
import com.nojom.client.model.Language;
import com.nojom.client.model.PriceRange;
import com.nojom.client.model.ServicesModel;
import com.nojom.client.model.SkillTags;
import com.nojom.client.model.SocialPlatformModel;
import com.nojom.client.model.SortByFilterModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InfluencerFilterActivity extends BaseActivity implements SocialServiceAdapter.OnClickCategoryListener, View.OnClickListener, PriceRangeAdapter.OnClickCategoryListener, FollowerRangeAdapter.OnClickCategoryListener, SortByAdapter.OnClickCategoryListener, AgeAdapter.OnClickCategoryListener, SkillAdapter.OnClickSkillListener {
    private final List<FollowersListModel> arrFollowersList = new ArrayList<>();
    private final List<SortByFilterModel> sortByList = new ArrayList<>();
    private ActivityInfluencerFilterBinding binding;
    private int selectedPlatformID, selectedGender = -1, selectedLanguageID = -1, selectedLocationID = 194, selectedCityID = -1;
    private ArrayList<Language.Data> languagesArray;
    private ArrayList<CountryModel.Data> locationArray;
    private ArrayList<CityModel.Data> locationCityArray;
    private ArrayList<ServicesModel.Data> selectedSkillsArray = new ArrayList<>();
    private ArrayList<SkillTags.Data> selectedSkillTagsArray = new ArrayList<>();
    private SelectItemAdapter itemAdapter;
    private SelectCountryListAdapter selectCountryListAdapter;
    private SelectCityListAdapter selectCityListAdapter;
    private FollowersListAdapter followersListAdapter;
    private PlatformAdapter platformAdapter;
    private InfluencerFilterActivityVM influencerFilterActivityVM;
    private SocialServiceAdapter serviceAdapter;
    private SkillAdapter skillAdapter;
    private String selectedSeller = "ALL";
    private String selectedMawthooq = "0";
    private String selectedPlatformName = "";
    private int filterCount = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_influencer_filter);
        influencerFilterActivityVM = new InfluencerFilterActivityVM(Task24Application.getInstance(), binding, this);
        initData();
        influencerFilterActivityVM.fetchData();

    }

    private void initData() {
        languagesArray = new ArrayList<>();
        locationArray = new ArrayList<>();
        locationCityArray = new ArrayList<>();
        selectedSkillsArray = new ArrayList<>();
        selectedSkillTagsArray = new ArrayList<>();

        if (getCurrency().equals("SAR")) {
            binding.etPrFrom.setHint(getString(R.string.sar));
            binding.etPrTo.setHint(getString(R.string.sar));
        } else {
            binding.etPrFrom.setHint(getString(R.string.dollar));
            binding.etPrTo.setHint(getString(R.string.dollar));
        }

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (serviceAdapter != null) {
                    serviceAdapter.getFilter().filter(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.etSearchSkills.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (skillAdapter != null) {
                    skillAdapter.getFilter().filter(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.txtBestSeller.setOnClickListener(this);
        binding.txtMostFollower.setOnClickListener(this);
        binding.txtAll.setOnClickListener(this);
        binding.txtMAll.setOnClickListener(this);
        binding.txtSaved.setOnClickListener(this);
        binding.txtMHave.setOnClickListener(this);
        binding.txtHired.setOnClickListener(this);
        binding.txtMNo.setOnClickListener(this);
        binding.txtFemale.setOnClickListener(this);
        binding.txtMale.setOnClickListener(this);
        binding.txtOther.setOnClickListener(this);
        binding.txtLanguage.setOnClickListener(this);
        binding.txtPlatform.setOnClickListener(this);
        binding.txtLocation.setOnClickListener(this);
        binding.txtCity.setOnClickListener(this);
        binding.imgClose.setOnClickListener(this);
        binding.tvCancel.setOnClickListener(this);
        binding.tvApply.setOnClickListener(this);
        binding.tvClear.setOnClickListener(this);

        influencerFilterActivityVM.getMutableServiceList().observe(this, this::setServiceAdapter);

        influencerFilterActivityVM.getMutableLanguageList().observe(this, data -> {
            languagesArray = (ArrayList<Language.Data>) data;

            if (selectedLanguageID != -1) {
                for (Language.Data langData : languagesArray) {
                    if (langData.id == selectedLanguageID) {
                        binding.txtLanguage.setText(langData.getServNameByLang(getLanguage()));
                        break;
                    }
                }
            }
        });

        influencerFilterActivityVM.getMutableLocationList().observe(this, data -> {
            locationArray = (ArrayList<CountryModel.Data>) data;

            for (CountryModel.Data locData : locationArray) {
                if (locData.id == selectedLocationID) {
                    locData.isSelected = true;
                    binding.txtLocation.setText(locData.getCountryName(getLanguage()));
                    break;
                }
            }

        });

        influencerFilterActivityVM.getMutableLocationCityList().observe(this, data -> {
            locationCityArray = (ArrayList<CityModel.Data>) data;

            if (selectedCityID != -1) {
                for (CityModel.Data locData : locationCityArray) {
                    if (locData.id == selectedCityID) {
                        locData.isSelected = true;
                        binding.txtCity.setText(locData.getCityName(getLanguage()));
                        break;
                    }
                }
            }

        });


        influencerFilterActivityVM.getMutableSkillTagsList().observe(this, data -> {
            if (data != null) {
                setSkillAdapter(data);
            }
        });

        influencerFilterActivityVM.getMutablePlatformList().observe(this, this::setPlatformData);

        setFilterData();

//        arrFollowersList.add(new FollowersListModel(1, getString(R.string._1k_10k)));
//        arrFollowersList.add(new FollowersListModel(2, getString(R.string._10k_50k)));
//        arrFollowersList.add(new FollowersListModel(3, getString(R.string._50k_100k)));
//        arrFollowersList.add(new FollowersListModel(4, getString(R.string._100k_500k)));
//        arrFollowersList.add(new FollowersListModel(5, getString(R.string._500k_1m)));
//        arrFollowersList.add(new FollowersListModel(6, getString(R.string._1m_10m)));
//        arrFollowersList.add(new FollowersListModel(7, getString(R.string._10m_)));

        setPriceRangeAdapter();

        sortByList.add(new SortByFilterModel(getString(R.string.price_high_to_low), "1"));
        sortByList.add(new SortByFilterModel(getString(R.string.price_low_to_high), "2"));
        sortByList.add(new SortByFilterModel(getString(R.string.number_of_follower_high_to_low), "3"));
        sortByList.add(new SortByFilterModel(getString(R.string.number_of_follower_low_to_high), "4"));
        sortByList.add(new SortByFilterModel(getString(R.string.newest), "5"));

        setSortByAdapter(sortByList);

        setFollowerAdapter();

        setAgeAdapter();
    }

    private void setSortByAdapter(List<SortByFilterModel> sortByList) {
        SortByAdapter serviceAdapter = new SortByAdapter(this, sortByList, this);
        serviceAdapter.setSelectedLanguageList(selectedSortby);
        binding.rvSortBy.setAdapter(serviceAdapter);
    }

    private void setSkillAdapter(List<SkillTags.Data> sortByList) {

        if (getIntent().getSerializableExtra("skilltags") != null && getIntent().getSerializableExtra("skilltags") != "") {
            ArrayList<SkillTags.Data> skillsArray = (ArrayList<SkillTags.Data>) getIntent().getSerializableExtra("skilltags");

            if (skillsArray != null && skillsArray.size() > 0) {
                for (int i = 0; i < sortByList.size(); i++) {
                    for (SkillTags.Data data1 : skillsArray) {
                        if (sortByList.get(i).id == data1.id) {
                            selectedSkillTagsArray.add(sortByList.get(i));
                            sortByList.get(i).isSelected = !sortByList.get(i).isSelected;
                        }
                    }
                }
            }
        }

        skillAdapter = new SkillAdapter(this, sortByList, this);
        skillAdapter.setSelectedLanguageList(selectedSkillTagsArray);
        binding.rvSkillTags.setAdapter(skillAdapter);
    }

    FollowerRangeAdapter followerAdapter;

    private void setFollowerAdapter() {
        List<PriceRange> arrFollRangeList = new ArrayList<>();
        arrFollRangeList.add(new PriceRange(0, 100, getString(R.string._0k_100k), 0));
        arrFollRangeList.add(new PriceRange(100, 500, getString(R.string._100k_500k_), 1));
        arrFollRangeList.add(new PriceRange(500, 1000000, getString(R.string._500k_1m_), 2));
        arrFollRangeList.add(new PriceRange(1000000, 5000000, getString(R.string._1m_5m), 3));
        arrFollRangeList.add(new PriceRange(5000000, 0, getString(R.string._5m), 4));
        arrFollRangeList.add(new PriceRange(-1, -1, getString(R.string.custom), 5));

        if (selectedFollowerRangeList != null && selectedFollowerRangeList.size() > 0) {
            for (PriceRange mP : arrFollRangeList) {
                for (PriceRange sP : selectedFollowerRangeList) {
                    if (sP.id.equals(mP.id) && sP.isSelected) {
                        mP.isSelected = true;
                        break;
                    }
                }
            }
        }

        followerAdapter = new FollowerRangeAdapter(this, arrFollRangeList, this);
//        serviceAdapter.setSelectedLanguageList(selectedFollowerRange);
        binding.rvFollower.setAdapter(followerAdapter);
    }

    AgeAdapter ageAdapter;

    private void setAgeAdapter() {
        List<PriceRange> arrAgeRangeList = new ArrayList<>();

        arrAgeRangeList.add(new PriceRange(13, 17, getString(R.string._13_17), 0));
        arrAgeRangeList.add(new PriceRange(18, 24, getString(R.string._18_24), 1));
        arrAgeRangeList.add(new PriceRange(25, 34, getString(R.string._25_34), 2));
        arrAgeRangeList.add(new PriceRange(35, 44, getString(R.string._35_44), 3));
        arrAgeRangeList.add(new PriceRange(45, 54, getString(R.string._45_54), 4));
        arrAgeRangeList.add(new PriceRange(55, 64, getString(R.string._55_64), 5));
        arrAgeRangeList.add(new PriceRange(65, 0, getString(R.string._65), 6));
        arrAgeRangeList.add(new PriceRange(-1, -1, getString(R.string.custom), 7));

        if (selectedAgeRangeList != null && selectedAgeRangeList.size() > 0) {
            for (PriceRange mP : arrAgeRangeList) {
                for (PriceRange sP : selectedAgeRangeList) {
                    if (sP.id.equals(mP.id) && sP.isSelected) {
                        mP.isSelected = true;
                        break;
                    }
                }
            }
        }

        ageAdapter = new AgeAdapter(this, arrAgeRangeList, this);
//        serviceAdapter.setSelectedLanguageList(selectedAgeRange);
        binding.rvAge.setAdapter(ageAdapter);
    }

    private PriceRange selectedPriceRange = null;
    private ArrayList<PriceRange> selectedPriceRangeList = null;
    private PriceRange selectedFollowerRange = null;
    private ArrayList<PriceRange> selectedFollowerRangeList = null;
    private PriceRange selectedAgeRange = null;
    private ArrayList<PriceRange> selectedAgeRangeList = null;
    private SortByFilterModel selectedSortby = null;

    PriceRangeAdapter priceAdapter;

    private void setPriceRangeAdapter() {
        List<PriceRange> arrPriceRangeList = new ArrayList<>();
        if (getCurrency().equals("SAR")) {
            arrPriceRangeList.add(new PriceRange(0, 1000, getString(R.string._0_1000) + " " + getString(R.string.sar), 0));
            arrPriceRangeList.add(new PriceRange(1000, 2500, getString(R.string._1000_2500) + " " + getString(R.string.sar), 1));
            arrPriceRangeList.add(new PriceRange(2500, 5000, getString(R.string._2500_5000) + " " + getString(R.string.sar), 2));
            arrPriceRangeList.add(new PriceRange(5000, 10000, getString(R.string._5000_10000) + " " + getString(R.string.sar), 3));
            arrPriceRangeList.add(new PriceRange(10000, 0, getString(R.string._10000) + " " + getString(R.string.sar), 4));
            arrPriceRangeList.add(new PriceRange(-1, -1, getString(R.string.custom), 5));
        } else {
            arrPriceRangeList.add(new PriceRange(0, 1000, "0 – 1000 " + getString(R.string.dollar), 0));
            arrPriceRangeList.add(new PriceRange(1000, 2500, "1000 – 2500 " + getString(R.string.dollar), 1));
            arrPriceRangeList.add(new PriceRange(2500, 5000, "2500 – 5000 " + getString(R.string.dollar), 2));
            arrPriceRangeList.add(new PriceRange(5000, 10000, "5000 – 10000 " + getString(R.string.dollar), 3));
            arrPriceRangeList.add(new PriceRange(10000, 0, "+10000 " + getString(R.string.dollar), 4));
            arrPriceRangeList.add(new PriceRange(-1, -1, getString(R.string.custom), 5));
        }

        if (selectedPriceRangeList != null && selectedPriceRangeList.size() > 0) {
            for (PriceRange mP : arrPriceRangeList) {
                for (PriceRange sP : selectedPriceRangeList) {
                    if (sP.id.equals(mP.id) && sP.isSelected) {
                        mP.isSelected = true;
                        break;
                    }
                }
            }
        }
        priceAdapter = new PriceRangeAdapter(this, arrPriceRangeList, this);
//        serviceAdapter.setSelectedLanguageList(selectedPriceRangeL);
        binding.rvPriceRange.setAdapter(priceAdapter);
    }

    private void setPlatformData(List<SocialPlatformModel.Data> data) {
        platformAdapter = new PlatformAdapter(this, data);
    }

    private void setServiceAdapter(List<ServicesModel.Data> data) {
        if (getIntent().getSerializableExtra("skill") != null && getIntent().getSerializableExtra("skill") != "") {
            ArrayList<ServicesModel.Data> skillsArray = (ArrayList<ServicesModel.Data>) getIntent().getSerializableExtra("skill");

            if (skillsArray != null && skillsArray.size() > 0) {
                for (int i = 0; i < data.size(); i++) {
                    for (ServicesModel.Data data1 : skillsArray) {
                        if (data.get(i).id == data1.id) {
                            selectedSkillsArray.add(data.get(i));
                            data.get(i).isSelected = !data.get(i).isSelected;
                        }
                    }
                }
            }
        }
        ViewCompat.setNestedScrollingEnabled(binding.rvSkills, true);

        serviceAdapter = new SocialServiceAdapter(this, data, this);
        serviceAdapter.setSelectedLanguageList(selectedSkillsArray);
        binding.rvSkills.setAdapter(serviceAdapter);
    }

    @Override
    public void onClickCategory(boolean isAdded, ServicesModel.Data data, int adapterPos) {
        addRemoveItem(isAdded, data, adapterPos);
    }

    public void addRemoveItem(boolean isAdded, ServicesModel.Data model, int adapterPos) {
        if (selectedSkillsArray != null && selectedSkillsArray.size() > 0) {
            if (isAdded) {
                selectedSkillsArray.add(model);
            } else {
                selectedSkillsArray.remove(model);
            }
        } else {
            selectedSkillsArray.add(model);
        }
        if (serviceAdapter != null) {
            serviceAdapter.setSelectedLanguageList(selectedSkillsArray);
            serviceAdapter.notifyItemChanged(adapterPos);
        }
    }

    public void addRemoveTags(boolean isAdded, SkillTags.Data model, int adapterPos) {
        if (selectedSkillTagsArray != null && selectedSkillTagsArray.size() > 0) {
            if (isAdded) {
                selectedSkillTagsArray.add(model);
            } else {
                selectedSkillTagsArray.remove(model);
            }
        } else {
            if (selectedSkillTagsArray != null) {
                selectedSkillTagsArray.add(model);
            }
        }
        if (skillAdapter != null) {
            skillAdapter.setSelectedLanguageList(selectedSkillTagsArray);
            skillAdapter.notifyItemChanged(adapterPos);
        }
    }

    private void setFilterData() {
        try {
            if (getIntent() != null) {
                selectedSortby = (SortByFilterModel) getIntent().getSerializableExtra("sortBy");
//                selectedPriceRange = (PriceRange) getIntent().getSerializableExtra("priceRange");
                selectedPriceRangeList = (ArrayList<PriceRange>) getIntent().getSerializableExtra("priceRange");
//                selectedFollowerRange = (PriceRange) getIntent().getSerializableExtra("followers");
                selectedFollowerRangeList = (ArrayList<PriceRange>) getIntent().getSerializableExtra("followers");
                selectedAgeRangeList = (ArrayList<PriceRange>) getIntent().getSerializableExtra("age");

                if (getIntent().getStringExtra("seller") != null && !TextUtils.isEmpty(getIntent().getStringExtra("seller"))) {
                    selectedSeller = getIntent().getStringExtra("seller");
                    if (selectedSeller.equalsIgnoreCase("ALL")) {
                        sellerClick(0);
                    } else if (selectedSeller.equalsIgnoreCase("SAVED")) {
                        sellerClick(1);
                    } else if (selectedSeller.equalsIgnoreCase("HIRED")) {
                        sellerClick(2);
                    }
                }

                if (getIntent().getStringExtra("mawthooq_status") != null && !TextUtils.isEmpty(getIntent().getStringExtra("mawthooq_status"))) {
                    selectedMawthooq = getIntent().getStringExtra("mawthooq_status");
                    if (TextUtils.isEmpty(selectedMawthooq)) {
                        selectedMawthooq = "0";
                    }
                    if (selectedMawthooq.equalsIgnoreCase("0")) {
                        mawthooqClick(0);
                    } else if (selectedMawthooq.equalsIgnoreCase("1")) {
                        mawthooqClick(2);
                    } else if (selectedMawthooq.equalsIgnoreCase("2")) {
                        mawthooqClick(1);
                    }
                }

                if (getIntent().getIntExtra("platform", 0) != 0) {
                    selectedPlatformID = getIntent().getIntExtra("platform", 0);
                    selectedPlatformName = getIntent().getStringExtra("platformName");
                    binding.txtPlatform.setText(selectedPlatformName);
                }


                /*if (selectedPriceRange != null) {
                    if (selectedPriceRange.minPrice == -1 && selectedPriceRange.maxPrice == -1) {
                        priceFrom = getIntent().getStringExtra("priceFrom");
                        binding.etPrFrom.setText(priceFrom);

                        priceTo = getIntent().getStringExtra("priceTo");
                        binding.etPrTo.setText(priceTo);

                        binding.linPrice.setVisibility(View.VISIBLE);
                    }
                }*/

                if (selectedPriceRangeList != null && selectedPriceRangeList.size() > 0) {
                    for (PriceRange ra : selectedPriceRangeList) {
                        if (ra.id == 5) {//custom
                            binding.etPrFrom.setText("" + ra.minPrice);
                            binding.etPrTo.setText("" + ra.maxPrice);
                            binding.linPrice.setVisibility(View.VISIBLE);
                        }
                    }

                }

                /*if (selectedFollowerRange != null) {
                    if (selectedFollowerRange.minPrice == -1 && selectedFollowerRange.maxPrice == -1) {
                        maxFollo = getIntent().getStringExtra("maxFoll");
                        binding.etCountFrom.setText(maxFollo);

                        minFollo = getIntent().getStringExtra("minFoll");
                        binding.etCountTo.setText(minFollo);

                        binding.linFollower.setVisibility(View.VISIBLE);
                    }
                }*/
                if (selectedFollowerRangeList != null && selectedFollowerRangeList.size() > 0) {
                    for (PriceRange ra : selectedFollowerRangeList) {
                        if (ra.id == 5) {//custom
                            binding.etCountFrom.setText("" + ra.minPrice);
                            binding.etCountTo.setText("" + ra.maxPrice);
                            binding.linFollower.setVisibility(View.VISIBLE);
                        }
                    }
                }
                /*if (selectedAgeRange != null) {
                    if (selectedAgeRange.minPrice == -1 && selectedAgeRange.maxPrice == -1) {
                        maxAge = getIntent().getStringExtra("maxAge");
                        binding.etAgeTo.setText(maxAge);

                        minAge = getIntent().getStringExtra("minAge");
                        binding.etAgeFrom.setText(minAge);

                        binding.linAge.setVisibility(View.VISIBLE);
                    }
                }*/
                if (selectedAgeRangeList != null && selectedAgeRangeList.size() > 0) {
                    for (PriceRange ra : selectedAgeRangeList) {
                        if (ra.id == 7) {//custom
                            binding.etAgeFrom.setText("" + ra.minPrice);
                            binding.etAgeTo.setText("" + ra.maxPrice);
                            binding.linAge.setVisibility(View.VISIBLE);
                        }
                    }
                }

//                if (getIntent().getIntExtra("followers", 0) != 0) {
//                    selectedFollowers = getIntent().getIntExtra("followers", 0);
////                    binding.txtFollowers.setText(Utils.getPlatformTxt(selectedFollowers) + " " + getString(R.string.followers));
//                }

                if (getIntent().getIntExtra("gender", -1) != -1) {
                    selectedGender = getIntent().getIntExtra("gender", -1);
                    genderClick(selectedGender);
                }

                if (getIntent().getIntExtra("language", 0) != 0) {
                    selectedLanguageID = getIntent().getIntExtra("language", 0);
                }

                if (getIntent().getIntExtra("location", 0) != 0) {
                    selectedLocationID = getIntent().getIntExtra("location", 0);
                }
                if (getIntent().getIntExtra("cityid", 0) != 0) {
                    selectedCityID = getIntent().getIntExtra("cityid", 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.txt_all:
                sellerClick(0);
                break;
            case R.id.txt_saved:
                sellerClick(1);
                break;
            case R.id.txt_hired:
                sellerClick(2);
                break;
            case R.id.txt_m_all:
                mawthooqClick(0);
                break;
            case R.id.txt_m_have:
                mawthooqClick(1);
                break;
            case R.id.txt_m_no:
                mawthooqClick(2);
                break;
            case R.id.txt_female:
                genderClick(1);
                break;
            case R.id.txt_male:
                genderClick(2);
                break;
            case R.id.txt_other:
                genderClick(0);
                break;
            case R.id.txt_language:
                showLanguageSelectDialog();
                break;
            case R.id.txt_location:
                showLocationSelectDialog();
                break;
            case R.id.txt_city:
                showCitySelectDialog();
                break;
//            case R.id.txt_followers:
//                showFollowersList();
//                break;
            case R.id.txt_platform:
                showPlatformSelectDialog();
                break;
            case R.id.img_close:
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_apply:
                Intent intent = new Intent();
//                intent.putExtra("sortBy", selectedSortBy);
                intent.putExtra("seller", selectedSeller);
                intent.putExtra("mawthooq_status", selectedMawthooq);
                intent.putExtra("platform", selectedPlatformID);
                if (selectedPlatformID != 0) {
                    filterCount++;
                }
                intent.putExtra("platformName", selectedPlatformName);
                intent.putExtra("skill", selectedSkillsArray);
                if (selectedSkillsArray != null && selectedSkillsArray.size() > 0) {
                    filterCount++;
                }
                intent.putExtra("skilltags", selectedSkillTagsArray);
                if (selectedSkillTagsArray != null && selectedSkillTagsArray.size() > 0) {
                    filterCount++;
                }
//                if (selectedPriceRange != null && selectedPriceRange.minPrice == -1 && selectedPriceRange.maxPrice == -1) {
//                    intent.putExtra("priceFrom", Objects.requireNonNull(binding.etPrFrom.getText()).toString());
//                    intent.putExtra("priceTo", Objects.requireNonNull(binding.etPrTo.getText()).toString());
//                }
                selectedPriceRangeList = priceAdapter.getSelectedItem();
                intent.putExtra("selectedPrice", selectedPriceRangeList);
                if (selectedPriceRangeList != null && selectedPriceRangeList.size() > 0) {
                    filterCount++;
                }
//                intent.putExtra("selectedFollower", selectedFollowerRange);
                intent.putExtra("sortBy", selectedSortby);
                if (selectedSortby != null) {
                    filterCount++;
                }
                selectedFollowerRangeList = followerAdapter.getSelectedItem();
                intent.putExtra("followers", selectedFollowerRangeList);
                if (selectedFollowerRangeList != null && selectedFollowerRangeList.size() > 0) {
                    filterCount++;
                }
                selectedAgeRangeList = ageAdapter.getSelectedItem();
                intent.putExtra("age", selectedAgeRangeList);
                if (selectedAgeRangeList != null && selectedAgeRangeList.size() > 0) {
                    filterCount++;
                }
//                if (selectedFollowerRange != null && selectedFollowerRange.minPrice == -1 && selectedFollowerRange.maxPrice == -1) {
//                    intent.putExtra("maxFoll", Objects.requireNonNull(binding.etCountTo.getText()).toString());
//                    intent.putExtra("minFoll", Objects.requireNonNull(binding.etCountFrom.getText()).toString());
//                }
                /*if (selectedAgeRange != null && selectedAgeRange.minPrice == -1 && selectedAgeRange.maxPrice == -1) {
                    intent.putExtra("maxAge", Objects.requireNonNull(binding.etAgeTo.getText()).toString());
                    intent.putExtra("minAge", Objects.requireNonNull(binding.etAgeFrom.getText()).toString());
                }*/
                intent.putExtra("gender", selectedGender);
                if (selectedGender != -1) {
                    filterCount++;
                }

                intent.putExtra("language", selectedLanguageID);
                intent.putExtra("location", selectedLocationID);
                intent.putExtra("cityid", selectedCityID);
                if (selectedCityID != -1) {
                    filterCount++;
                }
                intent.putExtra("filterCount", filterCount);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.tv_clear:
                selectedSortby = null;
                selectedPriceRange = null;
                selectedPriceRangeList = new ArrayList<>();
                selectedFollowerRange = null;
                selectedFollowerRangeList = new ArrayList<>();
                selectedAgeRange = null;
                selectedAgeRangeList = new ArrayList<>();
                filterCount = 3;
                selectedSeller = "ALL";
                selectedMawthooq = "0";
                selectedSkillsArray = new ArrayList<>();
                selectedSkillTagsArray = new ArrayList<>();
                if (skillAdapter != null) {
                    skillAdapter.setSelectedLanguageList(selectedSkillTagsArray);
                    skillAdapter.notifyDataSetChanged();
                }
                if (serviceAdapter != null) {
                    serviceAdapter.setSelectedLanguageList(selectedSkillsArray);
                    serviceAdapter.notifyDataSetChanged();
                }
                sellerClick(0);
                mawthooqClick(0);
                selectedPlatformID = 0;
                selectedPlatformName = "";
                binding.txtPlatform.setText(getString(R.string.select));
//                selectedFollowers = 0;
//                binding.txtFollowers.setText(getString(R.string.select));
                selectedGender = -1;
                genderClick(-1);
                selectedLanguageID = -1;
                binding.txtLanguage.setText(getString(R.string.select));
                selectedLocationID = 194;
                selectedCityID = -1;
                binding.txtCity.setText(getString(R.string.select));
                binding.etPrFrom.setText("");
                binding.etPrTo.setText("");
                binding.etCountFrom.setText("");
                binding.etCountTo.setText("");
                binding.etAgeFrom.setText("");
                binding.etAgeTo.setText("");
                setSortByAdapter(sortByList);
                setPriceRangeAdapter();
                setFollowerAdapter();
                setAgeAdapter();
                binding.linPrice.setVisibility(View.GONE);
                binding.linAge.setVisibility(View.GONE);
                binding.linFollower.setVisibility(View.GONE);

                for (CountryModel.Data locData : locationArray) {
                    if (locData.id == selectedLocationID) {
                        locData.isSelected = true;
                        binding.txtLocation.setText(locData.getCountryName(getLanguage()));
                        break;
                    }
                }
                influencerFilterActivityVM.getAllCity(selectedLocationID);
                break;
        }
    }

    private void genderClick(int pos) {
        if (pos == 1) {//all
            selectedGender = 1;
            binding.txtFemale.setBackgroundResource(R.drawable.blue_button_bg);
            binding.txtFemale.setTextColor(Color.WHITE);
            binding.txtMale.setBackgroundResource(R.drawable.white_button_bg);
            binding.txtMale.setTextColor(Color.BLACK);
            binding.txtOther.setBackgroundResource(R.drawable.white_button_bg);
            binding.txtOther.setTextColor(Color.BLACK);
        } else if (pos == 2) {//saved
            selectedGender = 2;
            binding.txtMale.setBackgroundResource(R.drawable.blue_button_bg);
            binding.txtMale.setTextColor(Color.WHITE);
            binding.txtFemale.setBackgroundResource(R.drawable.white_button_bg);
            binding.txtFemale.setTextColor(Color.BLACK);
            binding.txtOther.setBackgroundResource(R.drawable.white_button_bg);
            binding.txtOther.setTextColor(Color.BLACK);
        } else if (pos == 0) {//hired
            selectedGender = 0;
            binding.txtOther.setBackgroundResource(R.drawable.blue_button_bg);
            binding.txtOther.setTextColor(Color.WHITE);
            binding.txtMale.setBackgroundResource(R.drawable.white_button_bg);
            binding.txtMale.setTextColor(Color.BLACK);
            binding.txtFemale.setBackgroundResource(R.drawable.white_button_bg);
            binding.txtFemale.setTextColor(Color.BLACK);
        } else {
            selectedGender = -1;
            binding.txtFemale.setBackgroundResource(R.drawable.white_button_bg);
            binding.txtFemale.setTextColor(Color.BLACK);
            binding.txtMale.setBackgroundResource(R.drawable.white_button_bg);
            binding.txtMale.setTextColor(Color.BLACK);
            binding.txtOther.setBackgroundResource(R.drawable.white_button_bg);
            binding.txtOther.setTextColor(Color.BLACK);
        }
    }

    private void sellerClick(int pos) {
        if (pos == 0) {//all
            selectedSeller = "ALL";
            binding.txtAll.setBackgroundResource(R.drawable.blue_button_bg);
            binding.txtAll.setTextColor(Color.WHITE);
            binding.txtSaved.setBackgroundResource(R.drawable.white_button_bg);
            binding.txtSaved.setTextColor(Color.BLACK);
            binding.txtHired.setBackgroundResource(R.drawable.white_button_bg);
            binding.txtHired.setTextColor(Color.BLACK);
        } else if (pos == 1) {//saved
            selectedSeller = "SAVED";
            binding.txtSaved.setBackgroundResource(R.drawable.blue_button_bg);
            binding.txtSaved.setTextColor(Color.WHITE);
            binding.txtAll.setBackgroundResource(R.drawable.white_button_bg);
            binding.txtAll.setTextColor(Color.BLACK);
            binding.txtHired.setBackgroundResource(R.drawable.white_button_bg);
            binding.txtHired.setTextColor(Color.BLACK);
        } else if (pos == 2) {//hired
            selectedSeller = "HIRED";
            binding.txtHired.setBackgroundResource(R.drawable.blue_button_bg);
            binding.txtHired.setTextColor(Color.WHITE);
            binding.txtSaved.setBackgroundResource(R.drawable.white_button_bg);
            binding.txtSaved.setTextColor(Color.BLACK);
            binding.txtAll.setBackgroundResource(R.drawable.white_button_bg);
            binding.txtAll.setTextColor(Color.BLACK);
        }
    }

    private void mawthooqClick(int pos) {
        if (pos == 0) {//all
            selectedMawthooq = "0";
            binding.txtMAll.setBackgroundResource(R.drawable.blue_button_bg);
            binding.txtMAll.setTextColor(Color.WHITE);
            binding.txtMHave.setBackgroundResource(R.drawable.white_button_bg);
            binding.txtMHave.setTextColor(Color.BLACK);
            binding.txtMNo.setBackgroundResource(R.drawable.white_button_bg);
            binding.txtMNo.setTextColor(Color.BLACK);
        } else if (pos == 1) {//yes have = verified
            selectedMawthooq = "2";
            binding.txtMHave.setBackgroundResource(R.drawable.blue_button_bg);
            binding.txtMHave.setTextColor(Color.WHITE);
            binding.txtMAll.setBackgroundResource(R.drawable.white_button_bg);
            binding.txtMAll.setTextColor(Color.BLACK);
            binding.txtMNo.setBackgroundResource(R.drawable.white_button_bg);
            binding.txtMNo.setTextColor(Color.BLACK);
        } else if (pos == 2) {//not verified = no
            selectedMawthooq = "1";
            binding.txtMNo.setBackgroundResource(R.drawable.blue_button_bg);
            binding.txtMNo.setTextColor(Color.WHITE);
            binding.txtMHave.setBackgroundResource(R.drawable.white_button_bg);
            binding.txtMHave.setTextColor(Color.BLACK);
            binding.txtMAll.setBackgroundResource(R.drawable.white_button_bg);
            binding.txtMAll.setTextColor(Color.BLACK);
        }
    }

    private void showFollowersList() {
        final Dialog dialog = new Dialog(this, R.style.Theme_Design_Light_BottomSheetDialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_item_select_black);
        dialog.setCancelable(true);

        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        TextView tvApply = dialog.findViewById(R.id.tv_apply);
        final EditText etSearch = dialog.findViewById(R.id.et_search);
        RecyclerView rvTypes = dialog.findViewById(R.id.rv_items);

        etSearch.setVisibility(View.GONE);

        rvTypes.setLayoutManager(new LinearLayoutManager(this));
        if (arrFollowersList != null && arrFollowersList.size() > 0) {
            for (FollowersListModel data : arrFollowersList) {
//                data.isSelected = data.followers.equalsIgnoreCase(binding.txtFollowers.getText().toString());
            }
            followersListAdapter = new FollowersListAdapter(this, arrFollowersList);
            rvTypes.setAdapter(followersListAdapter);
        }

        tvCancel.setOnClickListener(v -> dialog.dismiss());

        tvApply.setOnClickListener(v -> {
            if (followersListAdapter != null && followersListAdapter.getSelectedItem() != null) {
//                selectedFollowers = followersListAdapter.getSelectedItem().followersLeave;
//                binding.txtFollowers.setText(followersListAdapter.getSelectedItem().followers);
                dialog.dismiss();
            } else {
                toastMessage(getString(R.string.please_select_one_followers));
            }
            dialog.dismiss();
        });


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }

    @SuppressLint("StringFormatInvalid")
    private void showLanguageSelectDialog() {
        @SuppressLint("PrivateResource") final Dialog dialog = new Dialog(this, R.style.Theme_Design_Light_BottomSheetDialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_item_select_black);
        dialog.setCancelable(true);

        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        TextView tvApply = dialog.findViewById(R.id.tv_apply);
        final EditText etSearch = dialog.findViewById(R.id.et_search);
        RecyclerView rvTypes = dialog.findViewById(R.id.rv_items);

        etSearch.setHint(String.format(getString(R.string.search_for), getString(R.string.language).toLowerCase()));

        rvTypes.setLayoutManager(new LinearLayoutManager(this));
        if (languagesArray != null && languagesArray.size() > 0) {
            for (Language.Data data : languagesArray) {
                data.isSelected = data.getServNameByLang(getLanguage()).equalsIgnoreCase(binding.txtLanguage.getText().toString());
            }
            itemAdapter = new SelectItemAdapter(this, languagesArray, true);
            rvTypes.setAdapter(itemAdapter);
        }

        tvCancel.setOnClickListener(v -> dialog.dismiss());

        tvApply.setOnClickListener(v -> {
            if (itemAdapter != null && itemAdapter.getSelectedItem() != null) {
                selectedLanguageID = itemAdapter.getSelectedItem().id;
                binding.txtLanguage.setText(itemAdapter.getSelectedItem().getServNameByLang(getLanguage()));
                dialog.dismiss();
            } else {
                toastMessage(getString(R.string.please_select_one_language));
            }
            dialog.dismiss();
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (itemAdapter != null) itemAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }

    @SuppressLint("StringFormatInvalid")
    private void showPlatformSelectDialog() {
        @SuppressLint("PrivateResource") final Dialog dialog = new Dialog(this, R.style.Theme_Design_Light_BottomSheetDialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_language);
        dialog.setCancelable(true);

        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        TextView tvApply = dialog.findViewById(R.id.tv_apply);
        RecyclerView rvTypes = dialog.findViewById(R.id.rv_items);

        rvTypes.setLayoutManager(new LinearLayoutManager(this));
        if (platformAdapter != null) {
            rvTypes.setAdapter(platformAdapter);
        }

        tvCancel.setOnClickListener(v -> dialog.dismiss());

        tvApply.setOnClickListener(v -> {
            if (platformAdapter != null && platformAdapter.getSelectedItem() != null) {
                selectedPlatformID = platformAdapter.getSelectedItem().id;
                selectedPlatformName = platformAdapter.getSelectedItem().getServNameByLang(getLanguage());
                binding.txtPlatform.setText(platformAdapter.getSelectedItem().getServNameByLang(getLanguage()));
                dialog.dismiss();
            } else {
                toastMessage(getString(R.string.please_select_one_platform));
            }
            dialog.dismiss();
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }

    private void showLocationSelectDialog() {
        @SuppressLint("PrivateResource") final Dialog dialog = new Dialog(this, R.style.Theme_Design_Light_BottomSheetDialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_item_select_black);
        dialog.setCancelable(true);

        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        TextView tvApply = dialog.findViewById(R.id.tv_apply);
        final EditText etSearch = dialog.findViewById(R.id.et_search);
        RecyclerView rvTypes = dialog.findViewById(R.id.rv_items);

//        etSearch.setHint(String.format(getString(R.string.search_for), getString(R.string.language).toLowerCase()));

        rvTypes.setLayoutManager(new LinearLayoutManager(this));
        if (locationArray != null && locationArray.size() > 0) {
//            for (CountryModel.Data data : locationArray) {
//                data.isSelected = data.getCountryName(getLanguage()).equalsIgnoreCase(binding.txtLocation.getText().toString());
//            }
            selectCountryListAdapter = new SelectCountryListAdapter(this, locationArray);
            selectCountryListAdapter.setBlackColor(true);
            rvTypes.setAdapter(selectCountryListAdapter);
        }

        tvCancel.setOnClickListener(v -> dialog.dismiss());

        tvApply.setOnClickListener(v -> {
            if (selectCountryListAdapter != null && selectCountryListAdapter.getSelectedItem() != null) {
                selectedLocationID = selectCountryListAdapter.getSelectedItem().id;
                binding.txtLocation.setText(selectCountryListAdapter.getSelectedItem().getCountryName(getLanguage()));
                selectedCityID = -1;
                binding.txtCity.setText(getString(R.string.select));
                influencerFilterActivityVM.getAllCity(selectedLocationID);
                dialog.dismiss();
            } else {
                toastMessage(getString(R.string.please_select_one_location));
            }
            dialog.dismiss();
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (selectCountryListAdapter != null)
                    selectCountryListAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }


    private void showCitySelectDialog() {
        @SuppressLint("PrivateResource") final Dialog dialog = new Dialog(this, R.style.Theme_Design_Light_BottomSheetDialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_item_select_black);
        dialog.setCancelable(true);

        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        TextView tvApply = dialog.findViewById(R.id.tv_apply);
        final EditText etSearch = dialog.findViewById(R.id.et_search);
        RecyclerView rvTypes = dialog.findViewById(R.id.rv_items);

//        etSearch.setHint(String.format(getString(R.string.search_for), getString(R.string.language).toLowerCase()));

        rvTypes.setLayoutManager(new LinearLayoutManager(this));
        if (locationCityArray != null && locationCityArray.size() > 0) {

            selectCityListAdapter = new SelectCityListAdapter(this, locationCityArray);
            selectCityListAdapter.setBlackColor(true);
            rvTypes.setAdapter(selectCityListAdapter);
        }

        tvCancel.setOnClickListener(v -> dialog.dismiss());

        tvApply.setOnClickListener(v -> {
            if (selectCityListAdapter != null && selectCityListAdapter.getSelectedItem() != null) {
                selectedCityID = selectCityListAdapter.getSelectedItem().id;
                binding.txtCity.setText(selectCityListAdapter.getSelectedItem().getCityName(getLanguage()));
                dialog.dismiss();
            } else {
                toastMessage(getString(R.string.please_select_one_city));
            }
            dialog.dismiss();
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (selectCityListAdapter != null)
                    selectCityListAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }


    @Override
    public void onClickCategory(boolean isAdded, PriceRange data, int adapterPos) {
//        selectedPriceRange = data;
        if (data.id == 5) {//custom case
            if (data.isSelected) {
                binding.linPrice.setVisibility(View.VISIBLE);
                binding.etPrFrom.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (TextUtils.isEmpty(s)) {
                            data.minPrice = 0;
                        } else {
                            data.minPrice = Integer.parseInt(s.toString());
                        }

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                binding.etPrTo.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (TextUtils.isEmpty(s)) {
                            data.maxPrice = 0;
                        } else {
                            data.maxPrice = Integer.parseInt(s.toString());
                        }

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            } else {
                binding.etPrTo.setText("");
                binding.etPrFrom.setText("");
                binding.linPrice.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClickCategory(boolean isAdded, SortByFilterModel data, int adapterPos) {
        selectedSortby = data;
    }

    @Override
    public void onClickFollower(boolean isAdded, PriceRange data, int adapterPos) {
        selectedFollowerRange = data;

        if (data.id == 5) {//custom case
            if (data.isSelected) {
                binding.linFollower.setVisibility(View.VISIBLE);
                binding.etCountFrom.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (TextUtils.isEmpty(s)) {
                            data.minPrice = 0;
                        } else {
                            data.minPrice = Integer.parseInt(s.toString());
                        }

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                binding.etCountTo.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (TextUtils.isEmpty(s)) {
                            data.maxPrice = 0;
                        } else {
                            data.maxPrice = Integer.parseInt(s.toString());
                        }

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            } else {
                binding.etCountTo.setText("");
                binding.etCountFrom.setText("");
                binding.linFollower.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClickAge(boolean isAdded, PriceRange data, int adapterPos) {
        selectedAgeRange = data;
        /*if (data.minPrice == -1) {
            binding.linAge.setVisibility(View.VISIBLE);
        } else {
            binding.etAgeTo.setText("");
            binding.etAgeFrom.setText("");
            binding.linAge.setVisibility(View.GONE);
        }*/

        if (data.id == 7) {//custom case
            if (data.isSelected) {
                binding.linAge.setVisibility(View.VISIBLE);
                binding.etAgeFrom.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (TextUtils.isEmpty(s)) {
                            data.minPrice = 0;
                        } else {
                            data.minPrice = Integer.parseInt(s.toString());
                        }

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                binding.etAgeTo.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (TextUtils.isEmpty(s)) {
                            data.maxPrice = 0;
                        } else {
                            data.maxPrice = Integer.parseInt(s.toString());
                        }

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            } else {
                binding.etAgeTo.setText("");
                binding.etAgeFrom.setText("");
                binding.linAge.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClickSkill(boolean isAdded, SkillTags.Data data, int adapterPos) {
        addRemoveTags(isAdded, data, adapterPos);
    }
}
