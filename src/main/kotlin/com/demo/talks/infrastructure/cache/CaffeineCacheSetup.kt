package com.demo.talks.infrastructure.cache

import com.github.benmanes.caffeine.cache.Cache
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableCaching
class CaffeineCacheSetup {
    @Bean
    fun cacheManager(caffeines: List<Pair<String, Cache<Any, Any>>>): CacheManager {
        val caffeineCacheManager = CaffeineCacheManager()

        caffeines.forEach {
            caffeineCacheManager.registerCustomCache(it.first, it.second)
        }
        return caffeineCacheManager
    }
}