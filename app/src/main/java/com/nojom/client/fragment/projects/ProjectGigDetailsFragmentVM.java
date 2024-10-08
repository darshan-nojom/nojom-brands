package com.nojom.client.fragment.projects;

import static android.app.Activity.RESULT_OK;
import static com.nojom.client.util.Constants.API_REMOVE_CLIENT_CONTRACT_ATTACHMENT;
import static com.nojom.client.util.Constants.API_UPLOAD_CLIENT_CONTRACT_ATTACHMENT;

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
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ahamed.multiviewadapter.SimpleRecyclerAdapter;
import com.google.gson.Gson;
import com.nojom.client.R;
import com.nojom.client.adapter.CustomGigDetailAdapter;
import com.nojom.client.adapter.GigAdapter;
import com.nojom.client.adapter.SocialGigDetailsAdapter;
import com.nojom.client.adapter.binder.FilesBinder;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.FragmentProjectDetailsBinding;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.model.Attachments;
import com.nojom.client.model.FileUpload;
import com.nojom.client.model.ProjectGigByID;
import com.nojom.client.model.Requirement;
import com.nojom.client.multitypepicker.Constant;
import com.nojom.client.multitypepicker.activity.ImagePickActivity;
import com.nojom.client.multitypepicker.activity.NormalFilePickActivity;
import com.nojom.client.multitypepicker.filter.entity.ImageFile;
import com.nojom.client.multitypepicker.filter.entity.NormalFile;
import com.nojom.client.ui.dialog.StorageDisclosureDialog;
import com.nojom.client.ui.projects.AddDescribeActivity;
import com.nojom.client.ui.projects.ProjectGigDetailsActivity;
import com.nojom.client.util.CompressFile;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Utils;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProjectGigDetailsFragmentVM extends AndroidViewModel implements View.OnClickListener, RequestResponseListener {
    public static final String IS_NEED_CAMERA = "IsNeedCamera";
    private final FragmentProjectDetailsBinding binding;
    private final BaseFragment fragment;
    private ProjectGigByID projectData;
    private List<Attachments> attachmentsList;
    private SimpleRecyclerAdapter myFilesAdapter;
    private ArrayList<File> fileList;
    private int selectedAttachmentID = 0;
    private String gigType = "";

    ProjectGigDetailsFragmentVM(Application application, FragmentProjectDetailsBinding projectDetailsBinding, BaseFragment projectDetailsFragment) {
        super(application);
        binding = projectDetailsBinding;
        fragment = projectDetailsFragment;
        initData();
    }

    private void initData() {
        try {
            binding.loutProjectDetails.setVisibility(View.GONE);
            binding.loutJobDescribe.setVisibility(View.VISIBLE);
            binding.loutGigDetails.setVisibility(View.VISIBLE);
            binding.tvUploadFile.setOnClickListener(this);
//            binding.btnAddDescribe.setOnClickListener(this);
            binding.loutDescribe.setOnClickListener(this);

            fileList = new ArrayList<>();
            if (fragment.activity != null) {
                projectData = ((ProjectGigDetailsActivity) fragment.activity).getProjectData();
                gigType = ((ProjectGigDetailsActivity) fragment.activity).getGigType();

            }
            attachmentsList = new ArrayList<>();

            if (projectData != null) {
                binding.tvJobTitleGig.setText(projectData.gigTitle);
                binding.tvProjectPriceGig.setText(String.format(Locale.US, fragment.activity.getCurrency().equals("SAR") ? fragment.getString(R.string.s_sar) : "$%s", projectData.totalPrice));
                binding.tvDetailsGig.setText(projectData.gigDescription);

                if (gigType.equalsIgnoreCase("1") || gigType.equalsIgnoreCase("3")) {
                    binding.loutPackageDetails.setVisibility(View.GONE);
                    binding.loutCustomPackageDetails.setVisibility(View.VISIBLE);
                    binding.tvCustomDeliveryDays.setText(projectData.deliveryTitle);

                    if (projectData.deadlineType.equalsIgnoreCase("1")) {
                        binding.tvCustomDeliveryDays.setText(projectData.deadlineValue + " " + fragment.activity.getString(R.string.hours));
                    } else {
                        binding.tvCustomDeliveryDays.setText(projectData.deadlineValue + " " + fragment.activity.getString(R.string.days));
                    }

                    if (projectData.minPrice == 0) {
                        binding.loutCustomPackagesPrice.setVisibility(View.GONE);
                        binding.tvCustomPackagesPrice.setText(fragment.activity.getString(R.string.free).toUpperCase());
                    } else {
                        binding.loutCustomPackagesPrice.setVisibility(View.VISIBLE);
                        binding.tvCustomPackagesPrice.setText(fragment.activity.getCurrency().equals("SAR") ? Utils.decimalFormat(String.valueOf(projectData.minPrice)) + " "+fragment.getString(R.string.sar)
                                : "$" + Utils.decimalFormat(String.valueOf(projectData.minPrice)));
                    }

                    binding.rvCustomGigItem.setLayoutManager(new LinearLayoutManager(fragment.activity));
                    binding.rvCustomGigItem.setNestedScrollingEnabled(false);
                    if (projectData.customPackages != null) {
                        setGigCustomAdapter(projectData.customPackages);
                    }
                } else {
                    binding.loutCustomPackageDetails.setVisibility(View.GONE);
                    binding.loutPackageDetails.setVisibility(View.VISIBLE);
                    binding.tvDeliveryDays.setText(projectData.deliveryTitle);
                    binding.tvRevisionsDays.setText(projectData.revisions + "");
                    binding.tvPackageTitle.setText(projectData.gigPackageName);
                    binding.tvPackageDesc.setText(projectData.gigPackageDescription);
                    binding.tvPaytypeGig.setText("( " + projectData.packageName + " " + fragment.activity.getString(R.string.plan) + " )");
                    binding.tvQuantity.setText(projectData.quantity + "");
                }

                if (projectData.gigStateID == (Constants.BIDDING) ||
                        projectData.gigStateID == (Constants.WAITING_FOR_AGENT_ACCEPTANCE) ||
                        projectData.gigStateID == (Constants.IN_PROGRESS) ||
                        projectData.gigStateID == (Constants.WAITING_FOR_DEPOSIT) ||
                        projectData.gigStateID == (Constants.SUBMIT_WAITING_FOR_PAYMENT)) {
                    binding.tvUploadFile.setVisibility(View.VISIBLE);
                }

                if (!TextUtils.isEmpty(projectData.clientJobDescribe)) {
                    binding.btnAddDescribe.setVisibility(View.GONE);
                    binding.tvDescribe.setText(projectData.clientJobDescribe);
                } else {
                    binding.btnAddDescribe.setVisibility(View.VISIBLE);
                }
                binding.tvJobId.setText(String.format(Locale.US, "%d", projectData.id));

                binding.tvNoTitle.setText(fragment.getString(R.string.no_files));
                binding.tvNoDescription.setText(fragment.getString(R.string.no_attached_file_desc));

                binding.rvMyFiles.setLayoutManager(new LinearLayoutManager(fragment.activity));
                if (projectData.attachments != null) {
                    attachmentsList = projectData.attachments;
                }
                setFileAdapter();
                binding.rvGigItem.setLayoutManager(new LinearLayoutManager(fragment.activity));
                binding.rvGigItem.setNestedScrollingEnabled(false);

                binding.rvGigSocial.setLayoutManager(new LinearLayoutManager(fragment.activity));
                binding.rvGigSocial.setNestedScrollingEnabled(false);

                if (projectData.requirements != null) {
                    setGigAdapter(projectData.requirements);
                }

                if (projectData.mainCategoryID == 4352) {
                    binding.rvGigSocial.setVisibility(View.VISIBLE);
                    if (projectData.socialPlatform != null && projectData.socialPlatform.size() > 0) {
                        setSocialGigAdapter(projectData.socialPlatform);
                    }
                } else {
                    binding.rvGigSocial.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.btnAddDescribe.setOnClickListener(v -> {
            fragment.startActivityForResult(new Intent(fragment.activity, AddDescribeActivity.class)
                    .putExtra(Constants.ADD_DESCRIBE, getDescribe())
                    .putExtra(Constants.CONTRACT_ID, projectData.id), 500);
        });
    }

    private void setGigAdapter(List<Requirement> arrGigPackages) {
        if (arrGigPackages.size() > 0) {
            binding.rvGigItem.setVisibility(View.VISIBLE);
            GigAdapter gigAdapter = new GigAdapter(fragment.activity, arrGigPackages, true);
            binding.rvGigItem.setAdapter(gigAdapter);
        } else {
            binding.rvGigItem.setVisibility(View.GONE);
        }
    }

    private void setGigCustomAdapter(List<ProjectGigByID.CustomPackage> arrGigPackages) {
        if (arrGigPackages.size() > 0) {
            binding.rvCustomGigItem.setVisibility(View.VISIBLE);
            CustomGigDetailAdapter gigAdapter = new CustomGigDetailAdapter(fragment.activity, arrGigPackages);
            binding.rvCustomGigItem.setAdapter(gigAdapter);
        } else {
            binding.rvCustomGigItem.setVisibility(View.GONE);
        }
    }

    private void setSocialGigAdapter(List<ProjectGigByID.SocialPlatform> arrSocialGigPackages) {
        if (arrSocialGigPackages.size() > 0) {
            binding.rvGigSocial.setVisibility(View.VISIBLE);
            SocialGigDetailsAdapter socialGigAdapter = new SocialGigDetailsAdapter(fragment.activity, arrSocialGigPackages);
            binding.rvGigSocial.setAdapter(socialGigAdapter);
        } else {
            binding.rvGigSocial.setVisibility(View.GONE);
        }
    }

    private void setFileAdapter() {
        if (attachmentsList != null && attachmentsList.size() > 0) {
            if (myFilesAdapter == null) {
                myFilesAdapter = new SimpleRecyclerAdapter<>(new FilesBinder(projectData.attachmentPath, true, attachmentID -> {
                    selectedAttachmentID = attachmentID;
                    deleteFile();
                }));
            }

            if (binding.rvMyFiles.getAdapter() == null) {
                binding.rvMyFiles.setAdapter(myFilesAdapter);
            }

            myFilesAdapter.setData(attachmentsList);
            binding.rvMyFiles.setVisibility(View.VISIBLE);
            binding.noData.setVisibility(View.GONE);
        } else {
            binding.rvMyFiles.setVisibility(View.GONE);
            binding.noData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_upload_file:
                selectFileDialog();
                break;
        }
    }

    private String getDescribe() {
        return binding.tvDescribe.getText().toString().trim();
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
            if (fragment.activity.checkStoragePermission()) {
                checkPermission(false);
            } else {
                new StorageDisclosureDialog(fragment.activity, () -> checkPermission(false));
            }
            dialog.dismiss();
        });

        llDocument.setOnClickListener(v -> {
            if (fragment.activity.checkStoragePermission()) {
                checkPermission(true);
            } else {
                new StorageDisclosureDialog(fragment.activity, () -> checkPermission(true));
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
    }


    private void uploadFile() {
        if (!fragment.activity.isNetworkConnected())
            return;

        MultipartBody.Part[] body = null;
        if (fileList != null && fileList.size() > 0) {
            body = new MultipartBody.Part[fileList.size()];
            for (int i = 0; i < fileList.size(); i++) {
                File file;
                if (fileList.get(i).getAbsolutePath().contains(".png") || fileList.get(i).getAbsolutePath().contains(".jpg") || fileList.get(i).getAbsolutePath().contains(".jpeg")) {
                    file = CompressFile.getCompressedImageFile(fileList.get(i), fragment.activity);
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
                headers.addUnsafeNonAscii("Content-Disposition", "form-data; name=\"file\"; filename=\"" + file.getName() + "\"");

                if (requestFile != null) {
                    body[i] = MultipartBody.Part.create(headers.build(), requestFile);
                }
            }
        }

        RequestBody job_post_id = RequestBody.create(String.valueOf(projectData.id), MultipartBody.FORM);

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("contractID", job_post_id);

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiImageUploadRequestBody(this, fragment.activity, API_UPLOAD_CLIENT_CONTRACT_ATTACHMENT, body, map);
    }

    private void deleteFile() {
        if (!fragment.activity.isNetworkConnected())
            return;

        fragment.activity.isClickableView = true;

        HashMap<String, String> map = new HashMap<>();
        map.put("contractID", projectData.id + "");
        map.put("attachmentID", selectedAttachmentID + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, fragment.activity, API_REMOVE_CLIENT_CONTRACT_ATTACHMENT, true, map);
    }

    void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constant.REQUEST_CODE_PICK_FILE:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    try {
                        ArrayList<NormalFile> docPaths = data.getParcelableArrayListExtra(Constant.RESULT_PICK_FILE);
                        if (docPaths != null && docPaths.size() > 0) {
                            for (NormalFile file : docPaths) {
                                fileList.add(new File(file.getPath()));
                            }
                            attachmentsList.add(new Attachments(-1, "", "temp", -1, -1));
                            setFileAdapter();
                            uploadFile();
                        } else {
                            fragment.activity.toastMessage(fragment.activity.getString(R.string.file_not_selected));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 4545://doc picker for android 10+
                String path = null;
                try {
                    if (data != null && data.getData() != null) {
                        path = Utils.getFilePath(fragment.activity, data.getData());
                        if (path != null) {
                            fileList.add(new File(path));
                            attachmentsList.add(new Attachments(-1, "", "temp", -1, -1));
                            setFileAdapter();
                        } else {
                            fragment.activity.toastMessage(fragment.activity.getString(R.string.file_not_selected));
                        }
                    }
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                break;
            case Constant.REQUEST_CODE_PICK_IMAGE:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    try {
                        ArrayList<ImageFile> imgPath = data.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE);
                        if (imgPath != null && imgPath.size() > 0 && fileList != null) {
                            for (ImageFile file : imgPath) {
                                fileList.add(new File(file.getPath()));
                            }
                            attachmentsList.add(new Attachments(-1, "", "temp", -1, -1));
                            setFileAdapter();
                            uploadFile();

                        } else {
                            fragment.activity.toastMessage(fragment.activity.getString(R.string.file_not_selected));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 500:
                if (resultCode == RESULT_OK && data != null) {
                    String gigDescribe = data.getStringExtra(Constants.ADD_DESCRIBE);
                    if (!TextUtils.isEmpty(gigDescribe)) {
                        binding.tvDescribe.setText(gigDescribe);
                    }
                }
                break;
        }
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data1) {
        try {
            if (url.equalsIgnoreCase(API_UPLOAD_CLIENT_CONTRACT_ATTACHMENT)) {
                FileUpload fileUpload = FileUpload.getAttachmentList(responseBody);
                if (fileUpload != null && fileUpload.data != null) {
                    fileList.clear();
                    for (Attachments att : attachmentsList) {//remove temp file from list
                        if (att.filename.equals("temp")) {
                            attachmentsList.remove(att);
                        }
                    }
                    for (FileUpload.Data data : fileUpload.data) {
                        String jsonString = new Gson().toJson(data);
                        Attachments attachments = new Gson().fromJson(jsonString, Attachments.class);
                        attachmentsList.add(attachments);
                    }
                }

                projectData.attachments = attachmentsList;
                setFileAdapter();
            } else if (url.equalsIgnoreCase(API_REMOVE_CLIENT_CONTRACT_ATTACHMENT)) {
                Iterator<Attachments> iterator = attachmentsList.iterator();
                while (iterator.hasNext()) {
                    Attachments file = iterator.next();
                    if (file.id == selectedAttachmentID) {
                        iterator.remove();
                        break;
                    }
                }

                //Todo Improvement
                projectData.attachments = attachmentsList;
                setFileAdapter();
                fragment.activity.isClickableView = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        if (url.equalsIgnoreCase(API_UPLOAD_CLIENT_CONTRACT_ATTACHMENT)) {
            for (Attachments att : attachmentsList) {//remove temp file from list
                if (att.filename.equals("temp")) {
                    attachmentsList.remove(att);
                }
            }
            setFileAdapter();
        } else if (url.equalsIgnoreCase(API_REMOVE_CLIENT_CONTRACT_ATTACHMENT)) {
            fragment.activity.isClickableView = false;
        }
    }
}
