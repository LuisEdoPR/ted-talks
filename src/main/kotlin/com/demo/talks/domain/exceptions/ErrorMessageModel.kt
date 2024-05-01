package com.demo.talks.domain.exceptions

import io.swagger.v3.oas.annotations.media.Schema

class ErrorMessageModel(
    @field:Schema(
        description = "Http status code related to the error",
        example = "404",
        type = "Int"
    )
    var status: Int? = null,
    @field:Schema(
        description = "Description of the error",
        example = "Not Found",
        type = "String"
    )
    var message: String? = null
)