package com.nojom.client.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nojom.client.model.AllSocialGigs;
import com.nojom.client.model.Banks;
import com.nojom.client.model.ClientRate;
import com.nojom.client.model.CountryModel;
import com.nojom.client.model.ExpertLawyers;
import com.nojom.client.model.Language;
import com.nojom.client.model.PaymentMethods;
import com.nojom.client.model.Profile;
import com.nojom.client.model.ServicesModel;
import com.nojom.client.model.ServicesSellersModel;
import com.nojom.client.model.SocialPlatformModel;
import com.nojom.client.model.WalletData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Preferences {

    private static final String PREF_NAME = "InfluencerBird";

    private static final int MODE = Context.MODE_PRIVATE;

    public static void writeBoolean(Context context, String key, boolean value) {
        getEditor(context).putBoolean(key, value).commit();
    }

    public static boolean readBoolean(Context context, String key, boolean defValue) {
        return getPreferences(context).getBoolean(key, defValue);
    }

    public static void writeInteger(Context context, String key, int value) {
        getEditor(context).putInt(key, value).commit();

    }

    public static int readInteger(Context context, String key, int defValue) {
        return getPreferences(context).getInt(key, defValue);
    }

    public static void writeString(Context context, String key, String value) {
        getEditor(context).putString(key, value).commit();

    }

    public static String readString(Context context, String key, String defValue) {
        return getPreferences(context).getString(key, defValue);
    }

    public static void writeFloat(Context context, String key, float value) {
        getEditor(context).putFloat(key, value).commit();
    }

    public static float readFloat(Context context, String key, float defValue) {
        return getPreferences(context).getFloat(key, defValue);
    }

    public static void writeLong(Context context, String key, long value) {
        getEditor(context).putLong(key, value).commit();
    }

    public static long readLong(Context context, String key, long defValue) {
        return getPreferences(context).getLong(key, defValue);
    }

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, MODE);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }

    public static void clearPreferences(Context context) {
        getEditor(context).clear().apply();
    }

    public static void saveServices(Context context, List<ServicesModel.Data> services) {
        SharedPreferences mPrefs = context.getSharedPreferences(Constants.PREF_SERVICES, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("myJson", new Gson().toJson(services));
        prefsEditor.apply();
    }

    public static void locationUpdate(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("locationUpdate", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    public static List<ServicesModel.Data> getServices(Context context) {
        List<ServicesModel.Data> services;
        SharedPreferences mPrefs = context.getSharedPreferences(Constants.PREF_SERVICES, Context.MODE_PRIVATE);
        String json = mPrefs.getString("myJson", "");
        if (json.isEmpty()) {
            services = new ArrayList<>();
        } else {
            Type type = new TypeToken<List<ServicesModel.Data>>() {
            }.getType();
            services = new Gson().fromJson(json, type);
        }
        return services;
    }

    public static void saveSocialPlatform(Context context, List<SocialPlatformModel.Data> services) {
        SharedPreferences mPrefs = context.getSharedPreferences(Constants.PREF_SOCIAL_PLATFORMS, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("platforms", new Gson().toJson(services));
        prefsEditor.apply();
    }

    public static List<SocialPlatformModel.Data> getSocialPlatforms(Context context) {
        List<SocialPlatformModel.Data> services;
        SharedPreferences mPrefs = context.getSharedPreferences(Constants.PREF_SOCIAL_PLATFORMS, Context.MODE_PRIVATE);
        String json = mPrefs.getString("platforms", "");
        if (json.isEmpty()) {
            services = new ArrayList<>();
        } else {
            Type type = new TypeToken<List<SocialPlatformModel.Data>>() {
            }.getType();
            services = new Gson().fromJson(json, type);
        }
        return services;
    }


    public static void saveLanguages(Context context, List<Language.Data> services) {
        SharedPreferences mPrefs = context.getSharedPreferences(Constants.PREF_LANGUAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("myLanguage", new Gson().toJson(services));
        prefsEditor.apply();
    }

    public static List<Language.Data> getLanguages(Context context) {
        List<Language.Data> services;
        SharedPreferences mPrefs = context.getSharedPreferences(Constants.PREF_LANGUAGE, Context.MODE_PRIVATE);
        String json = mPrefs.getString("myLanguage", "");
        if (json.isEmpty()) {
            services = new ArrayList<>();
        } else {
            Type type = new TypeToken<List<Language.Data>>() {
            }.getType();
            services = new Gson().fromJson(json, type);
        }
        return services;
    }


    public static void saveTopServices(Context context, List<ServicesModel.Data> services) {
        SharedPreferences mPrefs = context.getSharedPreferences(Constants.PREF_TOP_SERVICES, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("myJson", new Gson().toJson(services));
        prefsEditor.apply();
    }

    public static void saveCategoryV2(Context context, List<ServicesModel.Data> services) {
        SharedPreferences mPrefs = context.getSharedPreferences(Constants.PREF_TOP_CAT, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("catV2", new Gson().toJson(services));
        prefsEditor.apply();
    }

    public static void saveRates(Context context, List<WalletData> services) {
        SharedPreferences mPrefs = context.getSharedPreferences(Constants.PREF_TOP_CAT, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("rates", new Gson().toJson(services));
        prefsEditor.apply();
    }

    public static List<ServicesModel.Data> getCategoryV2(Context context) {
        List<ServicesModel.Data> services;
        SharedPreferences mPrefs = context.getSharedPreferences(Constants.PREF_TOP_CAT, Context.MODE_PRIVATE);
        String json = mPrefs.getString("catV2", "");
        if (json.isEmpty()) {
            services = new ArrayList<>();
        } else {
            Type type = new TypeToken<List<ServicesModel.Data>>() {
            }.getType();
            services = new Gson().fromJson(json, type);
        }
        return services;
    }

    public static List<WalletData> getRates(Context context) {
        List<WalletData> services;
        SharedPreferences mPrefs = context.getSharedPreferences(Constants.PREF_TOP_CAT, Context.MODE_PRIVATE);
        String json = mPrefs.getString("rates", "");
        if (json.isEmpty()) {
            services = new ArrayList<>();
        } else {
            Type type = new TypeToken<List<WalletData>>() {
            }.getType();
            services = new Gson().fromJson(json, type);
        }
        return services;
    }

    public static void saveBanks(Context context, List<Banks.Data> services) {
        SharedPreferences mPrefs = context.getSharedPreferences(Constants.PREF_TOP_SERVICES, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("banks", new Gson().toJson(services));
        prefsEditor.apply();
    }

    public static List<Banks.Data> getBanks(Context context) {
        List<Banks.Data> services;
        SharedPreferences mPrefs = context.getSharedPreferences(Constants.PREF_TOP_SERVICES, Context.MODE_PRIVATE);
        String json = mPrefs.getString("banks", "");
        if (json.isEmpty()) {
            services = new ArrayList<>();
        } else {
            Type type = new TypeToken<List<Banks.Data>>() {
            }.getType();
            services = new Gson().fromJson(json, type);
        }
        return services;
    }

    public static void saveIsTopServices(Context context, List<ServicesSellersModel.Data> services) {
        SharedPreferences mPrefs = context.getSharedPreferences(Constants.PREF_TOP_SERVICES, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("myJsonIs", new Gson().toJson(services));
        prefsEditor.apply();
    }

    public static List<ServicesModel.Data> getTopServices(Context context) {
        List<ServicesModel.Data> services;
        SharedPreferences mPrefs = context.getSharedPreferences(Constants.PREF_TOP_SERVICES, Context.MODE_PRIVATE);
        String json = mPrefs.getString("myJson", "");
        if (json.isEmpty()) {
            services = new ArrayList<>();
        } else {
            Type type = new TypeToken<List<ServicesModel.Data>>() {
            }.getType();
            services = new Gson().fromJson(json, type);
        }
        return services;
    }


    public static void setExpertUsers(Context context, List<ExpertLawyers.Data> expertUsers) {
        SharedPreferences mPrefs = context.getSharedPreferences(Constants.PREF_EXPERTS, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("Experts", new Gson().toJson(expertUsers));
        prefsEditor.apply();
    }

    public static List<ExpertLawyers.Data> getExpertUsers(Context context) {
        List<ExpertLawyers.Data> expertUsers;
        SharedPreferences mPrefs = context.getSharedPreferences(Constants.PREF_EXPERTS, Context.MODE_PRIVATE);
        String json = mPrefs.getString("Experts", "");
        if (json.isEmpty()) {
            expertUsers = new ArrayList<>();
        } else {
            Type type = new TypeToken<List<ExpertLawyers.Data>>() {
            }.getType();
            expertUsers = new Gson().fromJson(json, type);
        }
        return expertUsers;
    }

    public static void setProfileData(Context context, Profile userData) {
        SharedPreferences mPrefs = context.getSharedPreferences(Constants.PROFILE_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("profileData", new Gson().toJson(userData));
        prefsEditor.apply();
    }

    public static Profile getProfileData(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences(Constants.PROFILE_DATA, Context.MODE_PRIVATE);
        String json = mPrefs.getString("profileData", "");
        return new Gson().fromJson(json, Profile.class);
    }


    public static void setAllSocialGigs(Context context, AllSocialGigs expertLawyers) {
        SharedPreferences mPrefs = context.getSharedPreferences(Constants.SOCIAL_GIG_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.clear();
        prefsEditor.putString("socialGigData", new Gson().toJson(expertLawyers));
        prefsEditor.apply();
    }

    public static AllSocialGigs getSocialGigData(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences(Constants.SOCIAL_GIG_DATA, Context.MODE_PRIVATE);
        String json = mPrefs.getString("socialGigData", "");
        return new Gson().fromJson(json, AllSocialGigs.class);
    }


    public static void setClientRate(Context context, ClientRate clientRate) {
        SharedPreferences mPrefs = context.getSharedPreferences(Constants.PROFILE_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("clientRate", new Gson().toJson(clientRate));
        prefsEditor.apply();
    }

    public static ClientRate getClientRate(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences(Constants.PROFILE_DATA, Context.MODE_PRIVATE);
        String json = mPrefs.getString("clientRate", "");
        return new Gson().fromJson(json, ClientRate.class);
    }

    public static void setPaymentMethod(Context context, PaymentMethods paymentMethod) {
        SharedPreferences mPrefs = context.getSharedPreferences(Constants.PROFILE_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("paymentMethod", new Gson().toJson(paymentMethod));
        prefsEditor.apply();
    }

    public static PaymentMethods getPaymentMethod(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences(Constants.PROFILE_DATA, Context.MODE_PRIVATE);
        String json = mPrefs.getString("paymentMethod", "");
        return new Gson().fromJson(json, PaymentMethods.class);
    }

    public static List<CountryModel.Data> getLocation(Context context) {
        List<CountryModel.Data> services;
        SharedPreferences mPrefs = context.getSharedPreferences(Constants.PREF_LANGUAGE, Context.MODE_PRIVATE);
        String json = mPrefs.getString("myLocation", "");
        if (json.isEmpty()) {
            services = new ArrayList<>();
        } else {
            Type type = new TypeToken<List<CountryModel.Data>>() {
            }.getType();
            services = new Gson().fromJson(json, type);
        }
        return services;
    }

    public static void saveLocation(Context context, List<CountryModel.Data> services) {
        SharedPreferences mPrefs = context.getSharedPreferences(Constants.PREF_LANGUAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("myLocation", new Gson().toJson(services));
        prefsEditor.apply();
    }

}
