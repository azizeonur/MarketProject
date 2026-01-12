package com.example.getir.presention.categoryView

import androidx.lifecycle.ViewModel
import com.example.getir.domain.category.GetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    val categories = getCategoriesUseCase()
}