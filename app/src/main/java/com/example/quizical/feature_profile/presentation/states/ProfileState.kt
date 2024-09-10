package com.example.quizical.feature_profile.presentation.states

import android.net.Uri
import com.example.quizical.feature_profile.domain.models.User
import com.example.quizical.feature_profile.presentation.models.CountryCode

data class ProfileState(

    //Phone Fragment
    val countryCode: CountryCode = CountryCode(
        name = "INDIA",
        code = "91",
        flag = "🇮🇳",
    ),
    val phoneNumber: String = "",
    val countryCodeList: List<CountryCode> = emptyList(),

    //OTP Verification Fragment
    val otp: String = "",
    val otpExpiryTime: Long = 0L,

    //Setup Profile Fragment
    val userProfilePhoto: Uri? = null,
    val userName: String = "",
    val user: User = User(),

    //Others
    val currentFragmentType: CurrentFragmentType = CurrentFragmentType.PHONE_FRAGMENT,
)