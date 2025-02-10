package com.treska.yummier.controller

import com.treska.yummier.dto.RecipeDto
import com.treska.yummier.dto.RecipeFilter
import com.treska.yummier.model.Recipe
import com.treska.yummier.service.RecipeService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/")
class RecipeController(
    private val recipeService: RecipeService
) {
    @GetMapping("/{id}")
    fun getRecipe(@PathVariable id: Long): Recipe {
        return recipeService.getById(id)
    }

    @GetMapping
    fun getRecipes(
        @ModelAttribute filter: RecipeFilter?,
        @PageableDefault(size = 10, sort = ["id"]) pageable: Pageable
    ): Page<Recipe> {
        return recipeService.get(filter ?: RecipeFilter(), pageable)
    }

    @PostMapping
    fun addRecipe(@RequestBody recipeCreateDto: RecipeDto): RecipeDto {
        val recipe = recipeService.create(
            title = recipeCreateDto.title,
            description = recipeCreateDto.description,
            timeNeeded = recipeCreateDto.timeNeeded,
            difficulty = recipeCreateDto.difficulty,
            category = recipeCreateDto.category,
            ingredients = recipeCreateDto.ingredients,
            instructions = recipeCreateDto.instructions
        )

        return RecipeDto.from(recipe)
    }

    @DeleteMapping("/{id}")
    fun deleteRecipe(@PathVariable id: Long) {
        recipeService.delete(id)
    }
}