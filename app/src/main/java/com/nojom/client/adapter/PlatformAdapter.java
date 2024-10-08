package com.nojom.client.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.databinding.ItemSelectFullBinding;
import com.nojom.client.model.SocialPlatformModel;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PlatformAdapter extends RecyclerView.Adapter<PlatformAdapter.SimpleViewHolder> {

    private List<SocialPlatformModel.Data> mDatasetFiltered;
    private Context context;
    private BaseActivity activity;

    public PlatformAdapter(Context context, List<SocialPlatformModel.Data> objects) {
        this.mDatasetFiltered = objects;
        this.context = context;
        activity = (BaseActivity) context;
    }

    @NotNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemSelectFullBinding fullBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_select_full, parent, false);
        return new SimpleViewHolder(fullBinding);
    }

    @Override
    public void onBindViewHolder(@NotNull final SimpleViewHolder holder, final int position) {
        SocialPlatformModel.Data item = mDatasetFiltered.get(position);

        holder.binding.tvTitle.setText(item.getServNameByLang(activity.getLanguage()));

        if (item.isSelected) {
            holder.binding.tvTitle.setBackground(ContextCompat.getDrawable(context, R.drawable.black_button_bg));
            Typeface tf = Typeface.createFromAsset(context.getAssets(), Constants.SFTEXT_BOLD);
            holder.binding.tvTitle.setTypeface(tf);
            holder.binding.tvTitle.setTextColor(ContextCompat.getColor(context, R.color.white));
        } else {
            holder.binding.tvTitle.setBackgroundColor(Color.TRANSPARENT);
            holder.binding.tvTitle.setTextColor(ContextCompat.getColor(context, R.color.black));
            Typeface tf = Typeface.createFromAsset(context.getAssets(), Constants.SFTEXT_REGULAR);
            holder.binding.tvTitle.setTypeface(tf);
        }
    }

    private void clearSelected() {
        if (mDatasetFiltered != null && mDatasetFiltered.size() > 0) {
            for (SocialPlatformModel.Data data : mDatasetFiltered) {
                data.isSelected = false;
            }
        }
    }

    public SocialPlatformModel.Data getSelectedItem() {
        if (mDatasetFiltered != null && mDatasetFiltered.size() > 0) {
            for (SocialPlatformModel.Data data : mDatasetFiltered) {
                if (data.isSelected) {
                    return data;
                }
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return mDatasetFiltered != null ? mDatasetFiltered.size() : 0;
    }

    public List<SocialPlatformModel.Data> getData() {
        return mDatasetFiltered;
    }

    public class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemSelectFullBinding binding;

        public SimpleViewHolder(ItemSelectFullBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.rlView.setOnClickListener(v -> {
                clearSelected();
                mDatasetFiltered.get(getAbsoluteAdapterPosition()).isSelected = true;
                notifyDataSetChanged();
            });
        }
    }
}
