package com.nojom.client.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nojom.client.R;
import com.nojom.client.databinding.ItemChatListBindingImpl;
import com.nojom.client.model.ChatList;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.chat.ChatMessagesActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Utils;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;


public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.SimpleViewHolder> {
    private final BaseActivity baseActivity;
    private List<ChatList.Datum> arrUserList;
    private String imgPath = "";
    private LayoutInflater layoutInflater;
    private final PrettyTime prettyTime = new PrettyTime();

    public ChatListAdapter(Context context, List<ChatList.Datum> objects, String imgPath) {
        this.arrUserList = objects;
        this.imgPath = imgPath;
        baseActivity = ((BaseActivity) context);
    }

    public void doRefresh(List<ChatList.Datum> arrUserChatList) {
        this.arrUserList = arrUserChatList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        ItemChatListBindingImpl binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_chat_list, parent, false);
        return new SimpleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final SimpleViewHolder holder, final int position) {
        final ChatList.Datum item = arrUserList.get(position);
        if (!TextUtils.isEmpty(item.lastName) && !item.lastName.equals("null")) {
            holder.binding.tvReceiverName.setText(item.firstName + " " + item.lastName);
        } else {
            holder.binding.tvReceiverName.setText(item.firstName);
        }

        if (item.lastMessageData != null) {
            holder.binding.tvTime.setText(getTime(item.lastMessageData.messageCreatedAt));
        }

        if (!TextUtils.isEmpty(item.profilePic)) {
            Glide.with(baseActivity).load(imgPath + "/" + item.profilePic)
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(holder.binding.imgProfile);
        } else {
            holder.binding.imgProfile.setImageResource(R.mipmap.ic_launcher_round);
        }

        if (item.typing) {
            holder.binding.imgOnline.setVisibility(View.GONE);
            holder.binding.tvLastMessage.setTextColor(baseActivity.getResources().getColor(R.color.online));
            holder.binding.tvLastMessage.setText(baseActivity.getString(R.string.typing));
        } else {
            if (item.lastMessageData != null) {
                if (baseActivity.getUserID() == item.lastMessageData.senderId) {
                    holder.binding.tvLastMessage.setTextColor(baseActivity.getResources().getColor(R.color.black));
                } else if (item.lastMessageData.isSeenMessage.equalsIgnoreCase("1")) {
                    holder.binding.tvLastMessage.setTextColor(baseActivity.getResources().getColor(R.color.colorPrimary));
                } else {
                    holder.binding.tvLastMessage.setTextColor(baseActivity.getResources().getColor(R.color.black));
                }

                if (!TextUtils.isEmpty(item.lastMessageData.message.trim())) {
                    holder.binding.tvLastMessage.setText(item.lastMessageData.message);
                } else if (item.lastMessageData.file != null && item.lastMessageData.file.files != null) {
                    holder.binding.tvLastMessage.setText(item.lastMessageData.file.files.get(0).file);
                } else {
                    holder.binding.tvLastMessage.setText("💰 " + baseActivity.getString(R.string.offer));
                }
            } else {
                holder.binding.tvLastMessage.setTextColor(baseActivity.getResources().getColor(R.color.black));
            }
        }

        if (item.isSocketOnline == 1) {
            holder.binding.imgOnline.setVisibility(View.VISIBLE);
        } else {
            holder.binding.imgOnline.setVisibility(View.GONE);
        }

        holder.binding.loutHeader.setOnClickListener(v -> {
            baseActivity.setEnableDisableView(holder.binding.loutHeader);
            HashMap<String, String> chatMap = new HashMap<>();
            chatMap.put(Constants.RECEIVER_ID, item.id + "");
            if (!TextUtils.isEmpty(item.lastName) && !item.lastName.equals("null")) {
                chatMap.put(Constants.RECEIVER_NAME, item.firstName + " " + item.lastName);
            } else {
                chatMap.put(Constants.RECEIVER_NAME, item.firstName);
            }

            chatMap.put(Constants.RECEIVER_PIC, imgPath + "/" + item.profilePic);
            chatMap.put(Constants.SENDER_ID, baseActivity.getUserID() + "");
            chatMap.put(Constants.SENDER_NAME, baseActivity.getUserName());
            chatMap.put(Constants.SENDER_PIC, baseActivity.getProfilePic());
            chatMap.put("isProject", "1");//1 mean updated record

            Intent i = new Intent(baseActivity, ChatMessagesActivity.class);
            i.putExtra(Constants.CHAT_ID, baseActivity.getUserID() + "-" + item.id);  // ClientId - AgentId
            i.putExtra(Constants.CHAT_DATA, chatMap);
            if (baseActivity.getIsVerified() == 1) {
                baseActivity.startActivity(i);
            } else {
                baseActivity.toastMessage(baseActivity.getString(R.string.verification_is_pending_please_complete_the_verification_first_before_chatting_with_them));
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrUserList != null ? arrUserList.size() : 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public String getTime(long messageCreatedTime) {
        String validTime;
        String msgDate = Utils.convertDate(String.valueOf(messageCreatedTime), "dd MMM yyyy");
        String todayDate = Utils.convertDate(String.valueOf(System.currentTimeMillis()), "dd MMM yyyy");

        if (msgDate.equalsIgnoreCase(todayDate)) {
            validTime = prettyTime.format(new Date(messageCreatedTime));
        } else {
            String yesterdayDate = Utils.convertDate(String.valueOf(System.currentTimeMillis() - (1000 * 60 * 60 * 24)), "dd MMM yyyy");
            if (msgDate.equalsIgnoreCase(yesterdayDate)) {
                validTime = baseActivity.getString(R.string.yesterday);
            } else {
                if (baseActivity.getLanguage().equals("ar")) {
                    validTime = Utils.convertDate(String.valueOf(messageCreatedTime), "dd MMM, yyyy");
                } else {
                    validTime = Utils.convertDate(String.valueOf(messageCreatedTime), "MMM dd, yyyy");
                }
            }
        }

        return validTime;
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemChatListBindingImpl binding;

        public SimpleViewHolder(ItemChatListBindingImpl itemView) {
            super(itemView.getRoot());
            this.binding = itemView;

        }
    }
}
