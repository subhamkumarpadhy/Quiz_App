package com.example.quizical.feature_profile.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quizical.databinding.ItemCcpBinding
import com.example.quizical.feature_profile.presentation.models.CountryCode

class CountryCodeAdapter(
    private val list: MutableList<CountryCode>,
    private val context: Context,
    private val onCountryPicked: (CountryCode) -> Unit,
) : RecyclerView.Adapter<CountryCodeAdapter.CountryCodeViewerHolder>() {

    inner class CountryCodeViewerHolder(
        private val binding: ItemCcpBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: CountryCode) {
            binding.tvCountry.text = "${model.flag} ${model.name}"
            binding.tvCode.text = model.code

            binding.root.setOnClickListener {
                onCountryPicked(model)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryCodeViewerHolder {
        return CountryCodeViewerHolder(
            ItemCcpBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: CountryCodeViewerHolder, position: Int) {
        holder.bind(list[position])
    }
}