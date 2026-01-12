package com.example.getir.domain.product

import com.example.getir.data.product.ProductRepository
import com.example.getir.domain.product.Product
import kotlinx.coroutines.flow.Flow

class GetProductsUseCase(
    private val repository: ProductRepository
) {
    operator fun invoke(categoryId: String): Flow<List<Product>> {
        return repository.getProducts(categoryId)
    }
}