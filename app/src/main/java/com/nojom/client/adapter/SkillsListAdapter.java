package com.nojom.client.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.databinding.ItemChipViewBinding;
import com.nojom.client.model.AgentProfile;
import com.nojom.client.model.ProfileSkills;
import com.nojom.client.ui.BaseActivity;

import java.util.List;

public class SkillsListAdapter extends RecyclerView.Adapter<SkillsListAdapter.SimpleViewHolder> {
    private final List<AgentProfile.TagList> arrSkillsList;
    private BaseActivity activity;
    private boolean isTag = false;

    public void setTag(boolean tag) {
        isTag = tag;
    }

    public SkillsListAdapter(BaseActivity activity, List<AgentProfile.TagList> arrSkillsList) {
        this.arrSkillsList = arrSkillsList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemChipViewBinding popularBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_chip_view, parent, false);
        return new SimpleViewHolder(popularBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleViewHolder holder, int position) {
        AgentProfile.TagList item = arrSkillsList.get(position);
        holder.binding.txtTagList.setText((isTag ? "#" : "") + item.getName(activity.getLanguage()));
    }

    @Override
    public int getItemCount() {
        return Math.min(arrSkillsList.size(), 8);
    }

    static class SimpleViewHolder extends RecyclerView.ViewHolder {
        ItemChipViewBinding binding;

        public SimpleViewHolder(ItemChipViewBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
