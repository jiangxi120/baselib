package com.rocky.baselib.ext

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment

fun View.showKeyboard() = ViewCompat.getWindowInsetsController(this)?.show(WindowInsetsCompat.Type.ime())
fun View.hideKeyboard() = ViewCompat.getWindowInsetsController(this)?.hide(WindowInsetsCompat.Type.ime())

fun Dialog.showKeyboard() = window?.decorView?.showKeyboard()
fun Dialog.hideKeyboard() = window?.decorView?.hideKeyboard()

fun Context.showKeyboard() = getActivity()?.showKeyboard()
fun Context.hideKeyboard() = getActivity()?.hideKeyboard()

fun Fragment.showKeyboard() = activity?.showKeyboard()
fun Fragment.hideKeyboard() = activity?.hideKeyboard()

fun Activity.showKeyboard() = WindowCompat.getInsetsController(window, window.decorView)?.show(
    WindowInsetsCompat.Type.ime())
fun Activity.hideKeyboard() = WindowCompat.getInsetsController(window, window.decorView)?.hide(
    WindowInsetsCompat.Type.ime())

fun Context.getActivity(): Activity? {
    return when (this) {
        is Activity -> this
        is ContextWrapper -> this.baseContext.getActivity()
        else -> null
    }
}