package com.nojom.client.ui.balance;

import static android.app.Activity.RESULT_OK;
import static com.nojom.client.util.Constants.API_EDIT_PRIMARY_ACCOUNT;
import static com.nojom.client.util.Constants.API_GET_PAYMENTMETHOD;
import static com.nojom.client.util.Constants.API_GET_STRIPE_CARD_LIST;
import static com.nojom.client.util.Constants.API_USER_WALLET_LIST;

import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;
import com.nojom.client.BuildConfig;
import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.FragmentBalancePaymentBinding;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.model.BraintreeCard;
import com.nojom.client.model.Cardlist;
import com.nojom.client.model.PaymentBraintreeCards;
import com.nojom.client.model.PaymentMethods;
import com.nojom.client.util.PaymentsUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PaymentFragmentVM extends AndroidViewModel implements View.OnClickListener, RequestResponseListener {
    private final FragmentBalancePaymentBinding binding;
    private final BaseFragment fragment;
    private List<BraintreeCard.Data> paymentList;
    private AccountsAdapter mAdapter;
    private PaymentsClient mPaymentsClient;

    PaymentFragmentVM(Application application, FragmentBalancePaymentBinding balancePaymentBinding, BaseFragment paymentFragment) {
        super(application);
        binding = balancePaymentBinding;
        fragment = paymentFragment;
        initData();
    }

    private void initData() {
        binding.tvAddAccount.setOnClickListener(this);
        binding.rlGooglePay.setOnClickListener(this);
//        binding.rlVenmoPay.setOnClickListener(this);

        binding.noData.tvNoTitle.setText(fragment.getString(R.string.no_accounts));
        binding.noData.tvNoDescription.setText(fragment.getString(R.string.no_account_desc));

        binding.rvAccounts.setLayoutManager(new LinearLayoutManager(fragment.activity));
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            binding.shimmerLayout.startShimmer();
            getUserWalletList();
        });
        //initialize google pay payment client
        mPaymentsClient = Wallet.getPaymentsClient(fragment.activity,
                new Wallet.WalletOptions.Builder()
                        .setEnvironment(BuildConfig.DEBUG ? WalletConstants.ENVIRONMENT_TEST : WalletConstants.ENVIRONMENT_PRODUCTION)
                        .build());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            possiblyShowGooglePayButton();
        }

    }

    void onResumeMethod() {
        getPaymentMethod();
    }

    private void getPaymentMethod() {
        if (!fragment.activity.isNetworkConnected())
            return;

        fragment.activity.isClickableView = true;

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, fragment.activity, API_GET_PAYMENTMETHOD, false, null);
    }

    private void getUserWalletList() {
        try {
            if (!fragment.activity.isNetworkConnected()) {
                binding.swipeRefreshLayout.setRefreshing(false);
                return;
            }

            if (!binding.swipeRefreshLayout.isRefreshing()) {
                binding.shimmerLayout.startShimmer();
                binding.noData.llNoData.setVisibility(View.INVISIBLE);
            }

            ApiRequest apiRequest = new ApiRequest();

            if (Task24Application.getInstance().paymentCardType.equalsIgnoreCase("Stripe")) {
                apiRequest.apiRequest(this, fragment.activity, API_GET_STRIPE_CARD_LIST, false, null);
            } else {
                apiRequest.apiRequest(this, fragment.activity, API_USER_WALLET_LIST, false, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void updateUi() {
        if (paymentList == null || paymentList.size() <= 0) {
            if (binding.rlGooglePay.isShown()) {
                binding.noData.llNoData.setVisibility(View.GONE);
            } else {
                binding.noData.llNoData.setVisibility(View.VISIBLE);
            }
        }
//        binding.rlVenmoPay.setVisibility(View.VISIBLE);
    }

    private void setAdapter() {
        if (paymentList != null && paymentList.size() > 0) {
            binding.noData.llNoData.setVisibility(View.GONE);
            if (mAdapter == null) {
                mAdapter = new AccountsAdapter(fragment.activity);
            }
            mAdapter.doRefresh(paymentList);
            if (binding.rvAccounts.getAdapter() == null) {
                binding.rvAccounts.setAdapter(mAdapter);
            }
        } else {
            if (binding.rlGooglePay.isShown()) {
                binding.noData.llNoData.setVisibility(View.GONE);
            } else {
                binding.noData.llNoData.setVisibility(View.VISIBLE);
            }
            if (mAdapter != null)
                mAdapter.doRefresh(null);
        }
        binding.swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_add_account) {
            Intent intent = new Intent(fragment.activity, ChoosePaymentMethodActivity.class);
            fragment.startActivityForResult(intent, 111);
        } else if (view.getId() == R.id.rl_google_pay && !binding.tvPrimary.isShown()) {
            showAddPrimaryDialog(1);
        } else if (view.getId() == R.id.rl_venmo_pay && !binding.tvPrimaryVenmo.isShown()) {
            showAddPrimaryDialog(3);
        }
    }

    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 111 && resultCode == RESULT_OK) {
            paymentList = new ArrayList<>();
            getUserWalletList();
        }
    }

    /**
     * Determine the viewer's ability to pay with a payment method supported by your app and display a
     * Google Pay payment button.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void possiblyShowGooglePayButton() {
        final Optional<JSONObject> isReadyToPayJson = PaymentsUtil.getIsReadyToPayRequest();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (!isReadyToPayJson.isPresent()) {
                return;
            }
        }
        IsReadyToPayRequest request = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            request = IsReadyToPayRequest.fromJson(isReadyToPayJson.get().toString());
        }
        if (request == null) {
            return;
        }

        // The call to isReadyToPay is asynchronous and returns a Task. We need to provide an
        // OnCompleteListener to be triggered when the result of the call is known.
        Task<Boolean> task = mPaymentsClient.isReadyToPay(request);
        task.addOnCompleteListener(fragment.activity,
                task1 -> {
                    if (task1.isSuccessful()) {
                        setGooglePayAvailable(task1.getResult());
                    } else {
                        Log.w("isReadyToPay failed", task1.getException());
                    }
                });
    }

    private void setGooglePayAvailable(boolean available) {
        if (available) {
            binding.rlGooglePay.setVisibility(View.VISIBLE);
        } else {
            binding.rlGooglePay.setVisibility(View.GONE);
        }
    }

    private void showAddPrimaryDialog(int primary) {
        final Dialog dialog = new Dialog(fragment.activity, R.style.Theme_AppCompat_Dialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_logout);
        dialog.setCancelable(true);

        TextView tvMessage = dialog.findViewById(R.id.tv_message);
        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        TextView tvChatnow = dialog.findViewById(R.id.tv_chat_now);
        String text = primary == 1 ? fragment.activity.getString(R.string.google) : fragment.activity.getString(R.string.venmo);
        tvMessage.setText(fragment.activity.getString(R.string.do_you_want_to_add) + " " + text + " " + fragment.activity.getString(R.string.as_primary_account));

        tvCancel.setOnClickListener(v -> dialog.dismiss());

        tvChatnow.setOnClickListener(v -> {
            dialog.dismiss();
            googleVenmoAddPrimary(primary);
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.gravity = Gravity.CENTER;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }


    private void googleVenmoAddPrimary(int primary) {
        if (!fragment.activity.isNetworkConnected())
            return;

        HashMap<String, String> map = new HashMap<>();
        map.put("is_primary", primary + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, fragment.activity, API_EDIT_PRIMARY_ACCOUNT, true, map);
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data1) {
        if (url.equalsIgnoreCase(API_USER_WALLET_LIST) || url.equalsIgnoreCase(API_GET_STRIPE_CARD_LIST)) {
            PaymentBraintreeCards model = PaymentBraintreeCards.getPaymentCards(responseBody);
            if (model != null && model.cardPaypal.isPrimary == 1) {//google
                binding.tvPrimary.setVisibility(View.VISIBLE);
                binding.tvPrimaryVenmo.setVisibility(View.GONE);
            } else if (model != null && model.cardPaypal.isPrimary == 3) {//venmo
                binding.tvPrimaryVenmo.setVisibility(View.VISIBLE);
                binding.tvPrimary.setVisibility(View.GONE);
            } else {
                binding.tvPrimary.setVisibility(View.GONE);
                binding.tvPrimaryVenmo.setVisibility(View.GONE);
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
            binding.shimmerLayout.stopShimmer();
            binding.shimmerLayout.setVisibility(View.GONE);
        } else if (url.equalsIgnoreCase(API_EDIT_PRIMARY_ACCOUNT)) {
            getUserWalletList();
        } else if (url.equalsIgnoreCase(API_GET_PAYMENTMETHOD)) {
            PaymentMethods paymentMethods = PaymentMethods.gePaymentMethodInfo(responseBody);
            fragment.activity.isClickableView = false;
            try {
                if (paymentMethods.paymentMethod.size() > 0) {
                    for (int i = 0; i < paymentMethods.paymentMethod.size(); i++) {
                        switch (paymentMethods.paymentMethod.get(i).name) {
                            case "Bank Card":
                                Task24Application.getInstance().paymentCardType = paymentMethods.paymentMethod.get(i).paymentMethod;
                                break;
                            case "PayPal":
                                Task24Application.getInstance().paymentPayPalType = paymentMethods.paymentMethod.get(i).paymentMethod;
                                break;
                            case "Google Pay":
                                if (paymentMethods.paymentMethod.get(i).active.equalsIgnoreCase("1")) {
                                    binding.rlGooglePay.setVisibility(View.VISIBLE);
                                } else {
                                    binding.rlGooglePay.setVisibility(View.GONE);
                                }
                                Task24Application.getInstance().paymentGoogleType = paymentMethods.paymentMethod.get(i).paymentMethod;
                                break;
                            case "Venmo":
//                                if (paymentMethods.paymentMethod.get(i).active.equalsIgnoreCase("1")) {
//                                    binding.rlVenmoPay.setVisibility(View.VISIBLE);
//                                } else {
//                                    binding.rlVenmoPay.setVisibility(View.GONE);
//                                }
//                                Task24Application.getInstance().paymentVenmoType = paymentMethods.paymentMethod.get(i).paymentMethod;
                                break;
                        }
                    }
                    getUserWalletList();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        if (url.equalsIgnoreCase(API_USER_WALLET_LIST)) {
            binding.shimmerLayout.stopShimmer();
            binding.shimmerLayout.setVisibility(View.GONE);
            binding.swipeRefreshLayout.setRefreshing(false);
        }
    }
}
