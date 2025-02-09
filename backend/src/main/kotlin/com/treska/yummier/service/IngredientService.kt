package com.treska.yummier.service

import com.treska.yummier.model.Ingredient
import com.treska.yummier.repository.IngredientRepository
import org.springframework.stereotype.Service

@Service
class IngredientService(private val ingredientRepository: IngredientRepository) {

    fun createIfNotExists(name: String): Ingredient {
        val ingredient = ingredientRepository.findIngredientByName(name)
        return ingredient ?: ingredientRepository.save(Ingredient(name=name))
    }
}