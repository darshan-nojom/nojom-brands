package com.nojom.client.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nojom.client.R;
import com.nojom.client.model.Profile;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Utils;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> {

    private List<Profile> timelineItems;
    private BaseActivity activity;
    private final PrettyTime p = new PrettyTime();

    public TimelineAdapter(BaseActivity activity, List<Profile> timelineItems) {
        this.timelineItems = timelineItems;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeline_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Profile item = timelineItems.get(position);

        holder.userName.setText(item.firstName + " " + item.lastName);
        if (item.categories != null && item.categories.size() > 0) {
            holder.userDesignation.setText(item.categories.get(0).getName(activity.getLanguage()));
        }
        holder.timelineDate.setText("22 May");

        Glide.with(activity).load(item.profile_picture).error(R.color.orange).into(holder.imgProfile);

        //Change circle color based on approval status
        if (!TextUtils.isEmpty(item.req_status)) {
            if (item.req_status.equals("pending")) {
                holder.statusText.setText(R.string.your_request_is_pending);
                holder.timelineCircle.setBackgroundResource(R.drawable.orange_circle);
                holder.timelineLine.setBackgroundColor(activity.getResources().getColor(R.color.orange));
            } else {
                holder.statusText.setText("Your request is approved");
                holder.timelineCircle.setBackgroundResource(R.drawable.green_circle);
                holder.timelineLine.setBackgroundColor(activity.getResources().getColor(R.color.orange));
            }
        } else {
            holder.statusText.setText("Your request is pending");
            holder.timelineCircle.setBackgroundResource(R.drawable.orange_circle);
            holder.timelineLine.setBackgroundColor(activity.getResources().getColor(R.color.orange));
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
                holder.timelineDate.setText("" + result);
            } else {
                String finalDate = dfFinal2.format(date1);
                holder.timelineDate.setText("" + finalDate);
            }
        }

        if (timelineItems.size() - 1 == position) {
            holder.timelineLine.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return timelineItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView statusText, userName, userDesignation, timelineDate;
        View timelineCircle, timelineLine;
        CircleImageView imgProfile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            statusText = itemView.findViewById(R.id.status_text);
            userName = itemView.findViewById(R.id.user_name);
            userDesignation = itemView.findViewById(R.id.user_designation);
            timelineDate = itemView.findViewById(R.id.timeline_date);
            timelineCircle = itemView.findViewById(R.id.timeline_circle);
            timelineLine = itemView.findViewById(R.id.timeline_line);
            imgProfile = itemView.findViewById(R.id.img_profile);
        }
    }
}
