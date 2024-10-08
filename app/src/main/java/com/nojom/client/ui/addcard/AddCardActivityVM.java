package com.nojom.client.ui.addcard;

import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.AndroidViewModel;

import com.nojom.client.databinding.ContentAddcardBinding;
import com.prolificinteractive.creditcard.android.CreditCardTextWatcher;
import com.nojom.client.R;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.model.BraintreeCard;
import com.nojom.client.model.PaymentMethods;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Utils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static com.nojom.client.util.Constants.API_ADD_CARD;
import static com.nojom.client.util.Constants.API_DELETE_CARD;
import static com.nojom.client.util.Constants.API_DELETE_STRIPE_CARD;
import static com.nojom.client.util.Constants.API_EDIT_CARD;
import static com.nojom.client.util.Constants.API_GET_PAYMENTMETHOD;
import static com.nojom.client.util.Constants.API_STRIPE_ADD_CARD;
import static com.nojom.client.util.Constants.API_STRIPE_EDIT_CARD;
import static com.nojom.client.util.Constants.SYS_ID;

public class AddCardActivityVM extends AndroidViewModel implements View.OnClickListener, RequestResponseListener {
    private ContentAddcardBinding binding;
    private BaseActivity activity;
    private boolean isExpiry = false;
    private int addCard, editCard, cvv;
    private boolean isPrimary;
    private BraintreeCard.Data editCardData;
    private String cardId, paymentAccountId;
    private String paymentCardType = "";

    private String countryNameEN;

    AddCardActivityVM(Application application, ContentAddcardBinding addcardBinding, BaseActivity addCardActivity) {
        super(application);
        binding = addcardBinding;
        activity = addCardActivity;
        initData();
    }

    private void initData() {
        binding.toolbar.imgBack.setOnClickListener(this);
        binding.tvAddCard.setOnClickListener(this);
        binding.tvDeleteCard.setOnClickListener(this);

        if (activity.getIntent() != null) {
            cardId = activity.getIntent().getStringExtra(Constants.CARD_ID);
            addCard = activity.getIntent().getIntExtra(Constants.ADD_CARD_KEY, -1);
            editCard = activity.getIntent().getIntExtra(Constants.EDIT_CARD_KEY, -1);
            cvv = activity.getIntent().getIntExtra(Constants.CVV_VISIBLE_KEY, -1);
            if (activity.getIntent().hasExtra("cardData")) {
                editCardData = (BraintreeCard.Data) activity.getIntent().getExtras().get("cardData");
            }
        }

        binding.relIsPrimary.setVisibility(View.VISIBLE);
        binding.etCountryName.setClickable(false);
        binding.etCountryName.setEnabled(false);

        binding.segmentGroup.setOnPositionChangedListener(position -> {
            if (binding.tvAddCard.getText().toString().trim().equalsIgnoreCase("save")) {
                binding.loutCard.setVisibility(View.VISIBLE);
            }
            isPrimary = position != 0;
        });

        if (addCard == Constants.ADD_CARD_VAL) {
            binding.toolbar.tvTitle.setText(activity.getString(R.string.add_card_1));
            binding.tvAddCard.setText(activity.getString(R.string.add_card_1));
            binding.etCountryName.setText(binding.ccp.getDefaultCountryName());
            binding.etCountryName.setTag(binding.ccp.getDefaultCountryNameCode());
            countryNameEN = binding.ccp.getDefaultCountryNameEn();
        } else if (editCard == Constants.EDIT_CARD_VAL) {
            binding.loutCard.setVisibility(View.GONE);
            binding.toolbar.tvTitle.setText(activity.getString(R.string.edit_card));
            binding.tvAddCard.setText(activity.getString(R.string.save));
            textWatcher(binding.etBillingAddress);
            textWatcher(binding.etCountryName);
            textWatcher(binding.etSelectState);
            textWatcher(binding.etSelectCity);
            textWatcher(binding.etZipcode);
        } else if (cvv == Constants.CVV_VISIBLE_VAL) {
            binding.toolbar.tvTitle.setText(activity.getString(R.string.card));
            binding.tvAddCard.setText(activity.getString(R.string.pay_with_card));
            disableClick();
        } else {
            binding.etCountryName.setTag(binding.ccp.getDefaultCountryNameCode());
            binding.etCountryName.setText(binding.ccp.getDefaultCountryName());
            countryNameEN = binding.ccp.getDefaultCountryNameEn();
            binding.toolbar.tvTitle.setText(activity.getString(R.string.add_card_1));
            binding.tvAddCard.setText(activity.getString(R.string.add_card_1));
        }

        formateCardNumber();
        formateCardExpiry();

        textWatcher(binding.etFirstname);
        textWatcher(binding.etCardNumber);
        textWatcher(binding.etSecureCode);
        textWatcher(binding.etExpiry);

        binding.ccp.setOnCountryChangeListener(() -> {
            binding.etCountryName.setText(binding.ccp.getSelectedCountryName());
            binding.etCountryName.setTag(binding.ccp.getSelectedCountryNameCode());
            countryNameEN = binding.ccp.getSelectedCountryEnglishName();
        });

        getPaymentMethod();
    }

    private void getPaymentMethod() {
        if (!activity.isNetworkConnected())
            return;

        activity.isClickableView = true;

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_PAYMENTMETHOD, false, null);
    }

    private void disableClick() {
        binding.etFirstname.setEnabled(false);
        binding.etFirstname.setClickable(false);

        binding.etCardNumber.setEnabled(false);
        binding.etCardNumber.setClickable(false);

        binding.etExpiry.setEnabled(false);
        binding.etExpiry.setClickable(false);

        binding.etBillingAddress.setEnabled(false);
        binding.etBillingAddress.setClickable(false);

        binding.etSelectCity.setEnabled(false);
        binding.etSelectCity.setClickable(false);

        binding.etSelectState.setEnabled(false);
        binding.etSelectState.setClickable(false);

        binding.etCountryName.setEnabled(false);
        binding.etCountryName.setClickable(false);

        binding.etZipcode.setEnabled(false);
        binding.etZipcode.setClickable(false);

    }

    private void formateCardExpiry() {
        binding.etExpiry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isExpiry = before != 0;
            }

            @Override
            public void afterTextChanged(Editable s) {
                String source = s.toString();
                int length = source.length();

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(source);

                if (length == 3) {
                    if (isExpiry)
                        stringBuilder.deleteCharAt(length - 1);
                    else
                        stringBuilder.insert(length - 1, "/");

                    binding.etExpiry.setText(stringBuilder);
                    binding.etExpiry.setSelection(binding.etExpiry.getText().length());
                }
            }
        });
    }

    private void formateCardNumber() {
        if (!TextUtils.isEmpty(cardId) || editCard == Constants.EDIT_CARD_VAL || cvv == Constants.CVV_VISIBLE_VAL) {
            //edit card case
        } else {
            binding.etCardNumber.addTextChangedListener(new CreditCardTextWatcher());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                activity.onBackPressed();
                break;
            case R.id.tvDeleteCard:
                showDeleteDialog();
                break;
            case R.id.tvAddCard:
                if (binding.tvAddCard.getText().toString().trim().equalsIgnoreCase(activity.getString(R.string.pay_with_card))) {
                    Intent intent = activity.getIntent();
                    intent.putExtra("vvc", binding.etSecureCode.getText().toString().trim());
                    intent.putExtra("payment_account_id", String.valueOf(paymentAccountId));
                    activity.setResult(RESULT_OK, intent);
                    activity.finish();
                } else if (validation()) {
                    if (paymentCardType.equalsIgnoreCase("Stripe")) {
                        addStripeCardDetailToServer();
                    } else {
                        addCardDetailToServer();
                    }
                }
                break;
        }
    }

    private void textWatcher(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.llBillingAddress.setVisibility(View.VISIBLE);
                binding.loutCard.setVisibility(View.VISIBLE);
                if (editCard == Constants.EDIT_CARD_VAL)
                    binding.tvDeleteCard.setVisibility(View.VISIBLE);
                if (binding.tvAddCard.getText().toString().trim().equalsIgnoreCase("save")) {
                    binding.loutCard.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    private boolean validation() {
        if (activity.isEmpty(binding.etFirstname.getText().toString().trim())) {
            activity.validationError(activity.getString(R.string.enter_name));
            return false;
        }

        if (activity.isEmpty(binding.etCardNumber.getText().toString().trim())) {
            activity.validationError(activity.getString(R.string.enter_card_number));
            return false;
        }

        if (activity.isEmpty(binding.etExpiry.getText().toString().trim())) {
            activity.validationError(activity.getString(R.string.enter_expiry));
            return false;
        }

        if (activity.isEmpty(binding.etCountryName.getText().toString().trim())) {
            activity.validationError(activity.getString(R.string.select_country));
            return false;
        }

        if (activity.isEmpty(binding.etSelectState.getText().toString().trim())) {
            activity.validationError(activity.getString(R.string.select_state));
            return false;
        }

        if (activity.isEmpty(binding.etSelectCity.getText().toString().trim())) {
            activity.validationError(activity.getString(R.string.select_city));
            return false;
        }


        if (activity.isEmpty(binding.etBillingAddress.getText().toString().trim())) {
            activity.validationError(activity.getString(R.string.enter_billing_address));
            return false;
        }


        if (activity.isEmpty(binding.etZipcode.getText().toString().trim())) {
            activity.validationError(activity.getString(R.string.enter_zipcode));
            return false;
        }

        return true;
    }

    private void showDeleteDialog() {
        final Dialog dialog = new Dialog(activity, R.style.Theme_AppCompat_Dialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_delete_project);
        dialog.setCancelable(true);

        TextView tvMessage = dialog.findViewById(R.id.tv_message);
        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        TextView tvChatnow = dialog.findViewById(R.id.tv_chat_now);

        tvMessage.setText(Utils.fromHtml(activity.getString(R.string.delete_card_text)));

        tvCancel.setText(activity.getString(R.string.no));
        tvChatnow.setText(activity.getString(R.string.yes));
        tvCancel.setOnClickListener(v -> dialog.dismiss());

        tvChatnow.setOnClickListener(v -> {
            dialog.dismiss();
            deleteCard();
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.gravity = Gravity.CENTER;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }

    private void addCardDetailToServer() {
        if (!activity.isNetworkConnected())
            return;

        binding.progressBarSave.setVisibility(View.VISIBLE);
        activity.isClickableView = true;
        binding.tvAddCard.setVisibility(View.GONE);

        String firstName = binding.etFirstname.getText().toString().trim();
        String cardNumber = binding.etCardNumber.getText().toString().trim().replace(" ", "");
        String expiry = binding.etExpiry.getText().toString().trim();
//        String countryName = binding.etCountryName.getText().toString().trim();
        String state = binding.etSelectState.getText().toString().trim();
        String city = binding.etSelectCity.getText().toString().trim();
        String zipcode = binding.etZipcode.getText().toString().trim();
        String billingAddress = binding.etBillingAddress.getText().toString().trim();
        String cvc = binding.etSecureCode.getText().toString().trim();
        String[] expData = expiry.split("/");

        HashMap<String, String> map = new HashMap<>();

        map.put("exp_date", expiry);
        map.put("cvv", cvc);
        map.put("cardholder_name", firstName);
        map.put("street_address", billingAddress);
        map.put("extended_address", billingAddress);
        map.put("city", city);
        map.put("state", state);
        map.put("postal_code", zipcode);
        map.put("country", countryNameEN);
        map.put("is_primary", isPrimary ? "1" : "0");

        if (binding.tvAddCard.getText().toString().trim().equalsIgnoreCase("save")) {

            map.put("card_token", cardId);

            ApiRequest apiRequest = new ApiRequest();
            apiRequest.apiRequest(this, activity, API_EDIT_CARD, true, map);
        } else {
            map.put("card_number", cardNumber);

            ApiRequest apiRequest = new ApiRequest();
            apiRequest.apiRequest(this, activity, API_ADD_CARD, true, map);
        }
    }

    private void addStripeCardDetailToServer() {
        if (!activity.isNetworkConnected())
            return;

        activity.isClickableView = true;
        binding.progressBarSave.setVisibility(View.VISIBLE);
        binding.tvAddCard.setVisibility(View.GONE);

        String firstName = binding.etFirstname.getText().toString().trim();
        String cardNumber = binding.etCardNumber.getText().toString().trim().replace(" ", "");
//        String countryName = binding.etCountryName.getTag().toString().trim();
        String state = binding.etSelectState.getText().toString().trim();
        String city = binding.etSelectCity.getText().toString().trim();
        String zipcode = binding.etZipcode.getText().toString().trim();
        String billingAddress = binding.etBillingAddress.getText().toString().trim();
        String cvc = binding.etSecureCode.getText().toString().trim();

        HashMap<String, String> map = new HashMap<>();
        map.put("cardholder_name", firstName);
        map.put("street_address", billingAddress);
        map.put("extended_address", billingAddress);
        map.put("city", city);
        map.put("state", state);
        map.put("postal_code", zipcode);
        if (TextUtils.isEmpty(binding.etCountryName.getTag().toString())) {
            countryNameEN = "IN";
        } else {
            countryNameEN = binding.etCountryName.getTag().toString();
        }
        map.put("country", countryNameEN);
        map.put("is_primary", isPrimary ? "1" : "0");
        map.put("sys_id", "6");


        if (binding.tvAddCard.getText().toString().trim().equalsIgnoreCase("save")) {
            map.put("card_id", cardId);

            ApiRequest apiRequest = new ApiRequest();
            apiRequest.apiRequest(this, activity, API_STRIPE_EDIT_CARD, true, map);
        } else {
            String expiry = binding.etExpiry.getText().toString().trim();
            String[] expData = expiry.split("/");
            map.put("card_number", cardNumber);
            map.put("cvv", cvc);
            map.put("exp_month", expData[0]);
            map.put("exp_year", expData[1]);

            ApiRequest apiRequest = new ApiRequest();
            apiRequest.apiRequest(this, activity, API_STRIPE_ADD_CARD, true, map);
        }
    }

    private void deleteCard() {
        if (!activity.isNetworkConnected())
            return;

        binding.tvDeleteCard.setVisibility(View.INVISIBLE);
        binding.progressBarDelete.setVisibility(View.VISIBLE);
        activity.isClickableView = true;

        HashMap<String, String> map = new HashMap<>();

        ApiRequest apiRequest = new ApiRequest();
        if (paymentCardType.equalsIgnoreCase("Stripe")) {
            map.put("card_id", cardId);
            apiRequest.apiRequest(this, activity, API_DELETE_STRIPE_CARD, true, map);
        } else {
            map.put("card_token", cardId);
            apiRequest.apiRequest(this, activity, API_DELETE_CARD, true, map);
        }
    }

    private void setData(BraintreeCard.Data editCardData) {
        if (editCardData != null) {
            paymentAccountId = editCardData.token;
            binding.llBillingAddress.setVisibility(View.VISIBLE);
            binding.etFirstname.setText(editCardData.billingAddress.cardholderName);
            binding.etCardNumber.setText(String.format("**** **** **** %s", editCardData.lastDigit));
            binding.etCardNumber.setEnabled(false);
            String[] expDate = editCardData.expDate.split("/");
            binding.etExpiry.setText(String.format(Locale.US, "%s/%s", expDate[0], expDate[1]));
            binding.etBillingAddress.setText(editCardData.billingAddress.streetAddress);
            binding.etSelectCity.setText(editCardData.billingAddress.city);
            binding.etSelectState.setText(editCardData.billingAddress.state);
            binding.etZipcode.setText(editCardData.billingAddress.postalCode);

            binding.ccp.setCountryForNameCode(editCardData.billingAddress.country);
            binding.etCountryName.setText(binding.ccp.getSelectedCountryName());
            binding.etCountryName.setTag(binding.ccp.getSelectedCountryNameCode());
            countryNameEN = binding.ccp.getSelectedCountryEnglishName();

            if (editCardData.isPrimary == 1 /*|| editCardData.primary*/) {
                isPrimary = true;
                binding.segmentGroup.setPosition(1, true);
            } else {
                isPrimary = false;
                binding.segmentGroup.setPosition(0, true);
            }
        }

        if (editCard == Constants.EDIT_CARD_VAL)
            binding.loutCard.setVisibility(View.GONE);
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        activity.isClickableView = false;
        if (url.equalsIgnoreCase(API_ADD_CARD) || url.equalsIgnoreCase(API_EDIT_CARD)) {
            binding.progressBarSave.setVisibility(View.GONE);
            binding.tvAddCard.setVisibility(View.VISIBLE);
            activity.toastMessage(message);
            activity.setResult(RESULT_OK);
            activity.onBackPressed();
        } else if (url.equalsIgnoreCase(API_STRIPE_ADD_CARD) || url.equalsIgnoreCase(API_STRIPE_EDIT_CARD)) {
            binding.progressBarSave.setVisibility(View.GONE);
            binding.tvAddCard.setVisibility(View.VISIBLE);
            activity.toastMessage(message);
            activity.setResult(RESULT_OK);
            activity.onBackPressed();
        } else if (url.equalsIgnoreCase(API_DELETE_CARD) || url.equalsIgnoreCase(API_DELETE_STRIPE_CARD)) {
            binding.tvDeleteCard.setVisibility(View.VISIBLE);
            binding.progressBarDelete.setVisibility(View.GONE);
            activity.setResult(RESULT_OK);
            activity.onBackPressed();
        } else if (url.equalsIgnoreCase(API_GET_PAYMENTMETHOD)) {
            /*responseBody = "{\n" +
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
                        }
                    }
                }

                if (!TextUtils.isEmpty(cardId) || editCard == Constants.EDIT_CARD_VAL || cvv == Constants.CVV_VISIBLE_VAL) {
                    if (editCardData != null) {
                        if (paymentCardType.equalsIgnoreCase("Stripe")) {
                            cardId = editCardData.id;
                        } else {
                            cardId = editCardData.token;
                        }
                        setData(editCardData);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        if (url.equalsIgnoreCase(API_ADD_CARD) || url.equalsIgnoreCase(API_EDIT_CARD)) {
            binding.progressBarSave.setVisibility(View.GONE);
            binding.tvAddCard.setVisibility(View.VISIBLE);
            activity.toastMessage(message);
        } else if (url.equalsIgnoreCase(API_STRIPE_ADD_CARD) || url.equalsIgnoreCase(API_STRIPE_EDIT_CARD)) {
            binding.progressBarSave.setVisibility(View.GONE);
            binding.tvAddCard.setVisibility(View.VISIBLE);
        } else {
            binding.tvDeleteCard.setVisibility(View.VISIBLE);
            binding.progressBarDelete.setVisibility(View.GONE);
        }
        activity.isClickableView = false;
    }
}
