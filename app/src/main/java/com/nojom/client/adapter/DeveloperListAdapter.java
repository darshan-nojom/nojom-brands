package com.nojom.client.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.databinding.ItemCategoryListBinding;
import com.nojom.client.model.ServicesModel;
import com.nojom.client.ui.BaseActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DeveloperListAdapter extends RecyclerView.Adapter<DeveloperListAdapter.SimpleViewHolder>
        implements Filterable {

    private final List<ServicesModel.Data> mDataset;
    private final Context context;
    private final OnItemClickListener onItemClickListener;
    private List<ServicesModel.Data> mDatasetFiltered;

    public DeveloperListAdapter(Context context, List<ServicesModel.Data> objects, OnItemClickListener onItemClickListener) {
        this.mDataset = objects;
        this.mDatasetFiltered = objects;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @NotNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemCategoryListBinding itemCategoryListBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_category_list, parent, false);
        return new SimpleViewHolder(itemCategoryListBinding);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {
        final ServicesModel.Data item = mDatasetFiltered.get(position);
        holder.binding.tvCategory.setText(item.getServNameByLang(((BaseActivity) context).getLanguage()) == null ? item.name : item.getServNameByLang(((BaseActivity) context).getLanguage()));

        /*if (item.isSelected) {
            holder.binding.tvCategory.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            Typeface tf = Typeface.createFromAsset(context.getAssets(), Constants.SFTEXT_BOLD);
            holder.binding.tvCategory.setTypeface(tf);
        } else {
            holder.binding.tvCategory.setTextColor(Color.BLACK);
            Typeface tf = Typeface.createFromAsset(context.getAssets(), Constants.SFTEXT_REGULAR);
            holder.binding.tvCategory.setTypeface(tf);
        }*/

        holder.binding.rlView.setOnClickListener(v -> {
            holder.binding.ivNext.setVisibility(View.GONE);
            holder.binding.progressBar.setVisibility(View.VISIBLE);
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                holder.binding.progressBar.setVisibility(View.GONE);
                holder.binding.ivNext.setVisibility(View.VISIBLE);
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(item);
            }, 500);
        });

    }

    private void clearSelected() {
        if (mDatasetFiltered != null && mDatasetFiltered.size() > 0) {
            for (ServicesModel.Data data : mDatasetFiltered) {
                data.isSelected = false;
            }
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
                        String rowText = row.getServNameByLang(((BaseActivity) context).getLanguage()) == null ? row.name : row.getServNameByLang(((BaseActivity) context).getLanguage());
//                        String rowText = row.getServNameByLang(((BaseActivity) context).getLanguage()).toLowerCase();
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

    public interface OnItemClickListener {
        void onItemClick(ServicesModel.Data item);
    }

    public class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemCategoryListBinding binding;

        public SimpleViewHolder(ItemCategoryListBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
