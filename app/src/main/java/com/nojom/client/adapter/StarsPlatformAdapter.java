package com.nojom.client.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nojom.client.R;
import com.nojom.client.databinding.ItemStarsPlatformBinding;
import com.nojom.client.model.AgentService;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class StarsPlatformAdapter extends RecyclerView.Adapter<StarsPlatformAdapter.ViewHolder> {

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

    public StarsPlatformAdapter(BaseActivity context, List<AgentService> socialList, PlatformClickListener starClickListener) {
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
        ItemStarsPlatformBinding socialBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_stars_platform, parent, false);
        return new ViewHolder(socialBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AgentService agents = socialList.get(position);
        Glide.with(context).load(agents.filename).error(R.drawable.dp).into(holder.binding.imgProfile);
        if (agents.socialPlatformId == -1) {
            holder.binding.tvName.setText(context.getString(R.string.all_platforms));
            holder.binding.imgProfile.setVisibility(View.GONE);
        } else {
            holder.binding.imgProfile.setVisibility(View.VISIBLE);
            holder.binding.tvName.setText(agents.getName(context.getLanguage()));
        }

        if (agents.followers != null && !TextUtils.isEmpty(String.valueOf(agents.followers))) {
            holder.binding.tvCount.setText("(" + agents.followers + ")");
        }
        if (agents.price != 0) {
            String formattedNumber = Utils.getDecimalFormat(Utils.formatValue(agents.price));
            holder.binding.tvPrice.setText(formattedNumber + " ");
        }
        if (agents.isChecked) {
            holder.binding.imgChk.setImageResource(R.drawable.check_square);
        } else {
            holder.binding.imgChk.setImageResource(R.drawable.ic_chk_thin);
        }
    }

    @Override
    public int getItemCount() {
        return socialList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemStarsPlatformBinding binding;

        public ViewHolder(ItemStarsPlatformBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.imgChk.setOnClickListener(view -> {
                if (socialList.get(getAdapterPosition()).socialPlatformId == -1) {
                    for (AgentService ser : socialList) {
                        ser.isChecked = !ser.isChecked;
                    }
                } else {
                    socialList.get(getAdapterPosition()).isChecked = !socialList.get(getAdapterPosition()).isChecked;
                }
                notifyDataSetChanged();
                if (starClickListener != null) {
                    starClickListener.onClickServiceChecked();
                }
            });


        }
    }
}  