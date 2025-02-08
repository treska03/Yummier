package com.treska.yummier.dto

import com.treska.yummier.extension.normalize
import com.treska.yummier.model.Category
import com.treska.yummier.model.Difficulty
import com.treska.yummier.model.Recipe

data class RecipeDto(
    var title: String,
    var description: String,
    var timeNeeded: Int = 0,
    var difficulty: Difficulty,
    var category: Category,
    val ingredients: List<String>,
) {
    companion object {
        fun from(recipe: Recipe): RecipeDto {
            return RecipeDto(
                recipe.title,
                recipe.description,
                recipe.timeNeeded,
                recipe.difficulty,
                recipe.category,
                recipe.ingredients.map { it.name.normalize() }
            )
        }
    }
}
