package com.nojom.client.fragment.projects;

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
import com.nojom.client.databinding.FragmentProjectDetailsBinding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.nojom.client.R;
import com.nojom.client.adapter.binder.FilesBinder;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.model.Attachments;
import com.nojom.client.model.FileUpload;
import com.nojom.client.model.ProjectByID;
import com.nojom.client.multitypepicker.Constant;
import com.nojom.client.multitypepicker.activity.ImagePickActivity;
import com.nojom.client.multitypepicker.activity.NormalFilePickActivity;
import com.nojom.client.multitypepicker.filter.entity.ImageFile;
import com.nojom.client.multitypepicker.filter.entity.NormalFile;
import com.nojom.client.ui.clientprofile.PostJobActivity;
import com.nojom.client.ui.dialog.StorageDisclosureDialog;
import com.nojom.client.ui.projects.ProjectDetailsActivity;
import com.nojom.client.util.CompressFile;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.nojom.client.util.Constants.API_JOB_DETAILS;
import static com.nojom.client.util.Constants.API_UPLOAD_JOb_ATTACHMENT;

public class ProjectDetailsFragmentVM extends AndroidViewModel implements View.OnClickListener, RequestResponseListener {
    public static final String IS_NEED_CAMERA = "IsNeedCamera";
    private FragmentProjectDetailsBinding binding;
    private BaseFragment fragment;
    private ProjectByID projectData;
    private List<Attachments> attachmentsList;
    private SimpleRecyclerAdapter myFilesAdapter;
    private ArrayList<File> fileList;

    ProjectDetailsFragmentVM(Application application, FragmentProjectDetailsBinding projectDetailsBinding, BaseFragment projectDetailsFragment) {
        super(application);
        binding = projectDetailsBinding;
        fragment = projectDetailsFragment;
        initData();
    }

    private void initData() {
        binding.tvEdit.setOnClickListener(this);
        binding.tvUploadFile.setOnClickListener(this);
        binding.loutDescribe.setVisibility(View.GONE);
        binding.txtDetail.setVisibility(View.GONE);
        fileList = new ArrayList<>();
        if (fragment.activity != null) {
            projectData = ((ProjectDetailsActivity) fragment.activity).getProjectData();
        }
        attachmentsList = new ArrayList<>();

        if (projectData != null) {
            binding.tvJobTitle.setText(projectData.title);
            binding.tvDetails.setText(projectData.description);
            if (!TextUtils.isEmpty(projectData.deadline)) {
                String deadline = projectData.deadline.replace("T", " ");
                binding.tvDeadline.setText(Utils.setDeadLine(deadline, fragment.activity));
            }

            if (projectData.clientRateId != null && projectData.clientRateId == 0 && projectData.jobPostBudget != null && projectData.jobPostBudget.budget != null) {
                binding.tvProjectBudget.setText(String.format(Locale.US, fragment.activity.getCurrency().equals("SAR") ? fragment.getString(R.string.s_sar) : "$%s", projectData.jobPostBudget.budget));
            } else {
                if (!TextUtils.isEmpty(projectData.rangeTo)) {
                    binding.tvProjectBudget.setText(String.format(Locale.US, fragment.activity.getCurrency().equals("SAR") ? fragment.getString(R.string.s_sar_s_sar)
                            : "$%s - $%s", projectData.rangeFrom, projectData.rangeTo));
                } else if (!TextUtils.isEmpty(projectData.rangeFrom)) {
                    binding.tvProjectBudget.setText(String.format(Locale.US, fragment.activity.getCurrency().equals("SAR") ? fragment.getString(R.string.s_sar) : "$%s", projectData.rangeFrom));
                } else if (projectData.jobPostBudget != null && projectData.jobPostBudget.budget != null) {
                    binding.tvProjectBudget.setText(String.format(Locale.US, fragment.activity.getCurrency().equals("SAR") ? fragment.getString(R.string.s_sar) : "$%s", projectData.jobPostBudget.budget));
                } else {
                    binding.tvProjectBudget.setText(fragment.getString(R.string.Free));
                }
            }

            if (TextUtils.isEmpty(projectData.getSocialPlatformName(fragment.activity.getLanguage()))) {
                binding.tvService.setText(fragment.getString(R.string.all_platforms));
            } else {
                binding.tvService.setText(projectData.getSocialPlatformName(fragment.activity.getLanguage()));
            }
            if (projectData.jobPayType != null)
                binding.tvPaytype.setText(String.format("( %s )", projectData.jobPayType.getName(fragment.activity.getLanguage())));

            if (TextUtils.isEmpty(projectData.getName(fragment.activity.getLanguage()))) {
                binding.tvSkills.setText(fragment.getString(R.string.all_categories));
            } else {
                binding.tvSkills.setText(projectData.getName(fragment.activity.getLanguage()));
            }
            if (projectData.jpstateId == (Constants.BIDDING) ||
                    projectData.jpstateId == (Constants.WAITING_FOR_AGENT_ACCEPTANCE)) {
                binding.tvEdit.setVisibility(View.VISIBLE);
            }

            if (projectData.jpstateId == (Constants.BIDDING) ||
                    projectData.jpstateId == (Constants.WAITING_FOR_AGENT_ACCEPTANCE) ||
                    projectData.jpstateId == (Constants.IN_PROGRESS) ||
                    projectData.jpstateId == (Constants.WAITING_FOR_DEPOSIT) ||
                    projectData.jpstateId == (Constants.SUBMIT_WAITING_FOR_PAYMENT)) {
                binding.tvUploadFile.setVisibility(View.VISIBLE);
            }

            binding.tvJobId.setText(String.format(Locale.US, "%d", projectData.id));

            binding.tvNoTitle.setText(fragment.getString(R.string.no_files));
            binding.tvNoDescription.setText(fragment.getString(R.string.no_attached_file_desc));

            binding.rvMyFiles.setLayoutManager(new LinearLayoutManager(fragment.activity));
            if (projectData.attachments != null) {
                attachmentsList = projectData.attachments;
            }
            setFileAdapter();
        }
    }

    private void setFileAdapter() {
        if (attachmentsList != null && attachmentsList.size() > 0) {
            if (myFilesAdapter == null) {
                myFilesAdapter = new SimpleRecyclerAdapter<>(new FilesBinder(projectData.attachmentPath, false, null));
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
            case R.id.tv_edit:
                binding.tvEdit.setVisibility(View.INVISIBLE);
                binding.progressBar.setVisibility(View.VISIBLE);
                fragment.activity.isClickableView = true;
                getProjectById();
                break;
            case R.id.tv_upload_file:
                selectFileDialog();
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
        map.put("job_post_id", job_post_id);

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiImageUploadRequestBody(this, fragment.activity, API_UPLOAD_JOb_ATTACHMENT, body, map);
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
                            uploadFile();
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
        }
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data1) {
        if (url.equalsIgnoreCase(API_JOB_DETAILS)) {
            ProjectByID project = ProjectByID.getProjectById(responseBody);
            binding.tvEdit.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.GONE);
            fragment.activity.isClickableView = false;
            if (project != null) {
                projectData = project;

                Preferences.writeInteger(fragment.activity, Constants.EDIT_PROJECT_ID, projectData.id);
                Intent i = new Intent(fragment.activity, PostJobActivity.class);
                i.putExtra(Constants.IS_EDIT, true);
                i.putExtra(Constants.PROJECT, projectData);
                fragment.startActivityForResult(i, 121);
            }
        } else {
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
        }
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        if (url.equalsIgnoreCase(API_JOB_DETAILS)) {
            binding.tvEdit.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.GONE);
            fragment.activity.isClickableView = false;
        } else {
            for (Attachments att : attachmentsList) {//remove temp file from list
                if (att.filename.equals("temp")) {
                    attachmentsList.remove(att);
                }
            }
            setFileAdapter();
        }
    }

    private void getProjectById() {
        if (!fragment.activity.isNetworkConnected()) {
            return;
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("job_id", projectData.id + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, fragment.activity, API_JOB_DETAILS, true, map);
    }
}
