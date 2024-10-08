package com.nojom.client.fragment.chat;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;

import androidx.lifecycle.AndroidViewModel;

import com.nojom.client.databinding.FragmentLiveChatBinding;
import com.nojom.client.ui.BaseActivity;

import io.intercom.android.sdk.Intercom;

class LiveChatFragmentVM extends AndroidViewModel {
    private FragmentLiveChatBinding binding;
    private BaseActivity activity;

    LiveChatFragmentVM(Application application, FragmentLiveChatBinding liveChatBinding, BaseActivity activity) {
        super(application);
        binding = liveChatBinding;
        this.activity = activity;
        initData();
    }

    private void initData() {
        binding.llChatItem.setOnClickListener(v -> Intercom.client().displayMessageComposer());
    }
}
