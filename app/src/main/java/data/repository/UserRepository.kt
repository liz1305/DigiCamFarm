package com.example.digicamfarm.data.repository

import com.example.digicamfarm.data.dao.UserDao
import com.example.digicamfarm.data.entity.User

class UserRepository(private val dao: UserDao) {

    // Returns false if the email is already registered
    suspend fun register(user: User): Boolean {
        if (dao.findByEmail(user.email) != null) return false
        dao.insert(user)
        return true
    }

    // Returns the user if email and password match, otherwise null
    suspend fun login(email: String, password: String): User? =
        dao.login(email, password)
}