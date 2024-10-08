package com.nojom.client.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.databinding.ItemProposalFreelancerListBinding;
import com.nojom.client.model.Profile;
import com.nojom.client.model.ProjectByID;
import com.nojom.client.model.Proposals;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.chat.ChatMessagesActivity;
import com.nojom.client.ui.clientprofile.HireActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public class ProposalsAdapter extends RecyclerView.Adapter<ProposalsAdapter.SimpleViewHolder> {

    private final Context context;
    private final BaseActivity activity;
    private final Profile profileData;
    private final ProjectByID projectData;
    private final OnClickProfileListener onClickProfileListener;
    private List<Proposals.Data> proposalList;

    public ProposalsAdapter(Context context, ProjectByID projectData, OnClickProfileListener onClickProfileListener) {
        this.context = context;
        this.projectData = projectData;
        this.onClickProfileListener = onClickProfileListener;
        activity = (BaseActivity) context;
        profileData = Preferences.getProfileData(activity);
    }

    public List<Proposals.Data> getData() {
        return proposalList;
    }

    public void doRefresh(List<Proposals.Data> proposalList) {
        this.proposalList = proposalList;
        notifyDataSetChanged();
    }

    @NotNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemProposalFreelancerListBinding proposalListBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_proposal_freelancer_list, parent, false);
        return new SimpleViewHolder(proposalListBinding);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {
        try {
            Proposals.Data item = proposalList.get(position);

            if (item.isShowProfileProgress) {
                holder.binding.relProposal.setBackgroundResource(R.drawable.transp_rounded_corner_10);
                holder.binding.progressBar.setVisibility(View.VISIBLE);
            } else {
                holder.binding.progressBar.setVisibility(View.GONE);
                holder.binding.relProposal.setBackground(null);
            }

            if (!TextUtils.isEmpty(item.lastName) && !item.lastName.equals("null")) {
                holder.binding.tvName.setText(item.firstName + " " + item.lastName);
            } else {
                holder.binding.tvName.setText(item.firstName);
            }


            if (item.aveRate != null) {
                holder.binding.ratingbar.setRating(item.aveRate);
            } else {
                holder.binding.ratingbar.setRating(0);
            }

            if (item.totalReviews != null) {
                holder.binding.tvRating.setText("(" + Math.round(item.totalReviews) + " " + activity.getString(R.string.reviews) + ")");
            } else {
                holder.binding.tvRating.setText("(0 " + activity.getString(R.string.reviews) + ")");
            }

            holder.binding.tvBidPrice.setText(activity.getCurrency().equals("SAR") ? Utils.decimalFormat(String.valueOf(item.amount)) + " " + activity.getString(R.string.sar)
                    : activity.getString(R.string.dollar) + Utils.decimalFormat(String.valueOf(item.amount)));
            holder.binding.tvPriceType.setText(item.deadlineValue + "");
            holder.binding.tvType.setText(" " + item.getDeadlineType(activity.getLanguage()));
            holder.binding.tvProposal.setText(item.message);

            activity.setImage(holder.binding.imgUser, TextUtils.isEmpty(item.img) ? "" : activity.getUserData().filePath.pathProfilePicAgent + item.img, 0, 0);

            holder.binding.tvProposal.setTrimLines(3);
            holder.binding.tvProposal.setTrimMode(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return proposalList.size();
    }

    public interface OnClickProfileListener {
        void onClickProfile(int agentId, Proposals.Data userData, int selPos);
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemProposalFreelancerListBinding binding;

        SimpleViewHolder(ItemProposalFreelancerListBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.tvHire.setOnClickListener(v -> {
                Intent i = new Intent(context, HireActivity.class);
                i.putExtra(Constants.USER_DATA, proposalList.get(getAbsoluteAdapterPosition()));
                i.putExtra(Constants.USER_IMG_PATH, activity.getUserData().filePath.pathProfilePicAgent);
                context.startActivity(i);
            });

            binding.tvChat.setOnClickListener(v -> {
                if (profileData != null) {
                    HashMap<String, String> chatMap = new HashMap<>();
                    chatMap.put(Constants.RECEIVER_ID, proposalList.get(getAbsoluteAdapterPosition()).profileId + "");
                    if (!TextUtils.isEmpty(proposalList.get(getAbsoluteAdapterPosition()).lastName) && !proposalList.get(getAbsoluteAdapterPosition()).lastName.equals("null")) {
                        chatMap.put(Constants.RECEIVER_NAME, proposalList.get(getAbsoluteAdapterPosition()).firstName + " " + proposalList.get(getAbsoluteAdapterPosition()).lastName);
                    } else {
                        chatMap.put(Constants.RECEIVER_NAME, proposalList.get(getAbsoluteAdapterPosition()).firstName);
                    }

                    chatMap.put(Constants.RECEIVER_PIC, activity.getUserData().filePath.pathProfilePicAgent + proposalList.get(getAbsoluteAdapterPosition()).img);
                    chatMap.put(Constants.SENDER_ID, profileData.id + "");
                    chatMap.put(Constants.SENDER_NAME, profileData.username);
                    chatMap.put(Constants.SENDER_PIC, profileData.filePath.pathProfilePicClient + profileData.profilePic);
                    chatMap.put(Constants.PROJECT_ID, String.valueOf(proposalList.get(getAbsoluteAdapterPosition()).jobPostId));
                    chatMap.put("isProject", "1");//1 mean updated record
                    chatMap.put("projectType", "2");//2=job & 1= gig
                    chatMap.put("isDetailScreen", "true");

                    Intent i = new Intent(activity, ChatMessagesActivity.class);
                    i.putExtra(Constants.CHAT_ID, profileData.id + "-" + proposalList.get(getAbsoluteAdapterPosition()).profileId);  // ClientId - AgentId
                    i.putExtra(Constants.CHAT_DATA, chatMap);
                    if (activity.getIsVerified() == 1) {
                        context.startActivity(i);
                    } else {
                        activity.toastMessage(activity.getString(R.string.verification_is_pending_please_complete_the_verification_first_before_chatting_with_them));
                    }

                } else {
                    Utils.toastMessage(context, activity.getString(R.string.something_went_wrong));
                }
            });

            binding.rlProfile.setOnClickListener(v -> {
                if (onClickProfileListener != null) {
                    binding.relProposal.setBackgroundResource(R.drawable.transp_rounded_corner_10);
                    binding.progressBar.setVisibility(View.VISIBLE);
                    onClickProfileListener.onClickProfile(proposalList.get(getAbsoluteAdapterPosition()).profileId, proposalList.get(getAbsoluteAdapterPosition()), getAbsoluteAdapterPosition());
                }
            });
        }
    }
}
