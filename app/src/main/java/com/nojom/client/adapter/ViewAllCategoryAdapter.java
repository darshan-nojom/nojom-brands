package com.nojom.client.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.databinding.ItemViewAllCategoryBinding;
import com.nojom.client.model.HomeServiceCatg;
import com.nojom.client.ui.BaseActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ViewAllCategoryAdapter extends RecyclerView.Adapter<ViewAllCategoryAdapter.SimpleViewHolder> {
    private Context context;
    private Activity activity;
    private List<HomeServiceCatg> serviceCatgList;
    private int selectedCategory;
    private OnClickCategoryListener onClickCategoryListener;

    public ViewAllCategoryAdapter(Context context, List<HomeServiceCatg> cardList, int defaultService, OnClickCategoryListener listener) {
        this.context = context;
        activity = (BaseActivity) context;
        this.serviceCatgList = cardList;
        this.selectedCategory = defaultService;
        this.onClickCategoryListener = listener;
    }

    @NotNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemViewAllCategoryBinding itemCardListBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_view_all_category, parent, false);
        return new SimpleViewHolder(itemCardListBinding);
    }

    @Override
    public void onBindViewHolder(@NotNull final SimpleViewHolder holder, final int position) {
        try {
            HomeServiceCatg data = serviceCatgList.get(position);

            holder.binding.txtCategoryName.setText(data.categoryAppMenuName);

            if (selectedCategory == data.id) {
                holder.binding.txtCategoryName.setBackground(activity.getResources().getDrawable(R.drawable.blue_button_bg));
                holder.binding.txtCategoryName.setTextColor(activity.getResources().getColor(R.color.white));
            } else {
                holder.binding.txtCategoryName.setBackground(activity.getResources().getDrawable(R.drawable.white_button_bg));
                holder.binding.txtCategoryName.setTextColor(activity.getResources().getColor(R.color.tab_gray));
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

        ItemViewAllCategoryBinding binding;

        SimpleViewHolder(ItemViewAllCategoryBinding itemView) {
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
