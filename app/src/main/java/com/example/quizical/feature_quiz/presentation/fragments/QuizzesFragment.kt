package com.example.quizical.feature_quiz.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.quizical.databinding.FragmentQuizzesBinding
import com.example.quizical.feature_quiz.domain.models.QuizzesModel
import com.example.quizical.feature_quiz.presentation.adapters.QuizzesAdapter
import kotlin.random.Random

class QuizzesFragment : Fragment() {
    private val binding by lazy {
        FragmentQuizzesBinding.inflate(layoutInflater)
    }

    private val list = mutableListOf<QuizzesModel>()
    private val adapter by lazy {
        QuizzesAdapter(
            list = list,
            context = requireActivity()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.layoutBottomSheet.apply {
            rvQuizzes.adapter = adapter

            list.apply {
                repeat(100) {
                    list.add(
                        QuizzesModel(
                            questionsCount = Random.nextInt(1, 30),
                            quizIcon = "https://cdn-icons-png.flaticon.com/512/888/888839.png",
                            quizTitle = "Random Quiz $it",
                            quizId = null
                        )
                    )
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root
}