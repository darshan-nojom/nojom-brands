package com.nojom.client.ui.addcard;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ContentCardListBinding;
import com.nojom.client.ui.BaseActivity;

public class CardListActivity extends BaseActivity {
    private CardListActivityVM cardListActivityVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContentCardListBinding cardListBinding = DataBindingUtil.setContentView(this, R.layout.content_card_list);
        cardListActivityVM = new CardListActivityVM(Task24Application.getInstance(), cardListBinding, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cardListActivityVM.onResumeMethod();
    }

    public void updateList(int position, int type) {
        cardListActivityVM.updateList(position, type);
    }

    public void updatePaypalList(int position, int type) {
        cardListActivityVM.updatePaypalList(position, type);
    }
}
