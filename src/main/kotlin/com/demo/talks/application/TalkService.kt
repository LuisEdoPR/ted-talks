package com.demo.talks.application

import com.demo.talks.domain.TalkCustomRepository
import com.demo.talks.domain.exceptions.TalkException
import com.demo.talks.domain.model.Talk
import com.demo.talks.infrastructure.api.dto.TalkSearch
import com.demo.talks.infrastructure.database.TalkEntity
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class TalkService(
    private val talkRepository: TalkCustomRepository
) {

    @Cacheable("talks", sync = true)
    fun getTalks(page: Int, size: Int): Page<Talk> {
        return talkRepository.findAll(PageRequest.of(page, size))
                .map(TalkEntity::getTalkDomain)
    }

    @Cacheable("talks", sync = true)
    fun search(talkSearch: TalkSearch): List<Talk> {
        return talkRepository.searchTalk(talkSearch).map { it.getTalkDomain() }
    }

    @Cacheable("talks", sync = true)
    @Throws(TalkException::class)
    fun getTalkById(id: String): Talk {
        val talkEntity: TalkEntity = talkRepository
                .findById(id)
                .orElseThrow {
                    TalkException(
                            HttpStatus.NOT_FOUND,
                            "Talk with Id: $id not found"
                    )
                }

        return talkEntity.getTalkDomain()
    }

    @Throws(TalkException::class)
    @CacheEvict("talks", allEntries = true)
    fun createTalk(talk: Talk): Talk {
        talk.validateAllFields()
        if (talkRepository.existsByTitle(talk.title!!)) {
            throw TalkException(
                    HttpStatus.BAD_REQUEST,
                    "Talk with title:'${talk.title}' already exists"
            )
        }

        val entity: TalkEntity = TalkEntity.getFromDomain(talk)
        return talkRepository.save(entity).getTalkDomain()
    }

    @Throws(TalkException::class)
    @CacheEvict("talks", allEntries = true)
    fun loadTalks(talks: List<Talk>) {
        val entities: List<TalkEntity> = talks.map { TalkEntity.getFromDomain(it) }
        talkRepository.saveAll(entities)
    }

    @Throws(TalkException::class)
    @CacheEvict("talks", allEntries = true)
    fun updateTalk(id: String, talk: Talk): Talk {
        val talkEntity: TalkEntity = talkRepository
                .findById(id)
                .orElseThrow {
                    TalkException(
                            HttpStatus.NOT_FOUND,
                            "Talk can't be updated, Talk with Id: '${talk.id}' not found"
                    )
                }

        talkEntity.title = talk.title?: talkEntity.title
        talkEntity.author = talk.author?: talkEntity.author
        talkEntity.date = talk.date?: talkEntity.date
        talkEntity.likes = talk.likes?: talkEntity.likes
        talkEntity.link = talk.link?: talkEntity.link
        talkEntity.views = talk.views?: talkEntity.views

        return talkRepository.save(talkEntity).getTalkDomain()
    }

    @Throws(TalkException::class)
    @CacheEvict("talks", allEntries = true)
    fun deleteTalk(id: String) {
        val talkEntity: TalkEntity = talkRepository
                .findById(id)
                .orElseThrow {
                    TalkException(
                            HttpStatus.NOT_FOUND,
                            "Talk can't be deleted, talk with Id: '$id' not found"
                    )
                }

        talkRepository.delete(talkEntity)
    }

}