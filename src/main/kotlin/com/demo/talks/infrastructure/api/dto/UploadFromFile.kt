package com.demo.talks.infrastructure.api.dto

data class UploadFromFile(
    val success: String?,
    val errors: String?,
    val errorDetails: List<String>?
)
