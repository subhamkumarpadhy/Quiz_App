package com.example.quizical.feature_profile.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import com.example.quizical.databinding.FragmentCcpBinding
import com.example.quizical.feature_profile.presentation.activities.getProfileViewModel
import com.example.quizical.feature_profile.presentation.adapters.CountryCodeAdapter
import com.example.quizical.feature_profile.presentation.events.ProfileEvents
import com.example.quizical.feature_profile.presentation.models.CountryCode
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CcpFragment : BottomSheetDialogFragment() {

    private val binding by lazy {
        FragmentCcpBinding.inflate(requireActivity().layoutInflater)
    }

    companion object {
        private const val TAG = "CcpFragment"
    }

    private val viewModel by lazy {
        getProfileViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindStateObservers()
    }

    private fun bindStateObservers() {
        val stateLiveData = viewModel.state.asLiveData()

        var list = mutableListOf<CountryCode>()
        val adapter = CountryCodeAdapter(
            list = list,
            context = requireActivity(),
        ) { countryCode ->
            viewModel.onEvent(ProfileEvents.OnCountryCodePicked(countryCode))
            dismiss()
        }
        binding.rvCcp.adapter = adapter

        stateLiveData.observe(requireActivity()) {state ->
            val newList = state.countryCodeList
            if (newList.isEmpty()) {
                return@observe
            }
            newList.forEach {
                list.add(it)
            }
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root
}