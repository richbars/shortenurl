package com.richbars.shortnerurl.controller

import com.richbars.shortnerurl.repository.ShortnerRepository
import com.richbars.shortnerurl.service.ShortnerService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.view.RedirectView
import java.net.URI

@Controller
class ShortnerController(
    private val shortnerService: ShortnerService,
    private val shortnerRepository: ShortnerRepository
) {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @PostMapping("/shortner/create")
    fun create(@RequestParam url: String): ResponseEntity<Any> {

        if (!isValidUrl(url)) {
            logger.warn("[ShortnerController] Invalid URL received: {}", url)
            return ResponseEntity.badRequest().body(
                mapOf("message" to "Invalid URL format")
            )
        }

        val result = shortnerService.createShortner(url)

        val message = if (shortnerRepository.existsByUrl(url)) {
            logger.info("[ShortnerController] URL already exists in database: {}", url)
            "This URL already exists."
        } else {
            logger.info("[ShortnerController] Shortner created successfully - url: {} | code: {}", url, result?.code)
            "The shortcut was successfully created."
        }

        return ResponseEntity.ok(
            mapOf(
                "message" to message,
                "url" to result?.url,
                "code" to result?.code
            )
        )
    }


    @GetMapping("{code}")
    fun redirect(@PathVariable code: String): Any {
        val url = shortnerService.getUrlByCode(code)

        return if (url != null) {
            logger.info("[ShortnerController] URL: $url accessed successfully with code: $code")
            RedirectView(url)
        } else {
            logger.error("[ShortnerController] Invalid code ${code}")
            ResponseEntity.badRequest().body(
                mapOf("message" to "Invalid code or URL not found")
            )
        }
    }

    private fun isValidUrl(url: String): Boolean {
        return try {
            val uri = URI(url)
            val validScheme = uri.scheme == "http" || uri.scheme == "https"
            val hasHost = !uri.host.isNullOrBlank()
            validScheme && hasHost
        } catch (e: Exception) {
            false
        }
    }
}