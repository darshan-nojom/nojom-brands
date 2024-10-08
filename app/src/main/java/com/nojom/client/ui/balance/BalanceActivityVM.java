package com.nojom.client.ui.balance;

import android.app.Application;
import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.AndroidViewModel;
import androidx.viewpager.widget.ViewPager;

import com.nojom.client.R;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivityBalanceBinding;
import com.nojom.client.model.ClientBalance;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.nojom.client.util.Constants.API_GET_CLIENT_BALANCE;

class BalanceActivityVM extends AndroidViewModel implements View.OnClickListener, RequestResponseListener {
    private ActivityBalanceBinding binding;
    private BaseActivity activity;
    private int tabPosition = 0;
    private PaymentFragment paymentFragment;

    BalanceActivityVM(Application application, ActivityBalanceBinding activityBalanceBinding, BaseActivity balanceActivity) {
        super(application);
        binding = activityBalanceBinding;
        activity = balanceActivity;
        initData();
    }

    private void initData() {
        binding.imgBack.setOnClickListener(this);
        binding.tvWithdraw.setOnClickListener(this);
        binding.tvShowDetails.setOnClickListener(this);
        binding.imgArrowUp.setOnClickListener(this);

        if (activity.getIntent() != null) {
            tabPosition = activity.getIntent().getIntExtra(Constants.TAB_BALANCE, 0);
        }

        if (activity.getCurrency().equals("SAR")) {
            binding.txtSign.setText(activity.getString(R.string.sar));
        } else {
            binding.txtSign.setText(activity.getString(R.string.dollar));
        }

        setupPager();

        Utils.trackAppsFlayerEvent(activity, "Balance_List_Screen");
    }

    void onResumeMethod() {
        getClientBalance();
    }

    private void setupPager() {
        setupViewPager(binding.viewpager);

        binding.segmentedGroupTab.setOnPositionChangedListener(position -> {
            binding.viewpager.setCurrentItem(position);
        });

        binding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                binding.viewpager.setCurrentItem(i);
                setTab(i);
                if (i == 1 && paymentFragment != null) {
                    paymentFragment.updateUi();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        binding.viewpager.setCurrentItem(tabPosition);
        setTab(tabPosition);
    }

    private void setTab(int pos) {
        binding.segmentedGroupTab.setPosition(pos, true);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(activity.getSupportFragmentManager());
//        adapter.addFrag(new DepositFragment(), activity.getString(R.string.deposit));
        adapter.addFrag(new HistoryFragment(), activity.getString(R.string.history));
        adapter.addFrag(paymentFragment = new PaymentFragment(), activity.getString(R.string.payment));
        viewPager.setPageMargin(20);
        viewPager.setAdapter(adapter);

        viewPager.setOffscreenPageLimit(2);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                activity.onBackPressed();
                break;
            case R.id.tv_withdraw:
                Intent i = new Intent(activity, DepositActivity.class);
                i.putExtra(Constants.AVAILABLE_BALANCE, ((BalanceActivity) activity).availableBalance);
                activity.startActivity(i);
                break;
            case R.id.tv_show_details:
                if (binding.llShowDetails.getVisibility() == View.GONE) {
                    binding.llShowDetails.setVisibility(View.VISIBLE);
                    binding.tvShowDetails.setText("");
                    binding.tvShowDetails.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }
                break;
            case R.id.img_arrow_up:
                binding.llShowDetails.setVisibility(View.GONE);
                binding.tvShowDetails.setText(activity.getString(R.string.show_details));
                binding.tvShowDetails.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                break;
        }
    }

    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (paymentFragment != null) {
            paymentFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void getClientBalance() {
        if (!activity.isNetworkConnected()) {
            return;
        }

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_CLIENT_BALANCE, false, null);
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        if (url.equalsIgnoreCase(API_GET_CLIENT_BALANCE)) {
            ClientBalance clientBalance = ClientBalance.getClientBalance(responseBody);
            if (clientBalance != null) {
                try {
                    Double availableBalance = clientBalance.availableBalance != null ? clientBalance.availableBalance : 0;
                    Double pendingBalance = clientBalance.pending_balance != null ? clientBalance.pending_balance : 0;
                    Double totalBalance = availableBalance + pendingBalance;

                    ((BalanceActivity) activity).availableBalance = availableBalance;

                    binding.tvAvailableBalance.setText(activity.getCurrency().equals("SAR") ? Utils.priceWithSAR(activity,Utils.decimalFormat(String.valueOf(availableBalance))) : Utils.priceWith$(Utils.decimalFormat(String.valueOf(availableBalance)),activity));
                    binding.tvPendingBalance.setText(activity.getCurrency().equals("SAR") ? Utils.priceWithSAR(activity,Utils.decimalFormat(String.valueOf(pendingBalance))) : Utils.priceWith$(Utils.decimalFormat(String.valueOf(pendingBalance)),activity));
                    binding.tvTotalBalance.setText(activity.getCurrency().equals("SAR") ? Utils.priceWithSAR(activity,Utils.decimalFormat(String.valueOf(totalBalance))) : Utils.priceWith$(Utils.decimalFormat(String.valueOf(totalBalance)),activity));
                    binding.tvBalance.setText(activity.getCurrency().equals("SAR") ? Utils.priceWithoutSAR(activity,Utils.decimalFormat(String.valueOf(availableBalance)))
                            : Utils.priceWithout$(Utils.decimalFormat(String.valueOf(availableBalance))));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {
        final List<String> mFragmentTitleList = new ArrayList<>();
        private final List<Fragment> mFragmentList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @NotNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }
}
