package com.treska.yummier.repository

import com.treska.yummier.model.Ingredient
import org.springframework.data.jpa.repository.JpaRepository

interface IngredientRepository: JpaRepository<Ingredient, Long> {
    fun findIngredientByName(name: String): Ingredient?
}