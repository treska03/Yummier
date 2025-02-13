package com.treska.yummier.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
data class ReviewOwnerException(
    override val message: String?
) : IllegalArgumentException()