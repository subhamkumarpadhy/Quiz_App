package com.example.quizical.feature_main.presentation.states

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import com.example.quizical.databinding.LayoutLoadingBinding
import com.example.quizical.global_utils.alert_utils.showSnackBar
import com.example.quizical.global_utils.ui_utils.closeKeyboard
import com.example.quizical.global_utils.ui_utils.hideView
import com.example.quizical.global_utils.ui_utils.showView


data class GlobalState(
    val alertMessage: String? = null,

    val isProgress: Boolean = false,
    val progressText: String = ""
) {
    fun resetState() = GlobalState()

    fun setLoadingState(progressText: String = "") = GlobalState(
        alertMessage = null,
        isProgress = true,
        progressText = progressText
    )

    fun setMessageState(message: String) = GlobalState(
        alertMessage = message,
        isProgress = false,
        progressText = progressText
    )

    fun stateUiEvents(
        rootLayout: View,
        progressHolder: ViewGroup,
        binding: LayoutLoadingBinding,
    ) {
        if (alertMessage != null) {
            rootLayout.showSnackBar(alertMessage)
        }
        if (!isProgress) {
            progressHolder.hideView()
            return
        }
        progressHolder.showView()
        binding.apply {
            tvPrgInfo.text = progressText
        }

        val activity = binding.root.context as Activity
        activity.closeKeyboard()
    }
}