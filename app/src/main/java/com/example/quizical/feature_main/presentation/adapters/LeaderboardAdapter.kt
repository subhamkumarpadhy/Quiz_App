package com.example.quizical.feature_main.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quizical.databinding.ItemLeaderboardBinding
import com.example.quizical.feature_profile.domain.models.User
import com.example.quizical.global_utils.glide_utils.loadImage


class LeaderboardAdapter(private var list: MutableList<User>, var context: Context) :

    RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemLeaderboardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(model: User, context: Context) {
            binding.apply {
                tvIndex.text = (layoutPosition + 1).toString()
                tvUsername.text = model.name
                tvPoints.text = "${model.points} Points"

                ivUserImage.loadImage(
                    imageUrl = model.photo
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(ItemLeaderboardBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(model = list[position], context = context)
    }
}