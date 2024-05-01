package com.demo.talks.infrastructure.database.jpa

import com.demo.talks.infrastructure.database.TalkEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TalkJPARepository: JpaRepository<TalkEntity, String> {

    fun existsByTitle(title: String): Boolean

}