package com.nojom.client.ui.balance;

import android.app.Application;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nojom.client.R;
import com.nojom.client.adapter.RecyclerviewAdapter;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivityChooseAccountBinding;
import com.nojom.client.model.BraintreeCard;
import com.nojom.client.model.Cardlist;
import com.nojom.client.model.PaymentBraintreeCards;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;

import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static com.nojom.client.util.Constants.API_USER_WALLET_LIST;

public class ChooseAccountActivityVM extends AndroidViewModel implements View.OnClickListener, RecyclerviewAdapter.OnViewBindListner, RequestResponseListener {
    private ActivityChooseAccountBinding binding;
    private BaseActivity activity;
    private int google = 0, venmo = 0;
    private ArrayList<BraintreeCard.Data> paymentList;
    private RecyclerviewAdapter mAdapter;
    private BraintreeCard.Data paymentData;
    private boolean visibleGooglePay;
    private String accountId;
    private PaymentBraintreeCards paymentBraintreeCards;

    ChooseAccountActivityVM(Application application, ActivityChooseAccountBinding chooseAccountBinding, BaseActivity chooseAccountActivity) {
        super(application);
        binding = chooseAccountBinding;
        activity = chooseAccountActivity;
        initData();
    }

    private void initData() {
        binding.imgBack.setOnClickListener(this);
        binding.tvSave.setOnClickListener(this);
        binding.imgCheckUncheck.setOnClickListener(this);
        binding.imgCheckUncheckVenmo.setOnClickListener(this);
        binding.rlGooglePay.setOnClickListener(this);
        binding.tvAddAccount.setOnClickListener(this);
        binding.rlVenmoPay.setOnClickListener(this);

        if (activity.getIntent() != null) {
            accountId = activity.getIntent().getStringExtra(Constants.ACCOUNT_ID);
            paymentBraintreeCards = (PaymentBraintreeCards) activity.getIntent().getSerializableExtra(Constants.ACCOUNT_DATA);
            visibleGooglePay = activity.getIntent().getBooleanExtra("googlepay", false);
        }

        if (visibleGooglePay) {
            binding.rlGooglePay.setVisibility(View.VISIBLE);
        }
        binding.rlVenmoPay.setVisibility(View.VISIBLE);

        binding.noData.tvNoTitle.setText(activity.getString(R.string.no_accounts));
        binding.noData.tvNoDescription.setText(activity.getString(R.string.no_account_desc));
        binding.rvAccounts.setLayoutManager(new LinearLayoutManager(activity));

    }

    void onResumeMethod() {
        if (paymentBraintreeCards == null) {
            getAccounts();
        } else {
            updateUi(paymentBraintreeCards);
            paymentBraintreeCards = null;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                activity.onBackPressed();
                break;
            case R.id.tv_add_account:
                Intent intent = new Intent(activity, ChoosePaymentMethodActivity.class);
                intent.putExtra("isFromDeposite", true);
                activity.startActivity(intent);
                break;

            case R.id.img_check_uncheck_venmo:
            case R.id.rl_venmo_pay:
                if (venmo == -1) {
                    venmo = 0;
                    google = 0;
                    binding.imgCheckUncheckVenmo.setImageResource(R.drawable.circle_uncheck);
                    binding.imgCheckUncheck.setImageResource(R.drawable.circle_uncheck);
                } else if (venmo == 0) {
                    venmo = -1;
                    google = 0;
                    binding.imgCheckUncheckVenmo.setImageResource(R.drawable.circle_check);
                    binding.imgCheckUncheck.setImageResource(R.drawable.circle_uncheck);
                    accountId = "";
                    if (mAdapter != null) {
                        mAdapter.notifyDataSetChanged();
                    }
                    paymentData = null;
                }
                break;
            case R.id.img_check_uncheck:
            case R.id.rl_google_pay:
                if (google == -1) {
                    google = 0;
                    venmo = 0;
                    binding.imgCheckUncheck.setImageResource(R.drawable.circle_uncheck);
                    binding.imgCheckUncheckVenmo.setImageResource(R.drawable.circle_uncheck);
                } else if (google == 0) {
                    google = -1;
                    venmo = 0;
                    binding.imgCheckUncheck.setImageResource(R.drawable.circle_check);
                    binding.imgCheckUncheckVenmo.setImageResource(R.drawable.circle_uncheck);
                    accountId = "";
                    if (mAdapter != null) {
                        mAdapter.notifyDataSetChanged();
                    }
                    paymentData = null;
                }
                break;
            case R.id.tv_save:
                if (paymentData != null) {
                    Intent i = new Intent();
                    i.putExtra(Constants.ACCOUNT_DATA, paymentData);
                    activity.setResult(RESULT_OK, i);
                    activity.finish();
                } else if (visibleGooglePay && google == -1) {
                    Intent i = new Intent();
                    i.putExtra("googlepay", true);
                    activity.setResult(RESULT_OK, i);
                    activity.finish();
                } else if (venmo == -1) {
                    Intent i = new Intent();
                    i.putExtra("venmo", true);
                    activity.setResult(RESULT_OK, i);
                    activity.finish();
                } else {
                    activity.toastMessage(activity.getString(R.string.please_select_one_account_for_get_paid));
                }
                break;
        }
    }

    private void getAccounts() {
        if (!activity.isNetworkConnected()) {
            return;
        }

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_USER_WALLET_LIST, false, null);
    }

    private void setAdapter() {
        if (paymentList != null && paymentList.size() > 0) {
            binding.noData.llNoData.setVisibility(View.GONE);
            if (mAdapter == null) {
                mAdapter = new RecyclerviewAdapter(paymentList, R.layout.item_choose_account, this);
            }
            mAdapter.doRefresh(paymentList);
            if (binding.rvAccounts.getAdapter() == null) {
                binding.rvAccounts.setAdapter(mAdapter);
            }
        } else {
            if (visibleGooglePay) {
                binding.noData.llNoData.setVisibility(View.GONE);
            } else {
                binding.noData.llNoData.setVisibility(View.VISIBLE);
            }

            if (mAdapter != null)
                mAdapter.doRefresh(null);
        }
    }

    @Override
    public void bindView(View view, int position) {
        TextView tvEmail = view.findViewById(R.id.tv_email);
        TextView tvAccount = view.findViewById(R.id.tv_account);
        TextView tvStatus = view.findViewById(R.id.tv_status);
        ImageView imgCheckUnCheck = view.findViewById(R.id.img_check_uncheck);
        ImageView imgNext = view.findViewById(R.id.img_next);

        imgCheckUnCheck.setVisibility(View.VISIBLE);
        imgNext.setVisibility(View.GONE);

        BraintreeCard.Data item = paymentList.get(position);
        if (item.lastDigit != null) {
            tvEmail.setText(String.format(Locale.US,"**** **** **** %s", item.lastDigit));
        } else if (item.paypal != null) {
            tvEmail.setText(item.paypal.account);
        }

        if (item.paypal != null && item.paypal.provider != null) {
            tvAccount.setText(item.paypal.provider);
        } else {
            tvAccount.setBackground(null);
            if (item.expDate != null) {
                String[] expDate = item.expDate.split("/");
                tvAccount.setText(String.format(Locale.US,activity.getString(R.string.expire)+" %s/%s | %s", expDate[0], expDate[1], item.billingAddress.cardholderName));
            }
        }

        if (TextUtils.isEmpty(accountId)) {
            imgCheckUnCheck.setImageResource(R.drawable.circle_uncheck);
        } else if (!TextUtils.isEmpty(accountId) && (accountId.equals(item.token) || accountId.equals(item.paypal != null ? item.paypal.token : ""))) {
            imgCheckUnCheck.setImageResource(R.drawable.circle_check);
            google = 0;
            venmo = 0;
            binding.imgCheckUncheck.setImageResource(R.drawable.circle_uncheck);
            binding.imgCheckUncheckVenmo.setImageResource(R.drawable.circle_uncheck);
            paymentData = item;
        } else {
            imgCheckUnCheck.setImageResource(R.drawable.circle_uncheck);
        }

        if (item.paypal != null && item.paypal.verified != null) {
            if (item.paypal.verified.equals("0")) {
                tvStatus.setVisibility(View.VISIBLE);
                tvStatus.setText(activity.getString(R.string.not_verified));
                tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.red_border_5));
                tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.red_dark));
            } else {
                tvStatus.setVisibility(View.VISIBLE);
                tvStatus.setText(activity.getString(R.string.verified));
                tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.green_border_5));
                tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.greendark));
                if (item.paypal.token != null && item.paypal.token.equals(accountId)) {
                    imgCheckUnCheck.setImageResource(R.drawable.circle_check);
                } else {
                    imgCheckUnCheck.setImageResource(R.drawable.circle_uncheck);
                }
            }
        } else {
            tvStatus.setVisibility(View.GONE);
        }

        view.setOnClickListener(view1 -> {
            if (item.paypal != null && item.paypal.verified.equals("0")) {
                Intent i = new Intent(activity, EditPaypalActivity.class);
                i.putExtra(Constants.ACCOUNT_DATA, item);
                i.putExtra("isFromDeposite", true);
                activity.startActivity(i);
                return;
            }
            paymentData = item;
            if (item.paypal != null) {//for paypal
                accountId = item.paypal.token;
            } else {
                accountId = item.token;//for card
            }
            mAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data1) {
        if (url.equalsIgnoreCase(API_USER_WALLET_LIST)) {
            PaymentBraintreeCards model = PaymentBraintreeCards.getPaymentCards(responseBody);
            updateUi(model);
        }
    }

    private void updateUi(PaymentBraintreeCards model) {
        if (model != null && model.cardPaypal.isPrimary == 1) {//google
            binding.imgCheckUncheck.setImageResource(R.drawable.circle_check);
            binding.imgCheckUncheckVenmo.setImageResource(R.drawable.circle_uncheck);
            onClick(binding.rlGooglePay);
        } else if (model != null && model.cardPaypal.isPrimary == 3) {//venmo
            binding.imgCheckUncheck.setImageResource(R.drawable.circle_uncheck);
            binding.imgCheckUncheckVenmo.setImageResource(R.drawable.circle_check);
            onClick(binding.rlVenmoPay);
        } else {
            binding.imgCheckUncheck.setImageResource(R.drawable.circle_uncheck);
            binding.imgCheckUncheckVenmo.setImageResource(R.drawable.circle_uncheck);
        }

        paymentList = new ArrayList<>();

        if (model != null && model.cardPaypal != null && model.cardPaypal.cards != null) {
            paymentList = model.cardPaypal.cards;
        }

        if (model != null && model.cardPaypal != null && model.cardPaypal.paypal != null) {
            for (Cardlist.Paypal paypal : model.cardPaypal.paypal) {
                BraintreeCard.Data data = new BraintreeCard.Data();
                data.paypal = paypal;
                paymentList.add(data);
            }
        }

        setAdapter();
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
    }
}
