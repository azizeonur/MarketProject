package com.example.getir.presention

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
                onCategoryClick = { categoryId ->
                    navController.navigate("product/$categoryId") // categoryId gönderiyoruz
                },
                onCartClick = {
                    navController.navigate(Routes.CART)
                }
            )
        }

        composable(
            route = Routes.PRODUCT,
            arguments = listOf(navArgument("categoryId") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId")!!
            ProductScreen(
                categoryId = categoryId,
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