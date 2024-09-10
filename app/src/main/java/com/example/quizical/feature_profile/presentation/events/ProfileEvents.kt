package com.example.quizical.feature_profile.presentation.events

import android.app.Activity
import android.content.Context
import android.net.Uri
import com.example.quizical.feature_profile.presentation.models.CountryCode

sealed class ProfileEvents {

    //Phone Fragment
    data class GetCountryCode(val context: Context) : ProfileEvents()
    data class OnCountryCodePicked(val countryCode: CountryCode) : ProfileEvents()
    data class SignInWithPhone(val activity: Activity,val phoneNumber: String) : ProfileEvents()

    //OTP Verification Fragment
    data class OnOtpChange(val otp: String) : ProfileEvents()
    data object VerifyOtp : ProfileEvents()

    //Setup Profile Fragment
    data class OnImagePicked(val image: Uri) : ProfileEvents()
    data class CreateUser(val name: String, val context: Context) : ProfileEvents()
    data object GetCurrentUser : ProfileEvents()


    //Edit Profile Activity
    data class UpdateUser(val name: String) : ProfileEvents()
    data object DeleteProfile : ProfileEvents()

}