package com.nojom.client.util

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneyhash.sdk.android.common.IntentType
import com.moneyhash.sdk.android.common.LogLevel
import com.moneyhash.sdk.android.core.IntentCreationParams
import com.moneyhash.sdk.android.core.MoneyHashSDKBuilder
import com.moneyhash.sdk.android.model.IntentDetails
import com.moneyhash.sdk.android.model.embed.EmbedStyle
import com.moneyhash.sdk.android.vault.CardForm
import com.moneyhash.sdk.android.vault.CardFormBuilder
import com.moneyhash.shared.datasource.network.model.payment.methods.MethodType
import com.moneyhash.shared.errorhandling.MHThrowable
import kotlinx.coroutines.launch

class CardFormViewModelCopy : ViewModel() {
    var cardForm: CardForm? = null
    private val moneyHash = MoneyHashSDKBuilder
        .setPublicKey("public.obtP68uX.bcKjLr6yYmjjZNxIGqAyWnz6zUmGEzLNrh1TFuv1")
//        .setNativeGooglePayConfig(
//            NativeGooglePayConfig(
//                environment = GooglePayEnvironment.TEST,
//                allowedCards = AllowedCards.entries,
//                supportedMethods = SupportedMethods.entries
//            )
//        )
        .build()

    var mutableSuccess = MutableLiveData<IntentDetails>()
    var mutableProgress = MutableLiveData<Boolean>()
    var mutableError = MutableLiveData<String>()

    init {
//        val moneyHashSDK = com.moneyhash.sdk.android.core.MoneyHashSDKBuilder
//            .setPublicKey("public.obtP68uX.bcKjLr6yYmjjZNxIGqAyWnz6zUmGEzLNrh1TFuv1")
//            .build()
        setupCardForm()

    }

    private fun setupCardForm() {
        moneyHash.setLogLevel(LogLevel.Verbose)
        cardForm = CardFormBuilder()
            .setCardNumberField { state ->
                println("Card Number Field State: $state")
            }
            .setCVVField { state ->
                println("CVV Field State: $state")
            }
            .setCardHolderNameField { state ->
                println("Card Holder Name Field State: $state")
            }
            .setExpireMonthField { state ->
                println("Expire Month Field State: $state")
            }
            .setExpireYearField { state ->
                println("Expire Year Field State: $state")
            }
            .setCardBrandChangeListener { brand ->
                println("Card Brand Changed: $brand")
            }
            .build()
    }

    fun handlePayment(intentId: String) {
        viewModelScope.launch {
            try {
                val cardData = cardForm?.collect()
                if (cardData == null || cardData.cardHolderName?.isEmpty() == true || cardData.firstSixDigits?.isEmpty() == true
                    || cardData.expiryMonth?.isEmpty() == true || cardData.expiryYear?.isEmpty() == true || cardData.cvv?.isEmpty() == true
                ) {
                    println("Invalid Card Information")
                    mutableError.value = "Invalid Card Information"
                    mutableProgress.value = false
                    return@launch
                }
                mutableProgress.value = true
                moneyHash.proceedWithMethod(
                    intentId,
                    IntentType.Payment,
                    "CARD",
                    MethodType.PAYMENT_METHOD,
                    null
                )
                val intentDetails = cardForm?.pay(
                    intentId = intentId,
                    cardData = cardData,
                    saveCard = false, // Set to true to save the card (optional)
                    billingData = null, // Optional
                    shippingData = null  // Optional
                )

                println("Payment Successful: $intentDetails")
                mutableSuccess.postValue(intentDetails)
            } catch (throwable: Throwable) {
                mutableProgress.value = false
                if (throwable is MHThrowable) {
                    println("Payment Error: ${throwable.message}")
                    mutableError.value = "${throwable.message}"
                } else {
                    println("Payment Error: ${throwable.message}")
                    mutableError.value = "${throwable.message}"
                }
            }
        }
    }

    fun handleIntentPayment(
        intentId: String,
        launcher: ManagedActivityResultLauncher<IntentCreationParams, IntentDetails?>
    ) {

        viewModelScope.launch {
            try {
                val intentId = intentId
                val intentType = IntentType.Payment // or IntentType.Payout
                val embedStyle: EmbedStyle? = null // Optional: Customize the form's appearance

                moneyHash.renderForm(
                    intentId = intentId,
                    intentType = intentType,
                    embedStyle = embedStyle,
                    launcher = launcher
                )
                //println("Payment Successful: $intentDetails")
//                mutableSuccess.postValue(intentDetails)
            } catch (throwable: Throwable) {

                if (throwable is MHThrowable) {
                    println("Payment Error: ${throwable.message}")
                } else {
                    println("Payment Error: ${throwable.message}")
                }
            }
        }
    }

    fun handleFormPayment(
        intentId: String,
        launcher: ManagedActivityResultLauncher<IntentCreationParams, IntentDetails?>
    ) {

        viewModelScope.launch {
            val intentId = intentId
            val intentType = IntentType.Payment // or IntentType.Payout

            try {
                moneyHash.renderForm(
                    intentId = intentId,
                    intentType = intentType,
                    launcher = launcher
                )
            } catch (throwable: Throwable) {
                println(throwable)
            }
        }
    }
}