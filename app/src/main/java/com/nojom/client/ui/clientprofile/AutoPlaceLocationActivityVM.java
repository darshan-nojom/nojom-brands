package com.nojom.client.ui.clientprofile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.nojom.client.R;
import com.nojom.client.adapter.AutoCompletePlaceAdapter;
import com.nojom.client.databinding.ActivityAutoPalceLocationBinding;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.LocationAddress;
import com.nojom.client.util.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static com.facebook.FacebookSdk.getApplicationContext;

public class AutoPlaceLocationActivityVM extends AndroidViewModel implements OnMapReadyCallback, View.OnClickListener, Constants {
    private ActivityAutoPalceLocationBinding binding;
    private BaseActivity activity;
    private PlacesClient placesClient;
    private GoogleMap map;
    private AutoCompletePlaceAdapter autoCompletePlaceAdapter;
    private String strAddress = "";
    private Marker marker;
    private double latitude, longitude;

    AutoPlaceLocationActivityVM(Application application, ActivityAutoPalceLocationBinding activityAutoPalceLocationBinding, BaseActivity autoPlaceLocationActivity) {
        super(application);
        binding = activityAutoPalceLocationBinding;
        activity = autoPlaceLocationActivity;
        initData();
    }

    private void initData() {
        latitude = activity.getIntent().getDoubleExtra("latitude", 0.0);
        longitude = activity.getIntent().getDoubleExtra("longitude", 0.0);

        SupportMapFragment mapFragment = (SupportMapFragment) activity.getSupportFragmentManager().findFragmentById(R.id.map_view);
        mapFragment.getMapAsync(this);

        binding.imgBack.setOnClickListener(this);
        binding.imgClose.setOnClickListener(this);
        binding.tvSave.setOnClickListener(this);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), activity.getString(R.string.map_api_key));
        }

        placesClient = Places.createClient(activity);

        autoCompletePlaceAdapter = new AutoCompletePlaceAdapter(activity, placesClient);
        binding.edPlace.setAdapter(autoCompletePlaceAdapter);
        binding.edPlace.setThreshold(3);

        binding.edPlace.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    binding.imgClose.setVisibility(View.VISIBLE);
                } else {
                    binding.imgClose.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        binding.edPlace.setOnItemClickListener((parent, view, position, id) -> {
            Utils.hideSoftKeyboard(activity);
            try {
                final AutocompletePrediction item = autoCompletePlaceAdapter.getItem(position);
                String placeID = null;
                if (item != null) {
                    placeID = item.getPlaceId();
                }

                List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);

                FetchPlaceRequest request = null;
                if (placeID != null) {
                    request = FetchPlaceRequest.builder(placeID, placeFields)
                            .build();
                }

                if (request != null) {
                    placesClient.fetchPlace(request).addOnSuccessListener(fetchPlaceResponse -> {
                        try {
                            if (marker != null) {
                                marker.remove();   //if the marker is already added then remove it
                            }
                            String currentString = fetchPlaceResponse.getPlace().getLatLng().toString().replace("lat/lng: (", "").replace(")", "");
                            String[] splitLatLng = currentString.split(",");
                            latitude = Double.parseDouble(splitLatLng[0]);
                            longitude = Double.parseDouble(splitLatLng[1]);
                            binding.tvSave.setVisibility(View.VISIBLE);

                            LocationAddress.getAddressFromLocation(latitude, longitude,
                                    activity, new GeocoderHandler());
                            LatLng latLng = new LatLng(latitude, longitude);
                            map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                            map.animateCamera(CameraUpdateFactory.zoomTo(5));
                            marker = map.addMarker(new MarkerOptions().position(latLng));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }).addOnFailureListener(e -> e.printStackTrace());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        if (marker != null) {
            marker.remove();   //if the marker is already added then remove it
        }

        LatLng latLng = new LatLng(latitude, longitude);
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map.animateCamera(CameraUpdateFactory.zoomTo(5));
        marker = map.addMarker(new MarkerOptions().position(latLng));


        map.setOnMapClickListener(latLng1 -> {
            if (marker != null) {
                marker.remove();   //if the marker is already added then remove it
            }
            marker = map.addMarker(new MarkerOptions().position(latLng1));
            latitude = latLng1.latitude;
            longitude = latLng1.longitude;
            binding.tvSave.setVisibility(View.VISIBLE);
            LocationAddress.getAddressFromLocation(latLng1.latitude, latLng1.longitude,
                    activity, new GeocoderHandler());
        });
    }

    @Override
    public void onClick(View view) {
        Utils.hideSoftKeyboard(activity);
        switch (view.getId()) {
            case R.id.img_back:
                activity.finish();
                break;
            case R.id.img_close:
                binding.edPlace.setText("");
                binding.tvSave.setVisibility(View.GONE);
                break;
            case R.id.tv_save:
                Intent intent = new Intent();
                intent.putExtra(Constants.PICK_LOCATION_ADDRESS, strAddress);
                intent.putExtra(Constants.PICK_LOCATION_LATITUDE, latitude);
                intent.putExtra(Constants.PICK_LOCATION_LONGITUDE, longitude);
                activity.setResult(Activity.RESULT_OK, intent);
                activity.finish();
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            Address locationAddress;
            if (message.what == 1) {
                Bundle bundle = message.getData();
                locationAddress = bundle.getParcelable("address");
                if (locationAddress != null) {
                    strAddress = String.format(Locale.US,"%s", locationAddress.getAddressLine(0).replace("Unnamed Road,", ""));
                }
            }
        }
    }
}
