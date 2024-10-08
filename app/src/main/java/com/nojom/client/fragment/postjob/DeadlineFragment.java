package com.nojom.client.fragment.postjob;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.FragmentDeadlineBinding;
import com.nojom.client.fragment.BaseFragment;

import org.jetbrains.annotations.NotNull;

public class DeadlineFragment extends BaseFragment {
    private FragmentDeadlineBinding fragmentDeadlineBinding;
    private DeadlineFragmentVM deadlineFragmentVM;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentDeadlineBinding != null) {
            return fragmentDeadlineBinding.getRoot();
        }
        fragmentDeadlineBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_deadline, container, false);
        deadlineFragmentVM = new DeadlineFragmentVM(Task24Application.getInstance(), fragmentDeadlineBinding, this);

        fragmentDeadlineBinding.getRoot().setFocusableInTouchMode(true);
        fragmentDeadlineBinding.getRoot().requestFocus();
        fragmentDeadlineBinding.getRoot().setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                deadlineFragmentVM.goBack();
                return true;
            }
            return false;
        });

        return fragmentDeadlineBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        deadlineFragmentVM.onResumeMethod();
    }
}
