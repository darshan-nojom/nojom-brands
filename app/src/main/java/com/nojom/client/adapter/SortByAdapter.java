package com.nojom.client.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.databinding.ItemSocialServiceBinding;
import com.nojom.client.model.PriceRange;
import com.nojom.client.model.SortByFilterModel;
import com.nojom.client.ui.BaseActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SortByAdapter extends RecyclerView.Adapter<SortByAdapter.SimpleViewHolder> {
    private final Context context;
    private final BaseActivity activity;
    private List<SortByFilterModel> serviceCatgList;
    private final OnClickCategoryListener onClickCategoryListener;
    private SortByFilterModel selectedLocationList;

    public void setSelectedLanguageList(SortByFilterModel selectedLocationList) {
        this.selectedLocationList = selectedLocationList;
    }

    public SortByAdapter(Context context, List<SortByFilterModel> cardList, OnClickCategoryListener listener) {
        this.context = context;
        activity = (BaseActivity) context;
        this.serviceCatgList = cardList;
        this.onClickCategoryListener = listener;
    }

    @NotNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemSocialServiceBinding itemCardListBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_social_service, parent, false);
        return new SimpleViewHolder(itemCardListBinding);
    }

    @Override
    public void onBindViewHolder(@NotNull final SimpleViewHolder holder, int position) {
        try {
            SortByFilterModel data = serviceCatgList.get(position);

            holder.binding.txtCategoryName.setText(data.filterName);

            if (selectedLocationList != null) {
                if (selectedLocationList.filterID.equals(data.filterID)) {
                    holder.binding.imgCheck.setImageResource(R.drawable.radio_button_active);
                } else {
                    holder.binding.imgCheck.setImageResource(R.drawable.circle_uncheck);
                }
            } else {
                holder.binding.imgCheck.setImageResource(R.drawable.circle_uncheck);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearSelected() {
        try {
            if (serviceCatgList != null && serviceCatgList.size() > 0) {
                for (SortByFilterModel data : serviceCatgList) {
                    data.isSelected = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SortByFilterModel getSelectedItem() {
        SortByFilterModel selectedLanguages = null;
        try {
            if (serviceCatgList != null && serviceCatgList.size() > 0) {
                for (SortByFilterModel data : serviceCatgList) {
                    if (data.isSelected) {
                        selectedLanguages = (data);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return selectedLanguages;
    }

    @Override
    public int getItemCount() {
        return serviceCatgList.size();
    }

    public List<SortByFilterModel> getData() {
        return serviceCatgList;
    }

    public interface OnClickCategoryListener {
        void onClickCategory(boolean isAdded, SortByFilterModel data, int adapterPos);
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemSocialServiceBinding binding;

        SimpleViewHolder(ItemSocialServiceBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.getRoot().setOnClickListener(v -> {
                try {
//                    clearSelected();
                    serviceCatgList.get(getAbsoluteAdapterPosition()).isSelected = true;
                    selectedLocationList = serviceCatgList.get(getAbsoluteAdapterPosition());

                    if (onClickCategoryListener != null) {
                        onClickCategoryListener.onClickCategory(serviceCatgList.get(getAbsoluteAdapterPosition()).isSelected, serviceCatgList.get(getAbsoluteAdapterPosition()), getAbsoluteAdapterPosition());
                    }
                    notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
