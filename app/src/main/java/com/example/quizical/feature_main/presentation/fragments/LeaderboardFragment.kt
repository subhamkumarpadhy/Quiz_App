package com.example.quizical.feature_main.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.quizical.R
import com.example.quizical.databinding.FragmentEarningBinding
import com.example.quizical.databinding.FragmentLeaderboardBinding


class LeaderboardFragment : Fragment() {

    private val binding by lazy {
        FragmentLeaderboardBinding.inflate(layoutInflater)
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