package com.nojom.client.ui.projects;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityMyProjectsBinding;
import com.nojom.client.ui.BaseActivity;

public class MyProjectsActivity extends BaseActivity {
    private MyProjectsActivityVM myProjectsActivityVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMyProjectsBinding myProjectsBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_projects);
        myProjectsActivityVM = new MyProjectsActivityVM(Task24Application.getInstance(), myProjectsBinding, this);
    }

    public void showHideHorizontalProgress(int visibility) {
        myProjectsActivityVM.showHideHorizontalProgress(visibility);
    }

    @Override
    public void onBackPressed() {
        onBackPressedEvent();
    }
}
