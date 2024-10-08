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
import com.nojom.client.databinding.ItemSkillFilterBinding;
import com.nojom.client.model.ServicesModel;
import com.nojom.client.ui.BaseActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SkillFilterAdapter extends RecyclerView.Adapter<SkillFilterAdapter.SimpleViewHolder>
        implements Filterable {

    private List<ServicesModel.Data> mDataset;
    private List<ServicesModel.Data> mDatasetFiltered;
    private BaseActivity context;
    private OnItemClickListener onItemClickListener;

    public SkillFilterAdapter(BaseActivity context, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    public void doRefresh(List<ServicesModel.Data> objects) {
        this.mDataset = objects;
        this.mDatasetFiltered = objects;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(ServicesModel.Data item);
    }

    @NotNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemSkillFilterBinding itemSkillFilterBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_skill_filter, parent, false);
        return new SimpleViewHolder(itemSkillFilterBinding);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {
        final ServicesModel.Data item = mDatasetFiltered.get(position);
        holder.binding.tvCategory.setText(item.getServNameByLang(context.getLanguage()));

        if (item.isSelected) {
            holder.binding.imgCheck.setImageResource(R.drawable.circle_check);
        } else {
            holder.binding.imgCheck.setImageResource(R.drawable.circle_uncheck);
        }

        holder.itemView.setOnClickListener(v -> {
            mDatasetFiltered.get(position).isSelected = !mDatasetFiltered.get(position).isSelected;
            notifyDataSetChanged();
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(mDatasetFiltered.get(position));
        });
    }

    public void clearSelected() {
        if (mDatasetFiltered != null && mDatasetFiltered.size() > 0) {
            for (ServicesModel.Data data : mDatasetFiltered) {
                data.isSelected = false;
            }
            notifyDataSetChanged();
        }
    }

    public ServicesModel.Data getSelectedItem() {
        if (mDatasetFiltered != null && mDatasetFiltered.size() > 0) {
            for (ServicesModel.Data data : mDatasetFiltered) {
                if (data.isSelected) {
                    return data;
                }
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return mDatasetFiltered != null ? mDatasetFiltered.size() : 0;
    }

    public List<ServicesModel.Data> getData() {
        return mDatasetFiltered;
    }

    public class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemSkillFilterBinding binding;

        public SimpleViewHolder(ItemSkillFilterBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mDatasetFiltered = mDataset;
                } else {
                    List<ServicesModel.Data> filteredList = new ArrayList<>();
                    for (ServicesModel.Data row : mDataset) {
                        String rowText = row.name.toLowerCase();
                        if (!TextUtils.isEmpty(rowText)) {
                            if (rowText.contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }
                    }

                    mDatasetFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mDatasetFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mDatasetFiltered = (List<ServicesModel.Data>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
