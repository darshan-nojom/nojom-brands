package com.nojom.client.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.databinding.ItemCustomPriceBinding;
import com.nojom.client.model.ExpertGigDetail;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Utils;

import java.util.List;

public class CustomDeadlineAdapter extends RecyclerView.Adapter<CustomDeadlineAdapter.SimpleViewHolder> {
    LayoutInflater layoutInflater;
    private final BaseActivity activity;
    private final List<ExpertGigDetail.Deadline> arrRequirementList;

    public CustomDeadlineAdapter(BaseActivity context, List<ExpertGigDetail.Deadline> arrRequirementList) {
        this.activity = context;
        this.arrRequirementList = arrRequirementList;
        layoutInflater = activity.getLayoutInflater();
    }

    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCustomPriceBinding popularBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_custom_price, parent, false);
        return new SimpleViewHolder(popularBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleViewHolder holder, int position) {
        ExpertGigDetail.Deadline item = arrRequirementList.get(position);

        holder.binding.txName.setText(item.value + (item.type == 1 ? " " + activity.getString(R.string.hours) : " " + activity.getString(R.string.days)));
        holder.binding.txtPrice.setText(activity.getCurrency().equals("SAR") ? Utils.decimalFormat(String.valueOf(item.price)) + " "+activity.getString(R.string.sar)
                : "$" + Utils.decimalFormat(String.valueOf(item.price)));
    }

    @Override
    public int getItemCount() {
        return arrRequirementList.size();
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {
        ItemCustomPriceBinding binding;

        public SimpleViewHolder(ItemCustomPriceBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
