package com.treska.yummier.controller

import com.treska.yummier.dto.ErrorDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class RecipeExceptionHandler {
    @ExceptionHandler
    fun handleException(e: Exception): ResponseEntity<ErrorDto> {
        val responseStatus = e.javaClass.getAnnotation(ResponseStatus::class.java)

        val err = ErrorDto(
            title = e.javaClass.simpleName,
            status = responseStatus?.value ?: HttpStatus.BAD_REQUEST,
            detail = e.message
        )

        if (responseStatus != null) {
            return ResponseEntity.status(responseStatus.value).body<ErrorDto>(err)
        }

        return ResponseEntity.internalServerError().body<ErrorDto>(err)
    }
}