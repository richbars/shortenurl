package com.richbars.shortnerurl.service

import com.richbars.shortnerurl.entity.ShortnerEntity
import com.richbars.shortnerurl.repository.ShortnerRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

@ExtendWith(MockitoExtension::class)
class ShortnerServiceTest {

    @Mock
    private lateinit var shortnerRepository: ShortnerRepository

    @InjectMocks
    private lateinit var shortnerService: ShortnerService

    @Test
    fun `should create new shortner when url does not exist`() {
        val url = "https://google.com"

        Mockito.`when`(shortnerRepository.existsByUrl(url)).thenReturn(false)
        Mockito.`when`(shortnerRepository.save(Mockito.any())).thenAnswer { it.arguments[0] }

        val result = shortnerService.createShortner(url)

        assertNotNull(result)
        assertEquals(url, result.url)
        assertNotNull(result.code)
    }

    @Test
    fun `should return existing shortner when url already exists`() {
        val url = "https://google.com"
        val existing = ShortnerEntity(1, url, "abcdefg")

        Mockito.`when`(shortnerRepository.existsByUrl(url)).thenReturn(true)
        Mockito.`when`(shortnerRepository.findByUrl(url)).thenReturn(existing)

        val result = shortnerService.createShortner(url)

        assertEquals(existing, result)
    }

    @Test
    fun `should handle database failure gracefully`() {
        val url = "https://google.com"

        Mockito.`when`(shortnerRepository.existsByUrl(url))
            .thenThrow(RuntimeException("Database connection failed"))

        val exception = assertFailsWith<RuntimeException> {
            shortnerService.createShortner(url)
        }

        assertEquals("Database connection failed", exception.message)
    }

    @Test
    fun `should throw exception if table is missing and service does not handle it`() {
        val url = "https://google.com"

        Mockito.`when`(shortnerRepository.save(Mockito.any()))
            .thenThrow(RuntimeException("Table 'urls' not found"))

        assertFailsWith<RuntimeException> {
            shortnerService.createShortner(url)
        }
    }

    @Test
    fun `should return url by code`() {
        val url = "https://www.google.com.br/"
        val code = "testx123"

        val shortner = ShortnerEntity(
            id = 1,
            url = url,
            code = code
        )

        Mockito.`when`(shortnerRepository.findByCode(code))
            .thenReturn(shortner)

        val result = shortnerService.getUrlByCode(code)

        assertNotNull(result)
        assertEquals(url, result)
    }



}
