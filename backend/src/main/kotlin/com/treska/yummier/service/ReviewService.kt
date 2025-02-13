package com.treska.yummier.service

import com.treska.yummier.exception.RecipeNotFoundException
import com.treska.yummier.exception.ReviewNotFoundException
import com.treska.yummier.exception.ReviewOwnerException
import com.treska.yummier.model.Review
import com.treska.yummier.repository.RecipeRepository
import com.treska.yummier.repository.ReviewRepository
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
@Transactional
class ReviewService(private val recipeRepository: RecipeRepository, private val reviewRepository: ReviewRepository) {

    fun getReviewsByRecipeId(recipeId: Long, pageable: Pageable): Page<Review> {
        if (!recipeRepository.existsById(recipeId)) {
            throw RecipeNotFoundException("Recipe with id=[$recipeId] not found.")
        }
        return reviewRepository.findByRecipeId(recipeId, pageable)
    }

    fun createReview(recipeId: Long, content: String, grade: Int): Review {
        val recipe = recipeRepository.findById(recipeId).orElseThrow {
            RecipeNotFoundException("Recipe with id=[$recipeId] was not found.")
        }

        val review = Review(
            recipe = recipe,
            content = content,
            grade = grade
        )

        reviewRepository.save(review)

        return review
    }

    fun deleteReview(recipeId: Long, reviewId: Long) {
        if (!recipeRepository.existsById(recipeId)) {
            throw RecipeNotFoundException("Recipe with id=[$recipeId] not found.")
        }
        val review = reviewRepository.findById(reviewId).orElseThrow {
            ReviewNotFoundException("Review with id=[$reviewId] was not found.")
        }
        if (review.recipe.id != recipeId) {
            throw ReviewOwnerException("Recipe with id=[$recipeId] is not the owner of review with id=[$reviewId].")
        }
        reviewRepository.deleteById(reviewId)
    }
}