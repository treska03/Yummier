package com.treska.yummier.controller

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import com.treska.yummier.dto.ErrorDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class JacksonExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadable(e: HttpMessageNotReadableException): ResponseEntity<ErrorDto> {
        when (val cause = e.cause) {
            is InvalidFormatException -> return handleInvalidFormatException(cause)
            is MissingKotlinParameterException -> return handleMissingKotlinParameterException(cause)
        }

        val err = ErrorDto(
            title = e.javaClass.simpleName,
            status = HttpStatus.BAD_REQUEST,
            detail = e.message
        )
        // If it reaches this point - add more clauses to when statement
        return ResponseEntity.badRequest().body<ErrorDto>(err)
    }

    @ExceptionHandler
    private fun handleInvalidFormatException(e: InvalidFormatException): ResponseEntity<ErrorDto> {
        val targetType = e.targetType
        if (targetType.isEnum) {
            val fieldName = e.path.last().fieldName ?: "REPORT_THIS_ERROR"

            val allowedValues = targetType.enumConstants
                .joinToString(separator = ", ") { "[$it]" }

            val errorMsg = "${fieldName}=[${e.value}] is incorrect. Allowed values are: $allowedValues"

            val err = ErrorDto(
                title = e.javaClass.simpleName,
                status = HttpStatus.BAD_REQUEST,
                detail = errorMsg
            )
            return ResponseEntity.badRequest().body<ErrorDto>(err)
        }

        return ResponseEntity.badRequest().body<ErrorDto>(
            ErrorDto(
                title = e.javaClass.simpleName,
                status = HttpStatus.BAD_REQUEST,
                detail = e.message
            )
        )
    }

    private fun handleMissingKotlinParameterException(e: MissingKotlinParameterException): ResponseEntity<ErrorDto> {
        val err = ErrorDto(
            title = e.javaClass.simpleName,
            status = HttpStatus.BAD_REQUEST,
            detail = "${e.parameter.name} is required."
        )

        return ResponseEntity.badRequest().body<ErrorDto>(err)
    }
}