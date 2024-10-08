package com.nojom.client.adapter;

import static com.nojom.client.util.Constants.API_DELETE_JOB_POST;
import static com.nojom.client.util.Constants.API_JOB_DETAILS;
import static com.nojom.client.util.Constants.BANK_TRANSFER_REVIEW;
import static com.nojom.client.util.Constants.UNDER_REVIEW;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.nojom.client.R;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ItemProjectsListBinding;
import com.nojom.client.fragment.projects.ProjectsListFragment;
import com.nojom.client.model.ExpertDetail;
import com.nojom.client.model.ExpertLawyers;
import com.nojom.client.model.ProjectByID;
import com.nojom.client.model.Projects;
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

public class ProjectsAdapter extends RecyclerSwipeAdapter<ProjectsAdapter.SimpleViewHolder> implements RequestResponseListener, BaseActivity.ExpertInfoListener {

    private final ProjectsListFragment context;
    private List<Projects.Data> projectsList;
    private final PrettyTime p = new PrettyTime();
    private final BaseActivity activity;
    private SwipeLayout swipeLayout;
    private int adapterPosition;
    private final OnClickJobListener onClickJobListener;

    public ProjectsAdapter(ProjectsListFragment context, OnClickJobListener onClickJobListener) {
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

    public void doRefresh(List<Projects.Data> projectsList) {
        int curSize = getItemCount();
        this.projectsList = projectsList;
        notifyItemRangeInserted(curSize, projectsList.size() - 1);
    }

    public void initList(List<Projects.Data> projectsList) {
        this.projectsList = projectsList;
        notifyDataSetChanged();
    }

    public List<Projects.Data> getData() {
        return projectsList;
    }

    @NotNull
    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemProjectsListBinding itemProjectsListBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_projects_list, parent, false);
        return new SimpleViewHolder(itemProjectsListBinding);
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {
        Projects.Data item = projectsList.get(position);
        holder.binding.swipe.setShowMode(SwipeLayout.ShowMode.PullOut);

        if (item.isShowProgress) {
            holder.binding.rlItemview.setBackgroundResource(R.drawable.transp_rounded_corner_10);
            holder.binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            holder.binding.rlItemview.setBackgroundResource(R.drawable.white_rounded_corner_10);
            holder.binding.progressBar.setVisibility(View.GONE);
            item.isShowProgress = false;
        }

        holder.binding.tvTitle.setText(item.jpTitle);
        holder.binding.tvJobId.setText(String.format(Locale.US, activity.getString(R.string.jobid) + " : %s", item.jpId));
        if (item.job.contains("gig")) {
            holder.binding.tvBids.setVisibility(View.GONE);
            holder.binding.tvDotBids.setVisibility(View.GONE);
            holder.binding.swipe.setSwipeEnabled(false);
            if (item.budget != null && item.budget != 0) {
                holder.binding.tvBudget.setText(activity.getCurrency().equals("SAR") ? Utils.decimalFormat(String.valueOf(item.budget)) + " " + activity.getString(R.string.sar)
                        : activity.getString(R.string.dollar) + Utils.decimalFormat(String.valueOf(item.budget)) + "");
            } else {
                holder.binding.tvBudget.setText(activity.getString(R.string.free));
            }
        } else {
            holder.binding.tvBids.setVisibility(View.VISIBLE);
            holder.binding.tvDotBids.setVisibility(View.VISIBLE);
            holder.binding.tvBids.setText(item.bidsCount > 1 ? item.bidsCount + " " + activity.getString(R.string.bids) : item.bidsCount + " " + activity.getString(R.string.bid));
            holder.binding.swipe.setSwipeEnabled(true);

            if (item.clientRateId == 0 && item.budget != null && item.budget != 0) {
                holder.binding.tvBudget.setText(activity.getCurrency().equals("SAR") ? Utils.decimalFormat(String.valueOf(item.budget)) + " " + activity.getString(R.string.sar)
                        : activity.getString(R.string.dollar) + Utils.decimalFormat(String.valueOf(item.budget)) + "");
            } else {
                if (item.crId != null && item.crId != -1) {
                    if (item.rangeTo != null && item.rangeFrom != null) {
                        if (activity.getCurrency().equals("SAR")) {
                            holder.binding.tvBudget.setText(String.format(Locale.US, activity.getString(R.string.s_sar_s_sar), Utils.decimalFormat(String.valueOf(item.rangeFrom)), Utils.decimalFormat(String.valueOf(item.rangeTo))));
                        } else {
                            holder.binding.tvBudget.setText(String.format(Locale.US, "$%s - $%s", Utils.decimalFormat(String.valueOf(item.rangeFrom)), Utils.decimalFormat(String.valueOf(item.rangeTo))));
                        }
                    } else if (item.rangeFrom != null) {
                        if (activity.getCurrency().equals("SAR")) {
                            holder.binding.tvBudget.setText(String.format(Locale.US, activity.getString(R.string.s_sar), Utils.decimalFormat(String.valueOf(item.rangeFrom))));
                        } else {
                            holder.binding.tvBudget.setText(String.format(Locale.US, "$%s", Utils.decimalFormat(String.valueOf(item.rangeFrom))));
                        }
                    } else if (item.budget != null) {
                        holder.binding.tvBudget.setText(activity.getCurrency().equals("SAR") ? Utils.decimalFormat(String.valueOf(item.budget)) + " " + activity.getString(R.string.sar)
                                : "$" + Utils.decimalFormat(String.valueOf(item.budget)) + "");
                    }
                } else {
                    holder.binding.tvBudget.setText(activity.getString(R.string.free));
                }
            }
        }

        Date date1 = Utils.changeDateFormat("yyyy-MM-dd'T'hh:mm:ss", item.jpTimestamp);
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dfFinal2;
        if (activity.getLanguage().equals("ar")) {
            dfFinal2 = new SimpleDateFormat("dd MMM,yyyy");
        } else {
            dfFinal2 = new SimpleDateFormat("MMM dd,yyyy");
        }


        if (date1 != null) {
            if (activity.printDifference(date1, date).equalsIgnoreCase("0")) {
                String result = p.format(Utils.changeDateFormat("yyyy-MM-dd'T'hh:mm:ss", item.jpTimestamp));
                holder.binding.tvDaysleft.setText(result);
            } else {
                String finalDate = dfFinal2.format(date1);
                holder.binding.tvDaysleft.setText(finalDate);
            }
        }

        switch (item.jpsId) {
            case Constants.BIDDING:
                holder.binding.llRehire.setVisibility(View.GONE);
                holder.binding.llDelete.setVisibility(View.VISIBLE);
                holder.binding.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.yellow_border_5));
                holder.binding.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.yellow));
                holder.binding.tvRefunds.setVisibility(View.GONE);
                holder.binding.llEdit.setVisibility(View.VISIBLE);
                break;
            case Constants.WAITING_FOR_AGENT_ACCEPTANCE:
                holder.binding.llEdit.setVisibility(View.GONE);
                holder.binding.llDelete.setVisibility(View.GONE);
                holder.binding.llRehire.setVisibility(View.GONE);
                holder.binding.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.lovender_border_5));
                holder.binding.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.lovender));
                holder.binding.tvRefunds.setVisibility(View.GONE);
                break;
            case Constants.IN_PROGRESS:
                holder.binding.llEdit.setVisibility(View.GONE);
                holder.binding.llRehire.setVisibility(View.GONE);
                holder.binding.llDelete.setVisibility(View.GONE);
                holder.binding.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.blue_border_pro_5));
                holder.binding.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                if (item.jrId != null) {//refund case
                    holder.binding.tvRefunds.setVisibility(View.VISIBLE);
                } else {
                    holder.binding.tvRefunds.setVisibility(View.GONE);
                }
                break;
            case Constants.WAITING_FOR_DEPOSIT:
                holder.binding.llRehire.setVisibility(View.GONE);
                holder.binding.llEdit.setVisibility(View.GONE);
                holder.binding.llDelete.setVisibility(View.GONE);
                holder.binding.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.red_border_5));
                holder.binding.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.reset_pw_btn));
                holder.binding.tvRefunds.setVisibility(View.GONE);
                break;
            case Constants.SUBMIT_WAITING_FOR_PAYMENT:
                holder.binding.llRehire.setVisibility(View.GONE);
                holder.binding.llEdit.setVisibility(View.GONE);
                holder.binding.llDelete.setVisibility(View.GONE);
                holder.binding.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.green_border_5));
                holder.binding.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.greendark));
                if (item.jrId != null) {//refund case
                    holder.binding.tvRefunds.setVisibility(View.VISIBLE);
                } else {
                    holder.binding.tvRefunds.setVisibility(View.GONE);
                }
                break;
            case Constants.COMPLETED:
                holder.binding.llRehire.setVisibility(View.VISIBLE);
                holder.binding.llEdit.setVisibility(View.GONE);
                holder.binding.llDelete.setVisibility(View.GONE);
                holder.binding.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.green_border_5));
                holder.binding.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.greendark));
                if (item.jrId != null) {//refund case
                    holder.binding.tvRefunds.setVisibility(View.VISIBLE);
                } else {
                    holder.binding.tvRefunds.setVisibility(View.GONE);
                }
                break;
            case Constants.CANCELLED:
                holder.binding.llRehire.setVisibility(View.VISIBLE);
                holder.binding.llEdit.setVisibility(View.GONE);
                holder.binding.llDelete.setVisibility(View.GONE);
                holder.binding.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.black_gray_border_5));
                holder.binding.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.gray_text));
                holder.binding.tvRefunds.setVisibility(View.GONE);
                break;
            case Constants.REFUNDED:
                holder.binding.llRehire.setVisibility(View.VISIBLE);
                holder.binding.llEdit.setVisibility(View.GONE);
                holder.binding.llDelete.setVisibility(View.GONE);
                holder.binding.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.orange_border_5));
                holder.binding.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.orange_light));
                holder.binding.tvRefunds.setVisibility(View.GONE);
                break;
            case Constants.REMOVED:
                holder.binding.llRehire.setVisibility(View.GONE);
                holder.binding.llEdit.setVisibility(View.GONE);
                holder.binding.llDelete.setVisibility(View.GONE);
                holder.binding.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.black_border_5));
                holder.binding.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.black));
                holder.binding.tvRefunds.setVisibility(View.GONE);
                break;
            case UNDER_REVIEW:
                holder.binding.llRehire.setVisibility(View.GONE);
                holder.binding.llEdit.setVisibility(View.GONE);
                holder.binding.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.pink_border_5));
                holder.binding.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.pink_dark));
                holder.binding.tvRefunds.setVisibility(View.GONE);
                holder.binding.swipe.setSwipeEnabled(false);
                break;
            case BANK_TRANSFER_REVIEW:
                holder.binding.llRehire.setVisibility(View.GONE);
                holder.binding.llEdit.setVisibility(View.GONE);
                holder.binding.llDelete.setVisibility(View.GONE);
                holder.binding.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.orange_border_5));
                holder.binding.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.orange_light));
                holder.binding.tvRefunds.setVisibility(View.GONE);
                break;
        }


        holder.binding.tvStatus.setText(item.getStatusName(activity));

        mItemManger.bindView(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return projectsList != null ? projectsList.size() : 0;
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        try {
            if (url.equalsIgnoreCase(API_DELETE_JOB_POST)) {
                mItemManger.removeShownLayouts(swipeLayout);
                getData().get(adapterPosition).isShowProgress = false;
                projectsList.remove(adapterPosition);
                notifyItemRemoved(adapterPosition);
                notifyItemRangeChanged(adapterPosition, projectsList.size());
                mItemManger.closeAllItems();
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
        if (activity == null || !activity.isNetworkConnected())
            return;

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
        void onClickJob(int jpId, int position, String jobType, String gigType);
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemProjectsListBinding binding;

        SimpleViewHolder(ItemProjectsListBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.llRehire.setOnClickListener(view -> {
                binding.swipe.close();
                try {
                    if (projectsList.get(getAbsoluteAdapterPosition()).agentId > 0) {
                        binding.rlItemview.setBackgroundResource(R.drawable.transp_rounded_corner_10);
                        binding.progressBar.setVisibility(View.VISIBLE);

                        activity.setExpertInfoListener(ProjectsAdapter.this);//attach listener
                        adapterPosition = getAbsoluteAdapterPosition();
                        activity.getExpert(projectsList.get(getAbsoluteAdapterPosition()).agentId);
                    } else {
                        activity.toastMessage(activity.getString(R.string.agent_has_not_been_hired_on_this_project));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    activity.toastMessage(activity.getString(R.string.agent_has_not_been_hired_on_this_project));
                }
            });

            binding.llEdit.setOnClickListener(view -> {
                binding.swipe.close();
                binding.rlItemview.setBackgroundResource(R.drawable.transp_rounded_corner_10);
                binding.progressBar.setVisibility(View.VISIBLE);
                activity.isClickableView = true;
                adapterPosition = getAbsoluteAdapterPosition();

                Preferences.writeBoolean(activity, Constants.DUPLICATE_PROJECT, false);
                getProjectById(projectsList.get(getAbsoluteAdapterPosition()).jpId);
            });

            binding.llDelete.setOnClickListener(view -> {
                binding.swipe.close();
                showDeleteDialog(projectsList.get(getAbsoluteAdapterPosition()).jpId, binding.swipe, getAbsoluteAdapterPosition());
            });

            binding.llDuplicate.setOnClickListener(view -> {
                binding.swipe.close();
                binding.rlItemview.setBackgroundResource(R.drawable.transp_rounded_corner_10);
                binding.progressBar.setVisibility(View.VISIBLE);
                activity.isClickableView = true;
                adapterPosition = getAbsoluteAdapterPosition();
                Preferences.writeBoolean(activity, Constants.DUPLICATE_PROJECT, true);

                getProjectById(projectsList.get(getAbsoluteAdapterPosition()).jpId);

            });

            binding.rlItemview.setOnClickListener(view -> {
                activity.setEnableDisableView(binding.rlItemview);
                if (projectsList.get(getAbsoluteAdapterPosition()).jpsId != UNDER_REVIEW) {
                    if (projectsList.get(getAbsoluteAdapterPosition()).jpsId == Constants.REMOVED) {
                        showRepostDialog(projectsList.get(getAbsoluteAdapterPosition()).jpId, binding.swipe, getAbsoluteAdapterPosition());
                    } else {
                        binding.rlItemview.setBackgroundResource(R.drawable.transp_rounded_corner_10);
                        binding.progressBar.setVisibility(View.VISIBLE);

                        if (onClickJobListener != null) {
                            onClickJobListener.onClickJob(projectsList.get(getAbsoluteAdapterPosition()).jpId, getAbsoluteAdapterPosition(), projectsList.get(getAbsoluteAdapterPosition()).job, projectsList.get(getAbsoluteAdapterPosition()).gigType);
                        }
                    }
                }
            });

        }
    }
}
