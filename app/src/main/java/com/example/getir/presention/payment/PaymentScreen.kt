
package com.example.getir.presention.payment

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.getir.domain.payment.CardInfo
import com.example.getir.domain.payment.Payment
import com.example.getir.presention.cartView.CartViewModel
import com.example.getir.ui.theme.EmeraldGreen
import com.example.getir.ui.theme.TopAppBarMenu


@Composable
fun PaymentScreen(
    viewModel: PaymentViewModel = hiltViewModel(),
    cartViewModel: CartViewModel,
    onPaymentSuccess: () -> Unit,
    onBack: () -> Unit,
    onLogout: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val cartUiState by cartViewModel.uiState.collectAsState()

    var cardNumber by remember { mutableStateOf("") }
    var cardHolder by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onPaymentSuccess()
            viewModel.resetPaymentState()
        }
    }
    LaunchedEffect(cartUiState.totalPrice) {
    }

    Scaffold(
        topBar = { TopAppBarMenu(onLogout = onLogout) }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            Text("Ödeme", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Toplam Tutar")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "₺${String.format("%.2f", cartUiState.totalPrice)}",
                        style = MaterialTheme.typography.headlineLarge,
                        color = EmeraldGreen
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (uiState.isSuccess) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = EmeraldGreen.copy(alpha = 0.1f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("✓", style = MaterialTheme.typography.displayLarge)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Ödeme Başarılı!", style = MaterialTheme.typography.titleLarge)
                        uiState.transactionId?.let {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("İşlem No: $it")
                        }
                    }
                }
            } else {

                Text("Kart Bilgileri", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = cardNumber,
                    onValueChange = {
                        if (it.length <= 19) cardNumber = formatCardNumber(it)
                    },
                    label = { Text("Kart Numarası") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = cardHolder,
                    onValueChange = { cardHolder = it.uppercase() },
                    label = { Text("Kart Üzerindeki İsim") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    OutlinedTextField(
                        value = expiryDate,
                        onValueChange = {
                            if (it.length <= 5) expiryDate = formatExpiryDate(it)
                        },
                        label = { Text("Son Kullanma") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = cvv,
                        onValueChange = {
                            if (it.length <= 3 && it.all { c -> c.isDigit() }) cvv = it
                        },
                        label = { Text("CVV") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        viewModel.onEvent(
                            PaymentEvent.UpdateCardInfo(
                                CardInfo(
                                    cardNumber = cardNumber,
                                    cardHolder = cardHolder,
                                    expiryDate = expiryDate,
                                    cvv = cvv
                                )
                            )
                        )
                        viewModel.onEvent(PaymentEvent.ProcessPayment)
                    },
                    enabled = !uiState.isLoading &&
                            cardNumber.replace(" ", "").length == 16 &&
                            cardHolder.isNotBlank() &&
                            expiryDate.length == 5 &&
                            cvv.length == 3,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen)
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("İşleniyor...")
                    } else {
                        Text("₺${String.format("%.2f", cartUiState.totalPrice)} Öde")
                    }
                }

                Text(
                    "* Bu bir test ödeme sistemidir.",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            uiState.error?.let {
                Spacer(modifier = Modifier.height(12.dp))
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}
private fun formatExpiryDate(input: String): String {
    val digits = input.filter { it.isDigit() }

    return when {
        digits.length >= 3 -> {
            val month = digits.take(2)
            val year = digits.drop(2).take(2)
            "$month/$year"
        }
        else -> digits
    }
}

private fun formatCardNumber(input: String): String {
    val digits = input.filter { it.isDigit() }
    val builder = StringBuilder()

    digits.forEachIndexed { index, c ->
        if (index > 0 && index % 4 == 0) {
            builder.append(" ")
        }
        builder.append(c)
    }

    return builder.toString()
}