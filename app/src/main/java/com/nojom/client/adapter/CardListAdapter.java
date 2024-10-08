package com.nojom.client.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.databinding.ItemCardListBinding;
import com.nojom.client.model.BraintreeCard;
import com.nojom.client.ui.addcard.AddCardActivity;
import com.nojom.client.ui.addcard.CardListActivity;
import com.nojom.client.util.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.SimpleViewHolder> {
    private final Activity activity;
    private final List<BraintreeCard.Data> cardList;
    private String paymentCardType = "";

    public CardListAdapter(Activity activity, ArrayList<BraintreeCard.Data> cardList, String paymentCardType) {
        this.activity = activity;
        this.cardList = cardList;
        this.paymentCardType = paymentCardType;
    }

    @NotNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemCardListBinding itemCardListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_card_list, parent, false);
        return new SimpleViewHolder(itemCardListBinding);
    }

    @Override
    public void onBindViewHolder(@NotNull final SimpleViewHolder holder, final int position) {
        BraintreeCard.Data data = cardList.get(position);

        holder.binding.rbSelection.setChecked(data.localPrimary);

        holder.binding.tvCardNumber.setText(String.format(Locale.US,"**** **** **** %s", data.lastDigit));

        String[] expDate = data.expDate.split("/");
        holder.binding.tvExpiry.setText(String.format(Locale.US,activity.getString(R.string.expire) + " %s | %s", expDate[0] + "/" + expDate[1], data.billingAddress.cardholderName));

        holder.binding.rbSelection.setOnCheckedChangeListener((buttonView, isChecked) -> ((CardListActivity) activity).updateList(position, 1));
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
                Intent intent = new Intent(activity, AddCardActivity.class);
                if (paymentCardType.equalsIgnoreCase("Stripe")) {
                    intent.putExtra(Constants.CARD_ID, cardList.get(getAbsoluteAdapterPosition()).id);
                } else {
                    intent.putExtra(Constants.CARD_ID, cardList.get(getAbsoluteAdapterPosition()).token);
                }
                intent.putExtra(Constants.EDIT_CARD_KEY, Constants.EDIT_CARD_VAL);
                intent.putExtra("cardData", cardList.get(getAbsoluteAdapterPosition()));
                activity.startActivity(intent);
            });
        }
    }
}
