package com.example.getir.presention.cartView

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.getir.domain.card.CartItem

@Composable
fun CartScreen(
    viewModel: CartViewModel
) {
    val state = viewModel.uiState.collectAsState().value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Column(modifier = Modifier.fillMaxSize()) {

            // 🛒 Cart List
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(state.items) { item ->
                    CartItemRow(item = item)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 💰 Total
            Text(
                text = "Toplam: ${state.totalPrice} ₺",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            // ✅ Checkout Button
            Button(
                onClick = {
                    viewModel.onEvent(CartEvent.Checkout)
                },
                enabled = state.items.isNotEmpty() && !state.isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Siparişi Tamamla")
            }

            // ❌ Error
            state.error?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error
                )
            }

            // 🎉 Success
            if (state.isOrderSuccess) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Sipariş başarıyla alındı 🎉",
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        // ⏳ Loading Overlay
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
@Composable
fun CartItemRow(item: CartItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(item.name, style = MaterialTheme.typography.bodyLarge)
                Text(
                    text = "Adet: ${item.quantity}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Text(
                text = "${item.price * item.quantity} ₺",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    val fakeItems = listOf(
        CartItem(
            productId = "1",
            name = "Süt",
            price = 5.0,
            quantity = 2,
            imageUrl = ""
        ),
        CartItem(
            productId = "2",
            name = "Ekmek",
            price = 8.0,
            quantity = 1,
            imageUrl = ""
        )
    )

    MaterialTheme {
        LazyColumn {
            items(fakeItems) { item ->
                CartItemRow(item = item)
            }
        }
    }
}