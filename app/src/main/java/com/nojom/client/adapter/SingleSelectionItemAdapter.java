package com.nojom.client.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.databinding.ItemSelectLanguageBinding;
import com.nojom.client.model.Language;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class SingleSelectionItemAdapter extends RecyclerView.Adapter<SingleSelectionItemAdapter.SimpleViewHolder> {

    private List<Language.Data> mDataset;
    private Context context;
    private BaseActivity activity;
    private String selectedPosition;

    public SingleSelectionItemAdapter(Context context, ArrayList<Language.Data> objects, String selectedPosition) {
        this.mDataset = objects;
        this.context = context;
        activity= (BaseActivity) context;
        this.selectedPosition = selectedPosition;
    }

    public String getSelectedItem() {
        return selectedPosition;
    }

    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        ItemSelectLanguageBinding fullBinding =
                ItemSelectLanguageBinding.inflate(layoutInflater, parent, false);
        return new SimpleViewHolder(fullBinding);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {
        Language.Data item = mDataset.get(position);

        holder.binding.tvTitle.setText(item.getServNameByLang(activity.getLanguage()));

        if (selectedPosition.equals(item.level)) {
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

    @Override
    public int getItemCount() {
        return mDataset != null ? mDataset.size() : 0;
    }

    public List<Language.Data> getData() {
        return mDataset;
    }

    public class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemSelectLanguageBinding binding;

        public SimpleViewHolder(ItemSelectLanguageBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.rlView.setOnClickListener(v -> {
                selectedPosition = mDataset.get(getAbsoluteAdapterPosition()).level;
                notifyDataSetChanged();
            });
        }
    }
}
