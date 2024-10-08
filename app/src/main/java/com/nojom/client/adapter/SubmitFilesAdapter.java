package com.nojom.client.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.databinding.ItemSubmittedFilesBinding;
import com.nojom.client.model.FileList;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SubmitFilesAdapter extends RecyclerView.Adapter<SubmitFilesAdapter.SimpleViewHolder> {

    private ArrayList<FileList> mDataset;
    private BaseActivity activity;
    private String filePath;

    public SubmitFilesAdapter(Context context, ArrayList<FileList> objects, String submittedPath) {
        this.mDataset = objects;
        this.filePath = submittedPath;
        activity = (BaseActivity) context;
    }

    @NotNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemSubmittedFilesBinding itemListFilesBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_submitted_files, parent, false);
        return new SimpleViewHolder(itemListFilesBinding);
    }

    @Override
    public void onBindViewHolder(@NotNull final SimpleViewHolder holder, final int position) {
        FileList item = mDataset.get(position);
        try {
            holder.binding.tvDesc.setText(item.titleData.description);
            holder.binding.tvFileDate.setText(Utils.changeDateFormat("yyyy-MM-dd'T'hh:mm:ss", "dd/MM/yyyy hh:mm a", item.titleData.timestamp));
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
            holder.binding.rvFiles.setLayoutManager(linearLayoutManager);

            SubmitFileListAdapter fileAdapter = new SubmitFileListAdapter(activity, (ArrayList<FileList.FilesDatum>) item.filesData, filePath);
            holder.binding.rvFiles.setAdapter(fileAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mDataset != null ? mDataset.size() : 0;
    }

    public ArrayList<FileList> getData() {
        return mDataset;
    }

    static class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemSubmittedFilesBinding binding;

        SimpleViewHolder(ItemSubmittedFilesBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
