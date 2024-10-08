package com.nojom.client.ui.addcard;

import static android.app.Activity.RESULT_OK;
import static com.nojom.client.util.Constants.API_GENERATE_BRAINTREE_TOKEN;
import static com.nojom.client.util.Constants.API_GET_PAYMENTMETHOD;
import static com.nojom.client.util.Constants.API_GET_STRIPE_CARD_LIST;
import static com.nojom.client.util.Constants.API_USER_WALLET_LIST;
import static com.nojom.client.util.Constants.API_VERIFY_PAYPAL_PAYMENT;

import android.app.Application;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.braintreepayments.api.DropInClient;
import com.braintreepayments.api.DropInListener;
import com.braintreepayments.api.DropInRequest;
import com.braintreepayments.api.DropInResult;
import com.braintreepayments.api.PayPalVaultRequest;
import com.nojom.client.R;
import com.nojom.client.adapter.CardListAdapter;
import com.nojom.client.adapter.PaypalCardListAdapter;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ContentCardListBinding;
import com.nojom.client.model.BraintreeCard;
import com.nojom.client.model.BraintreeToken;
import com.nojom.client.model.Cardlist;
import com.nojom.client.model.PaymentBraintreeCards;
import com.nojom.client.model.PaymentMethods;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;

class CardListActivityVM extends AndroidViewModel implements RequestResponseListener, DropInListener/*, PaymentMethodNonceCreatedListener*/ {
    private final ContentCardListBinding binding;
    private final BaseActivity activity;
    private CardListAdapter mAdapter;
    private PaypalCardListAdapter mPaypalCardAdapter;
    private ArrayList<BraintreeCard.Data> cardList;
    private String paymentAccId;
    private String generatedNonce;
    private boolean isPaypal = false;
    private String paymentCardType = "";

    CardListActivityVM(Application application, ContentCardListBinding cardListBinding, BaseActivity cardListActivity) {
        super(application);
        binding = cardListBinding;
        activity = cardListActivity;
        initData();
    }

    private void initData() {
        binding.toolbar.imgBack.setOnClickListener(v -> activity.onBackPressed());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        binding.rlCardList.setLayoutManager(linearLayoutManager);

        if (activity.getIntent() != null) {
            isPaypal = activity.getIntent().getBooleanExtra("is_paypal", false);
            paymentAccId = activity.getIntent().getStringExtra("payment_account_id");
        }

        if (isPaypal) {
            binding.toolbar.tvTitle.setText(activity.getString(R.string.my_paypal));
        } else {
            binding.toolbar.tvTitle.setText(activity.getString(R.string.my_cards));
        }

        binding.tvAddNewCard.setOnClickListener(v -> {
            if (isPaypal) {
                generateBrantreeToken();
            } else {
                Intent intent = new Intent(activity, AddCardActivity.class);
                intent.putExtra(Constants.ADD_CARD_KEY, Constants.ADD_CARD_VAL);
                activity.startActivity(intent);
            }
        });

        if (isPaypal) {
            binding.noData.tvNoTitle.setText(activity.getString(R.string.add_account));
            binding.noData.tvNoDescription.setText(activity.getString(R.string.please_click_on_add_button_to_add_paypal_account));
        }
    }

    private void getPaymentMethod() {
        if (!activity.isNetworkConnected())
            return;

        activity.isClickableView = true;

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_PAYMENTMETHOD, false, null);
    }


    void updateList(int position, int type) {
        if (cardList != null && cardList.size() > 0) {
            for (int i = 0; i < cardList.size(); i++) {
                if (!TextUtils.isEmpty(paymentAccId)) {
                    cardList.get(i).localPrimary = paymentAccId.equals(cardList.get(i).token);
                }
            }
            if (mAdapter != null) {
                binding.rlCardList.post(() -> mAdapter.notifyDataSetChanged());
            }
        }

        if (type != 0) {
            Intent intent = activity.getIntent();
            intent.putExtra("payment_account", cardList.get(position).lastDigit + "");
            intent.putExtra("payment_account_id", cardList.get(position).token);
            intent.putExtra("cardNo", cardList.get(position).card_number + "");
            intent.putExtra("cardExp", cardList.get(position).expDate);
            activity.setResult(RESULT_OK, intent);
            activity.finish();
        }
    }

    void updatePaypalList(int position, int type) {
        if (cardList != null && cardList.size() > 0) {
            for (int i = 0; i < cardList.size(); i++) {
                if (!TextUtils.isEmpty(paymentAccId)) {
                    cardList.get(i).localPrimary = paymentAccId.equals(cardList.get(i).paypal.token);
                }
            }
            if (mPaypalCardAdapter != null) {
                binding.rlCardList.post(() -> mPaypalCardAdapter.notifyDataSetChanged());
            }
        }

        if (type != 0) {
            Intent intent = activity.getIntent();
            intent.putExtra("paypal_payment_account", cardList.get(position).paypal.account + "");
            intent.putExtra("paypal_payment_account_id", cardList.get(position).paypal.token);
            activity.setResult(RESULT_OK, intent);
            activity.finish();
        }
    }

    private void setAdapter() {
        if (cardList != null && cardList.size() > 0) {
            binding.tvNoResult.setVisibility(View.GONE);
            mAdapter = new CardListAdapter(activity, cardList, paymentCardType);
            binding.rlCardList.setAdapter(mAdapter);
            binding.rlCardList.setVisibility(View.VISIBLE);
        } else {
            binding.tvNoResult.setVisibility(View.VISIBLE);
            binding.rlCardList.setVisibility(View.GONE);
        }
    }

    private void setPaypalAdapter() {
        if (cardList != null && cardList.size() > 0) {

            binding.noData.llNoData.setVisibility(View.GONE);
            mPaypalCardAdapter = new PaypalCardListAdapter(activity, cardList);

            binding.rlCardList.setAdapter(mPaypalCardAdapter);
            binding.rlCardList.setVisibility(View.VISIBLE);
        } else {
            binding.noData.llNoData.setVisibility(View.VISIBLE);
            binding.rlCardList.setVisibility(View.GONE);
        }
    }

    private void getCardList() {
        if (!activity.isNetworkConnected())
            return;

        ApiRequest apiRequest = new ApiRequest();
        if (paymentCardType.equalsIgnoreCase("Stripe")) {
            apiRequest.apiRequest(this, activity, API_GET_STRIPE_CARD_LIST, false, null);
        } else {
            apiRequest.apiRequest(this, activity, API_USER_WALLET_LIST, false, null);
        }
    }

    void onResumeMethod() {
        if (isPaypal) {
            binding.tvAddNewCard.setText(activity.getString(R.string.add_paypal_card));
        }
        getPaymentMethod();
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data1) {
        if (url.equalsIgnoreCase(API_GENERATE_BRAINTREE_TOKEN)) {
            BraintreeToken braintreeToken = BraintreeToken.getBraintreeToken(responseBody);
            if (braintreeToken != null && braintreeToken.token != null) {
                generateNonce(braintreeToken.token);
            }
        } else if (url.equalsIgnoreCase(API_VERIFY_PAYPAL_PAYMENT)) {
            getCardList();
        } else if (url.equalsIgnoreCase(API_USER_WALLET_LIST) || url.equalsIgnoreCase(API_GET_STRIPE_CARD_LIST)) {
            PaymentBraintreeCards model = PaymentBraintreeCards.getPaymentCards(responseBody);

            cardList = new ArrayList<>();
            if (isPaypal) {//in case of paypal add account
                if (model != null && model.cardPaypal != null && model.cardPaypal.paypal != null && model.cardPaypal.paypal.size() > 0) {
                    for (Cardlist.Paypal paypal : model.cardPaypal.paypal) {
                        BraintreeCard.Data data = new BraintreeCard.Data();
                        data.paypal = paypal;
                        cardList.add(data);
                    }

                    binding.tvAddNewCard.setText(activity.getString(R.string.add_paypal_card));
                    updatePaypalList(0, 0);
                }
                setPaypalAdapter();
            } else {
                if (model != null && model.cardPaypal != null && model.cardPaypal.cards != null && model.cardPaypal.cards.size() > 0) {//cards
                    cardList = model.cardPaypal.cards;
                    updateList(0, 0);
                }
                setAdapter();
            }
        } else if (url.equalsIgnoreCase(API_GET_PAYMENTMETHOD)) {
            activity.isClickableView = false;
            /*responseBody="{\n" +
                    "  \"client_balance\": 547.65,\n" +
                    "  \"payment_method\": [\n" +
                    "    {\n" +
                    "      \"id\": 6,\n" +
                    "      \"name\": \"Bank Card\",\n" +
                    "      \"payment_method\": \"Stripe\",\n" +
                    "      \"payment_method_id\": 2,\n" +
                    "      \"active\": \"1\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 7,\n" +
                    "      \"name\": \"Apple Pay\",\n" +
                    "      \"payment_method\": \"Stripe\",\n" +
                    "      \"payment_method_id\": 2,\n" +
                    "      \"active\": \"1\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 8,\n" +
                    "      \"name\": \"Bank Transfer\",\n" +
                    "      \"payment_method\": \"Stripe\",\n" +
                    "      \"payment_method_id\": 2,\n" +
                    "      \"active\": \"1\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";*/
            PaymentMethods paymentMethods = PaymentMethods.gePaymentMethodInfo(responseBody);
            try {
                if (paymentMethods.paymentMethod.size() > 0) {
                    for (int i = 0; i < paymentMethods.paymentMethod.size(); i++) {
                        if (paymentMethods.paymentMethod.get(i).name.equals("Bank Card")) {
                            paymentCardType = paymentMethods.paymentMethod.get(i).paymentMethod;
                            getCardList();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        binding.tvNoResult.setVisibility(View.VISIBLE);
        binding.rlCardList.setVisibility(View.GONE);
    }

    public void generateBrantreeToken() {
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GENERATE_BRAINTREE_TOKEN, false, null);
    }

    public void generateNonce(String token) {
        /*BraintreeFragment mBraintreeFragment;
        try {
            mBraintreeFragment = BraintreeFragment.newInstance(activity, token);

            mBraintreeFragment.addListener(CardListActivityVM.this);

            PayPalRequest request = new PayPalRequest("1")
                    .localeCode("US")
                    .currencyCode("USD")
                    .billingAgreementDescription("Test")
                    .intent(PayPalRequest.INTENT_AUTHORIZE);

            PayPal.requestOneTimePayment(mBraintreeFragment, request);
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }*/


        DropInRequest dropInRequest = new DropInRequest();
        DropInClient dropInClient = new DropInClient(activity, token);
        dropInClient.setListener(this);
        dropInClient.launchDropIn(dropInRequest);

//        PayPalVaultRequest payPalRequest = new PayPalVaultRequest();
//        dropInRequest.setPayPalRequest(payPalRequest);
    }

    /*@Override
    public void onPaymentMethodNonceCreated(PaymentMethodNonce paymentMethodNonce) {
        generatedNonce = paymentMethodNonce.getNonce();
        verifyPaypal();
    }*/

    private void verifyPaypal() {
        if (!activity.isNetworkConnected())
            return;

        HashMap<String, String> map = new HashMap<>();
        map.put("nonce", generatedNonce);

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_VERIFY_PAYPAL_PAYMENT, true, map);
    }

    @Override
    public void onDropInSuccess(@NonNull DropInResult dropInResult) {
        if (dropInResult.getPaymentMethodNonce() != null) {
            generatedNonce = dropInResult.getPaymentMethodNonce().getString();
            verifyPaypal();
        }
    }

    @Override
    public void onDropInFailure(@NonNull Exception error) {
        binding.tvNoResult.setVisibility(View.VISIBLE);
        binding.rlCardList.setVisibility(View.GONE);
    }
}
