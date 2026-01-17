package com.example.getir.presention.productView

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.getir.domain.product.Product

@Composable
fun ProductCard(
    product: Product,
    quantity: Int,
    onCardClick: () -> Unit,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(4.dp)
            .clickable(onClick = onCardClick)
    ) {

        Box(modifier = Modifier.size(120.dp)) {

            Card(
                modifier = Modifier.fillMaxSize(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = product.name,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .background(
                        Color.White.copy(alpha = 0.8f),
                        RoundedCornerShape(6.dp)
                    )
                    .padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                IconButton(
                    onClick = { if (quantity > 0) onDecrease() },
                    modifier = Modifier.size(20.dp)
                ) {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "Decrease",
                        modifier = Modifier.size(14.dp)
                    )
                }

                Text(
                    text = quantity.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(vertical = 2.dp)
                )

                IconButton(
                    onClick = onIncrease,
                    modifier = Modifier.size(20.dp)
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Increase",
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = product.name,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1
        )

        Text(
            text = "${product.price} ₺",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}