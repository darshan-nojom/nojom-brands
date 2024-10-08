package com.nojom.client.ui.clientprofile;

import static com.nojom.client.cropper.CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE;
import static com.nojom.client.multitypepicker.Constant.REQUEST_CODE_PICK_IMAGE;
import static com.nojom.client.util.Constants.COUNTRY_CODE_VALUE;
import static com.nojom.client.util.Utils.WindowScreen.NAME;
import static com.nojom.client.util.Utils.WindowScreen.UNAME;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.cropper.CropImage;
import com.nojom.client.databinding.ActivityMyProfileBinding;
import com.nojom.client.databinding.DialogMyProfileDetailBinding;
import com.nojom.client.model.Profile;
import com.nojom.client.multitypepicker.Constant;
import com.nojom.client.multitypepicker.activity.ImagePickActivity;
import com.nojom.client.multitypepicker.activity.NormalFilePickActivity;
import com.nojom.client.multitypepicker.activity.VideoPickActivity;
import com.nojom.client.multitypepicker.filter.entity.ImageFile;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.dialog.StorageDisclosureDialog;
import com.nojom.client.ui.workprofile.VerificationActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import org.jetbrains.annotations.NotNull;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class MyProfileActivity extends BaseActivity implements BaseActivity.OnProfileLoadListener {

    private ActivityMyProfileBinding binding;
    private String selectedFilePath = "", selectedFilePathCRN = "", selectedFilePathVAT = "";
    private MyProfileActivityVM myProfileActivityVM;
    private Profile profileData;
    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_CODE_SENT = 2;
    private static final int STATE_VERIFY_FAILED = 3;
    private static final int STATE_VERIFY_SUCCESS = 4;
    private static final int STATE_SIGNIN_SUCCESS = 6;

    private FirebaseAuth mAuth;

    boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_profile);
        myProfileActivityVM = new MyProfileActivityVM(Task24Application.getInstance(), this);
        initData();
    }

    private void initData() {
        setOnProfileLoadListener(this);
        mAuth = FirebaseAuth.getInstance();
        mAuth.setLanguageCode(Locale.getDefault().getLanguage());

        binding.toolbar.imgBack.setOnClickListener(v -> onBackPressed());
        binding.relComName.setOnClickListener(v -> showDialog(NAME));
        binding.relUName.setOnClickListener(v -> showDialog(UNAME));
        binding.relBrandName.setOnClickListener(v -> showDialog(Utils.WindowScreen.BRAND_NAME));
        binding.relContName.setOnClickListener(v -> showDialog(Utils.WindowScreen.CONTACT_NAME));
        binding.relEmail.setOnClickListener(v -> showDialog(Utils.WindowScreen.EMAIL));
        binding.relMobile.setOnClickListener(v -> showDialog(Utils.WindowScreen.PHONE));
        binding.relCrn.setOnClickListener(v -> showDialog(Utils.WindowScreen.CRN));
        binding.relAboutUs.setOnClickListener(v -> redirectActivity(SelectExpertiseActivity.class));
        binding.relAboutUs.setOnClickListener(v -> redirectActivity(SelectExpertiseActivity.class));
        binding.linVerification.setOnClickListener(v -> redirectActivity(VerificationActivity.class));

        profileData = Preferences.getProfileData(this);

        if (profileData != null) {
            onProfileLoadData(profileData);
        }

        binding.relSubmitVerification.setOnClickListener(v -> {
            if (profileData == null) {
                return;
            }
            if (TextUtils.isEmpty(profileData.company_name)) {
                toastMessage(getString(R.string.please_enter_company_name));
                return;
            }
//            if (TextUtils.isEmpty(profileData.brand_name)) {
//                toastMessage(getString(R.string.brand_name));
//                return;
//            }
            if (TextUtils.isEmpty(profileData.firstName)) {
                toastMessage(getString(R.string.please_enter_first_name));
                return;
            }
            if (TextUtils.isEmpty(profileData.email)) {
                toastMessage(getString(R.string.email_is_invalid));
                return;
            }
            if (TextUtils.isEmpty(profileData.username)) {
                toastMessage(getString(R.string.enter_valid_username));
                return;
            }
            if (TextUtils.isEmpty(profileData.contactNo) || profileData.contactNo.equals("null.null")) {
                toastMessage(getString(R.string.please_enter_mobile));
                return;
            }
            if (TextUtils.isEmpty(profileData.cr_number)) {
                toastMessage(getString(R.string.please_enter_cr_number));
                return;
            }
//            if (profileData.aboutus_id == 0) {
//                toastMessage(getString(R.string.about_us));
//                return;
//            }
            myProfileActivityVM.updateProfile(null, profileData.company_name, profileData.brand_name, profileData.firstName, profileData.lastName, profileData.email, profileData.contactNo, profileData.getMobilePrefix(profileData.contactNo), profileData.aboutus_id, profileData.other_aboutus, 2, profileData.username);

            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(() -> {
                Preferences.writeBoolean(this, Constants.IS_LOGIN, true);
                setVerificationDone();
                gotoMainActivity(Constants.TAB_HOME);
            }, 2000);

        });

        binding.txtEditProfile.setOnClickListener(v -> {
            if (checkStoragePermission()) {
                checkPermission(false, REQUEST_CODE_PICK_IMAGE);
            } else {
                new StorageDisclosureDialog(this, () -> checkPermission(false, REQUEST_CODE_PICK_IMAGE));
            }
        });

        binding.switchVat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                showDialog(Utils.WindowScreen.VAT);
            }
        });

        myProfileActivityVM.getIsShowProgress().observe(this, aBoolean -> {
            if (bindingDialog != null) {
                if (aBoolean) {
                    bindingDialog.tvSave.setVisibility(View.INVISIBLE);
                    bindingDialog.progress.setVisibility(View.VISIBLE);
                } else {
                    bindingDialog.tvSave.setVisibility(View.VISIBLE);
                    bindingDialog.progress.setVisibility(View.GONE);
                }
            } else {
                if (aBoolean) {
                    binding.tvEditProfile.setVisibility(View.INVISIBLE);
                    binding.progress.setVisibility(View.VISIBLE);
                } else {
                    binding.tvEditProfile.setVisibility(View.VISIBLE);
                    binding.progress.setVisibility(View.GONE);
                }
            }
        });

        myProfileActivityVM.getIsDialogClose().observe(this, aBoolean -> {
            if (aBoolean) {
                if (nameDialog != null && nameDialog.isShowing()) {
                    nameDialog.dismiss();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getProfile();
    }

    private void onProfileLoadData(Profile profileData) {
        Glide.with(this).load(TextUtils.isEmpty(profileData.profilePic) ? "" : profileData.filePath.pathProfilePicClient + profileData.profilePic).centerCrop().placeholder(R.drawable.dp).into(binding.imgProfile);
        setData();
    }

    Dialog nameDialog;
    DialogMyProfileDetailBinding bindingDialog;

    private void showDialog(Utils.WindowScreen screen) {
        nameDialog = new Dialog(this, R.style.Theme_Design_Light_BottomSheetDialog);
        nameDialog.setTitle(null);
        bindingDialog = DialogMyProfileDetailBinding.inflate(LayoutInflater.from(this));
        nameDialog.setContentView(bindingDialog.getRoot());
        nameDialog.setCancelable(true);

        switch (screen) {
            case NAME:
                bindingDialog.linName.setVisibility(View.VISIBLE);
                bindingDialog.etName.setHint(getString(R.string.company_name));
                bindingDialog.txtTitle.setText(getString(R.string.company_name));
                break;
            case UNAME:
                bindingDialog.linUname.setVisibility(View.VISIBLE);
                bindingDialog.txtTitle.setText(getString(R.string.username));
                if (profileData != null) {
                    bindingDialog.etUsername.setText(profileData.username);
                }
                break;
            case BRAND_NAME:
                bindingDialog.linName.setVisibility(View.VISIBLE);
                bindingDialog.etName.setHint(getString(R.string.brand_name));
                bindingDialog.txtTitle.setText(getString(R.string.brand_name));
                break;
            case CONTACT_NAME:
                bindingDialog.linName.setVisibility(View.VISIBLE);
                bindingDialog.etName.setHint(getString(R.string.first_name));
                bindingDialog.etLname.setHint(getString(R.string.last_name));
                bindingDialog.etLname.setVisibility(View.VISIBLE);
                bindingDialog.txtTitle.setText(getString(R.string.contact_name));
                break;
            case EMAIL:
                bindingDialog.linEmail.setVisibility(View.VISIBLE);
                bindingDialog.txtTitle.setText(getString(R.string.email));
                if (profileData != null) {
                    bindingDialog.etEmail.setText(profileData.email);
                }
                bindingDialog.relSendEmail.setOnClickListener(v -> {
                    if (TextUtils.isEmpty(bindingDialog.etEmail.getText().toString().trim())) {
                        toastMessage(getString(R.string.msgEnterEmail));
                        return;
                    }
                    if (!isValidEmail(bindingDialog.etEmail.getText().toString().trim())) {
                        toastMessage(getString(R.string.invalid_email));
                        return;
                    }
                    myProfileActivityVM.verifyEmail(bindingDialog.etEmail.getText().toString());
                });
                break;
            case PHONE:
                bindingDialog.linMobile.setVisibility(View.VISIBLE);
                bindingDialog.txtTitle.setText(getString(R.string.mobile));

                if (profileData != null && !TextUtils.isEmpty(profileData.contactNo) && !TextUtils.isEmpty("null.null")) {
                    binding.tvMobile.setText(profileData.contactNo);
                    bindingDialog.ccp.setCountryForPhoneCode(Integer.parseInt(profileData.contactNo.split("\\.")[0]));
                    bindingDialog.etMobile.setText(profileData.contactNo.split("\\.")[1]);
                }

                bindingDialog.tvPhonePrefix.setText(bindingDialog.ccp.getSelectedCountryCodeWithPlus());
                bindingDialog.ccp.setOnCountryChangeListener(() -> {
                    bindingDialog.tvPhonePrefix.setText(bindingDialog.ccp.getSelectedCountryCodeWithPlus());
                });

                myProfileActivityVM.getIsShowProgressMobile().observe(this, aBoolean -> {
                    if (aBoolean) {
                        bindingDialog.progressBarSignup.setVisibility(View.VISIBLE);
                        bindingDialog.tvSendCode.setVisibility(View.INVISIBLE);
                    } else {
                        bindingDialog.progressBarSignup.setVisibility(View.GONE);
                        bindingDialog.tvSendCode.setVisibility(View.VISIBLE);
                        if (nameDialog != null) {
                            nameDialog.dismiss();
                        }
                    }
                });

                bindingDialog.tvResendCode.setOnClickListener(v -> {
                    resendVerificationCode(mResendToken);
                });

                bindingDialog.relSendCode.setOnClickListener(v -> {

                    if (bindingDialog.tvSendCode.getText().toString().equals(getString(R.string.send_verification_code))) {
                        if (TextUtils.isEmpty(bindingDialog.etMobile.getText().toString().trim())) {
                            toastMessage(getString(R.string.please_enter_mobile));
                            return;
                        }

                        startPhoneNumberVerification();
                    } else {
                        String code = bindingDialog.etOtp.getText().toString();
                        if (isEmpty(code)) {
                            toastMessage(getString(R.string.please_enter_otp));
                            return;
                        }

                        verifyPhoneNumberWithCode(code);
                    }
                });


                mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(@NotNull PhoneAuthCredential credential) {
                        mVerificationInProgress = false;
                        updateUI(STATE_VERIFY_SUCCESS, credential);
                        myProfileActivityVM.verifyPhoneNumber();
                    }

                    @Override
                    public void onVerificationFailed(@NotNull FirebaseException e) {
                        bindingDialog.progressBarSignup.setVisibility(View.GONE);
                        bindingDialog.tvSendCode.setVisibility(View.VISIBLE);
                        mVerificationInProgress = false;
                        isClickableView = false;
                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            bindingDialog.etMobile.setError(getString(R.string.invalid_phone_number));
                        } else if (e instanceof FirebaseTooManyRequestsException) {
                            toastMessage(getString(R.string.quota_exceeded));
                        }

                        updateUI(STATE_VERIFY_FAILED);
                    }

                    @Override
                    public void onCodeSent(@NotNull String verificationId, @NotNull PhoneAuthProvider.ForceResendingToken token) {
                        bindingDialog.progressBarSignup.setVisibility(View.GONE);
                        bindingDialog.tvSendCode.setVisibility(View.VISIBLE);
                        mVerificationId = verificationId;
                        mResendToken = token;
                        mVerificationInProgress = false;
                        isClickableView = false;
                        updateUI(STATE_CODE_SENT);
                    }
                };

                break;
            case CRN:
                bindingDialog.linCrn.setVisibility(View.VISIBLE);
                bindingDialog.txtTitle.setText(getString(R.string.commercial_registration_number));

                bindingDialog.linCrnUpload.setOnClickListener(v -> {
                    if (checkStoragePermission()) {
                        selectFileDialog(1234);
                    } else {
                        new StorageDisclosureDialog(this, () -> selectFileDialog(1234));
                    }
                });

                myProfileActivityVM.getDeleteSuccess().observe(this, aBoolean -> {
                    if (aBoolean == 1) {
                        bindingDialog.linCrnUpload.setVisibility(View.VISIBLE);
                        bindingDialog.relSelectedCrn.setVisibility(View.GONE);
                        selectedFilePathCRN = "";
                        bindingDialog.etCrn.setText("");
                        myProfileActivityVM.getDeleteSuccess().postValue(0);
                    }
                });
                bindingDialog.imgDownload.setOnClickListener(v -> {
                    viewFile(profileData.filePath.path_commercial_attechment + profileData.cr_file);
                });
                bindingDialog.imgDelete.setOnClickListener(v -> {
                    if (profileData != null && profileData.commercial_registration_id == null || profileData.commercial_registration_id == 0) {
                        bindingDialog.linCrnUpload.setVisibility(View.VISIBLE);
                        bindingDialog.relSelectedCrn.setVisibility(View.GONE);
                        selectedFilePathCRN = "";
                        bindingDialog.etCrn.setText("");
                    } else {
                        myProfileActivityVM.deleteCrn(profileData.commercial_registration_id);
                    }
                });
                break;
            case VAT:
                bindingDialog.linVat.setVisibility(View.VISIBLE);
                bindingDialog.txtTitle.setText(getString(R.string.vat_number));
                bindingDialog.txtAddAttach.setOnClickListener(v -> {
                    if (checkStoragePermission()) {
                        selectFileDialog(12345);
                    } else {
                        new StorageDisclosureDialog(this, () -> selectFileDialog(12345));
                    }
                });
                myProfileActivityVM.getDeleteSuccessVat().observe(this, aBoolean -> {
                    if (aBoolean) {
                        bindingDialog.txtAddAttach.setVisibility(View.VISIBLE);
                        bindingDialog.relSelectedVat.setVisibility(View.GONE);
                        selectedFilePathVAT = "";
                        bindingDialog.etVat.setText("");
                        myProfileActivityVM.getDeleteSuccessVat().postValue(false);
                    }
                });
                bindingDialog.imgDownloadVat.setOnClickListener(v -> {
                    viewFile(profileData.filePath.path_vat_attechment + profileData.vat_file);
                });
                bindingDialog.imgDeleteVat.setOnClickListener(v -> {
                    if (profileData != null && profileData.vat_registration_id == null || profileData.vat_registration_id == 0) {
                        bindingDialog.txtAddAttach.setVisibility(View.VISIBLE);
                        bindingDialog.relSelectedVat.setVisibility(View.GONE);
                        selectedFilePathVAT = "";
                        bindingDialog.etVat.setText("");
                    } else {
                        myProfileActivityVM.deleteVat(profileData.vat_registration_id);
                    }
                });
                break;
        }


        bindingDialog.txtCancel.setOnClickListener(v -> {
            if (screen == Utils.WindowScreen.VAT) {
                if (TextUtils.isEmpty(profileData.vat_number)) {
                    binding.switchVat.setChecked(false);
                }
            }
            nameDialog.dismiss();
        });
        bindingDialog.relSave.setOnClickListener(v -> {

            switch (screen) {
                case NAME:
                    if (TextUtils.isEmpty(bindingDialog.etName.getText().toString().trim())) {
                        toastMessage(getString(R.string.please_enter_company_name));
                        return;
                    }
                    myProfileActivityVM.updateProfile(screen, bindingDialog.etName.getText().toString(), profileData.brand_name, profileData.firstName, profileData.lastName, profileData.email, profileData.contactNo, profileData.getMobilePrefix(profileData.contactNo), profileData.aboutus_id, profileData.other_aboutus, profileData.is_verified, profileData.username);
                    break;
                case UNAME:
                    if (TextUtils.isEmpty(bindingDialog.etUsername.getText().toString().trim())) {
                        toastMessage(getString(R.string.enter_username));
                        return;
                    }
                    myProfileActivityVM.updateProfile(screen, profileData.company_name, profileData.brand_name, profileData.firstName, profileData.lastName, profileData.email, profileData.contactNo, profileData.getMobilePrefix(profileData.contactNo), profileData.aboutus_id, profileData.other_aboutus, profileData.is_verified, bindingDialog.etUsername.getText().toString().trim());
                    break;
                case BRAND_NAME:
                    if (TextUtils.isEmpty(bindingDialog.etName.getText().toString().trim())) {
                        toastMessage(getString(R.string.please_enter_brand_number));
                        return;
                    }
                    myProfileActivityVM.updateProfile(screen, profileData.company_name, bindingDialog.etName.getText().toString(), profileData.firstName, profileData.lastName, profileData.email, profileData.contactNo, profileData.getMobilePrefix(profileData.contactNo), profileData.aboutus_id, profileData.other_aboutus, profileData.is_verified, profileData.username);
                    break;
                case CONTACT_NAME:
                    if (TextUtils.isEmpty(bindingDialog.etName.getText().toString().trim())) {
                        toastMessage(getString(R.string.please_enter_first_name));
                        return;
                    }
                    if (TextUtils.isEmpty(bindingDialog.etLname.getText().toString().trim())) {
                        toastMessage(getString(R.string.please_enter_last_name));
                        return;
                    }
                    myProfileActivityVM.updateProfile(screen, profileData.company_name, profileData.brand_name, bindingDialog.etName.getText().toString(), bindingDialog.etLname.getText().toString(), profileData.email, profileData.contactNo, profileData.getMobilePrefix(profileData.contactNo), profileData.aboutus_id, profileData.other_aboutus, profileData.is_verified, profileData.username);
                    break;
                case EMAIL:
                    if (TextUtils.isEmpty(bindingDialog.etEmail.getText().toString().trim())) {
                        toastMessage(getString(R.string.msgEnterEmail));
                        return;
                    }
                    if (!isValidEmail(bindingDialog.etEmail.getText().toString().trim())) {
                        toastMessage(getString(R.string.invalid_email));
                        return;
                    }
                    myProfileActivityVM.updateProfile(screen, profileData.company_name, profileData.brand_name, profileData.firstName, profileData.lastName, bindingDialog.etEmail.getText().toString(), profileData.contactNo, profileData.getMobilePrefix(profileData.contactNo), profileData.aboutus_id, profileData.other_aboutus, profileData.is_verified, profileData.username);
                    break;
                case PHONE:
                    if (TextUtils.isEmpty(bindingDialog.etMobile.getText().toString().trim())) {
                        toastMessage(getString(R.string.please_enter_mobile));
                        return;
                    }
                    String nameCode = bindingDialog.ccp.getSelectedCountryNameCode();
                    Preferences.writeString(this, COUNTRY_CODE_VALUE, nameCode);

                    myProfileActivityVM.updateProfile(screen, profileData.company_name, profileData.brand_name, profileData.firstName, profileData.lastName, profileData.email, bindingDialog.etMobile.getText().toString(), bindingDialog.tvPhonePrefix.getText().toString(), profileData.aboutus_id, profileData.other_aboutus, profileData.is_verified, profileData.username);
                    break;
                case VAT:
                    if (TextUtils.isEmpty(bindingDialog.etVat.getText().toString().trim())) {
                        toastMessage(getString(R.string.please_enter_vat_number));
                        return;
                    }
//                    if (TextUtils.isEmpty(selectedFilePathVAT)) {
//                        toastMessage(getString(R.string.please_select_file));
//                        return;
//                    }
                    myProfileActivityVM.updateProfileVat(bindingDialog.etVat.getText().toString(), selectedFilePathVAT);
                    break;
                case CRN:
                    if (TextUtils.isEmpty(bindingDialog.etCrn.getText().toString().trim())) {
                        toastMessage(getString(R.string.please_enter_cr_number));
                        return;
                    }
//                    if (TextUtils.isEmpty(selectedFilePathCRN)) {
//                        toastMessage(getString(R.string.please_select_file));
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
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        lp.height = (int) (displayMetrics.heightPixels * 0.95f);
        nameDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        nameDialog.getWindow().setAttributes(lp);
    }

    private void selectFileDialog(int code) {
        @SuppressLint("PrivateResource") final Dialog dialog = new Dialog(this, R.style.Theme_Design_Light_BottomSheetDialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_camera_document_select);
        dialog.setCancelable(true);
        TextView tvCancel = dialog.findViewById(R.id.btn_cancel);
        LinearLayout llCamera = dialog.findViewById(R.id.ll_camera);
        LinearLayout llDocument = dialog.findViewById(R.id.ll_document);

        llCamera.setOnClickListener(v -> {
            dialog.dismiss();
            if (checkStoragePermission()) {
                checkPermission(false, code);
            } else {
                new StorageDisclosureDialog(this, () -> checkPermission(false, code));
            }
        });

        llDocument.setOnClickListener(v -> {
            dialog.dismiss();
            if (checkStoragePermission()) {
                checkPermission(true, code);
            } else {
                new StorageDisclosureDialog(this, () -> checkPermission(true, code));
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
            Dexter.withActivity(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA).withListener(new MultiplePermissionsListener() {
                @Override
                public void onPermissionsChecked(MultiplePermissionsReport report) {
                    if (report.areAllPermissionsGranted()) {
                        if (isDocument) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                openDocuments(MyProfileActivity.this, 1, code);
                            } else {
                                Intent intent = new Intent(MyProfileActivity.this, NormalFilePickActivity.class);
                                intent.putExtra(Constant.MAX_NUMBER, 1);
                                intent.putExtra(NormalFilePickActivity.SUFFIX, new String[]{"doc", "docx", "ppt", "pptx", "pdf"});
                                startActivityForResult(intent, code);
                            }
                        } else {
                            Intent intent = new Intent(MyProfileActivity.this, ImagePickActivity.class);
                            intent.putExtra(VideoPickActivity.IS_NEED_CAMERA, true);
                            intent.putExtra(Constant.MAX_NUMBER, 1);
                            intent.putExtra("rCode", Constant.REQUEST_CODE_PICK_IMAGE);
                            startActivityForResult(intent, code);
                        }
                    }

                    if (report.isAnyPermissionPermanentlyDenied()) {
                        toastMessage(getString(R.string.please_give_permission));
                    }
                }

                @Override
                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                    token.continuePermissionRequest();
                }
            }).onSameThread().check();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<ImageFile> imgPath = data.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE);
                if (imgPath != null && imgPath.size() > 0) {
                    selectedFilePath = imgPath.get(0).getPath();

                    Glide.with(this).load(selectedFilePath).centerCrop().placeholder(R.drawable.dp).into(binding.imgProfile);

                    myProfileActivityVM.updateProfilePic(selectedFilePath);

                }
            }
        } else if (requestCode == 1234 && resultCode == RESULT_OK && data != null) {//CRN
            bindingDialog.linCrnUpload.setVisibility(View.GONE);
            bindingDialog.relSelectedCrn.setVisibility(View.VISIBLE);
            ArrayList<ImageFile> imgPath = data.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE);
            if (imgPath != null && imgPath.size() > 0) {
                selectedFilePathCRN = imgPath.get(0).getPath();

                if (bindingDialog != null) {
                    bindingDialog.txtFileNameCrn.setText(imgPath.get(0).getName());
                    bindingDialog.txtDate.setText(getString(R.string.cr_number) + ": " + bindingDialog.etCrn.getText().toString());
                    bindingDialog.txtDate.invalidate();
                    bindingDialog.txtFileNameCrn.invalidate();
                }
                return;
            }
            String path = null;
            try {
                if (data.getData() != null) {
                    path = Utils.getFilePath(this, data.getData());
                    if (path != null) {
                        selectedFilePathCRN = path;

                        if (bindingDialog != null) {
                            bindingDialog.txtFileNameCrn.setText(path);
                            bindingDialog.txtDate.setText(getString(R.string.cr_number) + ": " + bindingDialog.etCrn.getText().toString());
                            bindingDialog.txtDate.invalidate();
                            bindingDialog.txtFileNameCrn.invalidate();
                        }
                    } else {
                        toastMessage(getString(R.string.please_select_file));
                    }
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else if (requestCode == 12345 && resultCode == RESULT_OK && data != null) {//vat
            ArrayList<ImageFile> imgPath = data.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE);
            if (imgPath != null && imgPath.size() > 0) {
                selectedFilePathVAT = imgPath.get(0).getPath();
                bindingDialog.txtAddAttach.setVisibility(View.GONE);
                bindingDialog.relSelectedVat.setVisibility(View.VISIBLE);
                if (bindingDialog != null) {
                    bindingDialog.txtFileNameVat.setText(imgPath.get(0).getName());
                    bindingDialog.txtDateVat.setText(getString(R.string.vat_number) + ": " + bindingDialog.etVat.getText().toString());
                    bindingDialog.txtDateVat.invalidate();
                    bindingDialog.txtFileNameVat.invalidate();
                }
                return;
            }
            String pathVat = null;
            try {
                if (data.getData() != null) {
                    pathVat = Utils.getFilePath(this, data.getData());
                    if (pathVat != null) {
                        selectedFilePathVAT = pathVat;

                        if (bindingDialog != null) {
                            bindingDialog.txtAddAttach.setVisibility(View.GONE);
                            bindingDialog.relSelectedVat.setVisibility(View.VISIBLE);
                            bindingDialog.txtFileNameVat.setText(pathVat);
                            bindingDialog.txtDateVat.setText(getString(R.string.vat_number) + ": " + bindingDialog.etVat.getText().toString());
                            bindingDialog.txtDateVat.invalidate();
                            bindingDialog.txtFileNameVat.invalidate();
                        }
                    } else {
                        toastMessage(getString(R.string.please_select_file));
                    }
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else if (requestCode == CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    selectedFilePath = null;
//                    imageutils.onActivityResult(requestCode, resultCode, data);
                } catch (Exception ex) {
                    toastMessage(ex.toString());
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                toastMessage(getString(R.string.something_went_wrong));
            }
        }
    }

    private void setData() {
        if (!TextUtils.isEmpty(profileData.company_name)) {
            binding.tvCmpName.setText(profileData.company_name);
        }
        if (!TextUtils.isEmpty(profileData.username)) {
            binding.tvUName.setText(profileData.username);
        }
        if (!TextUtils.isEmpty(profileData.brand_name)) {
            binding.tvCmpBrand.setText(profileData.brand_name);
        }
        if (!TextUtils.isEmpty(profileData.firstName)) {
            binding.tvCmpContact.setText(profileData.firstName);
        }
        if (!TextUtils.isEmpty(profileData.email)) {
            binding.tvEmail.setText(profileData.email);
        }
        if (!TextUtils.isEmpty(profileData.contactNo) && !TextUtils.isEmpty("null.null")) {
            binding.tvMobile.setText(profileData.contactNo);
        }
        if (!TextUtils.isEmpty(profileData.cr_number)) {
            binding.tvComNo.setText(profileData.cr_number);
        }
    }


    @Override
    public void onProfileLoad(Profile data) {
        profileData = data;


        if (profileData.profilePic != null) {
            Glide.with(this).load(TextUtils.isEmpty(profileData.profilePic) ? "" : profileData.filePath.pathProfilePicClient + profileData.profilePic).centerCrop().placeholder(R.drawable.dp).into(binding.imgProfile);
        }
        setData();

        if (nameDialog != null && bindingDialog != null && profileData.cr_file != null) {
            bindingDialog.txtFileNameCrn.setText(profileData.cr_file);
            bindingDialog.txtDate.setText(String.format("%s: %s", getString(R.string.cr_number), profileData.cr_number));
            bindingDialog.txtDate.invalidate();
            bindingDialog.txtFileNameCrn.invalidate();
        }
        if (nameDialog != null && bindingDialog != null && profileData.vat_file != null) {
            bindingDialog.txtFileNameVat.setText(profileData.vat_file);
            bindingDialog.txtDateVat.setText(String.format("%s: %s", getString(R.string.vat_number), profileData.vat_number));
            bindingDialog.txtDateVat.invalidate();
            bindingDialog.txtFileNameVat.invalidate();
        }
    }

    private void startPhoneNumberVerification() {
        bindingDialog.progressBarSignup.setVisibility(View.VISIBLE);
        bindingDialog.tvSendCode.setVisibility(View.INVISIBLE);
        isClickableView = true;

        PhoneAuthProvider.getInstance().verifyPhoneNumber(bindingDialog.ccp.getDefaultCountryCodeWithPlus() + bindingDialog.etMobile.getText().toString(), 60, TimeUnit.SECONDS, this, mCallbacks);

        mVerificationInProgress = true;
    }

    private void verifyPhoneNumberWithCode(String code) {
        try {
            bindingDialog.progressBarSignup.setVisibility(View.VISIBLE);
            bindingDialog.tvSendCode.setVisibility(View.INVISIBLE);
            isClickableView = true;

            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
            mAuth.signInWithCredential(credential).addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    myProfileActivityVM.verifyPhoneNumber();
                } else {
                    // Sign in failed, display a message and update the UI
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resendVerificationCode(PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(bindingDialog.ccp.getDefaultCountryCodeWithPlus() + bindingDialog.etMobile.getText().toString(), 60, TimeUnit.SECONDS, this, mCallbacks, token);
    }

    private void updateUI(int uiState) {
        updateUI(uiState, mAuth.getCurrentUser(), null);
    }

    private void updateUI(int uiState, PhoneAuthCredential cred) {
        updateUI(uiState, null, cred);
    }

    private void updateUI(int uiState, FirebaseUser user, PhoneAuthCredential cred) {
        switch (uiState) {
            case STATE_INITIALIZED:
                bindingDialog.llOtp.setVisibility(View.GONE);
                break;
            case STATE_CODE_SENT:
                bindingDialog.tvSendCode.setText(getString(R.string.verify_otp));
                bindingDialog.llOtp.setVisibility(View.VISIBLE);
                disableViews(bindingDialog.etMobile, bindingDialog.ccp);
                break;
            case STATE_VERIFY_FAILED:
                bindingDialog.tvSendCode.setText(getString(R.string.send_verification_code));
                bindingDialog.llOtp.setVisibility(View.GONE);
                toastMessage(getString(R.string.verification_failed));
                break;
        }
    }

    private void disableViews(View... views) {
        for (View v : views) {
            v.setEnabled(false);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        gotoMainActivity(Constants.TAB_HOME);
    }
}
