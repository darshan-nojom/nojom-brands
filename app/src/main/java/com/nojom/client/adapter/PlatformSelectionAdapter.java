package com.nojom.client.adapter;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nojom.client.R;
import com.nojom.client.databinding.ItemPlatformSelectionBinding;
import com.nojom.client.databinding.ItemSocialGigBinding;
import com.nojom.client.model.ExpertGigDetail;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Utils;

import java.util.List;

public class PlatformSelectionAdapter extends RecyclerView.Adapter<PlatformSelectionAdapter.SimpleViewHolder> {
    private final BaseActivity activity;
    private final List<ExpertGigDetail.SocialPlatform> arrSocialList;
    //    private final OnItemClick onItemClick;
    LayoutInflater layoutInflater;

    public PlatformSelectionAdapter(BaseActivity activity, List<ExpertGigDetail.SocialPlatform> arrSocialList) {
        this.activity = activity;
        this.arrSocialList = arrSocialList;
//        this.onItemClick = onItemClick;
        layoutInflater = activity.getLayoutInflater();
    }

    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPlatformSelectionBinding popularBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_platform_selection, parent, false);
        return new SimpleViewHolder(popularBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final SimpleViewHolder holder, int position) {
        try {
            ExpertGigDetail.SocialPlatform item = arrSocialList.get(position);

            holder.binding.txtGigName.setText(item.name);
            holder.binding.txtUserName.setText(activity.getString(R.string.followers) + ": " + activity.formatNumber(item.followersCount));

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
        ItemPlatformSelectionBinding binding;

        public SimpleViewHolder(ItemPlatformSelectionBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
//            binding.txtUserName.setPaintFlags(binding.txtUserName.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }
    }

}
