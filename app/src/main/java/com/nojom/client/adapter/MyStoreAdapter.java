package com.nojom.client.adapter;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.StyleableRes;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nojom.client.R;
import com.nojom.client.databinding.ItemInfStoreBinding;
import com.nojom.client.databinding.ItemMyStoreBinding;
import com.nojom.client.model.AgentProfile;
import com.nojom.client.model.GetStores;
import com.nojom.client.ui.BaseActivity;

import java.util.List;

import javax.annotation.Nullable;

public class MyStoreAdapter extends RecyclerView.Adapter<MyStoreAdapter.SimpleViewHolder> {

    private List<GetStores.Data> mDataset;
    private BaseActivity activity;
    private String path;

    public void doRefresh(List<GetStores.Data> objects, BaseActivity activity, String path) {
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
        ItemMyStoreBinding verifiedWithBinding =
                ItemMyStoreBinding.inflate(layoutInflater, parent, false);
        return new SimpleViewHolder(verifiedWithBinding);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {
        GetStores.Data item = mDataset.get(position);
//        holder.binding.imgProfile.setImageDrawable(item.icon);

        holder.binding.txtName.setText(item.title);

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

        ItemMyStoreBinding binding;

        SimpleViewHolder(ItemMyStoreBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.imgView.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(path + mDataset.get(getBindingAdapterPosition()).filename));
                activity.startActivity(intent);
            });
        }
    }
}
