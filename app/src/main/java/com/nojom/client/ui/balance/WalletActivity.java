package com.nojom.client.ui.balance;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.adapter.WallectTxnAdapter;
import com.nojom.client.databinding.ActivityWalletBinding;
import com.nojom.client.model.WalletData;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.projects.CampByIdVM;
import com.nojom.client.util.Utils;


public class WalletActivity extends BaseActivity {
    private ActivityWalletBinding binding;
    private double availableBalance;
    private CampByIdVM campByIdVM;
    private WalletData walletData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wallet);
        campByIdVM = new CampByIdVM(Task24Application.getInstance(), this);
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        campByIdVM.getWalletBalance();
        campByIdVM.getWalletTransaction();
    }

    private void initData() {
        if (getCurrency().equals("SAR")) {
            binding.txtSign.setText(getString(R.string.sar));
        } else {
            binding.txtSign.setText(getString(R.string.dollar));
        }
        binding.imgBack.setOnClickListener(v -> onBackPressed());

        binding.tvShowDetails.setOnClickListener(v -> {
            if (!binding.llShowDetails.isShown()) {
                binding.tvShowDetails.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_up_arrow, 0);

                expand(binding.llShowDetails);

            } else {
                binding.tvShowDetails.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down_arrow, 0);
                collapse(binding.llShowDetails);
            }
        });
//        campByIdVM.getWalletBalance();
//        campByIdVM.getWalletTransaction();

        campByIdVM.mpWalletData.observe(this, walletData -> {
            WallectTxnAdapter wallectTxnAdapter = new WallectTxnAdapter(this, walletData);
            binding.viewpager.setAdapter(wallectTxnAdapter);
        });

        campByIdVM.mpWalletBalanceData.observe(this, walletData -> {
            this.walletData = walletData;
            setBalance(walletData.available_balance, walletData.hold_balance, walletData.balance);
            binding.txtSign.setText(walletData.currency);
        });

        binding.txtAddBalance.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddBalanceActivity.class);
            intent.putExtra("data", walletData);
            startActivity(intent);
        });
    }

    public static void expand(final View v, int duration, int targetHeight) {

        int prevHeight = v.getHeight();

        v.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.addUpdateListener(animation -> {
            v.getLayoutParams().height = (int) animation.getAnimatedValue();
            v.requestLayout();
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    public static void expand(final View v) {
        int matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec(((View) v.getParent()).getWidth(), View.MeasureSpec.EXACTLY);
        int wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(matchParentMeasureSpec, wrapContentMeasureSpec);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1 ? LinearLayout.LayoutParams.WRAP_CONTENT : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Expansion speed of 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Collapse speed of 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public void setBalance(double availableBalance, double pendingBalance, double totalBalance) {
        this.availableBalance = availableBalance;
        if (getCurrency().equals("SAR")) {
            binding.tvPendingBalance.setText(String.format("%s", Utils.priceWithSAR(this, Utils.getDecimalValue("" + pendingBalance))));
            binding.tvTotalBalance.setText(String.format("%s", Utils.priceWithSAR(this, Utils.getDecimalValue("" + totalBalance))));
            binding.tvBalance.setText(Utils.priceWithSAR(this, Utils.getDecimalValue("" + availableBalance)).replace(getString(R.string.sar), ""));
        } else {
            binding.tvPendingBalance.setText(String.format("%s", Utils.priceWith$(Utils.getDecimalValue("" + pendingBalance), this)));
            binding.tvTotalBalance.setText(String.format("%s", Utils.priceWith$(Utils.getDecimalValue("" + totalBalance), this)));
            binding.tvBalance.setText(Utils.priceWith$(Utils.getDecimalValue("" + availableBalance), this).replace(getString(R.string.dollar), ""));
        }
    }
}
