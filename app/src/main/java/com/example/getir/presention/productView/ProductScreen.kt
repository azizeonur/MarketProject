package com.example.getir.presention.productView

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.lazy.items

@Composable
fun ProductScreen(
    viewModel: ProductViewModel = hiltViewModel()
) {
    val products = viewModel.products
        .collectAsState(initial = emptyList())
        .value

    LazyColumn {
        items(products) { product ->
            ProductCard(product)
        }
    }
}

