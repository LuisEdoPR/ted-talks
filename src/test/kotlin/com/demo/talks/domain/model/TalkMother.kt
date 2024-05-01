package com.demo.talks.domain.model

import com.github.javafaker.Faker
import java.text.SimpleDateFormat

class TalkMother {
    companion object {
        private val faker = Faker()
        private val dateFormat = SimpleDateFormat("MMMM yyyy")

        fun build(): Talk {
            return Talk(
                id = faker.number().randomNumber(),
                title = faker.book().title(),
                author = faker.book().author(),
                date = dateFormat.format(faker.date().birthday()),
                views = faker.number().randomNumber(),
                likes = faker.number().randomNumber(),
                link = faker.internet().url()
            )
        }
    }
}