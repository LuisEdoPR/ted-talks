package com.demo.talks.application

import com.demo.talks.domain.TalkCustomRepository
import com.demo.talks.domain.exceptions.TalkException
import com.demo.talks.domain.exceptions.TalkModelException
import com.demo.talks.domain.model.Talk
import com.demo.talks.domain.model.TalkMother
import com.demo.talks.infrastructure.api.dto.TalkSearchMother
import com.demo.talks.infrastructure.database.TalkEntity
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
internal class TalkServiceTest {

    private lateinit var talkService: TalkService

    @Mock
    lateinit var talkRepository: TalkCustomRepository

    @BeforeEach
    fun before() {
        talkService = TalkService(talkRepository)
    }

    @Test
    fun `should create a new Talk`() {
        // Given
        val talk = TalkMother.build()
        val talkEntity = TalkEntity.getFromDomain(talk)
        `when`(talkRepository.existsByTitle(talk.title!!)).thenReturn(false)
        `when`(talkRepository.save(talkEntity)).thenReturn(talkEntity)

        // When
        val response = talkService.createTalk(talk)

        // Then
        assertEquals(talk, response)
    }

    @Test
    fun `should fail when create a new Talk without all mandatory data`() {
        // Given
        val talk = TalkMother.build().copy(title = "")

        // Then
        val exception = assertThrows<TalkModelException> {
            talkService.createTalk(talk)
        }
        assertEquals("Fields for Talk are not valid", exception.message)
    }

    @Test
    fun `should fail when create a new Talk title already exists`() {
        // Given
        val talk = TalkMother.build()
        `when`(talkRepository.existsByTitle(talk.title!!)).thenReturn(true)

        // Then
        val exception = assertThrows<TalkException> {
            talkService.createTalk(talk)
        }
        assertEquals("Talk with title:'${talk.title}' already exists", exception.message)
    }

    @Test
    fun `Should return Talk by id`() {
        // Given
        val talk = TalkMother.build()
        val talkEntity = TalkEntity.getFromDomain(talk)
        `when`(talkRepository.findById(talk.id.toString())).thenReturn(Optional.of(talkEntity))

        // When
        val response = talkService.getTalkById(talk.id.toString())

        // Then
        assertEquals(talk, response)
    }

    @Test
    fun `Should return an Exception when Talk by do not exists`() {
        // Given
        val talk = TalkMother.build()
        `when`(talkRepository.findById(talk.id.toString())).thenReturn(Optional.empty())

        // Then
        val exception = assertThrows<TalkException> {
            talkService.getTalkById(talk.id.toString())
        }
        assertEquals("Talk with Id: ${talk.id} not found", exception.message)
    }


    @Test
    fun `Should return Talk when search by Title`() {
        // Given
        val talkSearch = TalkSearchMother.buildSearchByTitle()
        val talk = TalkMother.build().copy(title = talkSearch.title)
        val talkEntity = TalkEntity.getFromDomain(talk)
        `when`(talkRepository.searchTalk(talkSearch)).thenReturn(listOf(talkEntity))

        // When
        val response = talkService.search(talkSearch)

        // Then
        assertEquals(listOf(talk), response)
    }

    @Test
    fun `Should return empty list when search by Title have zero matches`() {
        // Given
        val talkSearch = TalkSearchMother.buildSearchByTitle()
        `when`(talkRepository.searchTalk(talkSearch)).thenReturn(emptyList())

        // When
        val response = talkService.search(talkSearch)

        // Then
        assertEquals(emptyList<Talk>(), response)
    }

}