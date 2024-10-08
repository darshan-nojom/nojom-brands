package com.nojom.client.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.nojom.client.R;
import com.nojom.client.databinding.ItemChatMsgBindingImpl;
import com.nojom.client.model.ChatMessageList;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Utils;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.SimpleViewHolder> {
    public BaseActivity baseActivity;
    private ArrayList<ChatMessageList.DataChatList> chatMessage;
    private LayoutInflater layoutInflater;
    private OnClickListener onClickListener;

    public ChatMessageAdapter(ArrayList<ChatMessageList.DataChatList> objects, Context context, OnClickListener onClickListener) {
        this.chatMessage = objects;
        this.onClickListener = onClickListener;
        baseActivity = ((BaseActivity) context);
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        ItemChatMsgBindingImpl binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_chat_msg, parent, false);
        return new SimpleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final SimpleViewHolder holder, final int position) {
        final ChatMessageList.DataChatList item = chatMessage.get(position);

        if (item.isDayChange) {
            holder.binding.dayDate.setVisibility(View.VISIBLE);
            holder.binding.dayDate.setText(getTime(item.messageCreatedAt));
        } else {
            holder.binding.dayDate.setVisibility(View.GONE);
        }

        if (item.senderId.equalsIgnoreCase(String.valueOf(baseActivity.getUserID()))) {
            holder.binding.frameOutgoing.setVisibility(View.VISIBLE);
            holder.binding.frameIncoming.setVisibility(View.GONE);
            holder.binding.tvMyTimestamp.setVisibility(View.VISIBLE);
            holder.binding.tvTimestamp.setVisibility(View.GONE);
            holder.binding.loutOffer.setVisibility(View.GONE);
            if (item.isSeenMessage.equalsIgnoreCase("1")) {
                holder.binding.imgSeen.setImageResource(R.drawable.sent);
            } else {
                holder.binding.imgSeen.setImageResource(R.drawable.seen);
            }
            try {
                if (item.file != null && item.file.files != null) {
                    if (!TextUtils.isEmpty(item.file.files.get(0).file)) {
                        holder.binding.tvMyMessage.setVisibility(View.GONE);
                        holder.binding.rlImageSender.setVisibility(View.VISIBLE);
                        String fileUrl, fileName;
                        if (item.file.files.get(0).fileStorage != null && item.file.files.get(0).fileStorage.equalsIgnoreCase("firebase")) {
                            fileUrl = item.file.files.get(0).firebaseUrl;
                            fileName = item.file.files.get(0).file;
                        } else {
                            fileUrl = item.file.path + item.file.files.get(0).file;
                            fileName = item.file.files.get(0).file;
                        }
                        redirectIntent(fileUrl, holder, true, fileName);
                    } else {
                        holder.binding.oProgressSender.setVisibility(View.GONE);
                        holder.binding.rlImageSender.setVisibility(View.GONE);
                        holder.binding.tvMyMessage.setVisibility(View.VISIBLE);
                    }
                } else {
                    holder.binding.oProgressSender.setVisibility(View.GONE);
                    holder.binding.rlImageSender.setVisibility(View.GONE);
                    holder.binding.tvMyMessage.setVisibility(View.VISIBLE);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!TextUtils.isEmpty(item.message)) {
                holder.binding.tvMyMessage.setText(item.message.trim());
            }

            if (item.messageCreatedAt != 0) {
                holder.binding.tvMyTimestamp.setVisibility(View.VISIBLE);
                holder.binding.tvMyTimestamp.setText(Utils.convertDate(String.valueOf(item.messageCreatedAt), "hh:mm a"));
            } else {
                holder.binding.tvMyTimestamp.setVisibility(View.GONE);
            }

        } else {
            holder.binding.frameOutgoing.setVisibility(View.GONE);
            holder.binding.frameIncoming.setVisibility(View.VISIBLE);
            holder.binding.tvMyTimestamp.setVisibility(View.GONE);
            holder.binding.tvTimestamp.setVisibility(View.VISIBLE);

            try {
                if (item.file != null && item.file.files != null) {
                    if (!TextUtils.isEmpty(item.file.files.get(0).file)) {
                        holder.binding.tvMessage.setVisibility(View.GONE);
                        holder.binding.rlImageReceiver.setVisibility(View.VISIBLE);
                        String fileUrl, fileName;
                        if (item.file.files.get(0).fileStorage != null && item.file.files.get(0).fileStorage.equalsIgnoreCase("firebase")) {
                            fileUrl = item.file.files.get(0).firebaseUrl;
                            fileName = item.file.files.get(0).file;
                        } else {
                            fileUrl = item.file.path + item.file.files.get(0).file;
                            fileName = item.file.files.get(0).file;
                        }
                        redirectIntent(fileUrl, holder, false, fileName);

                    } else {
                        holder.binding.rlImageReceiver.setVisibility(View.GONE);
                        holder.binding.tvMessage.setVisibility(View.VISIBLE);
                    }
                } else {
                    holder.binding.rlImageReceiver.setVisibility(View.GONE);
                    holder.binding.tvMessage.setVisibility(View.VISIBLE);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!TextUtils.isEmpty(item.message)) {
                holder.binding.tvMessage.setText(item.message.trim());
            }

            if (item.messageCreatedAt != 0) {
                holder.binding.tvTimestamp.setVisibility(View.VISIBLE);
                holder.binding.tvTimestamp.setText(Utils.convertDate(String.valueOf(item.messageCreatedAt), "hh:mm a"));
            } else {
                holder.binding.tvTimestamp.setVisibility(View.GONE);
            }

            if (item.offer != null) {
                holder.binding.frameIncoming.setVisibility(View.GONE);
                holder.binding.loutOffer.setVisibility(View.VISIBLE);
                holder.binding.progressBarView.setVisibility(View.GONE);
                holder.binding.tvView.setVisibility(View.VISIBLE);
                holder.binding.tvTitle.setText(item.offer.offerTitle);
                holder.binding.tvDescription.setText(item.offer.description);
                holder.binding.tvPrice.setText(
                        baseActivity.getCurrency().equals("SAR") ? Utils.decimalFormat(String.valueOf(item.offer.price)) + " "+baseActivity.getString(R.string.sar)
                                : baseActivity.getString(R.string.dollar) + Utils.decimalFormat(String.valueOf(item.offer.price)));
                if (item.messageCreatedAt != 0) {
                    holder.binding.tvTimestampOffer.setVisibility(View.VISIBLE);
                    holder.binding.tvTimestampOffer.setText(Utils.convertDate(String.valueOf(item.messageCreatedAt), "hh:mm a"));
                } else {
                    holder.binding.tvTimestampOffer.setVisibility(View.GONE);
                }

                if (item.offer.offerStatus == 2 || item.offer.offerStatus == 5) {
                    holder.binding.loutView.setBackground(null);
                    holder.binding.tvView.setText(item.offer.offerStatus == 2 ? baseActivity.getResources().getString(R.string.accepted) : baseActivity.getResources().getString(R.string.completed));
                    holder.binding.tvView.setTextColor(baseActivity.getResources().getColor(R.color.green));
                } else if (item.offer.offerStatus == 3 || item.offer.offerStatus == 4 || item.offer.offerStatus == 0 || item.offer.offerStatus == 6) {
                    holder.binding.loutView.setBackground(null);
                    if (item.offer.offerStatus == 3) {
                        holder.binding.tvView.setText(baseActivity.getResources().getString(R.string.rejected));
                    } else if (item.offer.offerStatus == 4) {
                        holder.binding.tvView.setText(baseActivity.getResources().getString(R.string.withdrawn));
                    } else if (item.offer.offerStatus == 0) {
                        holder.binding.tvView.setText(baseActivity.getResources().getString(R.string.deleted));
                    } else if (item.offer.offerStatus == 6) {
                        holder.binding.tvView.setText(baseActivity.getResources().getString(R.string.expired));
                    }
                    holder.binding.tvView.setTextColor(baseActivity.getResources().getColor(R.color.red));
                    holder.binding.loutView.setEnabled(false);
                } else if (item.offer.offerStatus == 1) {
                    holder.binding.loutView.setBackground(baseActivity.getResources().getDrawable(R.drawable.white_button_bg));
                    holder.binding.tvView.setText(baseActivity.getResources().getString(R.string.view));
                    holder.binding.tvView.setTextColor(baseActivity.getResources().getColor(R.color.black));
                    holder.binding.loutView.setEnabled(true);
                }
            } else {
                holder.binding.loutOffer.setVisibility(View.GONE);
            }
        }

        holder.binding.progressBarView.setVisibility(View.GONE);
        holder.binding.progressBarViewAccepted.setVisibility(View.GONE);
        holder.binding.tvView.setVisibility(View.VISIBLE);
    }

    private void commonLayout(boolean isMyData, SimpleViewHolder holder, Drawable drawable, String fileName, String fileUrl) {
        if (isMyData) {
            holder.binding.llMyDoc.setVisibility(View.VISIBLE);
            holder.binding.ivMyDoc.setImageDrawable(drawable);
            holder.binding.tvMyDocName.setText(fileName);
            holder.binding.ivMyDoc.setTag(fileUrl);
        } else {
            holder.binding.llDoc.setVisibility(View.VISIBLE);
            holder.binding.tvDocName.setText(fileName);
            holder.binding.ivDoc.setImageDrawable(drawable);
            holder.binding.ivDoc.setTag(fileUrl);
        }
    }

    public String getTime(long messageCreatedTime) {
        String validTime = "";
        String msgDate = Utils.convertDate(String.valueOf(messageCreatedTime), "dd MMM yyyy");
        String todayDate = Utils.convertDate(String.valueOf(System.currentTimeMillis()), "dd MMM yyyy");

        if (msgDate.equalsIgnoreCase(todayDate)) {
            validTime = "Today";
        } else {
            String yesterdayDate = Utils.convertDate(String.valueOf(System.currentTimeMillis() - (1000 * 60 * 60 * 24)), "dd MMM yyyy");
            if (msgDate.equalsIgnoreCase(yesterdayDate)) {
                validTime = "Yesterday";
            } else {
                validTime = Utils.convertDate(String.valueOf(messageCreatedTime), "dd MMM yyyy");
            }
        }

        return validTime;
    }

    private void redirectIntent(String fileUrl, SimpleViewHolder holder, boolean isMyData, String fileName) {

        try {
            holder.binding.ivOutgoing.setVisibility(View.GONE);
            holder.binding.loutOutgoing.setVisibility(View.GONE);
            holder.binding.llDoc.setVisibility(View.GONE);
            holder.binding.llMyDoc.setVisibility(View.GONE);
            holder.binding.ivIncoming.setVisibility(View.GONE);


            if (fileUrl.contains(".doc") || fileUrl.contains(".docx") || fileUrl.contains(".DOC") || fileUrl.contains(".DOCX")) {
                commonLayout(isMyData, holder, baseActivity.getResources().getDrawable(R.drawable.vw_ic_word), fileName, fileUrl);
            } else if (fileUrl.contains(".txt") || fileUrl.contains(".TXT")) {
                commonLayout(isMyData, holder, baseActivity.getResources().getDrawable(R.drawable.vw_ic_txt), fileName, fileUrl);
            } else if (fileUrl.contains(".pdf") || fileUrl.contains(".PDF")) {
                commonLayout(isMyData, holder, baseActivity.getResources().getDrawable(R.drawable.vw_ic_pdf), fileName, fileUrl);
            } else if (fileUrl.contains(".ppt") || fileUrl.contains(".pptx") || fileUrl.contains(".PPT") || fileUrl.contains(".PPTX")) {
                commonLayout(isMyData, holder, baseActivity.getResources().getDrawable(R.drawable.vw_ic_ppt), fileName, fileUrl);
            } else if (fileUrl.contains(".xls") || fileUrl.contains(".xlsx") || fileUrl.contains(".XLS") || fileUrl.contains(".XLSX")) {
                commonLayout(isMyData, holder, baseActivity.getResources().getDrawable(R.drawable.vw_ic_excel), fileName, fileUrl);
            } else if (fileUrl.contains(".jpg") || fileUrl.contains(".png") || fileUrl.contains(".jpeg") || fileUrl.contains(".gif")
                    || fileUrl.contains(".JPG") || fileUrl.contains(".PNG") || fileUrl.contains(".JPEG") || fileUrl.contains(".GIF")) {
                if (isMyData) {
                    holder.binding.ivOutgoing.setVisibility(View.VISIBLE);
                    holder.binding.loutOutgoing.setVisibility(View.VISIBLE);
                    if (!TextUtils.isEmpty(fileUrl)) {
                        Glide.with(baseActivity).load(fileUrl)
                                .placeholder(R.mipmap.ic_launcher)
                                .error(R.mipmap.ic_launcher)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .apply(new RequestOptions().override(512, 512))
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        holder.binding.oProgressSender.setVisibility(View.GONE);
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        holder.binding.oProgressSender.setVisibility(View.GONE);
                                        return false;
                                    }
                                })
                                .into(holder.binding.ivOutgoing);
                    } else {
                        holder.binding.ivOutgoing.setImageResource(R.mipmap.ic_launcher);
                    }
                    holder.binding.ivOutgoing.setTag(fileUrl);
                } else {
                    holder.binding.ivIncoming.setVisibility(View.VISIBLE);
                    if (!TextUtils.isEmpty(fileUrl)) {
                        Glide.with(baseActivity).load(fileUrl)
                                .placeholder(R.mipmap.ic_launcher)
                                .error(R.mipmap.ic_launcher)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .apply(new RequestOptions().override(512, 512))
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
                                .into(holder.binding.ivIncoming);
                    } else {
                        holder.binding.ivOutgoing.setImageResource(R.mipmap.ic_launcher);
                    }
                    holder.binding.ivIncoming.setTag(fileUrl);
                }

            } else if (fileUrl.contains(".mp4") || fileUrl.contains(".avi") || fileUrl.contains(".mov") || fileUrl.contains(".m4a") ||
                    fileUrl.contains(".MP4") || fileUrl.contains(".AVI") || fileUrl.contains(".MOV") || fileUrl.contains(".M4A")) {
                commonLayout(isMyData, holder, baseActivity.getResources().getDrawable(R.drawable.vw_ic_video), fileName, fileUrl);
            } else if (fileUrl.contains(".zip") || fileUrl.contains(".rar") || fileUrl.contains(".ZIP") || fileUrl.contains(".RAR")) {
                commonLayout(isMyData, holder, baseActivity.getResources().getDrawable(R.drawable.vw_ic_zip), fileName, fileUrl);
            } else if (fileUrl.contains(".mp3") || fileUrl.contains(".MP3")) {
                commonLayout(isMyData, holder, baseActivity.getResources().getDrawable(R.drawable.ic_audio), fileName, fileUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void redirectIntent(String fileUrl, ImageView view) {
        try {

            String driveUrl = "http://drive.google.com/viewerng/viewer?embedded=true&url=";
            String finalFileUrl;
            String mime;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(fileUrl));

            if (fileUrl.contains(".doc") || fileUrl.contains(".docx")) {
                mime = "application/msword";
                finalFileUrl = driveUrl + fileUrl;
            } else if (fileUrl.contains(".txt")) {
                mime = "text/plain";
                finalFileUrl = driveUrl + fileUrl;
            } else if (fileUrl.contains(".pdf")) {
                mime = "application/pdf";
                finalFileUrl = driveUrl + fileUrl;
            } else if (fileUrl.contains(".ppt") || fileUrl.contains(".pptx")) {
                mime = "application/vnd.ms-powerpoint";
                finalFileUrl = driveUrl + fileUrl;
            } else if (fileUrl.contains(".xls") || fileUrl.contains(".xlsx")) {
                mime = "application/vnd.ms-excel";
                finalFileUrl = driveUrl + fileUrl;
            } else if (fileUrl.contains(".jpg") || fileUrl.contains(".png") || fileUrl.contains(".jpeg") || fileUrl.contains(".gif")) {
                mime = "image/*";
                finalFileUrl = fileUrl;
            } else if (fileUrl.contains(".mp4") || fileUrl.contains(".avi") || fileUrl.contains(".MOV") || fileUrl.contains(".m4a")) {
                mime = "video/*";
                finalFileUrl = fileUrl;
            } else if (fileUrl.contains(".zip") || fileUrl.contains(".rar")) {
                mime = "application/x-wav";
                finalFileUrl = driveUrl + fileUrl;
            } else if (fileUrl.contains(".mp3")) {
                mime = "audio/*";
                finalFileUrl = fileUrl;
            } else {
                mime = "/";
                finalFileUrl = driveUrl + fileUrl;
            }
            if (mime.equalsIgnoreCase("image/*")) {
                intent.setDataAndType(Uri.parse(finalFileUrl), mime);
            } else {
                intent.setData(Uri.parse(finalFileUrl));
            }
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            baseActivity.startActivity(intent);
            view.setTag(null);
        } catch (Exception e) {
            baseActivity.toastMessage(baseActivity.getString(R.string.no_application_available_to_view_this_type_of_file));
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return chatMessage != null ? chatMessage.size() : 0;
    }

    @Override
    public long getItemId(int position) {
        try {
            return chatMessage.get(position).messageId;
        } catch (Exception e) {
            return position;
        }
    }

    private void clickListener(ImageView view) {
        view.setOnClickListener(v -> {
            try {
                if (view.getTag() != null)
                    redirectIntent(view.getTag().toString(), view);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public interface OnClickListener {
        void onClickViewDetail(ChatMessageList.DataChatList dataChatList, boolean isFromGigView);
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemChatMsgBindingImpl binding;

        public SimpleViewHolder(ItemChatMsgBindingImpl itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
            clickListener(binding.ivMyDoc);
            clickListener(binding.ivDoc);
            clickListener(binding.ivIncoming);
            clickListener(binding.ivOutgoing);

            binding.loutView.setOnClickListener(v -> {
                if (onClickListener != null) {
                    baseActivity.setEnableDisableView(binding.loutView);
                    if (chatMessage.get(getAbsoluteAdapterPosition()).offer.offerStatus == 2 || chatMessage.get(getAbsoluteAdapterPosition()).offer.offerStatus == 5) {
                        binding.progressBarViewAccepted.setVisibility(View.VISIBLE);
                        binding.tvView.setVisibility(View.GONE);
                        onClickListener.onClickViewDetail(chatMessage.get(getAbsoluteAdapterPosition()), false);
                    } else {
                        binding.progressBarView.setVisibility(View.VISIBLE);
                        binding.tvView.setVisibility(View.GONE);
                        onClickListener.onClickViewDetail(chatMessage.get(getAbsoluteAdapterPosition()), true);
                    }
                }
            });

            binding.loutOffer.setOnClickListener(v -> {
                if (chatMessage.get(getAbsoluteAdapterPosition()).offer.offerStatus == 2 || chatMessage.get(getAbsoluteAdapterPosition()).offer.offerStatus == 5) {
                    baseActivity.setEnableDisableView(binding.loutView);
                    binding.progressBarViewAccepted.setVisibility(View.VISIBLE);
                    binding.tvView.setVisibility(View.GONE);
                    onClickListener.onClickViewDetail(chatMessage.get(getAbsoluteAdapterPosition()), false);
                }
            });
        }
    }
}
