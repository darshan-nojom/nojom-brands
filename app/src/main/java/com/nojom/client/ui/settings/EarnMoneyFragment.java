package com.nojom.client.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.FragmentEarnMoneyBinding;
import com.nojom.client.fragment.BaseFragment;

import org.jetbrains.annotations.NotNull;

public class EarnMoneyFragment extends BaseFragment {
    private EarnMoneyFragmentVM earnMoneyFragmentVM;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentEarnMoneyBinding earnMoneyBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_earn_money, container, false);
        earnMoneyFragmentVM = new EarnMoneyFragmentVM(Task24Application.getInstance(), earnMoneyBinding, this);
        return earnMoneyBinding.getRoot();
    }

    void setLink() {
        if (earnMoneyFragmentVM != null) {
            earnMoneyFragmentVM.setLink();
        }
    }
}
