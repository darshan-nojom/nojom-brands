package com.nojom.client.ui.balance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ItemAccountBinding;
import com.nojom.client.model.BraintreeCard;
import com.nojom.client.ui.addcard.AddCardActivity;
import com.nojom.client.util.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.SimpleViewHolder> {

    private final Context context;
    private List<BraintreeCard.Data> paymentList;

    AccountsAdapter(Context context) {
        this.context = context;
    }

    public void doRefresh(List<BraintreeCard.Data> paymentList) {
        this.paymentList = paymentList;
        notifyDataSetChanged();
    }

    @NotNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemAccountBinding accountBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_account, parent, false);
        return new SimpleViewHolder(accountBinding);
    }

    @Override
    public void onBindViewHolder(@NotNull final SimpleViewHolder holder, final int position) {
        BraintreeCard.Data item = paymentList.get(position);
        if (item != null && item.lastDigit != null) {
            holder.binding.tvEmail.setText(String.format(Locale.US, "**** **** **** %s", item.lastDigit));
            holder.binding.tvPrimary.setVisibility(
                    item.isPrimary == 0 ? View.GONE : View.VISIBLE
            );
        } else if (item != null && item.paypal != null) {
            holder.binding.tvEmail.setText(item.paypal.account);
            holder.binding.tvPrimary.setVisibility(
                    item.paypal.isPrimary.equals("0") || TextUtils.isEmpty(item.paypal.isPrimary) ? View.GONE : View.VISIBLE
            );
        }

        if (item != null && item.paypal != null && item.paypal.provider != null) {
            holder.binding.tvAccount.setText(item.paypal.provider);
            holder.binding.tvAccount.setVisibility(View.VISIBLE);
        } else {
            holder.binding.tvAccount.setVisibility(View.GONE);
        }

        if (item != null && item.lastDigit != null) {
            holder.binding.tvDate.setVisibility(View.VISIBLE);
            String[] expDate = item.expDate.split("/");
            holder.binding.tvDate.setText(String.format(Locale.US, context.getString(R.string.expire) + " %s/%s | %s", expDate[0], expDate[1], item.billingAddress.cardholderName));
        } else {
            holder.binding.tvDate.setVisibility(View.GONE);
        }

        if (item != null && item.paypal != null && item.paypal.verified != null) {
            holder.binding.tvStatus.setVisibility(View.VISIBLE);
            if (item.paypal.verified.equals("0")) {
                holder.binding.tvStatus.setText(context.getString(R.string.not_verified));
                holder.binding.tvStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.red_border_5));
                holder.binding.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.red_dark));
            } else {
                holder.binding.tvStatus.setText(context.getString(R.string.verified));
                holder.binding.tvStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.green_border_5));
                holder.binding.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.greendark));
            }
        } else {
            holder.binding.tvStatus.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return paymentList != null ? paymentList.size() : 0;
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemAccountBinding binding;

        SimpleViewHolder(ItemAccountBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            itemView.getRoot().setOnClickListener(v -> {
                if (paymentList.get(getAbsoluteAdapterPosition()).token != null) {
                    Intent intent = new Intent(context, AddCardActivity.class);
                    if (Task24Application.getInstance().paymentCardType.equalsIgnoreCase("Stripe")) {
                        intent.putExtra(Constants.CARD_ID, paymentList.get(getAbsoluteAdapterPosition()).id);
                    } else {
                        intent.putExtra(Constants.CARD_ID, paymentList.get(getAbsoluteAdapterPosition()).token);
                    }
                    intent.putExtra(Constants.EDIT_CARD_KEY, Constants.EDIT_CARD_VAL);
                    intent.putExtra("cardData", paymentList.get(getAbsoluteAdapterPosition()));
                    ((Activity) context).startActivityForResult(intent, 111);
                } else {
                    Intent i = new Intent(context, EditPaypalActivity.class);
                    i.putExtra(Constants.ACCOUNT_DATA, paymentList.get(getAbsoluteAdapterPosition()));
                    context.startActivity(i);
                }
            });
        }
    }
}
