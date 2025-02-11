package com.treska.yummier.controller

import com.treska.yummier.dto.review.ReviewCreateDto
import com.treska.yummier.dto.review.ReviewResponseDto
import com.treska.yummier.model.Review
import com.treska.yummier.service.ReviewService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/review/")
class ReviewController(private val reviewService: ReviewService) {

    @GetMapping("/{recipeId}")
    fun getByRecipeId(
        @PathVariable recipeId: Long,
        @PageableDefault(size = 10, sort = ["id"]) pageable: Pageable
    ): List<ReviewResponseDto> {
        return reviewService.getReviewsByRecipeId(recipeId = recipeId).map { ReviewResponseDto.from(it) }
    }

    @PostMapping
    fun create(@RequestBody reviewCreateDto: ReviewCreateDto): ReviewResponseDto {
        val review = reviewService.createReview(
            recipeId = reviewCreateDto.recipeId,
            content = reviewCreateDto.content,
            grade = reviewCreateDto.grade
        )

        return ReviewResponseDto.from(review)
    }

    @DeleteMapping("/{id}")
    fun remove(@PathVariable id: Long) {
        reviewService.deleteReview(id)
    }
}