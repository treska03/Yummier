package com.treska.yummier.dto.review

data class ReviewCreateDto(
    val recipeId: Long,
    val content: String,
    val grade: Int
)
