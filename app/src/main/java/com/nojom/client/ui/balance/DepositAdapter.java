package com.nojom.client.ui.balance;

import android.graphics.Paint;
import android.view.LayoutInflater;
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

public class DepositAdapter extends RecyclerView.Adapter<DepositAdapter.SimpleViewHolder> {

    private BaseActivity context;
    private List<Deposit.Data> depositList;

    DepositAdapter(BaseActivity context) {
        this.context = context;
    }

    public void doRefresh(List<Deposit.Data> incomeList) {
        this.depositList = incomeList;
        notifyDataSetChanged();
    }


    void initList(List<Deposit.Data> projectsList) {
        this.depositList = projectsList;
        notifyDataSetChanged();
    }

    @NotNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemIncomeBalaneBinding incomeBalaneBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_income_balane, parent, false);
        return new SimpleViewHolder(incomeBalaneBinding);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {
        Deposit.Data item = depositList.get(position);
        holder.binding.tvJobId.setText(String.format(Locale.US,"%s", context.getString(R.string.deposit)));
        if (context.getLanguage().equals("ar")) {
            holder.binding.tvDate.setText(Utils.changeDateFormat("yyyy-MM-dd'T'HH:mm:ss", "dd MMM,yyyy hh:mm:ss a", item.timestamp));
        } else {
            holder.binding.tvDate.setText(Utils.changeDateFormat("yyyy-MM-dd'T'HH:mm:ss", "MMM dd,yyyy hh:mm:ss a", item.timestamp));
        }

        if (item.depositStatus != null) {

            switch (item.depositStatus) {
                case "0":
                    holder.binding.tvStatus.setText(context.getString(R.string.cancel));
                    holder.binding.tvStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.black_gray_border_5));
                    holder.binding.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.gray_text));
                    holder.binding.tvBalance.setTextColor(ContextCompat.getColor(context, R.color.gray_text));
                    holder.binding.tvBalance.setText(Utils.currencyFormat(item.amount));
                    holder.binding.tvBalance.setPaintFlags(holder.binding.tvBalance.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    break;
                case "1":
                    holder.binding.tvStatus.setText(context.getString(R.string.done));
                    holder.binding.tvStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.green_border_5));
                    holder.binding.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.greendark));
                    holder.binding.tvBalance.setTextColor(ContextCompat.getColor(context, R.color.greendark));
                    holder.binding.tvBalance.setText(String.format(Locale.US,"+%s", Utils.currencyFormat(item.amount)));
                    holder.binding.tvBalance.setPaintFlags(0);
                    break;
                case "2":
                    holder.binding.tvStatus.setText(context.getString(R.string.request_for_refund));
                    holder.binding.tvStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.orange_border_5));
                    holder.binding.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.orange_light));
                    holder.binding.tvBalance.setTextColor(ContextCompat.getColor(context, R.color.orange_light));
                    holder.binding.tvBalance.setText(String.format(Locale.US,"+%s", Utils.currencyFormat(item.amount)));
                    holder.binding.tvBalance.setPaintFlags(0);
                    break;
                case "3":
                    holder.binding.tvStatus.setText(context.getString(R.string.refund_declined));
                    holder.binding.tvStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.red_border_5));
                    holder.binding.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.reset_pw_btn));
                    holder.binding.tvBalance.setTextColor(ContextCompat.getColor(context, R.color.reset_pw_btn));
                    holder.binding.tvBalance.setText(String.format(Locale.US,"+%s", Utils.currencyFormat(item.amount)));
                    holder.binding.tvBalance.setPaintFlags(0);
                    break;
                case "4":
                    holder.binding.tvStatus.setText(context.getString(R.string.refund_approved));
                    holder.binding.tvStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.blue_border_5));
                    holder.binding.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                    holder.binding.tvBalance.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                    holder.binding.tvBalance.setText(String.format(Locale.US,"+%s", Utils.currencyFormat(item.amount)));
                    holder.binding.tvBalance.setPaintFlags(0);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return depositList != null ? depositList.size() : 0;
    }

    static class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemIncomeBalaneBinding binding;

        SimpleViewHolder(ItemIncomeBalaneBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            itemView.getRoot().setOnClickListener(v -> {
            });
        }
    }
}
