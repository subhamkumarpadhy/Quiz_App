package com.example.quizical.global_utils.fragment_utils

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

fun Activity.replaceFragment(
    fragment: Fragment,
    @IdRes containerId: Int, /*= R.id.fragment_container*/
    addToBackStack: Boolean = true,
    transition: Int = FragmentTransaction.TRANSIT_FRAGMENT_OPEN,
    extraData: (bundle: Bundle) -> Unit = {},
) {
    val fragmentActivity = this as FragmentActivity
    fragmentActivity.supportFragmentManager.beginTransaction().apply {
        setTransition(transition)
        val bundle = Bundle()
        extraData(bundle)
        fragment.arguments = bundle
        replace(containerId, fragment)
        if (addToBackStack)
            addToBackStack(null)
    }.commit()
}


private fun Context.openBottomSheetBuilder(
    sheetFragment: BottomSheetDialogFragment,
    extraData: (bundle: Bundle) -> Unit = {},
) {
    val bundle = Bundle()
    sheetFragment.arguments = bundle
    extraData(bundle)
    sheetFragment.show(
        (this as FragmentActivity).supportFragmentManager,
        sheetFragment.tag
    )
}


fun Activity.openSheet(
    sheetFragment: BottomSheetDialogFragment,
    extraData: (bundle: Bundle) -> Unit = {},
) {
    this.openBottomSheetBuilder(sheetFragment, extraData)
}

fun Fragment.openSheet(
    sheetFragment: BottomSheetDialogFragment,
    extraData: (bundle: Bundle) -> Unit = {},
) {
    requireActivity().openBottomSheetBuilder(sheetFragment, extraData)
}

fun Context.openSheet(
    sheetFragment: BottomSheetDialogFragment,
    extraData: (bundle: Bundle) -> Unit = {},
) {
    openBottomSheetBuilder(sheetFragment, extraData)
}