package com.treska.yummier.model

import com.treska.yummier.common.Category
import com.treska.yummier.common.Difficulty
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
    @ElementCollection val ingredients: List<String> = listOf(),
    @ElementCollection val instructions: List<String> = listOf()
)
