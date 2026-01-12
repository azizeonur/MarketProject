package com.example.getir.presention

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.getir.presention.cartView.CartScreen
import com.example.getir.presention.categoryView.CategoryScreen
import com.example.getir.presention.productView.ProductScreen

@Composable
fun GetirNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.CATEGORY
    ) {

        composable(Routes.CATEGORY) {
            CategoryScreen(
                onCategoryClick = {
                    navController.navigate(Routes.PRODUCT)
                },
                onCartClick = {
                    navController.navigate(Routes.CART)
                }
            )
        }

        composable(Routes.PRODUCT) {
            ProductScreen(
                onAddToCart = { cartItem ->
                    // Product → Cart event
                },
                onCartClick = {
                    navController.navigate(Routes.CART)
                }
            )
        }

        composable(Routes.CART) {
            CartScreen()
        }
    }
}