package com.nojom.client.fragment.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.FragmentChatListBinding;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.model.ChatList;
import com.nojom.client.ui.chat.chatInterface.ChatInterface;
import com.nojom.client.ui.chat.chatInterface.NewMessageForList;
import com.nojom.client.ui.chat.chatInterface.OnlineOfflineListener;

import org.jetbrains.annotations.NotNull;

public class ChatListFragment extends BaseFragment implements ChatInterface, OnlineOfflineListener, NewMessageForList {
    private ChatListFragmentVM chatListFragmentVM;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity.setChatInterface(this);
        activity.setOnlineOfflineListener(this);
        activity.setNewMessageForList(this);
        FragmentChatListBinding chatListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat_list, container, false);
        chatListFragmentVM = new ChatListFragmentVM(Task24Application.getInstance(), chatListBinding, this);
        return chatListBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        chatListFragmentVM.onResumeMethod();
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            if (activity.mSocket != null) {
                activity.mSocket.off("getMessage");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onConnect(boolean isConnect) {
    }

    @Override
    public void disconnect(boolean isConnect) {
        chatListFragmentVM.disconnect(isConnect);
    }

    @Override
    public void onError(Object args) {
        chatListFragmentVM.onError(args);
    }

    @Override
    public void getNewMessage(Object args) {

    }

    @Override
    public void onlineUser(ChatList.Datum args) {
        if (chatListFragmentVM != null) {
            if (args != null) {
                chatListFragmentVM.manageUserStatus(args, 1);
            }
        }
    }

    @Override
    public void offlineUsr(ChatList.Datum args) {
        if (chatListFragmentVM != null) {
            if (args != null) {
                chatListFragmentVM.manageUserStatus(args, 0);
            }
        }
    }
}
