package com.nojom.client.ui.workprofile;

import static com.nojom.client.multitypepicker.activity.VideoPickActivity.IS_NEED_CAMERA;
import static com.nojom.client.util.Constants.API_PROFILE_VERIFICATIONS;
import static com.nojom.client.util.Constants.API_VERIFY_FACEBOOK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.firebase.auth.FirebaseAuth;
import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.adapter.RecyclerviewAdapter;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivityVerificationBinding;
import com.nojom.client.databinding.DialogMyProfileDetailBinding;
import com.nojom.client.model.Attachment;
import com.nojom.client.model.Profile;
import com.nojom.client.model.TrustPoint;
import com.nojom.client.multitypepicker.Constant;
import com.nojom.client.multitypepicker.activity.ImagePickActivity;
import com.nojom.client.multitypepicker.activity.NormalFilePickActivity;
import com.nojom.client.multitypepicker.filter.entity.ImageFile;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.clientprofile.ClientProfileActivity;
import com.nojom.client.ui.clientprofile.MyProfileActivityVM;
import com.nojom.client.ui.clientprofile.VerifyEmailActivity;
import com.nojom.client.ui.clientprofile.VerifyPaymentActivity;
import com.nojom.client.ui.dialog.StorageDisclosureDialog;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

class VerificationActivityVM extends AndroidViewModel implements RecyclerviewAdapter.OnViewBindListner, BaseActivity.OnProfileLoadListener,
        RequestResponseListener {
    private static final int REQ_PHONE_VERIFICATION = 101;
    private static final int REQ_EMAIL_VERIFICATION = 102;
    private static final int REQ_PAYMENT_VERIFICATION = 103;
    private final ActivityVerificationBinding binding;
    private final BaseActivity activity;
    private ArrayList<TrustPoint> arrayList;
    private RecyclerviewAdapter mAdapter;
    private CallbackManager callbackManager;
    private FirebaseAuth mAuth;
    private Profile profileData;
    private MyProfileActivityVM myProfileActivityVM;
    private String selectedFilePathCRN = "", selectedFilePathVAT = "";

    VerificationActivityVM(Application application, ActivityVerificationBinding verificationBinding, BaseActivity verificationActivity) {
        super(application);
        binding = verificationBinding;
        activity = verificationActivity;
        myProfileActivityVM = new MyProfileActivityVM(Task24Application.getInstance(), activity);
        initData();
    }

    private void initData() {
        mAuth = FirebaseAuth.getInstance();

        callbackManager = CallbackManager.Factory.create();
        initFacebook();

        binding.toolbar.tvTitle.setText(activity.getString(R.string.trust_verification));

        binding.rvVerify.setLayoutManager(new LinearLayoutManager(activity));

        activity.setOnProfileLoadListener(this);

        binding.toolbar.imgBack.setOnClickListener(v -> activity.onBackPressed());

        if (Utils.isArabic(activity)) {
            binding.loutLeft.setBackground(activity.getResources().getDrawable(R.drawable.blue_round_corner_left_rtl));
            binding.loutRight.setBackground(activity.getResources().getDrawable(R.drawable.blue_round_corner_right_rtl));
        } else {
            binding.loutLeft.setBackground(activity.getResources().getDrawable(R.drawable.blue_round_corner_left));
            binding.loutRight.setBackground(activity.getResources().getDrawable(R.drawable.blue_round_corner_right));
        }
    }

    private void initFacebook() {
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        getGraphRequest(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException e) {
                        if (e instanceof FacebookAuthorizationException) {
                            if (AccessToken.getCurrentAccessToken() != null) {
                                LoginManager.getInstance().logOut();
                            }
                        }
                        if (!activity.isEmpty(e.getMessage()))
                            Log.e("LoginActivity", Objects.requireNonNull(e.getMessage()));
                    }
                });
    }

    void onResumeMethod() {
        profileData = Preferences.getProfileData(activity);
        if (profileData != null) {
            onProfileLoad(profileData);
        }
    }

    private void getGraphRequest(AccessToken token) {
        GraphRequest request = GraphRequest.newMeRequest(
                token,
                (object, response) -> {
                    try {
                        if (object != null) {
                            String id = object.getString("id");
                            verifyFacebook(id);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,first_name,last_name");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void setAdapter() {
        if (arrayList != null && arrayList.size() > 0) {
            if (mAdapter == null) {
                mAdapter = new RecyclerviewAdapter(arrayList, R.layout.item_verification, this);
            }
            mAdapter.doRefresh(arrayList);
            if (binding.rvVerify.getAdapter() == null) {
                binding.rvVerify.setAdapter(mAdapter);
            }
        }
    }

    private void getList(Profile.TrustRate trustPoints) {
        arrayList = new ArrayList<>();
        arrayList.add(new TrustPoint(activity.getString(R.string.email), trustPoints.email, 20));
        arrayList.add(new TrustPoint(activity.getString(R.string.phonenumber), trustPoints.phoneNumber, 20));
//        arrayList.add(new TrustPoint(activity.getString(R.string.facebook), trustPoints.facebook, trustPoints.facebook));
//        arrayList.add(new TrustPoint(activity.getString(R.string.payment_1), trustPoints.payment, trustPoints.payment));
        arrayList.add(new TrustPoint(activity.getString(R.string.cr_number), trustPoints.cr_id, 60));
        arrayList.add(new TrustPoint(activity.getString(R.string.vat_number), 0, 0));
    }


    @Override
    public void bindView(View view, final int position) {
        TextView tvTitle = view.findViewById(R.id.tv_title);
        TextView tvPoints = view.findViewById(R.id.tv_points);
        tvTitle.setText(arrayList.get(position).title);

        if (position == 3) {
            tvPoints.setVisibility(View.INVISIBLE);
        } else {
            tvPoints.setText(String.format(Locale.US, "%d " + activity.getString(R.string.points).toUpperCase(), arrayList.get(position).totalPoint));
            tvPoints.setVisibility(View.VISIBLE);
            if (arrayList.get(position).point == 0) {
                tvPoints.setBackground(ContextCompat.getDrawable(activity, R.drawable.red_rounded_corner));

                if (position == 2 && profileData.cr_status != null) {
                    if (profileData.cr_status == 2) {
                        tvPoints.setBackground(ContextCompat.getDrawable(activity, R.drawable.yellow_bg_10));
                    }
                }
            } else {
                tvPoints.setBackground(ContextCompat.getDrawable(activity, R.drawable.green_rounded_corner));
            }
        }

        LinearLayout llMain = view.findViewById(R.id.ll_main);
        llMain.setOnClickListener(view1 -> {
            String item = arrayList.get(position).title;
            if (item.equals(activity.getString(R.string.email))) {
                if (arrayList.get(position).point == 0) {
                    if (!activity.isEmpty(profileData.email)) {
                        Intent i = new Intent(activity, VerifyEmailActivity.class);
                        activity.startActivityForResult(i, REQ_EMAIL_VERIFICATION);
                    } else {
                        activity.toastMessage(activity.getString(R.string.please_enter_your_email_in_profile));
                    }
                } else {
                    activity.toastMessage(activity.getString(R.string.you_already_verify_email));
                }
            } else if (item.equals(activity.getString(R.string.phonenumber))) {
                if (arrayList.get(position).point == 0) {
                    if (TextUtils.isEmpty(profileData.contactNo)) {
                        showAddContactDialog();
                    } else {
                        Intent i = new Intent(activity, VerifyPhoneNumberActivity.class);
                        activity.startActivityForResult(i, REQ_PHONE_VERIFICATION);
                    }
                } else {
                    activity.toastMessage(activity.getString(R.string.you_already_verify_phone_number));
                }
            } else if (item.equals(activity.getString(R.string.facebook))) {
                if (arrayList.get(position).point == 0) {
                    showDialog();
                } else {
                    activity.toastMessage(activity.getString(R.string.you_already_verify_facebook));
                }
            } else if (item.equals(activity.getString(R.string.payment))) {
                if (arrayList.get(position).point == 0) {
                    Intent i = new Intent(activity, VerifyPaymentActivity.class);
                    activity.startActivityForResult(i, REQ_PAYMENT_VERIFICATION);
                } else {
                    activity.toastMessage(activity.getString(R.string.you_already_verify_payment));
                }
            } else if (item.equals(activity.getString(R.string.verify_id))) {
                if (arrayList.get(position).point == 0) {
//                    if (activity.checkStoragePermission()) {
//                        checkPermission(Constant.REQUEST_CODE_PICK_IMAGE);
//                    } else {
//                        new StorageDisclosureDialog(activity, () -> checkPermission(Constant.REQUEST_CODE_PICK_IMAGE));
//                    }
                } else {
                    activity.toastMessage(activity.getString(R.string.you_already_done_verifyid));
                }
            } else if (item.equals(activity.getString(R.string.cr_number))) {
                if (arrayList.get(position).point == 0) {
                    showDialog(Utils.WindowScreen.CRN);
                } else {
                    activity.toastMessage(activity.getString(R.string.you_already_verifyid_crno));
                }
            } else if (item.equals(activity.getString(R.string.vat_number))) {
                if (arrayList.get(position).point == 0) {
                    showDialog(Utils.WindowScreen.VAT);
                } else {
                    activity.toastMessage(activity.getString(R.string.you_already_verifyid_vatno));
                }
            }
        });
    }

    private void showAddContactDialog() {
        final Dialog dialog = new Dialog(activity, R.style.Theme_AppCompat_Dialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_add_contact);
        dialog.setCancelable(true);

        TextView tvNo = dialog.findViewById(R.id.tv_cancel);
        TextView tvYes = dialog.findViewById(R.id.tv_yes);

        tvNo.setOnClickListener(v -> dialog.dismiss());

        tvYes.setOnClickListener(v -> {
            dialog.dismiss();
            activity.redirectActivity(ClientProfileActivity.class);
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        dialog.show();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        lp.height = (int) (displayMetrics.heightPixels * 0.95f);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getString(R.string.influencerbird_wants_to_use_facebook_to_sign_in));
        builder.setMessage(activity.getString(R.string.this_allows_the_app_and_website_to_share_information_about_you));
        builder.setCancelable(false);

        builder.setPositiveButton(
                activity.getString(R.string.continue_),
                (dialog, id) -> {
                    dialog.cancel();
                    LoginManager.getInstance().logInWithReadPermissions(activity, Collections.singletonList("public_profile"));
                });

        builder.setNegativeButton(
                activity.getString(R.string.cancel),
                (dialog, id) -> dialog.cancel());

        AlertDialog alert = builder.create();
        Objects.requireNonNull(alert.getWindow()).getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        alert.show();
    }

    private void verifyFacebook(String fbId) {
        if (!activity.isNetworkConnected())
            return;

        HashMap<String, String> map = new HashMap<>();
        map.put("facebook_id", fbId);

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_VERIFY_FACEBOOK, true, map);
    }

    @Override
    public void onProfileLoad(Profile data) {
        profileData = data;

        if (data.trustRate != null) {
            binding.imgTriangle1.setVisibility(View.INVISIBLE);
            binding.imgTriangle2.setVisibility(View.INVISIBLE);
            binding.imgTriangle3.setVisibility(View.INVISIBLE);
            binding.imgTriangle4.setVisibility(View.INVISIBLE);
            binding.tvVeryLow.setVisibility(View.INVISIBLE);
            binding.tvLow.setVisibility(View.INVISIBLE);
            binding.tvGood.setVisibility(View.INVISIBLE);
            binding.tvVeryGood.setVisibility(View.INVISIBLE);

            getList(data.trustRate);
            setAdapter();

            int trustScore = 0;
            if (data.trustRate.email != 0) {
                trustScore = trustScore + 20;
            }
            if (data.trustRate.phoneNumber != 0) {
                trustScore = trustScore + 20;
            }
//            if (data.trustRate.facebook != 0) {
//                trustScore = trustScore + data.trustRate.facebook;
//            }
//            if (data.trustRate.payment != 0) {
//                trustScore = trustScore + data.trustRate.payment;
//            }
            if (data.trustRate.cr_id != 0) {
                trustScore = trustScore + 60;
            }
//            if (data.trustRate.vat_no != 0) {
//                trustScore = trustScore + data.trustRate.vat_no;
//            }

            binding.tvCurrentTrustScore.setText(String.format(Locale.US, activity.getString(R.string.current_trust_score) + " %d/100", trustScore));

            if (trustScore > 75) {
                binding.imgTriangle4.setVisibility(View.VISIBLE);
                binding.tvVeryGood.setVisibility(View.VISIBLE);
            } else if (trustScore > 50) {
                binding.imgTriangle3.setVisibility(View.VISIBLE);
                binding.tvGood.setVisibility(View.VISIBLE);
            } else if (trustScore > 25) {
                binding.imgTriangle2.setVisibility(View.VISIBLE);
                binding.tvLow.setVisibility(View.VISIBLE);
            } else if (trustScore > 0) {
                binding.imgTriangle1.setVisibility(View.VISIBLE);
                binding.tvVeryLow.setVisibility(View.VISIBLE);
            } else {
                binding.imgTriangle1.setVisibility(View.VISIBLE);
                binding.tvVeryLow.setVisibility(View.VISIBLE);
            }
        }

        if (nameDialog != null && bindingDialog != null && profileData.cr_file != null) {
            bindingDialog.txtFileNameCrn.setText(profileData.cr_file);
            bindingDialog.txtDate.setText(activity.getString(R.string.cr_number) + ": " + profileData.cr_number);
            bindingDialog.txtDate.invalidate();
            bindingDialog.txtFileNameCrn.invalidate();
            if (bindingDialog.txtFileNameCrn.isShown()) {

                bindingDialog.relReview.setVisibility(View.VISIBLE);
                bindingDialog.relReview.invalidate();
                bindingDialog.txtReview.setText(activity.getString(R.string.commercial_registration_number_is_under_review));
                bindingDialog.txtReview.invalidate();

            }
        }

        if (nameDialog != null && bindingDialog != null && profileData.vat_file != null) {
            bindingDialog.txtFileNameVat.setText(profileData.vat_file);
            bindingDialog.txtDateVat.setText(activity.getString(R.string.vat_number) + ": " + profileData.vat_number);
            bindingDialog.txtDateVat.invalidate();
            bindingDialog.txtFileNameVat.invalidate();
            if (bindingDialog.txtFileNameVat.isShown()) {
                bindingDialog.relReview.setVisibility(View.VISIBLE);
                bindingDialog.relReview.invalidate();
                bindingDialog.txtReview.setText(activity.getString(R.string.vat_number_is_under_review));
                bindingDialog.txtReview.invalidate();
            }
        }
    }

    void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constant.REQUEST_CODE_PICK_IMAGE:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    ArrayList<ImageFile> imgPaths = data.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE);
                    if (imgPaths != null && imgPaths.size() > 0) {
                        verifyId(new File(imgPaths.get(0).getPath()));
                    } else {
                        activity.toastMessage(activity.getString(R.string.file_not_selected));
                    }
                }
                break;
            case REQ_PHONE_VERIFICATION:
            case REQ_EMAIL_VERIFICATION:
            case REQ_PAYMENT_VERIFICATION:
                activity.getProfile();
                break;
            case 1234:
                if (resultCode == Activity.RESULT_OK && data != null) {//CRN
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
                }
                String path = null;
                try {
                    if (data != null && data.getData() != null) {
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

                break;
            case 12345:
                if (resultCode == Activity.RESULT_OK && data != null) {//vat
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
                }
                String pathVat = null;
                try {
                    if (data != null && data.getData() != null) {
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
                break;
        }
    }

    private void verifyId(File file) {
        if (!activity.isNetworkConnected())
            return;

        MultipartBody.Part body = null;
        if (file != null) {
            Uri selectedUri = Uri.fromFile(file);
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(selectedUri.toString());
            String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase());

            RequestBody requestFile = null;
            if (mimeType != null) {
                requestFile = RequestBody.create(file, MediaType.parse(mimeType));
            }

            Headers.Builder headers = new Headers.Builder();
            headers.addUnsafeNonAscii("Content-Disposition", "form-data; name=\"file\"; filename=\"" + file.getName() + "\"");

            if (requestFile != null) {
                body = MultipartBody.Part.create(headers.build(), requestFile);
            }
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("type", "1");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiImageUploadRequest(this, activity, API_PROFILE_VERIFICATIONS, body, map);
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        activity.getProfile();
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
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
                    bindingDialog.relReview.setVisibility(View.VISIBLE);
                    bindingDialog.txtReview.setText(activity.getString(R.string.commercial_registration_number_is_under_review));
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

                bindingDialog.imgDownload.setOnClickListener(v -> {
                    activity.viewFile(profileData.filePath.path_commercial_attechment + profileData.cr_file);
                });

                myProfileActivityVM.getDeleteSuccess().observe(activity, aBoolean -> {
                    if (aBoolean == 1) {
                        bindingDialog.linCrnUpload.setVisibility(View.VISIBLE);
                        bindingDialog.relSelectedCrn.setVisibility(View.GONE);
                        selectedFilePathCRN = "";
                        bindingDialog.etCrn.setText("");
                        bindingDialog.relReview.setVisibility(View.GONE);
                        myProfileActivityVM.getDeleteSuccess().postValue(0);
//                        binding.etCrNo.setText("");
                    }
                });

                bindingDialog.imgDelete.setOnClickListener(v -> {
                    if (profileData.commercial_registration_id == null || profileData.commercial_registration_id == 0) {
                        bindingDialog.linCrnUpload.setVisibility(View.VISIBLE);
                        bindingDialog.relSelectedCrn.setVisibility(View.GONE);
                        selectedFilePathCRN = "";
                        bindingDialog.etCrn.setText("");
                        bindingDialog.relReview.setVisibility(View.GONE);
//                        binding.etCrNo.setText("");
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
                    if (profileData.vat_status == 2) {//waiting
                        bindingDialog.relReview.setVisibility(View.VISIBLE);
                        bindingDialog.txtReview.setText(activity.getString(R.string.vat_number_is_under_review));
                    } else if (profileData.vat_status == 1) {//approved
                        bindingDialog.relReview.setVisibility(View.GONE);
                    }

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
                        bindingDialog.relReview.setVisibility(View.GONE);
                        myProfileActivityVM.getDeleteSuccessVat().postValue(false);
//                        binding.etVatNo.setText("");
                    }
                });

                bindingDialog.imgDeleteVat.setOnClickListener(v -> {
                    if (profileData.vat_registration_id == null || profileData.vat_registration_id == 0) {
                        bindingDialog.txtAddAttach.setVisibility(View.VISIBLE);
                        bindingDialog.relSelectedVat.setVisibility(View.GONE);
                        selectedFilePathVAT = "";
                        bindingDialog.etVat.setText("");
                        bindingDialog.relReview.setVisibility(View.GONE);
//                        binding.etVatNo.setText("");
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
                                    intent.putExtra(IS_NEED_CAMERA, true);
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
