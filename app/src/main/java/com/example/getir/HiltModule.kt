package com.example.getir

import com.example.getir.data.category.CategoryRepository
import com.example.getir.data.product.ProductApi
import com.example.getir.domain.category.CategoryRepositoryImpl
import com.example.getir.domain.category.GetCategoriesUseCase
import dagger.Provides

@Provides
fun provideCategoryRepository(
    api: ProductApi
): CategoryRepository = CategoryRepositoryImpl(api)

@Provides
fun provideGetCategoriesUseCase(
    repo: CategoryRepository
): GetCategoriesUseCase = GetCategoriesUseCase(repo)