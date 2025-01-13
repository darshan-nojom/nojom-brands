package com.nojom.client.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.databinding.ItemMyInvoiceBinding;
import com.nojom.client.model.BraintreeCard;
import com.nojom.client.model.Invoices;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class InvoicesAdapter extends RecyclerView.Adapter<InvoicesAdapter.SimpleViewHolder> {
    private final Activity activity;
    private final List<Invoices> cardList;
    private OnClickDownloadListener onClickDownloadListener;

    public interface OnClickDownloadListener {
        void invoiceDownload(Invoices card);
    }

    public InvoicesAdapter(Activity activity, ArrayList<Invoices> cardList, OnClickDownloadListener listener) {
        this.activity = activity;
        this.cardList = cardList;
        onClickDownloadListener = listener;
    }

    @NotNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemMyInvoiceBinding itemCardListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_my_invoice, parent, false);
        return new SimpleViewHolder(itemCardListBinding);
    }

    @Override
    public void onBindViewHolder(@NotNull final SimpleViewHolder holder, final int position) {
        Invoices data = cardList.get(position);
        holder.binding.tvCampId.setText("#" + data.campaignId);
        holder.binding.tvDate.setText(data.campaignCreatedAt.split("T")[0]);
        holder.binding.txtAmount.setText(data.totalPrice + " " + data.currency);
    }

    @Override
    public int getItemCount() {
        return cardList != null ? cardList.size() : 0;
    }

    public List<Invoices> getData() {
        return cardList;
    }


    class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemMyInvoiceBinding binding;

        SimpleViewHolder(ItemMyInvoiceBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.imgDownload.setOnClickListener(view -> {
                onClickDownloadListener.invoiceDownload(cardList.get(getAdapterPosition()));
            });
        }
    }
}
