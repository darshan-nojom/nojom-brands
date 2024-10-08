package com.nojom.client.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.databinding.ItemCardViewBinding;
import com.nojom.client.model.BraintreeCard;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.SimpleViewHolder> {
    private final Activity activity;
    private final List<BraintreeCard.Data> cardList;
    private OnClickCardListener onClickCardListener;

    public interface OnClickCardListener {
        void onClickCard(BraintreeCard.Data card);
    }

    public CardsAdapter(Activity activity, ArrayList<BraintreeCard.Data> cardList, OnClickCardListener listener) {
        this.activity = activity;
        this.cardList = cardList;
        onClickCardListener = listener;
    }

    @NotNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemCardViewBinding itemCardListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_card_view, parent, false);
        return new SimpleViewHolder(itemCardListBinding);
    }

    @Override
    public void onBindViewHolder(@NotNull final SimpleViewHolder holder, final int position) {
        BraintreeCard.Data data = cardList.get(position);

        if (data.localPrimary) {
            selectedBackground(holder.binding.relVisa, holder.binding.imgVisaCheck);
        } else {
            notSelectedBackground(holder.binding.relVisa, holder.binding.imgVisaCheck);
        }

        holder.binding.txtVisaCard.setText(String.format(Locale.US, "XXXX - %s", data.lastDigit));

    }

    @Override
    public int getItemCount() {
        return cardList != null ? cardList.size() : 0;
    }

    public List<BraintreeCard.Data> getData() {
        return cardList;
    }

    public void clearSelection() {
        for (BraintreeCard.Data card : cardList) {
            card.localPrimary = false;
        }
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemCardViewBinding binding;

        SimpleViewHolder(ItemCardViewBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.relVisa.setOnClickListener(v -> {
                /*Intent intent = new Intent(activity, AddCardActivity.class);
                if (paymentCardType.equalsIgnoreCase("Stripe")) {
                    intent.putExtra(Constants.CARD_ID, cardList.get(getAbsoluteAdapterPosition()).id);
                } else {
                    intent.putExtra(Constants.CARD_ID, cardList.get(getAbsoluteAdapterPosition()).token);
                }
                intent.putExtra(Constants.EDIT_CARD_KEY, Constants.EDIT_CARD_VAL);
                intent.putExtra("cardData", cardList.get(getAbsoluteAdapterPosition()));
                activity.startActivity(intent);*/
                clearSelection();
                cardList.get(getAbsoluteAdapterPosition()).localPrimary = true;

                if (onClickCardListener != null) {
                    onClickCardListener.onClickCard(cardList.get(getAbsoluteAdapterPosition()));
                }
                notifyDataSetChanged();
            });
        }
    }

    private void selectedBackground(RelativeLayout rel, AppCompatImageView imgCheck) {
        rel.setBackground(activity.getResources().getDrawable(R.drawable.lightgray_button_bg_12));
        imgCheck.setImageResource(R.drawable.circle_check);
    }

    private void notSelectedBackground(RelativeLayout rel, AppCompatImageView imgCheck) {
        rel.setBackground(activity.getResources().getDrawable(R.drawable.lightgray_border_12));
        imgCheck.setImageResource(R.drawable.circle_uncheck);
    }
}
