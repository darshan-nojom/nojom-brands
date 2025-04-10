package com.nojom.client.util

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moneyhash.sdk.android.core.IntentContract
import com.moneyhash.sdk.android.core.IntentCreationParams
import com.moneyhash.sdk.android.model.IntentDetails
import com.moneyhash.sdk.android.vault.SecureTextField
import com.moneyhash.shared.securevault.fields.FieldType
import com.nojom.client.R

class AddCardActivity : ComponentActivity() {
    private val viewModel: CardFormViewModelCopy by viewModels()
    private lateinit var intentId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intentId = intent.getStringExtra("intentId").toString()
        setContent {
            AddCardScreen(viewModel, this@AddCardActivity, intentId)
        }
    }
}

lateinit var gLauncher1: ManagedActivityResultLauncher<IntentCreationParams, IntentDetails?>

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCardScreen(
    viewModel: CardFormViewModelCopy, addCardActivity: AddCardActivity, intentId: String
) {

    var isLoading by remember { mutableStateOf(false) }

    gLauncher1 = rememberLauncherForActivityResult(
        IntentContract()
    ) { result ->
//        mutableSuccess.postValue(result)
        if (result != null) {
            handleIntentCase(
                result, addCardActivity, viewModel, intentId, gLauncher1
            )
            isLoading = false
        }
    }

    viewModel.mutableSuccess.observe(addCardActivity) {
        if (it != null) {
            handleIntentCase(it, addCardActivity, viewModel, intentId, gLauncher1)
            isLoading = false
        }
    }

    viewModel.mutableProgress.observe(addCardActivity) {
        if (it != null) {
            isLoading = it
        }
    }
    viewModel.mutableError.observe(addCardActivity) {
        if (it != null) {
            Toast.makeText(
                addCardActivity, it, Toast.LENGTH_SHORT
            ).show()
        }
    }


    var name by remember { mutableStateOf(TextFieldValue("")) }
    var cardNumber by remember { mutableStateOf(TextFieldValue("")) }
    var expiryDate by remember { mutableStateOf(TextFieldValue("")) }
    var cvv by remember { mutableStateOf(TextFieldValue("")) }
    var country by remember { mutableStateOf(TextFieldValue("")) }
    var city by remember { mutableStateOf(TextFieldValue("")) }
    var address by remember { mutableStateOf(TextFieldValue("")) }
    var state by remember { mutableStateOf(TextFieldValue("")) }
    var zipCode by remember { mutableStateOf(TextFieldValue("")) }
    var saveCard by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = com.nojom.client.R.color.background))
            .padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 5.dp)
            .verticalScroll(scrollState)
    ) {
        // Back Button & Title
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { addCardActivity.finish() }, Modifier.size(25.dp)) {
                Icon(
                    painter = painterResource(id = com.nojom.client.R.drawable.back),
                    tint = Color.Black,
                    contentDescription = "Back", modifier = Modifier.size(18.dp)
                )
            }
//            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(id = R.string.add_new_card),
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.weight(1.2f), // Ensures centering
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.weight(0.2f)) // Balances spacing
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Name
        SecureTextField(
            cardForm = viewModel.cardForm!!,
            type = FieldType.CARD_HOLDER_NAME,
            placeholder = { Text(stringResource(id = R.string.name), color = Color.Gray) },
            textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(8.dp)) // Rounded corners
                .background(colorResource(id = com.nojom.client.R.color.white)),
            shape = OutlinedTextFieldDefaults.shape,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent, // Remove focus border
                unfocusedBorderColor = Color.Transparent, // Remove unfocused border
                disabledBorderColor = Color.Transparent, // Remove disabled border
                cursorColor = Color.Black,
            ),
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Card Number Field
        SecureTextField(
            cardForm = viewModel.cardForm!!,
            type = FieldType.CARD_NUMBER,
            placeholder = { Text(stringResource(id = R.string.card_number), color = Color.Gray) },
            textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(8.dp)) // Rounded corners
                .background(colorResource(id = com.nojom.client.R.color.white)),
            shape = OutlinedTextFieldDefaults.shape,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent, // Remove focus border
                unfocusedBorderColor = Color.Transparent, // Remove unfocused border
                disabledBorderColor = Color.Transparent, // Remove disabled border
                cursorColor = Color.Black
            ),
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Expiry Date & CVV
        Row(modifier = Modifier.fillMaxWidth()) {
            SecureTextField(
                cardForm = viewModel.cardForm!!,
                type = FieldType.EXPIRE_MONTH,
                placeholder = { Text(stringResource(R.string.mm), color = Color.Gray) },
                textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .height(56.dp)
                    .clip(RoundedCornerShape(8.dp)) // Rounded corners
                    .background(colorResource(id = com.nojom.client.R.color.white)),
                shape = OutlinedTextFieldDefaults.shape,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent, // Remove focus border
                    unfocusedBorderColor = Color.Transparent, // Remove unfocused border
                    disabledBorderColor = Color.Transparent, // Remove disabled border
                    cursorColor = Color.Black
                ),
            )
            Spacer(modifier = Modifier.width(8.dp))
            SecureTextField(
                cardForm = viewModel.cardForm!!,
                type = FieldType.EXPIRE_YEAR,
                placeholder = { Text(stringResource(R.string.yy), color = Color.Gray) },
                textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .height(56.dp)
                    .clip(RoundedCornerShape(8.dp)) // Rounded corners
                    .background(colorResource(id = com.nojom.client.R.color.white)),
                shape = OutlinedTextFieldDefaults.shape,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent, // Remove focus border
                    unfocusedBorderColor = Color.Transparent, // Remove unfocused border
                    disabledBorderColor = Color.Transparent, // Remove disabled border
                    cursorColor = Color.Black
                ),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        SecureTextField(
            cardForm = viewModel.cardForm!!,
            type = FieldType.CVV,
            placeholder = { Text(stringResource(id = R.string.cvv), color = Color.Gray) },
            textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(8.dp)) // Rounded corners
                .background(colorResource(id = com.nojom.client.R.color.white)),
            shape = OutlinedTextFieldDefaults.shape,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent, // Remove focus border
                unfocusedBorderColor = Color.Transparent, // Remove unfocused border
                disabledBorderColor = Color.Transparent, // Remove disabled border
                cursorColor = Color.Black
            ),
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Billing Address Title
        Text(
            text = stringResource(id = R.string.billing_address),
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Country
        CustomTextField(label = stringResource(id = R.string.country), value = country) {
            country = it
        }

        Spacer(modifier = Modifier.height(16.dp))
        // City
        CustomTextField(label = stringResource(id = R.string.city), value = city) { city = it }
        Spacer(modifier = Modifier.height(16.dp))
        // Address
        CustomTextField(label = stringResource(id = R.string.address), value = address) {
            address = it
        }
        Spacer(modifier = Modifier.height(16.dp))
        // State & Zip Code
        Row(modifier = Modifier.fillMaxWidth()) {
            CustomTextFieldW(
                state,
                { state = it },
                stringResource(id = R.string.state),
                KeyboardType.Text,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            CustomTextFieldW(
                zipCode,
                { zipCode = it },
                stringResource(id = R.string.zipcode),
                KeyboardType.Number,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Save Card Toggle
        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Keep this card saved for faster checkout in the future",
                modifier = Modifier.weight(1f)
            )
            Switch(checked = saveCard, onCheckedChange = { saveCard = it })
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Submit Button
//        Button(
//            onClick = { /* Handle Submit */ },
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(50.dp)
//                .clip(RoundedCornerShape(12.dp)),
//            colors = ButtonDefaults.buttonColors(Color.Black)
//        ) {
//            Text(text = "Submit", color = Color.White, fontSize = 16.sp)
//        }

        PaymentButton(isLoading = isLoading, onClick = {
            isLoading = true
            viewModel.handlePayment(intentId)
        })
    }
}

// Custom Composable for TextField
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    label: String,
    value: TextFieldValue,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    modifier: Modifier = Modifier,
    onValueChange: (TextFieldValue) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
//        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType, imeAction = ImeAction.Next
        ),
        placeholder = { Text(label, color = Color.Gray) },
        textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(8.dp)) // Rounded corners
            .background(colorResource(id = com.nojom.client.R.color.white)),
        shape = OutlinedTextFieldDefaults.shape,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Transparent, // Remove focus border
            unfocusedBorderColor = Color.Transparent, // Remove unfocused border
            disabledBorderColor = Color.Transparent, // Remove disabled border
            cursorColor = Color.Black
        ),

        )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextFieldW(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    label: String,
    keyboardType: KeyboardType,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier, horizontalArrangement = Arrangement.spacedBy(8.dp) // Adds spacing
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType, imeAction = ImeAction.Next
            ),
            placeholder = { Text(label, color = Color.Gray) },
            textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
            modifier = Modifier
                .weight(1f) // Takes equal space inside Row
                .height(56.dp)
                .clip(RoundedCornerShape(8.dp)) // Rounded corners
                .background(colorResource(id = com.nojom.client.R.color.white)),
            shape = OutlinedTextFieldDefaults.shape,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent, // Remove focus border
                unfocusedBorderColor = Color.Transparent, // Remove unfocused border
                disabledBorderColor = Color.Transparent, // Remove disabled border
                cursorColor = Color.Black
            ),
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewAddCardScreen() {
//    AddCardScreen()
//}


fun handleIntentCase(
    it: IntentDetails,
    cardFormActivity: AddCardActivity,
    viewModel: CardFormViewModelCopy,
    intentId: String,
    launcher: ManagedActivityResultLauncher<IntentCreationParams, IntentDetails?>
) {
    when {
        it.state.toString() == "TransactionFailed(recommendedMethods=null)" -> {
//            Toast.makeText(
//                cardFormActivity, "Transaction failed. No recommended methods.", Toast.LENGTH_SHORT
//            ).show()
            cardFormActivity.setResult(Activity.RESULT_CANCELED)
//            onDismissRequest()
            cardFormActivity.finish()
        }

        it.state.toString() == "Closed" -> {
//            Toast.makeText(
//                cardFormActivity, "Transaction failed.", Toast.LENGTH_SHORT
//            ).show()
            cardFormActivity.setResult(Activity.RESULT_CANCELED)
//            onDismissRequest()
            cardFormActivity.finish()
        }

        it.state.toString() == "IntentProcessed" -> {
//            Toast.makeText(
//                cardFormActivity, "Intent has been processed.", Toast.LENGTH_SHORT
//            ).show()
            cardFormActivity.setResult(Activity.RESULT_OK)
//            onDismissRequest()
            cardFormActivity.finish()
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

@Composable
fun PaymentButton(
    isLoading: Boolean, onClick: () -> Unit
) {
    Button(
        onClick = { if (!isLoading) onClick() }, // Prevent multiple clicks when loading
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(12.dp)),
        colors = ButtonDefaults.buttonColors(Color.Black),
        enabled = !isLoading // Disable the button while loading
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color.White, strokeWidth = 2.dp, modifier = Modifier.size(24.dp)
            )
        } else {
            Text(stringResource(id = R.string.submit_3))
        }
    }
}