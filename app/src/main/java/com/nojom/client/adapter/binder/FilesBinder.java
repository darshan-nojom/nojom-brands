package com.nojom.client.adapter.binder;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import com.ahamed.multiviewadapter.BaseViewHolder;
import com.ahamed.multiviewadapter.ItemBinder;
import com.nojom.client.BuildConfig;
import com.nojom.client.databinding.ItemListFilesBinding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.nojom.client.R;
import com.nojom.client.model.Attachments;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.dialog.StorageDisclosureDialog;
import com.nojom.client.util.Constants;
import com.nojom.client.util.MyDownloadManager;
import com.nojom.client.util.Utils;

import java.io.File;
import java.util.Objects;

public class FilesBinder extends ItemBinder<Attachments, FilesBinder.FilesViewHolder> {

    private Context mContext;
    private String filePath;
    private boolean isShowDelete = false;
    private OnClickDeleteFileListener onClickDeleteFileListener;

    public FilesBinder(String attachmentPath, boolean isShowDelete, OnClickDeleteFileListener onClickDeleteFileListener) {
        this.filePath = attachmentPath;
        this.isShowDelete = isShowDelete;
        this.onClickDeleteFileListener = onClickDeleteFileListener;
    }

    @Override
    public FilesViewHolder create(LayoutInflater inflater, ViewGroup parent) {
        mContext = parent.getContext();
        ItemListFilesBinding itemListFilesBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_list_files, parent, false);
        return new FilesViewHolder(itemListFilesBinding);
    }

    @Override
    public boolean canBindData(Object item) {
        return item instanceof Attachments;
    }

    @Override
    public void bind(FilesViewHolder holder, final Attachments item) {
        if (item.filename.equalsIgnoreCase("temp")) {
            holder.binding.progressBarSave.setVisibility(View.VISIBLE);
            holder.binding.tvFileName.setVisibility(View.INVISIBLE);
            holder.binding.imgFolder.setVisibility(View.INVISIBLE);
            holder.binding.imgView.setVisibility(View.INVISIBLE);
            holder.binding.tvOwner.setVisibility(View.INVISIBLE);
        } else {
            holder.binding.tvFileName.setVisibility(View.VISIBLE);
            holder.binding.imgFolder.setVisibility(View.VISIBLE);
            holder.binding.imgView.setVisibility(View.VISIBLE);
            holder.binding.tvOwner.setVisibility(View.VISIBLE);

            holder.binding.progressBarSave.setVisibility(View.GONE);
            holder.binding.tvFileName.setText(item.filename);
            holder.binding.tvDate.setText(Utils.changeDateFormat("yyyy-MM-dd'T'hh:mm:ss", "dd/MM/yyyy", item.timestamp));
            holder.binding.tvOwner.setText(mContext.getString(R.string.by_me));
            holder.binding.imgView.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.eye_gray));

            holder.binding.imgView.setOnClickListener(v -> showOptionDialog(item));
        }

    }

    private void showOptionDialog(final Attachments userFiles) {
        @SuppressLint("PrivateResource") final Dialog dialog = new Dialog(mContext, R.style.Theme_Design_Light_BottomSheetDialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_file_option_menu);
        dialog.setCancelable(true);

        View llView = dialog.findViewById(R.id.ll_view);
        View llDownload = dialog.findViewById(R.id.ll_download);
        View llEmail = dialog.findViewById(R.id.ll_email);
        View llShare = dialog.findViewById(R.id.ll_share);
        View llUpload = dialog.findViewById(R.id.ll_upload);
        View llDelete = dialog.findViewById(R.id.ll_delete);
        TextView btnCancel = dialog.findViewById(R.id.btn_cancel);

        llEmail.setVisibility(View.VISIBLE);
        llShare.setVisibility(View.VISIBLE);
        llUpload.setVisibility(View.GONE);
        if (isShowDelete) {
            llDelete.setVisibility(View.VISIBLE);
        }

        llView.setOnClickListener(v -> {
            dialog.dismiss();
            ((BaseActivity) mContext).viewFile(filePath + userFiles.filename);
        });

        llDownload.setOnClickListener(v -> {
            dialog.dismiss();
            if (((BaseActivity) mContext).checkStoragePermission()) {
                checkPermission(userFiles, true, false, false);
            } else {
                new StorageDisclosureDialog(((BaseActivity) mContext), () -> checkPermission(userFiles, true, false, false));
            }
        });

        llShare.setOnClickListener(v -> {
            dialog.dismiss();
            if (((BaseActivity) mContext).checkStoragePermission()) {
                checkPermission(userFiles, false, false, true);
            } else {
                new StorageDisclosureDialog(((BaseActivity) mContext), () -> checkPermission(userFiles, false, false, true));
            }
        });

        llEmail.setOnClickListener(v -> {
            dialog.dismiss();
            if (((BaseActivity) mContext).checkStoragePermission()) {
                checkPermission(userFiles, false, true, false);
            } else {
                new StorageDisclosureDialog(((BaseActivity) mContext), () -> checkPermission(userFiles, false, true, false));
            }
        });

        llDelete.setOnClickListener(v -> {
            dialog.dismiss();
            showDeleteDialog(userFiles.id);
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

    private void downloadFile(final Attachments attachments, final boolean isDownload, final boolean isEmailShare, final boolean isShare) {
        File folder = new File(Environment.getExternalStorageDirectory(), "/Download/" + mContext.getString(R.string.app_name));
        if (!folder.exists())
            folder.mkdir();

        final File file = new File(folder, attachments.filename);
        if (!file.exists()) {
            String url = filePath + attachments.filename;
            if (!TextUtils.isEmpty(attachments.filename) && (url.startsWith("http:") || url.startsWith("https:"))) {
                MyDownloadManager downloadManager = new MyDownloadManager(mContext)
                        .setDownloadUrl(filePath + attachments.filename)
                        .setTitle(attachments.filename)
                        .setDestinationUri(file)
                        .setDownloadCompleteListener(new MyDownloadManager.DownloadCompleteListener() {
                            @Override
                            public void onDownloadComplete() {
                                showOutput("Download completed", isDownload, file, isEmailShare, isShare);
                            }

                            @Override
                            public void onDownloadFailure() {
                                ((BaseActivity) mContext).toastMessage("Download failed");
                            }
                        });
                downloadManager.startDownload();
            }
        } else {
            showOutput("Already Downloaded", isDownload, file, isEmailShare, isShare);
        }
    }

    private void showOutput(String message, boolean isDownload, File file, boolean isEmailShare, boolean isShare) {
        if (!isDownload) {
            if (isShare || isEmailShare)
                shareFile(file, isEmailShare);
            else
                ((BaseActivity) mContext).viewFile(file);
        } else {
            ((BaseActivity) mContext).toastMessage(message);
        }
    }

    private void shareFile(File file, boolean isEmailShare) {
        Uri uri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID + ".provider", file);
        Intent intentShareFile = new Intent(isEmailShare ? Intent.ACTION_SENDTO : Intent.ACTION_SEND);
        intentShareFile.setType("*/*");
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
                mime = "*/*";
            }
            intentShareFile.setType(mime);
            uri = Uri.parse("file://" + file.toString());
            intentShareFile.setData(Uri.parse("mailto:"));
            intentShareFile.putExtra(Intent.EXTRA_EMAIL, "");
        }
        intentShareFile.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intentShareFile.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intentShareFile.putExtra(Intent.EXTRA_STREAM, uri);
        mContext.startActivity(Intent.createChooser(intentShareFile, "Share File"));
    }

    private void checkPermission(final Attachments userFiles, final boolean isDownload, final boolean isEmailShare, final boolean isShare) {
        Dexter.withActivity((Activity) mContext)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        downloadFile(userFiles, isDownload, isEmailShare, isShare);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        ((BaseActivity) mContext).toastMessage(mContext.getString(R.string.give_storage_permission_first));
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showDeleteDialog(int attachmentID) {
        final Dialog dialog = new Dialog(mContext, R.style.Theme_AppCompat_Dialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_logout);
        dialog.setCancelable(true);

        TextView tvMessage = dialog.findViewById(R.id.tv_message);
        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        TextView tvChatnow = dialog.findViewById(R.id.tv_chat_now);

        String s = mContext.getString(R.string.delete_msg);
        String[] words = {mContext.getString(R.string.delete_file)};
        String[] fonts = {Constants.SFTEXT_BOLD};
        tvMessage.setText(Utils.getBoldString(mContext, s, fonts, null, words));

        tvCancel.setOnClickListener(v -> dialog.dismiss());

        tvChatnow.setOnClickListener(v -> {
            dialog.dismiss();
            onClickDeleteFileListener.onClickDeleteFileListener(attachmentID);
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.gravity = Gravity.CENTER;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }

    public interface OnClickDeleteFileListener {
        void onClickDeleteFileListener(int attachmentID);
    }

    static class FilesViewHolder extends BaseViewHolder<Attachments> {

        ItemListFilesBinding binding;

        FilesViewHolder(ItemListFilesBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}