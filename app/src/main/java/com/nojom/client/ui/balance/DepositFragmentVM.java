package com.nojom.client.ui.balance;

import android.app.Application;
import android.content.Intent;
import android.view.View;

import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nojom.client.R;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.FragmentBalanceDepositBinding;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.model.Deposit;
import com.nojom.client.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.nojom.client.util.Constants.API_GET_DEPOSIT;

class DepositFragmentVM extends AndroidViewModel implements RequestResponseListener {
    private FragmentBalanceDepositBinding binding;
    private BaseFragment fragment;
    private List<Deposit.Data> depositList;
    private DepositAdapter mAdapter;
    private int pageNo = 1;
    private int visibleItemCount, totalItemCount, pastVisiblesItems;
    private boolean loading;

    DepositFragmentVM(Application application, FragmentBalanceDepositBinding balanceDepositBinding, BaseFragment depositFragment) {
        super(application);
        binding = balanceDepositBinding;
        fragment = depositFragment;
        initData();
    }

    private void initData() {

        binding.noData.tvNoTitle.setText(fragment.getString(R.string.no_income_balance));
        binding.noData.tvNoDescription.setText(fragment.getString(R.string.no_income_balance_desc));

        depositList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(fragment.activity);
        binding.rvIncome.setLayoutManager(linearLayoutManager);

        if (((BalanceActivity) fragment.activity).getDepositList() != null && ((BalanceActivity) fragment.activity).getDepositList().size() > 0) {
            depositList = ((BalanceActivity) fragment.activity).getDepositList();
            setAdapter();
        } else {
            getDeposit();
        }

        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            depositList = new ArrayList<>();
            pageNo = 1;
            loading = false;
            binding.shimmerLayout.startShimmer();
            getDeposit();
        });

        binding.nestedScroll.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (v.getChildAt(v.getChildCount() - 1) != null) {
                if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
                        scrollY > oldScrollY) {

                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();

                    if (!loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            getDeposit();
                        }
                    }
                }
            }
        });

        binding.tvDepositMoney.setOnClickListener(view -> {
            Intent i = new Intent(fragment.activity, DepositActivity.class);
            i.putExtra(Constants.AVAILABLE_BALANCE, ((BalanceActivity) fragment.activity).availableBalance);
            fragment.startActivity(i);
        });
    }

    private void getDeposit() {
        if (!fragment.activity.isNetworkConnected()) {
            binding.swipeRefreshLayout.setRefreshing(false);
            return;
        }
        loading = true;
        if (!binding.swipeRefreshLayout.isRefreshing()) {
            binding.shimmerLayout.startShimmer();
            binding.noData.llNoData.setVisibility(View.INVISIBLE);
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("page_no", pageNo + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, fragment.activity, API_GET_DEPOSIT, true, map);
    }

    private void setAdapter() {
        if (depositList != null && depositList.size() > 0) {
            binding.noData.llNoData.setVisibility(View.GONE);
            if (mAdapter == null) {
                mAdapter = new DepositAdapter(fragment.activity);
            }
            if (pageNo == 1) {
                mAdapter.initList(depositList);
            } else {
                mAdapter.doRefresh(depositList);
            }
            if (binding.rvIncome.getAdapter() == null) {
                binding.rvIncome.setAdapter(mAdapter);
            }
        } else {
            binding.noData.llNoData.setVisibility(View.VISIBLE);
            if (mAdapter != null)
                mAdapter.doRefresh(null);
        }
        binding.swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        if (url.equalsIgnoreCase(API_GET_DEPOSIT)) {
            Deposit model = Deposit.getDeposits(responseBody);
            if (model != null) {
                if (model.data != null && model.data.size() > 0) {
                    depositList.addAll(model.data);
                    ((BalanceActivity) fragment.activity).setDepositList(depositList);
                    loading = false;
                    pageNo++;
                } else {
                    loading = true;
                }
            }
            setAdapter();
            binding.shimmerLayout.stopShimmer();
            binding.shimmerLayout.setVisibility(View.GONE);
            binding.tvDepositMoney.setVisibility(View.GONE);
        }
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        if (url.equalsIgnoreCase(API_GET_DEPOSIT)) {
            binding.swipeRefreshLayout.setRefreshing(false);
            loading = false;
            setAdapter();
            binding.shimmerLayout.stopShimmer();
            binding.shimmerLayout.setVisibility(View.GONE);
            binding.tvDepositMoney.setVisibility(View.GONE);
        }
    }
}
