package com.example.getir.presention.categoryView

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.example.getir.domain.category.Category
@Composable
fun CategoryScreen(
    viewModel: CategoryViewModel = hiltViewModel(),
    onCategoryClick: (String) -> Unit,
    onCartClick: () -> Unit
) {
    val categories = viewModel.categories.collectAsState(initial = emptyList()).value

    CategoryScreenContent(
        categories = categories,
        onCategoryClick = onCategoryClick,
        onCartClick = onCartClick
    )
}

@Composable
fun CategoryScreenContent(
    categories: List<Category>,
    onCategoryClick: (String) -> Unit,
    onCartClick: () -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier.padding(8.dp)
    ) {
        items(categories) { category ->
            CategoryCard(category) {
                onCategoryClick(category.id)
            }

        }
    }
}

@Composable
fun CategoryCard(
    category: Category,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        Card(
            modifier = Modifier
                .size(75.dp)
                .clickable { onClick() },
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    model = category.imageUrl,
                    contentDescription = category.name,
                    modifier = Modifier.
                    fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Text(
            text = category.name,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(top = 4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryScreenPreview() {
    val fakeCategories = listOf(
        Category(
            id = "1",
            name = "Süt Ürünleri",
            imageUrl = "https://via.placeholder.com/150"
        ),
        Category(
            id = "2",
            name = "Ekmek & Unlu Mamuller",
            imageUrl = "https://via.placeholder.com/150"
        ),
        Category(
            id = "3",
            name = "İçecekler",
            imageUrl = "https://via.placeholder.com/150"
        ),
        Category(
            id = "4",
            name = "Meyve & Sebze",
            imageUrl = "https://via.placeholder.com/150"
        )
    )

    MaterialTheme {
        CategoryScreenContent(
            categories = fakeCategories,
            onCategoryClick = {},
            onCartClick = {}
        )
    }
}