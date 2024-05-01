package com.demo.talks.domain

import com.demo.talks.infrastructure.api.dto.TalkSearch
import com.demo.talks.infrastructure.database.TalkEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface TalkCustomRepository {

    // Custom Methods
    fun searchTalk(talkSearch: TalkSearch): List<TalkEntity>

    //JPA Methods
    fun findAll(pageable: Pageable): Page<TalkEntity>
    fun findById(id: String): Optional<TalkEntity>
    fun existsByTitle(title: String): Boolean
    fun save(entity: TalkEntity): TalkEntity
    fun saveAll(entities: List<TalkEntity>): MutableList<TalkEntity>
    fun delete(entity: TalkEntity)
}