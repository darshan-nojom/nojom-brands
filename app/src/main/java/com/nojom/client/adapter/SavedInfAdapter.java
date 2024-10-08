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
import com.nojom.client.databinding.ItemGigHomeBinding;
import com.nojom.client.model.AllSocialGigs;
import com.nojom.client.model.ExpertGig;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Utils;

import java.util.List;

public class SavedInfAdapter extends RecyclerView.Adapter<SavedInfAdapter.SimpleViewHolder> {
    private final BaseActivity activity;
    private final OnClickListener onClickListener;
    private final String imagePath;
    private final int isFirstOrder;
    private final ExpertGig.CouponData couponData;
    private final int platform;
    LayoutInflater layoutInflater;
    private List<AllSocialGigs.AllData> arrGigList;

    public SavedInfAdapter(BaseActivity activity, OnClickListener onClickListener, List<AllSocialGigs.AllData> arrGigList, String imagePath, int isFirstOrder, ExpertGig.CouponData couponData, int plat) {
        this.activity = activity;
        this.onClickListener = onClickListener;
        this.arrGigList = arrGigList;
        this.imagePath = imagePath;
        this.isFirstOrder = isFirstOrder;
        this.couponData = couponData;
        platform = plat;
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
        AllSocialGigs.AllData expertGig = arrGigList.get(position);

        if (expertGig.isShowFavProgress) {
            holder.binding.imgFavourite.setVisibility(View.GONE);
            holder.binding.progressBarFav.setVisibility(View.VISIBLE);
        } else {
            holder.binding.imgFavourite.setVisibility(View.VISIBLE);
            holder.binding.progressBarFav.setVisibility(View.GONE);
        }

        if (expertGig.isShowProgress) {
            holder.binding.loutItemView.setBackgroundColor(activity.getResources().getColor(R.color.C_44000000));
            holder.binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            holder.binding.loutItemView.setBackgroundColor(activity.getResources().getColor(R.color.white));
            holder.binding.progressBar.setVisibility(View.GONE);
            expertGig.isShowProgress = false;
        }

        if (expertGig.saved == 0) {
            holder.binding.imgFavourite.setImageResource(R.drawable.ic_fav);
        } else {
            holder.binding.imgFavourite.setImageResource(R.drawable.ic_fav_fill);
        }

        try {
            if (expertGig.starpoints != null) {
                holder.binding.ratingbar.setVisibility(View.VISIBLE);
                holder.binding.ratingbar.setRating(expertGig.starpoints);
            } else {
                holder.binding.ratingbar.setVisibility(View.VISIBLE);
                holder.binding.ratingbar.setRating(0);
            }
        } catch (NumberFormatException e) {
            holder.binding.ratingbar.setVisibility(View.VISIBLE);
            holder.binding.ratingbar.setRating(0);
            e.printStackTrace();
        }

        if (expertGig.countRating != null) {
            holder.binding.tvRating.setText("(" + Math.round(expertGig.countRating) + ")");
        } else {
            holder.binding.tvRating.setText("(0)");
        }

        StringBuilder sbName = new StringBuilder();
        if (expertGig.first_name != null) {
            sbName.append(expertGig.first_name);
        }
        if (expertGig.last_name != null) {
            sbName.append(" - ");
            sbName.append(expertGig.last_name);
        }
        if (sbName.length() == 0) {
            sbName.append(expertGig.username);
        }
        holder.binding.tvUsername.setText(sbName.toString());

        String cityName = TextUtils.isEmpty(expertGig.getCity(activity.getLanguage())) ? "" : expertGig.getCity(activity.getLanguage()) + ", ";
        String countryName = TextUtils.isEmpty(expertGig.getCountry(activity.getLanguage())) ? "" : expertGig.getCountry(activity.getLanguage()) + " ";
        String boldText = "(" + Utils.getPlatformTxt(expertGig.followersCount) + ")";
        String normalText = cityName + countryName + boldText;
        String[] words = {boldText};
        String[] fonts = {Constants.SF_PRO_TEXT_BOLD};
        holder.binding.tvDescription.setText(Utils.getBoldString(activity, normalText, fonts, null, words));

        try {
            if (isFirstOrder == 0) {
                holder.binding.tvView.setVisibility(View.GONE);
                if (expertGig.minPrice == null || expertGig.minPrice == 0) {
                    holder.binding.tvFrom.setVisibility(View.GONE);
                    holder.binding.tvAmount.setText(activity.getString(R.string.free).toUpperCase());
                } else {
                    holder.binding.tvFrom.setVisibility(View.VISIBLE);
                    holder.binding.tvAmount.setText(activity.getCurrency().equals("SAR") ?
                            Utils.decimalFormat(String.valueOf(expertGig.minPrice) + " " + activity.getString(R.string.sar)) :
                            activity.getString(R.string.dollar) + Utils.decimalFormat(String.valueOf(expertGig.minPrice)));
                }
            } else {
                double finalAmt = 0;
                if (couponData != null) {
                    if (couponData.type == 2) {
                        finalAmt = expertGig.minPrice - couponData.discount;
                    } else {
                        finalAmt = (expertGig.minPrice * couponData.discount) / 100;
                    }

                }
                if (finalAmt <= 0) {
                    holder.binding.tvFrom.setVisibility(View.GONE);
                    holder.binding.tvView.setVisibility(View.GONE);
                    holder.binding.tvAmount.setText(activity.getString(R.string.free).toUpperCase());
                } else {
                    holder.binding.tvFrom.setVisibility(View.VISIBLE);
                    holder.binding.tvView.setVisibility(View.VISIBLE);
                    holder.binding.tvAmount.setText(activity.getCurrency().equals("SAR") ? Utils.decimalFormat(String.valueOf(finalAmt)) + " " + activity.getString(R.string.sar)
                            : activity.getString(R.string.dollar) + Utils.decimalFormat(String.valueOf(finalAmt)));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!activity.isEmpty(expertGig.img) && expertGig.img != null) {
            Glide.with(activity)
                    .load(imagePath + expertGig.img)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(holder.binding.imgGig);
        } else {
            holder.binding.imgGig.setImageResource(R.mipmap.ic_launcher);
        }

        if (Utils.isArabic(activity)) {
            holder.binding.imgGig.setCornerRadius(0, activity.getResources().getDimension(R.dimen._7sdp), 0, activity.getResources().getDimension(R.dimen._7sdp));
        } else {
            holder.binding.imgGig.setCornerRadius(activity.getResources().getDimension(R.dimen._7sdp), 0, activity.getResources().getDimension(R.dimen._7sdp), 0);
        }
    }

    public List<AllSocialGigs.AllData> getData() {
        return arrGigList;
    }

    public void doRefresh(List<AllSocialGigs.AllData> data) {
        arrGigList = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return Math.min(arrGigList.size(), 3);
    }

    public interface OnClickListener {
        void onClickFavouriteInf(AllSocialGigs.AllData data, int pos, int platform);

        void onClickViewInfluencer(AllSocialGigs.AllData data, int pos, int platform);
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {
        ItemGigHomeBinding binding;

        public SimpleViewHolder(ItemGigHomeBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.imgSocialInfom.setVisibility(View.GONE);
            binding.tvDisAmount.setVisibility(View.GONE);

            binding.loutGig.setOnClickListener(v -> {
                if (onClickListener != null) {
                    if (activity.isLogin()) {
                        activity.setEnableDisableView(binding.loutGig);
                        binding.loutItemView.setBackgroundColor(activity.getResources().getColor(R.color.C_44000000));
                        binding.progressBar.setVisibility(View.VISIBLE);
                    }
                    onClickListener.onClickViewInfluencer(arrGigList.get(getAbsoluteAdapterPosition()), getAbsoluteAdapterPosition(), platform);
                }
            });

            binding.imgFavourite.setOnClickListener(v -> {
                if (onClickListener != null) {
                    activity.setEnableDisableView(binding.imgFavourite);
                    if (activity.isLogin()) {
                        binding.imgFavourite.setVisibility(View.GONE);
                        binding.progressBarFav.setVisibility(View.VISIBLE);
                    }
                    onClickListener.onClickFavouriteInf(arrGigList.get(getAbsoluteAdapterPosition()), getAbsoluteAdapterPosition(), platform);
                }
            });
        }
    }
}
