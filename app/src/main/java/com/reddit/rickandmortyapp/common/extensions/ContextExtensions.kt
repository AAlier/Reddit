package com.reddit.rickandmortyapp.common.extensions

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.reddit.rickandmortyapp.R

fun Context.showInfoDialog(message: String) {
    AlertDialog.Builder(this)
        .setMessage(message)
        .setPositiveButton(android.R.string.ok, null)
        .show()
}

fun Context.showInfoDialog(messageRes: Int) {
    showInfoDialog(getString(messageRes))
}

fun Context.showInfoDialog(throwable: Throwable? = null) {
    showInfoDialog(throwable?.message.orEmpty().ifEmpty { getString(R.string.general_error_title) })
}