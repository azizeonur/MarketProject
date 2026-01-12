package com.example.getir.presention.cartView

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CartScreen(
    viewModel: CartViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsState().value

    LazyColumn {
        items(state.items) { item ->
            Text("${item.name} x${item.quantity}")
        }
    }

    Text("Toplam: ${state.totalPrice}₺")
}