package com.nojom.client.ui.balance;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.databinding.ItemIncomeBalaneBinding;
import com.nojom.client.model.Deposit;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.SimpleViewHolder> {

    private final Context context;
    private List<Deposit.Data> historyList;
    private final BaseActivity activity;
    private final OnCancelWithdrawals onCancelWithdrawals;

    HistoryAdapter(Context context, OnCancelWithdrawals onCancelWithdrawals) {
        this.context = context;
        activity = (BaseActivity) context;
        this.onCancelWithdrawals = onCancelWithdrawals;
    }

    public void doRefresh(List<Deposit.Data> withdrawList) {
        this.historyList = withdrawList;
        notifyDataSetChanged();
    }

    void initList(List<Deposit.Data> projectsList) {
        this.historyList = projectsList;
        notifyDataSetChanged();
    }

    @NotNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemIncomeBalaneBinding itemCardListBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_income_balane, parent, false);
        return new SimpleViewHolder(itemCardListBinding);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {
        Deposit.Data item = historyList.get(position);
        holder.binding.tvJobId.setText(String.format(Locale.US, activity.getString(R.string.paid_for_job_id) + " %d", item.jobPostId));
        if (activity.getLanguage().equals("ar")) {
            holder.binding.tvDate.setText(Utils.changeDateFormat("yyyy-MM-dd'T'HH:mm:ss", "dd MMM,yyyy hh:mm:ss a", item.datePaid));
        } else {
            holder.binding.tvDate.setText(Utils.changeDateFormat("yyyy-MM-dd'T'HH:mm:ss", "MMM dd,yyyy hh:mm:ss a", item.datePaid));
        }


        switch (item.incomeStatus) {
            case 0:
                holder.binding.tvStatus.setText(context.getString(R.string.pending));
                holder.binding.tvStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.blue_border_5));
                holder.binding.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                holder.binding.tvBalance.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                holder.binding.tvBalance.setText(String.format(Locale.US, "+%s", Utils.currencyFormat(item.redeemAmount == 0.0 ? item.amount : item.redeemAmount)));
                holder.binding.tvBalance.setPaintFlags(0);
                break;
            case 1:
                holder.binding.tvStatus.setText(context.getString(R.string.completed));
                holder.binding.tvStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.green_border_5));
                holder.binding.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.greendark));
                holder.binding.tvBalance.setTextColor(ContextCompat.getColor(context, R.color.greendark));
                holder.binding.tvBalance.setText(String.format(Locale.US, "+%s", Utils.currencyFormat(item.redeemAmount == 0.0 ? item.amount : item.redeemAmount)));
                holder.binding.tvBalance.setPaintFlags(0);
                break;
            case 2:
                holder.binding.tvStatus.setText(context.getString(R.string.cancel));
                holder.binding.tvStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.black_gray_border_5));
                holder.binding.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.gray_text));
                holder.binding.tvBalance.setTextColor(ContextCompat.getColor(context, R.color.gray_text));
                holder.binding.tvBalance.setText(Utils.currencyFormat(item.redeemAmount == 0.0 ? item.amount : item.redeemAmount));
                holder.binding.tvBalance.setPaintFlags(holder.binding.tvBalance.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                break;
            case 3:
                holder.binding.tvStatus.setText(context.getString(R.string.refunded));
                holder.binding.tvStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.red_border_5));
                holder.binding.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.red_dark));
                holder.binding.tvBalance.setTextColor(ContextCompat.getColor(context, R.color.red_dark));
                holder.binding.tvBalance.setText(String.format(Locale.US, "-%s", Utils.currencyFormat(item.redeemAmount == 0.0 ? item.amount : item.redeemAmount)));
                holder.binding.tvBalance.setPaintFlags(0);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return historyList != null ? historyList.size() : 0;
    }

    public interface OnCancelWithdrawals {
        void cancelWithdrawals();
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemIncomeBalaneBinding binding;

        SimpleViewHolder(ItemIncomeBalaneBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.tvJob.setVisibility(View.VISIBLE);
        }
    }
}
