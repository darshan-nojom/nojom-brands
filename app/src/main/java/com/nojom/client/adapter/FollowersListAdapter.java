package com.nojom.client.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.databinding.ItemFollwersListBinding;
import com.nojom.client.model.FollowersListModel;
import com.nojom.client.util.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FollowersListAdapter extends RecyclerView.Adapter<FollowersListAdapter.SimpleViewHolder> {
    private final Activity activity;
    private final List<FollowersListModel> arrFollowersList;

    public FollowersListAdapter(Activity activity, List<FollowersListModel> arrFollowersList) {
        this.activity = activity;
        this.arrFollowersList = arrFollowersList;
    }

    @NotNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemFollwersListBinding fullBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_follwers_list, parent, false);
        return new SimpleViewHolder(fullBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleViewHolder holder, int position) {
        FollowersListModel item = arrFollowersList.get(position);

        holder.binding.tvTitle.setText(arrFollowersList.get(position).followers);
        if (item.isSelected) {
            holder.binding.tvTitle.setBackground(ContextCompat.getDrawable(activity, R.drawable.black_button_bg));
            Typeface tf = Typeface.createFromAsset(activity.getAssets(), Constants.SFTEXT_BOLD);
            holder.binding.tvTitle.setTypeface(tf);
            holder.binding.tvTitle.setTextColor(ContextCompat.getColor(activity, R.color.white));
        } else {
            holder.binding.tvTitle.setBackgroundColor(Color.TRANSPARENT);
            holder.binding.tvTitle.setTextColor(ContextCompat.getColor(activity, R.color.black));
            Typeface tf = Typeface.createFromAsset(activity.getAssets(), Constants.SFTEXT_REGULAR);
            holder.binding.tvTitle.setTypeface(tf);
        }
    }


    private void clearSelected() {
        if (arrFollowersList != null && arrFollowersList.size() > 0) {
            for (FollowersListModel data : arrFollowersList) {
                data.isSelected = false;
            }
        }
    }

    public FollowersListModel getSelectedItem() {
        if (arrFollowersList != null && arrFollowersList.size() > 0) {
            for (FollowersListModel data : arrFollowersList) {
                if (data.isSelected) {
                    return data;
                }
            }
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return arrFollowersList != null ? arrFollowersList.size() : 0;
    }

    public class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemFollwersListBinding binding;

        public SimpleViewHolder(ItemFollwersListBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.tvTitle.setOnClickListener(v -> {
                clearSelected();
                arrFollowersList.get(getAbsoluteAdapterPosition()).isSelected = true;
                notifyDataSetChanged();
            });
        }
    }
}
