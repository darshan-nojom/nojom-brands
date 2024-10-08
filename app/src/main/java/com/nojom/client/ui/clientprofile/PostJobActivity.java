package com.nojom.client.ui.clientprofile;

import android.os.Bundle;
import android.textview.CustomTextView;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityPostJobBinding;
import com.nojom.client.model.ServicesModel;
import com.nojom.client.model.SocialPlatformModel;
import com.nojom.client.ui.BaseActivity;

import java.util.List;

public class PostJobActivity extends BaseActivity {
    private PostJobActivityVM postJobActivityVM;
    public List<SocialPlatformModel.Data> platformList;
    public List<ServicesModel.Data> subServiceList;
    private ActivityPostJobBinding postJobBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postJobBinding = DataBindingUtil.setContentView(this, R.layout.activity_post_job);
        postJobActivityVM = new PostJobActivityVM(Task24Application.getInstance(), postJobBinding, this);
    }

    public void setProgressView(float progress) {
        postJobActivityVM.setProgressView(progress);
    }

    public CustomTextView getNextView() {
        return postJobBinding.toolBack.tvNext;
    }

    public ImageView getBackView() {
        return postJobBinding.toolBack.imgBack;
    }

    public void showNextView() {
        postJobBinding.toolBack.tvNext.post(() -> postJobBinding.toolBack.tvNext.setVisibility(View.VISIBLE));
    }

    public void hideNextView() {
        postJobBinding.toolBack.tvNext.setVisibility(View.INVISIBLE);
    }

    public void hideProgressView() {
        postJobActivityVM.hideProgressView();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
