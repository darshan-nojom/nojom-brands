package com.nojom.client.adapter;

import android.graphics.Paint;
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
import com.nojom.client.databinding.ItemGigHomeBinding;
import com.nojom.client.model.InfluencerList;
import com.nojom.client.model.SocialInfluence;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Utils;

import java.util.List;

public class ServiceSellersGigViewAdapter extends RecyclerView.Adapter<ServiceSellersGigViewAdapter.SimpleViewHolder> {
    private final BaseActivity activity;
    private final OnClickListener onClickListener;
    private final String imagePath;
    private final int isFirstOrder;
    private final SocialInfluence.CouponData couponData;
    LayoutInflater layoutInflater;
    private List<InfluencerList.AllData> arrGigList;

    public ServiceSellersGigViewAdapter(BaseActivity activity, OnClickListener onClickListener, List<InfluencerList.AllData> arrGigList, String imagePath, int isFirstOrder, SocialInfluence.CouponData couponData) {
        this.activity = activity;
        this.onClickListener = onClickListener;
        this.arrGigList = arrGigList;
        this.imagePath = imagePath;
        this.isFirstOrder = isFirstOrder;
        this.couponData = couponData;
        layoutInflater = activity.getLayoutInflater();
    }

    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemGigHomeBinding popularBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_gig_home, parent, false);
        return new SimpleViewHolder(popularBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleViewHolder holder, int position) {
        InfluencerList.AllData socialInfluence = arrGigList.get(position);

//        if (socialInfluence.isShowFavProgress) {
//            holder.binding.imgFavourite.setVisibility(View.GONE);
//            holder.binding.progressBarFav.setVisibility(View.VISIBLE);
//        } else {
//            holder.binding.imgFavourite.setVisibility(View.VISIBLE);
//            holder.binding.progressBarFav.setVisibility(View.GONE);
//        }

        if (socialInfluence.isShowProgress) {
            holder.binding.loutItemView.setBackgroundColor(activity.getResources().getColor(R.color.C_44000000));
            holder.binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            holder.binding.loutItemView.setBackgroundColor(activity.getResources().getColor(R.color.white));
            holder.binding.progressBar.setVisibility(View.GONE);
            socialInfluence.isShowProgress = false;
        }

        holder.binding.tvUsername.setText(socialInfluence.first_name + " " + socialInfluence.last_name);
        String cityName = TextUtils.isEmpty(socialInfluence.getCityName(activity.getLanguage())) ? "" : socialInfluence.getCityName(activity.getLanguage()) + ", ";
        String countryName = TextUtils.isEmpty(socialInfluence.getCountryName(activity.getLanguage())) ? "" : socialInfluence.getCountryName(activity.getLanguage()) + " ";
//        String boldText = "(" + Utils.getPlatformTxt(socialInfluence.followersCount) + ")";
//        String normalText = cityName + countryName + boldText;
//        String[] words = {boldText};
//        String[] fonts = {Constants.SF_PRO_TEXT_BOLD};
        if (!TextUtils.isEmpty(cityName) && !TextUtils.isEmpty(countryName)) {
            holder.binding.tvDescription.setText(cityName + ", " + countryName);
        }

        if (socialInfluence.saved != null) {
            if (socialInfluence.saved == 0) {
                holder.binding.imgFavourite.setImageResource(R.drawable.ic_fav);
            } else {
                holder.binding.imgFavourite.setImageResource(R.drawable.ic_fav_fill);
            }
        }

        try {
            if (socialInfluence.starpoints != null) {
                holder.binding.ratingbar.setVisibility(View.VISIBLE);
                holder.binding.ratingbar.setRating(socialInfluence.starpoints);
            } else {
                holder.binding.ratingbar.setVisibility(View.VISIBLE);
                holder.binding.ratingbar.setRating(0);
            }
        } catch (NumberFormatException e) {
            holder.binding.ratingbar.setVisibility(View.VISIBLE);
            holder.binding.ratingbar.setRating(0);
            e.printStackTrace();
        }

//        if (socialInfluence.countRating != null) {
//            holder.binding.tvRating.setText("(" + Math.round(socialInfluence.countRating) + ")");
//        } else {
//            holder.binding.tvRating.setText("(0)");
//        }


        if (!activity.isEmpty(socialInfluence.img) && socialInfluence.img != null) {
            Glide.with(activity).load(imagePath + socialInfluence.img).diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(holder.binding.imgGig);
        } else {
            holder.binding.imgGig.setImageResource(R.mipmap.ic_launcher);
        }


//        if (!activity.isEmpty(socialInfluence.platformIcon) && socialInfluence.platformIcon != null) {
//            holder.binding.imgSocialInfom.setVisibility(View.VISIBLE);
//            Glide.with(activity)
//                    .load(socialInfluence.platformIcon)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .centerCrop()
//                    .placeholder(R.drawable.ic_logo)
//                    .error(R.drawable.ic_logo)
//                    .into(holder.binding.imgSocialInfom);
//        } else {
//            holder.binding.imgSocialInfom.setVisibility(View.GONE);
//        }

    }

    public List<InfluencerList.AllData> getData() {
        return arrGigList;
    }

    public void doRefresh(List<InfluencerList.AllData> data) {
        this.arrGigList = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return arrGigList.size();
    }

    public interface OnClickListener {
        void onClickFavourite(InfluencerList.AllData data, int pos);

        void onClickViewDetail(InfluencerList.AllData data, int pos);
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {
        ItemGigHomeBinding binding;

        public SimpleViewHolder(ItemGigHomeBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.imgSocialInfom.setVisibility(View.GONE);
            binding.linRate.setVisibility(View.GONE);
            binding.imgFavourite.setVisibility(View.GONE);
            binding.loutGig.setOnClickListener(v -> {
                if (onClickListener != null) {
                    activity.setEnableDisableView(binding.loutGig);
                    binding.loutItemView.setBackgroundColor(activity.getResources().getColor(R.color.C_44000000));
                    binding.progressBar.setVisibility(View.VISIBLE);
                    onClickListener.onClickViewDetail(arrGigList.get(getAbsoluteAdapterPosition()), getAbsoluteAdapterPosition());
                }
            });

//            binding.imgFavourite.setOnClickListener(v -> {
//                if (onClickListener != null) {
//                    if (activity.isLogin()) {
//                        activity.setEnableDisableView(binding.imgFavourite);
//                        binding.imgFavourite.setVisibility(View.GONE);
//                        binding.progressBarFav.setVisibility(View.VISIBLE);
//                        onClickListener.onClickFavourite(arrGigList.get(getAbsoluteAdapterPosition()), getAbsoluteAdapterPosition());
//                    } else {
//                        activity.openLoginDialog();
//                    }
//                }
//            });
        }
    }
}
