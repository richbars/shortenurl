package com.richbars.shortnerurl.repository

import com.richbars.shortnerurl.entity.ShortnerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ShortnerRepository : JpaRepository<ShortnerEntity, Long> {
    fun existsByUrl(url: String): Boolean
    fun findByCode(code: String): ShortnerEntity?
    fun findByUrl(url: String): ShortnerEntity?
}