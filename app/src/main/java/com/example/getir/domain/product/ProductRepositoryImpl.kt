package com.example.getir.domain.product

import com.example.getir.data.product.ProductApi
import com.example.getir.data.product.ProductRepository
import com.example.getir.data.product.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductRepositoryImpl(
    private val api: ProductApi
) : ProductRepository {

    override fun getProducts(categoryId: String): Flow<List<Product>> = flow {
        val response = api.getProducts(categoryId)
        emit(response.map { it.toDomain() })
    }
}