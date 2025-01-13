package com.nojom.client.adapter;

import static com.nojom.client.util.Constants.API_DELETE_JOB_POST;
import static com.nojom.client.util.Constants.API_JOB_DETAILS;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.daimajia.swipe.SwipeLayout;
import com.nojom.client.R;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ItemCampaignInprogressBinding;
import com.nojom.client.fragment.projects.CampaignListFragment;
import com.nojom.client.model.CampList;
import com.nojom.client.model.ExpertDetail;
import com.nojom.client.model.ExpertLawyers;
import com.nojom.client.model.Profile;
import com.nojom.client.model.ProjectByID;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.clientprofile.PostJobActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import org.jetbrains.annotations.NotNull;
import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class CampaignAdapter2 extends RecyclerView.Adapter<CampaignAdapter2.SimpleViewHolder> implements RequestResponseListener, BaseActivity.ExpertInfoListener {

    private final CampaignListFragment context;
    private List<CampList> projectsList;
    private final PrettyTime p = new PrettyTime();
    private final BaseActivity activity;
    private SwipeLayout swipeLayout;
    private int adapterPosition;
    private final OnClickJobListener onClickJobListener;

    private int selectedTab = 0;

    public void setSelectedTab(int selectedTab) {
        this.selectedTab = selectedTab;
    }

    public CampaignAdapter2(CampaignListFragment context, OnClickJobListener onClickJobListener) {
        this.context = context;
        this.onClickJobListener = onClickJobListener;
        activity = (BaseActivity) context.getContext();
    }

    @Override
    public void onExpertSuccess(ExpertDetail expertDetail) {
        Preferences.writeString(activity, Constants.PLATFORM_ID, expertDetail.serviceId + "");
        Preferences.writeString(activity, Constants.PLATFORM_NAME, expertDetail.serviceName + "");
        ArrayList<ExpertLawyers.Data> expertUsers = new ArrayList<>();
        expertUsers.add(new ExpertLawyers.Data(expertDetail.profileId, expertDetail.firstName + " " + expertDetail.lastName));
        Preferences.setExpertUsers(activity, expertUsers);
        activity.gotoMainActivity(Constants.TAB_POST_JOB);
        getData().get(adapterPosition).isShowProgress = false;
        notifyItemChanged(adapterPosition);
    }

    @Override
    public void onExpertFail() {
        Preferences.setExpertUsers(activity, null);
        getData().get(adapterPosition).isShowProgress = false;
        notifyItemChanged(adapterPosition);
    }

    @Override
    public void onPreExpert() {
        Preferences.setExpertUsers(activity, null);
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
        ItemCampaignInprogressBinding itemProjectsListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_campaign_inprogress, parent, false);
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

        if (selectedTab == 2) {
            holder.binding.linDate.setVisibility(View.GONE);
            holder.binding.txtStatus.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linDate.setVisibility(View.VISIBLE);
            holder.binding.txtStatus.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(item.campaignTitle)) {
            holder.binding.tvReceiverName.setText(item.campaignTitle);
        } else if (!TextUtils.isEmpty(item.jp_title)) {
            holder.binding.tvReceiverName.setText(item.jp_title);
        }

        if (item.campaignId != null) {
            holder.binding.tvDate.setText(String.format(Locale.US, "#" + activity.getString(R.string.jobid) + " : %s", item.campaignId));
        } else if (item.jp_id != null) {
            holder.binding.tvDate.setText(String.format(Locale.US, activity.getString(R.string.jobid) + " : %s", item.jp_id));
        }

        if (item.profiles != null && item.profiles.size() > 0) {
            holder.binding.txtAgents.setText(item.profiles.size() + " " + activity.getString(R.string.stars));
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
        if (!TextUtils.isEmpty(item.campaignBrief)) {
            holder.binding.txtPaid.setText(item.campaignBrief);
        }

//        if (item.job.contains("gig")) {
        if (item.totalPrice != 0 && item.totalPrice > 0) {
            holder.binding.txtPrice.setText(activity.getCurrency().equals("SAR") ? Utils.decimalFormat(String.valueOf(item.totalPrice)) + "" : Utils.decimalFormat(String.valueOf(item.totalPrice)) + "");
        } else {

            if (item.client_rate_id == 0 && item.budget != null && item.budget != 0) {
                holder.binding.txtPrice.setText(activity.getCurrency().equals("SAR") ? Utils.decimalFormat(String.valueOf(item.budget)) + "" : Utils.decimalFormat(String.valueOf(item.budget)) + "");
            } else {
                if (item.cr_id != null && item.cr_id != -1) {
                    if (item.range_to != null && item.range_from != null) {
                        if (activity.getCurrency().equals("SAR")) {
                            holder.binding.txtPrice.setText(String.format(Locale.US, activity.getString(R.string.s_sar_s_sar), Utils.decimalFormat(String.valueOf(item.range_from)), Utils.decimalFormat(String.valueOf(item.range_to))));
                        } else {
                            holder.binding.txtPrice.setText(String.format(Locale.US, "$%s - $%s", Utils.decimalFormat(String.valueOf(item.range_from)), Utils.decimalFormat(String.valueOf(item.range_to))));
                        }
                    } else if (item.range_from != null) {
                        if (activity.getCurrency().equals("SAR")) {
                            holder.binding.txtPrice.setText(Utils.decimalFormat(String.valueOf(item.range_from)));
                        } else {
                            holder.binding.txtPrice.setText(String.format(Locale.US, "$%s", Utils.decimalFormat(String.valueOf(item.range_from))));
                        }
                    } else if (item.budget != null) {
                        holder.binding.txtPrice.setText(activity.getCurrency().equals("SAR") ? Utils.decimalFormat(String.valueOf(item.budget)) + "" : "$" + Utils.decimalFormat(String.valueOf(item.budget)) + "");
                    }
                } else {
                    holder.binding.txtPrice.setText(activity.getString(R.string.free));
                }
            }
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
                holder.binding.txtDate.setText("Due Date: " + result);
            } else {
                String finalDate = dfFinal2.format(date1);
                holder.binding.txtDate.setText("Due Date: " + finalDate);
            }
        }

        if (item.campaignStatus != null) {
            switch (item.campaignStatus) {
                case "":
//                    holder.binding.llRehire.setVisibility(View.GONE);
//                    holder.binding.llDelete.setVisibility(View.VISIBLE);
//                    holder.binding.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.yellow_border_5));
//                    holder.binding.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.yellow));
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
                    holder.binding.txtStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.blue_border_pro_5));
                    holder.binding.txtStatus.setTextColor(ContextCompat.getColor(activity, R.color.colorPrimary));
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
                    holder.binding.txtStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.red_border_5));
                    holder.binding.txtStatus.setTextColor(ContextCompat.getColor(activity, R.color.reset_pw_btn));
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
                    holder.binding.txtStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.ltgreen_bg_50));
                    holder.binding.txtStatus.setTextColor(ContextCompat.getColor(activity, R.color.C_34C759));
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
                    holder.binding.txtStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.ltred_bg_50));
                    holder.binding.txtStatus.setTextColor(ContextCompat.getColor(activity, R.color.C_FF3B30));
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
        } else {

            if ((item.type).equals("paid")) {
//                holder.binding.llEdit.setVisibility(View.GONE);
//                holder.binding.llRehire.setVisibility(View.GONE);
//                holder.binding.llDelete.setVisibility(View.GONE);
//                holder.binding.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.blue_border_pro_5));
//                holder.binding.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.colorPrimary));
            } else {
//                holder.binding.llRehire.setVisibility(View.VISIBLE);
//                holder.binding.llEdit.setVisibility(View.GONE);
//                holder.binding.llDelete.setVisibility(View.GONE);
//                holder.binding.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.black_gray_border_5));
//                holder.binding.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.gray_text));
            }
        }


        if (item.campaignStatus != null && !TextUtils.isEmpty(item.campaignStatus)) {
            holder.binding.txtStatus.setText(capitalizeWords(item.campaignStatus));
        } else if (item.name != null) {
            holder.binding.txtStatus.setText(item.getStatusName(activity));
        }
    }

    @Override
    public int getItemCount() {
        return projectsList != null ? projectsList.size() : 0;
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        try {
            if (url.equalsIgnoreCase(API_DELETE_JOB_POST)) {
                //mItemManger.removeShownLayouts(swipeLayout);
                getData().get(adapterPosition).isShowProgress = false;
                projectsList.remove(adapterPosition);
                notifyItemRemoved(adapterPosition);
                notifyItemRangeChanged(adapterPosition, projectsList.size());
                //mItemManger.closeAllItems();
                activity.toastMessage(message);
            } else if (url.equalsIgnoreCase(API_JOB_DETAILS)) {
                ProjectByID project = ProjectByID.getProjectById(responseBody);

                ArrayList<ExpertLawyers.Data> expertUsers = new ArrayList<>();
                if (project.jobOffers != null) {
                    for (int i = 0; i < project.jobOffers.size(); i++) {
                        expertUsers.add(new ExpertLawyers.Data(project.jobOffers.get(i).profileId, project.jobOffers.get(i).firstName + " " + project.jobOffers.get(i).lastName));
                    }
                }

                Preferences.setExpertUsers(activity, expertUsers);

                boolean isDuplicate = Preferences.readBoolean(activity, Constants.DUPLICATE_PROJECT, false);

                getData().get(adapterPosition).isShowProgress = false;
                notifyItemChanged(adapterPosition);

                Intent i = new Intent(activity, PostJobActivity.class);
                if (isDuplicate) {
                    i.putExtra(Constants.DUPLICATE_PROJECT, true);
                } else {
                    i.putExtra(Constants.IS_EDIT, true);
                }
                i.putExtra(Constants.PROJECT, project);
                context.startActivity(i);
            }
            activity.isClickableView = false;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        activity.isClickableView = false;
        if (url.equalsIgnoreCase(API_JOB_DETAILS) || url.equalsIgnoreCase(API_DELETE_JOB_POST)) {
            getData().get(adapterPosition).isShowProgress = false;
            notifyItemChanged(adapterPosition);
            Preferences.writeBoolean(activity, Constants.DUPLICATE_PROJECT, false);
        }
    }

    private void showDeleteDialog(final int projectId, final SwipeLayout swipeLayout, final int position) {
        final Dialog dialog = new Dialog(activity, R.style.Theme_AppCompat_Dialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_delete_project);
        dialog.setCancelable(true);

        TextView tvMessage = dialog.findViewById(R.id.tv_message);
        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        TextView tvChatnow = dialog.findViewById(R.id.tv_chat_now);

        tvMessage.setText(Utils.fromHtml(context.getString(R.string.delete_project_text)));

        tvCancel.setText(context.getString(R.string.no));
        tvChatnow.setText(context.getString(R.string.yes));
        tvCancel.setOnClickListener(v -> dialog.dismiss());

        tvChatnow.setOnClickListener(v -> {
            dialog.dismiss();
            deleteProject(projectId, swipeLayout, position);
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.gravity = Gravity.CENTER;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }

    private void deleteProject(int projectId, final SwipeLayout swipeLayout, final int position) {
        if (activity == null || !activity.isNetworkConnected()) return;

        this.swipeLayout = swipeLayout;
        this.adapterPosition = position;

        activity.isClickableView = true;
        getData().get(adapterPosition).isShowProgress = true;
        notifyItemChanged(adapterPosition);

        HashMap<String, String> map = new HashMap<>();
        map.put("job_post_id", projectId + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_DELETE_JOB_POST, true, map);
    }

    private void showRepostDialog(final int projectId, final SwipeLayout swipeLayout, final int position) {
        @SuppressLint("PrivateResource") final Dialog dialog = new Dialog(activity, R.style.Theme_Design_Light_BottomSheetDialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_repost_delete_job);
        dialog.setCancelable(true);
        TextView tvCancel = dialog.findViewById(R.id.btn_cancel);
        TextView btnDelete = dialog.findViewById(R.id.btn_delete);
        TextView btnRepost = dialog.findViewById(R.id.btn_repost);

        btnDelete.setOnClickListener(v -> {
            deleteProject(projectId, swipeLayout, position);
            dialog.dismiss();
        });

        btnRepost.setOnClickListener(v -> {
            Intent i = new Intent(activity, PostJobActivity.class);
            i.putExtra(Constants.REPOST_PROJECT, true);
            i.putExtra(Constants.PROJECT_ID, projectId);
            context.startActivity(i);
            dialog.dismiss();
        });

        tvCancel.setOnClickListener(v -> dialog.dismiss());

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }

    private void getProjectById(int projectId) {
        if (!activity.isNetworkConnected()) {
            return;
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("job_id", projectId + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_JOB_DETAILS, true, map);
    }

    public interface OnClickJobListener {
        void onClickJob(int jpId, int position, String jobType, String gigType, CampList campList);
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemCampaignInprogressBinding binding;

        SimpleViewHolder(ItemCampaignInprogressBinding itemView) {
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

            if (i == 4) {
                break;
            }
        }

        if (imageRes.size() > 4) {
            TextView textView = new TextView(activity);
            textView.setText("+" + (imageRes.size() - 4));
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
