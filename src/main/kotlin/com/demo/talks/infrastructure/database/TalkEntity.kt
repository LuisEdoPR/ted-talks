package com.demo.talks.infrastructure.database

import com.demo.talks.domain.model.Talk
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "talks", schema = "test")

data class TalkEntity (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,
        var title: String,
        var author: String,
        var date: String,
        var views: Long,
        var likes: Long,
        var link: String
){

    fun getTalkDomain(): Talk {
        return Talk(
                this.id,
                this.title,
                this.author,
                this.date,
                this.views,
                this.likes,
                this.link
        )
    }

    companion object {
        fun getFromDomain(talk: Talk): TalkEntity {
            talk.validateAllFields()
            return TalkEntity(
                    talk.id ?: 0L,
                    talk.title!!,
                    talk.author!!,
                    talk.date!!,
                    talk.views!!,
                    talk.likes!!,
                    talk.link!!
            )
        }
    }
}
