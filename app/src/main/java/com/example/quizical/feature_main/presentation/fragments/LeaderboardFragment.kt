package com.example.quizical.feature_main.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.quizical.databinding.FragmentLeaderboardBinding
import com.example.quizical.feature_main.presentation.adapters.LeaderboardAdapter
import com.example.quizical.feature_profile.domain.models.User
import kotlin.random.Random


class LeaderboardFragment : Fragment() {

    private val binding by lazy {
        FragmentLeaderboardBinding.inflate(layoutInflater)
    }

    private val list = mutableListOf<User>()
    private val adapter by lazy {
        LeaderboardAdapter(
            context = requireActivity(),
            list = list
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            rvLeaderboard.adapter = adapter

            repeat(100) {
                list.add(
                    User(
                        photo = "https://cdn-icons-png.flaticon.com/512/888/888839.png",
                        name = "User $it",
                        points = Random.nextLong(50, 1000)
                    )
                )
            }
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root
}