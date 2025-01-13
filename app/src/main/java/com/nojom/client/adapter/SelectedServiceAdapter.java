package com.nojom.client.adapter;


import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
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
import com.nojom.client.databinding.ItemInfServiceBinding;
import com.nojom.client.model.Serv;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Utils;

import java.util.ArrayList;
import java.util.List;


public class SelectedServiceAdapter extends RecyclerView.Adapter<SelectedServiceAdapter.SimpleViewHolder> {

    private final List<Serv> mDataset;
    private final BaseActivity activity;
    private OnClickServiceListener onClickServiceListener;

    private boolean isLimit = false;

    public void setLimit(boolean limit) {
        isLimit = limit;
    }

    public void setOnclickListener(OnClickServiceListener listener) {
        this.onClickServiceListener = listener;
    }

    public interface OnClickServiceListener {
        void onClickService(int pos, Serv serv);

        void onClickServiceChecked();
    }

    public SelectedServiceAdapter(ArrayList<Serv> services, BaseActivity profileUpdateActivity) {
        this.mDataset = services;
        this.activity = profileUpdateActivity;
    }

//    public void doRefresh(ArrayList<Serv> objects, BaseActivity activity) {
//        this.mDataset = objects;
//        this.activity = activity;
//        notifyDataSetChanged();
//    }

    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemInfServiceBinding verifiedWithBinding = ItemInfServiceBinding.inflate(layoutInflater, parent, false);
        return new SimpleViewHolder(verifiedWithBinding);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {
        Serv item = mDataset.get(position);
        if (item.id == -1) {
            if (!isLimit) {
                int[] colorList = {R.color.colorPrimary};
                String[] fonts = {Constants.SFTEXT_BOLD};
                String[] words = {calculatePercentage() + "%"};

                holder.binding.tvCount.setText(Utils.getBoldString(activity, String.format(activity.getString(R.string._discount), calculatePercentage()), fonts, colorList, words));
            }
            holder.binding.tvName.setText(activity.getString(R.string.all_platforms));
        } else {
            holder.binding.tvName.setText(item.getName(activity.getLanguage()));
        }

        if (item.followers != 0) {
            String formattedNumber = activity.formatNumber(item.followers);
            holder.binding.tvCount.setText("(" + formattedNumber + ")");
        }
        if (item.price != 0) {
            String formattedNumber = Utils.getDecimalFormat(Utils.formatValue(item.price));
            holder.binding.tvPrice.setText(formattedNumber + " " + activity.getString(R.string.sar));
        }
        if (item.isChecked) {
            holder.binding.imgChk.setImageResource(R.drawable.ic_checked);
        } else {
            holder.binding.imgChk.setImageResource(R.drawable.ic_check_outline_gray);
        }

        if (!TextUtils.isEmpty(item.filename)) {
            holder.binding.imgProfile.setVisibility(View.VISIBLE);
            Glide.with(holder.binding.imgProfile.getContext()).load(item.filename).placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round).diskCacheStrategy(DiskCacheStrategy.ALL).listener(new RequestListener<Drawable>() {
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
            holder.binding.imgProfile.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mDataset != null ? isLimit ? Math.min(mDataset.size(), 3) : mDataset.size() : 0;
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemInfServiceBinding binding;

        SimpleViewHolder(ItemInfServiceBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.imgChk.setOnClickListener(view -> {
                if (mDataset.get(getAdapterPosition()).id == -1) {
                    for (Serv ser : mDataset) {
                        ser.isChecked = !mDataset.get(getAdapterPosition()).isChecked;
                    }
                } else {
                    mDataset.get(getAdapterPosition()).isChecked = !mDataset.get(getAdapterPosition()).isChecked;
                }
                notifyDataSetChanged();
                if (onClickServiceListener != null) {
                    onClickServiceListener.onClickServiceChecked();
                }

            });


            binding.getRoot().setOnClickListener(v -> {
                if (onClickServiceListener != null) {
                    onClickServiceListener.onClickService(getAdapterPosition(), mDataset.get(getAdapterPosition()));
                }
            });
        }
    }

    public double calculatePrice() {
        double finalPrice = 0;
        for (Serv data : mDataset) {
            if (data.price > 0 && data.isChecked && data.id == -1) {
                finalPrice = data.price;
                break;
            } else if (data.price > 0 && data.isChecked) {
                finalPrice = finalPrice + data.price;
            }
        }
        if (finalPrice == 0) {
            return 0;
        }
        return finalPrice + ((finalPrice * 5) / 100);//5% service tax
    }

    public String calculatePercentage() {
        double oldPrice = 0;
        double allPrice = 0;
        for (Serv data : mDataset) {
            if (data.id != -1) {
                oldPrice = oldPrice + data.price;
            } else {
                allPrice = data.price;
            }
        }
        double percDis = (oldPrice - allPrice) / oldPrice;
        return Utils.getDecimalFormat(Utils.formatValue(percDis * 100));

    }
}
