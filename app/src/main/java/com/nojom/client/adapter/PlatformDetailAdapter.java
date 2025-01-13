package com.nojom.client.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nojom.client.R;
import com.nojom.client.databinding.ItemPlatformImgBinding;
import com.nojom.client.databinding.ItemSelectFullBinding;
import com.nojom.client.model.CampSocialPlatform;
import com.nojom.client.model.SocialPlatformModel;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PlatformDetailAdapter extends RecyclerView.Adapter<PlatformDetailAdapter.SimpleViewHolder> {

    private List<CampSocialPlatform> mDatasetFiltered;
    private Context context;
    private BaseActivity activity;

    public PlatformDetailAdapter(Context context, List<CampSocialPlatform> objects) {
        this.mDatasetFiltered = objects;
        this.context = context;
        activity = (BaseActivity) context;
    }

    @NotNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemPlatformImgBinding fullBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_platform_img, parent, false);
        return new SimpleViewHolder(fullBinding);
    }

    @Override
    public void onBindViewHolder(@NotNull final SimpleViewHolder holder, final int position) {
        CampSocialPlatform item = mDatasetFiltered.get(position);

        Glide.with(activity).load(item.filename).diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(holder.binding.squareImage);
    }

    @Override
    public int getItemCount() {
        return mDatasetFiltered != null ? mDatasetFiltered.size() : 0;
    }

    public List<CampSocialPlatform> getData() {
        return mDatasetFiltered;
    }

    public class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemPlatformImgBinding binding;

        public SimpleViewHolder(ItemPlatformImgBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

        }
    }
}
