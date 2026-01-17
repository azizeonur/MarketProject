package com.example.getir.data.product

import com.example.getir.data.auth.AuthResponseDto
import com.example.getir.data.auth.LoginRequestDto
import com.example.getir.data.auth.RegisterRequestDto
import com.example.getir.data.card.CartItemDto
import com.example.getir.data.category.CategoryDto
import com.example.getir.data.checkout.OrderRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
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
}

