package com.treska.yummier.controller

import com.treska.yummier.dto.PaginatedResponseDto
import com.treska.yummier.dto.recipe.RecipeCreateDto
import com.treska.yummier.dto.recipe.RecipeEditDto
import com.treska.yummier.dto.recipe.RecipeResponseDto
import com.treska.yummier.dto.recipe.RecipeFilter
import com.treska.yummier.service.RecipeService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/recipes")
class RecipeController(
    private val recipeService: RecipeService
) {
    @GetMapping("/{id}")
    fun getRecipe(@PathVariable id: Long): RecipeResponseDto {
        return RecipeResponseDto.from(recipeService.getById(id))
    }

    @GetMapping
    fun getRecipes(
        @ModelAttribute filter: RecipeFilter,
        @PageableDefault(size = 10, sort = ["id"]) pageable: Pageable
    ): PaginatedResponseDto<RecipeResponseDto> {
        val page = recipeService.get(filter, pageable)
            .map { RecipeResponseDto.from(it) }

        return PaginatedResponseDto.from(page)
    }

    @PostMapping
    fun addRecipe(@RequestBody recipeCreateDto: RecipeCreateDto): RecipeResponseDto {
        val recipe = recipeService.create(
            title = recipeCreateDto.title,
            description = recipeCreateDto.description,
            timeNeeded = recipeCreateDto.timeNeeded,
            difficulty = recipeCreateDto.difficulty,
            category = recipeCreateDto.category,
            ingredients = recipeCreateDto.ingredients,
            instructions = recipeCreateDto.instructions
        )

        return RecipeResponseDto.from(recipe)
    }

    @PutMapping("/{id}")
    fun editRecipe(@RequestBody recipeEditDto: RecipeEditDto, @PathVariable id: Long): RecipeResponseDto {
        val recipe = recipeService.edit(
            id = id,
            title = recipeEditDto.title,
            description = recipeEditDto.description,
            timeNeeded = recipeEditDto.timeNeeded,
            difficulty = recipeEditDto.difficulty,
            category = recipeEditDto.category,
            ingredients = recipeEditDto.ingredients,
            instructions = recipeEditDto.instructions
        )

        return RecipeResponseDto.from(recipe)
    }

    @DeleteMapping("/{id}")
    fun deleteRecipe(@PathVariable id: Long) {
        recipeService.delete(id)
    }
}