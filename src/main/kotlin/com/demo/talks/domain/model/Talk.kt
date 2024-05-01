package com.demo.talks.domain.model

import com.demo.talks.domain.exceptions.TalkModelException
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode

@Schema(description = "Model for a Talk")
data class Talk(

        @field:Schema(
            description = "Id for the Talk",
            example = "1234",
            type = "Long"
        )
        val id: Long?,

        @field:Schema(
            description = "Title of the talk",
            example = "The myth of Narcissus and Echo",
            type = "String"
        )
        val title: String?,

        @field:Schema(
            description = "Author of the talk",
            example = "Luis Eduardo Patino",
            type = "String"
        )
        val author: String?,

        @field:Schema(
            description = "Date of the talk in the format [MMMM yyyy]",
            example = "October 2024",
            type = "String"
        )
        val date: String?,

        @field:Schema(
            description = "Views of the talk",
            example = "345",
            type = "Long",
            minimum = "0",
            maximum = "${Long.MAX_VALUE}"
        )
        @Min(0)
        @Max(Long.MAX_VALUE)
        val views: Long?,

        @field:Schema(
            description = "Likes of the talk",
            example = "5670",
            type = "Long",
            minimum = "0",
            maximum = "${Long.MAX_VALUE}"
        )
        @Min(0)
        @Max(Long.MAX_VALUE)
        val likes: Long?,

        @field:Schema(
            description = "Url or Link of the talk",
            example = "https://ted.com/talks/my-ted-talk-luis-2024",
            type = "String"
        )
        val link: String?
) {
    fun validateAllFields(){
        if (this.title.isNullOrEmpty()
                || this.author.isNullOrEmpty()
                || this.date.isNullOrEmpty()
                || this.views == null
                || this.likes == null
                || this.link.isNullOrEmpty()
        ){
            throw TalkModelException(
                    HttpStatus.BAD_REQUEST,
                    "Fields for Talk are not valid"
            )
        }
    }
}