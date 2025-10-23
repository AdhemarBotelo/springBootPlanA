package com.adhemar.userManagement.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import java.time.Instant

data class UserRequest(
    @field:NotBlank val username: String,
    @field:Email val email: String,
    @field:NotBlank val password: String
)

data class UserResponse(
    val id: Long,
    val username: String,
    val email: String,
    val created: Instant
)