package com.example.quizical.global_utils.ui_utils

import android.app.Activity
import android.content.Context
import android.content.res.Configuration

/**
 * Checks whether dark mode is enabled in the current system configuration.
 * @param context The context to retrieve the system configuration.
 * @return True if dark mode is enabled, false otherwise.
 */
fun isDarkModeEnabled(context: Context): Boolean {
    val activity = (context as? Activity) ?: return false
    return activity.resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES

}


/**
 * Runs code based on the current UI mode (light or dark).
 * @param onLightMode The code to be executed when the UI mode is light.
 * @param onDarkMode The code to be executed when the UI mode is dark.
 */
fun Context.runCodeBaseOnUiMode(
    onLightMode: () -> Unit = {},
    onDarkMode: () -> Unit = {},
) {
    if (isDarkModeEnabled(this)) onDarkMode()
    else onLightMode()
}
