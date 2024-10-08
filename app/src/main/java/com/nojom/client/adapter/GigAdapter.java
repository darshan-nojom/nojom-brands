package com.nojom.client.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.databinding.ItemGigDetailsBinding;
import com.nojom.client.model.Requirement;
import com.nojom.client.ui.BaseActivity;

import java.util.List;

public class GigAdapter extends RecyclerView.Adapter<GigAdapter.SimpleViewHolder> {
    LayoutInflater layoutInflater;
    private BaseActivity activity;
    private List<Requirement> arrRequirementList;
    private boolean isFromProjectDetail = false;

    public GigAdapter(BaseActivity context, List<Requirement> arrRequirementList, boolean isFromProjectDetail) {
        this.activity = context;
        this.arrRequirementList = arrRequirementList;
        this.isFromProjectDetail = isFromProjectDetail;
        layoutInflater = activity.getLayoutInflater();
    }

    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemGigDetailsBinding popularBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_gig_details, parent, false);
        return new SimpleViewHolder(popularBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleViewHolder holder, int position) {
        Requirement item = arrRequirementList.get(position);

        if (isFromProjectDetail) {
            holder.binding.tvGigName.setVisibility(View.GONE);
            holder.binding.tvGigNameProject.setVisibility(View.VISIBLE);
            holder.binding.tvGigNameProject.setText(item.name);
        } else {
            holder.binding.tvGigNameProject.setVisibility(View.GONE);
            holder.binding.tvGigName.setVisibility(View.VISIBLE);
            holder.binding.tvGigName.setText(item.name);
        }
    }

    @Override
    public int getItemCount() {
        return arrRequirementList.size();
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {
        ItemGigDetailsBinding binding;

        public SimpleViewHolder(ItemGigDetailsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
