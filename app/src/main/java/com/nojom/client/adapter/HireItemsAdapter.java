package com.nojom.client.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.databinding.HireGridItemBinding;
import com.nojom.client.ui.home.HomePagerModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HireItemsAdapter extends RecyclerView.Adapter<HireItemsAdapter.SimpleViewHolder> {

    private List<HomePagerModel> mDataset;
    private Context context;
    private OnItemClick onItemClick;

    public HireItemsAdapter(Context context, List<HomePagerModel> objects, OnItemClick onItemClick) {
        this.mDataset = objects;
        this.context = context;
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick {
        void onClickItem(HomePagerModel model);
    }

    @NotNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        HireGridItemBinding hireGridItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.hire_grid_item, parent, false);
        return new SimpleViewHolder(hireGridItemBinding);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {
        HomePagerModel item = mDataset.get(position);
        holder.binding.imgIcon.setImageResource(item.icon);
        holder.binding.tvTitle.setText(item.title);
    }

    @Override
    public int getItemCount() {
        return mDataset != null ? mDataset.size() : 0;
    }

    public List<HomePagerModel> getData() {
        return mDataset ;
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {

        HireGridItemBinding binding;

        SimpleViewHolder(HireGridItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            itemView.getRoot().setOnClickListener(v -> {
                if (onItemClick != null) {
                    onItemClick.onClickItem(mDataset.get(getAbsoluteAdapterPosition()));
                }
            });
        }
    }
}
