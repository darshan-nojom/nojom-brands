package com.nojom.client.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.databinding.ItemSelectFullBinding;
import com.nojom.client.model.CityModel;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SelectCityListAdapter extends RecyclerView.Adapter<SelectCityListAdapter.SimpleViewHolder>
        implements Filterable {

    private List<CityModel.Data> mDataset;
    private List<CityModel.Data> mDatasetFiltered;
    private Context context;
    private BaseActivity activity;

    public SelectCityListAdapter(Context context, List<CityModel.Data> objects) {
        this.mDataset = objects;
        this.mDatasetFiltered = objects;
        this.context = context;
        this.activity = (BaseActivity) context;
    }

    @NotNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemSelectFullBinding fullBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_select_full, parent, false);
        return new SimpleViewHolder(fullBinding);
    }

    @Override
    public void onBindViewHolder(@NotNull final SimpleViewHolder holder, final int position) {
        CityModel.Data item = mDatasetFiltered.get(position);

        holder.binding.tvTitle.setText(item.getCityName(activity.getLanguage()));

        if (item.isSelected) {
            holder.binding.tvTitle.setBackground(isBlackColor ? ContextCompat.getDrawable(context, R.drawable.black_button_bg) : ContextCompat.getDrawable(context, R.drawable.blue_button_bg));
            holder.binding.tvTitle.setTextColor(ContextCompat.getColor(context, R.color.white));
            Typeface tf = Typeface.createFromAsset(context.getAssets(), Constants.SFTEXT_BOLD);
            holder.binding.tvTitle.setTypeface(tf);
        } else {
            holder.binding.tvTitle.setBackgroundColor(Color.TRANSPARENT);
            holder.binding.tvTitle.setTextColor(ContextCompat.getColor(context, R.color.black));
            Typeface tf = Typeface.createFromAsset(context.getAssets(), Constants.SFTEXT_REGULAR);
            holder.binding.tvTitle.setTypeface(tf);
        }
    }

    public boolean isBlackColor;
    public void setBlackColor(boolean blackColor) {
        isBlackColor = blackColor;
    }
    private void clearSelected() {
        if (mDatasetFiltered != null && mDatasetFiltered.size() > 0) {
            for (CityModel.Data data : mDatasetFiltered) {
                data.isSelected = false;
            }
        }
    }

    public CityModel.Data getSelectedItem() {
        if (mDatasetFiltered != null && mDatasetFiltered.size() > 0) {
            for (CityModel.Data data : mDatasetFiltered) {
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

    public List<CityModel.Data> getData() {
        return mDatasetFiltered;
    }

    public class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemSelectFullBinding binding;

        public SimpleViewHolder(ItemSelectFullBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.rlView.setOnClickListener(v -> {
                clearSelected();
                mDatasetFiltered.get(getAbsoluteAdapterPosition()).isSelected = true;
                notifyDataSetChanged();
            });
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
                    List<CityModel.Data> filteredList = new ArrayList<>();
                    for (CityModel.Data row : mDataset) {
                        String rowText = row.getCityName(activity.getLanguage()).toLowerCase();
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
                mDatasetFiltered = (List<CityModel.Data>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
