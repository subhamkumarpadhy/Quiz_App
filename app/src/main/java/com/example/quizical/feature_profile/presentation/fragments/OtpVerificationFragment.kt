package com.example.quizical.feature_profile.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import com.example.quizical.databinding.FragmentOtpVerificationBinding
import com.example.quizical.feature_profile.presentation.activities.getProfileViewModel
import com.example.quizical.feature_profile.presentation.events.ProfileEvents
import com.example.quizical.global_utils.ui_utils.beautifyIntAsString
import com.example.quizical.global_utils.views_utils.onTextChange

class OtpVerificationFragment : Fragment() {
    private val binding by lazy {
        FragmentOtpVerificationBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        getProfileViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        bindStateObservers()
    }

    private fun initViews() {
        binding.apply {
            otpView.onTextChange { otp ->
                viewModel.onEvent(ProfileEvents.OnOtpChange(otp))
            }
            btnVerifyOtp.setOnClickListener {
                viewModel.onEvent(ProfileEvents.VerifyOtp)
            }
        }
    }

    private fun bindStateObservers() {
        val stateLiveData = viewModel.state.asLiveData()
        stateLiveData.observe(requireActivity()) { state ->
            state ?: return@observe

            binding.tvOtpExpire.text = state.otpExpiryTime.toInt().beautifyIntAsString()

            if (state.otp != binding.otpView.text.toString()) {
                binding.otpView.setText(state.otp)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root
}