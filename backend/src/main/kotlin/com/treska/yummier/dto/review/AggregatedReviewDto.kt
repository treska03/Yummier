package com.treska.yummier.dto.review

import com.treska.yummier.model.Review

data class AggregatedReviewDto(
    val reviews: List<NestedReviewResponseDto>,
    val average: Double,
    val totalCount: Int
) {
    companion object {
        fun from(reviews: List<Review>): AggregatedReviewDto {
            return AggregatedReviewDto(
                reviews = reviews.map { NestedReviewResponseDto.from(it) },
                average = reviews.avg(),
                totalCount = reviews.size
            )
        }

        private fun List<Review>.avg(): Double =
            map { it.grade }.average().let { if(it.isNaN()) 0.0 else it }
    }
}