package com.nojom.client.fragment.chat;

import static com.nojom.client.adapter.CampaignAdapter2.capitalizeWords;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.adapter.PlatformDetailAdapter;
import com.nojom.client.adapter.TimelineAdapter;
import com.nojom.client.databinding.FragmentCampDetailBinding;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.model.CampList;
import com.nojom.client.ui.projects.CampaignDetailActivity2;
import com.nojom.client.util.Utils;

import org.jetbrains.annotations.NotNull;
import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CampDetailFragment extends BaseFragment {
    private FragmentCampDetailBinding binding;
    private CampList campList;
    private final PrettyTime p = new PrettyTime();

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_camp_detail, container, false);
        return binding.getRoot();
    }

    private void renderView() {
        binding.txtCampaignTitle.setText("" + campList.campaignTitle);
        if (campList.profiles != null && campList.profiles.size() > 0) {
            binding.txtStars.setText(campList.profiles.size() + " " + activity.getString(R.string.stars));

            //bind adapter
            TimelineAdapter adapter = new TimelineAdapter(activity, campList.profiles);
            binding.rvTracks.setAdapter(adapter);
        }
        Date date1 = Utils.changeDateFormat("yyyy-MM-dd'T'hh:mm:ss", campList.timestamp);
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dfFinal2;
        if (activity.getLanguage().equals("ar")) {
            dfFinal2 = new SimpleDateFormat("dd MMM,yyyy");
        } else {
            dfFinal2 = new SimpleDateFormat("MMM dd,yyyy");
        }


        if (date1 != null) {
            if (activity.printDifference(date1, date).equalsIgnoreCase("0")) {
                String result = p.format(Utils.changeDateFormat("yyyy-MM-dd'T'hh:mm:ss", campList.timestamp));
                binding.txtDate.setText(getString(R.string.due_date) + result);
            } else {
                String finalDate = dfFinal2.format(date1);
                binding.txtDate.setText(getString(R.string.due_date) + finalDate);
            }

            String[] time = campList.timestamp.split("T")[1].split(":");
            binding.txtTime.setText(time[0] + ":" + time[1]);
        }

        if (!TextUtils.isEmpty(campList.campaignBrief)) {
            binding.txtDetails.setText(campList.campaignBrief);
        }

        if (campList.campaignId != null) {
            binding.txtCampId.setText(String.format(Locale.US, "#%s", campList.campaignId));
        } else if (campList.jp_id != null) {
            binding.txtCampId.setText(String.format(Locale.US, "%s", campList.jp_id));
        }

        if (campList.socialPlatforms != null && campList.socialPlatforms.size() > 0) {
            PlatformDetailAdapter adapter = new PlatformDetailAdapter(activity, campList.socialPlatforms);
            binding.rvPlatform.setAdapter(adapter);
        }

        if (!TextUtils.isEmpty(campList.campaignStatus)) {
//            binding.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.yellow_bg_20));
//            binding.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.black));
            binding.tvStatus.setText(capitalizeWords(campList.campaignStatus));
        }

        if (!TextUtils.isEmpty(campList.campaignAttachmentUrl)) {
            String fName = getFileNameFromUrl(campList.campaignAttachmentUrl);
            binding.txtFileName.setText("" + fName + " " + fName.length() + " " + getString(R.string.kb));
        }

        if (campList.campaignStatus != null) {
            switch (campList.campaignStatus) {
                case "pending":
//                    holder.binding.llRehire.setVisibility(View.GONE);
//                    holder.binding.llDelete.setVisibility(View.VISIBLE);
                    binding.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.yellow_bg_20));
                    binding.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.white));
//                    holder.binding.tvRefunds.setVisibility(View.GONE);
//                    holder.binding.llEdit.setVisibility(View.VISIBLE);
//                    break;
//                case Constants.WAITING_FOR_AGENT_ACCEPTANCE:
//                    holder.binding.llEdit.setVisibility(View.GONE);
//                    holder.binding.llDelete.setVisibility(View.GONE);
//                    holder.binding.llRehire.setVisibility(View.GONE);
//                    holder.binding.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.lovender_border_5));
//                    holder.binding.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.lovender));
//                    holder.binding.tvRefunds.setVisibility(View.GONE);
//                    break;
                case "in_progress":
//                    holder.binding.llEdit.setVisibility(View.GONE);
//                    holder.binding.llRehire.setVisibility(View.GONE);
//                    holder.binding.llDelete.setVisibility(View.GONE);
                    binding.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.blue_bg_20));
                    binding.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.white));
//                    if (item.jr_id != null) {//refund case
//                        holder.binding.tvRefunds.setVisibility(View.VISIBLE);
//                    } else {
//                        holder.binding.tvRefunds.setVisibility(View.GONE);
//                    }
                    break;
                case "Deposit":
//                    holder.binding.llRehire.setVisibility(View.GONE);
//                    holder.binding.llEdit.setVisibility(View.GONE);
//                    holder.binding.llDelete.setVisibility(View.GONE);
                    binding.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.red_bg_20));
                    binding.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.white));
//                    holder.binding.tvRefunds.setVisibility(View.GONE);
                    break;
//                case Constants.SUBMIT_WAITING_FOR_PAYMENT:
//                    holder.binding.llRehire.setVisibility(View.GONE);
//                    holder.binding.llEdit.setVisibility(View.GONE);
//                    holder.binding.llDelete.setVisibility(View.GONE);
//                    holder.binding.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.green_border_5));
//                    holder.binding.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.greendark));
//                    if (item.jr_id != null) {//refund case
//                        holder.binding.tvRefunds.setVisibility(View.VISIBLE);
//                    } else {
//                        holder.binding.tvRefunds.setVisibility(View.GONE);
//                    }
//                    break;
                case "completed":
//                    holder.binding.llRehire.setVisibility(View.VISIBLE);
//                    holder.binding.llEdit.setVisibility(View.GONE);
//                    holder.binding.llDelete.setVisibility(View.GONE);
                    binding.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.ltgreen_bg_50));
                    binding.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.C_34C759));
//                    if (item.jr_id != null) {//refund case
//                        holder.binding.tvRefunds.setVisibility(View.VISIBLE);
//                    } else {
//                        holder.binding.tvRefunds.setVisibility(View.GONE);
//                    }
                    break;
                case "canceled":
//                    holder.binding.llRehire.setVisibility(View.VISIBLE);
//                    holder.binding.llEdit.setVisibility(View.GONE);
//                    holder.binding.llDelete.setVisibility(View.GONE);
                    binding.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.ltred_bg_50));
                    binding.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.C_FF3B30));
//                    holder.binding.tvRefunds.setVisibility(View.GONE);
                    break;
//                case Constants.REFUNDED:
//                    holder.binding.llRehire.setVisibility(View.VISIBLE);
//                    holder.binding.llEdit.setVisibility(View.GONE);
//                    holder.binding.llDelete.setVisibility(View.GONE);
//                    holder.binding.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.orange_border_5));
//                    holder.binding.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.orange_light));
//                    holder.binding.tvRefunds.setVisibility(View.GONE);
//                    break;
//                case Constants.REMOVED:
//                    holder.binding.llRehire.setVisibility(View.GONE);
//                    holder.binding.llEdit.setVisibility(View.GONE);
//                    holder.binding.llDelete.setVisibility(View.GONE);
//                    holder.binding.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.black_border_5));
//                    holder.binding.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.black));
//                    holder.binding.tvRefunds.setVisibility(View.GONE);
//                    break;
//                case UNDER_REVIEW:
//                    holder.binding.llRehire.setVisibility(View.GONE);
//                    holder.binding.llEdit.setVisibility(View.GONE);
//                    holder.binding.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.pink_border_5));
//                    holder.binding.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.pink_dark));
//                    holder.binding.tvRefunds.setVisibility(View.GONE);
//                    holder.binding.swipe.setSwipeEnabled(false);
//                    break;
//                case BANK_TRANSFER_REVIEW:
//                    holder.binding.llRehire.setVisibility(View.GONE);
//                    holder.binding.llEdit.setVisibility(View.GONE);
//                    holder.binding.llDelete.setVisibility(View.GONE);
//                    holder.binding.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.orange_border_5));
//                    holder.binding.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.orange_light));
//                    holder.binding.tvRefunds.setVisibility(View.GONE);
//                    break;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        campList = ((CampaignDetailActivity2) activity).campList;
        renderView();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private String getFileNameFromUrl(String url) {
        // Use Uri class to parse the URL
        Uri uri = Uri.parse(url);

        // Get the last segment of the path, which is the file name
        return uri.getLastPathSegment();
    }
}
