package com.nojom.client.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.databinding.ItemSocialServiceBinding;
import com.nojom.client.model.PriceRange;
import com.nojom.client.ui.BaseActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PriceRangeAdapter extends RecyclerView.Adapter<PriceRangeAdapter.SimpleViewHolder> {
    private final Context context;
    private final BaseActivity activity;
    private List<PriceRange> serviceCatgList;
    private final OnClickCategoryListener onClickCategoryListener;
    private PriceRange selectedLocationList;

    public void setSelectedLanguageList(PriceRange selectedLocationList) {
        this.selectedLocationList = selectedLocationList;
    }

    public PriceRangeAdapter(Context context, List<PriceRange> cardList, OnClickCategoryListener listener) {
        this.context = context;
        activity = (BaseActivity) context;
        this.serviceCatgList = cardList;
        this.onClickCategoryListener = listener;
    }

    @NotNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemSocialServiceBinding itemCardListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_social_service, parent, false);
        return new SimpleViewHolder(itemCardListBinding);
    }

    @Override
    public void onBindViewHolder(@NotNull final SimpleViewHolder holder, int position) {
        try {
            PriceRange data = serviceCatgList.get(position);

            holder.binding.txtCategoryName.setText(data.text);

            /*if (selectedLocationList != null) {
                if (selectedLocationList.id == position) {
                    holder.binding.imgCheck.setImageResource(R.drawable.radio_button_active);
                } else {
                    holder.binding.imgCheck.setImageResource(R.drawable.circle_uncheck);
                }
            } else {
                holder.binding.imgCheck.setImageResource(R.drawable.circle_uncheck);
            }*/

            if (data.isSelected) {
                holder.binding.imgCheck.setImageResource(R.drawable.radio_button_active);
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
                for (PriceRange data : serviceCatgList) {
                    data.isSelected = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<PriceRange> getSelectedItem() {
        ArrayList<PriceRange> selectedLanguages = new ArrayList<>();
        try {
            if (serviceCatgList != null && serviceCatgList.size() > 0) {
                for (PriceRange data : serviceCatgList) {
                    if (data.isSelected) {
                        selectedLanguages.add(data);
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

    public List<PriceRange> getData() {
        return serviceCatgList;
    }

    public interface OnClickCategoryListener {
        void onClickCategory(boolean isAdded, PriceRange data, int adapterPos);
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemSocialServiceBinding binding;

        SimpleViewHolder(ItemSocialServiceBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.getRoot().setOnClickListener(v -> {
                try {
//                    clearSelected();
                    serviceCatgList.get(getAbsoluteAdapterPosition()).isSelected = !serviceCatgList.get(getAbsoluteAdapterPosition()).isSelected;
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
