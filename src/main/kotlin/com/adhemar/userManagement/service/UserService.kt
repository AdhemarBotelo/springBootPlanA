package com.adhemar.userManagement.service

import com.adhemar.userManagement.dto.*
import com.adhemar.userManagement.model.User
import com.adhemar.userManagement.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val repo: UserRepository) {
    fun createUser(request: UserRequest): UserResponse {
        val user = repo.save(User(username = request.username, email = request.email, password = request.password))
        return UserResponse(user.id, user.username, user.email, user.created)
    }

    fun getAll(): List<UserResponse> = repo.findAll().map { UserResponse(it.id, it.username, it.email, it.created) }

    fun getById(id: Long): UserResponse =
        repo.findById(id).map {it.toResponse() }.orElseThrow()

    fun delete(id: Long) = repo.deleteById(id)

    fun update(id: Long, req: UserRequest): UserResponse {
        val existing = repo.findById(id).orElseThrow { NoSuchElementException("User not found") }
        val updated = existing.copy(username = req.username, email = req.email, password = req.password)
        return repo.save(updated).toResponse()
    }

    private fun User.toResponse() = UserResponse(id, username, email, created)
}