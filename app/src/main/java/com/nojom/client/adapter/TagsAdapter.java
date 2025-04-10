package com.nojom.client.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.databinding.ItemTagViewBinding;
import com.nojom.client.model.AgentCategory;
import com.nojom.client.ui.BaseActivity;

import java.util.List;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.SimpleViewHolder> {
    private final BaseActivity activity;
    LayoutInflater layoutInflater;
    private List<AgentCategory> arrGigList;

    public TagsAdapter(BaseActivity activity, List<AgentCategory> arrGigList) {
        this.activity = activity;
        this.arrGigList = arrGigList;
        layoutInflater = activity.getLayoutInflater();
    }

    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTagViewBinding popularBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_tag_view, parent, false);
        return new SimpleViewHolder(popularBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleViewHolder holder, int position) {
        AgentCategory expertGig = arrGigList.get(position);

        if (position > 3) {
            int size = arrGigList.size() - 4;
            holder.binding.txtTagList.setText("+" + size);
        } else {
            holder.binding.txtTagList.setText(expertGig.getCategory(activity.getLanguage()));
        }
    }

    public List<AgentCategory> getData() {
        return arrGigList;
    }

    public void doRefresh(List<AgentCategory> data) {
        arrGigList = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return Math.min(arrGigList.size(), 5);
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {
        ItemTagViewBinding binding;

        public SimpleViewHolder(ItemTagViewBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
