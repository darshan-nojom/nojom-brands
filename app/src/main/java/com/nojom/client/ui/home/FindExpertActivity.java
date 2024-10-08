package com.nojom.client.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityFindExpertBinding;
import com.nojom.client.ui.BaseActivity;

public class FindExpertActivity extends BaseActivity {

    private FindExpertActivityVM findExpertActivityVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFindExpertBinding findExpertBinding = DataBindingUtil.setContentView(this, R.layout.activity_find_expert);
        findExpertActivityVM = new FindExpertActivityVM(Task24Application.getInstance(), findExpertBinding, this);
    }

    public boolean isPostJobScreen() {
        if (findExpertActivityVM != null) {
            return findExpertActivityVM.isFromPostJobScreen();
        }
        return false;
    }

    public void setCallApiForSearchTag(boolean isSearch) {
        if (findExpertActivityVM != null) {
            findExpertActivityVM.setCallAPIForSearch(isSearch);
        }
    }

    public String getSearchkeyword() {
        if (findExpertActivityVM != null) {
            return findExpertActivityVM.getSearchKeyword();
        }
        return "";
    }

    @Override
    protected void onResume() {
        super.onResume();
        findExpertActivityVM.onResumeMethod();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        findExpertActivityVM.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void unSelectAll() {
        findExpertActivityVM.unSelectAll();
    }

    public void openPostJob() {
        findExpertActivityVM.openPostJobFragment();
    }

    public int getServiceCategoryId() {
        return findExpertActivityVM.serviceCatId;
    }

    public int getLanguageId() {
        return findExpertActivityVM.languageId;
    }

    public String getWorkbase() {
        return findExpertActivityVM.workBase;
    }

    public String getAvailability() {
        return findExpertActivityVM.availability;
    }

    public String getSkillId() {
        return findExpertActivityVM.skillIds;
    }

    public String getSortBy() {
        return findExpertActivityVM.sortType;
    }
}
