package com.treska.yummier.service

import com.treska.yummier.dto.RecipeFilter
import com.treska.yummier.exception.RecipeNotFoundException
import com.treska.yummier.extension.normalize
import com.treska.yummier.model.Category
import com.treska.yummier.model.Difficulty
import com.treska.yummier.model.Recipe
import com.treska.yummier.repository.RecipeRepository
import com.treska.yummier.repository.SpecificationBuilder
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class RecipeService(private val recipeRepository: RecipeRepository, private val ingredientService: IngredientService) {
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
        timeNeeded: Int = 0,
        difficulty: Difficulty,
        category: Category,
        ingredients: List<String>,
        instructions: List<String>
    ): Recipe {
        val ingredients = ingredients.map { name -> ingredientService.createIfNotExists(name.normalize()) }

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

    fun delete(id: Long) {
        if(!recipeRepository.existsById(id)) {
            throw RecipeNotFoundException("Recipe with id=[$id] does not exist.")
        }
        recipeRepository.deleteById(id)
    }
}