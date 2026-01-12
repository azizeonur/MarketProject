package com.example.getir.data.product

import com.example.getir.data.card.CartItemDto
import com.example.getir.data.category.CategoryDto
import com.example.getir.data.product.ProductDto
import com.example.getir.domain.card.CartItem
import retrofit2.http.Body
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
}

