package com.nojom.client.ui.settings;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.adapter.InvoicesAdapter;
import com.nojom.client.databinding.ActivityMyInvoicesBinding;
import com.nojom.client.model.CampList;
import com.nojom.client.model.Invoices;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;

public class MyInvoiceActivity extends BaseActivity implements InvoicesAdapter.OnClickDownloadListener {
    private ActivityMyInvoicesBinding binding;
    private MyInvoiceActivityVM myInvoiceActivityVM;
    private boolean isPullToRefresh = false;
    private int pageNo = 1;
    private EndlessRecyclerViewScrollListener scrollListener;

    private ArrayList<CampList> myInvoiceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_invoices);
        myInvoiceActivityVM = new MyInvoiceActivityVM(Task24Application.getInstance(), this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.rvInvoices.setLayoutManager(linearLayoutManager);
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (totalItemsCount > 9) {
                    pageNo = page;
                    isPullToRefresh = true;
                    myInvoiceActivityVM.getMyInvoices(pageNo);
                }
            }
        };

        binding.imgBack.setOnClickListener(view -> onBackPressed());
        myInvoiceList = new ArrayList<>();
//        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
//            isPullToRefresh = false;
////            projectList = new ArrayList<>();
//            myInvoiceList = new ArrayList<>();
//            pageNo = 1;
//            scrollListener.resetState();
//            myInvoiceActivityVM.getMyInvoices(pageNo);
//        });

        myInvoiceActivityVM.getMyInvoices(pageNo);

        myInvoiceActivityVM.listMutableLiveData.observe(this, invoices -> {
            myInvoiceList.addAll(invoices);
            setAdapter();
        });

    }

    private InvoicesAdapter adapter;

    private void setAdapter() {
//        if (adapter == null) {
        adapter = new InvoicesAdapter(this, myInvoiceList, this);
        binding.rvInvoices.setAdapter(adapter);
//        }else{
//
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (scrollListener != null)
            binding.rvInvoices.addOnScrollListener(scrollListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (scrollListener != null)
            binding.rvInvoices.removeOnScrollListener(scrollListener);
    }

    @Override
    public void invoiceDownload(CampList card) {
        //download invoice API
        myInvoiceActivityVM.getInvoiceReport(card.invoiceId, card.clientProfileId);

    }
}
