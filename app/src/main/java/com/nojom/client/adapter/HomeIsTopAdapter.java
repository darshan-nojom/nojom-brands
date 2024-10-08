package com.nojom.client.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.databinding.ItemHomeIsTopBinding;
import com.nojom.client.model.HomeIsTopService;
import com.nojom.client.ui.BaseActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomeIsTopAdapter extends RecyclerView.Adapter<HomeIsTopAdapter.SimpleViewHolder> {
    private Context context;
    private Activity activity;
    private List<HomeIsTopService> serviceCatgList;
    private OnClickCategoryListener onClickCategoryListener;

    public HomeIsTopAdapter(Context context, List<HomeIsTopService> cardList, int defaultService, OnClickCategoryListener listener) {
        this.context = context;
        activity = (BaseActivity) context;
        this.serviceCatgList = cardList;
        this.onClickCategoryListener = listener;
    }

    @NotNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemHomeIsTopBinding itemCardListBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_home_is_top, parent, false);
        return new SimpleViewHolder(itemCardListBinding);
    }

    @Override
    public void onBindViewHolder(@NotNull final SimpleViewHolder holder, final int position) {
        try {
            HomeIsTopService data = serviceCatgList.get(position);

            holder.binding.txtCategoryName.setText(data.categoryNameApp);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return serviceCatgList != null ? serviceCatgList.size() : 0;
    }

    public List<HomeIsTopService> getData() {
        return serviceCatgList;
    }

    public interface OnClickCategoryListener {
        void onClickCategory(HomeIsTopService category);
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemHomeIsTopBinding binding;

        SimpleViewHolder(ItemHomeIsTopBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.getRoot().setOnClickListener(v -> {
                try {
                    if (onClickCategoryListener != null) {
                        onClickCategoryListener.onClickCategory(serviceCatgList.get(getAbsoluteAdapterPosition()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

}
