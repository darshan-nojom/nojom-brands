package com.nojom.client.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.databinding.ItemVerifiedWithBinding;
import com.nojom.client.model.Profile;

import java.util.List;

public class VerifiedAdapter extends RecyclerView.Adapter<VerifiedAdapter.SimpleViewHolder> {

    private List<Profile.VerifiedWith> mDataset;

    public void doRefresh(List<Profile.VerifiedWith> objects) {
        this.mDataset = objects;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        ItemVerifiedWithBinding verifiedWithBinding =
                ItemVerifiedWithBinding.inflate(layoutInflater, parent, false);
        return new SimpleViewHolder(verifiedWithBinding);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {
        Profile.VerifiedWith item = mDataset.get(position);
        holder.binding.tvTitle.setText(item.name);
        if (item.isVerified > 0) {
            holder.binding.imgVerify.setImageResource(R.drawable.verified_tick);
        } else {
            holder.binding.imgVerify.setImageResource(R.drawable.not_verified);
        }
    }

    @Override
    public int getItemCount() {
        return mDataset != null ? mDataset.size() : 0;
    }

    static class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemVerifiedWithBinding binding;

        SimpleViewHolder(ItemVerifiedWithBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
