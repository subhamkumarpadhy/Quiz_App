package com.example.quizical.global_utils.alert_utils

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * Builds and displays a Toast with the specified message.
 * @param context The context in which the Toast should be displayed.
 * @param message The message to be displayed in the Toast.
 */
private fun toastBuilder(
    context: Context,
    message: String?,
) {
    message ?: return
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

/**
 * Shows a Toast in the context of an Activity.
 * @param message The message to be displayed in the Toast.
 */
fun Activity.showToast(
    message: String?,
) {
    toastBuilder(this, message)
}

/**
 * Shows a Toast in the context of a Fragment.
 * @param message The message to be displayed in the Toast.
 */
fun Fragment.showToast(
    message: String?,
) {
    toastBuilder(this.requireActivity(), message)
}

/**
 * Shows a Toast in the context of general Context.
 * @param message The message to be displayed in the Toast.
 */
fun Context.showToast(
    message: String?,
) {
    toastBuilder(this, message)
}
