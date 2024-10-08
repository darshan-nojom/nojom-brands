package com.nojom.client.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.FragmentMyLevelBinding;
import com.nojom.client.fragment.BaseFragment;

import org.jetbrains.annotations.NotNull;

public class MyLevelFragment extends BaseFragment {

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentMyLevelBinding myLevelBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_level, container, false);
        new MyLevelFragmentVM(Task24Application.getInstance(), myLevelBinding, this);
        return myLevelBinding.getRoot();
    }
}
