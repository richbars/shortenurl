package com.richbars.shortnerurl.service

import com.richbars.shortnerurl.entity.ShortnerEntity
import com.richbars.shortnerurl.repository.ShortnerRepository
import org.springframework.stereotype.Service

@Service
class ShortnerService(
    private val shortnerRepository: ShortnerRepository
) {

    fun createShortner(url: String): ShortnerEntity? {

        if (shortnerRepository.existsByUrl(url)) {
            return shortnerRepository.findByUrl(url)!!
        }

        val shortner = ShortnerEntity(
            url = url,
            code = generateCode()
        )

        return shortnerRepository.save(shortner)

    }

    fun getUrlByCode(code: String): String? {
        return shortnerRepository.findByCode(code)?.url
    }

    private fun generateCode(): String {
        val chars = ('A'..'Z') + ('a'..'z')
        return (1..8)
            .map { chars.random() }
            .joinToString("")
    }

}