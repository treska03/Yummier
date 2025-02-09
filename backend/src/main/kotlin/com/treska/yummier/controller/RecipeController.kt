package com.treska.yummier.controller

import com.treska.yummier.dto.RecipeDto
import com.treska.yummier.service.RecipeService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/")
class RecipeController(
    private val recipeService: RecipeService
) {

    @GetMapping
    fun retrieveAll(): List<RecipeDto> {
        return recipeService.getAll().map { RecipeDto.from(it) }
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
            instruction = recipeCreateDto.instruction
        )

        return RecipeDto.from(recipe)
    }

    @DeleteMapping("/{id}")
    fun deleteRecipe(@PathVariable id: Long) {
        recipeService.delete(id)
    }
}