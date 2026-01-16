import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.getir.presention.Routes
import com.example.getir.presention.cartView.CartScreen
import com.example.getir.presention.cartView.CartViewModel
import com.example.getir.presention.categoryView.CategoryScreen
import com.example.getir.presention.productView.ProductScreen


@Composable
fun GetirNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.CATEGORY,
        route = "root"
    ) {

        composable(Routes.CATEGORY) {
            CategoryScreen(
                onCategoryClick = { categoryId ->
                    navController.navigate("product/$categoryId")
                },
                onCartClick = {
                    navController.navigate(Routes.CART)
                }
            )
        }

        composable(
            route = Routes.PRODUCT,
            arguments = listOf(navArgument("categoryId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->

            val categoryId =
                backStackEntry.arguments?.getString("categoryId")!!

            val cartViewModel: CartViewModel =
                hiltViewModel(navController.getBackStackEntry("root"))

            ProductScreen(
                categoryId = categoryId,
                cartViewModel = cartViewModel,
                onCartClick = {
                    navController.navigate(Routes.CART)
                }
            )
        }

        composable(Routes.CART) {

            val cartViewModel: CartViewModel =
                hiltViewModel(navController.getBackStackEntry("root"))

            CartScreen(
                viewModel = cartViewModel
            )
        }
    }
}