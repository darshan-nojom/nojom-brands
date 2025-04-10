package com.nojom.client.util

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.moneyhash.sdk.android.core.IntentContract
import com.moneyhash.sdk.android.core.IntentCreationParams
import com.moneyhash.sdk.android.model.IntentDetails
import com.moneyhash.sdk.android.vault.SecureTextField
import com.moneyhash.shared.securevault.fields.FieldType

class CardFormActivity : ComponentActivity() {
    private val viewModel: CardFormViewModelCopy by viewModels()
    private lateinit var intentId: String

    //    fun createIntent(message: String): Intent {
//        return Intent(this, CardFormActivity::class.java).apply {
//            putExtra("intentId", message)
//        }
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intentId = intent.getStringExtra("intentId").toString()

        setContent {
            TransparentDialogScreen(
                onClose = { finish() },
                intentId,
                this@CardFormActivity// Close the activity when the dialog is dismissed
            )
        }

    }

}

lateinit var gLauncher: ManagedActivityResultLauncher<IntentCreationParams, IntentDetails?>

@Composable
fun TransparentDialogScreen(
    onClose: () -> Unit, intentId: String, cardFormActivity: CardFormActivity
) {


    Box(
        modifier = Modifier
            .fillMaxSize()
            //.background(Color.Black.copy(alpha = 0.5f)) // Add dim background
            .clickable(onClick = { onClose() },
                indication = null,
                interactionSource = remember { MutableInteractionSource() }),
        contentAlignment = Alignment.Center
    ) {
        PaymentFormDialog(
            viewModel = CardFormViewModelCopy(),
            intentId = intentId,
            onDismissRequest = { onClose() },
            cardFormActivity
        )
    }
}

@Composable
fun PaymentFormDialog(
    viewModel: CardFormViewModelCopy,
    intentId: String,
    onDismissRequest: () -> Unit,
    cardFormActivity: CardFormActivity
) {
    var isLoading by remember { mutableStateOf(false) }

    gLauncher = rememberLauncherForActivityResult(
        IntentContract()
    ) { result ->
//        mutableSuccess.postValue(result)
        if (result != null) {
            handleIntentCase(
                result, cardFormActivity, onDismissRequest, viewModel, intentId, gLauncher
            )
            isLoading = false
        }
    }

    viewModel.mutableSuccess.observe(cardFormActivity) {
        if (it != null) {
            handleIntentCase(it, cardFormActivity, onDismissRequest, viewModel, intentId, gLauncher)
            isLoading = false
        }
    }

    viewModel.mutableProgress.observe(cardFormActivity) {
        if (it != null) {
            isLoading = it
        }
    }
    viewModel.mutableError.observe(cardFormActivity) {
        if (it != null) {
            Toast.makeText(
                cardFormActivity, it, Toast.LENGTH_SHORT
            ).show()
        }
    }


    Dialog(onDismissRequest = { onDismissRequest() }) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            shadowElevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Cardholder Name Field
                SecureTextField(
                    cardForm = viewModel.cardForm!!,
                    type = FieldType.CARD_HOLDER_NAME,
                    placeholder = { Text("Cardholder Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                )

                // Card Number Field
                SecureTextField(
                    cardForm = viewModel.cardForm!!,
                    type = FieldType.CARD_NUMBER,
                    placeholder = { Text("Card Number") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                )

                // Expiry Date and CVV Fields
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SecureTextField(
                        cardForm = viewModel.cardForm!!,
                        type = FieldType.EXPIRE_MONTH,
                        placeholder = { Text("MM") },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                    )

                    SecureTextField(
                        cardForm = viewModel.cardForm!!,
                        type = FieldType.EXPIRE_YEAR,
                        placeholder = { Text("YY") },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                    )

                    SecureTextField(
                        cardForm = viewModel.cardForm!!,
                        type = FieldType.CVV,
                        placeholder = { Text("CVV") },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                    )
                }

                // Pay Button
//                Button(
//                    onClick = {
//                        viewModel.handlePayment(intentId)
//                        //onDismissRequest()
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(48.dp)
//                ) {
//                    Text("Pay")
//                }

                PaymentButton(isLoading = isLoading, onClick = {
                    isLoading = true
                    viewModel.handlePayment(intentId)
                })
            }
        }
    }

}

//@Composable
//fun PaymentButton(
//    isLoading: Boolean, onClick: () -> Unit
//) {
//    Button(
//        onClick = { if (!isLoading) onClick() }, // Prevent multiple clicks when loading
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(48.dp),
//        enabled = !isLoading // Disable the button while loading
//    ) {
//        if (isLoading) {
//            CircularProgressIndicator(
//                color = Color.DarkGray, strokeWidth = 2.dp, modifier = Modifier.size(24.dp)
//            )
//        } else {
//            Text("Pay")
//        }
//    }
//}

fun handleIntentCase(
    it: IntentDetails,
    cardFormActivity: CardFormActivity,
    onDismissRequest: () -> Unit,
    viewModel: CardFormViewModelCopy,
    intentId: String,
    launcher: ManagedActivityResultLauncher<IntentCreationParams, IntentDetails?>
) {
    when {
        it.state.toString() == "TransactionFailed(recommendedMethods=null)" -> {
            Toast.makeText(
                cardFormActivity, "Transaction failed. No recommended methods.", Toast.LENGTH_SHORT
            ).show()
            cardFormActivity.setResult(Activity.RESULT_CANCELED)
            onDismissRequest()
        }

        it.state.toString() == "Closed" -> {
            Toast.makeText(
                cardFormActivity, "Transaction failed.", Toast.LENGTH_SHORT
            ).show()
            cardFormActivity.setResult(Activity.RESULT_CANCELED)
            onDismissRequest()
        }

        it.state.toString() == "IntentProcessed" -> {
            Toast.makeText(
                cardFormActivity, "Intent has been processed.", Toast.LENGTH_SHORT
            ).show()
            cardFormActivity.setResult(Activity.RESULT_OK)
            onDismissRequest()

//        }
        }

        it.state.toString() == "IntentForm" -> {
            viewModel.handleIntentPayment(intentId, launcher)
        }

        it.state.toString() == "UrlToRender" -> {
            viewModel.handleFormPayment(intentId, launcher)

        }
    }

}

