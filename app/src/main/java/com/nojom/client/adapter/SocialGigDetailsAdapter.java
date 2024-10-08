package com.nojom.client.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.databinding.ItemSocialGigDetailsBinding;
import com.nojom.client.model.ProjectGigByID;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Utils;

import java.util.List;

public class SocialGigDetailsAdapter extends RecyclerView.Adapter<SocialGigDetailsAdapter.SimpleViewHolder> {
    private final BaseActivity activity;
    private final List<ProjectGigByID.SocialPlatform> arrSocialList;
    LayoutInflater layoutInflater;

    public SocialGigDetailsAdapter(BaseActivity activity, List<ProjectGigByID.SocialPlatform> arrSocialList) {
        this.activity = activity;
        this.arrSocialList = arrSocialList;
        layoutInflater = activity.getLayoutInflater();
    }

    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSocialGigDetailsBinding popularBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_social_gig_details, parent, false);
        return new SimpleViewHolder(popularBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final SimpleViewHolder holder, int position) {
        try {
            ProjectGigByID.SocialPlatform item = arrSocialList.get(position);

            holder.binding.txtName.setText(item.name + " (" + activity.formatNumber(item.followersCount) + " " + activity.getString(R.string.followers) + ")");
            holder.binding.txtUserName.setText(item.username);

            holder.binding.txtUserName.setOnClickListener(v -> {
                ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(activity.getString(R.string.copied), item.username);
                if (clipboard != null) {
                    clipboard.setPrimaryClip(clip);
                    activity.toastMessage(item.username + " " + activity.getString(R.string.copied));
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return arrSocialList.size();
    }


    class SimpleViewHolder extends RecyclerView.ViewHolder {
        ItemSocialGigDetailsBinding binding;

        public SimpleViewHolder(ItemSocialGigDetailsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
