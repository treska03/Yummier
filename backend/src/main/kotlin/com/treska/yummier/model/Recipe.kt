package com.treska.yummier.model

import jakarta.persistence.*


@Entity
@Table(name = "recipes")
data class Recipe(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long = 0,
    val title: String = "",
    val description: String = "",
    val timeNeeded: Int = 0,
    val difficulty: Difficulty = Difficulty.MEDIUM,
    val category: Category = Category.BREAKFAST,
    @ManyToMany val ingredients: List<Ingredient> = listOf(),
    @ElementCollection val instructions: List<String> = listOf()
)

enum class Difficulty {
    EASY, MEDIUM, HARD
}

enum class Category {
    BREAKFAST, LUNCH, DINNER, SNACK
}