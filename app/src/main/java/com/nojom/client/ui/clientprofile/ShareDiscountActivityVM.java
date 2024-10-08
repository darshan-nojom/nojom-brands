package com.nojom.client.ui.clientprofile;

import android.app.Application;
import android.content.Intent;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;

import com.nojom.client.R;
import com.nojom.client.databinding.ActivityShareDiscountBinding;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Utils;

import org.jetbrains.annotations.NotNull;

class ShareDiscountActivityVM extends AndroidViewModel implements View.OnClickListener {
    private ActivityShareDiscountBinding binding;
    private BaseActivity activity;

    ShareDiscountActivityVM(Application application, ActivityShareDiscountBinding shareDiscountBinding, BaseActivity shareDiscountActivity) {
        super(application);
        binding = shareDiscountBinding;
        activity = shareDiscountActivity;
        initData();
    }

    private void initData() {
        binding.btnShareCode.setOnClickListener(this);
        binding.toolbar.imgBack.setOnClickListener(this);

        ClickableSpan termsOfUseClick = new ClickableSpan() {
            @Override
            public void onClick(@NotNull View view) {
                activity.redirectUsingCustomTab(Constants.TERMS_USE);
            }
        };

        Utils.makeLinks(binding.tvDiscountInfo, new String[]{activity.getString(R.string.terms_of_use)}, new ClickableSpan[]{termsOfUseClick});

    }

    @Override
    public void onClick(View view) {
        String shareCode = "R3RT2KK";
        switch (view.getId()) {
            case R.id.img_back:
                activity.onBackPressed();
                break;
            case R.id.btn_share_code:
                if (!activity.isEmpty(shareCode)) {
                    String textBody = activity.getString(R.string.influencebird_app);
                    shareReferral(textBody);
                }
                break;
        }

    }

    private void shareReferral(String textBody) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, textBody);
        activity.startActivity(Intent.createChooser(sharingIntent, "Share via..."));
    }

}
