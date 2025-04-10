package com.nojom.client.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityHomeBinding;
import com.nojom.client.ui.BaseActivity;

public class LawyerHomeActivity extends BaseActivity {
    private LawyerHomeActivityVM lawyerHomeActivityVM;
    ActivityHomeBinding homeNewBinding;
    private static boolean isAtBottom = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeNewBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        lawyerHomeActivityVM = new LawyerHomeActivityVM(Task24Application.getInstance(), homeNewBinding, this);

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
        lawyerHomeActivityVM.onResumeMethod();
    }


    @Override
    protected void onPause() {
        super.onPause();
        lawyerHomeActivityVM.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        lawyerHomeActivityVM.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        onBackPressedEvent();
    }
}
