package com.nojom.client.adapter;

import static com.nojom.client.util.Constants.API_GET_PROFILE_INFO;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ItemExpertListBinding;
import com.nojom.client.model.AgentProfile;
import com.nojom.client.model.Expert;
import com.nojom.client.model.ExpertLawyers;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.projects.InfluencerProfileActivityCopy;
import com.nojom.client.ui.projects.SelectFreelancerActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ExpertsAdapter extends RecyclerView.Adapter<ExpertsAdapter.SimpleViewHolder> implements RequestResponseListener {

    private List<Expert.Data> mDataset;
    private Context context;
    private BaseActivity activity;
    private boolean isSelectable;
    private List<ExpertLawyers.Data> expertUserList;
    private String filePath;
    private int adapterPos;

    public ExpertsAdapter(Context context, boolean isSelectable, String filePath) {
        this.context = context;
        this.isSelectable = isSelectable;
        this.filePath = filePath;
        activity = (BaseActivity) context;
        expertUserList = Preferences.getExpertUsers(context);
    }

    public void doRefresh(List<Expert.Data> projectsList) {
        int curSize = getItemCount();
        this.mDataset = projectsList;
        notifyItemRangeInserted(curSize, projectsList.size() - 1);
    }

    public void initList(List<Expert.Data> projectsList) {
        mDataset = new ArrayList<>();
        mDataset.addAll(projectsList);
        notifyDataSetChanged();
    }

    @NotNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemExpertListBinding itemExpertListBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_expert_list, parent, false);
        return new SimpleViewHolder(itemExpertListBinding);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {
        Expert.Data item = mDataset.get(position);
        //holder.tvName.setText(item.firstName + " " + item.lastName);
        holder.binding.tvName.setText(item.username);
        holder.binding.tvService.setText(String.format(Locale.US," / %s", item.serviceNameApp));
        try {
            holder.binding.ratingbar.setRating(Float.parseFloat(item.rate));
        } catch (NumberFormatException e) {
            holder.binding.ratingbar.setRating(0);
            e.printStackTrace();
        }
        holder.binding.tvRating.setText(String.format(Locale.US,"(%s reviews)", item.count));
        try {
            if (TextUtils.isEmpty(item.img)) {
                item.img = "";
            }
            activity.setImage(holder.binding.imgUser, filePath + item.img, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (item.emailVerified == 1) {
            holder.binding.imgVerify.setVisibility(View.VISIBLE);
        } else {
            holder.binding.imgVerify.setVisibility(View.GONE);
        }

        if (isSelectable) {
            holder.binding.imgCheck.setVisibility(View.VISIBLE);
            holder.binding.tvHire.setVisibility(View.GONE);
        } else {
            holder.binding.imgCheck.setVisibility(View.GONE);
            holder.binding.tvHire.setVisibility(View.VISIBLE);
        }

        if (checkIfExpertPresent(item.profileId)) {
            holder.binding.imgCheck.setImageResource(R.drawable.circle_check);
        } else {
            holder.binding.imgCheck.setImageResource(R.drawable.circle_uncheck);
        }

        if (item.isShowProgress) {
            holder.binding.progressBar.setVisibility(View.VISIBLE);
            holder.binding.rlProfile.setBackgroundResource(R.drawable.transp_rounded_corner_10);
        } else {
            holder.binding.progressBar.setVisibility(View.GONE);
            holder.binding.rlProfile.setBackground(null);
            item.isShowProgress = false;
        }
    }

    @Override
    public int getItemCount() {
        return mDataset != null ? mDataset.size() : 0;
    }

    public List<Expert.Data> getData() {
        return mDataset;
    }

    public void unSelectAll() {
        expertUserList = new ArrayList<>();
//        Preferences.setExpertUsers(context, null);//TODO:uncomment
        notifyDataSetChanged();
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemExpertListBinding binding;

        SimpleViewHolder(ItemExpertListBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            itemView.tvName.setOnClickListener(view -> {
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.rlProfile.setBackgroundResource(R.drawable.transp_rounded_corner_10);
                adapterPos = getAbsoluteAdapterPosition();

                getAgentProfile(mDataset.get(getAbsoluteAdapterPosition()).profileId);
            });

            itemView.imgUser.setOnClickListener(view -> {
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.rlProfile.setBackgroundResource(R.drawable.transp_rounded_corner_10);
                adapterPos = getAbsoluteAdapterPosition();

                getAgentProfile(mDataset.get(getAbsoluteAdapterPosition()).profileId);
            });

            itemView.getRoot().setOnClickListener(view -> {
                if (isSelectable) {
                    ((SelectFreelancerActivity) context).unSelectAll();

                    if (checkIfExpertPresent(mDataset.get(getAbsoluteAdapterPosition()).profileId)) {
                        removeExpert(mDataset.get(getAbsoluteAdapterPosition()).profileId);
                    } else {
                        addExpert(mDataset.get(getAbsoluteAdapterPosition()));
                    }
                    notifyDataSetChanged();
                }
            });

            itemView.tvHire.setOnClickListener(view -> {
                Preferences.writeString(activity, Constants.PLATFORM_ID, mDataset.get(getAbsoluteAdapterPosition()).serviceId + "");
                Preferences.writeString(activity, Constants.PLATFORM_NAME, mDataset.get(getAbsoluteAdapterPosition()).serviceName + "");
                ArrayList<ExpertLawyers.Data> expertUsers = new ArrayList<>();
                expertUsers.add(new ExpertLawyers.Data(mDataset.get(getAbsoluteAdapterPosition()).profileId, mDataset.get(getAbsoluteAdapterPosition()).username));
//                Preferences.setExpertUsers(activity, expertUsers);//TODO:uncomment
                activity.gotoMainActivity(Constants.TAB_POST_JOB);
            });
        }
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
//                    Preferences.setExpertUsers(context, expertUserList);//TODO:uncomment
                    break;
                }
            }
        }
    }

    private void addExpert(Expert.Data item) {
        if (expertUserList == null)
            expertUserList = new ArrayList<>();

        String name = item.username;
        expertUserList.add(new ExpertLawyers.Data(item.profileId, name));
//        Preferences.setExpertUsers(context, expertUserList);//TODO:uncomment
    }

    private void getAgentProfile(int agentProfileId) {
        if (!activity.isNetworkConnected())
            return;
        activity.isClickableView = true;

        HashMap<String, String> map = new HashMap<>();
        map.put("agent_profile_id", agentProfileId + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_PROFILE_INFO, true, map);
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        if (url.equalsIgnoreCase(API_GET_PROFILE_INFO)) {

            mDataset.get(adapterPos).isShowProgress = false;
            notifyItemChanged(adapterPos);

            AgentProfile profile = AgentProfile.getProfileInfo(responseBody);
            if (profile != null) {
//                Intent i = new Intent(context, FreelancerProfileActivity.class);
                Intent i = new Intent(context, InfluencerProfileActivityCopy.class);
                i.putExtra(Constants.AGENT_PROFILE_DATA, profile);
                context.startActivity(i);
            }
        }
        activity.isClickableView = false;
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        activity.isClickableView = false;
        if (url.equalsIgnoreCase(API_GET_PROFILE_INFO)) {
            mDataset.get(adapterPos).isShowProgress = false;
            notifyItemChanged(adapterPos);
        }
    }
}
