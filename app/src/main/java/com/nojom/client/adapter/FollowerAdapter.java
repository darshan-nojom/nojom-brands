package com.nojom.client.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nojom.client.R;
import com.nojom.client.databinding.ItemHomeBinding;
import com.nojom.client.databinding.ItemHomeFollowerBinding;
import com.nojom.client.model.AllSocialGigs;
import com.nojom.client.model.ExpertGig;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FollowerAdapter extends RecyclerView.Adapter<FollowerAdapter.SimpleViewHolder> {
    private final BaseActivity activity;
    LayoutInflater layoutInflater;
    private List<AllSocialGigs.SocialPlatform> arrGigList;

    public FollowerAdapter(BaseActivity activity, List<AllSocialGigs.SocialPlatform> arrGigList) {
        this.activity = activity;
        this.arrGigList = arrGigList;
        layoutInflater = activity.getLayoutInflater();
    }

    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHomeFollowerBinding popularBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_home_follower, parent, false);
        return new SimpleViewHolder(popularBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleViewHolder holder, int position) {
        AllSocialGigs.SocialPlatform expertGig = arrGigList.get(position);

        String formattedNumber = activity.formatNumber(expertGig.followers);
        holder.binding.txtName.setText(formattedNumber);
        Glide.with(activity)
                .load(expertGig.filename_gray)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.binding.imgLogo);

    }

    public List<AllSocialGigs.SocialPlatform> getData() {
        return arrGigList;
    }

    public void doRefresh(List<AllSocialGigs.SocialPlatform> data) {
        arrGigList = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (arrGigList != null && arrGigList.size() > 0) {
            Collections.sort(arrGigList, (o1, o2) -> {
                // Sort in descending order
                return Integer.compare(o2.followers, o1.followers);
            });
            return Math.min(arrGigList.size(), 3);
        } else {
            return 0;
        }

    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {
        ItemHomeFollowerBinding binding;

        public SimpleViewHolder(ItemHomeFollowerBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
