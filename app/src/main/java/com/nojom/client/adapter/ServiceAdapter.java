package com.nojom.client.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nojom.client.R;
import com.nojom.client.databinding.ItemServiceBinding;
import com.nojom.client.model.ServiceAgents;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Utils;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.SimpleViewHolder> {
    private final BaseActivity activity;
    private final OnClickListener onClickListener;
    LayoutInflater layoutInflater;
    private List<ServiceAgents> arrGigList;

    public ServiceAdapter(BaseActivity activity, OnClickListener onClickListener, List<ServiceAgents> arrGigList) {
        this.activity = activity;
        this.onClickListener = onClickListener;
        this.arrGigList = arrGigList;
        layoutInflater = activity.getLayoutInflater();
    }

    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemServiceBinding popularBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_service, parent, false);
        return new SimpleViewHolder(popularBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleViewHolder holder, int position) {
        ServiceAgents expertGig = arrGigList.get(position);

        if (expertGig.isShowProgress) {
//            holder.binding.loutItemView.setBackgroundColor(activity.getResources().getColor(R.color.C_44000000));
            holder.binding.progressBarOrder.setVisibility(View.VISIBLE);
            holder.binding.btnContinuePrice.setVisibility(View.INVISIBLE);
        } else {
//            holder.binding.loutItemView.setBackgroundColor(activity.getResources().getColor(R.color.white));
            holder.binding.progressBarOrder.setVisibility(View.GONE);
            holder.binding.btnContinuePrice.setVisibility(View.VISIBLE);
            expertGig.isShowProgress = false;
        }

        StringBuilder sbName = new StringBuilder();
        if (expertGig.first_name != null) {
            sbName.append(expertGig.first_name);
        }
        if (expertGig.last_name != null) {
            sbName.append(" ");
            sbName.append(expertGig.last_name);
        }

        holder.binding.tvName.setText(sbName.toString());

        if (expertGig.categories != null && expertGig.categories.size() > 0) {
            TagsAdapter followerAdapter = new TagsAdapter(activity, expertGig.categories);
            holder.binding.rvCategory.setAdapter(followerAdapter);
        }

        if (expertGig.image != null) {
            Glide.with(activity).load(expertGig.image.path + expertGig.image.file_name).diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(holder.binding.imgGig);
        } else {
            holder.binding.imgGig.setImageResource(R.mipmap.ic_launcher);
        }

        if (Utils.isArabic(activity)) {
            holder.binding.imgGig.setCornerRadius(0, activity.getResources().getDimension(R.dimen._7sdp), 0, activity.getResources().getDimension(R.dimen._7sdp));
        } else {
            holder.binding.imgGig.setCornerRadius(activity.getResources().getDimension(R.dimen._7sdp), 0, activity.getResources().getDimension(R.dimen._7sdp), 0);
        }

        if (expertGig.services != null && expertGig.services.size() > 0) {
//            holder.binding.rvPlatform.setVisibility(View.VISIBLE);

            SocialPlatformAdapter followerAdapter = new SocialPlatformAdapter(activity, expertGig.services);
            holder.binding.rvPlatform.setAdapter(followerAdapter);
        }

    }

    public List<ServiceAgents> getData() {
        return arrGigList;
    }

    public void doRefresh(List<ServiceAgents> data) {
        arrGigList = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return arrGigList.size();
    }

    public interface OnClickListener {
        void onClickFavouriteInf(ServiceAgents data, int pos, int platform);

        void onClickViewInfluencer(ServiceAgents data, int pos, int platform);
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {
        ItemServiceBinding binding;

        public SimpleViewHolder(ItemServiceBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
//            binding.imgSocialInfom.setVisibility(View.GONE);
//            binding.tvDisAmount.setVisibility(View.GONE);

            binding.loutGig.setOnClickListener(v -> {
                if (onClickListener != null) {
                    if (activity.isLogin()) {
                        activity.setEnableDisableView(binding.loutGig);
//                        binding.loutItemView.setBackgroundColor(activity.getResources().getColor(R.color.C_44000000));
                        binding.progressBarOrder.setVisibility(View.VISIBLE);
                        binding.btnContinuePrice.setVisibility(View.INVISIBLE);
                    }
                    onClickListener.onClickViewInfluencer(arrGigList.get(getAbsoluteAdapterPosition()), getAbsoluteAdapterPosition(), 6);
                }
            });
            binding.btnContinuePrice.setOnClickListener(v -> {
                if (onClickListener != null) {
                    if (activity.isLogin()) {
                        activity.setEnableDisableView(binding.loutGig);
//                        binding.loutItemView.setBackgroundColor(activity.getResources().getColor(R.color.C_44000000));
                        binding.progressBarOrder.setVisibility(View.VISIBLE);
                        binding.btnContinuePrice.setVisibility(View.INVISIBLE);
                    }
                    onClickListener.onClickViewInfluencer(arrGigList.get(getAbsoluteAdapterPosition()), getAbsoluteAdapterPosition(), 6);
                }
            });

        }
    }
}
