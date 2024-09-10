package com.example.quizical.global_utils.intent_utils

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import com.example.quizical.global_utils.alert_utils.showToast
import com.google.android.material.R
import com.google.android.material.color.MaterialColors

/**
 * Opens the URL externally using the system's default browser.
 * @param context The context from which the external browser is launched.
 */
fun MyUrl?.openExternalInBrowser(context: Context) {
    try {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(this)
        )
        context.startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
        context.showToast("Unable to Open...")
    }
}

/**
 * Opens the URL in a Chrome Custom Tab.
 * @param context The context from which the Chrome Custom Tab is launched.
 */
fun MyUrl?.openChromeTab(context: Context) {
    try {
        val colorInt = MaterialColors.getColor(context, R.attr.colorPrimary, Color.GRAY)
        val builder = CustomTabsIntent.Builder()
        builder.setToolbarColor(colorInt)
        builder.setSecondaryToolbarColor(colorInt)
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(context, Uri.parse(this))
    } catch (e: Exception) {
        e.printStackTrace()
        openExternalInBrowser(context)
    }
}

fun Context.openRateAppPage(){
    val appUrl =
        "https://play.google.com/store/apps/details?id=${packageName}"
    appUrl.openExternalInBrowser(this)
}




















