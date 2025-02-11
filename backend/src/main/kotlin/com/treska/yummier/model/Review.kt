package com.treska.yummier.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity
@Table(name = "review")
data class Review(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long = 0,
    @ManyToOne @JoinColumn(name="recipe_id") @JsonBackReference val recipe: Recipe,
    val content: String,
    val grade: Int
) {
    override fun toString(): String {
        return "Review(id=$id, recipeId=${recipe.id}, content='$content', grade=$grade)"
    }
}