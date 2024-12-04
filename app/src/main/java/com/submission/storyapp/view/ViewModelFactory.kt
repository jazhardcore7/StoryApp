package com.submission.storyapp.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.submission.storyapp.data.repository.AuthRepository
import com.submission.storyapp.data.repository.StoryRepository
import com.submission.storyapp.data.repository.UserRepository
import com.submission.storyapp.inject.Injection
import com.submission.storyapp.view.addstory.AddStoryViewModel
import com.submission.storyapp.view.login.LoginViewModel
import com.submission.storyapp.view.main.MainViewModel
import com.submission.storyapp.view.register.RegisterViewModel
import com.submission.storyapp.view.storydetail.StoryDetailViewModel

class ViewModelFactory(
    private val userRepository: UserRepository,
    private val storyRepository: StoryRepository,
    private val authRepository: AuthRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(userRepository, authRepository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(storyRepository, userRepository) as T
            }
            modelClass.isAssignableFrom(StoryDetailViewModel::class.java) -> {
                StoryDetailViewModel(storyRepository) as T
            }
            modelClass.isAssignableFrom(AddStoryViewModel::class.java) -> {
                AddStoryViewModel(storyRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(
                        Injection.provideUserRepository(context),
                        Injection.provideStoryRepository(context),
                        Injection.provideAuthRepository()
                    )
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}