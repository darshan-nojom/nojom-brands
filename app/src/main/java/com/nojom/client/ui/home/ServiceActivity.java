package com.nojom.client.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityHomeBinding;
import com.nojom.client.databinding.ActivityServiceBinding;
import com.nojom.client.databinding.ActivityServicesBinding;
import com.nojom.client.ui.BaseActivity;

public class ServiceActivity extends BaseActivity {
    private ServiceActivityVM serviceActivityVM;
    ActivityServiceBinding servicesBinding;
    private static boolean isAtBottom = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        servicesBinding = DataBindingUtil.setContentView(this, R.layout.activity_service);
        serviceActivityVM = new ServiceActivityVM(Task24Application.getInstance(), servicesBinding, this);

//        scrollHandle();

        /*homeNewBinding.nestedScroll.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {

            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                Log.i("TAG", "BOTTOM SCROLL");
                isAtBottom = true;
            }
        });*/
    }

    public static void notifClickHandle() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        serviceActivityVM.onResumeMethod();
    }


    @Override
    protected void onPause() {
        super.onPause();
        serviceActivityVM.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        serviceActivityVM.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        onBackPressedEvent();
    }
}
