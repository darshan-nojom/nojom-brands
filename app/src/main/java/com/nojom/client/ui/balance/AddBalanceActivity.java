package com.nojom.client.ui.balance;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityWalletAddBalanceBinding;
import com.nojom.client.model.WalletData;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.projects.CampByIdVM;
import com.nojom.client.ui.projects.WebViewWalletActivity;
import com.nojom.client.util.Utils;


public class AddBalanceActivity extends BaseActivity {
    private ActivityWalletAddBalanceBinding binding;
    private double availableBalance;
    private CampByIdVM campByIdVM;
    private WalletData walletData;
    public static AddBalanceActivity sActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wallet_add_balance);
        campByIdVM = new CampByIdVM(Task24Application.getInstance(), this);
        walletData = (WalletData) getIntent().getSerializableExtra("data");
        initData();
        sActivity = this;
    }

    private void initData() {
        if (getCurrency().equals("SAR")) {
            binding.txtSign.setText(getString(R.string.sar));
        } else {
            binding.txtSign.setText(getString(R.string.dollar));
        }
        binding.imgBack.setOnClickListener(v -> onBackPressed());
        binding.txtWalletBalance.setText(Utils.priceWithSAR(this, Utils.getDecimalValue("" + walletData.available_balance)).replace(getString(R.string.sar), ""));

        binding.txtAddBalance.setOnClickListener(view -> {
            if (TextUtils.isEmpty(binding.etAmount.getText().toString().trim())) {
                toastMessage(getString(R.string.amount_should_not_start_with_0));
                return;
            }
            campByIdVM.chargeWallet(Double.valueOf(binding.etAmount.getText().toString()));
        });

        campByIdVM.mutableProgress.observe(this, aBoolean -> {
            if (aBoolean) {
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.txtAddBalance.setVisibility(View.INVISIBLE);
            } else {
                binding.progressBar.setVisibility(View.GONE);
                binding.txtAddBalance.setVisibility(View.VISIBLE);
            }
        });

        campByIdVM.mpWalletBalanceData.observe(this, walletData1 -> {
            if (walletData1 != null) {
                String url = walletData1.embed_url;
                Uri uri = Uri.parse(url);
                // Extract the last segment from the path
                String lastSegment = uri.getLastPathSegment();

                binding.progressBar.setVisibility(View.GONE);
                binding.txtAddBalance.setVisibility(View.VISIBLE);

                Intent intent = new Intent(this, WebViewWalletActivity.class);
                intent.putExtra("url", url);
                intent.putExtra("bal", "" + binding.etAmount.getText().toString());
                intent.putExtra("intent", "" + lastSegment);
                intent.putExtra("campId", "" + getUserID());
                startActivity(intent);
            }
        });
    }


}
