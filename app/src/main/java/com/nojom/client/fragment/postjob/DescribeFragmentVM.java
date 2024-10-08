package com.nojom.client.fragment.postjob;

import static android.app.Activity.RESULT_OK;
import static com.nojom.client.multitypepicker.activity.ImagePickActivity.IS_NEED_CAMERA;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.AndroidViewModel;

import com.nojom.client.databinding.FragmentDescribeBinding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.nojom.client.R;
import com.nojom.client.adapter.UploadFileAdapter;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.model.Attachment;
import com.nojom.client.multitypepicker.Constant;
import com.nojom.client.multitypepicker.activity.ImagePickActivity;
import com.nojom.client.multitypepicker.activity.NormalFilePickActivity;
import com.nojom.client.multitypepicker.filter.entity.ImageFile;
import com.nojom.client.multitypepicker.filter.entity.NormalFile;
import com.nojom.client.ui.clientprofile.PostJobActivity;
import com.nojom.client.ui.dialog.StorageDisclosureDialog;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class DescribeFragmentVM extends AndroidViewModel implements View.OnClickListener, UploadFileAdapter.OnFileDeleteListener {
    private FragmentDescribeBinding binding;
    private BaseFragment fragment;
    private String moServiceID, moSkilIDs, moSkillNames, moBudget, moClientRate, selectedDeadline, payType, lawyerService;
    private int moClientRateId;
    private String filesList = "", description;
    private ArrayList<Attachment> fileList = new ArrayList<>();
    private UploadFileAdapter uploadFileAdapter;

    DescribeFragmentVM(Application application, FragmentDescribeBinding describeBinding, BaseFragment describeFragment) {
        super(application);
        binding = describeBinding;
        fragment = describeFragment;
        initData();
    }

    private void initData() {
        binding.btnLastStep.setOnClickListener(this);
        binding.rlAttachFile.setOnClickListener(this);

        binding.tvTitle.setText(Utils.getColorString(fragment.activity, fragment.getString(R.string.describe_what_you_need),
                fragment.getString(R.string.describe_1), R.color.colorPrimary));
        learnMoreClick();

        if (fragment.getArguments() != null) {
            moServiceID = fragment.getArguments().getString(Constants.PLATFORM_ID);
            moSkilIDs = fragment.getArguments().getString(Constants.SKILL_IDS);
            moSkillNames = fragment.getArguments().getString(Constants.SKILL_NAMES);
            moClientRateId = fragment.getArguments().getInt(Constants.CLIENT_RATE_ID);
            moClientRate = fragment.getArguments().getString(Constants.CLIENT_RATE);
            moBudget = fragment.getArguments().getString(Constants.BUDGET);
            selectedDeadline = fragment.getArguments().getString("deadline");
            description = fragment.getArguments().getString(Constants.DESCRIBE);

            payType = fragment.getArguments().getString(Constants.PAY_TYPE);
            lawyerService = fragment.getArguments().getString(Constants.PLATFORM_NAME);
        }

        binding.etDescribe.setText(description);

        binding.etDescribe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (TextUtils.isEmpty(s)) {
                        binding.etDescribe.setGravity(Gravity.TOP);
                        binding.etDescribe.setHint(fragment.getResources().getString(R.string.describe_hint_text));
                        binding.tvWordCounter.setText("0/5000");
                        binding.tvWordPerfect.setVisibility(View.INVISIBLE);
                    } else {
                        binding.etDescribe.setHint(null);
                        int length = s.toString().trim().length();
                        binding.tvWordCounter.setText(length + "/5000");
                        if (length >= 50) {
                            binding.tvWordPerfect.setVisibility(View.VISIBLE);
                        } else {
                            binding.tvWordPerfect.setVisibility(View.INVISIBLE);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void afterTextChanged(final Editable s) {
            }
        });

        Utils.trackAppsFlayerEvent(fragment.activity, "Project_Description");

        ((PostJobActivity) fragment.activity).getBackView().setOnClickListener(view -> {
            goBack();
        });
    }

    private void learnMoreClick() {
        AtomicBoolean isExpand = new AtomicBoolean(true);
        binding.txtLearnMore.setOnClickListener(view -> {
            if (isExpand.get()) {
                isExpand.set(false);
                binding.imgLearnMore.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                binding.imgLearnMore.setRotation(360);
                binding.txtLearnMoreDesc.setVisibility(View.VISIBLE);
            } else {
                isExpand.set(true);
                binding.imgLearnMore.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                binding.imgLearnMore.setRotation(180);
                binding.txtLearnMoreDesc.setVisibility(View.GONE);
            }
        });
    }


    void onResumeMethod() {
        ((PostJobActivity) fragment.activity).hideNextView();
        ((PostJobActivity) fragment.activity).setProgressView(1f);

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

    private boolean isValid() {
        if (TextUtils.isEmpty(getDescription())) {
            fragment.activity.validationError(fragment.activity.getString(R.string.please_enter_description));
            return false;
        }

        if (getDescription().length() < 50) {
            fragment.activity.validationError(fragment.activity.getString(R.string.please_add_your_project_description_more_than_50_characters));
            return false;
        }
        return true;
    }


    private String getDescription() {
        return binding.etDescribe.getText().toString().trim();
    }

    @Override
    public void onClick(View view) {
        Utils.hideSoftKeyboard(fragment.activity);
        switch (view.getId()) {
            case R.id.rl_attach_file:
                selectFileDialog();
                break;
            case R.id.btn_last_step:
                if (isValid()) {
                    Pattern pattern = Pattern.compile("-?\\d+");
                    Matcher m = pattern.matcher(binding.etDescribe.getText().toString().trim());
                    while (m.find()) {
                        if (m.group().length() >= 6 && m.group().length() <= 14) {
                            fragment.activity.validationError(fragment.activity.getString(R.string.you_cannot_enter_number));
                            return;
                        }
                    }

                    pattern = Pattern.compile("([a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+)");
                    m = pattern.matcher(binding.etDescribe.getText().toString().trim());
                    while (m.find()) {
                        fragment.activity.validationError(fragment.activity.getString(R.string.you_cannot_enter_email));
                        return;
                    }

//                    Preferences.writeString(fragment.activity, Constants.ATTACH_LOCAL_FILE, "");

                    Intent intent = new Intent();
                    intent.putExtra(Constants.PLATFORM_ID, moServiceID + "");
                    intent.putExtra(Constants.SKILL_IDS, moSkilIDs + "");
                    intent.putExtra(Constants.SKILL_NAMES, moSkillNames + "");
                    intent.putExtra(Constants.BUDGET, moBudget);
                    intent.putExtra(Constants.CLIENT_RATE_ID, moClientRateId);
                    intent.putExtra(Constants.CLIENT_RATE, moClientRate);
                    intent.putExtra(Constants.DESCRIBE, binding.etDescribe.getText().toString().trim());
                    intent.putExtra("deadline", selectedDeadline);
                    intent.putExtra(Constants.ATTACH_LOCAL_FILE, TextUtils.isEmpty(filesList) ? "" : filesList);
                    intent.putExtra(Constants.PAY_TYPE, payType);
                    intent.putExtra(Constants.PLATFORM_NAME, lawyerService);
                    fragment.activity.setResult(RESULT_OK, intent);
                    fragment.activity.finish();

                }
                break;
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
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<NormalFile> docPaths = data.getParcelableArrayListExtra(Constant.RESULT_PICK_FILE);
                    if (docPaths != null && docPaths.size() > 0) {
                        for (NormalFile file : docPaths) {
                            fileList.add(new Attachment(file.getPath(), "", "", false));
                        }
                        setFileAdapter(fileList);
                    } else {
                        fragment.activity.toastMessage(fragment.activity.getString(R.string.file_not_selected));
                    }
                }
                break;
            case 4545://doc picker for android 10+
                String path = null;
                try {
                    path = Utils.getFilePath(fragment.activity, data.getData());
                    if (path != null) {
                        fileList.add(new Attachment(path, "", "", false));
                        setFileAdapter(fileList);
                    } else {
                        fragment.activity.toastMessage(fragment.activity.getString(R.string.file_not_selected));
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
                            fileList.add(new Attachment(file.getPath(), "", "", true));
                        }
                        setFileAdapter(fileList);
                    } else {
                        fragment.activity.toastMessage(fragment.activity.getString(R.string.file_not_selected));
                    }
                }
                break;
        }
    }

    @Override
    public void onFileDelete(ArrayList<Attachment> mDataset) {
        setFileAdapter(mDataset);
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
                uploadFileAdapter = new UploadFileAdapter(fragment.activity, "");
                uploadFileAdapter.setOnFileDeleteListener(this);
            }

            uploadFileAdapter.doRefresh(uploadedfiles);

            if (binding.rvFiles.getAdapter() == null) {
                binding.rvFiles.setAdapter(uploadFileAdapter);
            }
        } else {
            filesList = "";
            Preferences.writeString(fragment.activity, Constants.ATTACH_LOCAL_FILE, filesList);
            binding.rvFiles.setVisibility(View.GONE);
        }
    }

    public void goBack() {
        Intent intent = new Intent();
        intent.putExtra(Constants.PLATFORM_ID, moServiceID + "");
        intent.putExtra(Constants.SKILL_IDS, moSkilIDs + "");
        intent.putExtra(Constants.SKILL_NAMES, moSkillNames + "");
        intent.putExtra(Constants.BUDGET, moBudget);
        intent.putExtra(Constants.CLIENT_RATE_ID, moClientRateId);
        intent.putExtra(Constants.CLIENT_RATE, moClientRate);
        intent.putExtra(Constants.DESCRIBE, binding.etDescribe.getText().toString().trim());
        intent.putExtra("deadline", selectedDeadline);
        intent.putExtra(Constants.ATTACH_LOCAL_FILE, TextUtils.isEmpty(filesList) ? "" : filesList);
        intent.putExtra(Constants.PAY_TYPE, payType);
        intent.putExtra(Constants.PLATFORM_NAME, lawyerService);
        fragment.activity.setResult(RESULT_OK, intent);
        ((PostJobActivity) fragment.activity).finish();
    }
}
