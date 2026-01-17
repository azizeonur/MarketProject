package com.example.getir.domain.product

import com.example.getir.data.card.CartItemDto
import com.example.getir.data.product.ProductApi
import com.example.getir.data.product.ProductRepository
import com.example.getir.data.product.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton

class ProductRepositoryImpl @Inject constructor(
    private val api: ProductApi
) : ProductRepository{

    override fun getProducts(categoryId: String): Flow<List<Product>> = flow {
        val response = api.getProducts(categoryId)
        emit(response.map { it.toDomain() })
    }

        override suspend fun getProductDetail(productId: String): Product {
            val productDto = api.getProductDetail(productId)
            return Product(
                id = productDto.id,
                name = productDto.name,
                price = productDto.price,
                imageUrl = productDto.imageUrl,
                categoryId = productDto.categoryId
            )
        }

    override suspend fun addToCart(product: Product, quantity: Int) {
        api.addToCart(
            CartItemDto(
                productId = product.id,
                quantity = quantity,
                price = product.price,
                imageUrl = product.imageUrl,
                name = product.name
            )
        )
    }
    }
