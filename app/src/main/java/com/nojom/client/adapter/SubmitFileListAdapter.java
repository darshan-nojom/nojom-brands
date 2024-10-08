package com.nojom.client.adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.BuildConfig;
import com.nojom.client.databinding.ItemListFilesBinding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.nojom.client.R;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.model.FileList;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.dialog.StorageDisclosureDialog;
import com.nojom.client.util.Utils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import static com.nojom.client.util.Constants.API_DOWNLOAD_FILE;

public class SubmitFileListAdapter extends RecyclerView.Adapter<SubmitFileListAdapter.SimpleViewHolder> implements RequestResponseListener {

    private ArrayList<FileList.FilesDatum> mDataset;
    private Context context;
    private BaseActivity activity;
    private String filePath;

    public SubmitFileListAdapter(Context context, ArrayList<FileList.FilesDatum> objects, String submittedPath) {
        this.mDataset = objects;
        this.context = context;
        this.filePath = submittedPath;
        activity = (BaseActivity) context;
    }

    @NotNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemListFilesBinding itemListFilesBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_list_files, parent, false);
        return new SimpleViewHolder(itemListFilesBinding);
    }

    @Override
    public void onBindViewHolder(@NotNull final SimpleViewHolder holder, final int position) {
        FileList.FilesDatum item = mDataset.get(position);
        try {
            holder.binding.tvFileName.setText(item.filename);
            holder.binding.tvDate.setText(Utils.formatFileSize(activity,String.valueOf(item.size)));
            holder.binding.imgView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.eye_gray));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mDataset != null ? mDataset.size() : 0;
    }

    public ArrayList<FileList.FilesDatum> getData() {
        return mDataset;
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
//        activity.hideProgress();
        showOutput(message, isDownload, file, isEmailShare, isShare);
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {

    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemListFilesBinding binding;

        SimpleViewHolder(ItemListFilesBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            itemView.getRoot().setOnClickListener(v -> showOptionDialog(mDataset.get(getAbsoluteAdapterPosition())));
        }
    }

    private void showOptionDialog(final FileList.FilesDatum userFiles) {
        @SuppressLint("PrivateResource") final Dialog dialog = new Dialog(activity, R.style.Theme_Design_Light_BottomSheetDialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_file_option_menu);
        dialog.setCancelable(true);

        View llView = dialog.findViewById(R.id.ll_view);
        View llDownload = dialog.findViewById(R.id.ll_download);
        View llEmail = dialog.findViewById(R.id.ll_email);
        View llShare = dialog.findViewById(R.id.ll_share);
        View llUpload = dialog.findViewById(R.id.ll_upload);
        TextView btnCancel = dialog.findViewById(R.id.btn_cancel);

        llEmail.setVisibility(View.VISIBLE);
        llShare.setVisibility(View.VISIBLE);
        llUpload.setVisibility(View.GONE);

        llView.setOnClickListener(v -> {
            dialog.dismiss();
            ((BaseActivity) context).viewFile(filePath + userFiles.filename);
        });

        llDownload.setOnClickListener(v -> {
            dialog.dismiss();
            if (activity.checkStoragePermission()) {
                checkPermission(userFiles, true, false, false);
            } else {
                new StorageDisclosureDialog(activity, () -> checkPermission(userFiles, true, false, false));
            }
        });

        llShare.setOnClickListener(v -> {
            dialog.dismiss();
            if (activity.checkStoragePermission()) {
                checkPermission(userFiles, false, false, true);
            } else {
                new StorageDisclosureDialog(activity, () -> checkPermission(userFiles, false, false, true));
            }
        });

        llEmail.setOnClickListener(v -> {
            dialog.dismiss();
            if (activity.checkStoragePermission()) {
                checkPermission(userFiles, false, true, false);
            } else {
                new StorageDisclosureDialog(activity, () -> checkPermission(userFiles, false, true, false));
            }
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }

    private boolean isDownload, isEmailShare, isShare;
    private File file;

    private void downloadFile(final FileList.FilesDatum attachments, final boolean isDownload, final boolean isEmailShare, final boolean isShare) {
        File folder = new File(Environment.getExternalStorageDirectory(), "/Download/" + context.getString(R.string.app_name));
        if (!folder.exists())
            folder.mkdir();

        final File file = new File(folder, attachments.filename);

        if (!file.exists()) {
            this.isDownload = isDownload;
            this.isEmailShare = isEmailShare;
            this.isShare = isShare;
            this.file = file;

            ApiRequest apiRequest = new ApiRequest();
            apiRequest.apiRequest(this, activity, API_DOWNLOAD_FILE + "file_id=" + attachments.id, false, null);
        } else {
            showOutput(activity.getString(R.string.already_downloaded), isDownload, file, isEmailShare, isShare);
        }
    }

    private void showOutput(String message, boolean isDownload, File file, boolean isEmailShare, boolean isShare) {
        if (!isDownload) {
            if (isShare || isEmailShare)
                shareFile(file, isEmailShare);
            else
                activity.viewFile(file);
        } else {
            activity.toastMessage(message);
        }
    }

    private void shareFile(File file, boolean isEmailShare) {

        Uri uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file);
        Intent intentShareFile = new Intent(isEmailShare ? Intent.ACTION_SENDTO : Intent.ACTION_SEND);
        intentShareFile.setType("/");
        if (isEmailShare) {
            String mime;
            if (file.getAbsolutePath().contains(".doc") || file.getAbsolutePath().contains(".docx")) {
                mime = "application/msword";
            } else if (file.getAbsolutePath().contains(".txt")) {
                mime = "text/plain";
            } else if (file.getAbsolutePath().contains(".pdf")) {
                mime = "application/pdf";
            } else if (file.getAbsolutePath().contains(".ppt") || file.getAbsolutePath().contains(".pptx")) {
                mime = "application/vnd.ms-powerpoint";
            } else if (file.getAbsolutePath().contains(".xls") || file.getAbsolutePath().contains(".xlsx")) {
                mime = "application/vnd.ms-excel";
            } else if (file.getAbsolutePath().contains(".jpg") || file.getAbsolutePath().contains(".png") ||
                    file.getAbsolutePath().contains(".jpeg") || file.getAbsolutePath().contains(".gif")) {
                mime = "image/*";
            } else if (file.getAbsolutePath().contains(".zip") || file.getAbsolutePath().contains(".rar")) {
                // WAV audio file
                mime = "application/x-wav";
            } else if (file.getAbsolutePath().contains(".mp4") || file.getAbsolutePath().contains(".avi")) {
                mime = "video/*";
            } else {
                mime = "/";
            }
            intentShareFile.setType(mime);
            uri = Uri.parse("file://" + file.toString());
            intentShareFile.setData(Uri.parse("mailto:"));
            intentShareFile.putExtra(Intent.EXTRA_EMAIL, "");
        }
        intentShareFile.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intentShareFile.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intentShareFile.putExtra(Intent.EXTRA_STREAM, uri);
        context.startActivity(Intent.createChooser(intentShareFile, "Share File"));
    }

    private void checkPermission(final FileList.FilesDatum userFiles, final boolean isDownload, final boolean isEmailShare, final boolean isShare) {
        Dexter.withActivity(activity)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        downloadFile(userFiles, isDownload, isEmailShare, isShare);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        activity.toastMessage(activity.getString(R.string.give_storage_permission_first));
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }
}
