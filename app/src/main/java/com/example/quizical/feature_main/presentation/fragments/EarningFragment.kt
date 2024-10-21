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
import com.example.quizical.feature_main.domain.models.EarningHistory
import com.example.quizical.feature_main.domain.models.EarningsType
import com.example.quizical.feature_main.presentation.adapters.EarningHistoryAdapter
import com.example.quizical.feature_main.presentation.adapters.LeaderboardAdapter
import com.example.quizical.feature_profile.domain.models.User
import com.example.quizical.feature_profile.presentation.activities.getProfileViewModel
import com.example.quizical.feature_profile.presentation.adapters.CountryCodeAdapter
import com.example.quizical.feature_profile.presentation.events.ProfileEvents
import com.example.quizical.feature_profile.presentation.models.CountryCode
import com.example.quizical.global_utils.ui_utils.hideView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.random.Random

class EarningFragment : Fragment() {

    private val binding by lazy {
        FragmentEarningBinding.inflate(layoutInflater)
    }

    private val list = mutableListOf<EarningHistory>()
    private val adapter by lazy {
        EarningHistoryAdapter(
            context = requireActivity(),
            list = list
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            rvEarningHistory.adapter = adapter

            repeat(100) { index ->
                list.add(
                    EarningHistory(
                        points = Random.nextLong(100,1000),
                        type = if ((index % 2) == 1) EarningsType.Plus else EarningsType.Minus
                    )
                )
            }
            if (list.isNotEmpty()) {
                tvHistoryNotFound.hideView()
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root
}