package com.example.quizical.feature_main.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quizical.R
import com.example.quizical.databinding.ItemEarningHistoryBinding
import com.example.quizical.feature_main.domain.models.EarningHistory
import com.example.quizical.feature_main.domain.models.EarningsType
import com.example.quizical.global_utils.ui_utils.hideView


class EarningHistoryAdapter(private var list: MutableList<EarningHistory>, var context: Context) :

    RecyclerView.Adapter<EarningHistoryAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemEarningHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(model: EarningHistory, context: Context) {
            binding.apply {
                tvPoints.text = "${model.points} Points"
                when(model.type){
                    EarningsType.None -> tvType.hideView()
                    EarningsType.Plus -> {
                        tvType.setTextColor(context.resources.getColor(R.color.custom_blue))
                        tvType.text = "+"
                    }
                    EarningsType.Minus -> {
                        tvType.setTextColor(context.resources.getColor(R.color.custom_red))
                        tvType.text = "-"
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(ItemEarningHistoryBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(model = list[position], context = context)
    }
}