package com.nojom.client.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.databinding.ItemSkillsBinding;
import com.nojom.client.model.Skill;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ExpertiseAdapter extends RecyclerView.Adapter<ExpertiseAdapter.SimpleViewHolder> {

    private ArrayList<Skill> mDataset;
    private Context context;

    public ExpertiseAdapter(Context context, ArrayList<Skill> objects) {
        this.mDataset = objects;
        this.context = context;
    }

    @NotNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemSkillsBinding skillsBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_skills, parent, false);
        return new SimpleViewHolder(skillsBinding);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {
        Skill item = mDataset.get(position);
        holder.binding.tvLanguage.setText(item.skillTitle);
        holder.binding.tvLevel.setText(item.skillValue);
    }

    @Override
    public int getItemCount() {
        return mDataset != null ? mDataset.size() : 0;
    }

    public ArrayList<Skill> getData() {
        return mDataset;
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemSkillsBinding binding;

        SimpleViewHolder(ItemSkillsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
