package com.example.quizical.feature_quiz.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quizical.databinding.ItemQuizzesBinding
import com.example.quizical.feature_quiz.domain.models.QuizzesModel
import com.example.quizical.global_utils.glide_utils.loadImage

class QuizzesAdapter(private var list: MutableList<QuizzesModel>, var context: Context) :

    RecyclerView.Adapter<QuizzesAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemQuizzesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(model: QuizzesModel, context: Context) {
            binding.apply {
                tvQuestionsCount.text = "${model.questionsCount} Questions"
                tvQuizName.text = model.quizTitle
                ivQuizIcon.loadImage(
                    imageUrl = model.quizIcon
                )
                cardRoot.setOnClickListener {
                    //todo
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(ItemQuizzesBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(model = list[position], context = context)
    }
}
