package com.example.getir.di

import com.example.getir.data.auth.AuthRepositoryImpl
import com.example.getir.data.card.CartRepositoryImpl
import com.example.getir.data.category.CategoryRepository
import com.example.getir.data.checkout.OrderRepositoryImpl
import com.example.getir.data.product.ProductRepository
import com.example.getir.domain.auth.AuthRepository
import com.example.getir.domain.card.CartRepository
import com.example.getir.domain.category.CategoryRepositoryImpl
import com.example.getir.domain.checkout.OrderRepository
import com.example.getir.domain.product.ProductRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCategoryRepository(
        impl: CategoryRepositoryImpl
    ): CategoryRepository

    @Binds
    abstract fun bindProductRepository(
        impl: ProductRepositoryImpl
    ): ProductRepository

    @Binds
    abstract fun bindCartRepository(
        impl: CartRepositoryImpl
    ): CartRepository

@Binds
abstract fun bindOrderRepository(
    impl: OrderRepositoryImpl
): OrderRepository

    @Binds
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

}