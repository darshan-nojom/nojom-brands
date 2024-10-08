package com.nojom.client.ui.chat.chatInterface;


import com.nojom.client.model.ChatList;

public interface OnlineOfflineListener {

    void onlineUser(ChatList.Datum args);

    void offlineUsr(ChatList.Datum args);

}
