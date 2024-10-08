package com.nojom.client.ui.workprofile;

import static com.nojom.client.util.Constants.API_GET_ALL_COUNTRIES;
import static com.nojom.client.util.Constants.API_GET_CITIES;
import static com.nojom.client.util.Constants.API_GET_STATE;
import static com.nojom.client.util.Constants.API_SET_COORDINATES;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.nojom.client.R;
import com.nojom.client.adapter.SelectCityListAdapter;
import com.nojom.client.adapter.SelectCountryListAdapter;
import com.nojom.client.adapter.SelectStateListAdapter;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivityUpdateLocationBinding;
import com.nojom.client.model.CityModel;
import com.nojom.client.model.CountryModel;
import com.nojom.client.model.Profile;
import com.nojom.client.model.StateModel;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.LocationAddress;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

class UpdateLocationActivityVM extends AndroidViewModel implements View.OnClickListener, RequestResponseListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, LocationListener {
    private ActivityUpdateLocationBinding binding;
    private BaseActivity activity;
    private Profile profileData;
    private SelectCountryListAdapter selectCountryListAdapter;
    private SelectStateListAdapter selectStateListAdapter;
    private SelectCityListAdapter selectCityListAdapter;
    private List<CountryModel.Data> arrCountryList = new ArrayList<>();
    private List<StateModel.Data> arrStateList = new ArrayList<>();
    private List<CityModel.Data> arrCityList = new ArrayList<>();
    private int countryID = 0, /*stateID = 0,*/ cityID = 0;
    private boolean isLocationUpdateCompulsory;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;
    private double latitude;
    private double longitude;
    private boolean isFromGoogleLocation = false;

    UpdateLocationActivityVM(Application application, ActivityUpdateLocationBinding updateLocationBinding, BaseActivity updateLocationActivity) {
        super(application);
        binding = updateLocationBinding;
        activity = updateLocationActivity;
        initData();
    }

    public void setLocationUpdateCompulsory(boolean locationUpdateCompulsory) {
        isLocationUpdateCompulsory = locationUpdateCompulsory;
    }

    private void initData() {
        binding.toolbar.imgBack.setOnClickListener(this);
        binding.btnLocation.setOnClickListener(this);
        binding.etCountry.setOnClickListener(this);
        binding.etState.setOnClickListener(this);
        binding.etCity.setOnClickListener(this);
        binding.toolbar.tvSave.setOnClickListener(this);
        binding.toolbar.tvSave.setVisibility(View.VISIBLE);
        mGoogleApiClient = new GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        profileData = Preferences.getProfileData(activity);
        if (profileData != null) {
            binding.etCountry.setText(TextUtils.isEmpty(profileData.getCountryName(activity.getLanguage())) ? "" : profileData.getCountryName(activity.getLanguage()));
            binding.etCity.setText(TextUtils.isEmpty(profileData.getCityName(activity.getLanguage())) ? "" : profileData.getCityName(activity.getLanguage()));
//            binding.etState.setText(TextUtils.isEmpty(profileData.getStateName(activity.getLanguage())) ? "" : profileData.getStateName(activity.getLanguage()));
            if (profileData.countryID != null) {
                countryID = profileData.countryID;
            }
            /*if (profileData.stateID != null) {
                stateID = profileData.stateID;
            }*/
            if (profileData.cityID != null) {
                cityID = profileData.cityID;
            }

            if(countryID==194){//saudi arabia
                binding.relCity.setVisibility(View.VISIBLE);
            }else{
                binding.relCity.setVisibility(View.GONE);
            }
        }

        callCountryApi();
    }


    private void getUpdateLocation() {
        if (mGoogleApiClient.isConnected())
            getLocation();
        else
            mGoogleApiClient.connect();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                if (isLocationUpdateCompulsory) {
                    activity.finishAffinity();
                } else {
                    activity.finish();
                }
                break;
            case R.id.btn_location:
                getUpdateLocation();
                break;
            case R.id.tv_save:
                if (validUpdateLocationData()) {
                    updateLocation();
                }
                break;
            case R.id.et_country:
                if (isFromGoogleLocation) {
                    getUpdateLocation();
                } else {
                    if (arrCountryList.size() > 0) {
                        showCountrySelectDialog();
                    } else {
                        callCountryApi();
                    }
                }
                break;
            /*case R.id.et_state:
                if (isFromGoogleLocation) {
                    getUpdateLocation();
                } else {
                    if (activity.isEmpty(getCountry())) {
                        activity.toastMessage(activity.getString(R.string.please_select_country));
                    } else {
                        callStateApi();
                    }
                }
                break;*/
            case R.id.et_city:
                if (isFromGoogleLocation) {
                    getUpdateLocation();
                } else {
                    if (activity.isEmpty(getCountry())) {
                        activity.toastMessage(activity.getString(R.string.please_select_country));
                    }/* else if (activity.isEmpty(getState())) {
                        activity.toastMessage(activity.getString(R.string.please_select_state));
                    }*/ else {
                        callCityApi();
                    }
                }
                break;
        }
    }

    private boolean validUpdateLocationData() {
        if (isFromGoogleLocation) {
            if (latitude == 0 || longitude == 0) {
                activity.validationError(activity.getString(R.string.please_allow_your_location));
                return false;
            }
        }

        if (activity.isEmpty(getCountry())) {
            activity.validationError(activity.getString(R.string.select_country));
            return false;
        }

       /* if (activity.isEmpty(getState())) {
            activity.validationError(activity.getString(R.string.select_state));
            return false;
        }*/

        /*if (activity.isEmpty(getCity())) {
            activity.validationError(activity.getString(R.string.select_city));
            return false;
        }*/
        return true;
    }

    void updateLocation() {
        if (!activity.isNetworkConnected())
            return;

        activity.disableEnableTouch(true);
        binding.toolbar.progressBar.setVisibility(View.VISIBLE);
        binding.toolbar.tvSave.setVisibility(View.INVISIBLE);

        HashMap<String, String> map = new HashMap<>();
        map.put("latitude", latitude + "");
        map.put("longitude", longitude + "");
        map.put("country", getCountry() + "");
//        map.put("region", getState() + "");
        map.put("city", getCity() + "");


        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_SET_COORDINATES, true, map);
    }


    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        if (url.equalsIgnoreCase(API_SET_COORDINATES)) {
            binding.toolbar.progressBar.setVisibility(View.GONE);
            binding.toolbar.tvSave.setVisibility(View.VISIBLE);
            if (profileData != null) {
//                profileData.country = getCountry();
//                profileData.region = getState();
                profileData.city = getCity();
                profileData.countryName = getCountry();
                profileData.cityName = getCity();
//                profileData.stateName = getState();
                profileData.countryID = countryID;
//                profileData.stateID = stateID;
                profileData.cityID = cityID;

                Preferences.setProfileData(activity, profileData);
                SharedPreferences prefs = activity.getSharedPreferences("locationUpdate", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("cancel", true);
                editor.apply();
            }
            activity.toastMessage(message);
            activity.finish();
            activity.disableEnableTouch(false);
        } else if (url.equalsIgnoreCase(API_GET_ALL_COUNTRIES)) {
            binding.progressBarCountry.setVisibility(View.GONE);
            binding.imgCountry.setVisibility(View.VISIBLE);
            if (arrCountryList.size() == 0) {
                CountryModel countryModel = CountryModel.getCountryList(responseBody);
                arrCountryList = new ArrayList<>();
                if (countryModel != null && countryModel.data != null) {
                    arrCountryList = countryModel.data;
                }
            } else {
                showCountrySelectDialog();
            }
        }/* else if (url.equalsIgnoreCase(API_GET_STATE + "/" + countryID)) {
            binding.progressBarState.setVisibility(View.GONE);
            binding.imgState.setVisibility(View.VISIBLE);
            StateModel stateModel = StateModel.getStateList(responseBody);
            arrStateList = new ArrayList<>();
            if (stateModel != null && stateModel.data != null) {
                arrStateList = stateModel.data;
            }
            showStateSelectDialog();
        }*/ else if (url.equalsIgnoreCase(API_GET_CITIES + "/2849")) {
            binding.progressBarCity.setVisibility(View.GONE);
            binding.imgCity.setVisibility(View.VISIBLE);
            CityModel cityModel = CityModel.getCityList(responseBody);
            arrCityList = new ArrayList<>();
            if (cityModel != null && cityModel.data != null) {
                arrCityList = cityModel.data;
            }
            showCitySelectDialog();
        }
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        activity.disableEnableTouch(false);
        binding.progressBarCountry.setVisibility(View.GONE);
        binding.progressBarState.setVisibility(View.GONE);
        binding.progressBarCity.setVisibility(View.GONE);
        binding.imgCountry.setVisibility(View.VISIBLE);
        binding.imgState.setVisibility(View.VISIBLE);
        binding.imgCity.setVisibility(View.VISIBLE);
    }

    void callCountryApi() {
        if (!activity.isNetworkConnected())
            return;

        binding.imgCountry.setVisibility(View.GONE);
        binding.progressBarCountry.setVisibility(View.VISIBLE);
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_ALL_COUNTRIES, false, null);
    }

    void callStateApi() {
        if (!activity.isNetworkConnected())
            return;

        binding.progressBarState.setVisibility(View.VISIBLE);
        binding.imgState.setVisibility(View.GONE);
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_STATE + "/" + countryID, false, null);
    }

    void callCityApi() {
        if (!activity.isNetworkConnected())
            return;

        binding.imgCity.setVisibility(View.GONE);
        binding.progressBarCity.setVisibility(View.VISIBLE);
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_CITIES + "/2849", false, null);
    }


    @SuppressLint("StringFormatInvalid")
    private void showCountrySelectDialog() {
        @SuppressLint("PrivateResource") final Dialog dialog = new Dialog(activity, R.style.Theme_Design_Light_BottomSheetDialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_item_select_black);
        dialog.setCancelable(true);

        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        TextView tvApply = dialog.findViewById(R.id.tv_apply);
        final EditText etSearch = dialog.findViewById(R.id.et_search);
        RecyclerView rvTypes = dialog.findViewById(R.id.rv_items);

        etSearch.setHint(String.format(activity.getString(R.string.search_for), activity.getString(R.string.country).toLowerCase()));

        rvTypes.setLayoutManager(new LinearLayoutManager(activity));
        if (arrCountryList != null && arrCountryList.size() > 0) {
            for (CountryModel.Data data : arrCountryList) {
                data.isSelected = data.getCountryName(activity.getLanguage()).equalsIgnoreCase(binding.etCountry.getText().toString());
            }
            selectCountryListAdapter = new SelectCountryListAdapter(activity, arrCountryList);
            rvTypes.setAdapter(selectCountryListAdapter);
        }

        tvCancel.setOnClickListener(v -> dialog.dismiss());

        tvApply.setOnClickListener(v -> {
            if (selectCountryListAdapter != null && selectCountryListAdapter.getSelectedItem() != null) {
                countryID = selectCountryListAdapter.getSelectedItem().id;
                binding.etCountry.setText(selectCountryListAdapter.getSelectedItem().getCountryName(activity.getLanguage()));
                binding.etState.setText("");
                binding.etCity.setText("");
//                stateID = 0;
                cityID = 0;

                if(countryID==194){//saudi arabia
                    binding.relCity.setVisibility(View.VISIBLE);
                }else{
                    binding.relCity.setVisibility(View.GONE);
                }

                dialog.dismiss();
            } else {
                activity.toastMessage(activity.getString(R.string.please_select_one_country));
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
        etSearch.setOnFocusChangeListener((v, hasFocus) -> etSearch.post(() -> Utils.openSoftKeyboard(activity, etSearch)));
        etSearch.requestFocus();
    }

    @SuppressLint("StringFormatInvalid")
    private void showStateSelectDialog() {
        final Dialog dialog = new Dialog(activity, R.style.Theme_Design_Light_BottomSheetDialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_item_select_black);
        dialog.setCancelable(true);

        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        TextView tvApply = dialog.findViewById(R.id.tv_apply);
        final EditText etSearch = dialog.findViewById(R.id.et_search);
        RecyclerView rvTypes = dialog.findViewById(R.id.rv_items);

        etSearch.setHint(String.format(activity.getString(R.string.search_for), activity.getString(R.string.state).toLowerCase()));

        rvTypes.setLayoutManager(new LinearLayoutManager(activity));
        if (arrStateList != null && arrStateList.size() > 0) {
            for (StateModel.Data data : arrStateList) {
                data.isSelected = data.getStateName(activity.getLanguage()).equalsIgnoreCase(binding.etState.getText().toString());
            }
            selectStateListAdapter = new SelectStateListAdapter(activity, arrStateList);
            rvTypes.setAdapter(selectStateListAdapter);
        }

        tvCancel.setOnClickListener(v -> dialog.dismiss());

        tvApply.setOnClickListener(v -> {
            if (selectStateListAdapter != null && selectStateListAdapter.getSelectedItem() != null) {
//                stateID = selectStateListAdapter.getSelectedItem().id;
                binding.etState.setText(selectStateListAdapter.getSelectedItem().getStateName(activity.getLanguage()));
                binding.etCity.setText("");
                cityID = 0;
                dialog.dismiss();
            } else {
                activity.toastMessage(activity.getString(R.string.please_select_one_state));
            }
            dialog.dismiss();
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (selectStateListAdapter != null)
                    selectStateListAdapter.getFilter().filter(s.toString());
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
        etSearch.setOnFocusChangeListener((v, hasFocus) -> etSearch.post(() -> Utils.openSoftKeyboard(activity, etSearch)));
        etSearch.requestFocus();
    }

    @SuppressLint("StringFormatInvalid")
    private void showCitySelectDialog() {
        @SuppressLint("PrivateResource") final Dialog dialog = new Dialog(activity, R.style.Theme_Design_Light_BottomSheetDialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_item_select_black);
        dialog.setCancelable(true);

        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        TextView tvApply = dialog.findViewById(R.id.tv_apply);
        final EditText etSearch = dialog.findViewById(R.id.et_search);
        RecyclerView rvTypes = dialog.findViewById(R.id.rv_items);

        etSearch.setHint(String.format(activity.getString(R.string.search_for), activity.getString(R.string.city).toLowerCase()));

        rvTypes.setLayoutManager(new LinearLayoutManager(activity));
        if (arrCityList != null && arrCityList.size() > 0) {
            for (CityModel.Data data : arrCityList) {
                data.isSelected = data.getCityName(activity.getLanguage()).equalsIgnoreCase(binding.etCity.getText().toString());
            }
            selectCityListAdapter = new SelectCityListAdapter(activity, arrCityList);
            rvTypes.setAdapter(selectCityListAdapter);
        }

        tvCancel.setOnClickListener(v -> dialog.dismiss());

        tvApply.setOnClickListener(v -> {
            if (selectCityListAdapter != null && selectCityListAdapter.getSelectedItem() != null) {
                cityID = selectCityListAdapter.getSelectedItem().id;
                binding.etCity.setText(selectCityListAdapter.getSelectedItem().getCityName(activity.getLanguage()));
                dialog.dismiss();
            } else {
                activity.toastMessage(activity.getString(R.string.please_select_one_city));
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
        etSearch.setOnFocusChangeListener((v, hasFocus) -> etSearch.post(() -> Utils.openSoftKeyboard(activity, etSearch)));
        etSearch.requestFocus();
    }

    public String getCountry() {
        return binding.etCountry.getText().toString().trim();
    }

    /*public String getState() {
        return binding.etState.getText().toString().trim();
    }*/

    public String getCity() {
        return binding.etCity.getText().toString().trim();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        activity.toastMessage(activity.getString(R.string.connection_failed));
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(activity, 90000);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("Current Location", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        settingRequest();
    }

    @Override
    public void onConnectionSuspended(int i) {
        activity.toastMessage(activity.getString(R.string.connection_suspended));
    }

    void onStopMethod() {
        if (mGoogleApiClient != null)
            mGoogleApiClient.disconnect();

    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocationPermission();
        } else {
            showLocationDisclosure();
        }
    }

    private void getLocationPermission() {
        try {
            Dexter.withActivity(activity)
                    .withPermissions(
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            if (report.areAllPermissionsGranted()) {
                                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                                        ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                    return;
                                }
                                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

                                latitude = mLastLocation.getLatitude();
                                longitude = mLastLocation.getLongitude();
                                LocationAddress.getAddressFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(),
                                        activity, new GeocoderHandler());
                            }

                            if (report.isAnyPermissionPermanentlyDenied()) {
                                activity.toastMessage(activity.getString(R.string.please_give_permission));
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    })
                    .onSameThread()
                    .check();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showLocationDisclosure() {
        final Dialog dialog = new Dialog(activity, R.style.Theme_AppCompat_Dialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_location_disclosure);
        dialog.setCancelable(false);

        TextView tvOk = dialog.findViewById(R.id.tv_ok);
        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);

        tvOk.setOnClickListener(v -> {
            dialog.dismiss();
            getLocationPermission();
            isFromGoogleLocation = true;
        });

        tvCancel.setOnClickListener(v -> {
            binding.etCountry.setEnabled(true);
            binding.etState.setEnabled(true);
            binding.etCity.setEnabled(true);
            dialog.dismiss();
            isFromGoogleLocation = false;
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.gravity = Gravity.CENTER;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);

    }

    private void settingRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);    // 10 seconds, in milliseconds
        mLocationRequest.setFastestInterval(1000);   // 1 second, in milliseconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient,
                        builder.build());

        result.setResultCallback(result1 -> {
            final Status status = result1.getStatus();
            switch (status.getStatusCode()) {
                case LocationSettingsStatusCodes.SUCCESS:
                    getLocation();
                    break;
                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                    try {
                        status.startResolutionForResult(activity, 1000);
                    } catch (IntentSender.SendIntentException ignored) {
                    }
                    break;
                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                    break;
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @SuppressLint("HandlerLeak")
    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            Address locationAddress;
            if (message.what == 1) {
                try {
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getParcelable("address");
                    if (locationAddress != null) {
                        binding.etCountry.setText(locationAddress.getCountryName() != null ? locationAddress.getCountryName() : "");
                        binding.etState.setText(locationAddress.getAdminArea() != null ? locationAddress.getAdminArea() : "");
                        binding.etCity.setText(locationAddress.getLocality() != null ? locationAddress.getLocality() : "");
                        if (profileData != null) {
                            profileData.countryName = getCountry();
//                            profileData.region = getState();
                            profileData.city = getCity();
                            Preferences.setProfileData(activity, profileData);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
