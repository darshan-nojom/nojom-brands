package com.nojom.client.ui.projects;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityProjectDetailsBinding;
import com.nojom.client.model.ProjectByID;
import com.nojom.client.ui.BaseActivity;

public class ProjectDetailsActivity extends BaseActivity {

    private ProjectDetailsActivityVM projectDetailsActivityVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityProjectDetailsBinding detailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_project_details);
        projectDetailsActivityVM = new ProjectDetailsActivityVM(Task24Application.getInstance(), detailsBinding, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        projectDetailsActivityVM.onResumeMethod();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        projectDetailsActivityVM.onActivityResult(requestCode, resultCode, data);
    }

    public ProjectByID getProjectData() {
        return projectDetailsActivityVM.getProjectData();
    }

    public void setPagerPosition(int position) {
        projectDetailsActivityVM.setPagerPosition(position);
    }

    public void getProjectById(boolean isNeedToRefresh) {
        projectDetailsActivityVM.getProjectById(isNeedToRefresh);
    }

    public boolean isState() {
        return projectDetailsActivityVM.isState;
    }
}
