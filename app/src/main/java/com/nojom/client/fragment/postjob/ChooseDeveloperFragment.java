package com.nojom.client.fragment.postjob;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.FragmentChooseDeveloperBinding;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.util.Constants;

import org.jetbrains.annotations.NotNull;

public class ChooseDeveloperFragment extends BaseFragment {

    private ChooseDeveloperFragmentVM chooseDeveloperFragmentVM;
    private FragmentChooseDeveloperBinding chooseDeveloperBinding;

    public static ChooseDeveloperFragment newInstance(boolean isEdit) {
        ChooseDeveloperFragment fragment = new ChooseDeveloperFragment();
        Bundle args = new Bundle();
        args.putBoolean(Constants.IS_EDIT, isEdit);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (chooseDeveloperBinding != null) {
            return chooseDeveloperBinding.getRoot();
        }

        chooseDeveloperBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_choose_developer, container, false);
        chooseDeveloperFragmentVM = new ChooseDeveloperFragmentVM(Task24Application.getInstance(), chooseDeveloperBinding, this);

        chooseDeveloperBinding.getRoot().setFocusableInTouchMode(true);
        chooseDeveloperBinding.getRoot().requestFocus();
        chooseDeveloperBinding.getRoot().setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                chooseDeveloperFragmentVM.goBack();
                return true;
            }
            return false;
        });

        return chooseDeveloperBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        chooseDeveloperFragmentVM.onResumeMethod();
    }
}
