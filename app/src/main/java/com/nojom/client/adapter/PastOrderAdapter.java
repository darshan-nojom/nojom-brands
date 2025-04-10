package com.nojom.client.adapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nojom.client.R;
import com.nojom.client.databinding.ItemOrdersBinding;
import com.nojom.client.model.CampList;
import com.nojom.client.model.Profile;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Utils;

import org.jetbrains.annotations.NotNull;
import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PastOrderAdapter extends RecyclerView.Adapter<PastOrderAdapter.SimpleViewHolder> {

    private final BaseActivity activity;
    private List<CampList> projectsList;
    private final PrettyTime p = new PrettyTime();
    private final OnClickJobListener onClickJobListener;

    public PastOrderAdapter(BaseActivity context, OnClickJobListener onClickJobListener) {
        this.activity = context;
        this.onClickJobListener = onClickJobListener;
    }

    public void doRefresh(List<CampList> projectsList) {
        int curSize = getItemCount();
        this.projectsList = projectsList;
        notifyItemRangeInserted(curSize, projectsList.size() - 1);
    }

    public void initList(List<CampList> projectsList) {
        this.projectsList = projectsList;
        notifyDataSetChanged();
    }

    public List<CampList> getData() {
        return projectsList;
    }

    @NotNull
    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemOrdersBinding itemProjectsListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_orders, parent, false);
        return new SimpleViewHolder(itemProjectsListBinding);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {
        CampList item = projectsList.get(position);

        if (item.isShowProgress) {
//            holder.binding.rlItemview.setBackgroundResource(R.drawable.transp_rounded_corner_10);
//            holder.binding.progressBar.setVisibility(View.VISIBLE);
        } else {
//            holder.binding.rlItemview.setBackgroundResource(R.drawable.white_rounded_corner_10);
//            holder.binding.progressBar.setVisibility(View.GONE);
            item.isShowProgress = false;
        }

        holder.binding.tvTitle.setText(item.campaignTitle);

        if (item.totalPrice > 0) {
            holder.binding.txtPrice.setText(Utils.decimalFormat(String.valueOf(item.totalPrice)) + " " + activity.getString(R.string.sar));
        } else {
            holder.binding.txtPrice.setText("0 " + activity.getString(R.string.sar));
        }

        holder.binding.txtCampId.setText("#" + item.campaignId);

        if (item.profiles != null && item.profiles.size() > 0) {
            holder.binding.imageContainer.removeAllViews();
            addOverlappingImages(holder.binding.imageContainer, item.profiles);
        } else {
            holder.binding.imageContainer.removeAllViews();
            Profile profile = new Profile();
            profile.profile_picture = "test";
            List<Profile> profileList = new ArrayList<>();
            profileList.add(profile);
            addOverlappingImages(holder.binding.imageContainer, profileList);
        }

        Date date1 = Utils.changeDateFormat("yyyy-MM-dd'T'hh:mm:ss", item.timestamp);
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dfFinal2;
        if (activity.getLanguage().equals("ar")) {
            dfFinal2 = new SimpleDateFormat("dd MMM,yyyy");
        } else {
            dfFinal2 = new SimpleDateFormat("MMM dd,yyyy");
        }

        if (date1 != null) {
            if (activity.printDifference(date1, date).equalsIgnoreCase("0")) {
                String result = p.format(Utils.changeDateFormat("yyyy-MM-dd'T'hh:mm:ss", item.timestamp));
                holder.binding.txtDate.setText(activity.getString(R.string.due_date) + result);
            } else {
                String finalDate = dfFinal2.format(date1);
                holder.binding.txtDate.setText(activity.getString(R.string.due_date) + finalDate);
            }
        }

        if (item.campaignStatus != null) {
            switch (item.campaignStatus) {
                case "approved", "in_progress" -> {
                    holder.binding.txtStatus.setText(activity.getString(R.string.approved_pending_your_progress));
                    holder.binding.txtStatus.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.C_D4E4FA)));
                    holder.binding.txtStatus.setTextColor(ContextCompat.getColor(activity, R.color.C_007AFF));
                    holder.binding.progress1.setProgressTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.C_007AFF)));
                    holder.binding.progress2.setProgressTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.C_007AFF)));
                    holder.binding.progress3.setProgressTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.C_F2F2F7)));
                    holder.binding.progress4.setProgressTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.C_F2F2F7)));
                    holder.binding.progress1.setProgress(100);
                    holder.binding.progress2.setProgress(100);
                    holder.binding.progress3.setProgress(0);
                    holder.binding.progress4.setProgress(0);
                }
                case "pending" -> {
                    holder.binding.txtStatus.setText(activity.getString(R.string.new_request_pending_approval));
                    holder.binding.txtStatus.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.C_F2F2F7)));
                    holder.binding.txtStatus.setTextColor(ContextCompat.getColor(activity, R.color.black));
                    holder.binding.progress1.setProgressTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.black)));
                    holder.binding.progress2.setProgressTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.C_F2F2F7)));
                    holder.binding.progress3.setProgressTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.C_F2F2F7)));
                    holder.binding.progress4.setProgressTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.C_F2F2F7)));
                    holder.binding.progress1.setProgress(100);
                    holder.binding.progress2.setProgress(0);
                    holder.binding.progress3.setProgress(0);
                    holder.binding.progress4.setProgress(0);
                }
                case "completed" -> {
                    if (item.profiles != null && item.profiles.get(0).is_released) {
                        holder.binding.txtStatus.setText(activity.getString(R.string.approved_completed));
                        holder.binding.txtStatus.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.C_C7591A)));
                        holder.binding.txtStatus.setTextColor(ContextCompat.getColor(activity, R.color.C_34C759));
                        holder.binding.progress1.setProgressTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.C_34C759)));
                        holder.binding.progress2.setProgressTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.C_34C759)));
                        holder.binding.progress3.setProgressTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.C_34C759)));
                        holder.binding.progress4.setProgressTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.C_34C759)));
                        holder.binding.progress1.setProgress(100);
                        holder.binding.progress2.setProgress(100);
                        holder.binding.progress3.setProgress(100);
                        holder.binding.progress4.setProgress(100);
                    } else {
                        holder.binding.txtStatus.setText(activity.getString(R.string.done_pending_client_review));
                        holder.binding.txtStatus.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.C_FF95001A)));
                        holder.binding.txtStatus.setTextColor(ContextCompat.getColor(activity, R.color.C_FF9500));
                        holder.binding.progress1.setProgressTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.C_FF9500)));
                        holder.binding.progress2.setProgressTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.C_FF9500)));
                        holder.binding.progress3.setProgressTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.C_FF9500)));
                        holder.binding.progress4.setProgressTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.C_F2F2F7)));
                        holder.binding.progress1.setProgress(100);
                        holder.binding.progress2.setProgress(100);
                        holder.binding.progress3.setProgress(100);
                        holder.binding.progress4.setProgress(0);
                    }
                }
                case "canceled", "rejected" -> {
                    holder.binding.txtStatus.setText(activity.getString(R.string.request_declined_cancelled));
                    holder.binding.txtStatus.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.C_FF3B30_10)));
                    holder.binding.txtStatus.setTextColor(ContextCompat.getColor(activity, R.color.C_FF3B30));
                    holder.binding.progress1.setProgressTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.C_FF3B30)));
                    holder.binding.progress2.setProgressTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.C_FF3B30)));
                    holder.binding.progress3.setProgressTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.C_FF3B30)));
                    holder.binding.progress4.setProgressTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.C_FF3B30)));
                    holder.binding.progress1.setProgress(100);
                    holder.binding.progress2.setProgress(100);
                    holder.binding.progress3.setProgress(100);
                    holder.binding.progress4.setProgress(100);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return projectsList != null ? projectsList.size() : 0;
    }


    public interface OnClickJobListener {
        void onClickJob(int jpId, int position, String jobType, String gigType, CampList campList);
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemOrdersBinding binding;

        SimpleViewHolder(ItemOrdersBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            itemView.getRoot().setOnClickListener(view -> {
                if (onClickJobListener != null) {
                    onClickJobListener.onClickJob(0, getAdapterPosition(), "", "", projectsList.get(getAdapterPosition()));
                }
            });
        }
    }

    public static String capitalizeWords(String input) {
        // split the input string into an array of words
        String[] words = input.split("\\s");

        // StringBuilder to store the result
        StringBuilder result = new StringBuilder();

        // iterate through each word
        for (String word : words) {
            // capitalize the first letter, append the rest of the word, and add a space
            result.append(Character.toTitleCase(word.charAt(0))).append(word.substring(1)).append(" ");
        }

        // convert StringBuilder to String and trim leading/trailing spaces
        return result.toString().trim();
    }

    private void addOverlappingImages(LinearLayout container, List<Profile> imageRes) {
        int overlapOffset = -5; // Adjust the overlap offset in dp
        int size = 40; // Circle size in dp

        // Convert dp to pixels
        float scale = activity.getResources().getDisplayMetrics().density;
        int offsetPx = (int) (overlapOffset * scale);
        int sizePx = (int) (size * scale);

        for (int i = 0; i < imageRes.size(); i++) {
            CircleImageView imageView = new CircleImageView(activity);
//            imageView.setImageResource(imageRes.get(i).image.path+imageRes.get(i).image.fileName);
            Glide.with(activity).load(imageRes.get(i).profile_picture).placeholder(R.drawable.dp).error(R.drawable.dp).into(imageView);
            // Set circular shape
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setBorderColor(Color.WHITE);
            imageView.setBorderWidth(3);
            //imageView.setBackgroundResource(R.drawable.circle_round_gray); // Circular shape drawable


            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(sizePx, sizePx);
            if (i != 0) {
                params.leftMargin = -30; // Overlap the images
            }
            imageView.setLayoutParams(params);

            container.addView(imageView);

            if (i == 2) {
                break;
            }
        }

        if (imageRes.size() > 3) {
            TextView textView = new TextView(activity);
            textView.setText("+" + (imageRes.size() - 3));
            textView.setTextColor(Color.WHITE);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.BLACK);
            textView.setBackgroundResource(R.drawable.circle_round_gray);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(sizePx, sizePx);
            params.leftMargin = -30; // Position after the last image
            textView.setLayoutParams(params);

            container.addView(textView);
        }
    }
}
