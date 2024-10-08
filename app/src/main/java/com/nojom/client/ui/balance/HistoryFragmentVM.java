package com.nojom.client.ui.balance;

import android.app.Application;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.FragmentBalanceHistoryBinding;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.model.Deposit;
import com.nojom.client.util.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.nojom.client.util.Constants.API_GET_HISTORY;

public class HistoryFragmentVM extends AndroidViewModel implements HistoryAdapter.OnCancelWithdrawals, RequestResponseListener {
    private FragmentBalanceHistoryBinding binding;
    private BaseFragment fragment;
    private List<Deposit.Data> historyList;
    private HistoryAdapter mAdapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    private int pageNo = 1;

    HistoryFragmentVM(Application application, FragmentBalanceHistoryBinding balanceHistoryBinding, BaseFragment historyFragment) {
        super(application);
        binding = balanceHistoryBinding;
        fragment = historyFragment;
        initData();
    }

    private void initData() {

        binding.noData.tvNoTitle.setText(fragment.getString(R.string.no_withdraw_balance_1));
        binding.noData.tvNoDescription.setText(fragment.getString(R.string.no_withdraw_balance_desc));

        historyList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(fragment.activity);
        binding.rvWithdraw.setLayoutManager(linearLayoutManager);

        //Get data from locally once APIs call & display it that data
        if (((BalanceActivity) fragment.activity).getHistoryList() != null && ((BalanceActivity) fragment.activity).getHistoryList().size() > 0) {
            historyList = ((BalanceActivity) fragment.activity).getHistoryList();
            setAdapter();
        } else {
            getHistory();
        }

        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            historyList = new ArrayList<>();
            pageNo = 1;
            scrollListener.resetState();
            binding.shimmerLayout.startShimmer();
            getHistory();
        });

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                pageNo = page;
                getHistory();
            }
        };
        binding.rvWithdraw.addOnScrollListener(scrollListener);
    }


    private void getHistory() {
        if (!fragment.activity.isNetworkConnected()) {
            binding.swipeRefreshLayout.setRefreshing(false);
            return;
        }

        if (!binding.swipeRefreshLayout.isRefreshing()) {
            binding.shimmerLayout.startShimmer();
            binding.noData.llNoData.setVisibility(View.INVISIBLE);
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("page_no", pageNo + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, fragment.activity, API_GET_HISTORY, true, map);
    }

    private void setAdapter() {
        if (historyList != null && historyList.size() > 0) {
            binding.noData.llNoData.setVisibility(View.GONE);
            if (mAdapter == null) {
                mAdapter = new HistoryAdapter(fragment.activity, this);
            }
            if (pageNo == 1) {
                mAdapter.initList(historyList);
            } else {
                mAdapter.doRefresh(historyList);
            }
            if (binding.rvWithdraw.getAdapter() == null) {
                binding.rvWithdraw.setAdapter(mAdapter);
            }
        } else {
            binding.noData.llNoData.setVisibility(View.VISIBLE);
            if (mAdapter != null)
                mAdapter.doRefresh(null);
        }
        binding.swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void cancelWithdrawals() {

    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        if (url.equalsIgnoreCase(API_GET_HISTORY)) {
            Deposit model = Deposit.getDeposits(responseBody);
            if (model != null && model.data != null) {
                historyList.addAll(model.data);
                ((BalanceActivity) fragment.activity).setHistoryList(historyList);//set data to prevent API call
            }
            setAdapter();
            binding.shimmerLayout.stopShimmer();
            binding.shimmerLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        binding.swipeRefreshLayout.setRefreshing(false);
        setAdapter();
        binding.shimmerLayout.stopShimmer();
        binding.shimmerLayout.setVisibility(View.GONE);
    }
}
