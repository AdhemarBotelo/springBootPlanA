package com.adhemar.userManagement.repository

import com.adhemar.userManagement.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
}