package com.nojom.client.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.databinding.ItemServiceSellersBinding;
import com.nojom.client.model.ServiceSellersTopCatg;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ServiceSellersTopAdapter extends RecyclerView.Adapter<ServiceSellersTopAdapter.SimpleViewHolder> {
    private final Activity activity;
    private final OnClickTopListener onClickCategory;
    private ArrayList<ServiceSellersTopCatg> arrServiceSellerList;

    public ServiceSellersTopAdapter(Activity activity, ArrayList<ServiceSellersTopCatg> arrServiceSellerList, OnClickTopListener listener) {
        this.activity = activity;
        this.arrServiceSellerList = arrServiceSellerList;
        this.onClickCategory = listener;
    }

    @NotNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemServiceSellersBinding itemCardListBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_service_sellers, parent, false);
        return new SimpleViewHolder(itemCardListBinding);
    }

    @Override
    public void onBindViewHolder(@NotNull final SimpleViewHolder holder, final int position) {
        try {
            holder.binding.txtName.setText(arrServiceSellerList.get(position).categoryName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return arrServiceSellerList != null ? arrServiceSellerList.size() : 0;
    }


    public interface OnClickTopListener {
        void onClickTopListener(ServiceSellersTopCatg serviceSellersTopCatg);
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemServiceSellersBinding binding;

        SimpleViewHolder(ItemServiceSellersBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.getRoot().setOnClickListener(v -> {
                try {
                    if (onClickCategory != null) {
                        onClickCategory.onClickTopListener(arrServiceSellerList.get(getAbsoluteAdapterPosition()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

}
