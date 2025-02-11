package com.treska.yummier.service

import com.treska.yummier.exception.RecipeNotFoundException
import com.treska.yummier.exception.ReviewNotFoundException
import com.treska.yummier.model.Review
import com.treska.yummier.repository.RecipeRepository
import com.treska.yummier.repository.ReviewRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class ReviewService(private val recipeRepository: RecipeRepository, private val reviewRepository: ReviewRepository) {

    fun getReviewsByRecipeId(recipeId: Long): List<Review> {
        if (!recipeRepository.existsById(recipeId)) {
            throw RecipeNotFoundException("Recipe with id=[$recipeId] not found.")
        }
        return reviewRepository.findByRecipeId(recipeId)
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

    fun deleteReview(reviewId: Long) {
        if (!reviewRepository.existsById(reviewId)) {
            throw ReviewNotFoundException("Review with id=[$reviewId] not found.")
        }
        reviewRepository.deleteById(reviewId)
    }
}