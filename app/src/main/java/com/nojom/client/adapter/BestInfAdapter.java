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
import com.nojom.client.databinding.ItemHomeBinding;
import com.nojom.client.model.AllSocialGigs;
import com.nojom.client.model.ExpertGig;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Utils;

import java.util.List;

public class BestInfAdapter extends RecyclerView.Adapter<BestInfAdapter.SimpleViewHolder> {
    private final BaseActivity activity;
    private final OnClickListener onClickListener;
    private final String imagePath;
    private final int isFirstOrder;
    private final ExpertGig.CouponData couponData;
    private final int platform;
    LayoutInflater layoutInflater;
    private List<AllSocialGigs.AllData> arrGigList;

    public BestInfAdapter(BaseActivity activity, OnClickListener onClickListener, List<AllSocialGigs.AllData> arrGigList, String imagePath, int isFirstOrder, ExpertGig.CouponData couponData, int plat) {
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
        ItemHomeBinding popularBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_home, parent, false);
        return new SimpleViewHolder(popularBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleViewHolder holder, int position) {
        AllSocialGigs.AllData expertGig = arrGigList.get(position);

//        if (expertGig.isShowFavProgress) {
//            holder.binding.imgFavourite.setVisibility(View.GONE);
//            holder.binding.progressBarFav.setVisibility(View.VISIBLE);
//        } else {
//            holder.binding.imgFavourite.setVisibility(View.VISIBLE);
//            holder.binding.progressBarFav.setVisibility(View.GONE);
//        }

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

//        try {
//            if (expertGig.starpoints != null) {
//                holder.binding.ratingbar.setVisibility(View.VISIBLE);
//                holder.binding.ratingbar.setRating(expertGig.starpoints);
//            } else {
//                holder.binding.ratingbar.setVisibility(View.VISIBLE);
//                holder.binding.ratingbar.setRating(0);
//            }
//        } catch (NumberFormatException e) {
//            holder.binding.ratingbar.setVisibility(View.VISIBLE);
//            holder.binding.ratingbar.setRating(0);
//            e.printStackTrace();
//        }

//        if (expertGig.countRating != null) {
//            holder.binding.tvRating.setText("(" + Math.round(expertGig.countRating) + ")");
//        } else {
//            holder.binding.tvRating.setText("(0)");
//        }

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
        holder.binding.tvName.setText(sbName.toString());

        StringBuilder stringBuilder = new StringBuilder();
        if (expertGig.skills != null && expertGig.skills.size() > 0) {
            for (AllSocialGigs.Skills sk : expertGig.skills) {
                if (sk.getSkillName(activity.getLanguage()) != null && !sk.getSkillName(activity.getLanguage()).equals("null")) {
                    stringBuilder.append(sk.getSkillName(activity.getLanguage()));
                    stringBuilder.append(", ");
                }
            }
            holder.binding.tvCategory.setText(stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString());
        } else {
            holder.binding.tvCategory.setText("");
        }

        try {
            if (expertGig.minPrice != null && expertGig.maxPrice != null && expertGig.minPrice != 0 && expertGig.maxPrice != 0) {
                holder.binding.tvPrice.setVisibility(View.VISIBLE);
                if (activity.getCurrency().equals("SAR")) {
                    if (activity.getLanguage().equals("ar")) {
                        holder.binding.tvPrice.setText(Utils.removeTrailingZeros(expertGig.minPrice) + " - " + Utils.removeTrailingZeros(expertGig.maxPrice) + " ريال / للإعلان ");
                        holder.binding.tvAds.setVisibility(View.GONE);
                    } else {
                        holder.binding.tvAds.setVisibility(View.VISIBLE);
                        holder.binding.tvPrice.setText(String.format(activity.getString(R.string.s_sar_s_sar_before), Utils.removeTrailingZeros(expertGig.minPrice), Utils.removeTrailingZeros(expertGig.maxPrice)));
                    }
                } else {
                    holder.binding.tvAds.setVisibility(View.VISIBLE);
                    holder.binding.tvPrice.setText(String.format("%s%s - %s%s", activity.getString(R.string.dollar), Utils.removeTrailingZeros(expertGig.minPrice), activity.getString(R.string.dollar), Utils.removeTrailingZeros(expertGig.maxPrice)));
                }
            } else {
                holder.binding.tvPrice.setVisibility(View.GONE);
                holder.binding.tvAds.setVisibility(View.GONE);
            }


//            if (isFirstOrder == 0) {
////                holder.binding.tvView.setVisibility(View.GONE);
//                if (expertGig.minPrice == null || expertGig.minPrice == 0) {
////                    holder.binding.tvFrom.setVisibility(View.GONE);
//                    holder.binding.tvPrice.setVisibility(View.GONE);
////                    holder.binding.tvAmount.setText(activity.getString(R.string.Free).toUpperCase());
//                } else {
////                    holder.binding.tvFrom.setVisibility(View.VISIBLE);
//                    holder.binding.tvPrice.setVisibility(View.VISIBLE);
//                    holder.binding.tvPrice.setText(activity.getCurrency().equals("SAR") ?
//                            Utils.decimalFormat(String.valueOf(expertGig.minPrice)) + " " + activity.getString(R.string.sar) :
//                            "$" + Utils.decimalFormat(String.valueOf(expertGig.minPrice)));
//                }
//            } else {
//                double finalAmt = 0;
//                if (couponData != null) {
//                    if (couponData.type == 2) {
//                        finalAmt = expertGig.minPrice - couponData.discount;
//                    } else {
//                        finalAmt = (expertGig.minPrice * couponData.discount) / 100;
//                    }
//
//                }
//                if (finalAmt <= 0) {
//                    holder.binding.tvPrice.setVisibility(View.GONE);
////                    holder.binding.tvFrom.setVisibility(View.GONE);
////                    holder.binding.tvView.setVisibility(View.GONE);
////                    holder.binding.tvAmount.setText(activity.getString(R.string.Free).toUpperCase());
//                } else {
////                    holder.binding.tvFrom.setVisibility(View.VISIBLE);
////                    holder.binding.tvView.setVisibility(View.VISIBLE);
//                    holder.binding.tvPrice.setVisibility(View.VISIBLE);
//                    holder.binding.tvPrice.setText(activity.getCurrency().equals("SAR") ? Utils.decimalFormat(String.valueOf(finalAmt)) + " " + activity.getString(R.string.sar)
//                            : "$" + Utils.decimalFormat(String.valueOf(finalAmt)));
//                }
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!activity.isEmpty(expertGig.profileimg) && expertGig.profileimg != null) {
            Glide.with(activity).load(imagePath + expertGig.profileimg).diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(holder.binding.imgGig);
        } else {
            holder.binding.imgGig.setImageResource(R.mipmap.ic_launcher);
        }

        if (Utils.isArabic(activity)) {
            holder.binding.imgGig.setCornerRadius(0, activity.getResources().getDimension(R.dimen._7sdp), 0, activity.getResources().getDimension(R.dimen._7sdp));
        } else {
            holder.binding.imgGig.setCornerRadius(activity.getResources().getDimension(R.dimen._7sdp), 0, activity.getResources().getDimension(R.dimen._7sdp), 0);
        }

        if (expertGig.social_platforms != null && expertGig.social_platforms.size() > 0) {
//            holder.binding.rvPlatform.setVisibility(View.VISIBLE);
            FollowerAdapter followerAdapter = new FollowerAdapter(activity, expertGig.social_platforms);
            holder.binding.rvPlatform.setAdapter(followerAdapter);
        } else {
//            holder.binding.rvPlatform.setVisibility(View.GONE);
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
        return arrGigList.size();
    }

    public interface OnClickListener {
        void onClickFavouriteInf(AllSocialGigs.AllData data, int pos, int platform);

        void onClickViewInfluencer(AllSocialGigs.AllData data, int pos, int platform);
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {
        ItemHomeBinding binding;

        public SimpleViewHolder(ItemHomeBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
//            binding.imgSocialInfom.setVisibility(View.GONE);
//            binding.tvDisAmount.setVisibility(View.GONE);

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
//                        binding.imgFavourite.setVisibility(View.GONE);
//                        binding.progressBarFav.setVisibility(View.VISIBLE);
                    }
                    onClickListener.onClickFavouriteInf(arrGigList.get(getAbsoluteAdapterPosition()), getAbsoluteAdapterPosition(), platform);
                }
            });
        }
    }
}
