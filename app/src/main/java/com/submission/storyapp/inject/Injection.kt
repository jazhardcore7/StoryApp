package com.submission.storyapp.inject

import android.content.Context
import com.submission.storyapp.data.pref.UserPreference
import com.submission.storyapp.data.pref.dataStore
import com.submission.storyapp.data.remote.retrofit.ApiConfig
import com.submission.storyapp.data.repository.AuthRepository
import com.submission.storyapp.data.repository.StoryRepository
import com.submission.storyapp.data.repository.UserRepository



object Injection {
    fun provideApiConfig(): ApiConfig {
        return ApiConfig
    }

    fun provideUserRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiConfig = provideApiConfig()
        return UserRepository.getInstance(pref, apiConfig)
    }

    fun provideAuthRepository(): AuthRepository {
        val apiService = ApiConfig.getApiService("")
        return AuthRepository(apiService)
    }

    fun provideStoryRepository(context: Context): StoryRepository {
        val userRepo = provideUserRepository(context)
        val apiConfig = provideApiConfig()

        return StoryRepository.getInstance(
            userRepo,
            apiConfig
        )
    }
}