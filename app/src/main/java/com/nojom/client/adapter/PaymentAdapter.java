package com.nojom.client.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.databinding.ItemCategoryListBinding;
import com.nojom.client.model.Banks;
import com.nojom.client.ui.BaseActivity;

import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.SimpleViewHolder> {

    private List<Banks.Data> mDatasetFiltered;
    private BaseActivity context;
    private OnClickPaymentListener onClickLanguageListener;
    private String selectedLanguageList;

    public void setSelectedLanguageList(String selectedLanguageList) {
        this.selectedLanguageList = selectedLanguageList;
    }

    public interface OnClickPaymentListener {
        void onClickBank(Banks.Data name, int adapterPos);
    }

    public PaymentAdapter(BaseActivity context, List<Banks.Data> objects, OnClickPaymentListener listener) {
        this.mDatasetFiltered = objects;
        this.context = context;
        this.onClickLanguageListener = listener;
    }

    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        ItemCategoryListBinding fullBinding =
                ItemCategoryListBinding.inflate(layoutInflater, parent, false);
        return new SimpleViewHolder(fullBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final SimpleViewHolder holder, final int position) {
        try {
            Banks.Data item = mDatasetFiltered.get(position);

            holder.binding.tvCategory.setText(item.getName(context.getLanguage()));
            if (selectedLanguageList.contains(item.getName(context.getLanguage()))) {
//            if (item.isSelected) {
                holder.binding.imgCheck.setImageResource(R.drawable.check_done);
                holder.binding.imgCheck.clearColorFilter();
            } else {
                holder.binding.imgCheck.setImageResource(R.drawable.circle_uncheck);
                holder.binding.imgCheck.setColorFilter(ContextCompat.getColor(context,
                        R.color.full_dark_green), android.graphics.PorterDuff.Mode.MULTIPLY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getSelectedItem() {
        return selectedLanguageList;
    }

    @Override
    public int getItemCount() {
        return mDatasetFiltered != null ? mDatasetFiltered.size() : 0;
    }

    public List<Banks.Data> getData() {
        return mDatasetFiltered;
    }

    public class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemCategoryListBinding binding;

        public SimpleViewHolder(ItemCategoryListBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.imgCheck.setVisibility(View.VISIBLE);
            binding.ivNext.setVisibility(View.GONE);
            binding.rlView.setOnClickListener(v -> {
                try {

                    selectedLanguageList = mDatasetFiltered.get(getAdapterPosition()).getName(context.getLanguage());

//                    if (mDatasetFiltered.get(getAdapterPosition()).isSelected) {
//                        if (onClickLanguageListener != null) {
//                            onClickLanguageListener.onClickLanguage(true, mDatasetFiltered.get(getAdapterPosition()));
//                        }
//                    } else {
//                        if (onClickLanguageListener != null) {
//                            onClickLanguageListener.onClickLanguage(false, mDatasetFiltered.get(getAdapterPosition()));
//                        }
//                    }
                    // notifyItemChanged(getAdapterPosition());

                    if (onClickLanguageListener != null) {
                        onClickLanguageListener.onClickBank(mDatasetFiltered.get(getAdapterPosition()), getAdapterPosition());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
