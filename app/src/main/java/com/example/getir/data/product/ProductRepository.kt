package com.example.getir.data.product

import com.example.getir.domain.product.Product
import javax.inject.Inject

import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(categoryId: String): Flow<List<Product>>
}