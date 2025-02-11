package com.treska.yummier.dto.recipe

import com.treska.yummier.common.Category
import com.treska.yummier.common.Difficulty

data class RecipeCreateDto(
    val title: String,
    val description: String,
    val timeNeeded: Int = 0,
    val difficulty: Difficulty,
    val category: Category,
    val ingredients: List<String>,
    val instructions: List<String>
)