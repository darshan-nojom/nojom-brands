package com.nojom.client.ui.balance;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityWalletAddBalanceBinding;
import com.nojom.client.databinding.DialogPayDoneBinding;
import com.nojom.client.model.WalletData;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.projects.CampByIdVM;
import com.nojom.client.util.AddCardActivity;
import com.nojom.client.util.Utils;

import java.util.Objects;


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

//                Intent intent = new Intent(this, WebViewWalletActivity.class);
//                intent.putExtra("url", url);
//                intent.putExtra("bal", "" + binding.etAmount.getText().toString());
//                intent.putExtra("intent", "" + lastSegment);
//                intent.putExtra("campId", "" + getUserID());
//                startActivity(intent);

                Intent intent = new Intent(this, AddCardActivity.class);
                intent.putExtra("intentId", lastSegment);
                startActivityForResult(intent, 121);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 121) {
            postDoneDialog(true);
        }
    }

    private void postDoneDialog(boolean isSuccess) {
        final Dialog dialog = new Dialog(this);
        dialog.setTitle(null);
        DialogPayDoneBinding bindingDialog = DialogPayDoneBinding.inflate(LayoutInflater.from(this));
        dialog.setContentView(bindingDialog.getRoot());
        dialog.setCancelable(true);

        if (!isSuccess) {
            bindingDialog.imgDone.setImageResource(R.drawable.ic_pay_fail);
            bindingDialog.txtTitle.setText(getString(R.string.payment_failed));
            bindingDialog.txtDesc.setText(getString(R.string.unfortunately_we_were_unable_to_process_your_payment_please_try_again_or_use_a_different_payment_method) + "\n");
            bindingDialog.txtDesc1.setText(getString(R.string.if_you_continue_to_experience_issues_our_support_team_is_here_to_help));
            bindingDialog.btnContinuePrice.setText(getString(R.string.try_again));
        } else {
            bindingDialog.txtTitle.setText(getString(R.string.successful));
            bindingDialog.txtDesc.setText(getString(R.string.your_wallet_has_been_charged_successfully_new_balance) + binding.etAmount.getText().toString() + " " + getString(R.string.sar));
            bindingDialog.txtDesc1.setText("");
            bindingDialog.btnContinuePrice.setText(getString(R.string.view_my_balance));
        }

        bindingDialog.btnContinuePrice.setOnClickListener(view -> {
            if (isSuccess) {
                dialog.dismiss();
                AddBalanceActivity.sActivity.finish();
                finish();
            } else {
                dialog.dismiss();
                finish();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        lp.gravity = Gravity.CENTER;
        lp.width = (int) (displaymetrics.widthPixels * 0.9);
        lp.height = (int) (displaymetrics.heightPixels * 0.7);
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
        dialog.setOnDismissListener(dialog1 -> {
            isClickableView = false;
            if (isSuccess) {
                dialog.dismiss();
                AddBalanceActivity.sActivity.finish();
                finish();
            } else {
                dialog.dismiss();
                finish();
            }
        });
    }
}
