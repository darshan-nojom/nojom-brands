package com.nojom.client.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.databinding.ItemProjectsListBinding;
import com.nojom.client.databinding.LayImageBinding;
import com.nojom.client.databinding.LayTextBinding;

import java.util.List;

public class AdapterClass extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> itemClassList;

    // public constructor for this class
    public AdapterClass(List<String> itemClassList) {
        this.itemClassList = itemClassList;
    }

    // Override the getItemViewType method
    @Override
    public int getItemViewType(int position) {
        return (position == position - 1) ? 2 : 1;
    }

    // Create classes for each layout ViewHolder
    static class LayoutImageVH extends RecyclerView.ViewHolder {
        private LayImageBinding layImageBinding;

        public LayoutImageVH(@NonNull LayImageBinding itemView) {
            super(itemView.getRoot());
            layImageBinding = itemView;
        }

        private void setView(String text) {

        }
    }

    static class LayoutTextVH extends RecyclerView.ViewHolder {
        private LayTextBinding layTextBinding;

        public LayoutTextVH(@NonNull LayTextBinding itemView) {
            super(itemView.getRoot());
            layTextBinding = itemView;
        }

        private void setViews(int image, String textOne, String textTwo) {


        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            LayImageBinding itemProjectsListBinding =
                    DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                            R.layout.lay_image, parent, false);
            return new LayoutImageVH(itemProjectsListBinding);
        } else if (viewType == 2) {
            LayTextBinding itemProjectsListBinding =
                    DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                            R.layout.lay_text, parent, false);
            return new LayoutTextVH(itemProjectsListBinding);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LayoutImageVH) {
//            String text = itemClassList.get(position).getText();
            if (position > 0) {
                ((LayoutImageVH) holder).layImageBinding.relMain.setPaddingRelative(-45, 0, 0, 0);
            }
        } else if (holder instanceof LayoutTextVH) {
//            int image = itemClassList.get(position).getImageResource();
//            String textOne = itemClassList.get(position).getText();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
