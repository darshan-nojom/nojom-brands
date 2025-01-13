package com.nojom.client.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nojom.client.R;
import com.nojom.client.databinding.ItemSmBinding;
import com.nojom.client.model.AgentService;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class SelectedStarsPlatformAdapter extends RecyclerView.Adapter<SelectedStarsPlatformAdapter.ViewHolder> {

    private BaseActivity context;
    private List<AgentService> socialList;
    private PlatformClickListener starClickListener;

    public List<AgentService> getData() {
        return socialList;
    }

    public interface PlatformClickListener {
        void onClickService(int pos, AgentService serv);

        void onClickServiceChecked();
    }

    public SelectedStarsPlatformAdapter(BaseActivity context, List<AgentService> socialList, PlatformClickListener starClickListener) {
        this.context = context;
        this.socialList = socialList;
        this.starClickListener = starClickListener;
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return socialList.get(position).socialPlatformId;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemSmBinding socialBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_sm, parent, false);
        return new ViewHolder(socialBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AgentService agents = socialList.get(position);
        Glide.with(context).load(agents.filename).error(R.drawable.dp).into(holder.binding.imgGig);
        if (agents.socialPlatformId == -1) {
            holder.binding.tvName.setText(context.getString(R.string.all_platforms));
            holder.binding.imgGig.setVisibility(View.GONE);
        } else {
            holder.binding.imgGig.setVisibility(View.VISIBLE);
            holder.binding.tvName.setText(agents.getName(context.getLanguage()));
        }

        if (agents.price != 0) {
            String formattedNumber = Utils.getDecimalFormat(Utils.formatValue(agents.price));
            holder.binding.tvPrice.setText(formattedNumber + " ");
        }
    }

    @Override
    public int getItemCount() {
        return socialList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemSmBinding binding;

        public ViewHolder(ItemSmBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;


        }
    }
}  