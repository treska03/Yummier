package com.treska.yummier.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
data class RecipeNotFoundException(
    override val message: String?
) : IllegalArgumentException()