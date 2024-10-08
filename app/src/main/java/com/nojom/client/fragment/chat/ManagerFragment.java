package com.nojom.client.fragment.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.FragmentChatManagerBinding;
import com.nojom.client.fragment.BaseFragment;

import org.jetbrains.annotations.NotNull;

public class ManagerFragment extends BaseFragment {

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentChatManagerBinding managerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat_manager, container, false);
        new ManagerFragmentVM(Task24Application.getInstance(), managerBinding, this);
        return managerBinding.getRoot();
    }
}
