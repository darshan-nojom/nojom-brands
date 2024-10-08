package com.nojom.client.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.SimpleViewHolder> {

    private ArrayList<?> mDataset;
    private int layoutId;
    private OnViewBindListner onViewBindListner;

    public RecyclerviewAdapter(ArrayList<?> objects, int layoutId, OnViewBindListner onViewBindListner) {
        this.mDataset = objects;
        this.layoutId = layoutId;
        this.onViewBindListner = onViewBindListner;
    }

    public void doRefresh(ArrayList<?> objects) {
        this.mDataset = objects;
        notifyDataSetChanged();
    }

    public interface OnViewBindListner {
        void bindView(View view, int position);
    }

    @NotNull
    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull final SimpleViewHolder viewHolder, final int position) {
        if (onViewBindListner != null) {
            onViewBindListner.bindView(viewHolder.itemView, position);
        }
    }

    @Override
    public int getItemCount() {
        return mDataset != null ? mDataset.size() : 0;
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {

        public SimpleViewHolder(View itemView) {
            super(itemView);
        }
    }
}
