package com.treska.yummier.controller

import com.treska.yummier.dto.PaginatedResponseDto
import com.treska.yummier.dto.review.ReviewCreateDto
import com.treska.yummier.dto.review.ReviewResponseDto
import com.treska.yummier.model.Review
import com.treska.yummier.service.ReviewService
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/recipes/{recipeId}/reviews")
class ReviewController(private val reviewService: ReviewService) {

    @GetMapping
    fun getReviews(
        @PathVariable recipeId: Long,
        @PageableDefault(page = 0, size = 10, sort = ["id"], direction = Sort.Direction.ASC) pageable: Pageable
    ): PaginatedResponseDto<ReviewResponseDto> {
        val page = reviewService.getReviewsByRecipeId(recipeId = recipeId, pageable = pageable)
            .map { ReviewResponseDto.from(it) }

        return PaginatedResponseDto.from(page)
    }

    @PostMapping
    fun create(
        @PathVariable recipeId: Long,
        @RequestBody reviewCreateDto: ReviewCreateDto
    ): ReviewResponseDto {
        val review = reviewService.createReview(
            recipeId = recipeId,
            content = reviewCreateDto.content,
            grade = reviewCreateDto.grade
        )
        return ReviewResponseDto.from(review)
    }

    @DeleteMapping("/{reviewId}")
    fun remove(@PathVariable recipeId: Long, @PathVariable reviewId: Long) {
        reviewService.deleteReview(
            recipeId = recipeId,
            reviewId = reviewId
        )
    }
}