package com.example.getir.domain.category

import com.example.getir.data.category.CategoryDto

data class Category(
    val id : String,
    val imageUrl : String,
    val name : String

)
fun CategoryDto.toDomain(): Category {
    return Category(
        id = id,
        name = name,
        imageUrl = imageUrl
    )
}