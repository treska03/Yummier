package com.treska.yummier.dto

import org.springframework.http.HttpStatus

data class ErrorDto(
    val title: String,
    val status: HttpStatus = HttpStatus.BAD_REQUEST,
    val detail: String?
)
