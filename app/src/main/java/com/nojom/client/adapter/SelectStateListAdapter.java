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
import com.nojom.client.model.StateModel;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SelectStateListAdapter extends RecyclerView.Adapter<SelectStateListAdapter.SimpleViewHolder>
        implements Filterable {

    private List<StateModel.Data> mDataset;
    private List<StateModel.Data> mDatasetFiltered;
    private Context context;
    private BaseActivity activity;

    public SelectStateListAdapter(Context context, List<StateModel.Data> objects) {
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
        StateModel.Data item = mDatasetFiltered.get(position);

        holder.binding.tvTitle.setText(item.getStateName(activity.getLanguage()));

        if (item.isSelected) {
            holder.binding.tvTitle.setBackground(ContextCompat.getDrawable(context, R.drawable.blue_button_bg));
            Typeface tf = Typeface.createFromAsset(context.getAssets(), Constants.SFTEXT_BOLD);
            holder.binding.tvTitle.setTypeface(tf);
            holder.binding.tvTitle.setTextColor(ContextCompat.getColor(context, R.color.white));
        } else {
            holder.binding.tvTitle.setBackgroundColor(Color.TRANSPARENT);
            holder.binding.tvTitle.setTextColor(ContextCompat.getColor(context, R.color.black));
            Typeface tf = Typeface.createFromAsset(context.getAssets(), Constants.SFTEXT_REGULAR);
            holder.binding.tvTitle.setTypeface(tf);
        }
    }

    private void clearSelected() {
        if (mDatasetFiltered != null && mDatasetFiltered.size() > 0) {
            for (StateModel.Data data : mDatasetFiltered) {
                data.isSelected = false;
            }
        }
    }

    public StateModel.Data getSelectedItem() {
        if (mDatasetFiltered != null && mDatasetFiltered.size() > 0) {
            for (StateModel.Data data : mDatasetFiltered) {
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

    public List<StateModel.Data> getData() {
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
                    List<StateModel.Data> filteredList = new ArrayList<>();
                    for (StateModel.Data row : mDataset) {
                        String rowText = row.getStateName(activity.getLanguage()).toLowerCase();
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
                mDatasetFiltered = (List<StateModel.Data>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
