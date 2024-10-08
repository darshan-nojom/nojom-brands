package com.nojom.client.ui.clientprofile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;

import androidx.lifecycle.AndroidViewModel;

import com.bumptech.glide.Glide;
import com.nojom.client.databinding.ActivityProfileInfoBinding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.nojom.client.R;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.cropper.CropImage;
import com.nojom.client.cropper.CropImageView;
import com.nojom.client.facedetection.FaceCenterCircleView.FaceCenterCrop;
import com.nojom.client.facedetection.ProgressBarUtil.Imageutils;
import com.nojom.client.facedetection.ProgressBarUtil.ProgressBarData;
import com.nojom.client.facedetection.ProgressBarUtil.ProgressUtils;
import com.nojom.client.model.Profile;
import com.nojom.client.multitypepicker.Constant;
import com.nojom.client.multitypepicker.activity.ImagePickActivity;
import com.nojom.client.multitypepicker.filter.entity.ImageFile;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.auth.UpdatePasswordActivity;
import com.nojom.client.ui.dialog.StorageDisclosureDialog;
import com.nojom.client.ui.workprofile.VerificationActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;
import static com.nojom.client.cropper.CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE;
import static com.nojom.client.facedetection.ProgressBarUtil.Imageutils.SCANNER_REQUEST_CODE;
import static com.nojom.client.multitypepicker.Constant.REQUEST_CODE_PICK_IMAGE;
import static com.nojom.client.multitypepicker.activity.ImagePickActivity.IS_NEED_CAMERA;
import static com.nojom.client.util.Constants.API_UPDATE_PROFILE_PIC;

class ProfileInfoActivityVM extends AndroidViewModel implements View.OnClickListener, RequestResponseListener {
    //    private Imageutils imageutils;
//    private Imageutils.ImageAttachmentListener imageAttachmentListener;
//    private FaceCenterCrop faceCenterCrop;
//    private ProgressUtils progressUtils;
    private ActivityProfileInfoBinding binding;
    private BaseActivity activity;
    private Profile profileData;
    private String selectedFilePath = "";

    ProfileInfoActivityVM(Application application, ActivityProfileInfoBinding profileInfoBinding, BaseActivity profileInfoActivity) {
        super(application);
        binding = profileInfoBinding;
        activity = profileInfoActivity;
        initData();
    }

    private void initData() {
        binding.imgProfile.setOnClickListener(this);
        binding.rlPrivateProfile.setOnClickListener(this);
        binding.txtEditProfile.setOnClickListener(this);
        binding.rlVerification.setOnClickListener(this);
        binding.rlUsername.setOnClickListener(this);
        binding.rlPassword.setOnClickListener(this);
        binding.toolbar.imgBack.setOnClickListener(this);
        binding.rlPublicProfile.setOnClickListener(this);

        boolean isSocialLoggedIn = Preferences.readBoolean(activity, Constants.IS_SOCIAL_LOGIN, false);
        if (isSocialLoggedIn) {
            binding.rlPassword.setVisibility(View.GONE);
        }

//        imageutils = new Imageutils(activity);
//        imageutils.setImageAttachment_callBack(getImageAttachmentCallback());
//
//        progressUtils = new ProgressUtils(activity);
//
//        faceCenterCrop = new FaceCenterCrop(activity, 100, 100, 1);

        Utils.trackAppsFlayerEvent(activity, "open_profile_screen");

    }

//    private Imageutils.ImageAttachmentListener getImageAttachmentCallback() {
//        if (imageAttachmentListener == null)
//            imageAttachmentListener = (from, filename, file, uri, filePath) -> {
//                selectedFilePath = filePath;
//
//                if (from == SCANNER_REQUEST_CODE) {
//                } else if (from == CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//                    ProgressBarData progressBarData = new ProgressBarData.ProgressBarBuilder()
//                            .setCancelable(true)
//                            .setProgressMessage(activity.getString(R.string.processing))
//                            .setProgressMessageColor(Color.parseColor("#4A4A4A"))
//                            .setBackgroundViewColor(Color.parseColor("#FFFFFF"))
//                            .setProgressbarTintColor(Color.parseColor("#FAC42A")).build();
//
//                    progressUtils.showDialog(progressBarData);
//
//                    faceCenterCrop.detectFace(file, new FaceCenterCrop.FaceCenterCropListener() {
//                        @Override
//                        public void onTransform(Bitmap updatedBitmap) {
//                            activity.setImage(binding.imgProfile, "file://" + selectedFilePath, 80, 80);
//                            activity.toastMessage(activity.getString(R.string.we_detected_a_face));
//                            progressUtils.dismissDialog();
//                            updateProfilePic();
//                        }
//
//                        @Override
//                        public void onFailure() {
//                            activity.toastMessage(activity.getString(R.string.no_face_was_detected));
//                            progressUtils.dismissDialog();
//                        }
//                    });
//                }
//            };
//
//        return imageAttachmentListener;
//    }

    void onResumeMethod() {
        profileData = Preferences.getProfileData(activity);

        if (profileData != null) {
            onProfileLoadData(profileData);
        }
    }

    @SuppressLint({"StringFormatInvalid", "StringFormatMatches"})
    private void onProfileLoadData(Profile profileData) {
//        activity.setImage(binding.imgProfile, TextUtils.isEmpty(profileData.profilePic) ? "" : profileData.filePath.pathProfilePicClient + profileData.profilePic, 0, 0);

        Glide
                .with(activity)
                .load(TextUtils.isEmpty(profileData.profilePic) ? "" : profileData.filePath.pathProfilePicClient + profileData.profilePic)
                .centerCrop()
                .placeholder(R.drawable.dp)
                .into(binding.imgProfile);

        if (profileData.percentage != null) {
            String profilePercentage = String.format(Locale.getDefault(), "%d%%", profileData.percentage.totalPercentage);
            String verificationPercentage = String.format(Locale.getDefault(), "%d%%", profileData.percentage.verification);

            binding.tvProfileComplete.setText(activity.getString(R.string.percent_complete, profilePercentage));
            binding.tvVerifications.setText(activity.getString(R.string.percent_complete, verificationPercentage));
        } else {
            binding.tvProfileComplete.setVisibility(View.INVISIBLE);
            binding.tvVerifications.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                activity.onBackPressed();
                break;
            case R.id.rl_private_profile:
                activity.redirectActivity(ClientProfileActivity.class);
                break;
            case R.id.txt_edit_profile:
                if (activity.checkStoragePermission()) {
                    checkPermission();
                } else {
                    new StorageDisclosureDialog(activity, () -> checkPermission());
                }
                break;
            case R.id.rl_verification:
                activity.redirectActivity(VerificationActivity.class);
                break;
            case R.id.rl_username:
                activity.redirectActivity(UsernameActivity.class);
                break;
            case R.id.rl_password:
                activity.redirectActivity(UpdatePasswordActivity.class);
                break;
            case R.id.rl_public_profile:
//                activity.redirectActivity(MyProfileActivity.class);
                activity.redirectActivity(PublicProfileActivity.class);
                break;
        }
    }

    public void checkPermission() {
        Dexter.withActivity(activity)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            Intent intent = new Intent(activity, ImagePickActivity.class);
                            intent.putExtra(IS_NEED_CAMERA, true);
                            intent.putExtra(Constant.MAX_NUMBER, 1);
                            activity.startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
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
        if (requestCode == REQUEST_CODE_PICK_IMAGE) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<ImageFile> imgPath = data.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE);
                if (imgPath != null && imgPath.size() > 0) {
//                    CropImage.activity(Uri.fromFile(new File(imgPath.get(0).getPath())))
//                            .setCropShape(CropImageView.CropShape.RECTANGLE)
//                            .setAspectRatio(1, 1)
//                            .setGuidelines(CropImageView.Guidelines.OFF)
//                            .setAllowFlipping(false)
//                            .setMinCropResultSize((int) activity.getResources().getDimension(R.dimen._100sdp), (int) activity.getResources().getDimension(R.dimen._100sdp))
//                            .setMaxCropResultSize((int) activity.getResources().getDimension(R.dimen._599sdp), (int) activity.getResources().getDimension(R.dimen._599sdp))
//                            .setFixAspectRatio(true)
//                            .setAllowRotation(false)
//                            .start(activity);
                    selectedFilePath = imgPath.get(0).getPath();
                    updateProfilePic();
                }
            }
        } else if (requestCode == CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    selectedFilePath = null;
//                    imageutils.onActivityResult(requestCode, resultCode, data);
                } catch (Exception ex) {
                    activity.toastMessage(ex.toString());
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                activity.toastMessage(activity.getString(R.string.something_went_wrong));
            }
        }
    }

    private void updateProfilePic() {
        if (!activity.isNetworkConnected())
            return;

        binding.progressBarProfile.setVisibility(View.VISIBLE);
        activity.isClickableView = true;

        MultipartBody.Part body = null;
        if (selectedFilePath != null) {
            File file = new File(selectedFilePath);
            Uri selectedUri = Uri.fromFile(file);
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(selectedUri.toString());
            String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase());

            RequestBody requestFile = null;
            if (mimeType != null) {
                requestFile = RequestBody.create(file, MediaType.parse(mimeType));
            }

            Headers.Builder headers = new Headers.Builder();
            headers.addUnsafeNonAscii("Content-Disposition", "form-data; name=\"profile\"; filename=\"" + file.getName() + "\"");

            if (requestFile != null) {
                body = MultipartBody.Part.create(headers.build(), requestFile);
            }
        }

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiImageUploadRequest(this, activity, API_UPDATE_PROFILE_PIC, body, null);
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        Profile profile = Profile.getProfileInfo(responseBody);
        if (profile != null) {
            if (!activity.isEmpty(profile.profilePic)) {
                profileData.filePath.pathProfilePicClient = profile.path;
                profileData.profilePic = profile.profilePic;
            }

            Preferences.setProfileData(activity, profileData);
            onProfileLoadData(profileData);
        }

        binding.progressBarProfile.setVisibility(View.GONE);
        activity.isClickableView = false;
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        binding.progressBarProfile.setVisibility(View.GONE);
        activity.isClickableView = false;
    }
}
