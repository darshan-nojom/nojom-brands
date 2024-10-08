package com.nojom.client.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.databinding.ItemGigExtrasBinding;
import com.nojom.client.ui.BaseActivity;

public class GigExtrasAdapter extends RecyclerView.Adapter<GigExtrasAdapter.SimpleViewHolder> {
    LayoutInflater layoutInflater;
    private BaseActivity activity;

    public GigExtrasAdapter(BaseActivity context) {
        activity = context;
        layoutInflater = activity.getLayoutInflater();
    }

    @NonNull
    @Override
    public GigExtrasAdapter.SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemGigExtrasBinding itemGigExtrasBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_gig_extras, parent, false);
        return new SimpleViewHolder(itemGigExtrasBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull GigExtrasAdapter.SimpleViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }


    class SimpleViewHolder extends RecyclerView.ViewHolder {
        ItemGigExtrasBinding binding;

        public SimpleViewHolder(ItemGigExtrasBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
