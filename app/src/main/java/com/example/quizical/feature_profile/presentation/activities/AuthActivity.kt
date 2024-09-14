package com.example.quizical.feature_profile.presentation.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.quizical.feature_quiz.presentation.activities.MainActivity
import com.example.quizical.R
import com.example.quizical.databinding.ActivityAuthBinding
import com.example.quizical.feature_profile.data.repository.AuthRepositoryimpl
import com.example.quizical.feature_profile.presentation.fragments.OtpVerificationFragment
import com.example.quizical.feature_profile.presentation.fragments.PhoneFragment
import com.example.quizical.feature_profile.presentation.fragments.SetupProfileFragment
import com.example.quizical.feature_profile.presentation.states.CurrentFragmentType
import com.example.quizical.feature_profile.presentation.states.ProfileState
import com.example.quizical.feature_profile.presentation.viewmodels.ProfileViewModel
import com.example.quizical.feature_profile.presentation.viewmodels.factories.ProfileVMFactory
import com.example.quizical.global_utils.fragment_utils.replaceFragment
import com.example.quizical.global_utils.intent_utils.startActivity

class AuthActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityAuthBinding.inflate(layoutInflater)
    }

    private val activity = this

    val viewModel by lazy {
        val repositoryimpl = AuthRepositoryimpl()
        ViewModelProvider(activity, ProfileVMFactory(repositoryimpl))[ProfileViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        bindStateObservers()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.auth_fragment_container, PhoneFragment())
        }.commit()

    }

    private fun bindStateObservers() {
        val stateLiveData = viewModel.state.asLiveData()
        stateLiveData.observe(activity) { state ->
            state ?: return@observe
            stateFragment(state)
        }

        val globalStateLiveData = viewModel.globalState.asLiveData()
        globalStateLiveData.observe(activity) { state ->
            state ?: return@observe
            state.stateUiEvents(
                rootLayout = binding.root,
                progressHolder = binding.lrProgressHolder,
                binding = binding.layoutProgress
            )
        }
    }

    private var currentFragment = CurrentFragmentType.PHONE_FRAGMENT

    private fun stateFragment(state: ProfileState) {
        if (state.currentFragmentType == currentFragment) {
            return
        }
        currentFragment = state.currentFragmentType
        when (currentFragment) {
            CurrentFragmentType.PHONE_FRAGMENT -> replaceFragment(
                fragment = PhoneFragment(),
                containerId = R.id.auth_fragment_container
            )

            CurrentFragmentType.OTP_VERIFICATION_FRAGMENT -> replaceFragment(
                fragment = OtpVerificationFragment(),
                containerId = R.id.auth_fragment_container
            )

            CurrentFragmentType.SETUP_PROFILE_FRAGMENT -> replaceFragment(
                fragment = SetupProfileFragment(),
                containerId = R.id.auth_fragment_container
            )

            CurrentFragmentType.NONE -> startActivity(MainActivity::class.java)
        }
    }
}

fun Fragment.getProfileViewModel(): ProfileViewModel {
    return if (requireActivity() is AuthActivity)
        (requireActivity() as AuthActivity).viewModel
    else throw Exception("Not an auth activity")
}