package com.example.getir.domain.product

import com.example.getir.data.product.ProductApi
import com.example.getir.data.product.ProductDto
import javax.inject.Inject

class GetProductDetailUseCase @Inject constructor(
    private val api: ProductApi
) {
    suspend operator fun invoke(productId: String): ProductDto {
        return api.getProductDetail(productId)
    }
}