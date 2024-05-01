package com.demo.talks.domain.exceptions

import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @ExceptionHandler(TalkException::class)
    fun handleIllegalStateException(exception: TalkException, request: HttpServletRequest): ResponseEntity<ErrorMessageModel> {
        logger.error("Error generated when calling ${request.requestURI}", exception)
        val errorMessage = ErrorMessageModel(
            exception.statusCode.value(),
            exception.message
        )
        return ResponseEntity(errorMessage, exception.statusCode)
    }

    @ExceptionHandler
    fun handleIllegalStateException(ex: Exception): ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.message
        )
        return ResponseEntity(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}