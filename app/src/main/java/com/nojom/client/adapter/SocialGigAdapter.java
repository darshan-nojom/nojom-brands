package com.nojom.client.adapter;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.florent37.viewtooltip.ViewTooltip;
import com.nojom.client.R;
import com.nojom.client.databinding.ItemSocialGigBinding;
import com.nojom.client.model.ExpertGigDetail;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Utils;

import java.util.List;
import java.util.Objects;

public class SocialGigAdapter extends RecyclerView.Adapter<SocialGigAdapter.SimpleViewHolder> {
    private final BaseActivity activity;
    private final List<ExpertGigDetail.SocialPlatform> arrSocialList;
    private final OnItemClick onItemClick;
    LayoutInflater layoutInflater;

    public SocialGigAdapter(BaseActivity activity, List<ExpertGigDetail.SocialPlatform> arrSocialList, OnItemClick onItemClick) {
        this.activity = activity;
        this.arrSocialList = arrSocialList;
        this.onItemClick = onItemClick;
        layoutInflater = activity.getLayoutInflater();
    }

    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSocialGigBinding popularBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_social_gig, parent, false);
        return new SimpleViewHolder(popularBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final SimpleViewHolder holder, int position) {
        try {
            ExpertGigDetail.SocialPlatform item = arrSocialList.get(position);

            holder.binding.txtGigName.setText(activity.formatNumber(item.followersCount));

            if (item.name.equalsIgnoreCase("Instagram")) {
                holder.binding.rlPlatformBg.setBackground(activity.getResources().getDrawable(R.drawable.insta_bg_5));
            } else {
                Drawable buttonDrawable = holder.binding.rlPlatformBg.getBackground();
                buttonDrawable = DrawableCompat.wrap(buttonDrawable);
                //the color is a direct color int and not a color resource
                if (!TextUtils.isEmpty(item.colorCode)) {
                    DrawableCompat.setTint(buttonDrawable, Color.parseColor(item.colorCode));
                    holder.binding.rlPlatformBg.setBackground(buttonDrawable);
                }
            }

            socialImage(item.platformIcon, holder.binding.imgPlatform);

            if (!TextUtils.isEmpty(item.username)) {
                holder.binding.txtUserName.setText("@" + item.username);
            }

            holder.binding.imgSocialInfom.setOnClickListener(v -> ViewTooltip
                    .on(activity, holder.binding.imgSocialInfom)
                    .color(activity.getResources().getColor(R.color.black))
                    .textColor(activity.getResources().getColor(R.color.white))
                    .autoHide(true, 3000)
                    .corner(30)
                    .position(ViewTooltip.Position.BOTTOM)
                    .text(activity.formatNumber(item.followersCount) + " " + activity.getString(R.string.followers))
                    .show());

            holder.binding.txtUserName.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.redirectUrl + item.username));
                activity.startActivity(intent);
            });

            holder.binding.rlPlatformBg.setOnClickListener(v -> {
                platformDialog(arrSocialList);
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void socialImage(String platformIcon, ImageView imgPlatform) {
        Glide.with(activity)
                .load(platformIcon)
                .into(imgPlatform);
    }

    @Override
    public int getItemCount() {
        return arrSocialList.size();
    }


    public interface OnItemClick {
        void onClickItem(ExpertGigDetail.CustomPackage customPackage);
    }


    class SimpleViewHolder extends RecyclerView.ViewHolder {
        ItemSocialGigBinding binding;

        public SimpleViewHolder(ItemSocialGigBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.txtUserName.setPaintFlags(binding.txtUserName.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }
    }

    private void platformDialog(List<ExpertGigDetail.SocialPlatform> arrSocialList) {
        final Dialog dialog = new Dialog(activity, R.style.Theme_AppCompat_Dialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_choose_platform);
        dialog.setCancelable(true);

        RecyclerView rvPlatform = dialog.findViewById(R.id.rv_platform);
//        List<SocialPlatformModel.Data> platformList = new ArrayList<>(Preferences.getSocialPlatforms(activity));//social platforms
//        if (platformList.size() > 0) {
//
//        }
        PlatformSelectionAdapter adapter = new PlatformSelectionAdapter(activity, arrSocialList);
        rvPlatform.setAdapter(adapter);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.gravity = Gravity.BOTTOM;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.show();
//        lp.width = (int) (displaymetrics.widthPixels * 0.9);
//        lp.height = (int) (displaymetrics.heightPixels * 0.7);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }
}
