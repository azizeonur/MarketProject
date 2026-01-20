package com.example.getir.presention.productView

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImage
import com.example.getir.data.product.ProductRepository
import com.example.getir.domain.card.CartItem
import com.example.getir.domain.product.Product
import com.example.getir.presention.cartView.CartEvent
import com.example.getir.presention.cartView.CartViewModel
import com.example.getir.ui.theme.EmeraldGreen
import com.example.getir.ui.theme.TopAppBarWithCartAndMenu

@Composable
fun ProductDetailScreen(
    productId: String,
    productViewModel: ProductDetailViewModel = hiltViewModel(),
    cartViewModel: CartViewModel,
    onBack: () -> Unit,
    onCartClick: () -> Unit,
    onLogout: () -> Unit
) {
    var quantity by remember { mutableStateOf(1) }

    val product by productViewModel.product.collectAsState()

    LaunchedEffect(productId) {
        productViewModel.loadProduct(productId)
    }

    Scaffold(
        topBar = { TopAppBarWithCartAndMenu(onCartClick = onCartClick,
            onLogout = onLogout) }
    ) { padding ->

        if (product == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = EmeraldGreen)
            }
            return@Scaffold
        }

        val p = product!!

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AsyncImage(
                model = p.imageUrl,
                contentDescription = p.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(bottom = 16.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                text = p.name,
                style = MaterialTheme.typography.titleLarge,
                color = EmeraldGreen
            )

            Text(
                text = "${p.price} ₺",
                style = MaterialTheme.typography.titleMedium,
                color = EmeraldGreen
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = { if (quantity > 1) quantity-- }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Decrease")
                }

                Text(
                    text = quantity.toString(),
                    modifier = Modifier.width(40.dp),
                    textAlign = TextAlign.Center
                )

                IconButton(onClick = { quantity++ }) {
                    Icon(Icons.Default.Add, contentDescription = "Increase")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    cartViewModel.onEvent(
                        CartEvent.AddToCart(
                            CartItem(
                                productId = p.id,
                                name = p.name,
                                price = p.price,
                                imageUrl = p.imageUrl,
                                quantity = quantity
                            )
                        )
                    )
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = EmeraldGreen,
                    contentColor = Color.White
                )
            ) {
                Text("Sepete Ekle ($quantity)")
            }
        }
    }
}