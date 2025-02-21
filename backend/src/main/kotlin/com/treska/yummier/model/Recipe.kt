package com.treska.yummier.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import com.treska.yummier.common.Category
import com.treska.yummier.common.Difficulty
import jakarta.persistence.*


@Entity
@Table(name = "recipes")
data class Recipe(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long = 0,
    var title: String = "",
    var description: String = "",
    var timeNeeded: Int = 0,
    var difficulty: Difficulty = Difficulty.MEDIUM,
    var category: Category = Category.BREAKFAST,

    @ElementCollection
    var ingredients: List<String> = listOf(),

    @ElementCollection
    var instructions: List<String> = listOf(),

    @OneToMany(mappedBy = "recipe", cascade = [CascadeType.ALL])
    @JsonManagedReference
    val reviews: List<Review> = listOf()
)