package com.treska.yummier.dto.review

import com.treska.yummier.model.Review

data class ReviewResponseDto(val id: Long, val recipeId: Long, val content: String, val grade: Int) {
    companion object {
        fun from(review: Review): ReviewResponseDto {
            return ReviewResponseDto(
                review.id,
                review.recipe.id,
                review.content,
                review.grade
            )
        }
    }
}
