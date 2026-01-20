package com.example.getir.data.product

import com.example.getir.data.address.AddressDto
import com.example.getir.data.auth.AuthResponseDto
import com.example.getir.data.auth.LoginRequestDto
import com.example.getir.data.auth.RegisterRequestDto
import com.example.getir.data.card.CartItemDto
import com.example.getir.data.category.CategoryDto
import com.example.getir.data.checkout.OrderRequest
import com.example.getir.data.payment.PaymentRequestDto
import com.example.getir.data.payment.PaymentResponseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductApi {


    @GET("products/{categoryId}")
    suspend fun getProducts(
        @Path("categoryId") categoryId: String
    ): List<ProductDto>


    @GET("categories")
    suspend fun getCategories(): List<CategoryDto>


    @GET("cart")
    suspend fun getCart(): List<CartItemDto>

    @POST("cart")
    suspend fun addToCart(
        @Body item: CartItemDto
    )

    @DELETE("cart")
    suspend fun clearCart()

    @POST("orders")
    suspend fun createOrder(
        @Body request: OrderRequest
    )

    @DELETE("cart/{product_id}")
    suspend fun removeFromCart(@Path("product_id") productId: String)

    @GET("products/detail/{productId}")
    suspend fun getProductDetail(
        @Path("productId") productId: String
    ): ProductDto


    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequestDto
    ): AuthResponseDto

    @POST("auth/register")
    suspend fun register(
        @Body request: RegisterRequestDto
    ): AuthResponseDto


    // --- Address ---
    @POST("address") // Sonuna slash ekle
    suspend fun saveAddress(@Body address: AddressDto)

    @GET("address/{userId}") // Diğerlerine de ekleyebilirsin
    suspend fun getAddresses(
        @Path("userId") userId: String
    ): List<AddressDto>

    @DELETE("address/{addressId}")
    suspend fun deleteAddress(@Path("addressId") addressId: String)

    @PUT("address")
    suspend fun updateAddress(@Body address: AddressDto)

    @GET("address/detail/{addressId}")
    suspend fun getAddressById(
        @Path("addressId") addressId: String
    ): AddressDto

    @POST("payment/process")
    suspend fun processPayment(@Body payment: PaymentRequestDto): PaymentResponseDto
}

