package com.nojom.client.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nojom.client.R;
import com.nojom.client.databinding.ItemMyStoreBinding;
import com.nojom.client.databinding.ItemPartnersBinding;
import com.nojom.client.model.GetAgentPartners;
import com.nojom.client.model.GetStores;
import com.nojom.client.ui.BaseActivity;

import java.util.List;

import javax.annotation.Nullable;

public class PartnerAdapter extends RecyclerView.Adapter<PartnerAdapter.SimpleViewHolder> {

    private List<GetAgentPartners.Data> mDataset;
    private BaseActivity activity;
    private String path;

    public void doRefresh(List<GetAgentPartners.Data> objects, BaseActivity activity, String path) {
        this.mDataset = objects;
        this.activity = activity;
        this.path = path;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        ItemPartnersBinding verifiedWithBinding =
                ItemPartnersBinding.inflate(layoutInflater, parent, false);
        return new SimpleViewHolder(verifiedWithBinding);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {
        GetAgentPartners.Data item = mDataset.get(position);
//        holder.binding.imgProfile.setImageDrawable(item.icon);

        holder.binding.txtTitle.setText(item.getName(activity.getLanguage()));
        if (!TextUtils.isEmpty(item.code)) {
            holder.binding.relCode.setVisibility(View.VISIBLE);
            holder.binding.txtCode.setText(item.code);
        } else {
            holder.binding.relCode.setVisibility(View.GONE);
        }


        if (!TextUtils.isEmpty(item.filename)) {
            Glide.with(holder.binding.imgProfile.getContext()).load(path + item.filename)
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(holder.binding.imgProfile);
        } else {
            holder.binding.imgProfile.setImageResource(R.mipmap.ic_launcher_round);
        }
    }

    @Override
    public int getItemCount() {
        return mDataset != null ? mDataset.size() : 0;
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemPartnersBinding binding;

        SimpleViewHolder(ItemPartnersBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.imgArrow.setOnClickListener(v -> {
                copyMsg(binding.txtCode.getText().toString());
            });

            binding.txtFollowerCount.setOnClickListener(v -> {
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mDataset.get(getAdapterPosition()).link)));
            });
        }
    }

    private void copyMsg(String msg) {
        ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied", msg);
        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
            activity.toastMessage(activity.getString(R.string.copied));
        }
    }
}
