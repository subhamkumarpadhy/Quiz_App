package com.example.quizical.feature_profile.domain.repository

import android.app.Activity
import android.content.Context
import android.net.Uri
import com.example.quizical.feature_profile.domain.models.User
import com.example.quizical.feature_profile.presentation.models.CountryCode
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {

    fun signInWithPhone(
        activity: Activity,
        phoneNumber: String,
        onSuccess: (FirebaseUser) -> Unit,
        onFailure: (Exception) -> Unit,
        onCodeSent: () -> Unit,
    )

    fun verifyOtp(
        otp: String,
        onSuccess: (FirebaseUser) -> Unit,
        onFailure: (Exception) -> Unit,
    )

    fun createUserInDB(
        profilePhoto: Uri?,
        user: User,
        onSuccess: (User) -> Unit,
        onFailure: (Exception) -> Unit,
    )

    fun getCurrentUser(
        onSuccess: (User) -> Unit,
        onFailure: (Exception) -> Unit,
    )

    fun getUser(
        uid: String,
        onSuccess: (User) -> Unit,
        onFailure: (Exception) -> Unit,
    )

    fun deleteProfile(
        onCompletion: (Boolean) -> Unit
    )

    fun getCountryCode(
        context: Context
    ): List<CountryCode>
}