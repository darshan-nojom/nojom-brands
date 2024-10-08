package com.nojom.client.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.databinding.ItemSocialServiceBinding;
import com.nojom.client.model.ServicesModel;
import com.nojom.client.model.SkillTags;
import com.nojom.client.ui.BaseActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SkillAdapter extends RecyclerView.Adapter<SkillAdapter.SimpleViewHolder> implements Filterable {
    private final Context context;
    private final BaseActivity activity;
    private List<SkillTags.Data> serviceCatgList;
    private final List<SkillTags.Data> mDatasetFiltered;
    private final OnClickSkillListener onClickCategoryListener;
    private ArrayList<SkillTags.Data> selectedLocationList;

    public void setSelectedLanguageList(ArrayList<SkillTags.Data> selectedLocationList) {
        this.selectedLocationList = selectedLocationList;
    }

    public SkillAdapter(Context context, List<SkillTags.Data> cardList, OnClickSkillListener listener) {
        this.context = context;
        activity = (BaseActivity) context;
        this.serviceCatgList = cardList;
        this.mDatasetFiltered = cardList;
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
    public void onBindViewHolder(@NotNull final SimpleViewHolder holder,  int position) {
        try {
            SkillTags.Data data = serviceCatgList.get(position);

            holder.binding.txtCategoryName.setText(data.getServNameByLang(activity.getLanguage()));

            if (selectedLocationList.contains(data)) {
                holder.binding.imgCheck.setImageResource(R.drawable.circle_check);
            } else {
                holder.binding.imgCheck.setImageResource(R.drawable.circle_uncheck);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearSelected() {
        try {
            if (mDatasetFiltered != null && mDatasetFiltered.size() > 0) {
                for (SkillTags.Data data : mDatasetFiltered) {
                    data.isSelected = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<SkillTags.Data> getSelectedItem() {
        ArrayList<SkillTags.Data> selectedLanguages = new ArrayList<>();
        try {
            if (mDatasetFiltered != null && mDatasetFiltered.size() > 0) {
                for (SkillTags.Data data : mDatasetFiltered) {
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
        return serviceCatgList != null ? serviceCatgList.size() : 0;
    }

    public List<SkillTags.Data> getData() {
        return serviceCatgList;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    serviceCatgList = mDatasetFiltered;
                } else {
                    List<SkillTags.Data> filteredList = new ArrayList<>();
                    for (SkillTags.Data row : mDatasetFiltered) {
                        String rowText = row.getServNameByLang(activity.getLanguage()).toLowerCase();
                        if (!TextUtils.isEmpty(rowText)) {
                            if (rowText.contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }
                    }

                    serviceCatgList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = serviceCatgList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                serviceCatgList = (List<SkillTags.Data>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface OnClickSkillListener {
        void onClickSkill(boolean isAdded, SkillTags.Data data, int adapterPos);
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemSocialServiceBinding binding;

        SimpleViewHolder(ItemSocialServiceBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.getRoot().setOnClickListener(v -> {
                try {

                    serviceCatgList.get(getAbsoluteAdapterPosition()).isSelected = !serviceCatgList.get(getAbsoluteAdapterPosition()).isSelected;

                    if (onClickCategoryListener != null) {
                        onClickCategoryListener.onClickSkill(serviceCatgList.get(getAbsoluteAdapterPosition()).isSelected, serviceCatgList.get(getAbsoluteAdapterPosition()), getAbsoluteAdapterPosition());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
