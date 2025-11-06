package com.richbars.shortnerurl.controller

import com.richbars.shortnerurl.repository.ShortnerRepository
import com.richbars.shortnerurl.service.ShortnerService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ShortnerController::class)
class ShortnerControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var shortnerService: ShortnerService

    @MockitoBean
    private lateinit var shortnerRepository: ShortnerRepository

    @Test
    fun `should return 400 when url is invalid`() {
        mockMvc.perform(
            post("/shortner/create")
                .param("url", "invalid-url")
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.message").value("Invalid URL format"))
    }
}
