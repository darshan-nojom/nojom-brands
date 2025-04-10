package com.nojom.client.adapter;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nojom.client.R;
import com.nojom.client.databinding.ItemCampaignStarsBinding;
import com.nojom.client.model.Profile;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.chat.ChatMessagesActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Utils;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CampaignStarAdapter extends RecyclerView.Adapter<CampaignStarAdapter.ViewHolder> {

    private List<Profile> timelineItems;
    private BaseActivity activity;
    private final PrettyTime p = new PrettyTime();
    private OnClickStarListener onClickStarListener;
    private boolean isWhiteBg;

    public void setWhiteBackground(boolean b) {
        isWhiteBg = b;
    }

    public interface OnClickStarListener {
        void onClickStar(int pos, Profile profile);

        void onClickChat(int pos, Profile profile);
    }

    public CampaignStarAdapter(BaseActivity activity, List<Profile> timelineItems, OnClickStarListener listener) {
        this.timelineItems = timelineItems;
        this.activity = activity;
        this.onClickStarListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCampaignStarsBinding itemCardListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_campaign_stars, parent, false);
        return new ViewHolder(itemCardListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Profile item = timelineItems.get(position);
        holder.binding.tvReceiverName.setText(item.firstName + " " + item.lastName);

        Glide.with(activity).load(item.profile_picture).error(R.color.orange).into(holder.binding.imgProfile);
//        holder.binding.tvStatus.setText(capitalizeWords(item.req_status));
        holder.binding.tvBudget.setText(Utils.decimalFormat(String.valueOf(item.total_service_price)) + " " + activity.getString(R.string.sar));

        if (!TextUtils.isEmpty(item.client_note)) {
            holder.binding.txtPaid.setText(item.client_note);
        }

        if (item.req_status.equals("pending")) {
            holder.binding.tvStatus.setText(activity.getString(R.string.pending));
            holder.binding.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.yellow_bg_20));
            holder.binding.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.black));
        } else if (item.req_status.equals("approved") || item.req_status.equals("completed")) {
            holder.binding.tvStatus.setText(activity.getString(R.string.completed));
            holder.binding.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.green_button_bg_20));
            holder.binding.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.white));
        } else {
            holder.binding.tvStatus.setText(activity.getString(R.string.reject));
            holder.binding.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.red_bg_20));
            holder.binding.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.white));
        }

        Date date1 = Utils.changeDateFormat("yyyy-MM-dd hh:mm:ss", item.req_status_updated_at);
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dfFinal2;
        if (activity.getLanguage().equals("ar")) {
            dfFinal2 = new SimpleDateFormat("dd MMM,yyyy");
        } else {
            dfFinal2 = new SimpleDateFormat("MMM dd,yyyy");
        }


        if (date1 != null) {
            if (activity.printDifference(date1, date).equalsIgnoreCase("0")) {
                String result = p.format(Utils.changeDateFormat("yyyy-MM-dd hh:mm:ss", item.req_status_updated_at));
                holder.binding.txtDate.setText(activity.getString(R.string.due_date) + " " + result);
            } else {
                String finalDate = dfFinal2.format(date1);
                holder.binding.txtDate.setText(activity.getString(R.string.due_date) + " " + finalDate);
            }
        }
    }

    @Override
    public int getItemCount() {
        return timelineItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemCampaignStarsBinding binding;

        public ViewHolder(@NonNull ItemCampaignStarsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            if (isWhiteBg) {
                binding.loutHeader.setBackground(activity.getResources().getDrawable(R.drawable.white_button_bg_7));
            }
            binding.loutHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //start star activity
                    if (onClickStarListener != null) {
                        onClickStarListener.onClickStar(getAdapterPosition(), timelineItems.get(getAdapterPosition()));
                    }
                }
            });

            binding.txtChat.setOnClickListener(view -> {
                HashMap<String, String> chatMap = new HashMap<>();
                chatMap.put(Constants.RECEIVER_ID, timelineItems.get(getAdapterPosition()).id + "");
                chatMap.put(Constants.RECEIVER_NAME, timelineItems.get(getAdapterPosition()).username);
                chatMap.put(Constants.RECEIVER_PIC, timelineItems.get(getAdapterPosition()).profile_picture);
                chatMap.put(Constants.SENDER_ID, activity.getUserData().id + "");
                chatMap.put(Constants.SENDER_NAME, activity.getUserData().username);
                chatMap.put(Constants.SENDER_PIC, activity.getUserData().filePath.pathProfilePicClient + activity.getUserData().profilePic);
                chatMap.put("isProject", "1");//1 mean updated record
//                if (expertGigDetail.gigType.equalsIgnoreCase("1")) {
//                    chatMap.put("projectType", "3");//2=job & 1= gig
//                } else {
                chatMap.put("projectType", "1");//2=job & 1= gig
//                }
                chatMap.put("isDetailScreen", "true");

                Intent chatIntent = new Intent(activity, ChatMessagesActivity.class);
                chatIntent.putExtra(Constants.CHAT_ID, activity.getUserData().id + "-" + timelineItems.get(getAdapterPosition()).id);  // ClientId - AgentId
                chatIntent.putExtra(Constants.CHAT_DATA, chatMap);
                if (activity.getIsVerified() == 1) {
                    activity.startActivity(chatIntent);
                } else {
                    activity.toastMessage(activity.getString(R.string.verification_is_pending_please_complete_the_verification_first_before_chatting_with_them));
                }
            });
        }
    }
}
