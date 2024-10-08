package com.nojom.client.ui.projects;

import static android.app.Activity.RESULT_OK;
import static com.nojom.client.multitypepicker.activity.ImagePickActivity.IS_NEED_CAMERA;
import static com.nojom.client.util.Constants.AGENT_PROFILE_DATA;

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

import com.nojom.client.R;
import com.nojom.client.adapter.UploadFileAdapter;
import com.nojom.client.databinding.FragmentDescribeBinding;
import com.nojom.client.model.AgentProfile;
import com.nojom.client.model.Attachment;
import com.nojom.client.multitypepicker.Constant;
import com.nojom.client.multitypepicker.activity.ImagePickActivity;
import com.nojom.client.multitypepicker.activity.NormalFilePickActivity;
import com.nojom.client.multitypepicker.filter.entity.ImageFile;
import com.nojom.client.multitypepicker.filter.entity.NormalFile;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.clientprofile.PostJobActivity;
import com.nojom.client.ui.dialog.StorageDisclosureDialog;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class HireDescribeActivityVM extends AndroidViewModel implements View.OnClickListener, UploadFileAdapter.OnFileDeleteListener {
    private FragmentDescribeBinding binding;
    private BaseActivity fragment;
    private String moServiceID, moSkilIDs, moSkillNames, moBudget, moClientRate, selectedDeadline, payType, lawyerService;
    private int moClientRateId;
    private String filesList = "", description;
    private ArrayList<Attachment> fileList = new ArrayList<>();
    private UploadFileAdapter uploadFileAdapter;
    private AgentProfile agentData;

    HireDescribeActivityVM(Application application, FragmentDescribeBinding describeBinding, BaseActivity describeFragment) {
        super(application);
        binding = describeBinding;
        fragment = describeFragment;
        initData();
    }

    private void initData() {
        binding.btnLastStep.setOnClickListener(this);
        binding.rlAttachFile.setOnClickListener(this);
        binding.imgBack.setOnClickListener(this);
        binding.header.setVisibility(View.VISIBLE);
        binding.llProgress.setVisibility(View.VISIBLE);
        binding.btnLastStep.setText(fragment.getString(R.string.next));

        agentData = (AgentProfile) fragment.getIntent().getSerializableExtra(AGENT_PROFILE_DATA);

        binding.tvTitle.setText(Utils.getColorString(fragment, fragment.getString(R.string.describe_what_you_need),
                fragment.getString(R.string.describe_1), R.color.colorPrimary));
        learnMoreClick();

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
        setProgressView(0.25f);
    }

    void setProgressView(float progress) {
        binding.llProgress.setVisibility(View.VISIBLE);

        float remainProgress = 1.0f - progress;
        LinearLayout.LayoutParams lParams = (LinearLayout.LayoutParams) binding.progressView.getLayoutParams();
        lParams.weight = progress;
        LinearLayout.LayoutParams rParams = (LinearLayout.LayoutParams) binding.blankView.getLayoutParams();
        rParams.weight = remainProgress;

        binding.progressView.setLayoutParams(lParams);
        binding.blankView.setLayoutParams(rParams);
    }

    void hideProgressView() {
        binding.llProgress.setVisibility(View.GONE);
    }

    private boolean isValid() {
        if (TextUtils.isEmpty(getDescription())) {
            fragment.validationError(fragment.getString(R.string.please_enter_description));
            return false;
        }

        if (getDescription().length() < 50) {
            fragment.validationError(fragment.getString(R.string.please_add_your_project_description_more_than_50_characters));
            return false;
        }
        return true;
    }


    private String getDescription() {
        return binding.etDescribe.getText().toString().trim();
    }

    @Override
    public void onClick(View view) {
        Utils.hideSoftKeyboard(fragment);
        switch (view.getId()) {
            case R.id.img_back:
                fragment.finish();
                break;
            case R.id.rl_attach_file:
                selectFileDialog();
                break;
            case R.id.btn_last_step:
                if (isValid()) {
                    Pattern pattern = Pattern.compile("-?\\d+");
                    Matcher m = pattern.matcher(binding.etDescribe.getText().toString().trim());
                    while (m.find()) {
                        if (m.group().length() >= 6 && m.group().length() <= 14) {
                            fragment.validationError(fragment.getString(R.string.you_cannot_enter_number));
                            return;
                        }
                    }

                    pattern = Pattern.compile("([a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+)");
                    m = pattern.matcher(binding.etDescribe.getText().toString().trim());
                    while (m.find()) {
                        fragment.validationError(fragment.getString(R.string.you_cannot_enter_email));
                        return;
                    }

                    Preferences.writeString(fragment, Constants.ATTACH_LOCAL_FILE, "");

                    Intent intent = new Intent(fragment, HirePriceRateActivity.class);
                    intent.putExtra(Constants.DESCRIBE, binding.etDescribe.getText().toString().trim());
                    intent.putExtra(Constants.ATTACH_LOCAL_FILE, TextUtils.isEmpty(filesList) ? "" : filesList);
                    intent.putExtra(Constants.AGENT_PROFILE_DATA, agentData);
                    fragment.startActivity(intent);
                }
                break;
        }
    }

    private void selectFileDialog() {
        @SuppressLint("PrivateResource") final Dialog dialog = new Dialog(fragment, R.style.Theme_Design_Light_BottomSheetDialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_camera_document_select);
        dialog.setCancelable(true);
        TextView tvCancel = dialog.findViewById(R.id.btn_cancel);
        LinearLayout llCamera = dialog.findViewById(R.id.ll_camera);
        LinearLayout llDocument = dialog.findViewById(R.id.ll_document);

        llCamera.setOnClickListener(v -> {
            dialog.dismiss();
            if (fragment.checkStoragePermission()) {
                checkPermission(false);
            } else {
                new StorageDisclosureDialog(fragment, () -> checkPermission(false));
            }
        });

        llDocument.setOnClickListener(v -> {
            dialog.dismiss();
            if (fragment.checkStoragePermission()) {
                checkPermission(true);
            } else {
                new StorageDisclosureDialog(fragment, () -> checkPermission(true));
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
            Dexter.withActivity(fragment)
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
                                        fragment.openDocuments(fragment, 1,4545);
                                    } else {
                                        Intent intent = new Intent(fragment, NormalFilePickActivity.class);
                                        intent.putExtra(Constant.MAX_NUMBER, 5);
                                        intent.putExtra(NormalFilePickActivity.SUFFIX, new String[]{"doc", "docx", "ppt", "pptx", "pdf"});
                                        fragment.startActivityForResult(intent, Constant.REQUEST_CODE_PICK_FILE);
                                    }
                                } else {
                                    Intent intent = new Intent(fragment, ImagePickActivity.class);
                                    intent.putExtra(IS_NEED_CAMERA, true);
                                    intent.putExtra(Constant.MAX_NUMBER, 5);
                                    intent.putExtra("rCode", Constant.REQUEST_CODE_PICK_IMAGE);
                                    fragment.startActivityForResult(intent, Constant.REQUEST_CODE_PICK_IMAGE);
                                }
                            }

                            if (report.isAnyPermissionPermanentlyDenied()) {
                                fragment.toastMessage(fragment.getString(R.string.please_give_permission));
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
                        fragment.toastMessage(fragment.getString(R.string.file_not_selected));
                    }
                }
                break;
            case 4545://doc picker for android 10+
                String path = null;
                try {
                    path = Utils.getFilePath(fragment, data.getData());
                    if (path != null) {
                        fileList.add(new Attachment(path, "", "", false));
                        setFileAdapter(fileList);
                    } else {
                        fragment.toastMessage(fragment.getString(R.string.file_not_selected));
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
                        fragment.toastMessage(fragment.getString(R.string.file_not_selected));
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
            Preferences.writeString(fragment, Constants.ATTACH_LOCAL_FILE, filesList);
            if (uploadFileAdapter == null) {
                uploadFileAdapter = new UploadFileAdapter(fragment, "");
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

    public void goBack() {
        Intent intent = new Intent();
        intent.putExtra(Constants.PLATFORM_ID, moServiceID + "");
        intent.putExtra(Constants.SKILL_IDS, moSkilIDs + "");
        intent.putExtra(Constants.SKILL_NAMES, moSkillNames + "");
        intent.putExtra(Constants.BUDGET, moBudget);
        intent.putExtra(Constants.CLIENT_RATE_ID, moClientRateId);
        intent.putExtra(Constants.CLIENT_RATE, moClientRate);
        intent.putExtra(Constants.DESCRIBE, "");
        intent.putExtra("deadline", selectedDeadline);
        intent.putExtra(Constants.ATTACH_LOCAL_FILE, TextUtils.isEmpty(filesList) ? "" : filesList);
        intent.putExtra(Constants.PAY_TYPE, payType);
        intent.putExtra(Constants.PLATFORM_NAME, lawyerService);
        fragment.setResult(RESULT_OK, intent);
        ((PostJobActivity) fragment).finish();
    }
}
