package com.example.quizical.feature_profile.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import com.example.quizical.global_utils.fragment_utils.openSheet
import com.example.quizical.databinding.FragmentPhoneBinding
import com.example.quizical.feature_profile.presentation.activities.getProfileViewModel
import com.example.quizical.feature_profile.presentation.events.ProfileEvents
import com.example.quizical.feature_profile.presentation.states.ProfileState

class PhoneFragment : Fragment() {
    private val binding by lazy {
        FragmentPhoneBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        getProfileViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onEvent(ProfileEvents.GetCountryCode(requireActivity()))
        initViews()
        bindStateObservers()
    }

    private fun bindStateObservers() {
        val stateLiveData = viewModel.state.asLiveData()
        stateLiveData.observe(requireActivity()) { state ->
            state ?: return@observe
            stateCountryCode(state)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun stateCountryCode(state: ProfileState) {
        val countryCode = state.countryCode
        binding.tvCcp.text = "${countryCode.flag} ${countryCode.code} ▼"
    }

    private fun initViews() {
        binding.tvCcp.setOnClickListener() {
            val cppFragment = CcpFragment()
            openSheet(
                cppFragment
            )
        }
        binding.btnSendButton.setOnClickListener() {
            val phoneNumber = binding.edtPhone.text.toString()
            viewModel.onEvent(ProfileEvents.SignInWithPhone(requireActivity(), phoneNumber))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = binding.root
}