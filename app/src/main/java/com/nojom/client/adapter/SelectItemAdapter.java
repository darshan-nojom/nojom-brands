package com.nojom.client.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.databinding.ItemSelectFullBinding;
import com.nojom.client.model.Language;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SelectItemAdapter extends RecyclerView.Adapter<SelectItemAdapter.SimpleViewHolder>
        implements Filterable {

    private List<Language.Data> mDataset;
    private List<Language.Data> mDatasetFiltered;
    private Context context;
    private BaseActivity activity;
    private boolean isLanguage;

    public SelectItemAdapter(Context context, ArrayList<Language.Data> objects, boolean isLanguage) {
        this.mDataset = objects;
        this.mDatasetFiltered = objects;
        this.context = context;
        activity= (BaseActivity) context;
        this.isLanguage = isLanguage;
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
        Language.Data item = mDatasetFiltered.get(position);

        if (!isLanguage) {
            holder.binding.tvTitle.setText(item.level);
            holder.binding.tvTitle.setGravity(Gravity.CENTER);
        } else {
            holder.binding.tvTitle.setText(item.getServNameByLang(activity.getLanguage()));
        }

        if (item.isSelected) {
            holder.binding.tvTitle.setBackground(ContextCompat.getDrawable(context, R.drawable.black_button_bg));
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
            for (Language.Data data : mDatasetFiltered) {
                data.isSelected = false;
            }
        }
    }

    public Language.Data getSelectedItem() {
        if (mDatasetFiltered != null && mDatasetFiltered.size() > 0) {
            for (Language.Data data : mDatasetFiltered) {
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

    public List<Language.Data> getData() {
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
                    List<Language.Data> filteredList = new ArrayList<>();
                    for (Language.Data row : mDataset) {
                        String rowText = row.getServNameByLang(activity.getLanguage()).toLowerCase();
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
                mDatasetFiltered = (List<Language.Data>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
