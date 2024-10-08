package com.nojom.client.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.databinding.ItemInfReviewsBinding;
import com.nojom.client.databinding.ItemReviewsBinding;
import com.nojom.client.model.ClientReviews;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.SimpleViewHolder> {

    private List<ClientReviews.Data> mDataset;
    private BaseActivity context;

    public ReviewsAdapter(BaseActivity context) {
        this.context = context;
    }

    public void doRefresh(List<ClientReviews.Data> objects) {
        this.mDataset = objects;
        notifyDataSetChanged();
    }

    @NotNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemInfReviewsBinding itemReviewsBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_inf_reviews, parent, false);
        return new SimpleViewHolder(itemReviewsBinding);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {
        if (mDataset != null) {
            ClientReviews.Data item = mDataset.get(position);
            if (item != null) {
                holder.binding.tvProjectName.setText(item.title);
//                holder.binding.tvReview.setText(item.comment);
                String rate = Utils.numberFormat(String.valueOf(item.rate));
                try {
                    holder.binding.ratingbar.setRating(Float.parseFloat(rate));
                } catch (Exception e) {
                    holder.binding.ratingbar.setRating(0);
                }
                holder.binding.tvRating.setText(String.format(Locale.getDefault(), "(%s)", rate));
                if (context.getLanguage().equals("ar")) {
                    holder.binding.tvDate.setText(Utils.changeDateFormat("yyyy-MM-dd'T'hh:mm:ss", "dd MMM yyyy", item.timestamp));
                } else {
                    holder.binding.tvDate.setText(Utils.changeDateFormat("yyyy-MM-dd'T'hh:mm:ss", "MMM dd yyyy", item.timestamp));
                }

            }
        }
    }

    @Override
    public int getItemCount() {
        return mDataset != null ? mDataset.size() : 2;
    }

    public List<ClientReviews.Data> getData() {
        return mDataset;
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemInfReviewsBinding binding;

        SimpleViewHolder(ItemInfReviewsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
