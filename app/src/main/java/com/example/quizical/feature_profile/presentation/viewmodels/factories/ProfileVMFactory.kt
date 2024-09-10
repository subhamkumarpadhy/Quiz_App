package com.example.quizical.feature_profile.presentation.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quizical.feature_profile.domain.repository.AuthRepository
import com.example.quizical.feature_profile.presentation.viewmodels.ProfileViewModel

class ProfileVMFactory(
    private val repository: AuthRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileViewModel(repository) as T
    }
}