package com.nojom.client.ui;

import android.app.Application;
import android.content.res.TypedArray;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.GridLayoutManager;

import com.nojom.client.R;
import com.nojom.client.adapter.HireItemsAdapter;
import com.nojom.client.databinding.ActivitySelectAccountBinding;
import com.nojom.client.ui.auth.LoginSignUpActivity;
import com.nojom.client.ui.home.HomePagerModel;
import com.nojom.client.util.Constants;
import com.nojom.client.util.EqualSpacingItemDecoration;
import com.nojom.client.util.Preferences;

import java.util.ArrayList;

class SelectAccountActivityVM extends AndroidViewModel implements View.OnClickListener {
    private ActivitySelectAccountBinding binding;
    private BaseActivity activity;

    SelectAccountActivityVM(Application application, ActivitySelectAccountBinding selectAccountBinding, BaseActivity selectAccountActivity) {
        super(application);
        binding = selectAccountBinding;
        activity = selectAccountActivity;
        initData();
    }

    private void initData() {
        binding.rlHire.setOnClickListener(this);
        binding.rlWork.setOnClickListener(this);
        binding.rvHire.setLayoutManager(new GridLayoutManager(activity, 4));
        binding.rvHire.addItemDecoration(new EqualSpacingItemDecoration(20));

        HireItemsAdapter mHireAdapter = new HireItemsAdapter(activity, getList(), null);

        binding.rvHire.setAdapter(mHireAdapter);
    }

    public ArrayList<HomePagerModel> getList() {
        ArrayList<HomePagerModel> arrayList = new ArrayList<>();
        try {
            TypedArray imgs = activity.getResources().obtainTypedArray(R.array.service_images);
            String[] stringArray = activity.getResources().getStringArray(R.array.services);

            for (int i = 0; i < stringArray.length; i++) {
                HomePagerModel model = new HomePagerModel();
                model.title = stringArray[i];
                model.icon = imgs.getResourceId(i, -1);
                arrayList.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_hire:
                Preferences.writeBoolean(activity, Constants.IS_CLIENT_ACCOUNT, true);
                activity.redirectActivity(LoginSignUpActivity.class);
                break;
            case R.id.rl_work:
                activity.redirectUsingCustomTab("https://24taskagents.page.link/fdga");
                break;
        }
    }
}
