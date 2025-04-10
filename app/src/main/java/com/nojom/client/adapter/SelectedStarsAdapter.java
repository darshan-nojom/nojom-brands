package com.nojom.client.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nojom.client.R;
import com.nojom.client.databinding.ItemSelectedStarsBinding;
import com.nojom.client.model.AgentService;
import com.nojom.client.model.Agents;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class SelectedStarsAdapter extends RecyclerView.Adapter<SelectedStarsAdapter.ViewHolder> {

    private BaseActivity context;
    private List<Agents> socialList;
    private StarDeletedListener starClickListener;

    public void removeData(int pos) {
        socialList.remove(pos);
        notifyDataSetChanged();
    }

    public List<Agents> getData() {
        return socialList;
    }


    public double calculatePrice() {
        double finalPrice = 0;
        for (Agents data : socialList) {
            finalPrice = finalPrice + data.getPrice(data.services);
        }
        if (finalPrice == 0) {
            return 0;
        }
//        List<WalletData> ratesData = Preferences.getRates(context);
//        double agencyFeeRate = 0;
//        double servTaxFeeRate = 0;
//        for (WalletData data : ratesData) {
//            if (Objects.equals(data.rate_type, "tax") && data.is_active == 1) {
//                servTaxFeeRate = data.rate_value * 100;
//            } else if (Objects.equals(data.rate_type, "agency_fee") && data.is_active == 1) {
//                agencyFeeRate = data.rate_value * 100;
//            }
//        }
//        return finalPrice + ((finalPrice * agencyFeeRate) / 100);//5% service tax
        return finalPrice;
    }

    public interface StarDeletedListener {
        void onClickDeleteStar(int pos, Agents agents);
    }

    public SelectedStarsAdapter(BaseActivity context, List<Agents> socialList, StarDeletedListener starClickListener) {
        this.context = context;
        this.socialList = socialList;
        this.starClickListener = starClickListener;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemSelectedStarsBinding socialBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_selected_stars, parent, false);
        return new ViewHolder(socialBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Agents agents = socialList.get(position);
        holder.binding.tvStar.setText(context.getString(R.string.influencer_) + " #" + (position + 1));
        Glide.with(context).load(agents.image.path + agents.image.fileName).error(R.drawable.dp).into(holder.binding.imgGig);
        holder.binding.tvName.setText(agents.firstName + " " + agents.lastName);
        double price = agents.getPrice(agents.services);
        if (price > 0) {
            String formattedNumber = Utils.getDecimalFormat(Utils.formatValue(price));
            holder.binding.tvPrice.setText(formattedNumber + " ");
        }

        if (agents.services != null && agents.services.size() > 0) {
            shiftSocialIdMinusOneToEnd(agents.services);
            List<AgentService> checkedList = getCheckedItems(agents.services);
            SelectedStarsPlatformAdapter adapter = new SelectedStarsPlatformAdapter(context, checkedList, null);
            holder.binding.rvPlatform.setAdapter(adapter);
        }
    }

    public List<AgentService> getCheckedItems(List<AgentService> list) {
        List<AgentService> filteredList = new ArrayList<>();
        for (AgentService agentService : list) {
            if (agentService.isChecked) { // Check if isChecked is true
                filteredList.add(agentService);
            }
        }
        return filteredList;
    }

    public void shiftSocialIdMinusOneToEnd(List<AgentService> list) {
        AgentService target = null;

        // Find and remove the item with socialId == -1
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).socialPlatformId == -1) {
                target = list.remove(i);
                break;
            }
        }

        // Add the item to the end of the list
        if (target != null) {
            list.add(target);
        }
    }

    @Override
    public int getItemCount() {
        return socialList.size();
    }

    public void addData(List<Agents> agentList) {
        socialList = agentList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemSelectedStarsBinding binding;

        public ViewHolder(ItemSelectedStarsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.imgDelete.setOnClickListener(view -> {
                if (starClickListener != null) {
                    starClickListener.onClickDeleteStar(getAdapterPosition(), socialList.get(getAdapterPosition()));
                }
            });

        }
    }
}