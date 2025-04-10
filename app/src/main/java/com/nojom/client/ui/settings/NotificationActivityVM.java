package com.nojom.client.ui.settings;

import android.annotation.SuppressLint;
import android.app.Application;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nojom.client.R;
import com.nojom.client.adapter.RecyclerviewAdapter;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivityNotificationBinding;
import com.nojom.client.model.Notification;
import com.nojom.client.segment.SegmentedButtonGroup;
import com.nojom.client.ui.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;

import static com.nojom.client.util.Constants.API_GET_NOTIFICATION_SETTINGS;
import static com.nojom.client.util.Constants.API_UPDATE_NOTIFICATION_SETTINGS;

class NotificationActivityVM extends AndroidViewModel implements RequestResponseListener {
    private ActivityNotificationBinding binding;
    @SuppressLint("StaticFieldLeak")
    private BaseActivity activity;
    private ArrayList<Notification.Data> notificationList;

    NotificationActivityVM(Application application, ActivityNotificationBinding notificationBinding, BaseActivity notificationActivity) {
        super(application);
        binding = notificationBinding;
        activity = notificationActivity;
        initData();
    }

    private void initData() {
        binding.toolbar.imgBack.setOnClickListener(v -> activity.onBackPressed());
        binding.toolbar.tvTitle.setText(activity.getString(R.string.notifications));

        binding.rvNotifications.setLayoutManager(new LinearLayoutManager(activity));

        getNotificationList();
    }

    private void getNotificationList() {
        if (!activity.isNetworkConnected())
            return;

        activity.isClickableView = true;
        binding.shimmerLayout.startShimmer();
        binding.rvNotifications.setVisibility(View.GONE);

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_NOTIFICATION_SETTINGS, false, null);
    }

    private void bindView(View view, int position) {
        Notification.Data item = notificationList.get(position);

        SegmentedButtonGroup segmentGroup = view.findViewById(R.id.segmentGroup);
        TextView tvTitle = view.findViewById(R.id.tv_title);

        tvTitle.setText(item.getName(activity.getLanguage()));
        segmentGroup.setPosition(item.status.equals("1") ? 1 : 0,true);

        segmentGroup.setOnPositionChangedListener(status -> addNotification(item.id, status));
    }

    private void addNotification(int notificationId, int status) {
        if (!activity.isNetworkConnected())
            return;

        HashMap<String, String> map = new HashMap<>();
        map.put("status", status + "");
        map.put("notification_id", notificationId + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_UPDATE_NOTIFICATION_SETTINGS, true, map);
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        if (url.equalsIgnoreCase(API_GET_NOTIFICATION_SETTINGS)) {
            Notification model = Notification.getNotifications(responseBody);
            notificationList = new ArrayList<>();
            if (model != null && model.data != null) {
                notificationList = (ArrayList<Notification.Data>) model.data;
            }
            RecyclerviewAdapter adapter = new RecyclerviewAdapter(notificationList,
                    R.layout.item_notification, NotificationActivityVM.this::bindView);
            binding.rvNotifications.setAdapter(adapter);
        } else {
           // activity.toastMessage(message);
        }
        activity.isClickableView = false;
        binding.shimmerLayout.stopShimmer();
        binding.shimmerLayout.setVisibility(View.GONE);
        binding.rvNotifications.setVisibility(View.VISIBLE);
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        activity.isClickableView = false;
        binding.shimmerLayout.stopShimmer();
        binding.rvNotifications.setVisibility(View.VISIBLE);
    }
}
