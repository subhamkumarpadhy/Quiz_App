package com.example.quizical.global_utils.ui_utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding

/**
 * Closes the soft keyboard if it's open.
 */
fun Activity.closeKeyboard() {
    val view: View? = this.currentFocus
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

/**
 * Toggles the status bar visibility.
 */
fun Activity.toggleStatusBar() {
    val flags = window.decorView.systemUiVisibility
    if (flags and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
        window.decorView.systemUiVisibility = flags or View.SYSTEM_UI_FLAG_FULLSCREEN
    } else {
        window.decorView.systemUiVisibility = flags and View.SYSTEM_UI_FLAG_FULLSCREEN.inv()
    }
}

fun Activity.toggleStatusBar(shouldHide: Boolean) {
    val flags = window.decorView.systemUiVisibility
    if (!shouldHide) {
        window.decorView.systemUiVisibility = flags or View.SYSTEM_UI_FLAG_FULLSCREEN
    } else {
        window.decorView.systemUiVisibility = flags and View.SYSTEM_UI_FLAG_FULLSCREEN.inv()
    }
}

fun View.makeClickable() {
    isClickable = true
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        focusable = View.FOCUSABLE
    }
    @SuppressLint("ObsoleteSdkInt")
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val selectableItemBackground = getSelectableItemBackground(context)
        foreground = selectableItemBackground
    }
}

private fun getSelectableItemBackground(context: Context): Drawable? {
    val outValue = TypedValue()
    context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
    val resourceId = outValue.resourceId
    return if (resourceId != 0) {
        ContextCompat.getDrawable(context, resourceId)
    } else {
        null
    }
}


/**
 * Hides the view by setting its visibility to GONE.
 */
fun View.hideView() {
    if (visibility != View.GONE)
        visibility = View.GONE
}

/**
 * Makes the view invisible by setting its visibility to INVISIBLE.
 */
fun View.invisibleView() {
    if (visibility != View.INVISIBLE)
        visibility = View.INVISIBLE
}

/**
 * Shows the view by setting its visibility to VISIBLE.
 */
fun View.showView() {
    if (visibility != View.VISIBLE)
        visibility = View.VISIBLE
}

/**
 * Batch operation to hide multiple views.
 */
fun batchHideViews(vararg views: View) {
    views.forEach {
        it.hideView()
    }
}

/**
 * Batch operation to make multiple views invisible.
 */
fun batchInvisibleViews(vararg views: View) {
    views.forEach {
        it.invisibleView()
    }
}

/**
 * Batch operation to show multiple views.
 */
fun batchShowViews(vararg views: View) {
    views.forEach {
        it.showView()
    }
}

/**
 * Disables the view.
 */
fun View.disable() {
    if (isEnabled)
        isEnabled = false
}

/**
 * Enables the view.
 */
fun View.enable() {
    if (!isEnabled)
        isEnabled = true
}

/**
 * Batch operation to disable multiple views.
 */
fun batchDisable(vararg views: View) {
    views.forEach {
        it.disable()
    }
}

/**
 * Batch operation to enable multiple views.
 */
fun batchEnable(vararg views: View) {
    views.forEach {
        it.enable()
    }
}

/**
 * Sets the clickability of the view.
 * @param value True to make the view clickable, false otherwise.
 */
fun View.clickable(value: Boolean) {
    isClickable = value
}

/**
 * Toggles the clickability of the view.
 */
fun View.toggleClickable() {
    clickable(!isClickable)
}

/**
 * Sets the opacity of the view.
 * @param value The opacity value (alpha) for the view.
 */
fun View.setOpacity(value: Float?) {
    value ?: return
    alpha = value
}

/**
 * Sets the shadow elevation of the view.
 * @param elevation The shadow elevation for the view.
 */
fun View.setShadow(elevation: Float?) {
    elevation ?: return
    this.elevation = elevation
}

/**
 * Sets the text size of a TextView.
 * @param size The text size value to set.
 */
fun TextView.setSize(size: Float) {
    textSize = size
}

/**
 * Sets the text direction of a TextView.
 * @param isTextLTR Flag indicating whether the text direction is left-to-right.
 */
fun TextView.setTextDirection(isTextLTR: Boolean? = true) {
    isTextLTR ?: return
    textDirection = if (isTextLTR)
        TextView.TEXT_DIRECTION_LTR
    else
        TextView.TEXT_DIRECTION_RTL
}

/**
 * Sets the height of the view.
 * @param height The height value for the view.
 */
fun View.setHeight(height: Float? = null) {
    height ?: return
    val layoutParams = this.layoutParams
    if (layoutParams != null) {
        layoutParams.height = height.toInt()
        this.layoutParams = layoutParams
    } else {
        val newLayoutParams =
            ViewGroup.LayoutParams(width, height.toInt())
        this.layoutParams = newLayoutParams
    }
}

/**
 * Sets the width of the view.
 * @param width The width value for the view.
 */
fun View.setWidth(width: Float? = null) {
    width ?: return
    val layoutParams = this.layoutParams
    if (layoutParams != null) {
        layoutParams.width = width.toInt()
        this.layoutParams = layoutParams
    } else {
        val newLayoutParams =
            ViewGroup.LayoutParams(width.toInt(), height)
        this.layoutParams = newLayoutParams
    }
}

/**
 * Sets the padding of the view.
 * @param padding The padding value for the view.
 */
fun View.setViewPadding(padding: Float? = null) {
    padding ?: return
    setPadding(padding.toInt())
}

/**
 * Sets the margin of the view.
 * @param margin The margin value for the view.
 */
fun View.setMargin(margin: Float?) {
    margin ?: return
    setMarginTop(margin)
    setMarginBottom(margin)
    setMarginLeft(margin)
    setMarginRight(margin)
}

/**
 * Sets the top margin of the view.
 * @param margin The top margin value for the view.
 */
fun View.setMarginTop(margin: Float?) {
    margin?.let {
        val params = layoutParams as? ViewGroup.MarginLayoutParams ?: return
        params.topMargin = it.toInt()
        layoutParams = params
    }
}

/**
 * Sets the bottom margin of the view.
 * @param margin The bottom margin value for the view.
 */
fun View.setMarginBottom(margin: Float?) {
    margin?.let {
        val params = layoutParams as? ViewGroup.MarginLayoutParams ?: return
        params.bottomMargin = it.toInt()
        layoutParams = params
    }
}

/**
 * Sets the left margin of the view.
 * @param margin The left margin value for the view.
 */
fun View.setMarginLeft(margin: Float?) {
    margin?.let {
        val params = layoutParams as? ViewGroup.MarginLayoutParams ?: return
        params.leftMargin = it.toInt()
        layoutParams = params
    }
}

/**
 * Sets the right margin of the view.
 * @param margin The right margin value for the view.
 */
fun View.setMarginRight(margin: Float?) {
    margin?.let {
        val params = layoutParams as? ViewGroup.MarginLayoutParams ?: return
        params.rightMargin = it.toInt()
        layoutParams = params
    }
}




















