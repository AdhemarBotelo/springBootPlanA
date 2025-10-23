package com.adhemar.userManagement

import com.adhemar.userManagement.dto.UserRequest
import com.adhemar.userManagement.model.User
import com.adhemar.userManagement.repository.UserRepository
import com.adhemar.userManagement.service.UserService
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class UserServiceTest {

    @MockK
    lateinit var repo: UserRepository

    @InjectMockKs
    lateinit var service: UserService

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `should create a user`() {
        val request = UserRequest("Alice", "alice@mail.com", "25")
        val savedUser = User(1, "Alice", "alice@mail.com", "25")
        every { repo.save(any()) } returns savedUser

        val result = service.createUser(request)

        assert(result.id == savedUser.id)
        assert(result.username == savedUser.username)
        verify { repo.save(any()) }
    }

    @Test
    fun `should return user by id`() {
        val user = User(1, "Bob", "bob@mail.com", "30")
        every { repo.findById(user.id) } returns Optional.of(user)

        val result = service.getById(1)

        assert(result.email == user.email)
        verify { repo.findById(user.id) }
    }

    @Test
    fun `should update user`() {
        val existingUser = User(1, "Old", "old@mail.com", "20")
        val updatedReq = UserRequest("New", "new@mail.com", "22")
        val updated = existingUser.copy(username = "New", email = "new@mail.com", password = "23")

        every { repo.findById(1) } returns Optional.of(existingUser)
        every { repo.save(any()) } returns updated

        val result = service.update(1, updatedReq)

        assert(result.username == updated.username)
        verify { repo.save(any()) }
    }

    @Test
    fun `should delete user`() {
        every { repo.deleteById(1) } just Runs

        service.delete(1)

        verify { repo.deleteById(1) }
    }
}