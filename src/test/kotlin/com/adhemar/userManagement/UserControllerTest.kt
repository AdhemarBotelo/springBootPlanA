package com.adhemar.userManagement

import com.adhemar.userManagement.controller.UserController
import com.adhemar.userManagement.dto.UserRequest
import com.adhemar.userManagement.dto.UserResponse
import com.adhemar.userManagement.service.UserService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.Instant


@WebMvcTest(UserController::class) // Spring Boot only loads: The controller under test , mvc configuration
@ExtendWith(SpringExtension::class)
@AutoConfigureMockMvc(addFilters = false) // disables all FilterChain like JWT auth
//@Import(SecurityConfig::class) or behave like production
class UserControllerTest() {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean //registers it in the Spring application context.
    private lateinit var userService: UserService

    private val objectMapper = jacksonObjectMapper()

    private val userRequest = UserRequest(
        username = "John Doe",
        email = "john@example.com",
        password = "12345"
    )

    private val userResponse = UserResponse(
        id = 1L,
        username = "John Doe",
        email = "john@example.com",
        created = Instant.now()
    )

    private val userList = listOf(userResponse)

    @BeforeEach
    fun setUp() {

    }

    @Test
    fun `create user - should return created user`() {
        // Given
        every { userService.createUser(any()) } returns userResponse

        // When & Then
        mockMvc.perform(
            post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(userResponse.id))
            .andExpect(jsonPath("$.username").value(userResponse.username))
            .andExpect(jsonPath("$.email").value(userResponse.email))

        verify { userService.createUser(any()) }
    }

    @Test
    fun `list users - should return all users`() {
        // Given
        every { userService.getAll() } returns userList

        // When & Then
        mockMvc.perform(get("/api/users"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(userList.size))
            .andExpect(jsonPath("$[0].id").value(userResponse.id))
            .andExpect(jsonPath("$[0].username").value(userResponse.username))

        verify { userService.getAll() }
    }

    @Test
    fun `get user by id - should return user when found`() {
        // Given
        val userId = 1L
        every { userService.getById(userId) } returns userResponse

        // When & Then
        mockMvc.perform(get("/api/users/$userId"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(userResponse.id))
            .andExpect(jsonPath("$.username").value(userResponse.username))
            .andExpect(jsonPath("$.email").value(userResponse.email))

        verify { userService.getById(userId) }
    }

    @Test
    fun `get user by id - should return 404 when not found`() {
        // Given
        val userId = 999L
        every { userService.getById(userId) } throws NoSuchElementException()

        // When & Then
        mockMvc.perform(get("/api/users/$userId"))
            .andExpect(status().isNotFound)

        verify { userService.getById(userId) }
    }

    @Test
    fun `update user - should return updated user`() {
        // Given
        val userId = 1L
        every { userService.update(userId, any()) } returns userResponse

        // When & Then
        mockMvc.perform(
            put("/api/users/$userId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(userResponse.id))
            .andExpect(jsonPath("$.username").value(userResponse.username))

        verify { userService.update(userId, any()) }
    }

    @Test
    fun `delete user - should return success`() {
        // Given
        val userId = 1L
        every { userService.delete(userId) } returns Unit

        // When & Then
        mockMvc.perform(delete("/api/users/$userId"))
            .andExpect(status().isOk)

        verify { userService.delete(userId) }
    }

    @Test
    fun `create user with invalid data - should return bad request`() {
        // Given
        val invalidRequest = UserRequest(
            username = "",
            email = "invalid-email",
            password = "password"
        )

        // When & Then - Spring will automatically return 400 for @Valid failures
        mockMvc.perform(
            post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest))
        )
            .andExpect(status().isBadRequest)
    }
}