package com.nojom.client.ui.balance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.FragmentBalanceHistoryBinding;
import com.nojom.client.fragment.BaseFragment;

import org.jetbrains.annotations.NotNull;

public class HistoryFragment extends BaseFragment {

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentBalanceHistoryBinding balanceHistoryBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_balance_history, container, false);
        new HistoryFragmentVM(Task24Application.getInstance(), balanceHistoryBinding, this);
        return balanceHistoryBinding.getRoot();
    }
}
