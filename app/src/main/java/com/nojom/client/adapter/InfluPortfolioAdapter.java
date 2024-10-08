package com.nojom.client.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
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
import com.nojom.client.databinding.ItemPortfolioListBinding;
import com.nojom.client.databinding.ItemPortfolioSmallBinding;
import com.nojom.client.model.Portfolios;
import com.nojom.client.ui.BaseActivity;

import java.util.List;

public class InfluPortfolioAdapter extends RecyclerView.Adapter<InfluPortfolioAdapter.SimpleViewHolder> {
    private List<Portfolios.Data> mDataset;
    private BaseActivity activity;
    private String filePath;


    public InfluPortfolioAdapter(BaseActivity context, List<Portfolios.Data> objects, String filePath) {
        this.mDataset = objects;
        this.filePath = filePath;
        activity = context;
    }

    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        ItemPortfolioSmallBinding listFilesBinding =
                ItemPortfolioSmallBinding.inflate(layoutInflater, parent, false);
        return new SimpleViewHolder(listFilesBinding);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {
        Portfolios.Data item = mDataset.get(position);

        holder.binding.tvUserName.setText(item.getCompany_name(activity.getLanguage()));
        if (item.filename != null) {

            Glide.with(activity).load(filePath + item.filename)
                    .placeholder(R.mipmap.ic_launcher)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                            progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(holder.binding.image);


        }
    }

    @Override
    public int getItemCount() {
        return mDataset != null ? mDataset.size() : 0;
    }

    public List<Portfolios.Data> getData() {
        return mDataset;
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemPortfolioSmallBinding binding;

        SimpleViewHolder(ItemPortfolioSmallBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

        }
    }

}
