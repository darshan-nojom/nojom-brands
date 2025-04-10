package com.nojom.client.fragment.chat;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.nojom.client.R;
import com.nojom.client.adapter.ChatListAdapter;
import com.nojom.client.databinding.FragmentChatListBinding;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.model.ChatList;
import com.nojom.client.model.ChatMessageList;
import com.nojom.client.model.Typing;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


class ChatListFragmentVM extends AndroidViewModel {
    private FragmentChatListBinding binding;
    private BaseFragment fragment;
    private ChatListAdapter chatListAdapter;
    private List<ChatList.Datum> arrChatList;
    private String imgPath = "";

    ChatListFragmentVM(Application application, FragmentChatListBinding chatListBinding, BaseFragment chatListFragment) {
        super(application);
        binding = chatListBinding;
        fragment = chatListFragment;
        initData();
    }

    private void initData() {
        binding.noData.tvNoTitle.setText(fragment.getString(R.string.no_messages));
        binding.noData.tvNoDescription.setText(fragment.getString(R.string.freelancer_chat_desc));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(fragment.activity);
        binding.rvChatList.setLayoutManager(linearLayoutManager);

        Utils.trackAppsFlayerEvent(fragment.activity, "Chat_Screen");

        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            if (fragment.activity.isLogin()) {
                getAllChatList();
            } else {
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void getAllChatList() {
        binding.shimmerLayout.startShimmer();
        getAllUser();
    }

    void onResumeMethod() {
        if (fragment.activity.isLogin()) {
            getAllChatList();
            getNewMessage();
            getTyping();
        } else {
            binding.linPlaceholderLogin.setVisibility(View.VISIBLE);
            binding.shimmerLayout.setVisibility(View.GONE);
            binding.rvChatList.setVisibility(View.GONE);
            binding.btnLogin.setOnClickListener(v -> fragment.activity.openLoginDialog());
        }
    }

    private void setAdapter() {
        try {
            if (arrChatList != null && arrChatList.size() > 0) {
                binding.noData.llNoData.setVisibility(View.GONE);
                chatListAdapter = new ChatListAdapter(fragment.activity, arrChatList, imgPath);
                binding.rvChatList.setAdapter(chatListAdapter);
            } else {
                binding.noData.llNoData.setVisibility(View.VISIBLE);
                if (chatListAdapter != null)
                    chatListAdapter.doRefresh(arrChatList);
            }
            binding.rvChatList.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect(boolean isConnect) {
        Log.e("AAAAA", "disconnect..." + isConnect);
    }

    public void onError(Object args) {
        Log.d("AAAAA", "onError..." + args.toString());
    }

    public void getNewMessage() {
        try {
            fragment.activity.mSocket.on("getMessage", args -> {
                fragment.activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ChatMessageList.DataChatList newMessage = new Gson().fromJson(args[0].toString(), ChatMessageList.DataChatList.class);
                        if (newMessage != null) {
                            for (int i = 0; i < arrChatList.size(); i++) {
                                if (!newMessage.self) {
                                    if (newMessage.senderId.equalsIgnoreCase(String.valueOf(arrChatList.get(i).id))) {
                                        updateChatListItem(i, newMessage);
                                        break;
                                    }
                                } else {
                                    if (newMessage.receiverId.equalsIgnoreCase(String.valueOf(arrChatList.get(i).id))) {
                                        updateChatListItem(i, newMessage);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                });
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAllUser() {
        try {
            if (!fragment.activity.isNetworkConnected()) {
                binding.swipeRefreshLayout.setRefreshing(false);
                return;
            }
            arrChatList = new ArrayList<>();
            Call<ChatList> call = fragment.activity.getService().getUser(
                    Constants.BASE_URL_CHAT + "users/getAllUsers",
                    String.valueOf(fragment.activity.getUserID()), "2", "6", fragment.activity.getJWT());
            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<ChatList> call, Response<ChatList> response) {
                    binding.shimmerLayout.stopShimmer();
                    binding.shimmerLayout.setVisibility(View.GONE);
                    binding.noData.llNoData.setVisibility(View.GONE);
                    ChatList user = response.body();
                    if (user != null) {
                        if (user.status) {
                            try {
                                if (user.data.data != null && user.data.data.size() > 0) {
                                    arrChatList = user.data.data;
                                    imgPath = user.data.path;
                                    setAdapter();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            binding.noData.llNoData.setVisibility(View.VISIBLE);
                            binding.shimmerLayout.stopShimmer();
                            binding.shimmerLayout.setVisibility(View.GONE);
                        }
                    }
                    binding.swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onFailure(Call<ChatList> call, Throwable t) {
                    fragment.activity.failureError(fragment.activity.getString(R.string.get_user_list_failed));
                    binding.shimmerLayout.stopShimmer();
                    binding.shimmerLayout.setVisibility(View.GONE);
                    binding.swipeRefreshLayout.setRefreshing(false);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            binding.swipeRefreshLayout.setRefreshing(false);
        }
    }


    private void getTyping() {
        fragment.activity.mSocket.on("getTyping", args -> {
            fragment.activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Typing typing = new Gson().fromJson(args[0].toString(), Typing.class);
                    if (arrChatList != null && arrChatList.size() > 0)
                        for (int i = 0; i < arrChatList.size(); i++) {
                            if (typing.senderId.equals(arrChatList.get(i).id)) {
                                arrChatList.get(i).typing = typing.type;
                                chatListAdapter.notifyItemChanged(i);
                                break;
                            }
                        }
                }
            });
        });
    }

    public void manageUserStatus(ChatList.Datum moUserStatus, int status) {
        fragment.activity.runOnUiThread(() -> {
            try {
                if (moUserStatus != null && arrChatList != null && arrChatList.size() > 0) {
                    for (int i = 0; i < arrChatList.size(); i++) {
                        if (moUserStatus.id.equals(arrChatList.get(i).id)) {
                            arrChatList.get(i).isSocketOnline = status;// moUserStatus.status;
                            chatListAdapter.notifyDataSetChanged();
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

    private void updateChatListItem(int position, ChatMessageList.DataChatList newMessage) {
        try {
            if (newMessage != null) {
                arrChatList.get(position).lastMessageData.message = newMessage.message;
                arrChatList.get(position).lastMessageData.messageCreatedAt = newMessage.messageCreatedAt;
                arrChatList.get(position).lastMessageData.isSeenMessage = newMessage.isSeenMessage;
                if (newMessage.message == null) {
                    if (newMessage.file.files.get(0).file != null) {
                        ChatList.FileImages fileImages = new ChatList.FileImages();
                        fileImages.file = newMessage.file.files.get(0).file;
                        arrChatList.get(position).lastMessageData.file.files.set(0, fileImages);
                    }
                }
                chatListAdapter.notifyItemChanged(position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
