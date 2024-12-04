package com.submission.storyapp.data.repository

import com.submission.storyapp.data.remote.response.LoginResponse
import com.submission.storyapp.data.remote.response.RegisterResponse
import com.submission.storyapp.data.remote.retrofit.ApiService

class AuthRepository (
    private val apiService: ApiService,
){
    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        return apiService.register(name, email, password)
    }
    suspend fun loginUser(email: String, password: String): LoginResponse {
        return apiService.loginUser(email, password)
    }
}