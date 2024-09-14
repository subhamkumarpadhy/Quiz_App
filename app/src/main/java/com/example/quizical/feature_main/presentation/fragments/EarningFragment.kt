package com.example.quizical.feature_main.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import com.example.quizical.R
import com.example.quizical.databinding.FragmentCcpBinding
import com.example.quizical.databinding.FragmentEarningBinding
import com.example.quizical.feature_profile.presentation.activities.getProfileViewModel
import com.example.quizical.feature_profile.presentation.adapters.CountryCodeAdapter
import com.example.quizical.feature_profile.presentation.events.ProfileEvents
import com.example.quizical.feature_profile.presentation.models.CountryCode
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EarningFragment : Fragment() {

    private val binding by lazy {
        FragmentEarningBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {

        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root
}