package com.treska.yummier.dto.recipe

import com.treska.yummier.common.Category
import com.treska.yummier.common.Difficulty

data class RecipeFilter(
    val title: String? = null,
    val description: String? = null,
    val minTimeNeeded: Int? = null,
    val maxTimeNeeded: Int? = null,
    val difficulty: Difficulty? = null,
    val category: Category? = null,
    val minRatingAverage: Float? = null,
    val maxRatingAverage: Float? = null
)