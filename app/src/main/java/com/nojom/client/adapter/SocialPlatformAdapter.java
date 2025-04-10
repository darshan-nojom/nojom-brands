package com.nojom.client.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nojom.client.R;
import com.nojom.client.databinding.ItemServicePlatformBinding;
import com.nojom.client.model.AgentServices;
import com.nojom.client.ui.BaseActivity;

import java.util.List;

public class SocialPlatformAdapter extends RecyclerView.Adapter<SocialPlatformAdapter.SimpleViewHolder> {
    private final BaseActivity activity;
    LayoutInflater layoutInflater;
    private List<AgentServices> arrGigList;

    public SocialPlatformAdapter(BaseActivity activity, List<AgentServices> arrGigList) {
        this.activity = activity;
        this.arrGigList = arrGigList;
        layoutInflater = activity.getLayoutInflater();
    }

    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemServicePlatformBinding popularBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_service_platform, parent, false);
        return new SimpleViewHolder(popularBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleViewHolder holder, int position) {
        AgentServices expertGig = arrGigList.get(position);
        if (expertGig.followers != null) {
            String formattedNumber = activity.formatNumber(expertGig.followers);
            holder.binding.tvName.setText(formattedNumber);
        } else {
            holder.binding.tvName.setText("");
        }
        holder.binding.txtPrice.setText(activity.format(expertGig.price) + " " + activity.getString(R.string.sar));
        Glide.with(activity)
                .load(expertGig.filename)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.binding.imgGig);

    }

    public List<AgentServices> getData() {
        return arrGigList;
    }

    public void doRefresh(List<AgentServices> data) {
        arrGigList = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        /*if (arrGigList != null && arrGigList.size() > 0) {
            Collections.sort(arrGigList, (o1, o2) -> {
                // Sort in descending order
                return Integer.compare(o2.followers, o1.followers);
            });
            return Math.min(arrGigList.size(), 3);
        } else {
            return 0;
        }*/
        return arrGigList.size();
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {
        ItemServicePlatformBinding binding;

        public SimpleViewHolder(ItemServicePlatformBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
