package com.treska.yummier.service

import com.treska.yummier.common.Category
import com.treska.yummier.common.Difficulty
import com.treska.yummier.dto.recipe.RecipeFilter
import com.treska.yummier.exception.RecipeNotFoundException
import com.treska.yummier.model.Recipe
import com.treska.yummier.repository.RecipeRepository
import com.treska.yummier.repository.SpecificationBuilder
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse
import kotlin.jvm.optionals.getOrNull

@Transactional
@Service
class RecipeService(private val recipeRepository: RecipeRepository) {
    fun get(filter: RecipeFilter, pageable: Pageable): Page<Recipe> {
        return recipeRepository.findAll(SpecificationBuilder.withFilters(filter), pageable)
    }

    fun getById(id: Long): Recipe {
        if (!recipeRepository.existsById(id)) {
            throw RecipeNotFoundException("Recipe with id=[$id] does not exist.")
        }
        return recipeRepository.findById(id).get()
    }

    fun create(
        title: String,
        description: String,
        timeNeeded: Int,
        difficulty: Difficulty,
        category: Category,
        ingredients: List<String>,
        instructions: List<String>
    ): Recipe {
        val recipe = Recipe(
            title = title,
            description = description,
            timeNeeded = timeNeeded,
            difficulty = difficulty,
            category = category,
            ingredients = ingredients,
            instructions = instructions
        )

        recipeRepository.save(recipe)

        return recipe
    }

    fun edit(
        id: Long,
        title: String,
        description: String,
        timeNeeded: Int,
        difficulty: Difficulty,
        category: Category,
        ingredients: List<String>,
        instructions: List<String>
    ): Recipe {
        val recipe = recipeRepository.findById(id).getOrElse {
            return create(title, description, timeNeeded, difficulty, category, ingredients, instructions)
        }

        modifyRecipe(recipe, title, description, timeNeeded, difficulty, category, ingredients, instructions)
        return recipeRepository.save(recipe)
    }

    fun delete(id: Long) {
        if (!recipeRepository.existsById(id)) {
            throw RecipeNotFoundException("Recipe with id=[$id] does not exist.")
        }
        recipeRepository.deleteById(id)
    }

    private fun modifyRecipe(
        recipe: Recipe,
        title: String,
        description: String,
        timeNeeded: Int,
        difficulty: Difficulty,
        category: Category,
        ingredients: List<String>,
        instructions: List<String>
    ) {
        recipe.title = title
        recipe.description = description
        recipe.timeNeeded = timeNeeded
        recipe.difficulty = difficulty
        recipe.category = category
        recipe.ingredients = ingredients
        recipe.instructions = instructions
    }
}