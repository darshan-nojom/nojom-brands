package com.nojom.client.ui.chat;

import static com.nojom.client.multitypepicker.activity.ImagePickActivity.IS_NEED_CAMERA;
import static com.nojom.client.util.Constants.API_GET_CONTRACT_DETAILS;
import static com.nojom.client.util.Constants.API_GET_CUSTOM_CONTRACT_DETAILS;
import static com.nojom.client.util.Constants.API_GET_CUSTOM_GIG_DETAILS;
import static com.nojom.client.util.Constants.API_GET_PROFILE_INFO;
import static com.nojom.client.util.Constants.API_GET_VIEW_OFFER_DETAILS;
import static com.nojom.client.util.Constants.API_JOB_DETAILS;
import static com.nojom.client.util.Constants.CLIENT_PROFILE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.nojom.client.R;
import com.nojom.client.adapter.ChatMessageAdapter;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivityChatMessagesBinding;
import com.nojom.client.model.AgentProfile;
import com.nojom.client.model.ChatList;
import com.nojom.client.model.ChatLiveOfferStatusModel;
import com.nojom.client.model.ChatMessageList;
import com.nojom.client.model.ChatSeenModel;
import com.nojom.client.model.ExpertDetail;
import com.nojom.client.model.ExpertGigDetail;
import com.nojom.client.model.ExpertLawyers;
import com.nojom.client.model.MuteUnmute;
import com.nojom.client.model.ProjectByID;
import com.nojom.client.model.ProjectGigByID;
import com.nojom.client.model.SenderReceiverData;
import com.nojom.client.model.Typing;
import com.nojom.client.multitypepicker.Constant;
import com.nojom.client.multitypepicker.activity.ImagePickActivity;
import com.nojom.client.multitypepicker.activity.NormalFilePickActivity;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.dialog.StorageDisclosureDialog;
import com.nojom.client.ui.home.GigDetailActivity;
import com.nojom.client.ui.projects.InfluencerProfileActivityCopy;
import com.nojom.client.ui.projects.ProjectDetailsActivity;
import com.nojom.client.ui.projects.ProjectGigDetailsActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import io.intercom.android.sdk.Intercom;


public class ChatMessagesActivityVM extends AndroidViewModel implements View.OnClickListener,
        BaseActivity.ExpertInfoListener, RequestResponseListener, ChatMessageAdapter.OnClickListener {
    boolean isScrolling = false;
    private int totalSize;
    private final ActivityChatMessagesBinding binding;
    private final BaseActivity activity;
    private ChatMessageAdapter chatMessageAdapter;
    private LinearLayoutManager layoutManager;
    private int count = 0;
    private int firstVisibleItemPosition;
    private JSONObject jsonDataLastKey;
    private int receiverID, gigID = 0, offerID = 0, contractID = 0;
    private ArrayList<ChatMessageList.DataChatList> chatMsgList = null;
    private Date lastMessageDate = null;
    private HashMap<String, String> chatMap = null;
    private ChatMessageList.Project project = null;
    private boolean isFromDetailScreen, isDataLoading, isFromManageProject;
    private String username, profilePic;
    private String chatId, pk = "";
    private Long sk = null, messageId = null;
    private ProgressDialog progress;
    private final RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (!isDataLoading) {
                try {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                    int lastVisibleItem = firstVisibleItemPosition + visibleItemCount;

                    if (lastVisibleItem == totalItemCount) {
                        if (!isScrolling) {
                            isScrolling = true;
                            isDataLoading = true;
                            getPreviousMessage(false);
                        }
                    }

                    if (firstVisibleItemPosition > 2) {
                        if (count == 0)
                            binding.tvNewMessageCount.setVisibility(View.GONE);
                        binding.rlScrollDown.setVisibility(View.VISIBLE);
                    } else {
                        count = 0;
                        binding.rlScrollDown.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    };
    private Dialog chatSettingDialog;
    private TextView tvHire, tvViewProfile, tvManage;
    private CircularProgressBar progressBar, progressBarProfile, progressBarProjectManage;
    private ChatMessageList.DataChatList tempNewMessage = null;
    private double price = 0;

    ChatMessagesActivityVM(Application application, ActivityChatMessagesBinding chatMessagesBinding, BaseActivity baseActivity) {
        super(application);
        binding = chatMessagesBinding;
        activity = baseActivity;
        initData();
    }

    @SuppressLint("CheckResult")
    private void initData() {

        if (activity.getIntent() != null) {
            chatMap = (HashMap<String, String>) activity.getIntent().getSerializableExtra(Constants.CHAT_DATA);
            chatId = activity.getIntent().getStringExtra(Constants.CHAT_ID);

            if (activity.isEmpty(chatId)) {
                activity.finish();
                return;
            }

            if (chatMap != null && !TextUtils.isEmpty(chatMap.get("isDetailScreen"))) {
                isFromDetailScreen = true;
            }

            if (chatMap != null && !TextUtils.isEmpty(chatMap.get(Constants.RECEIVER_ID)) && receiverID == 0) {
                receiverID = Integer.parseInt(chatMap.get(Constants.RECEIVER_ID));
            }

            if (chatMap != null && !TextUtils.isEmpty(chatMap.get(Constants.RECEIVER_NAME)) && TextUtils.isEmpty(username)) {
                username = chatMap.get(Constants.RECEIVER_NAME);
            }

            if (chatMap != null && !TextUtils.isEmpty(chatMap.get(Constants.RECEIVER_PIC)) && TextUtils.isEmpty(profilePic)) {
                profilePic = chatMap.get(Constants.RECEIVER_PIC);
            }
        }

        jsonDataLastKey = new JSONObject();
        binding.imgBack.setOnClickListener(this);
        binding.imgSend.setOnClickListener(this);
        binding.imgSetting.setOnClickListener(this);
        binding.imgAttach.setOnClickListener(this);
        binding.ivScrollDown.setOnClickListener(this);
        binding.tvNewMessageCount.setOnClickListener(this);

        binding.tvName.setText(username);

        if (!TextUtils.isEmpty(profilePic)) {
            Glide.with(activity).load(profilePic)
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(binding.imgProfile);
        } else {
            binding.imgProfile.setImageResource(R.mipmap.ic_launcher_round);
        }

        layoutManager = new LinearLayoutManager(activity.getApplicationContext());
        binding.rvMessages.setLayoutManager(layoutManager);
        layoutManager.setReverseLayout(true);
        binding.rvMessages.addOnScrollListener(onScrollListener);

        binding.etMessage.addTextChangedListener(new TextWatcher() {
            CountDownTimer timer = null;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (timer != null) {
                    timer.cancel();
                }
                if (activity.isEmpty(getMessage())) {
                    binding.imgSend.setAlpha(0.5f);
                    binding.imgSend.setClickable(false);
                    sendTyping(false);
                } else {
                    binding.imgSend.setAlpha(1f);
                    binding.imgSend.setClickable(true);
                    sendTyping(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                timer = new CountDownTimer(3000, 1000) {

                    public void onTick(long millisUntilFinished) {

                    }

                    public void onFinish() {
                        sendTyping(false);
                    }

                }.start();
            }
        });


        Utils.trackAppsFlayerEvent(activity, "Conversion_Screen");

        getPreviousMessage(true);
        getHistorySuccess();
        getHistoryError();
        invalidNewMessage();
        getTyping();
        fileSavedSuccess();
        invalidFile();
        getMessageSeenEvent();
        getMuteUnmute();
        getLiveOfferStatus();
        failMessageWhenSend();
        inValidOfferData();

    }

    void onResumeMethod() {
        getNewMessage();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                activity.onBackPressed();
                break;
            case R.id.tvNewMessageCount:
            case R.id.ivScrollDown:
                try {
                    count = 0;
                    binding.rlScrollDown.setVisibility(View.GONE);
                    binding.rvMessages.scrollToPosition(0);
                    binding.tvNewMessageCount.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.img_send:
                if (!activity.isNetworkConnected())
                    return;

                activity.connectSocket(activity);
                if (activity.isEmpty(getMessage())) {
                    return;
                }

                Pattern pattern = Pattern.compile("-?\\d+");
                Matcher m = pattern.matcher(getMessage());
                while (m.find()) {
                    if (m.group().length() >= 6 && m.group().length() <= 15) {
                        showAlert();
                        return;
                    }
                }

                pattern = Pattern.compile("([a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+)");
                m = pattern.matcher(getMessage());
                while (m.find()) {
                    showAlert();
                    return;
                }

                senMessageAPI("", "");
                break;
            case R.id.img_setting:
                showSettingDialog();
                break;
            case R.id.img_attach:
                selectFileDialog();
                break;
        }
    }

    void showAlert() {
        final Dialog dialog = new Dialog(activity, R.style.Theme_AppCompat_Dialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_logout);
        dialog.setCancelable(true);

        TextView tvMessage = dialog.findViewById(R.id.tv_message);
        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        TextView tvChatnow = dialog.findViewById(R.id.tv_chat_now);

        tvMessage.setText(activity.getString(R.string.your_message_contains_sensitive_information));

        tvCancel.setOnClickListener(v -> {
            binding.etMessage.setText("");
            dialog.dismiss();
        });

        tvChatnow.setOnClickListener(v -> {
            dialog.dismiss();
            senMessageAPI("", "");
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.gravity = Gravity.CENTER;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }

    private void selectFileDialog() {
        @SuppressLint("PrivateResource") final Dialog dialog = new Dialog(activity, R.style.Theme_Design_Light_BottomSheetDialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_camera_document_select);
        dialog.setCancelable(true);
        TextView tvCancel = dialog.findViewById(R.id.btn_cancel);
        LinearLayout llCamera = dialog.findViewById(R.id.ll_camera);
        LinearLayout llDocument = dialog.findViewById(R.id.ll_document);

        llCamera.setOnClickListener(v -> {
            if (activity.checkStoragePermission()) {
                checkPermission(false);
            } else {
                new StorageDisclosureDialog(activity, () -> checkPermission(false));
            }
            dialog.dismiss();
        });

        llDocument.setOnClickListener(v -> {
            if (activity.checkStoragePermission()) {
                checkPermission(true);
            } else {
                new StorageDisclosureDialog(activity, () -> checkPermission(true));
            }
            dialog.dismiss();
        });

        tvCancel.setOnClickListener(v -> dialog.dismiss());

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }

    public void checkPermission(final boolean isDocument) {
        Dexter.withActivity(activity)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            if (isDocument) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                    activity.openDocuments(activity, 1, 4545);
                                } else {
                                    Intent intent = new Intent(activity, NormalFilePickActivity.class);
                                    intent.putExtra(Constant.MAX_NUMBER, 2);
                                    intent.putExtra(NormalFilePickActivity.SUFFIX, new String[]{"doc", "docx", "ppt", "pptx", "pdf"});
                                    activity.startActivityForResult(intent, Constant.REQUEST_CODE_PICK_FILE);
                                }
                            } else {
                                Intent intent = new Intent(activity, ImagePickActivity.class);
                                intent.putExtra(IS_NEED_CAMERA, true);
                                intent.putExtra(Constant.MAX_NUMBER, 2);
                                activity.startActivityForResult(intent, Constant.REQUEST_CODE_PICK_IMAGE);
                            }
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            activity.toastMessage(activity.getString(R.string.please_give_permission));
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
    }

    private void getPreviousMessage(boolean isPass) {
        try {
            JSONObject jsonData = new JSONObject();
            jsonData.put("senderId", activity.getUserID());
            jsonData.put("receiverId", receiverID);
            jsonData.put("partitionKey", "#message#" + activity.getUserID() + "-" + receiverID);
            jsonData.put("limit", 10);
            if (isPass && chatMap != null && !TextUtils.isEmpty(chatMap.get("isProject"))) {
                jsonData.put("isProject", chatMap.get("isProject"));
            } else {
                jsonData.put("isProject", "1");
            }
            if (isPass && chatMap != null && !TextUtils.isEmpty(chatMap.get("projectType"))) {//gig or job
                jsonData.put("projectType", chatMap.get("projectType"));
            }
            if (isPass && chatMap != null && !TextUtils.isEmpty(chatMap.get(Constants.PROJECT_ID))) {//gig or job
                jsonData.put("projectId", chatMap.get(Constants.PROJECT_ID));
            }
            if (jsonDataLastKey != null) {
                jsonData.put("lastEvaluatedKey", jsonDataLastKey);
            } else {
                return;
            }

            activity.mSocket.emit("getMessageHistory", jsonData);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getMessageSeenEvent() {
        try {
            activity.mSocket.on("getMessageSeenEvent", args -> {
                activity.runOnUiThread(() -> {
                    ChatSeenModel chatSeenModel = new Gson().fromJson(args[0].toString(), ChatSeenModel.class);

                    if (chatMsgList != null && chatMsgList.size() > 0) {
                        for (int i = 0; i < chatSeenModel.messageIds.length; i++) {
                            for (int j = 0; j < chatMsgList.size(); j++) {
                                if (chatSeenModel.messageIds[i].equals(chatMsgList.get(j).messageId)) {
                                    chatMsgList.get(j).isSeenMessage = "2";
                                    chatMessageAdapter.notifyItemChanged(j);
                                    break;
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

    private void getLiveOfferStatus() {
        try {
            activity.mSocket.on("getLiveOfferStatus", args -> {
                activity.runOnUiThread(() -> {
                    ChatLiveOfferStatusModel chatLiveOfferStatusModel = new Gson().fromJson(args[0].toString(), ChatLiveOfferStatusModel.class);

                    if (chatMsgList != null && chatMsgList.size() > 0) {
                        for (int j = 0; j < chatMsgList.size(); j++) {
                            if (chatLiveOfferStatusModel.messageId.equals(chatMsgList.get(j).messageId)) {
                                chatMsgList.get(j).offer.offerStatus = chatLiveOfferStatusModel.offerStatus;
                                chatMessageAdapter.notifyItemChanged(j);
                                break;
                            }
                        }
                    }
                });
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getMessage() {
        return Objects.requireNonNull(binding.etMessage.getText()).toString().trim();
    }

    private void showSettingDialog() {
        chatSettingDialog = new Dialog(activity);
        chatSettingDialog.setTitle(null);
        chatSettingDialog.setContentView(R.layout.dialog_chat_setting);
        chatSettingDialog.setCancelable(true);
        TextView tvCancel = chatSettingDialog.findViewById(R.id.btn_cancel);
        tvViewProfile = chatSettingDialog.findViewById(R.id.tv_view_profile);
        TextView tvMute = chatSettingDialog.findViewById(R.id.tv_mute);
        tvManage = chatSettingDialog.findViewById(R.id.tv_manage_project);
        TextView tvReport = chatSettingDialog.findViewById(R.id.tv_report_freelancer);
        tvHire = chatSettingDialog.findViewById(R.id.tv_hire);
        progressBar = chatSettingDialog.findViewById(R.id.progress_bar);
        progressBarProfile = chatSettingDialog.findViewById(R.id.progress_bar_profile);
        progressBarProjectManage = chatSettingDialog.findViewById(R.id.progress_bar_manage_project);

        if (project != null) {
            tvMute.setVisibility(View.VISIBLE);
            tvMute.setText(project.a_mute ? activity.getString(R.string.unmute_conversation) : activity.getString(R.string.mute_conversation));
        } else {
            tvMute.setVisibility(View.GONE);
        }

        tvHire.setOnClickListener(view -> {
            try {
                if (!TextUtils.isEmpty(chatId)) {
                    activity.setExpertInfoListener(this);
                    activity.getExpert(Integer.parseInt(chatId.split("-")[1]));
                } else
                    activity.toastMessage(activity.getString(R.string.agent_has_not_been_hired_on_this_project));
            } catch (Exception e) {
                e.printStackTrace();
                activity.toastMessage(activity.getString(R.string.agent_has_not_been_hired_on_this_project));
            }
        });

        tvViewProfile.setOnClickListener(v -> {
            if (receiverID != 0) {
                getAgentProfile(receiverID);
            }
        });

        tvMute.setOnClickListener(v -> {
            chatSettingDialog.dismiss();
            muteUnmute(!project.a_mute);

        });

        tvManage.setOnClickListener(v -> {
            try {
                if (isFromDetailScreen) {//if come from details or contract screen at that time no need to call API, simply redirect to back screen
                    chatSettingDialog.dismiss();
                    activity.onBackPressed();
                } else {
                    if (project != null && project.projectId != 0) {
                        isFromManageProject = true;
                        if (project.projectType.equalsIgnoreCase("1")) {//gig
                            contractID = project.projectId;
                            getGigDetailAPI();
                        } else if (project.projectType.equalsIgnoreCase("2")) {//job
                            getProjectById(project.projectId);
                        } else if (project.projectType.equalsIgnoreCase("3")) {//job
                            contractID = project.projectId;
                            getCustomGigDetailAPI();
                        }
                    } else {
                        chatSettingDialog.dismiss();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        tvReport.setOnClickListener(v -> {
            chatSettingDialog.dismiss();
//            activity.openWhatsappChat();
            Intercom.client().displayMessageComposer();
        });

        tvCancel.setOnClickListener(v -> chatSettingDialog.dismiss());

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(chatSettingDialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.TOP;
        chatSettingDialog.show();
        chatSettingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        chatSettingDialog.getWindow().setAttributes(lp);
    }

    private void muteUnmute(boolean isMute) {
        try {
            JSONObject jsonData = new JSONObject();
            jsonData.put("a_mute", isMute);
            jsonData.put("profile_type_id", "" + CLIENT_PROFILE);
            jsonData.put("partitionKey", "#message#" + activity.getUserID() + "-" + receiverID);

            activity.mSocket.emit("userToMuteUnmute", jsonData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onExpertSuccess(ExpertDetail expertDetail) {
        Preferences.writeString(activity, Constants.PLATFORM_ID, expertDetail.serviceId + "");
        Preferences.writeString(activity, Constants.PLATFORM_NAME, expertDetail.serviceName + "");
        ArrayList<ExpertLawyers.Data> expertUsers = new ArrayList<>();
        expertUsers.add(new ExpertLawyers.Data(expertDetail.profileId, expertDetail.firstName + " " + expertDetail.lastName));
        Preferences.setExpertUsers(activity, expertUsers);

        progressBar.setVisibility(View.GONE);
        tvHire.setVisibility(View.VISIBLE);
        activity.isClickableView = false;
        chatSettingDialog.dismiss();
        activity.gotoMainActivity(Constants.TAB_POST_JOB);
    }

    public void manageUserStatus(ChatList.Datum moUserStatus, int status) {
        try {
            activity.runOnUiThread(() -> {
                if (moUserStatus != null) {
                    if (moUserStatus.isSocketOnline == 1) {
                        binding.tvOnline.setText(activity.getString(R.string.active_now));
                    } else {
                        binding.tvOnline.setText(activity.getString(R.string.offline));
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendTyping(boolean isTyping) {
        try {
            JSONObject jsonData = new JSONObject();
            jsonData.put("senderId", activity.getUserID());
            jsonData.put("receiverId", receiverID);
            jsonData.put("type", isTyping);

            activity.mSocket.emit("sendTyping", jsonData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getMuteUnmute() {
        activity.mSocket.on("responseOfMuteUnmute", args -> {
            activity.runOnUiThread(() -> {
                MuteUnmute munm = new Gson().fromJson(args[0].toString(), MuteUnmute.class);
                project.a_mute = munm.data.aMute;
                activity.toastMessage((project.a_mute ? "Muted" : "Unmuted"));
            });
        });
    }

    private void getMessageSeen() {
        try {
            JSONObject jsonData = new JSONObject();
            jsonData.put("partitionKey", "#message#" + activity.getUserID() + "-" + receiverID);
            jsonData.put("senderId", activity.getUserID());
            jsonData.put("receiverId", receiverID);

            activity.mSocket.emit("messageSeen", jsonData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onExpertFail() {
        progressBar.setVisibility(View.GONE);
        tvHire.setVisibility(View.VISIBLE);
        activity.isClickableView = false;

    }

    @Override
    public void onPreExpert() {
        progressBar.setVisibility(View.VISIBLE);
        tvHire.setVisibility(View.INVISIBLE);
        activity.isClickableView = true;
    }

    private void getAgentProfile(int agentProfileId) {
        if (!activity.isNetworkConnected())
            return;
        activity.isClickableView = true;
        tvViewProfile.setVisibility(View.INVISIBLE);
        progressBarProfile.setVisibility(View.VISIBLE);

        HashMap<String, String> map = new HashMap<>();
        map.put("agent_profile_id", agentProfileId + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_PROFILE_INFO, true, map);
    }

    void getProjectById(int jpId) {
        if (!activity.isNetworkConnected()) {
            return;
        }

        activity.isClickableView = true;
        tvManage.setVisibility(View.INVISIBLE);
        progressBarProjectManage.setVisibility(View.VISIBLE);

        HashMap<String, String> map = new HashMap<>();
        map.put("job_id", jpId + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_JOB_DETAILS, true, map);
    }

    void getGigDetailAPI() {
        if (!activity.isNetworkConnected()) {
            return;
        }

        activity.isClickableView = true;

        if (isFromManageProject) {
            tvManage.setVisibility(View.INVISIBLE);
            progressBarProjectManage.setVisibility(View.VISIBLE);
        }

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_CONTRACT_DETAILS + "/" + contractID, false, null);
    }

    void getCustomGigDetailAPI() {
        if (!activity.isNetworkConnected()) {
            return;
        }

        activity.isClickableView = true;
        if (isFromManageProject) {
            tvManage.setVisibility(View.INVISIBLE);
            progressBarProjectManage.setVisibility(View.VISIBLE);
        }

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_CUSTOM_CONTRACT_DETAILS + "/" + contractID, false, null);
    }

    void getGigViewDetailAPI() {
        if (!activity.isNetworkConnected()) {
            return;
        }

        activity.isClickableView = true;
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_CUSTOM_GIG_DETAILS + "/" + gigID, false, null);
    }

    void getOfferViewDetailAPI() {
        if (!activity.isNetworkConnected()) {
            return;
        }

        activity.isClickableView = true;
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_VIEW_OFFER_DETAILS + "/" + offerID, false, null);
    }

    private void getHistorySuccess() {
        try {
            activity.mSocket.on("loadMessageHistory", args -> {
                ChatMessageList chatMsg = new Gson().fromJson(args[0].toString(), ChatMessageList.class);
                if (chatMsg.data.lastEvaluatedKey != null) {
                    try {
                        jsonDataLastKey = new JSONObject(chatMsg.data.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    jsonDataLastKey = null;
                }

                if (chatMsg.data.dataChatList.size() > 0) {
                    try {
                        if (chatMsgList != null && chatMsgList.size() > 0) {
                            List<ChatMessageList.DataChatList> chatListTemp = chatMsg.data.dataChatList;
                            Collections.reverse(chatListTemp);
                            chatMsgList.addAll(chatListTemp);
                            Collections.reverse(chatMsgList);
                            Date lastMessageDate = null;
                            for (ChatMessageList.DataChatList chatList : chatMsgList) {
                                String messageCreatedAt = Utils.convertDate(String.valueOf(chatList.messageCreatedAt), "yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");
                                Date date = Utils.changeDateFormat("yyyy-MM-dd'T'hh:mm:ss", messageCreatedAt);
                                chatList.isDayChange = !Utils.isSameDay(lastMessageDate, date);
                                lastMessageDate = date;
                            }
                            Collections.reverse(chatMsgList);

                            activity.runOnUiThread(() -> {
                                chatMessageAdapter.notifyDataSetChanged();
                                isScrolling = false;
                            });
                        } else {
                            chatMsgList = new ArrayList<>();
                            chatMsgList.addAll(chatMsg.data.dataChatList);

                            if (count == 0) {
                                getMessageSeen();
                            }
                            activity.runOnUiThread(() -> {
                                setAdapter();
                                isScrolling = false;
                            });
                        }
                        isDataLoading = false;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    activity.runOnUiThread(() -> {
                        binding.shimmerLayout.stopShimmer();
                        binding.shimmerLayout.setVisibility(View.GONE);
                    });
                }
                if (chatMsg.data != null && chatMsg.data.project != null) {
                    project = chatMsg.data.project;
                }
            });

            activity.mSocket.on("loadSenderReceiverData", args -> {
                SenderReceiverData recSendData = new Gson().fromJson(args[0].toString(), SenderReceiverData.class);
                if (recSendData != null && recSendData.data != null) {
                    if (recSendData.data.receiverData != null) {
                        try {
                            if (recSendData.data.receiverData.isSocketOnline.equalsIgnoreCase("1")) {
                                binding.tvOnline.setText(activity.getString(R.string.active_now));
                            } else {
                                binding.tvOnline.setText(activity.getString(R.string.offline));
                            }

                            if (recSendData.data.receiverData.is_chat.equalsIgnoreCase("1")) {
                                binding.rlBottom.setVisibility(View.VISIBLE);
                                binding.view3.setVisibility(View.VISIBLE);
                            } else {
                                binding.rlBottom.setVisibility(View.INVISIBLE);
                                binding.view3.setVisibility(View.INVISIBLE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setAdapter() {
        try {
            binding.shimmerLayout.stopShimmer();
            binding.shimmerLayout.setVisibility(View.GONE);

            if (chatMsgList != null && chatMsgList.size() > 0) {
                for (ChatMessageList.DataChatList chatList : chatMsgList) {
                    String messageCreatedAt = Utils.convertDate(String.valueOf(chatList.messageCreatedAt), "yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");
                    Date date = Utils.changeDateFormat("yyyy-MM-dd'T'hh:mm:ss", messageCreatedAt);
                    chatList.isDayChange = !Utils.isSameDay(lastMessageDate, date);
                    lastMessageDate = date;
                }

                Collections.reverse(chatMsgList);

                if (chatMessageAdapter == null) {
                    chatMessageAdapter = new ChatMessageAdapter(chatMsgList, activity, ChatMessagesActivityVM.this);
                    binding.rvMessages.setAdapter(chatMessageAdapter);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        if (url.equalsIgnoreCase(API_GET_PROFILE_INFO)) {
            chatSettingDialog.dismiss();
            AgentProfile profile = AgentProfile.getProfileInfo(responseBody);
            if (profile != null) {
//                Intent i = new Intent(activity, FreelancerProfileActivity.class);
                Intent i = new Intent(activity, InfluencerProfileActivityCopy.class);
                i.putExtra(Constants.AGENT_PROFILE_DATA, profile);
                i.putExtra(Constants.SHOW_HIRE, true);
                i.putExtra("from", true);
                activity.startActivity(i);
            }
            tvViewProfile.setVisibility(View.VISIBLE);
            progressBarProfile.setVisibility(View.GONE);
        } else if (url.equalsIgnoreCase(API_JOB_DETAILS)) {
            chatSettingDialog.dismiss();
            ProjectByID project = ProjectByID.getProjectById(responseBody);

            if (project != null) {
                Intent i = new Intent(activity, ProjectDetailsActivity.class);
                i.putExtra(Constants.PROJECT, project);
                activity.startActivity(i);
            }
            tvManage.setVisibility(View.VISIBLE);
            progressBarProjectManage.setVisibility(View.GONE);
        } else if (url.equalsIgnoreCase(API_GET_CONTRACT_DETAILS + "/" + contractID) || url.equalsIgnoreCase(API_GET_CUSTOM_CONTRACT_DETAILS + "/" + contractID)) {
            ProjectGigByID project = ProjectGigByID.getProjectGigById(responseBody);

            if (project != null) {
                Intent intent = new Intent(activity, ProjectGigDetailsActivity.class);
                intent.putExtra(Constants.PROJECT_GIG, project);
                intent.putExtra("state", true);
                intent.putExtra("gigType", project.gigType);
                activity.startActivity(intent);
            }
            if (isFromManageProject) {
                tvManage.setVisibility(View.VISIBLE);
                progressBarProjectManage.setVisibility(View.GONE);
            } else {
                chatMessageAdapter.notifyDataSetChanged();
            }
        } else if (url.equalsIgnoreCase(API_GET_CUSTOM_GIG_DETAILS + "/" + gigID) || url.equalsIgnoreCase(API_GET_VIEW_OFFER_DETAILS + "/" + offerID)) {
            ExpertGigDetail expertGigDetail = ExpertGigDetail.getGigDetail(responseBody);

            if (expertGigDetail != null) {
                chatMessageAdapter.notifyDataSetChanged();
                expertGigDetail.isFromOffer = true;
                expertGigDetail.offerID = offerID;
                expertGigDetail.pk = pk;
                expertGigDetail.sk = sk;
                expertGigDetail.receiverId = receiverID;
                expertGigDetail.messageId = messageId;
                expertGigDetail.price = price;
                Intent intent = new Intent(activity, GigDetailActivity.class);
                intent.putExtra(Constants.PROJECT_DETAIL, expertGigDetail);
                intent.putExtra("gigID", gigID);
                activity.startActivity(intent);

            }
        }
        activity.isClickableView = false;
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        activity.isClickableView = false;
        if (url.equalsIgnoreCase(API_GET_PROFILE_INFO)) {
            tvViewProfile.setVisibility(View.VISIBLE);
            progressBarProfile.setVisibility(View.GONE);
        } else if (url.equalsIgnoreCase(API_JOB_DETAILS)) {
            tvManage.setVisibility(View.VISIBLE);
            progressBarProjectManage.setVisibility(View.GONE);
        }
    }

    private void senMessageAPI(String base64, String filename) {
        try {
            JSONObject jsonData = new JSONObject();
            jsonData.put("partitionKey", "#message#" + activity.getUserID() + "-" + receiverID);
            jsonData.put("message", binding.etMessage.getText().toString().trim());
            jsonData.put("senderId", activity.getUserID());
            jsonData.put("receiverId", receiverID);
            if (!TextUtils.isEmpty(base64) && !TextUtils.isEmpty(filename)) {
                JSONObject sendImages = new JSONObject();
                sendImages.put("filename", filename);
                sendImages.put("base64", base64);
                JSONArray array = new JSONArray();
                array.put(sendImages);
                jsonData.put("files", array);
            }
            activity.mSocket.emit("sendMessage", jsonData);
        } catch (JSONException e) {
            Log.d("AAAAAA", "error send message " + e.getMessage());
        }

        binding.etMessage.setText("");
    }

    public void onFileSelect(File imgPaths, String filename) {
        String imgPath = String.valueOf(imgPaths);
        String name = imgPath.substring(imgPath.lastIndexOf("/") + 1);
        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute(String.valueOf(imgPaths), filename, name);
    }

    private void getHistoryError() {
        activity.mSocket.on("inValidMessageHistoryRequest", args -> {
            activity.runOnUiThread(() -> activity.toastMessage(args[0].toString()));
            isDataLoading = false;
        });
    }

    public void getNewMessage() {
        activity.mSocket.on("getMessage", args -> {
            activity.runOnUiThread(() -> {
                ChatMessageList.DataChatList newMessage = new Gson().fromJson(args[0].toString(), ChatMessageList.DataChatList.class);
                try {
                    if ((!newMessage.self && this.receiverID != Integer.parseInt(newMessage.senderId))
                            || (newMessage.self && activity.getUserID() != Integer.parseInt(newMessage.senderId))) {
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (tempNewMessage != null) {//this code is to remove duplication case in case of file send
                    if (newMessage.messageId.equals(tempNewMessage.messageId)) {
                        return;
                    }
                }
                tempNewMessage = newMessage;

                if (chatMsgList == null) {
                    chatMsgList = new ArrayList<>();
                }

                String messageCreatedAt = Utils.convertDate(String.valueOf(newMessage.messageCreatedAt), "yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");
                Date date = Utils.changeDateFormat("yyyy-MM-dd'T'hh:mm:ss", messageCreatedAt);
                newMessage.isDayChange = !Utils.isSameDay(lastMessageDate, date);
                lastMessageDate = date;
                chatMsgList.add(0, newMessage);
                if (activity.getUserID() == Integer.parseInt(newMessage.receiverId)) {
                    getMessageSeen();
                }
                if (chatMessageAdapter != null)
                    chatMessageAdapter.notifyItemInserted(0);
                else {
                    chatMessageAdapter = new ChatMessageAdapter(chatMsgList, activity, ChatMessagesActivityVM.this);
                    binding.rvMessages.setAdapter(chatMessageAdapter);
                }

                if (firstVisibleItemPosition >= 0 && firstVisibleItemPosition < 5) {
                    binding.rvMessages.scrollToPosition(0);
                } else {
                    count++;
                    binding.tvNewMessageCount.setVisibility(View.VISIBLE);
                    binding.tvNewMessageCount.setText(count + "");
                }
            });

            if (totalSize == 0) {
                hideProgress();
            }
        });
    }

    private void invalidNewMessage() {
        activity.mSocket.on("inValidMessage", args -> {
            activity.runOnUiThread(() -> activity.toastMessage(args[0].toString()));
        });
    }

    private void getTyping() {
        activity.mSocket.on("getTyping", args -> {
            activity.runOnUiThread(() -> {
                Typing typing = new Gson().fromJson(args[0].toString(), Typing.class);
                if (typing.type) {
                    if (typing.senderId == receiverID) {
                        binding.tvOnline.setText(activity.getString(R.string.typing));
                    }
                } else {
                    if (typing.senderId == receiverID) {
                        binding.tvOnline.setText(activity.getString(R.string.active_now));
                    }
                }
            });
        });
    }

    private void fileSavedSuccess() {
        activity.mSocket.on("fileSavedSuccess", args -> {
            activity.runOnUiThread(() -> {
            });
        });
    }

    public void showProgress(int fileSize) {
        progress = new ProgressDialog(activity);
        progress.setMessage(activity.getString(R.string.uploading_please_wait));
        progress.show();
        totalSize = fileSize;
    }

    private void hideProgress() {
        try {
            if (progress != null && progress.isShowing()) {
                progress.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void inValidOfferData() {
        activity.mSocket.on("inValidOfferData", args -> Log.e("AAAAAA", "inValidOfferData args...." + args[0].toString()));
    }

    private void failMessageWhenSend() {
        activity.mSocket.on("failMessageWhenSend", args -> Log.e("AAAAAA", "failMessageWhenSend args...." + args[0].toString()));
    }

    private void invalidFile() {
        activity.mSocket.on("invalidFile", args -> Log.e("AAAAAA", "invalidFile args...." + args[0].toString()));
    }

    @Override
    public void onClickViewDetail(ChatMessageList.DataChatList dataChatList, boolean isFromGigView) {
        offerID = dataChatList.offer.offerID;
        gigID = dataChatList.offer.gigID;
        pk = dataChatList.pK;
        sk = dataChatList.sK;
        messageId = dataChatList.messageId;
        price = dataChatList.offer.price;
        if (isFromGigView) {
            if (dataChatList.offer.gigType == 3) {
                getOfferViewDetailAPI();
            } else {
                getGigViewDetailAPI();
            }
        } else {
            isFromManageProject = false;
            if (dataChatList.offer.offerStatus == 2 || dataChatList.offer.offerStatus == 5) {
                contractID = dataChatList.offer.contractID;
                if (dataChatList.offer.gigType == 1 || dataChatList.offer.gigType == 3) {//gig
                    getCustomGigDetailAPI();
                } else {
                    getGigDetailAPI();
                }
            }
        }
    }

    private class AsyncTaskRunner extends AsyncTask<String, Integer, String> {
        private String name;
        private String base64;

        @Override
        protected String doInBackground(String... params) {
            String file = params[0];
            name = params[2];
            base64 = chatMessageAdapter.baseActivity.getStringFile(new File(file));
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            binding.progress.setVisibility(View.GONE);
            senMessageAPI(base64, name);
            totalSize = totalSize - 1;
        }

        @Override
        protected void onPreExecute() {
            binding.progress.setVisibility(View.GONE);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}
