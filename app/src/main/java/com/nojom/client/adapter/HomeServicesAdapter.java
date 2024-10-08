package com.nojom.client.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.databinding.ItemHomeCategoryBinding;
import com.nojom.client.model.HomeServiceCatg;
import com.nojom.client.ui.BaseActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomeServicesAdapter extends RecyclerView.Adapter<HomeServicesAdapter.SimpleViewHolder> {
    private final Context context;
    private final Activity activity;
    private final List<HomeServiceCatg> serviceCatgList;
    private final OnClickCategoryListener onClickCategoryListener;
    private int selectedCategory;

    public HomeServicesAdapter(Context context, List<HomeServiceCatg> cardList, int defaultService, OnClickCategoryListener listener) {
        this.context = context;
        activity = (BaseActivity) context;
        this.serviceCatgList = cardList;
        this.selectedCategory = defaultService;
        this.onClickCategoryListener = listener;
    }

    @NotNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemHomeCategoryBinding itemCardListBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_home_category, parent, false);
        return new SimpleViewHolder(itemCardListBinding);
    }

    @Override
    public void onBindViewHolder(@NotNull final SimpleViewHolder holder, final int position) {
        try {
            HomeServiceCatg data = serviceCatgList.get(position);

            holder.binding.txtCategoryName.setText(data.categoryAppMenuName);

            if (selectedCategory == data.id) {
                holder.binding.txtCategoryName.setTextColor(!data.categoryColor.equalsIgnoreCase("#000000") ? Color.parseColor(data.categoryColor) : activity.getResources().getColor(R.color.white));
                holder.binding.loutHeader.setBackground(activity.getResources().getDrawable(R.drawable.yello_border_5));
                GradientDrawable drawable = (GradientDrawable) holder.binding.loutHeader.getBackground();
                drawable.setStroke((int) activity.getResources().getDimension(R.dimen.radius), !data.categoryColor.equalsIgnoreCase("#000000") ? Color.parseColor(data.categoryColor) : activity.getResources().getColor(R.color.white));
                holder.binding.loutHeader.setBackground(drawable);
            } else {
                holder.binding.loutHeader.setBackground(activity.getResources().getDrawable(R.drawable.light_black_border_5));
                holder.binding.txtCategoryName.setTextColor(activity.getResources().getColor(R.color.light_black));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return serviceCatgList != null ? serviceCatgList.size() : 0;
    }

    public List<HomeServiceCatg> getData() {
        return serviceCatgList;
    }

    public void doRefresh(int selectedCatId) {
        this.selectedCategory = selectedCatId;
        notifyDataSetChanged();
    }

    public interface OnClickCategoryListener {
        void onClickCategory(HomeServiceCatg category);
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemHomeCategoryBinding binding;

        SimpleViewHolder(ItemHomeCategoryBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.getRoot().setOnClickListener(v -> {
                try {
                    if (onClickCategoryListener != null) {
                        onClickCategoryListener.onClickCategory(serviceCatgList.get(getAbsoluteAdapterPosition()));
                    }
                    selectedCategory = serviceCatgList.get(getAbsoluteAdapterPosition()).id;
                    notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

}
