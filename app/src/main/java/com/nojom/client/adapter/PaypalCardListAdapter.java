package com.nojom.client.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.databinding.ItemCardListBinding;
import com.nojom.client.model.BraintreeCard;
import com.nojom.client.model.Cardlist;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.addcard.CardListActivity;
import com.nojom.client.ui.balance.EditPaypalActivity;
import com.nojom.client.util.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PaypalCardListAdapter extends RecyclerView.Adapter<PaypalCardListAdapter.SimpleViewHolder> {
    private Context context;
    private Activity activity;
    private List<BraintreeCard.Data> cardList;

    public PaypalCardListAdapter(Context context, ArrayList<BraintreeCard.Data> cardList) {
        this.context = context;
        activity = (BaseActivity) context;
        this.cardList = cardList;
    }

    @NotNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemCardListBinding itemCardListBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_card_list, parent, false);
        return new SimpleViewHolder(itemCardListBinding);
    }

    @Override
    public void onBindViewHolder(@NotNull final SimpleViewHolder holder, final int position) {
        Cardlist.Paypal data = cardList.get(position).paypal;

        holder.binding.rbSelection.setChecked(cardList.get(position).localPrimary);

        holder.binding.tvCardNumber.setText(data.account);
        holder.binding.tvExpiry.setText(data.provider);

        holder.binding.rbSelection.setOnCheckedChangeListener((buttonView, isChecked) -> ((CardListActivity) activity).updatePaypalList(position, 1));
    }

    @Override
    public int getItemCount() {
        return cardList != null ? cardList.size() : 0;
    }

    public List<BraintreeCard.Data> getData() {
        return cardList;
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemCardListBinding binding;

        SimpleViewHolder(ItemCardListBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.rlCardItem.setOnClickListener(v -> {
                Intent i = new Intent(activity, EditPaypalActivity.class);
                i.putExtra(Constants.ACCOUNT_DATA, cardList.get(getAbsoluteAdapterPosition()));
                context.startActivity(i);
            });
        }
    }

}
