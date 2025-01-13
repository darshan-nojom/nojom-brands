package com.nojom.client.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nojom.client.R;
import com.nojom.client.databinding.ItemStarsPlatformBinding;
import com.nojom.client.model.CampService;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class CampStarsPlatformAdapter extends RecyclerView.Adapter<CampStarsPlatformAdapter.ViewHolder> {

    private BaseActivity context;
    private List<CampService> socialList;
    private PlatformClickListener starClickListener;

    public List<CampService> getData() {
        return socialList;
    }

    public interface PlatformClickListener {
        void onClickService(int pos, CampService serv);

        void onClickServiceChecked();
    }

    public CampStarsPlatformAdapter(BaseActivity context, List<CampService> socialList, PlatformClickListener starClickListener) {
        this.context = context;
        this.socialList = socialList;
        this.starClickListener = starClickListener;
        setHasStableIds(true);
    }


    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemStarsPlatformBinding socialBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_stars_platform, parent, false);
        return new ViewHolder(socialBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CampService agents = socialList.get(position);
        if (agents.socialPlatform != null && agents.socialPlatform.size() > 0) {
            Glide.with(context).load(agents.socialPlatform.get(0).filename).error(R.drawable.dp).into(holder.binding.imgProfile);
            if (agents.socialPlatform.get(0).id != null && agents.socialPlatform.get(0).id == -1) {
                holder.binding.tvName.setText(context.getString(R.string.all_platforms));
                holder.binding.imgProfile.setVisibility(View.GONE);
            } else {
                holder.binding.imgProfile.setVisibility(View.VISIBLE);
                holder.binding.tvName.setText(agents.socialPlatform.get(0).getName(context.getLanguage()));
            }
        }
//        if (agents.followers != null && !TextUtils.isEmpty(agents.followers)) {
//            holder.binding.tvCount.setText("(" + agents.followers + ")");
//        }
        if (agents.price != null && agents.price != 0) {
            String formattedNumber = Utils.getDecimalFormat(Utils.formatValue(agents.price));
            holder.binding.tvPrice.setText(formattedNumber + " ");
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
            binding.imgChk.setVisibility(View.GONE);
        }
    }
}  