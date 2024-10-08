package com.nojom.client.fragment.postjob;

import android.app.Application;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;

import com.nojom.client.R;
import com.nojom.client.databinding.FragmentPayTypeBinding;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.ui.clientprofile.PostJobActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

public class PayTypeFragmentVM extends AndroidViewModel implements View.OnClickListener {
    private FragmentPayTypeBinding binding;
    private BaseFragment fragment;

    PayTypeFragmentVM(Application application, FragmentPayTypeBinding payTypeBinding, BaseFragment payTypeFragment) {
        super(application);
        binding = payTypeBinding;
        fragment = payTypeFragment;
        initData();
    }

    private void initData() {
        binding.toolbar.imgBack.setOnClickListener(this);
        binding.rlHourly.setOnClickListener(this);
        binding.rlFixedPrice.setOnClickListener(this);

        binding.toolbar.tvNext.setVisibility(View.GONE);
    }

    void onResumeMethod() {
        ((PostJobActivity) fragment.activity).setProgressView(0.4f);
    }


    @Override
    public void onClick(View view) {
        Utils.hideSoftKeyboard(fragment.activity);
        switch (view.getId()) {
            case R.id.img_back:
                fragment.goBackTo();
                break;
            case R.id.rl_hourly:
                Preferences.writeBoolean(fragment.activity, Constants.IS_FIXED_PRICE, false);
                fragment.activity.replaceFragment(PriceRateFragment.newInstance(false));
                break;
            case R.id.rl_fixed_price:
                Preferences.writeBoolean(fragment.activity, Constants.IS_FIXED_PRICE, true);
                fragment.activity.replaceFragment(PriceRateFragment.newInstance(false));
                break;
        }
    }
}
