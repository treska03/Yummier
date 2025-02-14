package com.treska.yummier.repository

import com.treska.yummier.model.Review
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository: JpaRepository<Review, Long>, JpaSpecificationExecutor<Review> {
    fun findByRecipeId(recipeId: Long, pageable: Pageable): Page<Review>
}