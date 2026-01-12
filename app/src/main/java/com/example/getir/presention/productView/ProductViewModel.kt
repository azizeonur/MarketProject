package com.example.getir.presention.productView

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.getir.domain.product.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val categoryId: String =
        savedStateHandle["categoryId"] ?: ""

    val products = getProductsUseCase(categoryId)
}