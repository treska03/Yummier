package com.treska.yummier.dto

import com.treska.yummier.common.Category
import com.treska.yummier.common.Difficulty
import com.treska.yummier.model.Recipe

data class RecipeResponseDto(
    val id: Long,
    val title: String,
    val description: String,
    val timeNeeded: Int,
    val difficulty: Difficulty,
    val category: Category,
    val ingredients: List<String>,
    val instructions: List<String>
) {
    companion object {
        fun from(recipe: Recipe): RecipeResponseDto {
            return RecipeResponseDto(
                recipe.id,
                recipe.title,
                recipe.description,
                recipe.timeNeeded,
                recipe.difficulty,
                recipe.category,
                recipe.ingredients,
                recipe.instructions
            )
        }
    }
}