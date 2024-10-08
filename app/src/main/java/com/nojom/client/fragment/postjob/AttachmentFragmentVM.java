package com.nojom.client.fragment.postjob;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nojom.client.databinding.FragmentAttachmentBinding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.nojom.client.R;
import com.nojom.client.adapter.UploadFileAdapter;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.model.Attachment;
import com.nojom.client.model.ProjectByID;
import com.nojom.client.multitypepicker.Constant;
import com.nojom.client.multitypepicker.activity.ImagePickActivity;
import com.nojom.client.multitypepicker.activity.NormalFilePickActivity;
import com.nojom.client.multitypepicker.filter.entity.ImageFile;
import com.nojom.client.multitypepicker.filter.entity.NormalFile;
import com.nojom.client.ui.clientprofile.PostJobActivity;
import com.nojom.client.ui.dialog.StorageDisclosureDialog;
import com.nojom.client.ui.projects.ProjectDetailsActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.nojom.client.multitypepicker.activity.VideoPickActivity.IS_NEED_CAMERA;

class AttachmentFragmentVM extends AndroidViewModel implements UploadFileAdapter.OnFileDeleteListener, View.OnClickListener {
    private FragmentAttachmentBinding binding;
    private BaseFragment fragment;
    private UploadFileAdapter uploadFileAdapter;
    private String filesList = "";
    private ArrayList<Attachment> fileList = new ArrayList<>();
    private String moServiceID, moSkilIDs, moSkillNames, moBudget, moClientRate, moDescribe, selectedDeadline, payType, lawyerService;
    private int moClientRateId;
    private ProjectByID projectData;

    AttachmentFragmentVM(Application application, FragmentAttachmentBinding attachmentBinding, BaseFragment attachmentFragment) {
        super(application);
        binding = attachmentBinding;
        fragment = attachmentFragment;

        try {//need in case of Edit or duplicate job
            if ((fragment.activity) != null && ((ProjectDetailsActivity) fragment.activity).getProjectData() != null) {
                projectData = ((ProjectDetailsActivity) fragment.activity).getProjectData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (fragment.getArguments() != null) {
            moServiceID = fragment.getArguments().getString(Constants.PLATFORM_ID);
            moSkilIDs = fragment.getArguments().getString(Constants.SKILL_IDS);
            moSkillNames = fragment.getArguments().getString(Constants.SKILL_NAMES);
            moClientRateId = fragment.getArguments().getInt(Constants.CLIENT_RATE_ID);
            moClientRate = fragment.getArguments().getString(Constants.CLIENT_RATE);
            moBudget = fragment.getArguments().getString(Constants.BUDGET);
            moDescribe = fragment.getArguments().getString(Constants.DESCRIBE);
            selectedDeadline = fragment.getArguments().getString("deadline");
            payType = fragment.getArguments().getString(Constants.PAY_TYPE);
            lawyerService = fragment.getArguments().getString(Constants.PLATFORM_NAME);
        }

        initData();
    }

    private void initData() {
        binding.toolbar.imgBack.setOnClickListener(this);
        binding.tvAttachFile.setOnClickListener(this);
        binding.toolbar.tvNext.setOnClickListener(this);
        binding.tvSkip.setOnClickListener(this);

        filesList = "";
        fileList.clear();
        fileList = new ArrayList<>();

        binding.rvFiles.setLayoutManager(new LinearLayoutManager(fragment.activity));

        Utils.trackAppsFlayerEvent(fragment.activity, "File_attachment_Screen");

    }

    @Override
    public void onClick(View view) {

        Utils.hideSoftKeyboard(fragment.activity);
        switch (view.getId()) {
            case R.id.img_back:
                fragment.goBackTo();
                break;
            case R.id.tv_next:
                if (!TextUtils.isEmpty(filesList)) {
                    changeFragment();
                } else
                    fragment.activity.validationError(fragment.activity.getString(R.string.please_select_file));
                break;
            case R.id.tv_attach_file:
                selectFileDialog();
                break;
            case R.id.tv_skip:
                filesList = "";
                fileList = new ArrayList<>();
                binding.rvFiles.setVisibility(View.GONE);
                Preferences.writeString(fragment.activity, Constants.ATTACH_LOCAL_FILE, "");
                changeFragment();
                break;
        }
    }

    private void changeFragment() {
        try {
            Fragment fragmentA = new PostJobFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constants.PLATFORM_ID, moServiceID + "");
            bundle.putString(Constants.SKILL_IDS, moSkilIDs + "");
            bundle.putString(Constants.SKILL_NAMES, moSkillNames + "");
            bundle.putString(Constants.BUDGET, moBudget);
            bundle.putInt(Constants.CLIENT_RATE_ID, moClientRateId);
            bundle.putString(Constants.CLIENT_RATE, moClientRate);
            bundle.putString(Constants.DESCRIBE, moDescribe);
            bundle.putString("deadline", selectedDeadline);
            bundle.putString(Constants.ATTACH_LOCAL_FILE, TextUtils.isEmpty(filesList) ? "" : filesList);
            bundle.putString(Constants.PAY_TYPE, payType);
            bundle.putString(Constants.PLATFORM_NAME, lawyerService);
            fragmentA.setArguments(bundle);
            fragment.activity.replaceFragment(fragmentA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setFileAdapter(ArrayList<Attachment> uploadedfiles) {
        if (uploadedfiles != null && uploadedfiles.size() > 0) {
            binding.rvFiles.setVisibility(View.VISIBLE);
            filesList = "";
            for (Attachment file : uploadedfiles) {
                if (TextUtils.isEmpty(filesList)) {
                    filesList = file.filepath;
                } else {
                    filesList = filesList + "," + file.filepath;
                }
            }
            Preferences.writeString(fragment.activity, Constants.ATTACH_LOCAL_FILE, filesList);
            if (uploadFileAdapter == null) {
                uploadFileAdapter = new UploadFileAdapter(fragment.activity, projectData != null ? projectData.attachmentPath : "");
                uploadFileAdapter.setOnFileDeleteListener(this);
            }

            uploadFileAdapter.doRefresh(uploadedfiles);

            if (binding.rvFiles.getAdapter() == null) {
                binding.rvFiles.setAdapter(uploadFileAdapter);
            }
        } else {
            filesList = "";
            binding.rvFiles.setVisibility(View.GONE);
        }
    }

    private void selectFileDialog() {
        @SuppressLint("PrivateResource") final Dialog dialog = new Dialog(fragment.activity, R.style.Theme_Design_Light_BottomSheetDialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_camera_document_select);
        dialog.setCancelable(true);
        TextView tvCancel = dialog.findViewById(R.id.btn_cancel);
        LinearLayout llCamera = dialog.findViewById(R.id.ll_camera);
        LinearLayout llDocument = dialog.findViewById(R.id.ll_document);

        llCamera.setOnClickListener(v -> {
            dialog.dismiss();
            if (fragment.activity.checkStoragePermission()) {
                checkPermission(false);
            } else {
                new StorageDisclosureDialog(fragment.activity, () -> checkPermission(false));
            }
        });

        llDocument.setOnClickListener(v -> {
            dialog.dismiss();
            if (fragment.activity.checkStoragePermission()) {
                checkPermission(true);
            } else {
                new StorageDisclosureDialog(fragment.activity, () -> checkPermission(true));
            }
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
        try {
            Dexter.withActivity(fragment.activity)
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
                                        fragment.activity.openDocuments(fragment, 1);
                                    } else {
                                        Intent intent = new Intent(fragment.activity, NormalFilePickActivity.class);
                                        intent.putExtra(Constant.MAX_NUMBER, 5);
                                        intent.putExtra(NormalFilePickActivity.SUFFIX, new String[]{"doc", "docx", "ppt", "pptx", "pdf"});
                                        fragment.startActivityForResult(intent, Constant.REQUEST_CODE_PICK_FILE);
                                    }
                                } else {
                                    Intent intent = new Intent(fragment.activity, ImagePickActivity.class);
                                    intent.putExtra(IS_NEED_CAMERA, true);
                                    intent.putExtra(Constant.MAX_NUMBER, 5);
                                    intent.putExtra("rCode", Constant.REQUEST_CODE_PICK_IMAGE);
                                    fragment.startActivityForResult(intent, Constant.REQUEST_CODE_PICK_IMAGE);
                                }
                            }

                            if (report.isAnyPermissionPermanentlyDenied()) {
                                fragment.activity.toastMessage(fragment.activity.getString(R.string.please_give_permission));
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    })
                    .onSameThread()
                    .check();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case Constant.REQUEST_CODE_PICK_FILE:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    ArrayList<NormalFile> docPaths = data.getParcelableArrayListExtra(Constant.RESULT_PICK_FILE);
                    if (docPaths != null && docPaths.size() > 0) {
                        for (NormalFile file : docPaths) {
                            fileList.add(new Attachment(file.getPath(), "", "", false));
                        }
                        setFileAdapter(fileList);
                    } else {
                        fragment.activity.toastMessage(fragment.activity.getString(R.string.please_select_file));
                    }
                }
                break;
            case 4545://doc picker for android 10+
                String path = null;
                try {
                    if (data != null && data.getData() != null) {
                        path = Utils.getFilePath(fragment.activity, data.getData());
                        if (path != null) {
                            fileList.add(new Attachment(path, "", "", false));
                            setFileAdapter(fileList);
                        } else {
                            fragment.activity.toastMessage(fragment.activity.getString(R.string.please_select_file));
                        }
                    }
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                break;
            case Constant.REQUEST_CODE_PICK_IMAGE:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    ArrayList<ImageFile> imgPath = data.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE);
                    if (imgPath != null && imgPath.size() > 0) {
                        for (ImageFile file : imgPath) {
                            fileList.add(new Attachment(file.getPath(), "", "", true));
                        }
                        setFileAdapter(fileList);
                    } else {
                        fragment.activity.toastMessage(fragment.activity.getString(R.string.please_select_file));
                    }
                }
                break;
        }
    }

    @Override
    public void onFileDelete(ArrayList<Attachment> mDataset) {
        setFileAdapter(mDataset);
    }

    void onResumeMethod() {
        ((PostJobActivity) fragment.activity).setProgressView(1.0f);
        filesList = "";
        filesList = Preferences.readString(fragment.activity, Constants.ATTACH_LOCAL_FILE, "");
        if (!TextUtils.isEmpty(filesList)) {
            fileList = new ArrayList<>();
            String[] filesSplit = filesList.split(",");
            for (String aFilesSplit : filesSplit) {
                if (aFilesSplit.contains("png") || aFilesSplit.contains("jpg") || aFilesSplit.contains("jpeg")) {
                    fileList.add(new Attachment(aFilesSplit, "", "", true));
                } else {
                    fileList.add(new Attachment(aFilesSplit, "", "", false));
                }
            }
            setFileAdapter(fileList);
        } else {
            filesList = "";
            fileList.clear();
            fileList = new ArrayList<>();
            binding.rvFiles.setVisibility(View.GONE);
        }
    }
}
