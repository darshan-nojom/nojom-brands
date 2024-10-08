package com.nojom.client.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.databinding.ItemUploadedFilesBinding;
import com.nojom.client.model.Attachment;
import com.nojom.client.ui.BaseActivity;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class UploadFileAdapter extends RecyclerView.Adapter<UploadFileAdapter.SimpleViewHolder> {

    private ArrayList<Attachment> mDataset;
    private BaseActivity activity;
    private OnFileDeleteListener onFileDeletelistener;
    private String toDelete = "";
    private String filePath;

    public UploadFileAdapter(Context context, String filePath) {
        activity = (BaseActivity) context;
        this.filePath = filePath;
    }

    @NotNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemUploadedFilesBinding itemUploadedFilesBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_uploaded_files, parent, false);
        return new SimpleViewHolder(itemUploadedFilesBinding);
    }

    public void doRefresh(ArrayList<Attachment> mDataset) {
        this.mDataset = mDataset;
        notifyDataSetChanged();
    }

    public void setOnFileDeleteListener(OnFileDeleteListener onFileDeletelistener) {
        this.onFileDeletelistener = onFileDeletelistener;
    }

    @Override
    public void onBindViewHolder(@NotNull final SimpleViewHolder viewHolder, final int position) {
        final Attachment attachment = mDataset.get(position);
        if (!TextUtils.isEmpty(attachment.filepath)) {
            File file = new File(attachment.filepath);
            viewHolder.binding.tvFileName.setText(file.getName());
            if (attachment.isImage)
                activity.setImage(viewHolder.binding.imgFile, "file://" + file.getAbsolutePath(), 0, 0);
            else
                viewHolder.binding.imgFile.setImageResource(R.drawable.file);
        } else {
            viewHolder.binding.tvFileName.setText(attachment.fileUrl);
            if (attachment.isImage)
                activity.setImage(viewHolder.binding.imgFile, filePath + attachment.fileUrl, 0, 0);
            else
                viewHolder.binding.imgFile.setImageResource(R.drawable.file);
        }
    }

    public String toDeleteId() {
        return toDelete;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    private void showDeleteFileDialog(int pos) {
        final Dialog dialog = new Dialog(activity, R.style.Theme_AppCompat_Dialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_delete_project);
        dialog.setCancelable(true);

        TextView tvMessage = dialog.findViewById(R.id.tv_message);
        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        TextView tvChatnow = dialog.findViewById(R.id.tv_chat_now);

        tvMessage.setText(activity.getString(R.string.are_you_sure_wants_delete_file));

        tvCancel.setText(activity.getString(R.string.no));
        tvChatnow.setText(activity.getString(R.string.yes));
        tvCancel.setOnClickListener(v -> dialog.dismiss());

        tvChatnow.setOnClickListener(v -> {
            dialog.dismiss();
            if (!TextUtils.isEmpty(mDataset.get(pos).fileId)) {
                if (TextUtils.isEmpty(toDelete)) {
                    toDelete = mDataset.get(pos).fileId;
                } else {
                    toDelete = toDelete + "," + mDataset.get(pos).fileId;
                }
            }
            mDataset.remove(pos);
            notifyItemRemoved(pos);
            notifyItemRangeChanged(pos, mDataset.size());
            if (onFileDeletelistener != null) {
                onFileDeletelistener.onFileDelete(mDataset);
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.gravity = Gravity.CENTER;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }

    public interface OnFileDeleteListener {
        void onFileDelete(ArrayList<Attachment> mDataset);
    }

    public class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemUploadedFilesBinding binding;

        public SimpleViewHolder(ItemUploadedFilesBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            itemView.getRoot().setOnClickListener(v -> {
                File file = new File(mDataset.get(getAbsoluteAdapterPosition()).filepath);
                activity.viewFile(file);
            });

            binding.imgDelete.setOnClickListener(v -> {
                showDeleteFileDialog(getAbsoluteAdapterPosition());
            });
        }
    }
}
