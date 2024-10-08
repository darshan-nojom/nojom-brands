package com.nojom.client.ui.projects;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityProjectDetailsBinding;
import com.nojom.client.model.ProjectGigByID;
import com.nojom.client.ui.BaseActivity;

public class ProjectGigDetailsActivity extends BaseActivity {

    private ProjectGigDetailsActivityVM projectGigDetailsActivityVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityProjectDetailsBinding detailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_project_details);
        projectGigDetailsActivityVM = new ProjectGigDetailsActivityVM(Task24Application.getInstance(), detailsBinding, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        projectGigDetailsActivityVM.onResumeMethod();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        projectGigDetailsActivityVM.onActivityResult(requestCode, resultCode, data);
    }

    public ProjectGigByID getProjectData() {
        return projectGigDetailsActivityVM.getProjectData();
    }

    public void setPagerPosition(int position) {
        projectGigDetailsActivityVM.setPagerPosition(position);
    }

    public void getProjectGigById(boolean isNeedToRefresh) {
        projectGigDetailsActivityVM.getProjectGigById(isNeedToRefresh);
    }

    public boolean isState() {
        return projectGigDetailsActivityVM.isState;
    }

    public String getGigType() {
        return projectGigDetailsActivityVM.gigType;
    }

}
