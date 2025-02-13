package com.treska.yummier.dto

import org.springframework.data.domain.Page

data class PaginatedResponseDto<T>(
    val content: List<T>,
    val totalElements: Long,
    val totalPages: Int,
    val size: Int,
    val number: Int,
    val numberOfElements: Int,
    val first: Boolean,
    val last: Boolean,
    val empty: Boolean
) {
    companion object {
        fun <T> from(page: Page<T>): PaginatedResponseDto<T> {
            return PaginatedResponseDto(
                content = page.content,
                totalElements = page.totalElements,
                totalPages = page.totalPages,
                size = page.size,
                number = page.number,
                numberOfElements = page.numberOfElements,
                first = page.isFirst,
                last = page.isLast,
                empty = page.isEmpty
            )
        }
    }
}