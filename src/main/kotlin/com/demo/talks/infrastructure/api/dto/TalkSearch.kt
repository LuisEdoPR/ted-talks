package com.demo.talks.infrastructure.api.dto

import com.demo.talks.domain.exceptions.TalkModelException
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.springframework.http.HttpStatus

@Schema(description = "Model for a search of talks by optional parameters (title, author, views or likes). At least one filter is required")
data class TalkSearch(

    @field:Schema(
        description = "Part of the title of the talk",
        example = "The myth",
        type = "String"
    )
    val title: String?,

    @field:Schema(
        description = "Part of the Author of the talk",
        example = "Luis",
        type = "String"
    )
    val author: String?,

    @field:Schema(
        description = "Minimum of Views of the talk to filter",
        defaultValue = "0",
        example = "50",
        type = "Long",
        minimum = "0",
        maximum = "${Long.MAX_VALUE}"
    )
    @Min(0)
    @Max(Long.MAX_VALUE)
    var minViews: Long?,

    @field:Schema(
        description = "Maximum of Views of the talk to filter",
        defaultValue = "${Long.MAX_VALUE}",
        example = "123456",
        type = "Long",
        minimum = "0",
        maximum = "${Long.MAX_VALUE}"
    )
    @Min(0)
    @Max(Long.MAX_VALUE)
    var maxViews: Long?,

    @field:Schema(
        description = "Minimum of Likes of the talk to filter",
        defaultValue = "0",
        example = "50",
        type = "Long",
        minimum = "0",
        maximum = "${Long.MAX_VALUE}"
    )
    @Min(0)
    @Max(Long.MAX_VALUE)
    var minLikes: Long?,

    @field:Schema(
        description = "Maximum of Likes of the talk to filter",
        defaultValue = "${Long.MAX_VALUE}",
        example = "12456",
        type = "Long",
        minimum = "0",
        maximum = "${Long.MAX_VALUE}"
    )
    @Min(0)
    @Max(Long.MAX_VALUE)
    var maxLikes: Long?
) {
    fun validate() {
        if (
            author.isNullOrEmpty()
            && title.isNullOrEmpty()
            && minViews == null
            && maxViews == null
            && minLikes == null
            && maxLikes == null
        ) {
            throw TalkModelException(
                HttpStatus.BAD_REQUEST,
                "All fields are empty, please send minimum one filter from options"
            )
        }

        if (minViews != null || maxViews != null) {
            minViews = minViews ?: 0
            maxViews = maxViews ?: Long.MAX_VALUE
        }
        if (minLikes != null || maxLikes != null) {
            minLikes = minLikes ?: 0
            maxLikes = maxLikes ?: Long.MAX_VALUE
        }
    }
}
