package com.adhemar.userManagement.model

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "users") //hibernate H2 error user is default table so rename it as users as it is recommended as well
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val username: String,
    val email: String,
    val password: String,
    val created: Instant = Instant.now()
)
