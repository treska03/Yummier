package com.treska.yummier.service

import com.treska.yummier.extension.normalize
import com.treska.yummier.model.Category
import com.treska.yummier.model.Difficulty
import com.treska.yummier.model.Recipe
import com.treska.yummier.repository.RecipeRepository
import org.springframework.stereotype.Service

@Service
class RecipeService(private val recipeRepository: RecipeRepository, private val ingredientService: IngredientService) {
    fun getAll(): List<Recipe> = recipeRepository.findAll()

    fun create(
        title: String,
        description: String,
        timeNeeded: Int = 0,
        difficulty: Difficulty,
        category: Category,
        ingredients: List<String>,
        instruction: List<String>
    ): Recipe {
        val ingredients = ingredients.map { name -> ingredientService.createIfNotExists(name.normalize()) }

        val recipe = Recipe(
            title = title,
            description = description,
            timeNeeded = timeNeeded,
            difficulty = difficulty,
            category = category,
            ingredients = ingredients,
            instruction = instruction
        )

        recipeRepository.save(recipe)

        return recipe
    }

    fun delete(id: Long) {
        recipeRepository.deleteById(id)
    }
}