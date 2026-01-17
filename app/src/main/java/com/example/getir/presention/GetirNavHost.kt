import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.getir.domain.product.Product
import com.example.getir.presention.Routes
import com.example.getir.presention.authView.LoginScreen
import com.example.getir.presention.authView.RegisterScreen
import com.example.getir.presention.cartView.CartScreen
import com.example.getir.presention.cartView.CartViewModel
import com.example.getir.presention.categoryView.CategoryScreen
import com.example.getir.presention.productView.ProductDetailScreen
import com.example.getir.presention.productView.ProductDetailViewModel
import com.example.getir.presention.productView.ProductScreen
import com.example.getir.presention.productView.ProductViewModel

@Composable
fun GetirNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN,
        route = "root"
    ) {

        // --- Login ---
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.CATEGORY) {
                        popUpTo(Routes.LOGIN) { inclusive = true } // Login ekranını stackten sil
                    }
                },
                onNavigateRegister = { navController.navigate(Routes.REGISTER) }
            )
        }

        // --- Register ---
        composable(Routes.REGISTER) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Routes.CATEGORY) {
                        popUpTo(Routes.REGISTER) { inclusive = true } // Register ekranını stackten sil
                    }
                },
                onNavigateLogin = { navController.popBackStack() } // Login’e geri dön
            )
        }

        // --- Kategoriler ---
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
        // --- Kategoriler ---
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

        // --- Ürünler ---
        composable(
            route = "product/{categoryId}",
            arguments = listOf(navArgument("categoryId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->

            val categoryId = backStackEntry.arguments?.getString("categoryId")!!

            val cartViewModel: CartViewModel =
                hiltViewModel(navController.getBackStackEntry("root"))

            ProductScreen(
                categoryId = categoryId,
                cartViewModel = cartViewModel,
                onCartClick = { navController.navigate(Routes.CART) },
                onProductClick = { productId ->
                    navController.navigate("productDetail/$productId")
                }
            )
        }

        composable(
            route = "productDetail/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->

            val productId = backStackEntry.arguments?.getString("productId")!!

            val cartViewModel: CartViewModel =
                hiltViewModel(navController.getBackStackEntry("root"))

            val productDetailViewModel: ProductDetailViewModel =
                hiltViewModel()

            ProductDetailScreen(
                productId = productId,
                productViewModel = productDetailViewModel,
                cartViewModel = cartViewModel,
                onBack = { navController.popBackStack() },
                onCartClick = { navController.navigate(Routes.CART) }
            )
        }
        // --- Sepet ---
        composable(Routes.CART) {
            val cartViewModel: CartViewModel =
                hiltViewModel(navController.getBackStackEntry("root"))

            CartScreen(viewModel = cartViewModel)
        }
    }
}