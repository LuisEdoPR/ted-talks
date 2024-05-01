package com.demo.talks.domain.exceptions

import org.springframework.http.HttpStatusCode

class   TalkModelException(
        var statusCode: HttpStatusCode,
        override var message: String?,
        override var cause: Throwable? = null
) : RuntimeException(message, cause)