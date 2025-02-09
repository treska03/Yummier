package com.treska.yummier.extension

fun String.normalize() = lowercase().replaceFirstChar { it.uppercase() }