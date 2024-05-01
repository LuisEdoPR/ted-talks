package com.demo.talks.infrastructure.api.dto

import com.github.javafaker.Faker
import java.text.SimpleDateFormat

class TalkSearchMother {
    companion object {
        private val faker = Faker()
        private val dateFormat = SimpleDateFormat("MMMM yyyy")

        fun buildSearchByTitle(): TalkSearch {
            return TalkSearch(
                title = faker.book().title(),
                null,
                null,
                null,
                null,
                null
            )
        }
    }
}