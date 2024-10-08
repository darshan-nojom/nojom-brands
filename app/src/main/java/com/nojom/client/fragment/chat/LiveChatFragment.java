package com.nojom.client.fragment.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.FragmentLiveChatBinding;
import com.nojom.client.fragment.BaseFragment;

import org.jetbrains.annotations.NotNull;

public class LiveChatFragment extends BaseFragment {

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentLiveChatBinding liveChatBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_live_chat, container, false);
        new LiveChatFragmentVM(Task24Application.getInstance(), liveChatBinding,activity);
        return liveChatBinding.getRoot();
    }
}
