package com.nojom.client.ui;


import static com.nojom.client.util.Constants.API_GET_ALL_CITY;
import static com.nojom.client.util.Constants.API_GET_ALL_COUNTRIES;
import static com.nojom.client.util.Constants.API_GET_LANGUAGE;
import static com.nojom.client.util.Constants.API_GET_SKILL;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivityInfluencerFilterBinding;
import com.nojom.client.model.CityModel;
import com.nojom.client.model.CountryModel;
import com.nojom.client.model.Language;
import com.nojom.client.model.ServicesModel;
import com.nojom.client.model.SkillTags;
import com.nojom.client.model.SocialPlatformModel;
import com.nojom.client.util.Preferences;

import java.util.ArrayList;
import java.util.List;

public class InfluencerFilterActivityVM extends AndroidViewModel implements RequestResponseListener {
    private final BaseActivity activity;
    private final MutableLiveData<List<Language.Data>> mutableLanguageList = new MutableLiveData<>();
    private final MutableLiveData<List<SkillTags.Data>> mutableSkillTagsList = new MutableLiveData<>();
    private final MutableLiveData<List<CountryModel.Data>> mutableLocationList = new MutableLiveData<>();
    private final MutableLiveData<List<CityModel.Data>> mutableLocationCityList = new MutableLiveData<>();
    private final MutableLiveData<List<ServicesModel.Data>> mutableServiceList = new MutableLiveData<>();
    private final MutableLiveData<List<SocialPlatformModel.Data>> mutablePlatformList = new MutableLiveData<>();

    public MutableLiveData<List<SkillTags.Data>> getMutableSkillTagsList() {
        return mutableSkillTagsList;
    }

    public InfluencerFilterActivityVM(@NonNull Application application, ActivityInfluencerFilterBinding binding, BaseActivity activity) {
        super(application);
        this.activity = activity;
    }

    public MutableLiveData<List<SocialPlatformModel.Data>> getMutablePlatformList() {
        return mutablePlatformList;
    }

    public MutableLiveData<List<ServicesModel.Data>> getMutableServiceList() {
        return mutableServiceList;
    }

    public MutableLiveData<List<Language.Data>> getMutableLanguageList() {
        return mutableLanguageList;
    }

    public MutableLiveData<List<CountryModel.Data>> getMutableLocationList() {
        return mutableLocationList;
    }

    public MutableLiveData<List<CityModel.Data>> getMutableLocationCityList() {
        return mutableLocationCityList;
    }

    public void fetchData() {
        //get language
        List<Language.Data> languageList = Preferences.getLanguages(activity);
        if (languageList == null || languageList.size() == 0) {
            getLanguageList();
        } else {
            getMutableLanguageList().postValue(languageList);
        }

        List<CountryModel.Data> locationList = Preferences.getLocation(activity);// location list
        if (locationList == null || locationList.size() == 0) {
            getLocation();
        } else {
            getMutableLocationList().postValue(locationList);
        }
        getAllCity(194);//saudi
        getAllSkill();

        //get sub service list
        List<ServicesModel.Data> servicesSubList = new ArrayList<>(Preferences.getCategoryV2(activity));//sub category list
        /*List<ServicesModel.Data> subCatList = new ArrayList<>();
        //get all sub category from parent skills
        for (ServicesModel.Data serviceData : servicesSubList) {
            if (serviceData.id == 4352 && serviceData.services != null) {
                subCatList.addAll(serviceData.services);
                break;
            }
        }*/
        getMutableServiceList().postValue(servicesSubList);
        //get platform
        List<SocialPlatformModel.Data> platformList = new ArrayList<>(Preferences.getSocialPlatforms(activity));//social platforms
        if (platformList.size() > 0) {
            getMutablePlatformList().postValue(platformList);
        }
    }

    private void getLanguageList() {
        if (!activity.isNetworkConnected())
            return;


        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_LANGUAGE, false, null);
    }

    private void getLocation() {
        if (!activity.isNetworkConnected())
            return;


        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_ALL_COUNTRIES, false, null);
    }

    public void getAllCity(int countryId) {
        if (!activity.isNetworkConnected())
            return;


        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_ALL_CITY + countryId, false, null);
    }

    public void getAllSkill() {
        if (!activity.isNetworkConnected())
            return;


        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_SKILL);
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        if (url.equalsIgnoreCase(API_GET_LANGUAGE)) {
            Language language = Language.getLanguages(responseBody);
            if (language != null && language.data != null) {
                Preferences.saveLanguages(activity, language.data);
                getMutableLanguageList().postValue(language.data);
            }
        } else if (url.equalsIgnoreCase(API_GET_ALL_COUNTRIES)) {
            CountryModel countryModel = CountryModel.getCountryList(responseBody);
            if (countryModel != null && countryModel.data != null) {
                getMutableLocationList().postValue(countryModel.data);
                Preferences.saveLocation(activity, countryModel.data);
            }
        } else if (url.equalsIgnoreCase(API_GET_SKILL)) {
            List<SkillTags.Data> language = SkillTags.getSkillTags(responseBody);
            if (language != null && language.size() > 0) {
                getMutableSkillTagsList().postValue(language);
            }
        } else {
            CityModel countryModel = CityModel.getCityList(responseBody);
            if (countryModel != null && countryModel.data != null) {
                getMutableLocationCityList().postValue(countryModel.data);
            }
        }
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        if (url.equalsIgnoreCase(API_GET_ALL_CITY)) {

        }
    }
}
