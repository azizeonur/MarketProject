package com.example.getir.presention.productView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getir.data.product.ProductDto
import com.example.getir.data.product.ProductRepository
import com.example.getir.domain.product.GetProductDetailUseCase
import com.example.getir.domain.product.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> = _product

    fun loadProduct(productId: String) {
        viewModelScope.launch {
            _product.value = repository.getProductDetail(productId)
        }
    }
}