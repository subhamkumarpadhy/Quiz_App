package com.example.quizical.global_utils.alert_utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

/**
 * Builds and displays a Snackbar with the specified parameters.
 * @param view The view on which the Snackbar should be displayed.
 * @param message The message to be displayed in the Snackbar.
 * @param action The optional action text for the Snackbar.
 * @param shouldSlide True if the Snackbar should use slide animation, false for fade animation.
 * @param onAction The code to be executed when the action in the Snackbar is clicked.
 * @return The created Snackbar instance, or null if the message is null.
 */
private fun snackBarBuilder(
    view: View,
    message: String?,
    action: String? = null,
    shouldSlide: Boolean = true,
    onAction: (snackBar: Snackbar) -> Unit = {},
): Snackbar? {
    message ?: return null
    val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT).apply {
        action?.let {
            setAction(it) { _ ->
                onAction(this)
            }
        }
        animationMode = if (shouldSlide) {
            Snackbar.ANIMATION_MODE_SLIDE
        } else {
            Snackbar.ANIMATION_MODE_FADE
        }
    }
    snackBar.show()
    return snackBar
}

/**
 * Shows a Snackbar on the specified view with the given message and optional action.
 * @param message The message to be displayed in the Snackbar.
 * @param action The optional action text for the Snackbar.
 * @param shouldSlide True if the Snackbar should use slide animation, false for fade animation.
 * @param onAction The code to be executed when the action in the Snackbar is clicked.
 * @return The created Snackbar instance, or null if the message is null.
 */
fun View.showSnackBar(
    message: String?,
    action: String? = null,
    shouldSlide: Boolean = true,
    onAction: (snackBar: Snackbar) -> Unit = {},
): Snackbar? {
    return snackBarBuilder(
        view = this,
        message = message,
        action = action,
        shouldSlide = shouldSlide,
        onAction = onAction
    )
}
