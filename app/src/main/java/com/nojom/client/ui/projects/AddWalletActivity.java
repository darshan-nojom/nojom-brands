package com.nojom.client.ui.projects;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.databinding.ActivityAddMoneyBinding;
import com.nojom.client.databinding.ActivityPaymentBinding;
import com.nojom.client.model.ExpertGigDetail;
import com.nojom.client.model.ProjectByID;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;

public class AddWalletActivity extends BaseActivity implements View.OnClickListener {
    ActivityAddMoneyBinding binding;
    private String wallet, id, code, redeem, total, depAmnt, fees,appliedPromoCode;
    private boolean isFromGig;
    private ProjectByID projectData;
    private ExpertGigDetail gigData;
    private double promoDiscountAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_money);
        initData();
    }

    private void initData() {
        binding.toolbar.tvTitle.setText(getString(R.string.add_money_to_my_wallet));
        binding.toolbar.imgBack.setOnClickListener(this);
        binding.relDepositNow.setOnClickListener(this);
        binding.txtCopyName.setOnClickListener(this);
        binding.txtCopyAcc.setOnClickListener(this);
        binding.txtCopyIban.setOnClickListener(this);

        if (getIntent() != null) {
            isFromGig = getIntent().getBooleanExtra("isFromGig", false);
            wallet = getIntent().getStringExtra("wallet");
            appliedPromoCode = getIntent().getStringExtra("promocode");
            promoDiscountAmount = getIntent().getDoubleExtra("promoamount",0.0);
            id = getIntent().getStringExtra("id");
            depAmnt = getIntent().getStringExtra("depAmnt");
            fees = getIntent().getStringExtra("fees");
            total = getIntent().getStringExtra("total");
            code = getIntent().getStringExtra("code");
            redeem = getIntent().getStringExtra("redeem");
            projectData = (ProjectByID) getIntent().getSerializableExtra("project");
            gigData = (ExpertGigDetail) getIntent().getSerializableExtra("gig");

            if (!TextUtils.isEmpty(wallet)) {
                binding.txtWalletAmount.setText(wallet);
            }
            binding.txtJobId.setText(id);
            binding.txtDepAmount.setText(depAmnt);
            binding.txtServAmount.setText(fees);

            if (!TextUtils.isEmpty(code)) {
                binding.txtPromoCode.setText(code);
                binding.relPromoCode.setVisibility(View.VISIBLE);
            } else {
                binding.relPromoCode.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(redeem)) {
                binding.relRedeem.setVisibility(View.VISIBLE);
                binding.txtRedeem.setText(redeem);
            } else {
                binding.relRedeem.setVisibility(View.GONE);
            }

            binding.txtTotal.setText(total);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_copy_name:
                copyMsg(binding.txtRecName.getText().toString());
                break;
            case R.id.txt_copy_acc:
                copyMsg(binding.txtAccNo.getText().toString());
                break;
            case R.id.txt_copy_iban:
                copyMsg(binding.txtIbanNo.getText().toString());
                break;
            case R.id.rel_depositNow:
//                startActivity(new Intent(this, BankTransferActivity.class));
                Intent in = new Intent(this, BankTransferActivity.class);
                in.putExtra("wallet", binding.txtWalletAmount.getText().toString());
                in.putExtra("id", binding.txtJobId.getText().toString());
                in.putExtra("depAmnt", binding.txtDepAmount.getText().toString());
                in.putExtra("fees", binding.txtServAmount.getText().toString());
                in.putExtra("total", binding.txtTotal.getText().toString());
                in.putExtra("code", binding.txtPromoCode.getText().toString());
                in.putExtra("redeem", binding.txtRedeem.getText().toString());
                in.putExtra("isFromGig", isFromGig);
                in.putExtra("project", projectData);
                in.putExtra("promocode", appliedPromoCode);
                in.putExtra("promoamount", promoDiscountAmount);
                in.putExtra("gig", gigData);
                startActivity(in);
                break;
            case R.id.img_back:
                onBackPressed();
                break;
        }
    }

    private void selectedBackground(RelativeLayout rel, AppCompatImageView imgCheck) {
        rel.setBackground(getResources().getDrawable(R.drawable.lightgray_button_bg_12));
        imgCheck.setImageResource(R.drawable.circle_check);
    }

    private void notSelectedBackground(RelativeLayout rel, AppCompatImageView imgCheck) {
        rel.setBackground(getResources().getDrawable(R.drawable.lightgray_border_12));
        imgCheck.setImageResource(R.drawable.circle_uncheck);
    }

    private void copyMsg(String msg) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied", msg);
        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
            toastMessage(getString(R.string.copied));
        }
    }
}
