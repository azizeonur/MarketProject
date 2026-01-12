package com.example.getir.data.category

import com.example.getir.domain.category.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategories(): Flow<List<Category>>
}