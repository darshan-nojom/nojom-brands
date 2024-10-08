package com.nojom.client.ui.chat;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityChatBinding;
import com.nojom.client.ui.BaseActivity;

public class ChatActivity extends BaseActivity {
    ChatActivityVM chatActivityVM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityChatBinding chatBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
        chatActivityVM=new ChatActivityVM(Task24Application.getInstance(), chatBinding, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        chatActivityVM.onResume();
    }

    @Override
    public void onBackPressed() {
        onBackPressedEvent();
    }
}
