package com.demo.talks.infrastructure.database

import com.demo.talks.domain.TalkCustomRepository
import com.demo.talks.infrastructure.api.dto.TalkSearch
import com.demo.talks.infrastructure.database.jpa.TalkJPARepository
import jakarta.persistence.EntityManager
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.util.*


@Repository
class TalkCustomRepositoryImpl(
    private val talkRepository: TalkJPARepository,
    private val entityManager: EntityManager
): TalkCustomRepository {

    override fun searchTalk(talkSearch: TalkSearch): List<TalkEntity> {
        val builder: CriteriaBuilder = entityManager.criteriaBuilder
        val query: CriteriaQuery<TalkEntity> = builder.createQuery(TalkEntity::class.java)
        val entity: Root<TalkEntity> = query.from(TalkEntity::class.java)

        val predicates = mutableListOf<Predicate>()

        if (!talkSearch.title.isNullOrEmpty()) {
            predicates.add(builder.like(entity.get("title"), "%${talkSearch.title}%"))
        }
        if (!talkSearch.author.isNullOrEmpty()) {
            predicates.add(builder.like(entity.get("author"), "%${talkSearch.author}%"))
        }
        if (talkSearch.minViews != null && talkSearch.maxViews != null) {
            predicates.add(builder.between(entity.get("views"), talkSearch.minViews!!, talkSearch.maxViews!!))
        }
        if (talkSearch.minLikes != null && talkSearch.maxLikes != null) {
            predicates.add(builder.between(entity.get("likes"), talkSearch.minLikes!!, talkSearch.maxLikes!!))
        }

        query.where(*predicates.toTypedArray())

        return entityManager.createQuery(query).resultList
    }

    // Default JPA Methods
    override fun findAll(pageable: Pageable): Page<TalkEntity> =  talkRepository.findAll(pageable)

    override fun findById(id: String): Optional<TalkEntity> =  talkRepository.findById(id)

    override fun existsByTitle(title: String): Boolean =  talkRepository.existsByTitle(title)

    override fun save(entity: TalkEntity): TalkEntity =  talkRepository.save(entity)

    override fun saveAll(entities: List<TalkEntity>): MutableList<TalkEntity> =  talkRepository.saveAll(entities)

    override fun delete(entity: TalkEntity) =  talkRepository.delete(entity)

}