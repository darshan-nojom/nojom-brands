package com.nojom.client.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.ui.home.HomePagerModel;
import com.nojom.client.util.Constants;
import com.nojom.client.util.DividerView;
import com.nojom.client.util.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

public class HomeItemsAdapter extends RecyclerView.Adapter<HomeItemsAdapter.SimpleViewHolder> {

    private List<HomePagerModel> mDataset;
    private Context context;
    private String types;
    private OnItemClick onItemClick;

    public HomeItemsAdapter(Context context, List<HomePagerModel> objects, String types, OnItemClick onItemClick) {
        this.mDataset = objects;
        this.context = context;
        this.types = types;
        this.onItemClick = onItemClick;
    }

    @NotNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view;
        if (types.equalsIgnoreCase(Constants.HOW_IT_WORKS)) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_item, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_grid_item, parent, false);
        }
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {
        HomePagerModel item = mDataset.get(position);
        if (holder.imgIcon != null) {
            holder.imgIcon.setImageResource(item.icon);
        }
        if (holder.tvTitle != null) {
            holder.tvTitle.setText(item.title);
        }
        if (types.equalsIgnoreCase(Constants.HOW_IT_WORKS)) {
            switch (position) {
                case 0:
                    if (holder.tvCount != null) {
                        holder.tvCount.setTextColor(ContextCompat.getColor(context, R.color.pink));
                        holder.tvCount.setBackground(ContextCompat.getDrawable(context, R.drawable.dotted_round_pink));
                    }
                    if (holder.viewBottom != null) {
                        holder.viewBottom.setColor(ContextCompat.getColor(context, R.color.pink));
                        holder.viewBottom.setVisibility(View.VISIBLE);
                    }
                    if (holder.viewTop != null) {
                        holder.viewTop.setVisibility(View.GONE);
                    }

                    break;
                case 1:
                    if (holder.tvCount != null) {
                        holder.tvCount.setTextColor(ContextCompat.getColor(context, R.color.sky));
                        holder.tvCount.setBackground(ContextCompat.getDrawable(context, R.drawable.dotted_round_sky));
                    }
                    if (holder.viewBottom != null) {
                        holder.viewBottom.setColor(ContextCompat.getColor(context, R.color.sky));
                        holder.viewBottom.setVisibility(View.VISIBLE);
                    }
                    if (holder.viewTop != null) {
                        holder.viewTop.setColor(ContextCompat.getColor(context, R.color.sky));
                        holder.viewTop.setVisibility(View.VISIBLE);
                    }

                    break;
                case 2:
                    if (holder.tvCount != null) {
                        holder.tvCount.setTextColor(ContextCompat.getColor(context, R.color.light_blue));
                        holder.tvCount.setBackground(ContextCompat.getDrawable(context, R.drawable.dotted_round_blue));
                    }
                    if (holder.viewTop != null) {
                        holder.viewTop.setColor(ContextCompat.getColor(context, R.color.light_blue));
                        holder.viewTop.setVisibility(View.VISIBLE);
                    }
                    if (holder.viewBottom != null) {
                        holder.viewBottom.setVisibility(View.GONE);
                    }
                    break;
            }
            if (holder.tvCount != null) {
                holder.tvCount.setText(String.format(Locale.US,"%d", position + 1));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDataset != null ? mDataset.size() : 0;
    }

    public List<HomePagerModel> getData() {
        return mDataset;
    }

    public interface OnItemClick {
        void onClickItem(HomePagerModel model);
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {

        ImageView imgIcon;
        TextView tvTitle;
        DividerView viewTop;
        TextView tvCount;
        DividerView viewBottom;

        SimpleViewHolder(View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.img_icon);
            tvTitle = itemView.findViewById(R.id.tv_title);
            viewTop = itemView.findViewById(R.id.view_top);
            tvCount = itemView.findViewById(R.id.tv_count);
            viewBottom = itemView.findViewById(R.id.view_bottom);

            itemView.setOnClickListener(v -> {
                if (onItemClick != null) {
                    onItemClick.onClickItem(mDataset.get(getAbsoluteAdapterPosition()));
                } else {
                    Utils.toastMessage(context, mDataset.get(getAbsoluteAdapterPosition()).title);
                }
            });
        }
    }
}
