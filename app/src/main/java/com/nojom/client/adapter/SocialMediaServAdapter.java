package com.nojom.client.adapter;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nojom.client.R;
import com.nojom.client.databinding.ItemSocialInfServBinding;
import com.nojom.client.model.SocialPlatformList;
import com.nojom.client.ui.BaseActivity;

import java.util.List;


public class SocialMediaServAdapter extends RecyclerView.Adapter<SocialMediaServAdapter.SimpleViewHolder> {

    private List<SocialPlatformList.Data> mDataset;
    private BaseActivity activity;

    public void doRefresh(List<SocialPlatformList.Data> objects, BaseActivity activity) {
        this.mDataset = objects;
        this.activity = activity;
//        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemSocialInfServBinding verifiedWithBinding = ItemSocialInfServBinding.inflate(layoutInflater, parent, false);
        return new SimpleViewHolder(verifiedWithBinding);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {
        SocialPlatformList.Data item = mDataset.get(position);
        if (item.followersCount != 0) {
            String formattedNumber = activity.formatNumber(item.followersCount);
            holder.binding.txtFollowerCount.setText(formattedNumber);
        }

        if (!TextUtils.isEmpty(item.platform_icon)) {
            Glide.with(holder.binding.imgProfile.getContext()).load(item.platform_icon).placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round).diskCacheStrategy(DiskCacheStrategy.ALL).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    return false;
                }
            }).into(holder.binding.imgProfile);
        } else {
            holder.binding.imgProfile.setImageResource(R.mipmap.ic_launcher_round);
        }
    }

    @Override
    public int getItemCount() {
        return mDataset != null ? mDataset.size() : 0;
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemSocialInfServBinding binding;

        SimpleViewHolder(ItemSocialInfServBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.getRoot().setOnClickListener(v -> {

            });
        }
    }
}
