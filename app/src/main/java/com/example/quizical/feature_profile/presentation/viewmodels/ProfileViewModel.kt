package com.example.quizical.feature_profile.presentation.viewmodels

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.quizical.feature_main.presentation.states.GlobalState
import com.example.quizical.feature_profile.data.repository.AuthRepositoryimpl
import com.example.quizical.feature_profile.domain.repository.AuthRepository
import com.example.quizical.feature_profile.presentation.events.ProfileEvents
import com.example.quizical.feature_profile.presentation.states.CurrentFragmentType
import com.example.quizical.feature_profile.presentation.states.ProfileState
import com.example.quizical.global_utils.bitmap_utils.compressToBitmapUri
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

class ProfileViewModel(private val repository: AuthRepository) : ViewModel() {

    companion object {
        private const val TAG = "ProfileViewModel"
    }

    var state = MutableStateFlow(ProfileState())
        private set
    var globalState = MutableStateFlow(GlobalState())
        private set

    //job
    private var getCountryJob: Job? = null
    private var signInJob: Job? = null
    private var createUserJob: Job? = null
    private var verifyOtpJob: Job? = null
    private var getCurrentUserJob: Job? = null

    //timers
    private val timer = Timer()
    private var timerTask: TimerTask? = null

    fun onEvent(event: ProfileEvents) {
        globalState.update {
            it.resetState()
        }
        Log.d(TAG, "onEvent: $event")
        when (event) {
            ProfileEvents.VerifyOtp -> verifyOTP()
            ProfileEvents.DeleteProfile -> deleteProfile()
            ProfileEvents.GetCurrentUser -> getCurrentUser()
            is ProfileEvents.UpdateUser -> updateUser()
            is ProfileEvents.CreateUser -> createUser(name = event.name, context = event.context)
            is ProfileEvents.GetCountryCode -> getCountryCodes(event.context)

            is ProfileEvents.OnCountryCodePicked -> state.update {
                it.copy(countryCode = event.countryCode)
            }

            is ProfileEvents.OnImagePicked -> state.update {
                it.copy(userProfilePhoto = event.image)
            }

            is ProfileEvents.OnOtpChange -> state.update {
                it.copy(otp = event.otp)
            }

            is ProfileEvents.SignInWithPhone -> signInWithPhone(
                activity = event.activity,
                phoneNumber = event.phoneNumber
            )
        }
    }

    private fun getCurrentUser() {
        getCurrentUserJob?.cancel()

        getCurrentUserJob = CoroutineScope(Dispatchers.IO).launch {

            repository.getCurrentUser(
                onSuccess = { user ->
                    state.update {
                        it.copy(
                            user = user
                        )
                    }
                    globalState.update {
                        it.setMessageState("Existing Profile Loaded")
                    }
                },
                onFailure = {}
            )
        }
    }

    private fun createUser(name: String, context: Context) {
        createUserJob?.cancel()

        createUserJob = CoroutineScope(Dispatchers.IO).launch {
            globalState.update {
                it.setLoadingState("Initializing Your Account...")
            }
            val uri = state.value.userProfilePhoto?.compressToBitmapUri(context)
            val user = state.value.user.copy(
                name = name
            )
            repository.createUserInDB(
                profilePhoto = uri,
                user = user,
                onSuccess = { newUser ->
                    globalState.update {
                        it.resetState()
                    }
                    state.update {
                        it.copy(
                            user = newUser,
                            currentFragmentType = CurrentFragmentType.NONE
                        )
                    }
                },
                onFailure = {exception ->
                    globalState.update {
                        it.setMessageState(exception.localizedMessage ?: "Something went wrong")
                    }
                }
            )
        }
    }

    private fun deleteProfile() {
        TODO("Not yet implemented")
    }

    private fun updateUser() {
        TODO("Not yet implemented")
    }

    private fun signInWithPhone(activity: Activity, phoneNumber: String) {
        signInJob?.cancel()
        val code = state.value.countryCode.code
        val finalPhoneNumber = "+$code$phoneNumber".trim()
        signInJob = CoroutineScope(Dispatchers.IO).launch {
            globalState.update {
                it.setLoadingState("Signing in...")
            }
            repository.signInWithPhone(
                activity = activity,
                phoneNumber = finalPhoneNumber,
                onSuccess = { user ->
                    onUserSignedIn(user)
                },
                onFailure = { exception ->
                    onUserFailedToSignIn(exception)
                },
                onCodeSent = {
                    globalState.update {
                        it.resetState()
                    }
                    state.update {
                        it.copy(
                            currentFragmentType = CurrentFragmentType.OTP_VERIFICATION_FRAGMENT,
                            otpExpiryTime = AuthRepositoryimpl.TIMEOUT
                        )
                    }
                    val totalSeconds = AuthRepositoryimpl.TIMEOUT
                    var remainingSeconds = totalSeconds
                    timerTask = object : TimerTask() {
                        override fun run() {
                            if (remainingSeconds > 0) {
                                //otp is valid
                                remainingSeconds--
                                state.update {
                                    it.copy(
                                        otpExpiryTime = remainingSeconds
                                    )
                                }
                            } else {
                                //otp expired
                                state.update {
                                    it.copy(
                                        currentFragmentType = CurrentFragmentType.PHONE_FRAGMENT,
                                        otp = "",
                                        otpExpiryTime = 0L
                                    )
                                }
                                globalState.update {
                                    it.setMessageState("OTP expired")
                                }
                                this.cancel()
                            }
                        }
                    }
                    timer.schedule(timerTask, 0, 1000)
                }
            )
        }
    }

    private fun getCountryCodes(context: Context) {
        getCountryJob?.cancel()
        getCountryJob = CoroutineScope(Dispatchers.IO).launch {
            val list = repository.getCountryCode(context = context)
            state.update {
                it.copy(
                    countryCodeList = list
                )
            }
        }
    }

    private fun verifyOTP() {

        globalState.update {
            it.setLoadingState("Verifying OTP")
        }
        val otp = state.value.otp
        repository.verifyOtp(
            otp = otp,
            onSuccess = { user ->
                onUserSignedIn(user)
            },
            onFailure = { exception ->
                onUserFailedToSignIn(exception)
            }
        )
    }

    private fun onUserSignedIn(user: FirebaseUser) {
        timerTask?.cancel()
        globalState.update {
            it.resetState()
        }
        state.update {
            it.copy(
                currentFragmentType = CurrentFragmentType.SETUP_PROFILE_FRAGMENT,
                otpExpiryTime = 0L,
                otp = ""
            )
        }
    }

    private fun onUserFailedToSignIn(exception: Exception) {
        globalState.update {
            it.setMessageState(
                exception.localizedMessage ?: "Something went wrong"
            )
        }
        if (state.value.currentFragmentType != CurrentFragmentType.PHONE_FRAGMENT) {
            state.update {
                it.copy(
                    currentFragmentType = CurrentFragmentType.PHONE_FRAGMENT
                )
            }
        }
    }
}