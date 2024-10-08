package com.nojom.client.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.databinding.ItemServiceSellersBinding;
import com.nojom.client.model.ServicesModel;
import com.nojom.client.ui.BaseActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ServiceSellersAdapter extends RecyclerView.Adapter<ServiceSellersAdapter.SimpleViewHolder> {
    private final BaseActivity activity;
    private final OnClickListener onClickCategory;
    private ArrayList<ServicesModel.Data> arrServiceSellerList;

    public ServiceSellersAdapter(BaseActivity activity, ArrayList<ServicesModel.Data> arrServiceSellerList, OnClickListener listener) {
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
            holder.binding.txtName.setText(arrServiceSellerList.get(position).getServNameByLang(activity.getLanguage()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return arrServiceSellerList != null ? arrServiceSellerList.size() : 0;
    }

    public interface OnClickListener {
        void onClickListener(ServicesModel.Data data);
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemServiceSellersBinding binding;

        SimpleViewHolder(ItemServiceSellersBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.getRoot().setOnClickListener(v -> {
                try {
                    if (onClickCategory != null) {
                        onClickCategory.onClickListener(arrServiceSellerList.get(getAbsoluteAdapterPosition()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

}
