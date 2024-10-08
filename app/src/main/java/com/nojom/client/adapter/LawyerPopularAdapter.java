package com.nojom.client.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.databinding.ItemPopularBinding;
import com.nojom.client.model.ExpertLawyers;
import com.nojom.client.model.InfluencerList;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.home.FindExpertActivity;
import com.nojom.client.util.Preferences;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class LawyerPopularAdapter extends RecyclerView.Adapter<LawyerPopularAdapter.SimpleViewHolder> {
    private final Context context;
    private final BaseActivity activity;
    private final List<InfluencerList.AllData> lawyerExpertData;
    private final LayoutInflater layoutInflater;
    private final OnClickListener onClickListener;
    private final String filePath;
    private final boolean isFromJobScreen;
    private List<ExpertLawyers.Data> expertUserList;

    public LawyerPopularAdapter(BaseActivity context, List<InfluencerList.AllData> lawyerExpertData, OnClickListener listener, String filePath, boolean isJobScreen) {
        this.context = context;
        activity = (BaseActivity) context;
        this.lawyerExpertData = lawyerExpertData;
        this.onClickListener = listener;
        this.filePath = filePath;
        this.isFromJobScreen = isJobScreen;
        layoutInflater = activity.getLayoutInflater();
        expertUserList = Preferences.getExpertUsers(context);
    }

    @NotNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemPopularBinding popularBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_popular, parent, false);
        return new SimpleViewHolder(popularBinding);
    }

    @Override
    public void onBindViewHolder(@NotNull final SimpleViewHolder holder, final int position) {
        InfluencerList.AllData expertData = lawyerExpertData.get(position);

        if (isFromJobScreen) {
            holder.binding.imgCheck.setVisibility(View.VISIBLE);
            holder.binding.imgFavourite.setVisibility(View.INVISIBLE);
        } else {
            holder.binding.imgCheck.setVisibility(View.GONE);
            holder.binding.imgFavourite.setVisibility(View.VISIBLE);

            if (expertData.isShowFavProgress) {
                holder.binding.imgFavourite.setVisibility(View.INVISIBLE);
                holder.binding.progressBarFav.setVisibility(View.VISIBLE);
            } else {
                holder.binding.imgFavourite.setVisibility(View.VISIBLE);
                holder.binding.progressBarFav.setVisibility(View.GONE);
            }
        }

        StringBuilder sbName = new StringBuilder();
        if (expertData.first_name != null) {
            sbName.append(expertData.first_name);
        }
        if (expertData.last_name != null) {
            sbName.append(" - ");
            sbName.append(expertData.last_name);
        }
        if (sbName.length() == 0) {
            sbName.append(expertData.username);
        }
        holder.binding.tvName.setText(sbName.toString());

//        if (expertData.verifCompleted == 1) {
//            holder.binding.tvName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_verified, 0);
//            holder.binding.tvName.setCompoundDrawablePadding((int) activity.getResources().getDimension(R.dimen._5sdp));
//        } else {
//            holder.binding.tvName.setCompoundDrawablesRelative(null, null, null, null);
//        }

        if (expertData.isShowProfileProgress) {
            holder.binding.tvViewProfile.setVisibility(View.INVISIBLE);
            holder.binding.progressBarProfile.setVisibility(View.VISIBLE);
        } else {
            holder.binding.tvViewProfile.setVisibility(View.VISIBLE);
            holder.binding.progressBarProfile.setVisibility(View.GONE);
        }

        holder.binding.tvDescription.setText(expertData.getCityName(activity.getLanguage()) + ", " + expertData.getCountryName(activity.getLanguage()));
//        if (activity.getCurrency().equals("SAR")) {
//            holder.binding.tvAmount.setText(String.format(Locale.US, "%s "+activity.getString(R.string.sar) + activity.getString(R.string._hr), Objects.requireNonNullElse(expertData.payRate, "0")));
//        } else {
//            holder.binding.tvAmount.setText(String.format(Locale.US, "$%s" + activity.getString(R.string._hr), Objects.requireNonNullElse(expertData.payRate, "0")));
//        }

//        if (expertData.countRating != null) {
//            holder.binding.tvRating.setText(String.format(Locale.US, "(%s " + activity.getString(R.string.review) + ")", Math.round(expertData.countRating)));
//        } else {
//            holder.binding.tvRating.setText("");
//        }

//        holder.binding.tvCity.setText(String.format(Locale.US, "%s %s", TextUtils.isEmpty(expertData.cityName) ? "" : expertData.cityName + ",", TextUtils.isEmpty(expertData.countryName) ? "" : expertData.countryName));

        if (isFromJobScreen) {
            if (checkIfExpertPresent(expertData.id)) {
                holder.binding.imgCheck.setImageResource(R.drawable.circle_check);
            } else {
                holder.binding.imgCheck.setImageResource(R.drawable.circle_uncheck);
            }
        } else {
            if (expertData.saved == 0) {
                holder.binding.imgFavourite.setImageResource(R.drawable.ic_fav);
            } else {
                holder.binding.imgFavourite.setImageResource(R.drawable.ic_fav_fill);
            }
        }

//        try {
//            if (expertData.rate != null) {
//                holder.binding.ratingbar.setVisibility(View.VISIBLE);
//                holder.binding.ratingbar.setRating(expertData.rate);
//            } else {
//                holder.binding.ratingbar.setRating(0);
//                holder.binding.ratingbar.setVisibility(View.GONE);
//            }
//        } catch (NumberFormatException e) {
//            holder.binding.ratingbar.setRating(0);
//            holder.binding.ratingbar.setVisibility(View.GONE);
//            e.printStackTrace();
//        }
        String img = "";
        if (expertData.img != null) {
            img = expertData.img;
        }

        activity.setImage(holder.binding.imgProfile, filePath + img, 0, 0);

//        if (expertData.skills != null && expertData.skills.size() > 0) {
//            holder.binding.textChipAttrs.setChipList(expertData.skills);
//        }

        if (getItemCount() == 1) {
            setBannerView(position, holder, 0);
        } else if (getItemCount() == 2) {
            setBannerView(position, holder, 1);
        } else {
            setBannerView(position, holder, 2);
        }

    }

    private void setBannerView(int position, SimpleViewHolder holder, int showPos) {
        if (position == showPos) {
            holder.binding.banner.rlPostJob.setVisibility(View.VISIBLE);
        } else {
            holder.binding.banner.rlPostJob.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return lawyerExpertData != null ? lawyerExpertData.size() : 0;
    }

    public List<InfluencerList.AllData> getData() {
        return lawyerExpertData;
    }

    public void updateSingleItem(int agentId, int saved) {
        for (int i = 0; i < lawyerExpertData.size(); i++) {
            if (lawyerExpertData.get(i).id == agentId) {
                lawyerExpertData.get(i).saved = saved;
                notifyItemChanged(i);
                break;
            }
        }
    }

    public void deleteSingleItem(int agentId) {
        for (int i = 0; i < lawyerExpertData.size(); i++) {
            if (lawyerExpertData.get(i).id == agentId) {
                lawyerExpertData.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }

    public void addItem(List<InfluencerList.AllData> data) {
        int curSize = getItemCount();
        this.lawyerExpertData.addAll(data);
        notifyItemRangeInserted(curSize, data.size() - 1);
    }


    private boolean checkIfExpertPresent(int profileId) {
        if (expertUserList != null && expertUserList.size() > 0) {
            for (ExpertLawyers.Data expert : expertUserList) {
                if (expert.id == profileId) {
                    return true;
                }
            }
        }

        return false;
    }

    private void removeExpert(int profileId) {
        if (expertUserList != null && expertUserList.size() > 0) {
            for (int i = 0; i < expertUserList.size(); i++) {
                if (expertUserList.get(i).id == profileId) {
                    expertUserList.remove(i);
                    Preferences.setExpertUsers(context, expertUserList);
                    break;
                }
            }
        }
    }

    private void addExpert(InfluencerList.AllData item) {
        if (expertUserList == null)
            expertUserList = new ArrayList<>();

        String name = item.first_name + " " + item.last_name;
        expertUserList.add(new ExpertLawyers.Data(item.id, name));
        Preferences.setExpertUsers(context, expertUserList);
    }

    public void unSelectAll() {
        expertUserList = new ArrayList<>();
        Preferences.setExpertUsers(context, null);
        notifyDataSetChanged();
    }

    public interface OnClickListener {
        void onClickFavourite(InfluencerList.AllData data);

        void onClickchat(InfluencerList.AllData data);

        void onClickViewProfile(InfluencerList.AllData data, int selPos);

        void onClickPostJobFree(InfluencerList.AllData data);
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemPopularBinding binding;

        SimpleViewHolder(ItemPopularBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.linRatingBar.setVisibility(View.GONE);
            binding.tvAmount.setVisibility(View.GONE);
            binding.tvCity.setVisibility(View.GONE);
            binding.textChipAttrs.setVisibility(View.INVISIBLE);

            binding.imgCheck.setOnClickListener(v -> {

                ((FindExpertActivity) context).unSelectAll();

                if (checkIfExpertPresent(lawyerExpertData.get(getAbsoluteAdapterPosition()).id)) {
                    removeExpert(lawyerExpertData.get(getAbsoluteAdapterPosition()).id);
                } else {
                    addExpert(lawyerExpertData.get(getAbsoluteAdapterPosition()));
                }
                notifyItemChanged(getAbsoluteAdapterPosition());

            });

            binding.linParent.setOnClickListener(v -> {
                if (isFromJobScreen) {
                    ((FindExpertActivity) context).unSelectAll();

                    if (checkIfExpertPresent(lawyerExpertData.get(getAbsoluteAdapterPosition()).id)) {
                        removeExpert(lawyerExpertData.get(getAbsoluteAdapterPosition()).id);
                    } else {
                        addExpert(lawyerExpertData.get(getAbsoluteAdapterPosition()));
                    }
                    notifyItemChanged(getAbsoluteAdapterPosition());
                }

            });

            binding.imgFavourite.setOnClickListener(v -> {
                if (onClickListener != null) {
                    binding.imgFavourite.setVisibility(View.GONE);
                    binding.progressBarFav.setVisibility(View.VISIBLE);
                    onClickListener.onClickFavourite(lawyerExpertData.get(getAbsoluteAdapterPosition()));
                }
            });

            binding.tvChat.setOnClickListener(v -> {
                if (onClickListener != null) {
                    activity.setEnableDisableView(binding.tvChat);
                    onClickListener.onClickchat(lawyerExpertData.get(getAbsoluteAdapterPosition()));
                }
            });

            binding.tvViewProfile.setOnClickListener(v -> {
                if (onClickListener != null) {
                    activity.setEnableDisableView(binding.tvViewProfile);
                    binding.tvViewProfile.setVisibility(View.INVISIBLE);
                    binding.progressBarProfile.setVisibility(View.VISIBLE);
                    onClickListener.onClickViewProfile(lawyerExpertData.get(getAbsoluteAdapterPosition()), getAbsoluteAdapterPosition());
                }
            });

            binding.banner.rlPostJob.setOnClickListener(v -> {
                if (onClickListener != null) {
                    onClickListener.onClickPostJobFree(lawyerExpertData.get(getAbsoluteAdapterPosition()));
                }
            });
        }
    }
}
