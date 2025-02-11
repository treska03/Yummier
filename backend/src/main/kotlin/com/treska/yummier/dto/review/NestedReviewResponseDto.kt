package com.treska.yummier.dto.review

import com.treska.yummier.model.Review

data class NestedReviewResponseDto(val id: Long, val content: String, val grade: Int) {
    companion object {
        fun from(review: Review): NestedReviewResponseDto {
            return NestedReviewResponseDto(
                review.id,
                review.content,
                review.grade
            )
        }
    }
}