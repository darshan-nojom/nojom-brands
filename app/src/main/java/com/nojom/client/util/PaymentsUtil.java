/*
 * Copyright 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nojom.client.util;

import android.app.Activity;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.nojom.client.BuildConfig;
import com.nojom.client.model.UserPaymentInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import static com.nojom.client.util.Constants.STRIPE_KEY_PRODUCTION;
import static com.nojom.client.util.Constants.STRIPE_KEY_TEST;

/**
 * Contains helper static methods for dealing with the Payments API.
 *
 * <p>Many of the parameters used in the code are optional and are set here merely to call out their
 * existence. Please consult the documentation to learn more and feel free to remove ones not
 * relevant to your implementation.
 */
public class PaymentsUtil {
    private static final BigDecimal MICROS = new BigDecimal(1000000d);

    private PaymentsUtil() {
    }

    /**
     * Create a Google Pay API base request object with properties used in all requests.
     *
     * @return Google Pay API base request object.
     * @throws JSONException
     */
    private static JSONObject getBaseRequest() throws JSONException {
        return new JSONObject().put("apiVersion", 2).put("apiVersionMinor", 0);
    }


    /**
     * Creates an instance of {@link PaymentsClient} for use in an {@link Activity} using the
     * environment and theme set in {@link Constants}.
     *
     * @param activity is the caller's activity.
     */
    public static PaymentsClient createPaymentsClient(Activity activity) {
        Wallet.WalletOptions walletOptions =
                new Wallet.WalletOptions.Builder().setEnvironment(Constants.PAYMENTS_ENVIRONMENT).build();
        return Wallet.getPaymentsClient(activity, walletOptions);
    }

    /**
     * Gateway Integration: Identify your gateway and your app's gateway merchant identifier.
     *
     * <p>The Google Pay API response will return an encrypted payment method capable of being charged
     * by a supported gateway after payer authorization.
     *
     * <p>TODO: Check with your gateway on the parameters to pass and modify them in Constants.java.
     *
     * @return Payment data tokenization for the CARD payment method.
     * @throws JSONException
     * @see <a href=
     * "https://developers.google.com/pay/api/android/reference/object#PaymentMethodTokenizationSpecification">PaymentMethodTokenizationSpecification</a>
     */
    private static JSONObject getGatewayTokenizationSpecification() throws JSONException {
        return new JSONObject() {{
            put("type", "PAYMENT_GATEWAY");
            put("parameters", new JSONObject() {
                {
                    put("gateway", "stripe");
                    put("stripe:version", "2019-12-03");
//                    put("gatewayMerchantId", "pk_test_n05ja72jeYH7zsKIZQL9q513");
                    put("stripe:publishableKey", BuildConfig.DEBUG ? STRIPE_KEY_TEST : STRIPE_KEY_PRODUCTION);
                }
            });
        }};
    }


    /**
     * Card networks supported by your app and your gateway.
     */
    private static JSONArray getAllowedCardNetworks() {
        return new JSONArray(Constants.SUPPORTED_NETWORKS);
    }

    /**
     * Card authentication methods supported by your app and your gateway.
     */
    private static JSONArray getAllowedCardAuthMethods() {
        return new JSONArray(Constants.SUPPORTED_METHODS);
    }

    /**
     * Describe your app's support for the CARD payment method.
     */
    private static JSONObject getBaseCardPaymentMethod() throws JSONException {
        JSONObject cardPaymentMethod = new JSONObject();
        cardPaymentMethod.put("type", "CARD");

        JSONObject parameters = new JSONObject();
        parameters.put("allowedAuthMethods", getAllowedCardAuthMethods());
        parameters.put("allowedCardNetworks", getAllowedCardNetworks());
        // Optionally, you can add billing address/phone number associated with a CARD payment method.
        parameters.put("billingAddressRequired", true);

        JSONObject billingAddressParameters = new JSONObject();
        billingAddressParameters.put("format", "MIN");
//        billingAddressParameters.put("city", userPaymentData.city);
//        billingAddressParameters.put("email", userPaymentData.email);
//        billingAddressParameters.put("name", userPaymentData.username);
//        billingAddressParameters.put("country", userPaymentData.region + "," + userPaymentData.country);
//        billingAddressParameters.put("phone", userPaymentData.phone);

        parameters.put("billingAddressParameters", billingAddressParameters);

        cardPaymentMethod.put("parameters", parameters);

        return cardPaymentMethod;
    }

    private static JSONObject getCardPaymentMethod() throws JSONException {
//        final JSONObject tokenizationSpec = new GooglePayConfig(activity).getTokenizationSpecification();

        JSONObject cardPaymentMethod = getBaseCardPaymentMethod();
        cardPaymentMethod.put("tokenizationSpecification", getGatewayTokenizationSpecification());//getGatewayTokenizationSpecification());

        return cardPaymentMethod;
    }

    /**
     * Describe the expected returned payment data for the CARD payment method
     */
//    private static JSONObject getCardPaymentMethod(Activity activity, UserPaymentInfo.Data userPaymentData) throws JSONException {
//        final JSONObject tokenizationSpec = new GooglePayConfig(activity).getTokenizationSpecification();
//
//        JSONObject cardPaymentMethod = getBaseCardPaymentMethod(userPaymentData);
//        cardPaymentMethod.put("tokenizationSpecification", getGatewayTokenizationSpecification());//getGatewayTokenizationSpecification());
//
//        return cardPaymentMethod;
//    }

    /**
     * An object describing accepted forms of payment by your app, used to determine a viewer's
     * readiness to pay.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Optional<JSONObject> getIsReadyToPayRequest() {
        try {
            JSONObject isReadyToPayRequest = getBaseRequest();
            isReadyToPayRequest.put(
                    "allowedPaymentMethods", new JSONArray().put(getBaseCardPaymentMethod()));

            return Optional.of(isReadyToPayRequest);
        } catch (JSONException e) {
            return Optional.empty();
        }
    }

    /**
     * Provide Google Pay API with a payment amount, currency, and amount status.
     */
    private static JSONObject getTransactionInfo(String price) throws JSONException {
        JSONObject transactionInfo = new JSONObject();
        transactionInfo.put("totalPrice", price);//TODO: Remove static price and set original price over here
        transactionInfo.put("totalPriceStatus", "FINAL");
        transactionInfo.put("countryCode", Constants.COUNTRY_CODE);
        transactionInfo.put("currencyCode", Constants.CURRENCY_CODE);

        return transactionInfo;
    }

    /**
     * Information about the merchant requesting payment information
     */
    private static JSONObject getMerchantInfo(UserPaymentInfo.Data userPaymentData) throws JSONException {
        Log.e("USERNAME G", "" + userPaymentData.username);

        return new JSONObject().put("merchantName", userPaymentData.username)
                /*.put("city", userPaymentData.city)
                .put("email", userPaymentData.email)
                .put("name", userPaymentData.username)
                .put("country", userPaymentData.region + "," + userPaymentData.country)
                .put("phone", userPaymentData.phone)*/;

    }


    private static JSONObject getTransactionInfo(String price, Integer orderId) throws JSONException {
        JSONObject transactionInfo = new JSONObject();
        transactionInfo.put("totalPrice", price);
        transactionInfo.put("totalPriceStatus", "FINAL");
        transactionInfo.put("countryCode", Constants.COUNTRY_CODE);
        transactionInfo.put("currencyCode", Constants.CURRENCY_CODE);
//        transactionInfo.put("description", "Order Item # " + orderId);

        return transactionInfo;
    }

    /**
     * Information about the merchant requesting payment information
     */
    private static JSONObject getMerchantInfo(String username) throws JSONException {
        Log.e("USERNAME G", "" + username);

        return new JSONObject().put("merchantName", username)
                /*.put("city", userPaymentData.city)
                .put("email", userPaymentData.email)
                .put("name", userPaymentData.username)
                .put("country", userPaymentData.region + "," + userPaymentData.country)
                .put("phone", userPaymentData.phone)*/;

    }

    /**
     * An object describing information requested in a Google Pay payment sheet
     */
  /*  @RequiresApi(api = Build.VERSION_CODES.N)
    public static Optional<JSONObject> getPaymentDataRequest(String price, ProjectByID projectData, Activity activity, UserPaymentInfo.Data userPaymentData) {
        try {
            JSONObject paymentDataRequest = PaymentsUtil.getBaseRequest();
//            paymentDataRequest.put(
//                    "allowedPaymentMethods", new JSONArray().put(PaymentsUtil.getCardPaymentMethod(activity, userPaymentData)));
            paymentDataRequest.put("transactionInfo", PaymentsUtil.getTransactionInfo(price));
            paymentDataRequest.put("merchantInfo", PaymentsUtil.getMerchantInfo(userPaymentData));

            paymentDataRequest.put("shippingAddressRequired", true);

            JSONObject shippingAddressParameters = new JSONObject();
            shippingAddressParameters.put("phoneNumberRequired", true);
            JSONArray allowedCountryCodes = new JSONArray(Constants.SHIPPING_SUPPORTED_COUNTRIES);

            shippingAddressParameters.put("allowedCountryCodes", allowedCountryCodes);
            paymentDataRequest.put("shippingAddressParameters", shippingAddressParameters);
            return Optional.of(paymentDataRequest);
        } catch (JSONException e) {
            return Optional.empty();
        }
    }*/

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Optional<JSONObject> getPaymentDataRequest(String price, String username, int orderId) {
        try {
            JSONObject paymentDataRequest = PaymentsUtil.getBaseRequest();
            paymentDataRequest.put(
                    "allowedPaymentMethods", new JSONArray().put(PaymentsUtil.getCardPaymentMethod()));
            paymentDataRequest.put("transactionInfo", PaymentsUtil.getTransactionInfo(price, orderId));
            paymentDataRequest.put("merchantInfo", PaymentsUtil.getMerchantInfo(username));

            paymentDataRequest.put("shippingAddressRequired", true);

            JSONObject shippingAddressParameters = new JSONObject();
            shippingAddressParameters.put("phoneNumberRequired", true);
            JSONArray allowedCountryCodes = new JSONArray(Constants.SHIPPING_SUPPORTED_COUNTRIES);

            shippingAddressParameters.put("allowedCountryCodes", allowedCountryCodes);
            paymentDataRequest.put("shippingAddressParameters", shippingAddressParameters);
            return Optional.of(paymentDataRequest);
        } catch (JSONException e) {
            return Optional.empty();
        }
    }

    public static String microsToString(Double micros) {
        return new BigDecimal(micros).divide(MICROS).setScale(2, RoundingMode.HALF_EVEN).toString();
    }
}
