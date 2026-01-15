package com.example.getir.presention.productView

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.getir.domain.card.CartItem
import com.example.getir.domain.product.Product
import com.example.getir.presention.cartView.CartEvent
import com.example.getir.presention.cartView.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    viewModel: ProductViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(),
    categoryId: String,
    onCartClick: () -> Unit
) {
    val products by viewModel.products.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ürünler") },
                actions = {
                    IconButton(onClick = onCartClick) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Cart"
                        )
                    }
                }
            )
        }
    ) { padding ->

        LazyColumn(modifier = Modifier.padding(padding)) {
            items(products) { product ->
                ProductItem(
                    product = product,
                    onAddToCart = {
                        cartViewModel.onEvent(
                            CartEvent.AddToCart(
                                CartItem(
                                    productId = product.id,
                                    name = product.name,
                                    price = product.price,
                                    imageUrl = product.imageUrl,
                                    quantity = 1
                                )
                            )
                        )
                    }
                )
            }
        }
    }
}
@Composable
fun ProductItem(
    product: Product,
    onAddToCart: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp)
        ) {

            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                modifier = Modifier.size(64.dp)
            )

            Spacer(Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = product.name)
                Spacer(Modifier.height(4.dp))
                Text(text = "${product.price} ₺")
            }

            Button(onClick = onAddToCart) {
                Text("Ekle")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductItemPreview() {
    val fakeProduct = Product(
        id = "1",
        name = "Süt",
        imageUrl = "",
        price = 5.0,
        categoryId = "1"
    )

    MaterialTheme {
        ProductItem(
            product = fakeProduct,
            onAddToCart = {}
        )
    }
}