import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.getir.presention.map.AddressScreen
import com.example.getir.presention.map.MapScreen
import com.example.getir.presention.Routes
import com.example.getir.presention.SplashScreen
import com.example.getir.presention.authView.AuthViewModel
import com.example.getir.presention.authView.LoginScreen
import com.example.getir.presention.authView.RegisterScreen
import com.example.getir.presention.cartView.CartEvent
import com.example.getir.presention.cartView.CartScreen
import com.example.getir.presention.cartView.CartViewModel
import com.example.getir.presention.categoryView.CategoryScreen
import com.example.getir.presention.payment.PaymentScreen
import com.example.getir.presention.productView.ProductDetailScreen
import com.example.getir.presention.productView.ProductDetailViewModel
import com.example.getir.presention.productView.ProductScreen
import com.example.getir.presention.map.AddressViewModel

@Composable
fun GetirNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH,
        route = "root"
    ) {

        composable(Routes.SPLASH) {
            val authViewModel: AuthViewModel = hiltViewModel()

            LaunchedEffect(Unit) {
                kotlinx.coroutines.delay(1500)

                if (authViewModel.isLoggedIn()) {
                    navController.navigate(Routes.CATEGORY) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                } else {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                }
            }

            SplashScreen()
        }



        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.CATEGORY) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onNavigateRegister = { navController.navigate(Routes.REGISTER) }
            )
        }


        composable(Routes.REGISTER) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Routes.CATEGORY) {
                        popUpTo(Routes.REGISTER) {
                            inclusive = true
                        }
                    }
                },
                onNavigateLogin = { navController.popBackStack() }
            )
        }

        composable(Routes.CATEGORY) {
            val authViewModel: AuthViewModel = hiltViewModel()

            CategoryScreen(
                onCategoryClick = { categoryId ->
                    navController.navigate("product/$categoryId")
                },
                onLogout = {
                    authViewModel.logOut()
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.CATEGORY) { inclusive = true }
                    }
                }
            )
        }

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

            val productDetailViewModel: ProductDetailViewModel = hiltViewModel()
            val authViewModel: AuthViewModel = hiltViewModel()

            ProductDetailScreen(
                productId = productId,
                productViewModel = productDetailViewModel,
                cartViewModel = cartViewModel,
                onBack = { navController.popBackStack() },
                onCartClick = { navController.navigate(Routes.CART) },
                onLogout = {
                    authViewModel.logOut()
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.CATEGORY) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.CART) {
            val cartViewModel: CartViewModel =
                hiltViewModel(navController.getBackStackEntry("root"))
            val authViewModel: AuthViewModel = hiltViewModel()


            CartScreen(
                viewModel = cartViewModel,
                onBack = { navController.popBackStack() },
                onLogout = {
                    authViewModel.logOut()
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.CATEGORY) { inclusive = true }
                    }
                },
                onCheckout = {
                    navController.navigate("address_graph")

                }
            )
        }
        navigation(
            route = "address_graph",
            startDestination = Routes.ADDRESS
        ) {

            composable(Routes.ADDRESS) {
                val parentEntry = remember(navController) {
                    navController.getBackStackEntry("address_graph")
                }

                val addressViewModel: AddressViewModel = hiltViewModel(parentEntry)
                val authViewModel: AuthViewModel = hiltViewModel()

                AddressScreen(
                    viewModel = addressViewModel,
                    onLogout = {
                        authViewModel.logOut()
                        navController.navigate(Routes.LOGIN) {
                            popUpTo(Routes.CATEGORY) { inclusive = true }
                        }
                    },
                    onOpenMap = {
                        navController.navigate(Routes.MAP)
                    },
                    onNavigateToPayment = {
                        navController.navigate(Routes.PAYMENT)
                    },
                    onBack = { navController.popBackStack() }
                )
            }

            composable(Routes.MAP) {
                val parentEntry = remember(navController) {
                    navController.getBackStackEntry("address_graph")
                }

                val addressViewModel: AddressViewModel = hiltViewModel(parentEntry)
                val authViewModel: AuthViewModel = hiltViewModel()

                MapScreen(
                    viewModel = addressViewModel,
                    onBack = {
                        navController.popBackStack()
                    },
                    onLogout = {
                        authViewModel.logOut()
                        navController.navigate(Routes.LOGIN) {
                            popUpTo(Routes.CATEGORY) { inclusive = true }
                        }
                    }
                )
            }
        }

        composable(Routes.PAYMENT) {

            val cartViewModel: CartViewModel =
                hiltViewModel(navController.getBackStackEntry("root"))

            val authViewModel: AuthViewModel = hiltViewModel()

            PaymentScreen(
                cartViewModel = cartViewModel,
                onPaymentSuccess = {
                    cartViewModel.onEvent(CartEvent.ClearCart)
                    navController.navigate(Routes.CATEGORY) {
                        popUpTo(Routes.PAYMENT) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() },
                onLogout = {
                    authViewModel.logOut()
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.CATEGORY) { inclusive = true }
                    }
                }
            )
        }
    }
}