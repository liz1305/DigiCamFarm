package com.example.digicamfarm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.digicamfarm.data.database.AppDatabase
import com.example.digicamfarm.data.entity.User
import com.example.digicamfarm.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = UserRepository(AppDatabase.get(app).userDao())

    // The logged-in user (null = nobody logged in). Profile will read this.
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    fun register(user: User, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            onResult(repo.register(user))
        }
    }

    fun login(email: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val user = repo.login(email.trim(), password)
            _currentUser.value = user
            onResult(user != null)
        }
    }
}