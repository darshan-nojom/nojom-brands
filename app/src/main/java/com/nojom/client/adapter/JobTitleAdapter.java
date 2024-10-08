package com.nojom.client.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.databinding.ItemJobTitleBinding;
import com.nojom.client.model.ServicesModel;
import com.nojom.client.ui.BaseActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class JobTitleAdapter extends RecyclerView.Adapter<JobTitleAdapter.SimpleViewHolder> {
    private BaseActivity activity;
    private List<ServicesModel.JobTitle> cardList;
    private OnClickTitle onClickTitleListener;

    public JobTitleAdapter(BaseActivity activity, List<ServicesModel.JobTitle> cardList, OnClickTitle onClickTitleListener) {
        this.activity = activity;
        this.cardList = cardList;
        this.onClickTitleListener = onClickTitleListener;
    }

    @NotNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemJobTitleBinding itemCardListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_job_title, parent, false);
        return new SimpleViewHolder(itemCardListBinding);
    }

    @Override
    public void onBindViewHolder(@NotNull final SimpleViewHolder holder, final int position) {
        ServicesModel.JobTitle data = cardList.get(position);

        holder.binding.tvTitle.setText(" - " + data.getTitle(activity.getLanguage()));
    }

    @Override
    public int getItemCount() {
        return cardList != null ? cardList.size() : 0;
    }

    public List<ServicesModel.JobTitle> getData() {
        return cardList;
    }

    public interface OnClickTitle {
        void onClickTitle(ServicesModel.JobTitle title);
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemJobTitleBinding binding;

        SimpleViewHolder(ItemJobTitleBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.getRoot().setOnClickListener(v -> {
                if (onClickTitleListener != null) {
                    onClickTitleListener.onClickTitle(cardList.get(getAbsoluteAdapterPosition()));
                }
            });
        }
    }
}
