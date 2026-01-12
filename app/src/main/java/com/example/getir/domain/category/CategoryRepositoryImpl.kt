package com.example.getir.domain.category

import com.example.getir.data.category.CategoryRepository
import com.example.getir.data.product.ProductApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CategoryRepositoryImpl(
    private val api: ProductApi
) : CategoryRepository {

    override fun getCategories(): Flow<List<Category>> = flow {
        val response = api.getCategories()
        emit(response.map { it.toDomain() })
    }
}