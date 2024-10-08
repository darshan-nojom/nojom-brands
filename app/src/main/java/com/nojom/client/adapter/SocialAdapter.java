package com.nojom.client.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.databinding.ItemSocialBinding;
import com.nojom.client.util.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class SocialAdapter extends RecyclerView.Adapter<SocialAdapter.ViewHolder> {

    private Context context;
    private List<String> socialList;
    private int selectedService = -1;

    public SocialAdapter(Context context, List<String> socialList) {
        this.context = context;
        this.socialList = socialList;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemSocialBinding socialBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_social, parent, false);
        return new ViewHolder(socialBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.tvSkill.setText(socialList.get(position));

        if (selectedService == position) {
            holder.binding.tvSkill.setBackground(ContextCompat.getDrawable(context, R.drawable.black_button_bg));
            holder.binding.tvSkill.setTextColor(Color.WHITE);
            Typeface tf = Typeface.createFromAsset(context.getAssets(), Constants.SFTEXT_BOLD);
            holder.binding.tvSkill.setTypeface(tf);
        } else {
            holder.binding.tvSkill.setBackground(ContextCompat.getDrawable(context, R.drawable.white_button_bg));
            holder.binding.tvSkill.setTextColor(Color.BLACK);
            Typeface tf = Typeface.createFromAsset(context.getAssets(), Constants.SFTEXT_REGULAR);
            holder.binding.tvSkill.setTypeface(tf);
        }
    }

    public String getSelectedItem() {
        if (selectedService != -1) {
            return socialList.get(selectedService);
        }
        return "";
    }

    @Override
    public int getItemCount() {
        return socialList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemSocialBinding binding;

        public ViewHolder(ItemSocialBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.tvSkill.setOnClickListener(view1 -> {
                selectedService = getAbsoluteAdapterPosition();
                notifyDataSetChanged();
            });
        }
    }
}  