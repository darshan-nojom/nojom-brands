package com.nojom.client.ui.home;

import static com.nojom.client.util.Constants.AGENT_PROFILE_DATA;
import static com.nojom.client.util.Constants.API_GET_PROFILE_INFO;
import static com.nojom.client.util.Constants.API_SEARCH_INFLU;

import android.app.Application;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.adapter.LawyerPopularAdapter;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.FragmentAllPopularLawyerBinding;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.model.AgentProfile;
import com.nojom.client.model.InfluencerList;
import com.nojom.client.ui.chat.ChatMessagesActivity;
import com.nojom.client.ui.projects.InfluencerProfileActivityCopy;
import com.nojom.client.util.Constants;
import com.nojom.client.util.EndlessRecyclerViewScrollListener;
import com.nojom.client.util.SaveRemoveClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class AllLawyerFragmentVM extends AndroidViewModel implements LawyerPopularAdapter.OnClickListener,
        SaveRemoveClickListener, RequestResponseListener {
    private final FragmentAllPopularLawyerBinding binding;
    private final BaseFragment fragment;
    private List<InfluencerList.AllData> expertList;
    private int pageNo = 1, selectedPos;
    private EndlessRecyclerViewScrollListener scrollListener;
    private LawyerPopularAdapter servicesAdapter;
    private String filePath;

    AllLawyerFragmentVM(Application application, FragmentAllPopularLawyerBinding chatListBinding, BaseFragment chatListFragment) {
        super(application);
        binding = chatListBinding;
        fragment = chatListFragment;
        initData();
    }

    public void initData() {

        binding.noData.tvNoTitle.setText(fragment.getString(R.string.no_saved_experts));
        binding.noData.tvNoDescription.setText(fragment.getString(R.string.all_saved_expert_lists_will_be_displayed_here));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(fragment.activity);
        binding.rvExpert.setLayoutManager(linearLayoutManager);
        refreshData();
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (servicesAdapter != null && servicesAdapter.getItemCount() > 9) {
                    pageNo = page;
                    getExperts(false, ((FindExpertActivity) fragment.activity).getServiceCategoryId(), "ALL", pageNo);
                }
            }
        };
        binding.rvExpert.addOnScrollListener(scrollListener);
    }

    public void refreshData() {
        expertList = new ArrayList<>();
        this.pageNo = 1;
        servicesAdapter = null;
        getExperts(false, ((FindExpertActivity) fragment.activity).getServiceCategoryId(), "ALL", pageNo);
    }

    void refreshAdapter() {
        if (servicesAdapter != null) {
            servicesAdapter.unSelectAll();
        }
    }

    public void getExperts(boolean isClearAll, int serviceCatId) {
        getExperts(isClearAll, serviceCatId, "ALL", 1);
    }

    private void setAdapter(List<InfluencerList.AllData> data, String filePath) {
        if (expertList != null && expertList.size() > 0) {
            if (servicesAdapter == null) {
                boolean isFromJobScreen = ((FindExpertActivity) fragment.activity).isPostJobScreen();
                servicesAdapter = new LawyerPopularAdapter(fragment.activity, expertList, AllLawyerFragmentVM.this, filePath, isFromJobScreen);
                binding.rvExpert.setAdapter(servicesAdapter);
            } else {
                servicesAdapter.addItem(data);
            }
            binding.rvExpert.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClickFavourite(InfluencerList.AllData data) {
        if (fragment.activity.isLogin()) {
            fragment.activity.saveRemoveAgent(data.id, this, data.saved != 0);
        } else {
            fragment.activity.openLoginDialog();
        }
    }

    @Override
    public void onClickchat(InfluencerList.AllData data) {
        if (fragment.activity.isLogin()) {
            HashMap<String, String> chatMap = new HashMap<>();
            chatMap.put(Constants.RECEIVER_ID, data.id + "");
            if (!TextUtils.isEmpty(data.last_name) && !data.last_name.equals("null")) {
                chatMap.put(Constants.RECEIVER_NAME, data.first_name + " " + data.last_name);
            } else {
                chatMap.put(Constants.RECEIVER_NAME, data.first_name);
            }

            String img = "";
            if (data.img != null) {
                img = data.img;
            }
            chatMap.put(Constants.RECEIVER_PIC, filePath + img);
            chatMap.put(Constants.SENDER_ID, fragment.activity.getUserID() + "");
            chatMap.put(Constants.SENDER_NAME, fragment.activity.getUserName());
            chatMap.put(Constants.SENDER_PIC, fragment.activity.getProfilePic());
            chatMap.put("isProject", "1");//1 mean updated record
            chatMap.put("projectType", "2");//2=job & 1= gig

            Intent i = new Intent(fragment.activity, ChatMessagesActivity.class);
            i.putExtra(Constants.CHAT_ID, fragment.activity.getUserID() + "-" + data.id);  // ClientId - AgentId
            i.putExtra(Constants.CHAT_DATA, chatMap);
            if (fragment.activity.getIsVerified() == 1) {
                fragment.startActivity(i);
            } else {
                fragment.activity.toastMessage(fragment.getString(R.string.verification_is_pending_please_complete_the_verification_first_before_chatting_with_them));
            }

        } else {
            fragment.activity.openLoginDialog();
        }
    }

    @Override
    public void onClickViewProfile(InfluencerList.AllData data, int pos) {
        if (fragment.activity.isLogin()) {
            selectedPos = pos;
            getAgentProfile(data.id);
        } else {
            fragment.activity.openLoginDialog();
        }
    }

    @Override
    public void onClickPostJobFree(InfluencerList.AllData data) {
        ((FindExpertActivity) fragment.activity).openPostJob();
    }

    public void onPreExcecute(boolean isClearAll, int pageNo) {
        if (isClearAll) {
            this.pageNo = 1;
            servicesAdapter = null;
            expertList = new ArrayList<>();
            scrollListener.resetState();
        }
        if (pageNo == 1) {
            binding.shimmerLayout.startShimmer();
            binding.shimmerLayout.setVisibility(View.VISIBLE);
            binding.rvExpert.setVisibility(View.INVISIBLE);
        }
    }

    public void onError() {
        binding.shimmerLayout.hideShimmer();
        binding.shimmerLayout.setVisibility(View.GONE);
    }

    public void onSuccessExperts(InfluencerList expert) {
        if (expert != null && expert.influencer != null) {
            filePath = expert.path;
            if (expert.influencer.size() > 0) {
                binding.noData.llNoData.setVisibility(View.GONE);
                expertList.addAll(expert.influencer);
                setAdapter(expert.influencer, expert.path);
            } else if (pageNo == 1) {
                binding.noData.llNoData.setVisibility(View.VISIBLE);
            }
            binding.shimmerLayout.hideShimmer();
            binding.shimmerLayout.setVisibility(View.GONE);
        } else if (pageNo == 1) {
            binding.noData.llNoData.setVisibility(View.VISIBLE);
        }

        binding.shimmerLayout.hideShimmer();
        binding.shimmerLayout.setVisibility(View.GONE);

    }

    @Override
    public void savedAgentSuccess(int agentId) {
        if (servicesAdapter != null) {
            servicesAdapter.updateSingleItem(agentId, 1);
            fragment.activity.isClickableView = false;
        }
    }

    @Override
    public void removeAgentSuccess(int agentId) {
        if (servicesAdapter != null) {
            servicesAdapter.updateSingleItem(agentId, 0);
            fragment.activity.isClickableView = false;
        }
    }

    public void getExperts(boolean isClearAll, int serviceCatId, String selectedTab, int page) {
        if (!fragment.activity.isNetworkConnected()) {
            return;
        }

        String searchKey = ((FindExpertActivity) fragment.activity).getSearchkeyword();
//        onPreExcecute(isClearAll, pageNo);
//
//        HashMap<String, String> map = new HashMap<>();
//        map.put("page_no", pageNo + "");
//        map.put("tab", selectedTab);
//        map.put("search", searchKey);
//        map.put("service_category_id", 4352 + "");
//        map.put("language_id", ((FindExpertActivity) fragment.activity).getLanguageId() > 0 ? ((FindExpertActivity) fragment.activity).getLanguageId() + "" : "");
//        map.put("sort_by", ((FindExpertActivity) fragment.activity).getSortBy());
//        map.put("pay_type_id", ((FindExpertActivity) fragment.activity).getAvailability());
//        map.put("workbase", ((FindExpertActivity) fragment.activity).getWorkbase());
//        map.put("skill_ids", ((FindExpertActivity) fragment.activity).getSkillId());
//
//        ApiRequest apiRequest = new ApiRequest();
//        apiRequest.apiRequest(this, fragment.activity, API_GET_PROFILE_BY_CATEGORY, true, map);


        onPreExcecute(isClearAll, pageNo);

        HashMap<String, String> map = new HashMap<>();
        map.put("page_no", page + "");
        if (!TextUtils.isEmpty(searchKey)) {
            map.put("search", searchKey + "");
        }
        map.put("tab", selectedTab);

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, fragment.activity, API_SEARCH_INFLU, true, map);
    }

    private void getAgentProfile(int agentProfileId) {
        if (!fragment.activity.isNetworkConnected())
            return;
        fragment.activity.isClickableView = true;

        HashMap<String, String> map = new HashMap<>();
        map.put("agent_profile_id", agentProfileId + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, fragment.activity, API_GET_PROFILE_INFO, true, map);
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        if (url.equalsIgnoreCase(API_SEARCH_INFLU)) {
//            ExpertLawyers expert = ExpertLawyers.getTop3Agents(responseBody);
            InfluencerList expert = InfluencerList.getAllInfluencers(responseBody);
            onSuccessExperts(expert);
            ((FindExpertActivity) fragment.activity).setCallApiForSearchTag(false);
        } else if (url.equalsIgnoreCase(API_GET_PROFILE_INFO)) {
            AgentProfile profile = AgentProfile.getProfileInfo(responseBody);
            if (profile != null) {
//                Intent i = new Intent(fragment.activity, FreelancerProfileActivity.class);
                Intent i = new Intent(fragment.activity, InfluencerProfileActivityCopy.class);
                i.putExtra(AGENT_PROFILE_DATA, profile);
                fragment.startActivity(i);
            }
            notifyProfileProgress();
        }
        fragment.activity.isClickableView = false;
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        onError();
        notifyProfileProgress();
        fragment.activity.isClickableView = false;
        ((FindExpertActivity) fragment.activity).setCallApiForSearchTag(false);
    }

    private void notifyProfileProgress() {
        if (servicesAdapter != null && servicesAdapter.getData() != null
                && servicesAdapter.getData().size() > 0) {
            servicesAdapter.getData().get(selectedPos).isShowProfileProgress = false;
            servicesAdapter.notifyItemChanged(selectedPos);
        }
    }

}
