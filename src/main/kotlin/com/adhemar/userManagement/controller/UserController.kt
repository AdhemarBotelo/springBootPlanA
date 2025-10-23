package com.adhemar.userManagement.controller

import com.adhemar.userManagement.dto.UserRequest
import com.adhemar.userManagement.dto.UserResponse
import com.adhemar.userManagement.service.UserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(private val service: UserService) {

    @PostMapping
    fun create(@RequestBody @Valid req: UserRequest) = service.createUser(req)

    @GetMapping
    fun list() = service.getAll()

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): ResponseEntity<UserResponse> {
        return try {
            val user = service.getById(id)
            ResponseEntity.ok(user)
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody @Valid req: UserRequest) = service.update(id, req)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = service.delete(id)
}