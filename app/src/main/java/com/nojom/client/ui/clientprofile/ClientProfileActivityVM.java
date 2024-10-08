package com.nojom.client.ui.clientprofile;

import static com.nojom.client.util.Constants.API_UPDATE_PROFILE;
import static com.nojom.client.util.Constants.COUNTRY_CODE_VALUE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.AndroidViewModel;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivityClientProfileBinding;
import com.nojom.client.databinding.DialogMyProfileDetailBinding;
import com.nojom.client.model.Profile;
import com.nojom.client.multitypepicker.Constant;
import com.nojom.client.multitypepicker.activity.ImagePickActivity;
import com.nojom.client.multitypepicker.activity.NormalFilePickActivity;
import com.nojom.client.multitypepicker.activity.VideoPickActivity;
import com.nojom.client.multitypepicker.filter.entity.ImageFile;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.auth.UpdatePasswordActivity;
import com.nojom.client.ui.dialog.StorageDisclosureDialog;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

class ClientProfileActivityVM extends AndroidViewModel implements View.OnClickListener, RequestResponseListener, BaseActivity.OnProfileLoadListener {
    @SuppressLint("StaticFieldLeak")
    private BaseActivity activity;
    private ActivityClientProfileBinding binding;
    private File profileFile;
    private Profile profileData;
    private String selectedFilePathCRN = "", selectedFilePathVAT = "";

    private MyProfileActivityVM myProfileActivityVM;

    ClientProfileActivityVM(Application application, ActivityClientProfileBinding clientProfileBinding, BaseActivity clientProfileActivity) {
        super(application);
        binding = clientProfileBinding;
        activity = clientProfileActivity;
        myProfileActivityVM = new MyProfileActivityVM(Task24Application.getInstance(), activity);
        activity.setOnProfileLoadListener(this);
        initData();
    }

    private void initData() {
        binding.tvChangepassword.setOnClickListener(this);
        binding.toolbar.imgBack.setOnClickListener(this);
        binding.toolbar.tvSave.setOnClickListener(this);
        binding.etCrNo.setOnClickListener(this);
        binding.etVatNo.setOnClickListener(this);
        binding.imgView.setOnClickListener(this);
        binding.imgViewVat.setOnClickListener(this);

        binding.toolbar.tvToolbarTitle.setText(activity.getString(R.string.private_information));

        profileData = Preferences.getProfileData(activity);
        if (profileData == null) {
            activity.toastMessage(activity.getString(R.string.data_not_found));
            activity.finish();
            return;
        }

        if (profileData.firstName != null) binding.etFirstname.setText(profileData.firstName);
        if (profileData.lastName != null) binding.etLastname.setText(profileData.lastName);
        if (profileData.email != null) binding.etEmail.setText(profileData.email);
        if (profileData.company_name != null) binding.etCompName.setText(profileData.company_name);
        if (profileData.brand_name != null) binding.etBrandName.setText(profileData.brand_name);
        if (profileData.cr_number != null) {
            binding.etCrNo.setText(profileData.cr_number);
            binding.relSelectedCrn.setVisibility(View.VISIBLE);
            binding.txtFileNameCrn.setText(profileData.cr_file);
            binding.txtDate.setText(activity.getString(R.string.cr_number) + ": " + profileData.cr_number);
        }
        if (profileData.vat_number != null) {
            binding.etVatNo.setText(profileData.vat_number);
            binding.relSelectedVat.setVisibility(View.VISIBLE);
            binding.txtFileNameVat.setText(profileData.vat_file);
            binding.txtDateVat.setText(activity.getString(R.string.vat_number) + ": " + profileData.vat_number);
        }

        if (profileData.contactNo != null) {
            String[] split = profileData.contactNo.split("\\.");
            if (split.length == 2) {
                binding.etMobile.setText(split[1]);
                binding.tvPhonePrefix.setText(split[0]);
                try {
                    String nameCode = Preferences.readString(activity, COUNTRY_CODE_VALUE, "");
                    if (!TextUtils.isEmpty(nameCode)) {
                        binding.ccp.setDetectCountryWithAreaCode(false);
                        binding.ccp.setCountryForNameCode(nameCode);
                    } else {
                        String code = split[0].replace("+", "").replace(" ", "");
                        binding.ccp.setCountryForPhoneCode(Integer.parseInt(code));
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        binding.ccp.registerCarrierNumberEditText(binding.etMobile);
        addTextChangeEvent(binding.etFirstname, binding.etLastname, binding.etEmail, binding.etMobile, binding.etCompName, binding.etBrandName, binding.etCrNo, binding.etVatNo);

        binding.ccp.setOnCountryChangeListener(() -> {
            binding.toolbar.tvSave.setVisibility(View.VISIBLE);
            binding.tvPhonePrefix.setText(binding.ccp.getSelectedCountryCodeWithPlus());
        });

    }


    public String getFirstName() {
        return binding.etFirstname.getText().toString().trim();
    }

    public String getLastName() {
        return binding.etLastname.getText().toString().trim();
    }

    public String getEmail() {
        return binding.etEmail.getText().toString().trim();
    }

    public String getCompanyName() {
        return binding.etCompName.getText().toString().trim();
    }

    public String getBrandName() {
        return binding.etBrandName.getText().toString().trim();
    }

    public String getCrn() {
        return binding.etCrNo.getText().toString().trim();
    }

    public String getVat() {
        return binding.etVatNo.getText().toString().trim();
    }

    private String getMobile() {
        return binding.etMobile.getText().toString().trim();
    }

    private String getMobilePrefix() {
        return binding.ccp.getSelectedCountryCodeWithPlus();
    }

    private void addTextChangeEvent(EditText... editTexts) {
        for (EditText edittext : editTexts) {
            edittext.addTextChangedListener(textWatcher);
        }
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (binding.toolbar.tvSave.getVisibility() == View.GONE) {
                binding.toolbar.tvSave.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                activity.onBackPressed();
                break;
            case R.id.tv_save:
                updateProfile();
                break;
            case R.id.et_crNo:
                showDialog(Utils.WindowScreen.CRN);
                break;
            case R.id.et_vatNo:
                showDialog(Utils.WindowScreen.VAT);
                break;
            case R.id.tv_changepassword:
                activity.redirectActivity(UpdatePasswordActivity.class);
                break;
            case R.id.imgView:
                activity.viewFile(profileData.filePath.path_commercial_attechment + profileData.cr_file);
                break;
            case R.id.imgViewVat:
                activity.viewFile(profileData.filePath.path_vat_attechment + profileData.vat_file);
                break;
        }

    }

    private void updateProfile() {
        if (!activity.isNetworkConnected()) return;

        if (TextUtils.isEmpty(getCompanyName())) {
            activity.toastMessage(activity.getString(R.string.please_enter_company_name));
            return;
        }
//        if (TextUtils.isEmpty(getBrandName())) {
//            activity.toastMessage(activity.getString(R.string.brand_name));
//            return;
//        }

        if (TextUtils.isEmpty(getFirstName())) {
            activity.toastMessage(activity.getString(R.string.please_enter_first_name));
            return;
        }
        if (TextUtils.isEmpty(getLastName())) {
            activity.toastMessage(activity.getString(R.string.please_enter_last_name));
            return;
        }
        if (TextUtils.isEmpty(getEmail())) {
            activity.toastMessage(activity.getString(R.string.msgEnterEmail));
            return;
        }

        if (!activity.isValidEmail(getEmail())) {
            activity.toastMessage(activity.getString(R.string.invalid_email));
            return;
        }

        if (TextUtils.isEmpty(getMobile())) {
            activity.toastMessage(activity.getString(R.string.please_enter_mobile));
            return;
        }

        if (TextUtils.isEmpty(getCrn())) {
            activity.toastMessage(activity.getString(R.string.please_enter_cr_number));
            return;
        }

        if (TextUtils.isEmpty(getVat())) {
            activity.toastMessage(activity.getString(R.string.please_enter_vat_number));
            return;
        }

        binding.toolbar.progressBar.setVisibility(View.VISIBLE);
        binding.toolbar.tvSave.setVisibility(View.INVISIBLE);
        activity.isClickableView = true;

        HashMap<String, String> map = new HashMap<>();
        map.put("company_name", getCompanyName());
        map.put("brand_name", getBrandName());
        map.put("cr_number", getCrn());
        map.put("vat_number", getVat());
        map.put("first_name", getFirstName());
        map.put("last_name", getLastName());
        map.put("email", getEmail());
        map.put("contactNo", getMobile());
        map.put("mobile_prefix", getMobilePrefix());

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_UPDATE_PROFILE, true, map);
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        activity.getProfile();
        String nameCode = binding.ccp.getSelectedCountryNameCode();
        Preferences.writeString(activity, COUNTRY_CODE_VALUE, nameCode);

        binding.toolbar.progressBar.setVisibility(View.GONE);
        binding.toolbar.tvSave.setVisibility(View.VISIBLE);
        activity.isClickableView = false;
        activity.toastMessage(message);
        profileData.firstName = getFirstName();
        profileData.lastName = getLastName();
        profileData.email = getEmail();
        profileData.company_name = getCompanyName();
        profileData.brand_name = getBrandName();
        profileData.contactNo = getMobilePrefix() + "." + getMobile();
        Preferences.setProfileData(activity, profileData);
        activity.onBackPressed();
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        binding.toolbar.progressBar.setVisibility(View.GONE);
        binding.toolbar.tvSave.setVisibility(View.VISIBLE);
        activity.isClickableView = false;
        activity.toastMessage(message);
    }

    Dialog nameDialog;
    DialogMyProfileDetailBinding bindingDialog;

    private void showDialog(Utils.WindowScreen screen) {
        nameDialog = new Dialog(activity, R.style.Theme_Design_Light_BottomSheetDialog);
        nameDialog.setTitle(null);
        bindingDialog = DialogMyProfileDetailBinding.inflate(LayoutInflater.from(activity));
        nameDialog.setContentView(bindingDialog.getRoot());
        nameDialog.setCancelable(true);

        myProfileActivityVM.getIsDialogClose().observe(activity, aBoolean -> {
            if (aBoolean) {
                if (nameDialog != null) {
                    nameDialog.dismiss();
                }
            }
        });

        switch (screen) {
            case CRN:
                bindingDialog.linCrn.setVisibility(View.VISIBLE);
                bindingDialog.txtTitle.setText(activity.getString(R.string.commercial_registration_number));

                if (profileData.cr_file != null) {
                    bindingDialog.linCrnUpload.setVisibility(View.GONE);
                    bindingDialog.relSelectedCrn.setVisibility(View.VISIBLE);
                    bindingDialog.etCrn.setText(profileData.cr_number);
                    bindingDialog.txtFileNameCrn.setText(profileData.cr_file);
                    bindingDialog.txtDate.setText(activity.getString(R.string.cr_number) + ": " + profileData.cr_number);
                }

                bindingDialog.linCrnUpload.setOnClickListener(v -> {
                    if (activity.checkStoragePermission()) {
                        selectFileDialog(1234);
                    } else {
                        new StorageDisclosureDialog(activity, () -> selectFileDialog(1234));
                    }
                });

                myProfileActivityVM.getDeleteSuccess().observe(activity, aBoolean -> {
                    if (aBoolean == 1) {
                        bindingDialog.linCrnUpload.setVisibility(View.VISIBLE);
                        bindingDialog.relSelectedCrn.setVisibility(View.GONE);
                        selectedFilePathCRN = "";
                        bindingDialog.etCrn.setText("");
                        binding.etCrNo.setText("");
                        binding.relSelectedCrn.setVisibility(View.GONE);
                        binding.txtFileNameCrn.setText("");
                        binding.txtDate.setText("");
                        myProfileActivityVM.getDeleteSuccess().postValue(0);
                    }
                });

                bindingDialog.imgDownload.setOnClickListener(v -> {
                    activity.viewFile(profileData.filePath.path_commercial_attechment + profileData.cr_file);
                });

                bindingDialog.imgDelete.setOnClickListener(v -> {
                    if (profileData.commercial_registration_id == null || profileData.commercial_registration_id == 0) {
                        bindingDialog.linCrnUpload.setVisibility(View.VISIBLE);
                        bindingDialog.relSelectedCrn.setVisibility(View.GONE);
                        selectedFilePathCRN = "";
                        bindingDialog.etCrn.setText("");
                        binding.etCrNo.setText("");
                        binding.relSelectedCrn.setVisibility(View.GONE);
                        binding.txtFileNameCrn.setText("");
                        binding.txtDate.setText("");
                    } else {
                        myProfileActivityVM.deleteCrn(profileData.commercial_registration_id);
                    }
                });
                break;
            case VAT:
                bindingDialog.linVat.setVisibility(View.VISIBLE);
                bindingDialog.txtTitle.setText(activity.getString(R.string.vat_number));

                if (profileData.vat_file != null) {
                    bindingDialog.txtAddAttach.setVisibility(View.GONE);
                    bindingDialog.relSelectedVat.setVisibility(View.VISIBLE);
                    bindingDialog.etVat.setText(profileData.vat_number);
                    bindingDialog.txtFileNameVat.setText(profileData.vat_file);
                    bindingDialog.txtDateVat.setText(activity.getString(R.string.vat_number) + ": " + profileData.vat_number);
                }

                bindingDialog.txtAddAttach.setOnClickListener(v -> {
                    if (activity.checkStoragePermission()) {
                        selectFileDialog(12345);
                    } else {
                        new StorageDisclosureDialog(activity, () -> selectFileDialog(12345));
                    }
                });

                bindingDialog.imgDownloadVat.setOnClickListener(v -> {
                    activity.viewFile(profileData.filePath.path_vat_attechment + profileData.vat_file);
                });

                myProfileActivityVM.getDeleteSuccessVat().observe(activity, aBoolean -> {
                    if (aBoolean) {
                        bindingDialog.txtAddAttach.setVisibility(View.VISIBLE);
                        bindingDialog.relSelectedVat.setVisibility(View.GONE);
                        selectedFilePathVAT = "";
                        bindingDialog.etVat.setText("");
                        binding.etVatNo.setText("");
                        binding.relSelectedVat.setVisibility(View.GONE);
                        binding.txtFileNameVat.setText("");
                        binding.txtDateVat.setText("");
                        myProfileActivityVM.getDeleteSuccessVat().postValue(false);
                    }
                });

                bindingDialog.imgDeleteVat.setOnClickListener(v -> {
                    if (profileData.vat_registration_id == null || profileData.vat_registration_id == 0) {
                        bindingDialog.txtAddAttach.setVisibility(View.VISIBLE);
                        bindingDialog.relSelectedVat.setVisibility(View.GONE);
                        selectedFilePathVAT = "";
                        bindingDialog.etVat.setText("");
                        binding.etVatNo.setText("");
                        binding.relSelectedVat.setVisibility(View.GONE);
                        binding.txtFileNameVat.setText("");
                        binding.txtDateVat.setText("");
                    } else {
                        myProfileActivityVM.deleteVat(profileData.vat_registration_id);
                    }
                });
                break;
        }


        bindingDialog.txtCancel.setOnClickListener(v -> {
//            switch (screen) {
//                case VAT:
//                    binding.switchVat.setChecked(false);
//                    break;
//            }
            nameDialog.dismiss();
        });
        bindingDialog.relSave.setOnClickListener(v -> {

            switch (screen) {
                case VAT:
                    if (TextUtils.isEmpty(bindingDialog.etVat.getText().toString().trim())) {
                        activity.toastMessage(activity.getString(R.string.please_enter_vat_number));
                        return;
                    }
//                    if (TextUtils.isEmpty(selectedFilePathVAT)) {
//                        activity.toastMessage(activity.getString(R.string.please_select_file));
//                        return;
//                    }
                    myProfileActivityVM.updateProfileVat(bindingDialog.etVat.getText().toString(), selectedFilePathVAT);
                    break;
                case CRN:
                    if (TextUtils.isEmpty(bindingDialog.etCrn.getText().toString().trim())) {
                        activity.toastMessage(activity.getString(R.string.please_enter_cr_number));
                        return;
                    }
//                    if (TextUtils.isEmpty(selectedFilePathCRN)) {
//                        activity.toastMessage(activity.getString(R.string.please_select_file));
//                        return;
//                    }
                    myProfileActivityVM.updateProfile(bindingDialog.etCrn.getText().toString(), selectedFilePathCRN);
                    break;
            }

        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(nameDialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        nameDialog.show();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        lp.height = (int) (displayMetrics.heightPixels * 0.95f);
        nameDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        nameDialog.getWindow().setAttributes(lp);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1234 && resultCode == Activity.RESULT_OK && data != null) {//CRN
            bindingDialog.linCrnUpload.setVisibility(View.GONE);
            bindingDialog.relSelectedCrn.setVisibility(View.VISIBLE);
            ArrayList<ImageFile> imgPath = data.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE);
            if (imgPath != null && imgPath.size() > 0) {
                selectedFilePathCRN = imgPath.get(0).getPath();

                if (bindingDialog != null) {
                    bindingDialog.txtFileNameCrn.setText(imgPath.get(0).getName());
                    bindingDialog.txtDate.setText(activity.getString(R.string.cr_number) + ": " + bindingDialog.etCrn.getText().toString());
                    bindingDialog.txtDate.invalidate();
                    bindingDialog.txtFileNameCrn.invalidate();
                }
                return;
            }

            String path = null;
            try {
                if (data.getData() != null) {
                    path = Utils.getFilePath(activity, data.getData());
                    if (path != null) {
                        selectedFilePathCRN = path;

                        if (bindingDialog != null) {
                            bindingDialog.txtFileNameCrn.setText(path);
                            bindingDialog.txtDate.setText(activity.getString(R.string.cr_number) + ": " + bindingDialog.etCrn.getText().toString());
                            bindingDialog.txtDate.invalidate();
                            bindingDialog.txtFileNameCrn.invalidate();
                        }
                    } else {
                        activity.toastMessage(activity.getString(R.string.please_select_file));
                    }
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        } else if (requestCode == 12345 && resultCode == Activity.RESULT_OK && data != null) {//vat
            ArrayList<ImageFile> imgPath = data.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE);
            if (imgPath != null && imgPath.size() > 0) {
                selectedFilePathVAT = imgPath.get(0).getPath();
                if (bindingDialog != null) {
                    bindingDialog.txtAddAttach.setVisibility(View.GONE);
                    bindingDialog.relSelectedVat.setVisibility(View.VISIBLE);
                    bindingDialog.txtFileNameVat.setText(imgPath.get(0).getName());
                    bindingDialog.txtDateVat.setText(activity.getString(R.string.vat_number) + ": " + bindingDialog.etVat.getText().toString());
                    bindingDialog.txtDateVat.invalidate();
                    bindingDialog.txtFileNameVat.invalidate();
                }
                return;
            }

            String pathVat = null;
            try {
                if (data.getData() != null) {
                    pathVat = Utils.getFilePath(activity, data.getData());
                    if (pathVat != null) {
                        selectedFilePathVAT = pathVat;

                        if (bindingDialog != null) {
                            bindingDialog.txtAddAttach.setVisibility(View.GONE);
                            bindingDialog.relSelectedVat.setVisibility(View.VISIBLE);
                            bindingDialog.txtFileNameVat.setText(pathVat);
                            bindingDialog.txtDateVat.setText(activity.getString(R.string.vat_number) + ": " + bindingDialog.etVat.getText().toString());
                            bindingDialog.txtDateVat.invalidate();
                            bindingDialog.txtFileNameVat.invalidate();
                        }
                    } else {
                        activity.toastMessage(activity.getString(R.string.please_select_file));
                    }
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onProfileLoad(Profile data) {

        profileData = data;

        if (nameDialog != null && bindingDialog != null && profileData.cr_file != null) {
            bindingDialog.txtFileNameCrn.setText(profileData.cr_file);
            bindingDialog.txtDate.setText(activity.getString(R.string.cr_number) + ": " + profileData.cr_number);
            bindingDialog.txtDate.invalidate();
            bindingDialog.txtFileNameCrn.invalidate();
        }
        if (profileData.cr_number != null) {
            binding.etCrNo.setText(profileData.cr_number);

            binding.relSelectedCrn.setVisibility(View.VISIBLE);
            binding.txtFileNameCrn.setText(profileData.cr_file);
            binding.txtDate.setText(activity.getString(R.string.cr_number) + ": " + profileData.cr_number);
        } else {
            binding.relSelectedCrn.setVisibility(View.GONE);
        }
        if (profileData.vat_number != null) {
            binding.etVatNo.setText(profileData.vat_number);
            binding.relSelectedVat.setVisibility(View.VISIBLE);
            binding.txtFileNameVat.setText(profileData.vat_file);
            binding.txtDateVat.setText(activity.getString(R.string.vat_number) + ": " + profileData.vat_number);
        } else {
            bindingDialog.relSelectedVat.setVisibility(View.GONE);
        }
        if (nameDialog != null && bindingDialog != null && profileData.vat_file != null) {
            bindingDialog.txtFileNameVat.setText(profileData.vat_file);
            bindingDialog.txtDateVat.setText(activity.getString(R.string.vat_number) + ": " + profileData.vat_number);
            bindingDialog.txtDateVat.invalidate();
            bindingDialog.txtFileNameVat.invalidate();
        }
    }

    private void selectFileDialog(int code) {
        @SuppressLint("PrivateResource") final Dialog dialog = new Dialog(activity, R.style.Theme_Design_Light_BottomSheetDialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_camera_document_select);
        dialog.setCancelable(true);
        TextView tvCancel = dialog.findViewById(R.id.btn_cancel);
        LinearLayout llCamera = dialog.findViewById(R.id.ll_camera);
        LinearLayout llDocument = dialog.findViewById(R.id.ll_document);

        llCamera.setOnClickListener(v -> {
            dialog.dismiss();
            if (activity.checkStoragePermission()) {
                checkPermission(false, code);
            } else {
                new StorageDisclosureDialog(activity, () -> checkPermission(false, code));
            }
        });

        llDocument.setOnClickListener(v -> {
            dialog.dismiss();
            if (activity.checkStoragePermission()) {
                checkPermission(true, code);
            } else {
                new StorageDisclosureDialog(activity, () -> checkPermission(true, code));
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

    public void checkPermission(final boolean isDocument, int code) {
        try {
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
                                        activity.openDocuments(activity, 1, code);
                                    } else {
                                        Intent intent = new Intent(activity, NormalFilePickActivity.class);
                                        intent.putExtra(Constant.MAX_NUMBER, 1);
                                        intent.putExtra(NormalFilePickActivity.SUFFIX, new String[]{"doc", "docx", "ppt", "pptx", "pdf"});
                                        activity.startActivityForResult(intent, code);
                                    }
                                } else {
                                    Intent intent = new Intent(activity, ImagePickActivity.class);
                                    intent.putExtra(VideoPickActivity.IS_NEED_CAMERA, true);
                                    intent.putExtra(Constant.MAX_NUMBER, 1);
                                    intent.putExtra("rCode", Constant.REQUEST_CODE_PICK_IMAGE);
                                    activity.startActivityForResult(intent, code);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
