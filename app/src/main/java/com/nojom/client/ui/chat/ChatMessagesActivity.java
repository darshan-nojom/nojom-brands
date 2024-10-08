package com.nojom.client.ui.chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityChatMessagesBinding;
import com.nojom.client.model.ChatList;
import com.nojom.client.multitypepicker.Constant;
import com.nojom.client.multitypepicker.filter.entity.AudioFile;
import com.nojom.client.multitypepicker.filter.entity.ImageFile;
import com.nojom.client.multitypepicker.filter.entity.NormalFile;
import com.nojom.client.multitypepicker.filter.entity.VideoFile;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.chat.chatInterface.OnlineOfflineListener;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class ChatMessagesActivity extends BaseActivity implements OnlineOfflineListener {

    private ChatMessagesActivityVM chatMessagesActivityVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setOnlineOfflineListener(this);
        ActivityChatMessagesBinding chatMessagesBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat_messages);
        chatMessagesActivityVM = new ChatMessagesActivityVM(Task24Application.getInstance(), chatMessagesBinding, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        chatMessagesActivityVM.onResumeMethod();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Preferences.writeString(this, Constants.CHAT_OPEN_ID, "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.off("loadMessageHistory");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constant.REQUEST_CODE_PICK_IMAGE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                ArrayList<ImageFile> imgPaths = data.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE);
                if (imgPaths != null && imgPaths.size() > 0) {
                    chatMessagesActivityVM.showProgress(imgPaths.size());
                    for (ImageFile file : imgPaths) {
                        chatMessagesActivityVM.onFileSelect(new File(file.getPath()), file.getName());
                    }
                }
            }
        } else if (requestCode == Constant.REQUEST_CODE_PICK_FILE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                ArrayList<NormalFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_FILE);
                chatMessagesActivityVM.showProgress(list.size());
                for (NormalFile file : list) {
                    chatMessagesActivityVM.onFileSelect(new File(file.getPath()), file.getName());
                }
            }
        } else if (requestCode == 4545) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                String path = null;
                try {
                    path = Utils.getFilePath(ChatMessagesActivity.this, data.getData());
                    if (path != null) {
                        chatMessagesActivityVM.onFileSelect(new File(path), new File(path).getName());
                    }
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

            }
        } else if (requestCode == Constant.REQUEST_CODE_PICK_VIDEO) {
            if (resultCode == RESULT_OK) {
                ArrayList<VideoFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_VIDEO);
                chatMessagesActivityVM.showProgress(list.size());
                for (VideoFile file : list) {
                    chatMessagesActivityVM.onFileSelect(new File(file.getPath()), file.getName());
                }
            }
        } else if (requestCode == Constant.REQUEST_CODE_PICK_AUDIO) {
            if (resultCode == RESULT_OK) {
                ArrayList<AudioFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_AUDIO);
                chatMessagesActivityVM.showProgress(list.size());
                for (AudioFile file : list) {
                    chatMessagesActivityVM.onFileSelect(new File(file.getPath()), file.getName());
                }
            }
        }
    }

    @Override
    public void onlineUser(ChatList.Datum args) {
        try {
            if (args != null) {
                chatMessagesActivityVM.manageUserStatus(args, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void offlineUsr(ChatList.Datum args) {
        try {
            if (args != null) {
                chatMessagesActivityVM.manageUserStatus(args, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
