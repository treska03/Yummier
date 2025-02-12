package com.treska.yummier.repository

import com.treska.yummier.dto.review.ReviewResponseDto
import com.treska.yummier.model.Review
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository: JpaRepository<Review, Long> {
    fun findByRecipeId(recipeId: Long): List<Review>
}