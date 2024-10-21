package com.example.quizical.feature_quiz.presentation.activities

import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quizical.R
import com.example.quizical.databinding.ActivityMainBinding
import com.example.quizical.feature_main.presentation.fragments.EarningFragment
import com.example.quizical.feature_main.presentation.fragments.LeaderboardFragment
import com.example.quizical.feature_profile.presentation.fragments.ProfileFragment
import com.example.quizical.feature_quiz.presentation.fragments.QuizzesFragment
import com.example.quizical.global_utils.fragment_utils.replaceFragment
import com.google.android.material.color.MaterialColors

class MainActivity : AppCompatActivity() {
    private val activity = this
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//        val color = MaterialColors.getColor(
//            activity, com.google.android.material.R.attr.colorPrimary, Color.GRAY
//        )
//    window.statusBarColor = color
        replaceFragment(
            fragment = QuizzesFragment(),
            containerId = R.id.main_fragment_container,
            addToBackStack = false
        )
        initViews()
    }

    private fun initViews() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_quizzes -> replaceFragment(
                    fragment = QuizzesFragment(),
                    containerId = R.id.main_fragment_container,
                    addToBackStack = false
                )
                R.id.menu_leaderboard -> replaceFragment(
                    fragment = LeaderboardFragment(),
                    containerId = R.id.main_fragment_container,
                    addToBackStack = false
                )
                R.id.menu_earning -> replaceFragment(
                    fragment = EarningFragment(),
                    containerId = R.id.main_fragment_container,
                    addToBackStack = false
                )
                R.id.menu_profile -> replaceFragment(
                    fragment = ProfileFragment(),
                    containerId = R.id.main_fragment_container,
                    addToBackStack = false
                )
            }
            return@setOnItemSelectedListener true
        }
    }
}

