package com.nojom.client.adapter;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nojom.client.R;
import com.nojom.client.databinding.ItemStarsBinding;
import com.nojom.client.model.AgentCategory;
import com.nojom.client.model.AgentService;
import com.nojom.client.model.Agents;
import com.nojom.client.model.Serv;
import com.nojom.client.ui.BaseActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class StarsAdapter extends RecyclerView.Adapter<StarsAdapter.ViewHolder> implements StarsPlatformAdapter.PlatformClickListener {

    private BaseActivity context;
    private List<Agents> socialList;
    private StarClickListener starClickListener;

    public List<Agents> getData() {
        return socialList;
    }

    @Override
    public void onClickService(int pos, AgentService serv) {

    }

    @Override
    public void onClickServiceChecked() {
//        if (adapter != null) {
//            String formattedNumber = Utils.getDecimalFormat(Utils.formatValue(adapter.calculatePrice()));
//            binding.btnContinuePrice.setText(getString(R.string.continue_) + " (" + formattedNumber + " " + getString(R.string.sar) + ")");
//        }
        if (starClickListener != null) {
            starClickListener.getPrice(calculatePrice());
        }
    }

    public double calculatePrice() {
        double finalPrice = 0;
        for (Agents data : socialList) {
            finalPrice = finalPrice + data.getPrice(data.services);
        }
        if (finalPrice == 0) {
            return 0;
        }
        return finalPrice + ((finalPrice * 5) / 100);//5% service tax
    }

    public interface StarClickListener {
        void onClickProfile(int pos, Agents agents);

        void getPrice(double price);
    }

    public StarsAdapter(BaseActivity context, List<Agents> socialList, StarClickListener starClickListener) {
        this.context = context;
        this.socialList = socialList;
        this.starClickListener = starClickListener;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemStarsBinding socialBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_stars, parent, false);
        return new ViewHolder(socialBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Agents agents = socialList.get(position);
        Glide.with(context).load(agents.image.path + agents.image.fileName).error(R.drawable.dp).into(holder.binding.imgGig);
        holder.binding.tvName.setText(agents.firstName + " " + agents.lastName);

        if (agents.categories != null && agents.categories.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (AgentCategory category : agents.categories) {
                stringBuilder.append(category.getCategory(context.getLanguage()));
                stringBuilder.append(", ");
            }
            holder.binding.tvDesc.setText(stringBuilder.toString());
        }

        if (agents.isShowProgress) {
            holder.binding.loutItemView.setBackgroundColor(context.getResources().getColor(R.color.C_44000000));
            holder.binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            holder.binding.loutItemView.setBackgroundColor(context.getResources().getColor(R.color.white));
            holder.binding.progressBar.setVisibility(View.GONE);
            agents.isShowProgress = false;
        }

        if (agents.services != null && agents.services.size() > 0) {
            shiftSocialIdMinusOneToEnd(agents.services);
            StarsPlatformAdapter adapter = new StarsPlatformAdapter(context, agents.services, this);
            holder.binding.rvPlatform.setAdapter(adapter);
        }

        if (!TextUtils.isEmpty(agents.notes)) {
            holder.binding.etNotes.setText(agents.notes);
        } else {
            holder.binding.etNotes.setText("");
        }

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
        ItemStarsBinding binding;

        public ViewHolder(ItemStarsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.txtProfile.setOnClickListener(view -> {
                if (starClickListener != null) {
                    starClickListener.onClickProfile(getAdapterPosition(), socialList.get(getAdapterPosition()));
                }
            });

            binding.etNotes.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    socialList.get(getAdapterPosition()).notes = charSequence + "";
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
    }
}