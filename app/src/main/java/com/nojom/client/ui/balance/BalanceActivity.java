package com.nojom.client.ui.balance;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityBalanceBinding;
import com.nojom.client.model.Deposit;
import com.nojom.client.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class BalanceActivity extends BaseActivity {

    private BalanceActivityVM balanceActivityVM;
    public double availableBalance;
    private List<Deposit.Data> depositList;
    private List<Deposit.Data> historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBalanceBinding activityBalanceBinding = DataBindingUtil.setContentView(this, R.layout.activity_balance);
        balanceActivityVM = new BalanceActivityVM(Task24Application.getInstance(), activityBalanceBinding, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        balanceActivityVM.onResumeMethod();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        balanceActivityVM.onActivityResult(requestCode, resultCode, data);
    }

    public List<Deposit.Data> getDepositList() {
        if (depositList == null) {
            depositList = new ArrayList<>();
        }
        return depositList;
    }

    public void setDepositList(List<Deposit.Data> depositList) {
        this.depositList = depositList;
    }

    public List<Deposit.Data> getHistoryList() {
        if (historyList == null) {
            historyList = new ArrayList<>();
        }
        return historyList;
    }

    public void setHistoryList(List<Deposit.Data> historyList) {
        this.historyList = historyList;
    }
}
