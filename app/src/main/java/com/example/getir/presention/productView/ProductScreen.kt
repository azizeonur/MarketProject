package com.example.getir.presention.productView

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.getir.domain.card.CartItem
import com.example.getir.domain.product.Product
import com.example.getir.presention.cartView.CartEvent
import com.example.getir.presention.cartView.CartViewModel
import com.example.getir.ui.theme.EmeraldGreen

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    viewModel: ProductViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(),
    onProductClick: (String) -> Unit,
    categoryId: String,
    onCartClick: () -> Unit
) {
    val products by viewModel.products.collectAsState()
    val cartState by cartViewModel.uiState.collectAsState()

    val totalItemCount = cartState.items.sumOf { it.quantity }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ürünler") },
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Text(
                            text = "${cartState.totalPrice} ₺",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        BadgedBox(
                            badge = {
                                if (totalItemCount > 0) {
                                    Badge {
                                        Text(totalItemCount.toString())
                                    }
                                }
                            }
                        ) {
                            IconButton(onClick = onCartClick) {
                                Icon(
                                    imageVector = Icons.Default.ShoppingCart,
                                    contentDescription = "Cart"
                                )
                            }
                        }
                    }
                }
            )
        }
    ) { padding ->

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(padding)
        ) {
            items(
                items = products,
                key = { it.id }
            ) { product ->

                val quantityInCart =
                    cartState.items.firstOrNull { it.productId == product.id }?.quantity ?: 0

                ProductCard(
                    product = product,
                    quantity = quantityInCart,
                    onCardClick = {
                        onProductClick(product.id)
                    },
                    onIncrease = {
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
                    },
                    onDecrease = {
                        cartViewModel.onEvent(
                            CartEvent.RemoveFromCart(product.id)
                        )
                    }
                )
            }
        }
    }
}
