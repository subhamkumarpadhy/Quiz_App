package com.example.quizical.global_utils.intent_utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.fragment.app.Fragment
import java.util.UUID

/**
 * Private function to build and start an activity with the specified parameters.
 * @param context The context from which the activity is started.
 * @param activity The target activity class.
 * @param finishAfter True if the current activity should finish after starting the new activity.
 * @param extraData Additional data to be passed to the new activity.
 * @param customCode Custom code to modify the intent before starting the activity.
 */
private fun <T> startActivityBuilder(
    context: Context,
    activity: Class<T>,
    finishAfter: Boolean = false,
    vararg extraData: Pair<String, Any>,
    customCode: (Intent) -> Unit = {},
) {
    val intent = Intent()
    intent.apply {
        customCode(this)
        extraData.forEach {
            val key = it.first
            var intData: Int? = null
            var longData: Long? = null
            var stringData: String? = null
            when (it.second) {
                is String -> stringData = it.second as String
                is Long -> longData = it.second as Long
                is Int -> intData = it.second as Int
            }

            intData?.let { value ->
                putExtra(key, value)
            }
            longData?.let { value ->
                putExtra(key, value)
            }
            stringData?.let { value ->
                putExtra(key, value)
            }
        }
        setClass(context, activity)
    }
    context.startActivity(intent)
    if (finishAfter)
        (context as Activity).finish()
}

/**
 * Starts an activity from an Activity context.
 * @param activity The target activity class.
 * @param finishAfter True if the current activity should finish after starting the new activity.
 * @param extraData Additional data to be passed to the new activity.
 * @param customCode Custom code to modify the intent before starting the activity.
 */
fun <T> Activity.startActivity(
    activity: Class<T>,
    finishAfter: Boolean = false,
    vararg extraData: Pair<String, Any>,
    customCode: (Intent) -> Unit = {},
) {
    startActivityBuilder(
        context = this,
        activity = activity,
        finishAfter = finishAfter,
        extraData = extraData,
        customCode = customCode
    )
}

/**
 * Starts an activity from a Fragment context.
 * @param activity The target activity class.
 * @param finishAfter True if the current activity should finish after starting the new activity.
 * @param extraData Additional data to be passed to the new activity.
 * @param customCode Custom code to modify the intent before starting the activity.
 */
fun <T> Fragment.startActivity(
    activity: Class<T>,
    finishAfter: Boolean = false,
    vararg extraData: Pair<String, Any>,
    customCode: (Intent) -> Unit = {},
) {
    startActivityBuilder(
        context = requireActivity(),
        activity = activity,
        finishAfter = finishAfter,
        extraData = extraData,
        customCode = customCode
    )
}

/**
 * Starts an activity from a general Context.
 * @param activity The target activity class.
 * @param finishAfter True if the current activity should finish after starting the new activity.
 * @param extraData Additional data to be passed to the new activity.
 * @param customCode Custom code to modify the intent before starting the activity.
 */
fun <T> Context.startActivity(
    activity: Class<T>,
    finishAfter: Boolean = false,
    vararg extraData: Pair<String, Any>,
    customCode: (Intent) -> Unit = {},
) {
    startActivityBuilder(
        context = this,
        activity = activity,
        finishAfter = finishAfter,
        extraData = extraData,
        customCode = customCode
    )
}

/**
 * Starts an activity from a general Context.
 * @param activity The target activity class as String.
 * @param finishAfter True if the current activity should finish after starting the new activity.
 * @param extraData Additional data to be passed to the new activity.
 * @param customCode Custom code to modify the intent before starting the activity.
 */
fun Context.startActivity(
    activity: String?,
    finishAfter: Boolean = false,
    vararg extraData: Pair<String, Any>,
    customCode: (Intent) -> Unit = {},
) {
    activity ?: return
    try {
        startActivityBuilder(
            context = this,
            activity = Class.forName(activity),
            finishAfter = finishAfter,
            extraData = extraData,
            customCode = customCode
        )
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

/**
 * Registers an ActivityResultLauncher with the specified contract and callback.
 * @param contract The ActivityResultContract for the launcher.
 * @param callback The callback to handle the result of the launched activity.
 * @return The registered ActivityResultLauncher.
 */
fun <I, O> ComponentActivity.registerActivityResultLauncher(
    contract: ActivityResultContract<I, O>,
    callback: ActivityResultCallback<O>,
): ActivityResultLauncher<I> {
    val key = UUID.randomUUID().toString()
    return activityResultRegistry.register(key, contract, callback)
}

/**
 * Shares text content using the system's default sharing options.
 * @param title The title of the shared content.
 * @param text The text content to be shared.
 */
fun Context.shareText(title: String, text: String) {
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.type = "text/plain"
    shareIntent.putExtra(Intent.EXTRA_SUBJECT, title)
    shareIntent.putExtra(Intent.EXTRA_TEXT, text)
    // Create a chooser to let the user choose where to share the content
    val chooserIntent = Intent.createChooser(shareIntent, "Share via")

    // Verify that the intent will resolve to an activity
    if (shareIntent.resolveActivity(packageManager) != null) {
        startActivity(chooserIntent)
    }
}
