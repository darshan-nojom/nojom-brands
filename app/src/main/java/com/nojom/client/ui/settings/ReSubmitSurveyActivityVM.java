package com.nojom.client.ui.settings;

import static android.app.Activity.RESULT_OK;
import static com.nojom.client.util.Constants.API_ADD_SOCIAL_SURVEY;
import static com.nojom.client.util.Constants.API_DELETE_SURVEY_IMG;
import static com.nojom.client.util.Constants.API_GET_SURVEY_DETAILS;
import static com.nojom.client.util.Constants.APPSTORE_URL;
import static com.nojom.client.util.Constants.FACEBOOK_URL;
import static com.nojom.client.util.Constants.GOOGLE_URL;
import static com.nojom.client.util.Constants.JABBER_URL;
import static com.nojom.client.util.Constants.PLAYSTORE_URL;
import static com.nojom.client.util.Constants.TRUSTPILOT_URL;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.style.ClickableSpan;
import android.textview.CustomTextView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ahamed.multiviewadapter.SimpleRecyclerAdapter;
import com.nojom.client.R;
import com.nojom.client.adapter.binder.SurveyFilesBinder;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivityAddSurveySubmitBinding;
import com.nojom.client.model.SocialDetailModel;
import com.nojom.client.multitypepicker.Constant;
import com.nojom.client.multitypepicker.activity.ImagePickActivity;
import com.nojom.client.multitypepicker.activity.NormalFilePickActivity;
import com.nojom.client.multitypepicker.filter.entity.ImageFile;
import com.nojom.client.multitypepicker.filter.entity.NormalFile;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.dialog.StorageDisclosureDialog;
import com.nojom.client.util.CompressFile;
import com.nojom.client.util.Utils;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

class ReSubmitSurveyActivityVM extends AndroidViewModel implements View.OnClickListener, SurveyFilesBinder.OnClickFileListener, RequestResponseListener {
    public static final String IS_NEED_CAMERA = "IsNeedCamera";
    private final ActivityAddSurveySubmitBinding binding;
    private final BaseActivity activity;
    boolean isFileDeleted;
    private ArrayList<File> fileList;
    private List<SocialDetailModel.Data> attachmentsList;
    private SimpleRecyclerAdapter myFilesAdapter;
    private int socialId;
    private String note;
    private int deletedSurveyImgPosition;
    private String filePath;

    ReSubmitSurveyActivityVM(Application application, ActivityAddSurveySubmitBinding addSurveySubmitBinding, BaseActivity reSubmitSurveyActivity) {
        super(application);
        binding = addSurveySubmitBinding;
        activity = reSubmitSurveyActivity;
        initData();
    }

    private void initData() {
        binding.imgBack.setOnClickListener(this);
        binding.btnAddSurvey.setOnClickListener(this);
        binding.txtAddFile.setOnClickListener(this);

        if (activity.getIntent() != null) {
            socialId = activity.getIntent().getIntExtra("social_id", 0);
            note = activity.getIntent().getStringExtra("note");
        }

        if (!TextUtils.isEmpty(note) && !note.equalsIgnoreCase("null")) {
            binding.txtNote.setText(note);
            binding.txtNote.setVisibility(View.VISIBLE);
        }
        getSurveyDetail();

        ClickableSpan link = new ClickableSpan() {
            @Override
            public void onClick(@NotNull View view) {
                switch (socialId) {
                    case 1:
                        rateMe(APPSTORE_URL);
                        break;
                    case 2:
                        rateMe(PLAYSTORE_URL);
                        break;
                    case 3:
                        rateMe(GOOGLE_URL);
                        break;
                    case 4:
                        rateMe(FACEBOOK_URL);
                        break;
                    case 5:
                        rateMe(TRUSTPILOT_URL);
                        break;
                    case 6:
                        rateMe(JABBER_URL);
                        break;
                }

            }
        };

        Utils.makeLinks(binding.txtLink, new String[]{activity.getString(R.string.click_here_to_open_link)}, new ClickableSpan[]{link});

        binding.txtStep1Label.setText(Utils.getColorString(activity, activity.getString(R.string.submit_survey_step_1),
                "1- " + activity.getString(R.string.click_on_a_link), R.color.black, true));
        binding.txtStep2Label.setText(Utils.getColorString(activity, activity.getString(R.string.submit_survey_step_2),
                "2- " + activity.getString(R.string.submit_a_screenshot), R.color.black, true));
        if (activity.getCurrency().equals("SAR")) {
            binding.txtStep3Label.setText(Utils.getColorString(activity, activity.getString(R.string.submit_survey_step_3_sar),
                    "3- " + activity.getString(R.string.get) + " 2 "+activity.getString(R.string.sar), R.color.black, true));
        } else {
            binding.txtStep3Label.setText(Utils.getColorString(activity, activity.getString(R.string.submit_survey_step_3),
                    "3- " + activity.getString(R.string.get) + " $2", R.color.black, true));
        }

        fileList = new ArrayList<>();
        attachmentsList = new ArrayList<>();
        binding.rvFiles.setLayoutManager(new LinearLayoutManager(activity));

    }

    private void rateMe(String url) {
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(url)));
        } catch (android.content.ActivityNotFoundException e) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(url)));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                if (isFileDeleted) {
                    Intent intent = new Intent();
                    activity.setResult(RESULT_OK, intent);
                }
                activity.onBackPressed();
                break;
            case R.id.txt_add_file:
                selectFileDialog();
                break;
            case R.id.btn_add_survey:
                checkValidation();
                break;
        }

    }

    private void checkValidation() {
        if (fileList == null || fileList.size() == 0) {
            activity.toastMessage(activity.getString(R.string.please_attach_screenshot));
            return;
        }
        submitSurvey();
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
                                    activity.openDocuments(activity, 1,4545);
                                } else {
                                    Intent intent = new Intent(activity, NormalFilePickActivity.class);
                                    intent.putExtra(Constant.MAX_NUMBER, 5);
                                    intent.putExtra(NormalFilePickActivity.SUFFIX, new String[]{"doc", "docx", "ppt", "pptx", "pdf"});
                                    activity.startActivityForResult(intent, Constant.REQUEST_CODE_PICK_FILE);
                                }
                            } else {
                                Intent intent = new Intent(activity, ImagePickActivity.class);
                                intent.putExtra(IS_NEED_CAMERA, true);
                                intent.putExtra(Constant.MAX_NUMBER, 5);
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

    void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case Constant.REQUEST_CODE_PICK_FILE:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<NormalFile> docPaths = data.getParcelableArrayListExtra(Constant.RESULT_PICK_FILE);
                    if (docPaths != null && docPaths.size() > 0) {
                        for (NormalFile file : docPaths) {
                            fileList.add(new File(file.getPath()));
                            if (attachmentsList == null) {
                                attachmentsList = new ArrayList<>();
                            }
                            SocialDetailModel.Data data1 = new SocialDetailModel.Data();
                            data1.screenshot = new File(file.getPath()).getName();
                            data1.timestamp = getDate(System.currentTimeMillis());
                            data1.id = -1;
                            attachmentsList.add(data1);
                        }
                        setFileAdapter();
                    } else {
                        activity.toastMessage(activity.getString(R.string.file_not_selected));
                    }
                }
                break;
            case 4545://doc picker for android 10+
                String path = null;
                try {
                    if (data != null && data.getData() != null) {
                        path = Utils.getFilePath(activity, data.getData());
                        if (path != null) {
                            fileList.add(new File(path));
                            if (attachmentsList == null) {
                                attachmentsList = new ArrayList<>();
                            }
                            SocialDetailModel.Data data1 = new SocialDetailModel.Data();
                            data1.screenshot = new File(path).getName();
                            data1.timestamp = getDate(System.currentTimeMillis());
                            data1.id = -1;
                            attachmentsList.add(data1);
                            setFileAdapter();
                        } else {
                            activity.toastMessage(activity.getString(R.string.file_not_selected));
                        }
                    }
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                break;
            case Constant.REQUEST_CODE_PICK_IMAGE:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<ImageFile> imgPath = data.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE);
                    if (imgPath != null && imgPath.size() > 0) {
                        for (ImageFile file : imgPath) {
                            fileList.add(new File(file.getPath()));
                            if (attachmentsList == null) {
                                attachmentsList = new ArrayList<>();
                            }
                            SocialDetailModel.Data data1 = new SocialDetailModel.Data();
                            data1.screenshot = new File(file.getPath()).getName();
                            data1.timestamp = getDate(System.currentTimeMillis());
                            data1.id = -1;
                            attachmentsList.add(data1);
                        }
                        setFileAdapter();
                    } else {
                        activity.toastMessage(activity.getString(R.string.file_not_selected));
                    }
                }
                break;
        }
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time * 1000);
        return DateFormat.format("yyyy-MM-dd'T'hh:mm:ss", cal).toString();
    }

    private void submitSurvey() {
        try {
            if (!activity.isNetworkConnected())
                return;

            binding.btnAddSurvey.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);
            activity.isClickableView = true;

            MultipartBody.Part[] body = null;
            if (fileList != null && fileList.size() > 0) {
                body = new MultipartBody.Part[fileList.size()];
                for (int i = 0; i < fileList.size(); i++) {
                    File file;
                    if (fileList.get(i) == null) {
                        return;
                    }
                    if (fileList.get(i).getAbsolutePath().contains(".png") || fileList.get(i).getAbsolutePath().contains(".jpg") || fileList.get(i).getAbsolutePath().contains(".jpeg")) {
                        file = CompressFile.getCompressedImageFile(fileList.get(i), activity);
                    } else {
                        file = fileList.get(i);
                    }
                    Uri selectedUri = Uri.fromFile(file);
                    String fileExtension = MimeTypeMap.getFileExtensionFromUrl(selectedUri.toString());
                    String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase());

                    RequestBody requestFile = null;
                    if (mimeType != null) {
                        requestFile = RequestBody.create(file, MediaType.parse(mimeType));
                    }

                    Headers.Builder headers = new Headers.Builder();
                    headers.addUnsafeNonAscii("Content-Disposition", "form-data; name=\"screenshot[]\"; filename=\"" + file.getName() + "\"");

                    if (requestFile != null) {
                        body[i] = MultipartBody.Part.create(headers.build(), requestFile);
                    }
                }
            }

            HashMap<String, String> map = new HashMap<>();
            map.put("social_media", this.socialId + "");

            ApiRequest apiRequest = new ApiRequest();
            apiRequest.apiImageUploadRequest(this, activity, API_ADD_SOCIAL_SURVEY, body, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setFileAdapter() {
        if (attachmentsList != null && attachmentsList.size() > 0) {
            if (myFilesAdapter == null) {
                SurveyFilesBinder binder = new SurveyFilesBinder();
                binder.setListener(ReSubmitSurveyActivityVM.this);
                binder.setFilePath(filePath);
                myFilesAdapter = new SimpleRecyclerAdapter<>(binder);
            }

            if (binding.rvFiles.getAdapter() == null) {
                binding.rvFiles.setAdapter(myFilesAdapter);
            }

            myFilesAdapter.setData(attachmentsList);
            binding.rvFiles.setVisibility(View.VISIBLE);
        } else {
            binding.rvFiles.setVisibility(View.GONE);
        }
    }

    private void getSurveyDetail() {
        if (!activity.isNetworkConnected())
            return;

        HashMap<String, String> map = new HashMap<>();
        map.put("social_media", socialId + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_SURVEY_DETAILS, true, map);
    }

    private void setStatus(int status, CustomTextView txtStatus) {
        switch (status) {
            case 1:
                txtStatus.setText(activity.getString(R.string.deposited));
                txtStatus.setBackground(activity.getResources().getDrawable(R.drawable.green_rounded_corner_20));
                txtStatus.setTextColor(activity.getResources().getColor(R.color.white));
                break;
            case 2:
                txtStatus.setText(activity.getString(R.string.under_review));
                txtStatus.setBackground(activity.getResources().getDrawable(R.drawable.orangelight_bg_20));
                txtStatus.setTextColor(activity.getResources().getColor(R.color.white));
                break;
            case 3:
                txtStatus.setText(activity.getString(R.string.rejected));
                txtStatus.setBackground(activity.getResources().getDrawable(R.drawable.red_bg_20));
                txtStatus.setTextColor(activity.getResources().getColor(R.color.white));
                break;
            default:
                txtStatus.setText(activity.getString(R.string.not_started));
                txtStatus.setBackground(activity.getResources().getDrawable(R.drawable.gray_rounded_corner_20));
                txtStatus.setTextColor(activity.getResources().getColor(R.color.tab_gray));
        }
    }

    @Override
    public void onClickDeleteFile(SocialDetailModel.Data data) {
        if (attachmentsList != null && attachmentsList.size() > 0) {
            for (int i = 0; i < attachmentsList.size(); i++) {
                if (attachmentsList.get(i).id == data.id) {
                    deleteFile(data, i);
                    break;
                }
            }
        }
    }

    private void deleteFile(SocialDetailModel.Data data, int pos) {
        if (!activity.isNetworkConnected())
            return;

        if (data.id == -1) {//means file is locally, so no need to call API, delete file from locally
            for (int i = 0; i < fileList.size(); i++) {
                if (fileList.get(i).getName().equals(data.screenshot)) {
                    fileList.remove(i);
                    break;
                }
            }
            attachmentsList.remove(pos);
            setFileAdapter();
            return;
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("social_survey_id", data.id + "");
        deletedSurveyImgPosition = pos;

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_DELETE_SURVEY_IMG, true, map);
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        if (url.equalsIgnoreCase(API_GET_SURVEY_DETAILS)) {
            SocialDetailModel socialDetailModel = SocialDetailModel.getSurveyDetails(responseBody);
            if (attachmentsList == null) {
                attachmentsList = new ArrayList<>();
            }
            if (socialDetailModel != null && socialDetailModel.data != null && socialDetailModel.data.size() > 0) {
                attachmentsList.addAll(socialDetailModel.data);
                int status = socialDetailModel.data.get(0).surveyStatus;
                setStatus(status, binding.tvStatus);
            }
            filePath = socialDetailModel != null ? socialDetailModel.path : "";
            setFileAdapter();
        } else if (url.equalsIgnoreCase(API_DELETE_SURVEY_IMG)) {
            activity.toastMessage(message);
            attachmentsList.remove(deletedSurveyImgPosition);
            setFileAdapter();
            if (attachmentsList != null && attachmentsList.size() == 0) {
                isFileDeleted = true;
            }
            setFileAdapter();
        } else if (url.equalsIgnoreCase(API_ADD_SOCIAL_SURVEY)) {
            activity.toastMessage(message);
            binding.btnAddSurvey.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.GONE);

            Intent intent = new Intent();
            activity.setResult(RESULT_OK, intent);
            activity.finish();
        }
        activity.isClickableView = false;
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        if (url.equalsIgnoreCase(API_ADD_SOCIAL_SURVEY)) {
            binding.btnAddSurvey.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.GONE);
        }
        activity.isClickableView = false;
    }
}
