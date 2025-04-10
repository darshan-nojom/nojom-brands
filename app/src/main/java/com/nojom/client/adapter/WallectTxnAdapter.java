package com.nojom.client.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.databinding.ItemWalletBinding;
import com.nojom.client.model.WalletData;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Utils;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WallectTxnAdapter extends RecyclerView.Adapter<WallectTxnAdapter.ViewHolder> {

    private List<WalletData> timelineItems;
    private BaseActivity activity;
    private final PrettyTime p = new PrettyTime();
    private OnClickStarListener onClickStarListener;

    public interface OnClickStarListener {
        void onClickStar(int pos, WalletData WalletData);

        void onClickChat(int pos, WalletData profile);
    }

    public WallectTxnAdapter(BaseActivity activity, List<WalletData> timelineItems) {
        this.timelineItems = timelineItems;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemWalletBinding itemCardListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_wallet, parent, false);
        return new ViewHolder(itemCardListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WalletData item = timelineItems.get(position);

//        Glide.with(activity).load(item.profile_picture).error(R.color.orange).into(holder.binding.imgProfile);
        holder.binding.tvFileName.setText("" + item.description);
//        holder.binding.tvStatus.setText(item.campaign_status);
        boolean isCredit = true;
        if (item.direction != null) {
            if (item.direction.equals("debit")) {
                isCredit = false;
            }
        }
        holder.binding.tvAmount.setText((isCredit ? "+ " : "- ") + Utils.decimalFormat(String.valueOf(item.amount)) + " " + activity.getString(R.string.sar));

//        if (item.campaign_status != null) {
//            holder.binding.tvStatus.setVisibility(View.VISIBLE);
//            if (item.campaign_status.equals("pending")) {
//                holder.binding.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.yellow_bg_20));
//                holder.binding.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.black));
//            } else if (item.campaign_status.equals("approved")) {
//                holder.binding.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.green_button_bg_20));
//                holder.binding.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.white));
//            } else {
//                holder.binding.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.red_bg_20));
//                holder.binding.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.white));
//            }
//        } else {
//            holder.binding.tvStatus.setVisibility(View.INVISIBLE);
//        }

        if (item.transaction_type != null) {
            switch (item.transaction_type) {
                case "RELEASE" -> {
                    holder.binding.img.setImageResource(R.drawable.ic_wallet_approved);
                    holder.binding.tvStatus.setText(activity.getString(R.string.approved));
                    holder.binding.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.blue_light_bg_40));
                    holder.binding.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                    holder.binding.tvAmount.setTextColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                }
                case "DEPOSIT" -> {
                    holder.binding.img.setImageResource(R.drawable.ic_wallet_add);
                    holder.binding.tvStatus.setText(activity.getString(R.string.add_money));
                    holder.binding.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.green_light_bg_40));
                    holder.binding.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.C_075E45));
                    holder.binding.tvAmount.setTextColor(ContextCompat.getColor(activity, R.color.C_34C759));
                }
                case "HOLD" -> {
                    holder.binding.img.setImageResource(R.drawable.ic_wallet_pending);
                    holder.binding.tvStatus.setText(activity.getString(R.string.pending));
                    holder.binding.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.orange_light_bg_40));
                    holder.binding.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.C_FF9500));
                    holder.binding.tvAmount.setTextColor(ContextCompat.getColor(activity, R.color.C_FF9500));
                }
                case "CANCEL" -> {
                    holder.binding.img.setImageResource(R.drawable.ic_wallet_cancel);
                    holder.binding.tvStatus.setText(activity.getString(R.string.reject));
                    holder.binding.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.red_light_bg_40));
                    holder.binding.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.C_FF3B30));
                    holder.binding.tvAmount.setTextColor(ContextCompat.getColor(activity, R.color.C_FF3B30));
                }
            }
        }

        Date date1 = Utils.changeDateFormat("yyyy-MM-dd'T'hh:mm:ss", item.updated_at);
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dfFinal2;
        if (activity.getLanguage().equals("ar")) {
            dfFinal2 = new SimpleDateFormat("dd MMM,yyyy");
        } else {
            dfFinal2 = new SimpleDateFormat("MMM dd,yyyy");
        }


        if (date1 != null) {
            if (activity.printDifference(date1, date).equalsIgnoreCase("0")) {
                String result = p.format(Utils.changeDateFormat("yyyy-MM-dd'T'hh:mm:ss", item.updated_at));
                holder.binding.tvDate.setText("" + result);
            } else {
                String finalDate = dfFinal2.format(date1);
                holder.binding.tvDate.setText("" + finalDate);
            }
        }
    }

    @Override
    public int getItemCount() {
        return timelineItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemWalletBinding binding;

        public ViewHolder(@NonNull ItemWalletBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

        }
    }
}
